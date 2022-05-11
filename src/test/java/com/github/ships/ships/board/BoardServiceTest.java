package com.github.ships.ships.board;

import com.github.ships.ships.InvalidBoardSizeException;
import com.github.ships.ships.fleet.Fleet;
import com.github.ships.ships.game.Game;
import com.github.ships.ships.users.User;
import com.github.ships.ships.users.UserService;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.mockito.BDDMockito.*;

import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * @author Anna Ovcharenko
 */
@Listeners({MockitoTestNGListener.class})
public class BoardServiceTest {
    @Mock
    BoardRepository repository;
    @Mock
    UserService userService;

    @DataProvider
    public static Object[][] validBoardSizes() {
        return new Object[][]{
                {10, 10}
                , {15, 15}
                , {12, 15}
        };
    }

    @Test(dataProvider = "validBoardSizes")
    public void shouldCreateProperBoard(int width, int height) {
        //give
        BoardService service = new BoardService(repository, userService);
        User user = new User("test@email.com", "testUser");
        BoardPostDto boardPostDto = new BoardPostDto(width, height);
        Game game = new Game(user, new ArrayList<Board>(), new ArrayList<Fleet>(), true);
        BoardGetDto expected = new BoardGetDto(width, height);
        given(repository.save(any())).willReturn(null);
        //when
        BoardGetDto actual = service.createBoard(user, game, boardPostDto);
        //then
        assertEquals(actual, expected);
    }

    @DataProvider
    public static Object[][] invalidBoardSizes() {
        return new Object[][]{
                {9, 9}
                , {16, 16}
                , {0, 0}
                , {-5, -5}
                , {1000, 1000}
        };
    }

    @Test(dataProvider = "invalidBoardSizes", expectedExceptions = InvalidBoardSizeException.class)
    public void shouldNotBeAbleToCreateBoardWithInvalidSize(int width, int height) {
        //given
        BoardService service = new BoardService(repository, userService);
        User user = new User("test@email.com", "testUser");
        BoardPostDto boardPostDto = new BoardPostDto(width, height);
        Game game = new Game(user, new ArrayList<Board>(), new ArrayList<Fleet>(), true);
        BoardGetDto expected = new BoardGetDto(width, height);
        given(repository.save(any())).willReturn(null);
        //when
        BoardGetDto actual = service.createBoard(user, game, boardPostDto);
        //then
    }

}