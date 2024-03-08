package skySoarer.game;


import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * This class represents a Bird sprite. The player will control the bird and collect stars.
 * The game variables, like score, health etc are stored in this class.
 * The bird will fly from side to side of the screen and its the players goal to send inputs to make the bird fly
 * and collect stars that are scattered on the field
 */
public class Bird extends JComponent {

    private ImageIcon bird;
    private double birdX;
    private double birdY;
    private double middleY;
    private int width;
    private int height;
    private int frameWidth;
    private int frameHeight;

    private final static double GRAVITY = 0.25;
    private final static int SPEED = 7;
    private double velocity = 0;
    private Heading heading = Heading.LEFT;
    private int currentStars = 0;   // Current stars is used to purchase power ups
    private int totalStars = 0;     // Total stars keeps track of the total amount of stars collected
    private static final int BASE_HEALTH = 100;
    private double health = BASE_HEALTH;
    private static final double BASE_HEALTH_LOSS = 0.25;
    private double healthLoss = BASE_HEALTH_LOSS;
    private int level = 0;
    private int highestLevel = 0;


    public Bird(int frameWidth, int frameHeight) {

            // Try to load in the birdLeft image. If image doesn't exist, URL will be null
        final URL birdURL = ClassLoader.getSystemResource("images/birdLeft.png");

        if (birdURL == null) {
            // If URL is null, exit program immediately
            System.err.println("Error: image 'birdLeft.png' not found.");
            System.exit(1);        // Status 1 to signal failure
        }

        this.bird = new ImageIcon(birdURL);

        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;

        this.width = bird.getIconWidth();
        this.height = bird.getIconHeight();

        // Place the bird in the middle of the screen
        this.birdX = (double) frameWidth / 2 - (double) bird.getIconWidth() / 2;
        this.birdY = (double) frameHeight / 2 - (double) bird.getIconHeight() / 2;
        this.middleY = birdY;
    }

    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        // Set x and y to 0 since these coordinates offsets according to bounds
        // I set the bounds dynamically in updatePosition
        bird.paintIcon(this, g, 0, 0);

    }

    public void update() {
        updatePosition();
        updateVelocity();
        decreaseHealth();
        updateLevel();
        updateHealthLoss();
    }

    private void updatePosition() {

        setBounds((int) birdX, (int) middleY, width, height);

        // Updates the Y position
        double updatedY = birdY - velocity;
        setBirdY(updatedY);

        // Updates the X position
        // Store old X position
        double oldXPosition = birdX;

        if (heading == Heading.LEFT) {
            birdX -= SPEED;

            if (outOfBounds()) {
                birdX = oldXPosition;
                heading = Heading.RIGHT;

                // Change the bird sprite to match direction
                final URL birdURL = ClassLoader.getSystemResource("images/birdRight.png");

                if (birdURL == null) {
                    // If URL is null, exit program immediately
                    System.err.println("Error: image 'birdRight.png' not found.");
                    System.exit(1);        // Status 1 to signal failure
                }

                setBird(new ImageIcon(birdURL));

            }

        } else {
            birdX += SPEED;

            if (outOfBounds()) {
                birdX = oldXPosition;
                heading = Heading.LEFT;

                // Change the bird sprite to match direction
                final URL birdURL = ClassLoader.getSystemResource("images/birdLeft.png");

                if (birdURL == null) {
                    // If URL is null, exit program immediately
                    System.err.println("Error: image 'birdLeft.png' not found.");
                    System.exit(1);        // Status 1 to signal failure
                }

                setBird(new ImageIcon(birdURL));
            }
        }
    }

    private boolean outOfBounds() {
        // If bird goes out of bounds to the right
        if (birdX + bird.getIconWidth() > frameWidth) {
            return true;

        // If bird goes out of bounds to the left
        } else if (birdX < 0) {
            return true;
        }

        // Else, bird is not out of bounds
        return false;
    }

    private void updateVelocity() {
	    velocity += GRAVITY;
    }

    private void updateLevel() {
        // Levels are calculated by getting the thousands of the birds Y value
        // e.g birdY = 3972, level = 3

        final int levelConverter = 1000;
        final int newLevel = (int) Math.floor(getBirdY() / levelConverter);
        level = newLevel;

        // Make sure you cant get negative levels
        if (level < 0) {
            level = 0;
        }

        // Update highest level
        if (level > highestLevel) {
            highestLevel = level;
        }

    }

    private void updateHealthLoss() {
        // Health loss starts at base 0.25 each tick, and 0.05 is added for each level
        final int levelMultiplier = 5;
        final int denominator = 100;
        final double newHealthLoss = (double) level * levelMultiplier / denominator;
        healthLoss = BASE_HEALTH_LOSS + newHealthLoss;
    }

    private void decreaseHealth() {
        health -= healthLoss;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getHealth() {
        return health;
    }

    public void reset() {
        setHealth(100);
        this.currentStars = 0;
        this.totalStars = 0;
        this.velocity = 0;
        this.heading = Heading.LEFT;
        setSpriteLeft();
        this.birdX = (double) frameWidth / 2 - (double) bird.getIconWidth() / 2;
        this.birdY = (double) frameHeight / 2 - (double) bird.getIconHeight() / 2;
        this.middleY = birdY;
        this.level = 0;
        this.highestLevel = 0;

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(bird.getIconWidth(), bird.getIconHeight());
    }


    public double getBirdY() {
        return birdY;
    }

    @Override public int getWidth() {
        return width;
    }

    @Override public int getHeight() {
        return height;
    }

    public int getLevel() {
        return level;
    }

    public int getHighestLevel() {
        return highestLevel;
    }

    public void addCurrentStars() {
        this.currentStars++;
        this.totalStars++;
    }

    public int getCurrentStars() {
        return currentStars;
    }

    public int getTotalStars() {
        return totalStars;
    }

    public void setBirdY(final double birdY) {
        this.birdY = birdY;
    }

    public void setVelocity(final double velocity) {
        this.velocity = velocity;
    }

    private void setBird(final ImageIcon bird) {
        this.bird = bird;
    }

    private void setSpriteLeft() {
        // Change the bird sprite to match direction
        final URL birdURL = ClassLoader.getSystemResource("images/birdLeft.png");

        if (birdURL == null) {
            // If URL is null, exit program immediately
            System.err.println("Error: image 'birdLeft.png' not found.");
            System.exit(1);        // Status 1 to signal failure
        }
        setBird(new ImageIcon(birdURL));
    }
}
