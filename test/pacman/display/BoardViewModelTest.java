package pacman.display;

import org.junit.Before;
import org.junit.Test;
import pacman.board.BoardItem;
import pacman.board.PacmanBoard;
import pacman.game.PacmanGame;
import pacman.ghost.Phase;
import pacman.hunter.Hungry;
import pacman.hunter.Hunter;
import pacman.util.Direction;
import pacman.util.Position;
import static org.junit.Assert.*;

public class BoardViewModelTest {
    private PacmanGame model;
    private BoardViewModel bVModel;
    private static int WIDTH = 10;
    private static int HEIGHT = 10;

    @Before
    public void setUp() {
        String title = "BoardViewModelTest";
        String author = "Jinyuan_Chen";
        Hunter hunter = new Hungry();
        hunter.setPosition(new Position(5,2));
        PacmanBoard board = new PacmanBoard(WIDTH, HEIGHT);
        model = new PacmanGame(title, author, hunter, board);
        bVModel = new BoardViewModel(model);
    }

    @Test
    public void getLives() {
        assertEquals(4,bVModel.getLives());

        model.setLives(10);
        assertEquals(10,bVModel.getLives());

        model.setLives(0);
        assertEquals(0,bVModel.getLives());

        model.setLives(-1);
        assertEquals(0,bVModel.getLives());
    }

    @Test
    public void getLevel() {
        assertEquals(0, bVModel.getLevel());

        model.setLives(2);
        assertEquals(2,bVModel.getLives());

        model.setLives(10);
        assertEquals(10,bVModel.getLives());

        model.setLives(-5);
        assertEquals(0,bVModel.getLives());
    }

    @Test
    public void getPacmanColour() {
        assertFalse(model.getHunter().isSpecialActive());
        assertEquals("#FFE709",bVModel.getPacmanColour());

        model.getHunter().activateSpecial(25);
        assertTrue(model.getHunter().isSpecialActive());
        assertEquals("#CDC3FF",bVModel.getPacmanColour());
    }

    @Test
    public void getPacmanMouthAngle() {
        model.getHunter().setDirection(Direction.RIGHT);
        assertEquals(30, bVModel.getPacmanMouthAngle());

        model.getHunter().setDirection(Direction.UP);
        assertEquals(120, bVModel.getPacmanMouthAngle());

        model.getHunter().setDirection(Direction.LEFT);
        assertEquals(210, bVModel.getPacmanMouthAngle());

        model.getHunter().setDirection(Direction.DOWN);
        assertEquals(300, bVModel.getPacmanMouthAngle());
    }

    @Test
    public void getPacmanPosition() {
        assertEquals(new Position(5,2), bVModel.getPacmanPosition());

        model.getHunter().setPosition(new Position(8, 9));
        assertEquals(new Position(8, 9), bVModel.getPacmanPosition());

        model.getHunter().setPosition(null);
        assertEquals(new Position(8, 9), bVModel.getPacmanPosition());
    }

    @Test
    public void getBoard() {
        assertEquals(WIDTH, bVModel.getBoard().getWidth());
        assertEquals(HEIGHT, bVModel.getBoard().getHeight());

        for (var i = 0; i < WIDTH; i++) {//check the Item of the board
            for (var j = 0; j < HEIGHT; j++) {
                if (j == 0 || j == (HEIGHT - 1) ||
                        i == 0 || i == (WIDTH - 1)) {
                    assertEquals(
                        BoardItem.WALL,
                        bVModel.getBoard().getEntry(new Position(i, j)));
                } else {
                    assertEquals(
                        BoardItem.NONE,
                        bVModel.getBoard().getEntry(new Position(i, j)));
                }
            }
        }
    }

    @Test
    public void getGhosts() {
        assertFalse(bVModel.getGhosts().isEmpty());

        assertEquals(4,  bVModel.getGhosts().size());

        for (var ghost : model.getGhosts()) {
            ghost.setPhase(Phase.FRIGHTENED, 20);
        } //check if phase is FRIGHTENED, the colour are same.
        assertEquals("#0000FF", bVModel.getGhosts().get(0).getValue());
        assertEquals("#0000FF", bVModel.getGhosts().get(1).getValue());
        assertEquals("#0000FF", bVModel.getGhosts().get(2).getValue());
        assertEquals("#0000FF", bVModel.getGhosts().get(3).getValue());
    }
}