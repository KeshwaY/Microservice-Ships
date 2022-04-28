package com.github.ships.ships.shot;

import com.github.ships.ships.FAKEFLEET.FleetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcedureAfterDefiningLegalShot implements ProcedureAfterDefinedShotLegality {

    @Autowired
    private FleetService fleetService;

    @Override
    public ShotResult perform(ShotPostDTO shotPostDTO) {
        ShotResult shotResult = new ShotResult();
        shotResult = defineShotLegality(shotResult);
        shotResult = defineCellIDofLegalShot(shotResult, shotPostDTO);
        shotResult = defineShotResultByFleet(shotResult, shotPostDTO);
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

    private ShotResult defineShotResultByFleet(ShotResult shotResult, ShotPostDTO shotPostDTO) {
        return fleetService.placeShot(shotResult, shotPostDTO);
    }
}
