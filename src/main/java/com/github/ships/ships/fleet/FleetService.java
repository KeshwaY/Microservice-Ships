package com.github.ships.ships.fleet;

import com.github.ships.ships.board.Board;
import com.github.ships.ships.board.BoardRepository;
import com.github.ships.ships.board.Cell;
import com.github.ships.ships.game.Game;
import com.github.ships.ships.users.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        int boardWidth = board.getWidth();
        int boardHeight = board.getHeight();
        Fleet fleet = generateFleet(boardWidth, boardHeight, user);
        repository.save(fleet);
        game.getFleets().add(fleet);

        Map<Integer, Cell> cells = board.getCells();
        List<Integer> occupiedCells = fleet.retrieveOccupiedCells();
        occupiedCells.forEach(cellID -> {
            cells.put(cellID, Cell.OCCUPIED);
        });
        board.setCells(cells);
        boardRepository.save(board);

        return new FleetGetDto(fleet.getShips().stream()
                .map(s -> new ShipGetDto(s.getType(), s.getMasts().keySet()))
                .toList()
        );
    }

    private Fleet generateFleet(int boardWidth, int boardHeight, User user) {
        List<ShipTemplate> templates = new ArrayList<>();
        templates.add(new ShipTemplate(List.of(22, 32, 42, 52), boardWidth, boardHeight));
        templates.add(new ShipTemplate(List.of(24, 25, 26), boardWidth, boardHeight));
        templates.add(new ShipTemplate(List.of(55, 56, 57), boardWidth, boardHeight));
        templates.add(new ShipTemplate(List.of(10, 20), boardWidth, boardHeight));
        templates.add(new ShipTemplate(List.of(84, 85), boardWidth, boardHeight));
        templates.add(new ShipTemplate(List.of(71, 81), boardWidth, boardHeight));
        templates.add(new ShipTemplate(List.of(1), boardWidth, boardHeight));
        templates.add(new ShipTemplate(List.of(4), boardWidth, boardHeight));
        templates.add(new ShipTemplate(List.of(39), boardWidth, boardHeight));
        templates.add(new ShipTemplate(List.of(89), boardWidth, boardHeight));
        Collection<Ship> ships = new ArrayList<>();
        templates.forEach(shipTemplate -> {
            ShipType shipType = ShipType.getBySize(shipTemplate.getMasts().size());
            Map<Integer, MastState> masts = shipTemplate.getMasts();
            Collection<Integer> extraOccupied = shipTemplate.getAdjacentCells();
            ships.add(new Ship(shipType, masts, extraOccupied));
        });
        return new Fleet(ships, user);
    }
}
