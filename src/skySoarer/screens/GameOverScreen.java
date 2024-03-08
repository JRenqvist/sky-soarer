package skySoarer.screens;


import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * This class generates a game over screen from the gameOverScreen image in the resouces folder.
 * The image containts two text areas that represent buttons that the player can interact with:
 * one "Try again?" button, and one "Quit" button
 */
public class GameOverScreen extends JComponent {

    private ImageIcon gameOverImage;
    private int width;
    private int height;

    public GameOverScreen() {

        // Try to load in the gameOverScreen image. If image doesn't exist, URL will be null
        final URL gameOverUrl = ClassLoader.getSystemResource("images/gameOverScreen.png");

        if (gameOverUrl == null) {
            // If URL is null, exit program immediately
            System.err.println("Error: image 'gameOverScreen.png' not found.");
            System.exit(1);        // Status 1 to signal failure
        }

        this.gameOverImage = new ImageIcon(gameOverUrl);
        this.width = gameOverImage.getIconWidth();
        this.height = gameOverImage.getIconHeight();
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        setBounds(0, 0, width, height);
        gameOverImage.paintIcon(this, g, 0, 0);
    }

    @Override
    public Dimension getPreferredSize() {
	    return new Dimension(gameOverImage.getIconWidth(), gameOverImage.getIconHeight());
    }

    @Override
    public int getWidth() {
	    return width;
    }

    @Override
    public int getHeight() {
	    return height;
    }

}
