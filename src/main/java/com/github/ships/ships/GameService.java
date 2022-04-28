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
        createAndSaveFleets(gamePostDTO, game);
        createAndSaveBoards(gamePostDTO, game);
        BoardGetDTO boardGetDTO = produceBoardGetDTO(gamePostDTO);
        return mapper.gameToGameCreatedDTO(game, boardGetDTO);
    }

    private BoardGetDTO produceBoardGetDTO(GamePostDTO gamePostDTO) {
        int boardWidth = gamePostDTO.getWidth();
        int boardHeight = gamePostDTO.getHeight();
        return new BoardGetDTO(boardWidth, boardHeight);
    }

    private void createAndSaveFleets(GamePostDTO gamePostDTO, Game game) {
        int boardWidth = gamePostDTO.getWidth();
        int boardHeight = gamePostDTO.getHeight();
        fleetService.createAndSaveFleet(boardWidth, boardHeight, game, game.getFirstPlayerID());
        fleetService.createAndSaveFleet(boardWidth, boardHeight, game, game.getSecondPlayerID());
    }

    private void createAndSaveBoards(GamePostDTO gamePostDTO, Game game) {
        int boardWidth = gamePostDTO.getWidth();
        int boardHeight = gamePostDTO.getHeight();
        boardService.createAndSaveBoard(boardWidth, boardHeight, game, game.getFirstPlayerID());
        boardService.createAndSaveBoard(boardWidth, boardHeight, game, game.getSecondPlayerID());
    }
}
