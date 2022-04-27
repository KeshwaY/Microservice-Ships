package com.github.ships.ships;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

@Service
public class BoardService {

    private final BoardRepository repository;

    public BoardService(final BoardRepository repository) {
        this.repository = repository;
    }

    BoardGetDTO create(int width, int height, Game game) {
        Map<Integer, Cell> cells = initCells(width, height);
        Board playerBoard = new Board(game.getId(), 1, width, height, cells);
        Board enemyBoard = new Board(game.getId(), 2, width, height, cells);
        repository.saveAll(List.of(playerBoard, enemyBoard));
        return new BoardGetDTO(width, height);
    }

    private Map<Integer, Cell> initCells(int width, int height) {
        final TreeMap<Integer, Cell> result = new TreeMap<>();
        IntStream.rangeClosed(1, width * height).forEach(i ->
                result.put(i , Cell.WATER));
        return result;
    }

}
