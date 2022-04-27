package com.github.ships.ships;

import com.github.ships.ships.shot.ShotPostDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@Service
public class BoardService {

    private final BoardRepository repository;

    public BoardService(BoardRepository repository) {
        this.repository = repository;
    }

    public BoardGetDTO create(int width, int height, Game game) {
        Map<Integer, Cell> cells = initCells(width, height);
        Board playerBoard = new Board(game.getId(), 1, width, height, cells);
        Board enemyBoard = new Board(game.getId(), 2, width, height, cells);
        repository.saveAll(List.of(playerBoard, enemyBoard));
        return new BoardGetDTO(width, height);
    }

    private Map<Integer, Cell> initCells(int width, int height) {
        TreeMap<Integer, Cell> result = new TreeMap<>();
        IntStream.rangeClosed(1, width * height).forEach(i ->
                result.put(i , Cell.WATER));
        return result;
    }

    /**
     * @return true if shot is legal and was placed
     *         false if not legal and was not placed
     */
    public boolean placeShot(ShotPostDTO shotPostDTO) {
        Supplier<Boolean> forExisting = this::methodForExistingBoard;
        Supplier<Boolean> forNotExisting = this::methodForNonExistingBoard;
        Map<Boolean, Supplier<Boolean>> actions = new HashMap<>();
        actions.put(true, forExisting);
        actions.put(false, forNotExisting);

        boolean specifiedBoardExists = isSpecifiedBoardExists(shotPostDTO.getGameId(), shotPostDTO.getPlayerId());

        return actions.get(specifiedBoardExists).get();
    }

    private boolean isSpecifiedBoardExists(String gameId, int playerId) {
        List<Board> optionalBoard =
                    repository.getSpecifiedBoards(gameId, playerId);
        return !optionalBoard.isEmpty();
    }

    private boolean methodForExistingBoard() {
        System.out.println("EXISTS!");
        return true;
    }

    private boolean methodForNonExistingBoard() {
        System.out.println("NOT FOUND");
        return false;
    }

}
