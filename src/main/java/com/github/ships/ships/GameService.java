package com.github.ships.ships;

import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
class GameService {

    private final GameRepository repository;
    private final BoardService boardService;
    private final GameMapper mapper;


    GameService(final GameRepository repository, final BoardService boardService, final GameMapper mapper) {
        this.repository = repository;
        this.boardService = boardService;
        this.mapper = mapper;
    }

    GameCreatedDTO create(GamePostDTO gamePostDTO) {
        Game game = new Game();
        game = repository.save(game);
        game.setBoards(new HashSet<>());
        BoardGetDTO boardGetDTO = boardService.create(gamePostDTO.getWidth(), gamePostDTO.getHeight(), game);
        return new GameCreatedDTO(game.getId());
    }

}
