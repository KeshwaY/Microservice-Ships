package com.github.ships.ships.shot;

import com.github.ships.ships.fleet.FleetService;

public class ProcedureAfterDefiningLegalShot implements ProcedureAfterDefinedShotLegality {

    @Override
    public ShotResult perform(ShotPostDTO shotPostDTO, FleetService fleetService) {
        ShotResult shotResult = new ShotResult();
        shotResult = defineShotLegality(shotResult);
        shotResult = defineCellIDofLegalShot(shotResult, shotPostDTO);
        shotResult = defineShotResultByFleet(shotResult, shotPostDTO, fleetService);
        return shotResult;
    }

    private ShotResult defineShotLegality(ShotResult shotResult) {
        shotResult.setShotLegality(ShotLegality.LEGAL);
        return shotResult;
    }

    private ShotResult defineCellIDofLegalShot(ShotResult shotResult, ShotPostDTO shotPostDTO) {
        int cellIDofLegalShot = shotPostDTO.getCellIdToPlaceShot();
        shotResult.setCellIDofLegalShot(cellIDofLegalShot);
        return shotResult;
    }

    private ShotResult defineShotResultByFleet(ShotResult shotResult,
                                               ShotPostDTO shotPostDTO,
                                               FleetService fleetService) {
        return fleetService.placeShot(shotResult, shotPostDTO);
    }
}
