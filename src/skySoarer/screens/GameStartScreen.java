package skySoarer.screens;


import javax.swing.*;
import java.awt.*;

/**
 * This class will create a start screen which consists of a see-through background and some simple instructions
 * on how to control the bird. While this screen is being displayed, it will be removed once the player
 * has made their first input
 */
public class GameStartScreen extends JComponent {

    private int width;
    private int height;

    public GameStartScreen(int frameWidth, int frameHeight) {
		this.width = frameWidth;
		this.height = frameHeight;
    }

    @Override
    public void paintComponent(final Graphics g) {
		super.paintComponent(g);
			setBounds(0, 0, width, height);

		// Draw transparent background
		g.setColor(new Color(100, 100, 100, 127));
		g.fillRect(0, 0, width, height);

		// Draw text
		Graphics2D g2d = (Graphics2D) g;
		Font font = new Font("Arial", Font.BOLD, 30);
		g2d.setFont(font);

		final int stringX = 50;
		final int stringY = 200;
		g2d.setColor(Color.BLACK);
		g2d.drawString("Press SPACE or ↑ to start!", stringX, stringY);

		g2d.dispose();

    }

    @Override
    public Dimension getPreferredSize() {
		return new Dimension(width, height);
    }

    @Override
    public int getHeight() {
		return height;
    }

    @Override
    public int getWidth() {
		return width;
    }
}
