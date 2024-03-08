package skySoarer;

import skySoarer.game.*;
import skySoarer.menu.*;
import skySoarer.screens.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* ----------------------------------------------------------------
TODO:
	- Add power ups to buy with collected stars
	- Add power ups to the play field
	- Add debuffs to the play field
 */


/**
 * The main class for the "Sky Soarer" game. This class initializes and manages the game components,
 * handles game state transitions, and contains the main game loop.
 *
 * @author JRenqvist
 */
public class Main {
    private final static int TICKS_PER_SECOND = 120;
    private final static Random RAND = new Random();
    private final static int FRAME_WIDTH = 500;
    private final static int FRAME_HEIGHT = 700;

    public static void main(String[] args) {
		SwingUtilities.invokeLater(Main::createAndShowGame);
    }

    private static void createAndShowGame() {

		// Create a JFrame and set variables
		JFrame frame = new JFrame("Sky Soarer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("Sky Soarer");
		frame.setResizable(false);
		frame.setVisible(true);

		// Get frame content pane width and height
		Container frameContent = frame.getContentPane();
		int frameContentWidth = frame.getContentPane().getWidth();
		int frameContentHeight = frame.getContentPane().getHeight();

		// Create game instance
		SkySoarer game = new SkySoarer();

		// Create and add start screen
		GameStartScreen gameStartScreen = new GameStartScreen(frameContentWidth, frameContentHeight);
		frameContent.add(gameStartScreen);

		// Create and add bird to frame
		Bird bird = new Bird(frameContentWidth, frameContentHeight);
		bird.update();
		frameContent.add(bird);

		// Add heart icon through helper function to avoid code review issues
		Utils.addHeartIcon(frameContent, frameContentHeight);

		// Add health bar
		// This, scoreDisplay and levelDisplay all have the same x values
		final int menuItemX = 80;
		final int healthBarYOffset = 60;
		final int healthBarY = frameContentHeight - healthBarYOffset;
		HealthBar healthBar = new HealthBar(bird, menuItemX, healthBarY);	// Bird has to be used as argument to get current health
		frameContent.add(healthBar);

		// Add stars collected text
		final int scoreDisplayYOffset = 90;
		final int scoreDisplayY = frameContentHeight - scoreDisplayYOffset;
		ScoreDisplay scoreDisplay = new ScoreDisplay(bird, menuItemX, scoreDisplayY);	// Bird has to be used as argument to get current score
		frameContent.add(scoreDisplay);

		// Add level display
		final int levelDisplayYOffset = 25;
		final int levelDisplayY = frameContentHeight - levelDisplayYOffset;
		LevelDisplay levelDisplay = new LevelDisplay(bird, menuItemX, levelDisplayY);	// Bird has to be used as argument to get current level
		frameContent.add(levelDisplay);

		// Add menu bar, star and background through function to avoid code duplication in setGameStart
		Utils.addMenuBarStarsAndBackgrounds(frameContent, frameContentWidth, frameContentHeight,
						 game, bird);

		// Create input map
		Input input = new Input(frame, game, bird);

		// Create a Timer to update the game at 120 ticks per second for a smooth feel
		Timer timer = new Timer(1000 / TICKS_PER_SECOND, e -> {

			if (game.getGameState() == GameState.START) {
			// Dont do anything, waits until player clicks jump

			} else if (game.getGameState() == GameState.PLAYING) {

			// If gameStartScreen is visible on the frame, delete it
			if (Utils.isClassInFrame(frame, GameStartScreen.class)) {
				Utils.removeComponentsOfClass(frame, GameStartScreen.class);
				frame.revalidate();
			}

			// Main game loop


			double camOffset = -bird.getBirdY() + (double) FRAME_HEIGHT / 2 - (double) bird.getPreferredSize().height / 2;

			bird.update();
			healthBar.update();
			levelDisplay.update();

			// Check if health <= 0, means game over
			if (bird.getHealth() <= 0) {
				game.setGameState(GameState.END);
			}

			List<Star> starsCopy = new ArrayList<>(game.getStars());

			for (Star star : starsCopy) {

				star.updateOffset(camOffset);
				star.update();

				// If a star is below the bird and out of the frame, reposition the star above the bird
				final int starLowestPositionPadding = 90;
				final int starLowestPosition = star.getY() - starLowestPositionPadding;
				if (starLowestPosition > frameContentHeight) {
				star.setInitialY(star.getInitialY() - frameContentHeight*2);

				final int starIconWidth = 40;
				final int randomStarX = RAND.nextInt(frameContentWidth - starIconWidth);
				star.setX(randomStarX);
				}

				// Check if the bird is colliding with a star
				if (Utils.hasCollision(bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight(),
						   star.getX(), star.getY(), star.getWidth(), star.getHeight())) {

				// Add a star and set health to 100
				bird.addCurrentStars();
				bird.setHealth(100);

				// Update score display since we collected a star
				scoreDisplay.update();

				// Update star position
				final int bound = 300;
				final int randomY = RAND.nextInt(bound);
				final int updatedStarY = star.getInitialY() - (frameContentHeight - randomY);
				final int starIconWidth = 40;
				final int updatedStarX = RAND.nextInt(frameContentWidth - starIconWidth);
				star.setInitialY(updatedStarY);
				star.setX(updatedStarX);
				}
			}

			for (Background background : game.getBackgrounds()) {
				background.update();

			}

			// Update background positions
			int bgOffset = 40;
			Background baseBg = game.getBackgrounds().get(0);
			baseBg.setPosition((int) (bgOffset+camOffset + Math.round(bird.getBirdY() / FRAME_HEIGHT) * FRAME_HEIGHT));
			game.getBackgrounds().get(1).setPosition((int) (baseBg.getPosition() - FRAME_HEIGHT));
			game.getBackgrounds().get(2).setPosition((int) (baseBg.getPosition() + FRAME_HEIGHT));

			frame.revalidate();
			frame.repaint(); // Repaint to update the frame


			} else if (game.getGameState() == GameState.END){


			// -- Game over. Gives the option to replay or quit
			// Display game over screen

			frameContent.removeAll();

			GameOverScreen gameOverScreen = new GameOverScreen();
			frameContent.add(gameOverScreen);
			gameOverScreen.setSize(gameOverScreen.getPreferredSize());

			// Display total stars collected - Could implement another class similarily to LevelDisplay and ScoreDisplay
			//				   Seems a bit unnecessary
			JLabel totalStarsCollected = new JLabel("Total Stars Collected: " + bird.getTotalStars());
			totalStarsCollected.setFont( new Font("Arial", Font.PLAIN, 20));
			totalStarsCollected.setSize(totalStarsCollected.getPreferredSize());
			final int textX = 100;
			final int starsTextY = 200;
			totalStarsCollected.setLocation(textX, starsTextY);
			gameOverScreen.add(totalStarsCollected);

			// Display total stars collected - Could implement another class similarily to LevelDisplay and ScoreDisplay
			//				   Seems a bit unnecessary
			JLabel levelAchieved = new JLabel("Highest level: " + bird.getHighestLevel());
			levelAchieved.setFont( new Font("Arial", Font.PLAIN, 20));
			levelAchieved.setSize(levelAchieved.getPreferredSize());
			final int levelsTextY = 240;
			levelAchieved.setLocation(textX, levelsTextY);
			gameOverScreen.add(levelAchieved);

			// Find mouse coordinates and if mouse is pressed
			Point mouseLocation = input.getMouseLocation();
			boolean mousePressed = input.isMousePressed();

			if (Utils.isMouseOverQuitButton(mouseLocation) && mousePressed) {
				System.exit(0);
			} else if (Utils.isMouseOverRestartButton(mouseLocation) && mousePressed) {
				setGameStart(frame, game, bird, healthBar, scoreDisplay, levelDisplay);
				frame.repaint();
				game.setGameState(GameState.START);
			}
			frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
			}
		});



		// Start the timer
		timer.start();
    }

    public static void setGameStart(JFrame frame, SkySoarer game, Bird bird, HealthBar healthBar, ScoreDisplay scoreDisplay, LevelDisplay levelDisplay) {
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

		// Get frame content width and height
		Container frameContent = frame.getContentPane();
		int frameContentWidth = frame.getContentPane().getWidth();
		int frameContentHeight = frame.getContentPane().getHeight();

		// Remove all components
		frameContent.removeAll();



		// Reset game variables
		bird.reset();
		healthBar.reset();
		levelDisplay.reset();
		scoreDisplay.reset();
		game.getStars().clear();
		game.getBackgrounds().clear();

		// Create and add start screen
		GameStartScreen gameStartScreen = new GameStartScreen(frameContentWidth, frameContentHeight);
		frameContent.add(gameStartScreen);

		bird.update();
		frameContent.add(bird);

		// Add heart icon through helper function to avoid code review issues
		Utils.addHeartIcon(frameContent, frameContentHeight);

		// Add health bar
		frameContent.add(healthBar);

		// Add stars collected display
		frameContent.add(scoreDisplay);
		scoreDisplay.update();

		// Add level display
		frameContent.add(levelDisplay);
		levelDisplay.update();

		// Add menu bar, stars and background to avoid code duplication
		Utils.addMenuBarStarsAndBackgrounds(frameContent, frameContentWidth, frameContentHeight,
						 game, bird);

		// Revalidate and repaint the frame
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.revalidate();
		frame.repaint();

    }
}
