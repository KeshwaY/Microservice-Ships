package com.github.ships.ships.fleet;

import com.github.ships.ships.board.Board;
import com.github.ships.ships.board.BoardRepository;
import com.github.ships.ships.board.Cell;
import com.github.ships.ships.game.Game;
import com.github.ships.ships.users.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FleetService {

    private final FleetRepository repository;
    private final BoardRepository boardRepository;

    public FleetService(FleetRepository repository, BoardRepository boardRepository) {
        this.repository = repository;
        this.boardRepository = boardRepository;
    }

    public FleetGetDto create(User user, Game game, Board board) {
        Ship firstShip = new Ship(
                ShipType.DESTROYER,
                Map.of(14, MastState.ALIVE),
                List.of(3, 4, 5, 13, 15, 23, 24, 25)
        );
        Ship secondShip = new Ship(
                ShipType.DESTROYER,
                Map.of(17, MastState.ALIVE),
                List.of(6, 7, 8, 16, 18, 26, 27, 28)
        );
        Map<Integer, Cell> cells = board.getCells();
        cells.put(3, Cell.OCCUPIED);
        cells.put(4, Cell.OCCUPIED);
        cells.put(5, Cell.OCCUPIED);
        cells.put(13, Cell.OCCUPIED);
        cells.put(14, Cell.OCCUPIED);
        cells.put(15, Cell.OCCUPIED);
        cells.put(23, Cell.OCCUPIED);
        cells.put(24, Cell.OCCUPIED);
        cells.put(25, Cell.OCCUPIED);

        cells.put(6, Cell.OCCUPIED);
        cells.put(7, Cell.OCCUPIED);
        cells.put(8, Cell.OCCUPIED);
        cells.put(16, Cell.OCCUPIED);
        cells.put(17, Cell.OCCUPIED);
        cells.put(18, Cell.OCCUPIED);
        cells.put(26, Cell.OCCUPIED);
        cells.put(27, Cell.OCCUPIED);
        cells.put(28, Cell.OCCUPIED);
        board.setCells(cells);
        boardRepository.save(board);

        Fleet fleet = repository.save(new Fleet(List.of(firstShip, secondShip), user));
        game.getFleets().add(fleet);

        return new FleetGetDto(fleet.getShips().stream()
                .map(s -> new ShipGetDto(s.getType(), s.getMasts().keySet()))
                .toList()
        );
    }

}
