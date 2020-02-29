package pacman.display;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pacman.game.PacmanGame;

/**
 * ScoreViewModel is an intermediary between ScoreView and the PacmanGame.
 * Used for displaying the player's score in the GUI.
 */
public class ScoreViewModel {
    private PacmanGame model;
    private final StringProperty currentScoreProperty =
            new SimpleStringProperty();
    private final StringProperty sortedBy =
            new SimpleStringProperty();
    private ObservableList<String> getScores =
            FXCollections.observableArrayList();

    /**
     * Creates a new ScoreViewModel and updates its properties.
     * The default ordering of the scores should be by name.
     * @param model - the PacmanGame to link to the view.
     */
    public ScoreViewModel(PacmanGame model) {
        this.model = model;
        sortedBy.set("Sorted by Name");
    }

    /**
     * Updates the properties containing the current score,
     * the sort order of the scoreboard and the list of sorted scores.
     *
     * The format for the current score property should be
     * "Score: [currentScore]" without quotes or brackets,
     * where currentScore is the value returned by ScoreBoard.getScore().
     *
     * The sort order property should be set to either
     * "Sorted by Name" or "Sorted by Score",
     * depending on the current score sort order.
     *
     * Finally, the property representing the list of scores should be
     * updated to contain a list of scores sorted
     * according to the current score sort order,
     * as returned by ScoreBoard.getEntriesByName()
     * and ScoreBoard.getEntriesByScore().
     */
    public void update() {
        getScores.clear();
        currentScoreProperty.set("Score: " + model.getScores().getScore());
        if (sortedBy.get().equals("Sorted by Name")) {
            getScores.addAll(model.getScores().getEntriesByName());
        }//check to contain the sort order property set to "Sorted by Name"
        else if (sortedBy.get().equals("Sorted by Score")) {
            getScores.addAll(model.getScores().getEntriesByScore());
        }//check to contain the sort order property set to "Sorted by Score"
    }

    /**
     * Changes the order in which player's scores are displayed.
     * The possible orderings are by name or by score.
     * Calling this method once should switch between these two orderings.
     */
    public void switchScoreOrder() {
        if (sortedBy.get().equals("Sorted by Name")) {
            sortedBy.set("Sorted by Score");
        } else {
            sortedBy.set("Sorted by Name");
        }//check order and change the order if different
    }

    /**
     * Returns the StringProperty containing the current score
     * for the player.
     * @return the property representing the current score.
     */
    public StringProperty getCurrentScoreProperty() {
        return this.currentScoreProperty;
    }

    /**
     * Returns the StringProperty of how
     * the player's scores are displayed.
     * @return StringProperty representing how
     * the player's scores are displayed.
     */
    public StringProperty getSortedBy() {
        return this.sortedBy;
    }

    /**
     * Returns a list containing all "Name : Value" score entries
     * in the game's ScoreBoard, sorted by the current sort order.
     * @return the list of sorted scores.
     */
    public ObservableList<String> getScores() {
        return getScores;
    }

    /**
     * Returns the overall current score for the game.
     * @return current score.
     */
    public int getCurrentScore() {
        return model.getScores().getScore();
    }

    /**
     * Sets the given player's score to score.
     * This should override the score if it was previously set
     * (even if new score is lower).
     * Invalid player names should be ignored.
     * @param player - the player
     * @param score - the new score
     */
    public void setPlayerScore(String player, int score) {
        model.getScores().setScore(player,score);
    }
}