package skySoarer.game;


import java.util.ArrayList;
import java.util.List;

/**
 * This class is the main skeleton of the game Sky Soarer
 * It contains a list of the stars on the screen and a list of backgrounds to give a smoother vertical scrolling game experience
 * aswell as the state of the game
 */
public class SkySoarer {

    private GameState gameState;
    private List<Star> stars;
    private List<Background> backgrounds;

    public SkySoarer() {
        this.gameState = GameState.START;
        this.stars = new ArrayList<>();
        this.backgrounds = new ArrayList<>();
    }

    public void addStar(Star star) {
	    stars.add(star);
    }

    public void addBackground(Background background) {
	    backgrounds.add(background);
    }

    public GameState getGameState() {
	    return gameState;
    }

    public List<Star> getStars() {
	    return stars;
    }

    public List<Background> getBackgrounds() {
	    return backgrounds;
    }

    public void setGameState(final GameState gameState) {
	    this.gameState = gameState;
    }
}
