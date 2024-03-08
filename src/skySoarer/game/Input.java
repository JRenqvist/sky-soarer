package skySoarer.game;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This class handles the inputs of the game
 * It handles both input and action maps to get keyboard inputs,
 * and also mouse movement and mouse click events
 */
public class Input {

    private Point mouseLocation = null;
    private boolean mousePressed;

    public Input(JFrame frame, SkySoarer game, Bird bird) {

		// Create input and action maps
		InputMap inputMap = frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = frame.getRootPane().getActionMap();

		// Define keybindings - If a player presses up arrow or space, the bird should jump
		inputMap.put(KeyStroke.getKeyStroke("UP"), "jump");
		inputMap.put(KeyStroke.getKeyStroke("SPACE"), "jump");

		actionMap.put("jump", new AbstractAction()
		{
			@Override public void actionPerformed(final ActionEvent e) {
			bird.setVelocity(-5.0);
			if (game.getGameState() == GameState.START) {
				game.setGameState(GameState.PLAYING);
			}
			}
		});

		// Get the mouse location
		MouseMotionListener mouseMotionListener = new MouseMotionListener()
		{
			@Override public void mouseDragged(final MouseEvent e) {

			}

			@Override public void mouseMoved(final MouseEvent e) {
			// Gets called when you move the mouse. Also sets mouseLocation to updated value
			Point mouseLocation = e.getPoint();
			setMouseLocation(mouseLocation);
			}
		};

		// Get if the mouse is clicked or not
		MouseListener mouseListener = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			setMousePressed(true);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			setMousePressed(false);
			}
		};

		frame.getContentPane().addMouseMotionListener(mouseMotionListener);
		frame.getContentPane().addMouseListener(mouseListener);
    }


    public Point getMouseLocation() {
		return mouseLocation;
    }

    public boolean isMousePressed() {
		return mousePressed;
    }

    public void setMouseLocation(final Point mouseLocation) {
		this.mouseLocation = mouseLocation;
    }

    public void setMousePressed(final boolean mousePressed) {
		this.mousePressed = mousePressed;
    }
}
