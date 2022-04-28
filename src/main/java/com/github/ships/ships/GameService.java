package com.github.ships.ships;

import com.github.ships.ships.users.User;
import com.github.ships.ships.users.UserRepository;
import com.github.ships.ships.websocket.Event;
import com.github.ships.ships.websocket.EventType;
import com.github.ships.ships.websocket.WebsocketService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
class GameService {

    private final GameRepository repository;
    private final BoardService boardService;
    private final UserRepository userRepository;
    private final GameMapper mapper;
    private final WebsocketService websocketService;


    GameService(final GameRepository repository, final BoardService boardService, final UserRepository userRepository, final GameMapper mapper, final WebsocketService websocketService) {
        this.repository = repository;
        this.boardService = boardService;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.websocketService = websocketService;
    }

    GameCreatedDTO create(String userEmail, GamePostDTO gamePostDTO) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(NotFoundException::new);
        Game game = new Game();
        game.setPlayers(Set.of(user));
        game = repository.save(game);
        BoardGetDTO boardGetDTO = boardService.create(gamePostDTO.getWidth(), gamePostDTO.getHeight(), game);
        websocketService.notifyFrontEnd(userEmail, new Event(EventType.GAME_CREATE, "TEST"));
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
