const body = document.getElementsByTagName("body")[0]

// REQUEST URL BASE
// TODO: MAKE IT ENV VARIABLE
const ip = 'http://localhost'
const port = '8080'
const apiVersion = 'v1'

// COLORS DEFINITION
const shootShipColor = '#bf616a'
const shootWaterColor = '#81a1c1'
const borderColor = '#2e3440'

// BOARDS PROPERTIES
let enemyAnimation
let playerAnimation
let enemyLeft
let playerLeft
let height
let width

let gameid
// CELL INDEXING FROM [
let id = 1

// CONNECT WITH BACKEND
connect()

let createGameButton = document.createElement('button')
createGameButton.innerText = 'Create game'
createGameButton.setAttribute('onclick', 'createGame()')
createGameButton.setAttribute('id', '999')
createGameButton.classList.add("gamecreate")
document.getElementById("boardContainer").appendChild(createGameButton)

let joinGameButton = document.createElement('button')
joinGameButton.innerText = 'Join game'
joinGameButton.setAttribute('onclick', 'getGameIDFromPlayer()')
joinGameButton.setAttribute('id', '696')
joinGameButton.classList.add("gamejoin")
document.getElementById("boardContainer").appendChild(joinGameButton)

function getGameIDFromPlayer() {
    const gameID = window.prompt("Enter game id: ")
    console.log(gameID)
    joinGame(gameID)
}


function connect() {
    const socket = new SockJS('/ships-websocket')
    stompClient = Stomp.over(socket)
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame)
        stompClient.subscribe('/user/events', async function (message) {
            let event = JSON.parse(message.body)
            let eventType = event['eventType']
            switch (eventType) {
                case "ENEMY_JOIN": {
                    init('enemy')
                    break
                }
                case "ENEMY_MISS": {
                    let cell = event['cell']
                    await shoot(cell, 'enemy')
                    swapBoards()
                    break
                }
                case "ENEMY_SHOT": {
                    let cell = event['cell']
                    await shoot(cell, 'enemy')
                    break
                }
                case "ENEMY_WIN": {
                    let cell = event['cell']
                    await shoot(cell, 'enemy')
                    alert('YOU LOST!')
                    window.location.reload()
                }
            }
        })
    });

}

async function shoot(x, type) {
    if (type === 'enemy') {
        let playerCell = document.getElementById(x + ":player")
        let color
        if (playerCell.classList.contains('ship')) {
            color = shootShipColor
        } else {
            color = shootWaterColor
        }
        await shootCell(x, color, type)
        return
    }
    let cellId = x.id.toString().split(":")[0]
    const requestURL = ip + ":" + port + "/api/" + apiVersion + "/shots?game-id=" + gameid + "&cell-id=" + cellId
    const request = new Request(requestURL, {
        method: 'POST',
        mode: 'cors'
    })
    const response = await fetch(request)
    const shootMessage = await response.json()
    const shotResult = shootMessage['shotResult']
    console.log(shootMessage);
    switch (shotResult) {
        case 'MISS': {
            await shootCell(cellId, shootWaterColor)
            swapBoards()
            break
        }
        case 'SHIP_HIT': {
            await shootCell(cellId, shootShipColor)
            break
        }
        case 'SHIP_SUNK': {
            let cellsList = shootMessage['cells']
            cellsList.forEach(c => {
                shootCell(c, shootWaterColor)
            })
            break
        }
        case 'FLEET_SUNK': {
            let cellsList = shootMessage['cells']
            cellsList.forEach(c => {
                shootCell(c, shootWaterColor)
            })
            alert('YOU WON!')
            window.location.reload()
            break
        }
    }
}

async function shootCell(x, color, type) {
    if (type === 'enemy') {
        document.getElementById(x + ":player").removeAttribute("onclick")
        document.getElementById(x + ":player").style.backgroundColor = color
        document.getElementById(x + ":player").style.borderColor = borderColor
        document.getElementById(x + ":player").style.cursor = 'default'
        await delay(100)
        return
    }

    document.getElementById(x + ":enemy").removeAttribute("onclick")
    document.getElementById(x + ":enemy").style.backgroundColor = color
    document.getElementById(x + ":enemy").style.borderColor = borderColor
    document.getElementById(x + ":enemy").style.cursor = 'default'
    await delay(100)
}

async function resetBoardContainer() {
    const boardContainer = document.getElementById("boardContainer")
    boardContainer.removeChild(createGameButton)
    boardContainer.removeChild(joinGameButton)
    boardContainer.style.backgroundColor = "#3b4252"
    boardContainer.style.borderColor = "#3b4252"
}


async function createGame() {
    enemyAnimation = 'fadein'
    playerAnimation = 'fadeout'
    enemyLeft = '30vw'
    playerLeft = '-50vw'
    await resetBoardContainer()

    const game = {
        "board": {
            "width": 10,
            "height": 10
        }
    }


    const header = new Headers()
    header.append('Content-Type', 'application/json')
    header.append('Authorization', 'Basic dGVzdEBlbWFpbC5jb206dGVzdA==')

    const requestURL = ip + ":" + port + "/api/" + apiVersion + "/games"
    const request = new Request(requestURL, {
        method: 'POST',
        body: JSON.stringify(game),
        headers: header,
        mode: 'cors'
    })

    const response = await fetch(request)
    const createdGame = await response.json()
    const gameID = createdGame["id"].toString()
    const boardSize = createdGame["board"]

    width = boardSize["width"]
    height = boardSize["height"]

    const fleet = createdGame['fleet']

    gameid = gameID
    let displayId = document.createElement('p')
    displayId.classList.add('game-id')
    displayId.textContent = 'game-id:' + gameid
    body.appendChild(displayId)
    console.log(gameID)
    init('player', fleet)
}

async function joinGame(gameID) {
    enemyAnimation = 'fadeout'
    playerAnimation = 'fadein'
    enemyLeft = '-50vw'
    playerLeft = '30vw'
    await resetBoardContainer()
    const requestURL = ip + ":" + port + "/api/" + apiVersion + "/games/" + gameID
    const request = new Request(requestURL, {
        method: 'POST',
        mode: 'cors'
    })
    const response = await fetch(request)
    const joinedGame = await response.json()
    const joinedGameID = joinedGame["id"].toString()
    const boardSize = joinedGame["board"]

    gameid = joinedGameID

    width = boardSize["width"]
    height = boardSize["height"]

    const fleet = joinedGame['fleet']


    init('player', fleet)
    init('enemy')
}

function swapBoards() {

    document.getElementById('enemy').style.left = playerLeft
    document.getElementById('player').style.left = enemyLeft

    let temp1 = playerLeft
    playerLeft = enemyLeft
    enemyLeft = temp1

    boardAnimation()
}

function boardAnimation() {
    document.getElementById('player').classList.add(enemyAnimation)
    document.getElementById('player').classList.remove(playerAnimation)
    document.getElementById('enemy').classList.add(playerAnimation)
    document.getElementById('enemy').classList.remove(enemyAnimation)

    let temp = playerAnimation
    playerAnimation = enemyAnimation
    enemyAnimation = temp
}

function init(type, fleet) {
    let container = document.createElement("div")
    container.classList.add('container')
    container.classList.add(type)
    container.setAttribute('id', type)

    for (let i = 0; i < width * height; i++) {
        let cell = document.createElement("div")
        cell.classList.add('cell')
        cell.setAttribute('id', id.toString() + ':' + type)
        if (type === 'enemy') {
            cell.setAttribute('onclick', 'shoot(this)')
            cell.style.cursor = 'crosshair'
        }
        id++
        container.appendChild(cell)
    }
    id = 1
    body.appendChild(container)
    createShips(fleet, type).then(r => {
        if (type === 'enemy') {
            document.getElementById('enemy').style.left = enemyLeft
            document.getElementById('enemy').classList.add(enemyAnimation)
        } else {
            document.getElementById('player').style.left = playerLeft
            document.getElementById('player').classList.add(playerAnimation)
        }
    })
}

async function createShips(fleet, type) {
    if (type === 'player') {
        fleet.forEach(ship => ship["masts"].forEach(cellID => {
            document.getElementById(cellID + ":" + type).classList.add("ship")
        } ))
    }
}

const delay = millis => new Promise((resolve, reject) => {
    setTimeout(_ => resolve(), millis)
});
