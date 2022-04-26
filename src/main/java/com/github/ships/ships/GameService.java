package com.github.ships.ships;

import org.springframework.stereotype.Service;

@Service
class GameService {

    private final GameRepository repository;
    private final GameMapper mapper;

    GameService(final GameRepository repository, final GameMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    GameGetDTO create() {
        Game game = new Game();
        return mapper.gameToGameGetDto(repository.save(game));
    }

    GameGetDTO get(String id) {
        return mapper.gameToGameGetDto(
                repository.findById(id).orElseThrow(NotFoundException::new)
        );
    }
}
