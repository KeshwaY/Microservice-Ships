package com.github.ships.ships;

import org.springframework.stereotype.Service;

/**
 * @author Anna Ovcharenko
 */
@Service
public class ShotService {

    private final ShotMapper mapper;
    private final BoardService boardService;

    public ShotService(ShotMapper mapper, BoardService boardService) {
        this.mapper = mapper;
        this.boardService = boardService;
    }

    public ShotResultDTO placeShot(ShotPostDTO shotPostDTO) {
        ShotResult shotResult = new ShotResult();
        if (boardService.placeShot(shotPostDTO.getGameId(), shotPostDTO.getCellId())) {
            shotResult.setStatus(ShotStatus.HITWATER);
        } else {
            shotResult.setStatus(ShotStatus.ILLEGAL);
        }
        return mapper.shotResultToShotResultDTO(shotResult);
    }
}
