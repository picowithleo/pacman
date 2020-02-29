package pacman.display;

import org.junit.Before;
import org.junit.Test;
import pacman.board.PacmanBoard;
import pacman.game.PacmanGame;
import pacman.hunter.Hungry;
import pacman.hunter.Hunter;
import static org.junit.Assert.*;

public class ScoreViewModelTest {
    private PacmanGame model;
    private ScoreViewModel sVModel;

    @Before
    public void setUp() {
        String title = "ScoreViewModelTest";
        String author = "Jinyuan_Chen";
        Hunter hunter = new Hungry();
        PacmanBoard board = new PacmanBoard(10, 10);
        model = new PacmanGame(title, author, hunter, board);
        sVModel = new ScoreViewModel(model);
        sVModel.getCurrentScoreProperty().set(
                "Score: " + model.getScores().getScore());
    }

    @Test
    public void update() {
        assertNotNull(sVModel.getCurrentScoreProperty().getValue());

        assertEquals("Score: " + model.getScores().getScore(),
        sVModel.getCurrentScoreProperty().get());

        assertEquals("Sorted by Name",
                sVModel.getSortedBy().getValue());
        assertEquals(model.getScores().getEntriesByName(),
                sVModel.getScores());

        sVModel.getSortedBy().setValue("Sorted by Score");
        assertEquals("Sorted by Score",
                sVModel.getSortedBy().getValue());
        assertEquals(model.getScores().getEntriesByScore(),
                sVModel.getScores());
    }

    @Test
    public void switchScoreOrder() {
        assertEquals("Sorted by Name",
                sVModel.getSortedBy().getValue());
        sVModel.switchScoreOrder();
        sVModel.update();

        assertEquals("Sorted by Score",
                sVModel.getSortedBy().getValue());
    }

    @Test
    public void getCurrentScoreProperty() {
        assertEquals("Score: " + model.getScores().getScore(),
                sVModel.getCurrentScoreProperty().get());
    }

    @Test
    public void getSortedBy() {
        assertEquals("Sorted by Name",
                sVModel.getSortedBy().getValue());
        sVModel.switchScoreOrder();
        sVModel.update();

        assertEquals("Sorted by Score",
                sVModel.getSortedBy().getValue());
    }

    @Test
    public void getScores() {
        assertEquals("Sorted by Name",
                sVModel.getSortedBy().getValue());
        assertEquals(model.getScores().getEntriesByName().size(),
                sVModel.getScores().size());

        sVModel.switchScoreOrder();
        sVModel.update();

        assertEquals("Sorted by Score",
                sVModel.getSortedBy().getValue());
        assertEquals(model.getScores().getEntriesByScore().size(),
                sVModel.getScores().size());
    }

    @Test
    public void getCurrentScore() {
        assertEquals(model.getScores().getScore(),
                sVModel.getCurrentScore());
    }

    @Test
    public void setPlayerScore() {
        assertEquals("Sorted by Name",
                sVModel.getSortedBy().getValue());

        model.getScores().setScore("@@@@@@@", 7);
        assertEquals(0, sVModel.getScores().size());
        model.getScores().setScore("ValidName", -111);
        assertEquals(0, sVModel.getScores().size());
        model.getScores().setScore(null, 111);
        assertEquals(0, sVModel.getScores().size());

        sVModel.switchScoreOrder();
        sVModel.update();
        assertEquals("Sorted by Score",
                sVModel.getSortedBy().getValue());

        model.getScores().setScore("@@@@@@@", 7);
        assertEquals(0, sVModel.getScores().size());
        model.getScores().setScore("ValidName", -111);
        assertEquals(0, sVModel.getScores().size());
        model.getScores().setScore(null, 111);
        assertEquals(0, sVModel.getScores().size());
    }
}