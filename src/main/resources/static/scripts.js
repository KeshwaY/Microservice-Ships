let stompClient = null;

$(document).ready(function() {
    console.log("Index page is ready");
    connect();
});

function connect() {
    var socket = new SockJS('/ships-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/events', function (message) {
            console.log(JSON.parse(message.body).content);
        });
    });
}