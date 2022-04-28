package com.github.ships.ships;

import com.github.ships.ships.shot.ShotPostDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

@Service
public class BoardService {

    private final BoardRepository repository;

    public BoardService(BoardRepository repository) {
        this.repository = repository;
    }

    public BoardGetDTO create(int width, int height, Game game) {
        Map<Integer, Cell> cells = initCells(width, height);
        Board playerBoard = new Board(game.getId(), 0, width, height, cells);
        Board enemyBoard = new Board(game.getId(), 1, width, height, cells);
        repository.saveAll(List.of(playerBoard, enemyBoard));
        return new BoardGetDTO(width, height);
    }

    private Map<Integer, Cell> initCells(int width, int height) {
        TreeMap<Integer, Cell> result = new TreeMap<>();
        IntStream.rangeClosed(1, width * height).forEach(i ->
                result.put(i , Cell.WATER_NOT_HIT_USUAL));
        return result;
    }

    /**
     * @return true if shot is legal and was placed
     *         false if not legal and was not placed
     */
    public boolean placeShot(ShotPostDTO shotPostDTO) {
        BoardPlaceShotProcedure boardPlaceShotProcedure =
                new BoardPlaceShotProcedure(repository, shotPostDTO);
        return boardPlaceShotProcedure.perform();
    }

    public BoardGetDTO get(String gameId, int playerId) {
        Board board = repository.getBoardByGameIdAndPlayerId(gameId, playerId).orElseThrow(NotFoundException::new);
        return new BoardGetDTO(board.getWidth(), board.getHeight());
    }

}
