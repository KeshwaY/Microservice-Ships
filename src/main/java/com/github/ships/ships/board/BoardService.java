package com.github.ships.ships.board;

import com.github.ships.ships.InvalidBoardSizeException;
import com.github.ships.ships.game.Game;
import com.github.ships.ships.users.User;
import com.github.ships.ships.users.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tinylog.Logger;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class BoardService {

    private final BoardRepository repository;
    private final UserService userService;

    public BoardService(BoardRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public BoardGetDto createBoard(User user, Game game, BoardPostDto boardPostDto) {
        if(isValidSize(boardPostDto)){
            Board board = new Board(
                    user,
                    boardPostDto.getWidth(),
                    boardPostDto.getHeight(),
                    initCells(
                            boardPostDto.getWidth(),
                            boardPostDto.getHeight()
                    )
            );
            repository.save(board);
            game.getBoards().add(board);
            Logger.info(String.format("Board for %s is created.", user.getName()));
            return new BoardGetDto(boardPostDto.getWidth(), boardPostDto.getHeight());
        }
        throw new InvalidBoardSizeException();
    }

    private Map<Integer, Cell> initCells(int width, int height) {
        return IntStream.rangeClosed(1, width * height).boxed()
                .collect(Collectors.toMap((i) -> i, (i) -> Cell.WATER));
    }

    private boolean isValidSize(BoardPostDto boardPostDto){
        return (boardPostDto.getHeight() >= 10 && boardPostDto.getHeight() <= 15) &&
                (boardPostDto.getWidth() >= 10 && boardPostDto.getWidth() <= 15);
    }

}
