/*
CLASS: Game
DESCRIPTION: A painted canvas in its own window, updated every tenth second.
USAGE: Extended by Asteroids.
NOTE: You don't need to understand the details here, no fiddling necessary.
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

abstract class Game extends Canvas {
    boolean on = true;
    int width, height;
    Image buffer;
    private Timer updateTimer;

    public Game(String name, int inWidth, int inHeight) {
        width = inWidth;
        height = inHeight;

        Frame frame = new Frame(name);
        frame.add(this);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                on = false;
                if (updateTimer != null) {
                    updateTimer.stop();
                }
                System.exit(0);
            }
        });

        buffer = createImage(width, height);

        setFocusable(true);
        requestFocus();

        // Initialize the timer but don't start it here
        // The subclass will handle starting its own timer
        updateTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (on) {
                    repaint();
                }
            }
        });
    }

    abstract public void paint(Graphics brush);

    public void update(Graphics brush) {
        paint(buffer.getGraphics());
        brush.drawImage(buffer, 0, 0, this);
        // Remove the sleep and repaint calls from here since
        // the timer in the subclass will handle the timing
    }

    // Method to start the timer if needed by subclasses
    protected void startTimer() {
        if (updateTimer != null && !updateTimer.isRunning()) {
            updateTimer.start();
        }
    }

    // Method to stop the timer if needed by subclasses
    protected void stopTimer() {
        if (updateTimer != null && updateTimer.isRunning()) {
            updateTimer.stop();
        }
    }
}