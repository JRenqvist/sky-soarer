package skySoarer.menu;


import skySoarer.game.Bird;

import javax.swing.*;
import java.awt.*;

/**
 * This class will create a health bar that displays how much health the player has left
 * The health bar will continuously update based on the player's health, with a red outline
 * to show how much health the player is missing
 */
public class HealthBar extends JComponent {

    private int x;
    private int y;
    private int width;
    private int currentWidth;
    private int height;
    private Bird bird;

    public HealthBar(Bird bird, int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 150;
        this.currentWidth = width;
        this.height = 30;
        this.bird = bird;
    }

    @Override
    public Dimension getPreferredSize() {
	    return new Dimension(currentWidth, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBounds(x, y, currentWidth, height);
        g.setColor(Color.RED);
        g.fillRect(0, 0, currentWidth, height-1);

        // Draw outline
        g.drawRect(0, 0, width-1, height-1);
    }

    public void update() {
        double health = bird.getHealth();
        currentWidth = (int) Math.round((health / 100) * width);
        setBounds(x, y, currentWidth, height);
    }

    public void reset() {
        currentWidth = width;
        setBounds(0, 0, width, height);
    }

    @Override public int getWidth() {
	    return width;
    }

    @Override public int getHeight() {
	    return height;
    }
}
