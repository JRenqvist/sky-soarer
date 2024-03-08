package skySoarer.menu;


import skySoarer.game.Bird;

import javax.swing.*;
import java.awt.*;

/**
 * This class will create a text field that displays the current amount of stars the bird has collected
 * The text field will be displayed in the black menu at the bottom of the window
 */
public class ScoreDisplay extends JLabel
{
    private Bird bird;

    public ScoreDisplay(Bird bird, int x, int y) {
		super("Stars: 0");
		this.bird = bird;

		setFont(new Font("Arial", Font.PLAIN, 20));
		setForeground(Color.RED);
		setSize(getPreferredSize());
		setLocation(x, y);
    }



    public void update() {
		setText("Stars: " + bird.getCurrentStars());
		setSize(getPreferredSize());
    }


    public void reset() {
		setText("Stars: 0");
		setSize(getPreferredSize());
    }

}
