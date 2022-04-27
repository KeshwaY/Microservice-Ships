package com.github.ships.ships;

import com.github.ships.ships.shot.ShotPostDTO;

import java.util.List;

public class BoardPlaceShotProcedure {

    private final BoardRepository repository;
    private final ShotPostDTO shotPostDTO;

    public BoardPlaceShotProcedure(BoardRepository repository,
                                   ShotPostDTO shotPostDTO) {
        this.repository = repository;
        this.shotPostDTO = shotPostDTO;
    }

    /**
     * @return true if shot is legal and was placed
     *         false if not legal and was not placed
     */
    public boolean perform() {
       return true;
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
