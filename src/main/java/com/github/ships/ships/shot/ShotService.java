package com.github.ships.ships.shot;

import com.github.ships.ships.InvalidShotException;
import com.github.ships.ships.NotFoundException;
import com.github.ships.ships.NotUserTurnException;
import com.github.ships.ships.ShotResult;
import com.github.ships.ships.board.Board;
import com.github.ships.ships.board.BoardRepository;
import com.github.ships.ships.board.Cell;
import com.github.ships.ships.fleet.*;
import com.github.ships.ships.game.Game;
import com.github.ships.ships.game.GameRepository;
import com.github.ships.ships.users.User;
import com.github.ships.ships.users.UserService;
import com.github.ships.ships.websocket.Event;
import com.github.ships.ships.websocket.EventType;
import com.github.ships.ships.websocket.WebsocketService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

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
        if (!game.isUserTurn(player)) throw new NotUserTurnException();
        int cellIndex = Integer.parseInt(shotId);
        handleShotOnBoard(player, game, cellIndex);
        ShotResultDto shotResultDto = handleShotOnFleet(player, game, cellIndex);
        game.markTurn();
        gameRepository.save(game);
        sendNotification(player, game, shotResultDto.getShotResult(), cellIndex);
        return shotResultDto;
    }

    private void sendNotification(User player, Game game, ShotResult shotResult, int index) {
        switch (shotResult) {
            case MISS -> websocketService.notifyFrontEnd(
                    game.getRelativeOpponent(player).getEmail(),
                    new Event(EventType.ENEMY_MISS, "Enemy miss", index)
            );
            case SHIP_HIT, SHIP_SUNK -> websocketService.notifyFrontEnd(
                    game.getRelativeOpponent(player).getEmail(),
                    new Event(EventType.ENEMY_SHOT, "Enemy shot", index)
            );
            case FLEET_SUNK -> websocketService.notifyFrontEnd(
                    game.getRelativeOpponent(player).getEmail(),
                    new Event(EventType.ENEMY_WIN, "Enemy won", index)
            );
        }
    }

    private ShotResultDto handleShotOnFleet(User player, Game game, int cellIndex) {
        Fleet fleet = game.getUserFleet(player).orElseThrow(NotFoundException::new);
        ShotResultDto shotResultDto = fleet.placeShot(cellIndex);
        if (shotResultDto.getShotResult() != ShotResult.SHIP_HIT || shotResultDto.getShotResult() != ShotResult.MISS) {
            putRequiredHitsOnBoard(player, game, shotResultDto);
        }
        fleetRepository.save(fleet);
        return shotResultDto;
    }

    private void putRequiredHitsOnBoard(User player, Game game, ShotResultDto shotResultDto) {
        Board board = game.getUserBoard(player).orElseThrow();
        Collection<Integer> shotCells = shotResultDto.getCells();
        Map<Integer, Cell> cells = board.getCells();
        shotCells.forEach(i -> cells.put(i, Cell.HIT));
        boardRepository.save(board);
    }

    private void handleShotOnBoard(User player, Game game, int cellIndex) {
        Board board = game.getUserBoard(player).orElseThrow(NotFoundException::new);
        if (!board.containsIndex(cellIndex)) throw new NotFoundException();
        if (!board.isValidShot(cellIndex)) throw new InvalidShotException();
        board.getCells().put(cellIndex, Cell.HIT);
        boardRepository.save(board);
    }

}
