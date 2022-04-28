package com.github.ships.ships.shot;

import com.github.ships.ships.BoardService;
import org.springframework.stereotype.Service;

@Service
public class ShotService {

    private final ShotMapper mapper;
    private final BoardService boardService;

    public ShotService(ShotMapper mapper, BoardService boardService) {
        this.mapper = mapper;
        this.boardService = boardService;
    }

    public ShotResultDTO placeShot(ShotPostDTO shotPostDTO) {
        ShotLegality shotLegality = placeShotOnBoard(shotPostDTO);
        ShotResult shotResult = shotLegality.performAfterwardsProcedure(shotPostDTO, boardService);
        //TODO: START FROM HERE!
        //TODO: [NOW!] PROCEDURE FOR A LEGAL SHOT...
        return mapper.shotResultToShotResultDTO(shotResult);
    }

    private ShotLegality placeShotOnBoard(ShotPostDTO shotPostDTO) {
        boolean isLegalShot = boardService.placeShot(shotPostDTO);
        return ShotLegality.obtainShotLegality(isLegalShot);
    }
}
