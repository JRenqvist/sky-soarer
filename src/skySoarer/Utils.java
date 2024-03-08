package skySoarer;

import skySoarer.menu.*;
import skySoarer.menu.MenuBar;
import skySoarer.game.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * This class is a utility class, which provides helpful methods like collision detection and mouse location detection
 */
public class Utils {

    private final static Random RAND = new Random();

    public static boolean hasCollision(int aX, int aY, int aWidth, int aHeight, int bX, int bY, int bWidth, int bHeight) {
		// Check if a is to the right of b
		if (aX > bX + bWidth || bX > aX + aWidth) {
			return false;
		}

		// Check if a is below b
		if (aY > bY + bHeight || bY > aY + aHeight) {
			return false;
		}

		// If the above conditions are false, the rectangles overlap
		return true;
    }

    public static boolean isMouseOverRestartButton(Point mouseLocation) {

		if (mouseLocation == null) {
			return false;
		}

		// Manually found coordinates for restart button
		final int mouseWidthHeight = 10;
		final int restartButtonX = 145;
		final int restartButtonY = 340;
		final int restartButtonWidth = 205;
			final int restartButtonHeight = 40;
		return hasCollision(mouseLocation.x, mouseLocation.y, mouseWidthHeight, mouseWidthHeight,
					restartButtonX, restartButtonY, restartButtonWidth, restartButtonHeight);
    }

    public static boolean isMouseOverQuitButton(Point mouseLocation) {

		if (mouseLocation == null) {
			return false;
		}

		// Manually found coordinates for quit button
		final int mouseWidthHeight = 10;
		final int quitButtonX = 215;
		final int quitButtonY = 460;
		final int quitButtonWidth = 80;
		final int quitButtonHeight = 40;
		return hasCollision(mouseLocation.x, mouseLocation.y, mouseWidthHeight, mouseWidthHeight,
			    quitButtonX, quitButtonY, quitButtonWidth, quitButtonHeight);
    }

    public static boolean isClassInFrame(JFrame frame, Class<?> targetClass) {
		Component[] components = frame.getContentPane().getComponents();
		for (Component c : components) {
			Class<?> componentClass = c.getClass();
			if (targetClass.equals(componentClass)) {
				return true;
	    	}
		}
		return false;
    }

    public static void removeComponentsOfClass(JFrame frame, Class<?> targetClass) {
		Component[] components = frame.getContentPane().getComponents();
		for (Component c : components) {
			Class<?> componentClass = c.getClass();
			if (targetClass.equals(componentClass)) {
				frame.getContentPane().remove(c);
			}
		}
    }

    public static void addMenuBarStarsAndBackgrounds(Container frameContent, int frameContentWidth, int frameContentHeight,
						     SkySoarer game, Bird bird) {
		// Create menu bar
		final int menuBarX = 0;
		final int menuBarYOffset = 100;
		final int menuBarY = frameContentHeight - menuBarYOffset;
		MenuBar menuBar = new MenuBar(menuBarX, menuBarY);
		frameContent.add(menuBar);

		// Create list of 5 stars
		final int amountOfStars = 5;
		for (int i = 0; i < amountOfStars; i++) {

			// Create x with -40 to range to avoid star being out of bounds since width of star image is 40
			final int starIconWidth = 40;
			final int starX = RAND.nextInt(frameContentWidth) - starIconWidth;

			// Create y above the bird, randomly, with 100 pixels as padding
			final int starYPadding = 100;
			final int starY = (int) bird.getBirdY() - RAND.nextInt(frameContentHeight) - starYPadding;
			game.addStar(new Star(starX, starY));
		}

		// Add stars to the frame
		for (Star star : game.getStars()) {
			frameContent.add(star);
		}

		// Add 3 backgrounds
		final int amountOfBackgrounds = 3;
		for (int i = 0; i < amountOfBackgrounds; i++) {
			game.addBackground(new Background());
		}

		for (Background background : game.getBackgrounds()) {
			frameContent.add(background);
		}
    }

    public static void addHeartIcon(Container frameContent, int frameContentHeight) {
		// Add heart icon
		final int heartX = 20;
		final int heartYOffset = 70;
		final int heartY = frameContentHeight - heartYOffset;
		Heart heart = new Heart(heartX, heartY);
		frameContent.add(heart);
    }
}
