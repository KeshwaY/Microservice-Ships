package com.github.ships.ships.fleet;

import com.github.ships.ships.board.Board;
import com.github.ships.ships.board.BoardRepository;
import com.github.ships.ships.board.Cell;
import com.github.ships.ships.game.Game;
import com.github.ships.ships.users.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FleetService {

    private final FleetRepository repository;
    private final BoardRepository boardRepository;

    public FleetService(FleetRepository repository, BoardRepository boardRepository) {
        this.repository = repository;
        this.boardRepository = boardRepository;
    }

    public FleetGetDto create(User user, Game game, Board board) {
        Fleet fleet = new RandomlyPlacedShipsGenerator(board.getWidth(), board.getHeight(), user).
                generateRandomlyPlacedFleet();
        repository.save(fleet);
        game.getFleets().add(fleet);

        Map<Integer, Cell> cells = board.getCells();
        List<Integer> occupiedCells = fleet.retrieveOccupiedCells();
        occupiedCells.forEach(cellID -> cells.put(cellID, Cell.OCCUPIED));
        board.setCells(cells);
        boardRepository.save(board);

        return new FleetGetDto(fleet.getShips().stream()
                .map(s -> new ShipGetDto(s.getType(), s.getMasts().keySet()))
                .toList()
        );
    }

}
