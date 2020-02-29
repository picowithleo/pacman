package pacman.display;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pacman.game.GameWriter;
import pacman.game.PacmanGame;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import static pacman.hunter.Hunter.SPECIAL_DURATION;
import static pacman.util.Direction.*;

/**
 * Used as an intermediary between the game and the MainView.
 */
public class MainViewModel {
    private PacmanGame model;
    private ScoreViewModel scoreViewModel;
    private BoardViewModel boardViewModel;
    private final StringProperty title = new SimpleStringProperty();
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();
    private final BooleanProperty isPaused = new SimpleBooleanProperty();
    private String saveFilename;
    private int tick;

    /**
     * Creates a MainViewModel and updates the properties
     * and the ScoreViewModel created.
     * ScoreViewModel and BoardViewModel should be created here.
     * By default, the game should be paused.
     * @param model - the PacmanGame to link to the view
     * @param saveFilename - the name for saving the game
     */
    public MainViewModel(PacmanGame model,
                          String saveFilename) {
        this.model = model;
        scoreViewModel = new ScoreViewModel(model);
        boardViewModel = new BoardViewModel(model);
        this.isPaused.set(true);
        this.isGameOver.set(false);
        this.saveFilename = saveFilename;
        tick = 0;
    }

    /**
     * Updates the title of the game window and the score view model.
     * @ensures title format is "[title] by [author]" without
     * the quotes or brackets.
     * For example, "Default CSSE2002 PacMan Map by Evan Hughes".
     * There should be a single space either side of "by"
     */
    public void update() {
        this.title.set(model.getTitle() + " by " + model.getAuthor());
        scoreViewModel.update();
    }

    /**
     * Gets the title property of the Game Window.
     * @return the title property of the game.
     */
    public StringProperty getTitle() {
        return this.title;
    }

    /**
     * Gets the property representing whether the game is over or not.
     * @return the game over status.
     */
    public BooleanProperty isGameOver() {
        return this.isGameOver;
    }

    /**
     * Saves the current state of the game to the file location
     * given in the constructor.
     * An IOException should not cause the program to crash
     * and should be ignored.
     */
    public void save() {
        try {
            Writer writer = new BufferedWriter(
                    new FileWriter(saveFilename));
            GameWriter.write(writer, model);
            writer.close();
        } catch (IOException e) { //ignored
        }
    }

    /**
     * Tick is to be called by the view at around 60 times a second.
     * This method will pass these ticks down to the model at a reduced rate
     * depending on the level of the game. The game starts with zero ticks.
     * If the game is not paused:
     *
     * · Check if the current tick count is integer-divisible by
     *   the delay specified for the current level.
     *   If it is integer-divisible:
     *      · Tick the model. See PacmanGame.tick()
     * · Increment the tick count by 1.
     * Finally, update the "game over" property to be true
     * if the player currently has 0 lives left, and false otherwise.
     * This should be done regardless of whether or not the game is paused.
     *
     *    Delays
     * levels	delay
     * 0,1	    50
     * 2,3	    40
     * 4,5	    30
     * 6,7,8	20
     * other	10
     */
    public void tick() {
        if (!this.isPaused.get()) {
            if (model.getLevel() == 0 || model.getLevel() == 1) {
                if (tick % 50 == 0) { //check is integer-divisible
                    model.tick();
                }
            }
            else if (model.getLevel() == 2 || model.getLevel() == 3) {
                if (tick % 40 == 0) {//check is integer-divisible
                    model.tick();
                }
            }
            else if (model.getLevel() == 4 || model.getLevel() == 5) {
                if (tick % 30 == 0) {//check is integer-divisible
                    model.tick();
                }
            }
            else if (model.getLevel() == 6 || model.getLevel() == 7
                    || model.getLevel() == 8) {
                if (tick % 20 == 0) {//check is integer-divisible
                    model.tick();
                }
            }
            else if (model.getLevel() > 8 ) {//check other level cases
                if (tick % 10 == 0) {//check is integer-divisible
                    model.tick();
                }
            }
            tick ++;
        }
        if (model.getLives() == 0) {
            this.isGameOver.set(true);
        } else {this.isGameOver.set(false);}
    }

    /**
     * Accepts key input from the view and acts according to the key.
     *          Keyboard Actions
     * Keys	    Available when paused	   Action
     * P, p	        true	      Pauses or unpauses the game.
     * R, r	        true	      Resets the model.
     * A, a	        false	      Changes pacman to face left.
     * D, d	        false	      Changes pacman to face right.
     * W, w	        false	      Changes pacman to face up.
     * S, s	        false	      Changes pacman to face down.
     * O, o	        false	      Activates the hunter's special ability
     *                            (if it is available) for a duration of
     *                            Hunter.SPECIAL_DURATION.
     * @param input - incoming input from the view.
     */
    public void accept(String input) {
        switch (input.toLowerCase()) {//set all input to lower case
            case "p" :
                if (this.isPaused.get()) {//check is Available
                    this.isPaused.set(false);
                } else {
                    this.isPaused.set(true);
                }
                break;
            case "r" :
                if (this.isPaused.get()) {//check is Available
                    model.reset();
                }
                break;
            case "a" :
                if (!this.isPaused.get()) {//check is Available
                    model.getHunter().setDirection(LEFT);
                }
                break;
            case "d" :
                if (!this.isPaused.get()) {//check is Available
                    model.getHunter().setDirection(RIGHT);
                }
                break;
            case "w" :
                if (!this.isPaused.get()) {//check is Available
                    model.getHunter().setDirection(UP);
                }
                break;
            case "s" :
                if (!this.isPaused.get()) {//check is Available
                    model.getHunter().setDirection(DOWN);
                }
                break;
            case "o" :
                if (!this.isPaused.get()) {//check is Available
                    if (model.getHunter().getSpecialDurationRemaining() > 0) {
                        model.getHunter().activateSpecial(
                            model.getHunter().getSpecialDurationRemaining());
                    } else {
                        model.getHunter().activateSpecial(SPECIAL_DURATION);
                    }//check is available to active special when <= 0
                }
                break;
        }
    }

    /**
     * Gets the paused property of the game.
     * @return the property associated with the pause state.
     */
    public BooleanProperty isPaused() {
        return this.isPaused;
    }

    /**
     * Gets the ScoreViewModel created at initialisation.
     * @return the ScoreViewModel associated wtih the game's scores.
     */
    public ScoreViewModel getScoreVM() {
        return scoreViewModel;
    }

    /**
     * Gets the BoardViewModel created at initialisation.
     * @return the BoardViewModel associated with the game play.
     */
    public BoardViewModel getBoardVM() {
        return boardViewModel;
    }
}