package com.github.ships.ships.shot;

import com.github.ships.ships.BoardService;

public interface ProcedureAfterDefinedShotLegality {

    ShotResult perform(ShotPostDTO shotPostDTO, BoardService boardService);
}
