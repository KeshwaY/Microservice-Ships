package com.github.ships.ships;

import com.github.ships.ships.shot.ShotPostDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BoardPlaceShotProcedure {

    private final BoardRepository repository;
    private final ShotPostDTO shotPostDTO;
    /**
     * value: is a board exists
     * key: a procedure in case a board exists and
     *      a procedure in a case a board doesn't exist
     */
    private final Map<Boolean, Supplier<Boolean>> subProcedures;

    public BoardPlaceShotProcedure(BoardRepository repository,
                                   ShotPostDTO shotPostDTO) {
        this.repository = repository;
        this.shotPostDTO = shotPostDTO;
        subProcedures = initSubProcedures();
    }

    private Map<Boolean, Supplier<Boolean>> initSubProcedures() {
        Map<Boolean, Supplier<Boolean>> subProcedures = new HashMap<>();
        subProcedures.put(true, this::subProcedureForExistingBoard);
        subProcedures.put(false, this::subProcedureForNonExistingBoard);
        return subProcedures;
    }

    /**
     * @return true if shot is legal and was placed
     *         false if not legal and was not placed
     */
    public boolean perform() {
        Supplier<Boolean> subProcedureToPerform = subProcedures.get(isSpecifiedBoardExists());
        return subProcedureToPerform.get();
    }

    private boolean isSpecifiedBoardExists() {
        List<Board> matchingBoards = getBoardsByGameAndPlayerIDs();
        return !matchingBoards.isEmpty();
    }

    private boolean subProcedureForExistingBoard() {
        Board matchingBoard = getBoardsByGameAndPlayerIDs().get(0);

        return true;
    }

    private List<Board> getBoardsByGameAndPlayerIDs() {
        String gameId = shotPostDTO.getGameId();
        int playerId = shotPostDTO.getPlayerId();
        return repository.getSpecifiedBoards(gameId, playerId);
    }

    private boolean subProcedureForNonExistingBoard() {
        System.out.println("NOT FOUND");
        return false;
    }
}
