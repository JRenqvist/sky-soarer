package skySoarer.game;



import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * This class handles a background object. In the game Sky Soarer, three of these instances will be created.
 * This is to create a smooth scrolling effect as the player increases or decreases their height
 */
public class Background extends JComponent
{
    private ImageIcon background;
    private int position;
    private int width;
    private int height;


    public Background() {
        this.position = 0;

        // Try to load in the background image. If image doesn't exist, URL will be null
        final URL backgroundURL = ClassLoader.getSystemResource("images/background.png");

        if (backgroundURL == null) {
            // If URL is null, exit program immediately
            System.err.println("Error: image 'background.png' not found.");
            System.exit(1);        // Status 1 to signal failure
        }

        this.background = new ImageIcon(backgroundURL);

        this.width = background.getIconWidth();
        this.height = background.getIconHeight();
    }

    public void update() {
	    updateBackgroundPosition();
    }

    public void setPosition(final int position) {
	    this.position = position;
    }


    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        background.paintIcon(this, g, 0, 0);
    }

    @Override
    public Dimension getPreferredSize() {
	    return new Dimension(background.getIconWidth(), background.getIconHeight());
    }

    private void updateBackgroundPosition() {
        // Invert the position, since otherwise we get the opposite jumping effect
        this.position = -this.position;
	    setBounds(0, this.position, width, height);
    }

    public int getHeight() {
	    return height;
    }

    public double getPosition() {
	    return position;
    }
}