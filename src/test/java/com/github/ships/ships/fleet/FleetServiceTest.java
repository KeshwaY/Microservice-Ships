package com.github.ships.ships.fleet;

import com.github.ships.ships.board.Board;
import com.github.ships.ships.board.BoardRepository;
import com.github.ships.ships.board.Cell;
import com.github.ships.ships.game.Game;
import com.github.ships.ships.users.User;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.testng.Assert.*;
import static org.mockito.BDDMockito.*;

@Listeners({MockitoTestNGListener.class})
public class FleetServiceTest {

    @Mock
    FleetRepository fleetRepository;
    @Mock
    BoardRepository boardRepository;
    private FleetService service;
    private User user;
    private Game game;
    private Board board;

    @BeforeMethod
    public void setUp() {
        this.service = new FleetService(fleetRepository, boardRepository);
        this.user = new User("test@email.com", "testUser");
        this.game = new Game(user, new ArrayList<Board>(), new ArrayList<Fleet>(), true);
        this.board = new Board(user, 10, 10, new HashMap<Integer, Cell>());
        given(fleetRepository.save(any())).willReturn(null);
        given(boardRepository.save(any())).willReturn(null);
    }

    @Test
    public void shouldCreate10ShipsFleet() {
        //given
        //when
        FleetGetDto actual = service.create(user, game, board);
        System.out.println(actual);
        //then
        assertEquals(actual.getShips().size(), 10);

    }

    @Test
    public void shouldCreateFleetWith4Destroyers() {
        //given
        //when
        FleetGetDto actual = service.create(user, game, board);
        //then
        assertEquals(actual.getShips().stream()
                .filter(shipGetDto -> shipGetDto.getType() == ShipType.DESTROYER)
                .count(), 4);
    }

    @Test
    public void shouldCreateFleetWith3Submarines() {
        //given
        FleetGetDto actual = service.create(user, game, board);
        //when
        //then
        assertEquals(actual.getShips().stream()
                .filter(shipGetDto -> shipGetDto.getType() == ShipType.SUBMARINE)
                .count(), 3);
    }

    @Test
    public void shouldCreateFleetWith2Cruiser() {
        //given
        FleetGetDto actual = service.create(user, game, board);
        //when
        //then
        assertEquals(actual.getShips().stream()
                .filter(shipGetDto -> shipGetDto.getType() == ShipType.CRUISER)
                .count(), 2);
    }

    @Test
    public void shouldCreateFleetWith1Battleship() {
        //given
        FleetGetDto actual = service.create(user, game, board);
        //when
        //then
        assertEquals(actual.getShips().stream()
                .filter(shipGetDto -> shipGetDto.getType() == ShipType.BATTLESHIP)
                .count(), 1);
    }
}
