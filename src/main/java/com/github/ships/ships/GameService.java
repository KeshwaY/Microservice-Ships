package com.github.ships.ships;

import com.github.ships.ships.fleet.FleetService;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
class GameService {

    private final GameRepository repository;
    private final BoardService boardService;
    private final FleetService fleetService;
    private final GameMapper mapper;


    public GameService(GameRepository repository, GameMapper mapper,
                       BoardService boardService, FleetService fleetService) {
        this.repository = repository;
        this.mapper = mapper;
        this.boardService = boardService;
        this.fleetService = fleetService;
    }

    public GameCreatedDTO createGame(GamePostDTO gamePostDTO) {
        Game game = new Game();
        game.setBoards(new HashSet<>());
        game = repository.save(game);
        BoardGetDTO boardGetDTO = createAndSaveBoards(gamePostDTO, game);
        createAndSaveFleets(gamePostDTO, game);
        return mapper.gameToGameCreatedDTO(game, boardGetDTO);
    }

    private void createAndSaveFleets(GamePostDTO gamePostDTO, Game game) {
        int width = gamePostDTO.getWidth();
        int height = gamePostDTO.getHeight();
        fleetService.createAndSaveFleets(width, height, game);
    }

    private BoardGetDTO createAndSaveBoards(GamePostDTO gamePostDTO, Game game) {
        int width = gamePostDTO.getWidth();
        int height = gamePostDTO.getHeight();
        return boardService.createAndSaveBoards(width, height, game);
    }
}
