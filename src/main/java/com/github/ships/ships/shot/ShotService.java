package com.github.ships.ships.shot;

import com.github.ships.ships.BoardService;
import com.github.ships.ships.fleet.FleetService;
import org.springframework.stereotype.Service;

@Service
public class ShotService {

    private final ShotMapper mapper;
    private final BoardService boardService;
    private final FleetService fleetService;

    public ShotService(ShotMapper mapper,
                       BoardService boardService,
                       FleetService fleetService) {
        this.mapper = mapper;
        this.boardService = boardService;
        this.fleetService = fleetService;
    }

    public ShotResultDTO placeShot(ShotPostDTO shotPostDTO) {
        ShotLegality shotLegality = placeShotOnBoard(shotPostDTO);
        ShotResult shotResult = shotLegality.performAfterwardsProcedure(shotPostDTO, fleetService);
        return mapper.shotResultToShotResultDTO(shotResult);
    }

    private ShotLegality placeShotOnBoard(ShotPostDTO shotPostDTO) {
        boolean isLegalShot = boardService.placeShot(shotPostDTO);
        return ShotLegality.obtainShotLegality(isLegalShot);
    }
}
