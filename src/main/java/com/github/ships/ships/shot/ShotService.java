package com.github.ships.ships.shot;

import com.github.ships.ships.NotFoundException;
import com.github.ships.ships.board.Board;
import com.github.ships.ships.board.BoardRepository;
import com.github.ships.ships.board.Cell;
import com.github.ships.ships.fleet.Fleet;
import com.github.ships.ships.fleet.FleetRepository;
import com.github.ships.ships.fleet.MastState;
import com.github.ships.ships.fleet.Ship;
import com.github.ships.ships.game.Game;
import com.github.ships.ships.game.GameRepository;
import com.github.ships.ships.users.User;
import com.github.ships.ships.users.UserService;
import com.github.ships.ships.websocket.EventType;
import com.github.ships.ships.websocket.WebsocketService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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

    public ShotDto shot(String email, String gameId, String shotId) {
        User player = userService.getRawUser(email);
        Game game = gameRepository.findById(gameId).orElseThrow(NotFoundException::new);
        if (!List.of(game.getOwner().getEmail(), game.getOpponent().getEmail()).contains(email)) throw new NotFoundException();

        Board board = game.getBoards().stream().filter(b -> !b.getPlayer().getEmail().equals(email)).findFirst()
                .orElseThrow(NotFoundException::new);
        int cellId = Integer.parseInt(shotId);
        if(!board.getCells().containsKey(cellId) || board.getCells().get(cellId) == Cell.HIT) throw new NotFoundException();
        board.getCells().put(cellId, Cell.HIT);
        boardRepository.save(board);

        ShotDto shotDto = null;
        Fleet fleet = game.getFleets().stream().filter(f -> !f.getPlayer().getEmail().equals(email)).findFirst()
                .orElseThrow(NotFoundException::new);
        for (Ship ship : fleet.getShips()) {
            if(ship.getMasts().containsKey(cellId)) {
                ship.getMasts().put(cellId, MastState.HIT);
                if (!ship.isAlive()) {
                    Collection<CellHitDto> hit = new HashSet<>();
                    ship.getMasts().forEach((k, v) -> hit.add(new CellHitDto(k, true)));
                    ship.getExtraOccupied().forEach(o -> hit.add(new CellHitDto(o, false)));
                    shotDto = new ShotDto("Ship sunk", hit);
                    break;
                }
                shotDto = new ShotDto("Ship hit", List.of(new CellHitDto(cellId, true)));
                break;
            }
        }

        fleetRepository.save(fleet);

        if (shotDto == null) {
            shotDto = new ShotDto("Miss", List.of(new CellHitDto(cellId, false)));
        }

        websocketService.notifyFrontEnd(game.getRelativeOpponent(player).getEmail(), EventType.ENEMY_SHOT);

        if (fleet.getShips().stream().map(Ship::isAlive).filter(b -> b).findAny().isEmpty()) {
            websocketService.notifyFrontEnd(game.getRelativeOpponent(player).getEmail(), EventType.ENEMY_WIN);
            shotDto = new ShotDto("Fleet sunk", new HashSet<>());
        }

        return shotDto;
    }

}
