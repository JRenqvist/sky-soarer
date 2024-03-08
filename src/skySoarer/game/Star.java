package skySoarer.game;


import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * This class represents a star object. This is the main collectible for the game, and the player needs to collect these
 * to get a higher score. One instance of this class will have their coordinates updated after being collected
 */
public class Star extends JComponent {

    private ImageIcon star;
    private int x;
    private double camOffset;
    private int y;
    private int initialY;
    private int width;
    private int height;


    public Star(final int x, final int y) {
        this.x = x;
        this.y = y;
        this.initialY = y;
        this.camOffset = 0;

        final URL starURL = ClassLoader.getSystemResource("images/star.png");

        if (starURL == null) {
            // If URL is null, exit program immediately
            System.err.println("Error: image 'star.png' not found.");
            System.exit(1);        // Status 1 to signal failure
        }

        this.star = new ImageIcon(starURL);

        this.width = star.getIconWidth();
        this.height = star.getIconHeight();

    }

    public void update() {
	    updateStarPosition();
    }

    public void updateOffset(double camOffset) {
	    this.camOffset = (int) camOffset;
    }

    public void setY(final int y) {
	    this.y = y;
    }

    public void setInitialY(final int initialY) {
	    this.initialY = initialY;
    }

    public int getInitialY() {
	    return initialY;
    }

    public void setX(final int x) {
	    this.x = x;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Set x and y to 0 since these coordinates offsets according to bounds
        // I set the bounds dynamically in updateStarPosition
        star.paintIcon(this, g, 0, 0);

    }

    @Override
    public Dimension getPreferredSize() {
	    return new Dimension(star.getIconWidth(), star.getIconHeight());
    }

    private void updateStarPosition() {
        // I technically only move the stars and not the bird
        // Set the Y to the changing camera offset, and the starting Y value
        setY(initialY - (int)camOffset);
        setBounds(x, y, width, height);
    }

    @Override public int getX() {
	    return x;
    }

    @Override public int getY() {
	    return y;
    }

    @Override public int getWidth() {
	    return width;
    }

    @Override public int getHeight() {
	    return height;
    }
}
