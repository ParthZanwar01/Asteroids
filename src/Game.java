/*
CLASS: Game
DESCRIPTION: A painted canvas in its own window, updated every tenth second.
USAGE: Extended by Asteroids.
NOTE: You don't need to understand the details here, no fiddling necessary.
*/
import java.awt.*;
import java.awt.event.*;

abstract class Game extends Canvas {
    boolean on = true;
    int width, height;
    Image buffer;

    public Game(String name, int inWidth, int inHeight) {
        width = inWidth;
        height = inHeight;


        Frame frame = new Frame(name);
        frame.add(this);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });

        buffer = createImage(width, height);


        setFocusable(true);
        requestFocus();
    }


    abstract public void paint(Graphics brush);




    public void update(Graphics brush) {
        paint(buffer.getGraphics());
        brush.drawImage(buffer, 0, 0, this);
        if (on) {sleep(10); repaint();}
    }


    private void sleep(int time) {
        try {Thread.sleep(time);} catch(Exception exc){};
    }
}