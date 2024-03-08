package skySoarer.menu;


import skySoarer.game.Bird;

import javax.swing.*;
import java.awt.*;

/**
 * This class will create a text field that displays which level the player is on
 * The text field will be displayed in the black menu at the bottom of the window
 */
public class LevelDisplay extends JLabel
{

    private Bird bird;

    public LevelDisplay(Bird bird, int x, int y) {
		super("Level: 0");
		this.bird = bird;


		setFont(new Font("Arial", Font.PLAIN, 20));
		setForeground(Color.CYAN);
		setSize(getPreferredSize());
		setLocation(x, y);
    }

    public void update() {
		setText("Level: " + bird.getLevel());
		setSize(getPreferredSize());
    }

    public void reset() {
		setText("Level: 0");
			setSize(getPreferredSize());
	}
}
