
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
    }

    protected void startTimer() {
        if (updateTimer != null && !updateTimer.isRunning()) {
            updateTimer.start();
        }
    }

    protected void stopTimer() {
        if (updateTimer != null && updateTimer.isRunning()) {
            updateTimer.stop();
        }
    }
}