package com.github.ships.ships;

import com.github.ships.ships.shot.ShotPostDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        subProcedures.put(true, this::subProcedureForLegalShot);
        subProcedures.put(false, this::subProcedureForIllegalShot);
        return subProcedures;
    }

    /**
     * @return true if shot is legal and was placed
     *         false if not legal and was not placed
     */
    public boolean perform() {
        Supplier<Boolean> subProcedureToPerform = subProcedures.get(isLegalShot());
        return subProcedureToPerform.get();
    }

    private boolean isLegalShot() {
        return isSpecifiedBoardExists()
               && isSpecifiedCellExists()
               && canSpecifiedCellBeHit();
    }

    private boolean isSpecifiedBoardExists() {
        List<Board> matchingBoards = getBoardsByGameAndPlayerIDs();
        return !matchingBoards.isEmpty();
    }

    private boolean isSpecifiedCellExists() {
        Optional<Board> optionalBoard = Optional.ofNullable(getBoardsByGameAndPlayerIDs().get(0));
        if (optionalBoard.isPresent()) {
            Board matchingBoard = optionalBoard.get();
            Map<Integer, Cell> cells = matchingBoard.getCells();
            int specifiedCellID = shotPostDTO.getCellIdToPlaceShot();
            return cells.containsKey(specifiedCellID);
        }
        return false;
    }

    private boolean canSpecifiedCellBeHit() {
        Optional<Board> optionalBoard = Optional.ofNullable(getBoardsByGameAndPlayerIDs().get(0));
        if (optionalBoard.isPresent()) {
            Board matchingBoard = optionalBoard.get();
            Map<Integer, Cell> cells = matchingBoard.getCells();
            int specifiedCellID = shotPostDTO.getCellIdToPlaceShot();
            Cell cellToBeHit = cells.get(specifiedCellID);
            return cellToBeHit.canBeHit();
        }
        return false;
    }

    private boolean subProcedureForLegalShot() {
        Board matchingBoard = getBoardsByGameAndPlayerIDs().get(0);
        Map<Integer, Cell> cellsBeforeHit = matchingBoard.getCells();
        Map<Integer, Cell> cellsAfterHit = putLegalShot(cellsBeforeHit);
        matchingBoard.setCells(cellsAfterHit);
        repository.save(matchingBoard);
        return true;
    }

    private Map<Integer, Cell> putLegalShot(Map<Integer, Cell> cells) {
        int specifiedCellID = shotPostDTO.getCellIdToPlaceShot();
        Cell cellBeforeHit = cells.get(specifiedCellID);
        Cell cellAfterHit = cellBeforeHit.cellAfterHit();
        cells.put(specifiedCellID, cellAfterHit);
        return cells;
    }

    private List<Board> getBoardsByGameAndPlayerIDs() {
        String gameId = shotPostDTO.getGameId();
        int playerId = shotPostDTO.getPlayerId();
        return repository.getSpecifiedBoards(gameId, playerId);
    }

    private boolean subProcedureForIllegalShot() {
        return false;
    }
}
