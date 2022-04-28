package com.github.ships.ships;

import com.github.ships.ships.users.User;
import com.github.ships.ships.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
class GameService {

    private final GameRepository repository;
    private final BoardService boardService;
    private final UserRepository userRepository;
    private final GameMapper mapper;


    GameService(final GameRepository repository, final BoardService boardService, final UserRepository userRepository, final GameMapper mapper) {
        this.repository = repository;
        this.boardService = boardService;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    GameCreatedDTO create(String userEmail, GamePostDTO gamePostDTO) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(NotFoundException::new);
        Game game = new Game();
        game.setPlayers(Set.of(user));
        game = repository.save(game);
        BoardGetDTO boardGetDTO = boardService.create(gamePostDTO.getWidth(), gamePostDTO.getHeight(), game);
        return mapper.gameToGameCreatedDTO(game, boardGetDTO);
    }

    GameCreatedDTO join(String userEmail, String id) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(NotFoundException::new);
        Game game = repository.findById(id).orElseThrow(NotFoundException::new);
        game.getPlayers().add(user);
        repository.save(game);
        BoardGetDTO boardGetDTO = boardService.get(game.getId(), 1);
        return mapper.gameToGameCreatedDTO(game, boardGetDTO);
    }


}
