package com.github.ships.ships.shot;

import com.github.ships.ships.BoardService;

public class ProcedureAfterDefiningLegalShot implements ProcedureAfterDefinedShotLegality {

    @Override
    public ShotResult perform(ShotPostDTO shotPostDTO, BoardService boardService) {
        ShotResult shotResult = new ShotResult();
        shotResult.setShotLegality(ShotLegality.LEGAL);
        return shotResult;
    }
}
