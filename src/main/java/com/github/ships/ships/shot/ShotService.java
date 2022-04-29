package com.github.ships.ships.shot;

import com.github.ships.ships.InvalidShotException;
import com.github.ships.ships.NotFoundException;
import com.github.ships.ships.ShotResult;
import com.github.ships.ships.board.Board;
import com.github.ships.ships.board.BoardRepository;
import com.github.ships.ships.fleet.*;
import com.github.ships.ships.game.Game;
import com.github.ships.ships.game.GameRepository;
import com.github.ships.ships.users.User;
import com.github.ships.ships.users.UserService;
import com.github.ships.ships.websocket.Event;
import com.github.ships.ships.websocket.EventType;
import com.github.ships.ships.websocket.WebsocketService;
import org.springframework.stereotype.Service;

@Service
public class ShotService {

    private final UserService userService;
    private final GameRepository gameRepository;
    private final WebsocketService websocketService;
    private final BoardRepository boardRepository;
    private final FleetRepository fleetRepository;

    public ShotService(UserService userService, GameRepository gameRepository, WebsocketService websocketService, BoardRepository boardRepository, FleetRepository fleetRepository) {
        this.userService = userService;
        this.gameRepository = gameRepository;
        this.websocketService = websocketService;
        this.boardRepository = boardRepository;
        this.fleetRepository = fleetRepository;
    }

    public ShotResultDto shot(String email, String gameId, String shotId) {
        User player = userService.getRawUser(email);

        Game game = gameRepository.findById(gameId).orElseThrow(NotFoundException::new);
        if (!game.containsUser(player)) throw new NotFoundException();
        
        int cellIndex = Integer.parseInt(shotId);
        handleShotOnBoard(player, game, cellIndex);

        ShotResultDto shotResultDto = handleShotOnFleet(player, game, cellIndex);

        sendNotification(player, game, shotResultDto);

        return shotResultDto;
    }

    private void sendNotification(User player, Game game, ShotResultDto shotResultDto) {
        websocketService.notifyFrontEnd(
                game.getRelativeOpponent(player).getEmail(),
                new Event(EventType.ENEMY_SHOT, "Enemy shot")
        );
        if (shotResultDto.getShotResult() == ShotResult.FLEET_SUNK) {
            websocketService.notifyFrontEnd(
                    game.getRelativeOpponent(player).getEmail(),
                    new Event(EventType.ENEMY_WIN, "Enemy won")
            );
        }
    }

    private ShotResultDto handleShotOnFleet(User player, Game game, int cellIndex) {
        Fleet fleet = game.getUserFleet(player).orElseThrow(NotFoundException::new);
        ShotResultDto shotResultDto = fleet.placeShot(cellIndex);
        fleetRepository.save(fleet);
        return shotResultDto;
    }

    private void handleShotOnBoard(User player, Game game, int cellIndex) {
        Board board = game.getUserBoard(player).orElseThrow(NotFoundException::new);
        if (!board.containsIndex(cellIndex)) throw new NotFoundException();
        if (!board.isValidShot(cellIndex)) throw new InvalidShotException();
        boardRepository.save(board);
    }

}
