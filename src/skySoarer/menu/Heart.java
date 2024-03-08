package skySoarer.menu;


import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * This class will create a heart icon to display next to the health bar, to further indicate that it is the players health
 * This image will be displayed next to the health bar in the menu at the bottom of the window
 */
public class Heart extends JComponent {
    private ImageIcon heart;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public Heart(int x, int y) {

        // Try to load in the heart image. If image doesn't exist, URL will be null
        final URL heartURL = ClassLoader.getSystemResource("images/heart.png");

        if (heartURL == null) {
            // If URL is null, exit program immediately
            System.err.println("Error: image 'heart.png' not found.");
            System.exit(1);        // Status 1 to signal failure
        }

        this.heart = new ImageIcon(heartURL);
        this.x = x;
        this.y = y;
        this.width = heart.getIconWidth();
        this.height = heart.getIconHeight();
    }

    @Override
    public Dimension getPreferredSize() {
	    return new Dimension(width, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBounds(x, y, width, height);
        heart.paintIcon(this, g, 0, 0);
    }

    @Override public int getWidth() {
	    return width;
    }

    @Override public int getHeight() {
	    return height;
    }
}
