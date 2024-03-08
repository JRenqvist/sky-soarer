package skySoarer.menu;


import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * This class will create a black bar at the bottom of the screen.
 * Is used as a base for other components of the menu, like health bar, level display etc
 */
public class MenuBar extends JComponent {

    private ImageIcon menu;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public MenuBar(int x, int y) {

        // Try to load in the menuBar image. If image doesn't exist, URL will be null
        final URL menuURL = ClassLoader.getSystemResource("images/menuBar.png");

        if (menuURL == null) {
            // If URL is null, exit program immediately
            System.err.println("Error: image 'menuBar.png' not found.");
            System.exit(1);        // Status 1 to signal failure
        }

        this.menu = new ImageIcon(menuURL);
        this.x = x;
        this.y = y;
        this.width = menu.getIconWidth();
        this.height = menu.getIconHeight();

    }

    @Override
    public Dimension getPreferredSize() {
	    return new Dimension(width, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBounds(x, y, width, height);
        menu.paintIcon(this, g, 0, 0);
    }

    @Override public int getWidth() {
	    return width;
    }

    @Override public int getHeight() {
	    return height;
    }
}