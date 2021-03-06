package com.github.ships.ships.game;

import com.github.ships.ships.NotFoundException;
import com.github.ships.ships.ResourceAlreadyExistsException;
import com.github.ships.ships.board.Board;
import com.github.ships.ships.board.BoardGetDto;
import com.github.ships.ships.board.BoardPostDto;
import com.github.ships.ships.board.BoardService;
import com.github.ships.ships.fleet.FleetGetDto;
import com.github.ships.ships.fleet.FleetService;
import com.github.ships.ships.users.User;
import com.github.ships.ships.users.UserService;
import com.github.ships.ships.websocket.Event;
import com.github.ships.ships.websocket.EventType;
import com.github.ships.ships.websocket.WebsocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tinylog.Logger;

import javax.sql.rowset.Joinable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
class GameService {
    private final GameRepository repository;
    private final BoardService boardService;
    private final UserService userService;
    private final FleetService fleetService;
    private final WebsocketService websocketService;

    GameService(
            GameRepository repository,
            BoardService boardService,
            UserService userService,
            FleetService fleetService, WebsocketService websocketService
    ) {
        this.repository = repository;
        this.boardService = boardService;
        this.userService = userService;
        this.fleetService = fleetService;
        this.websocketService = websocketService;
    }

    GameDto create(String ownerEmail, GamePostDto gamePostDto) {
        User owner = userService.getRawUser(ownerEmail);
        Game game = new Game(owner, new ArrayList<>(), new ArrayList<>(), true);
        BoardGetDto board = boardService.createBoard(owner, game, gamePostDto.getBoard());
        FleetGetDto fleet = fleetService.create(owner, game, ((List<Board>) game.getBoards()).get(0));
        repository.save(game);
        Logger.info(String.format("Game %s is created", game.getId()));
        return new GameDto(game.getId(), board, fleet.getShips());
    }

    @Transactional
    public GameDto join(String opponentEmail, String gameId) {
        Game game = repository.findById(gameId).orElseThrow(NotFoundException::new);
        if (game.getOpponent() != null || game.getOwner().getEmail().equals(opponentEmail)) throw new ResourceAlreadyExistsException();
        User opponent = userService.getRawUser(opponentEmail);
        game.setOpponent(opponent);
        Board ownerBoard = ((List<Board>) game.getBoards()).get(0);
        BoardGetDto board = boardService.createBoard(
                opponent,
                game,
                new BoardPostDto(
                        ownerBoard.getWidth(),
                        ownerBoard.getHeight()
                )
        );
        FleetGetDto fleet = fleetService.create(opponent, game, ((List<Board>) game.getBoards()).get(1));
        repository.save(game);
        websocketService.notifyFrontEnd(
                ownerBoard.getPlayer().getEmail(),
                new Event(EventType.ENEMY_JOIN, "Enemy joined")
        );
        Logger.info(String.format("Opponent joined %s game.", game.getId()));
        return new GameDto(game.getId(), board, fleet.getShips());
    }
}
