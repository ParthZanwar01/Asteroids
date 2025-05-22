/*
CLASS: Ship
DESCRIPTION: A ship that can be controlled by the player in the Asteroids game.
*/
import java.awt.*;
import java.awt.event.*;

class Ship extends Polygon implements KeyListener {

    private boolean up, right, left, space;
    private Point pull;
    private final int width, height;

    public Ship(int width, int height) {

        super(new Point[] {new Point(-10, -10), new Point(20, 0), new Point(-10, 10)}, new Point(width/2, height/2), 0);

        this.width = width;
        this.height = height;


        pull = new Point(0, 0);


        up = false;
        right = false;
        left = false;
        space = false;
    }


    public void paint(Graphics brush) {

        Point[] points = getPoints();


        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];


        for (int i = 0; i < points.length; i++) {
            xPoints[i] = (int)points[i].x;
            yPoints[i] = (int)points[i].y;
        }


        brush.setColor(Color.WHITE);
        brush.drawPolygon(xPoints, yPoints, points.length);
    }


    public void move() {

        if (up) {
            accelerate(0.05);
        }
        if (right) {
            rotate(2);
        }
        if (left) {
            rotate(-2);
        }


        position.x += pull.x;
        position.y += pull.y;


        pull.x *= 0.99;
        pull.y *= 0.99;


        if (position.x > width) {
            position.x = 0;
        } else if (position.x < 0) {
            position.x = width;
        }

        if (position.y > height) {
            position.y = 0;
        } else if (position.y < 0) {
            position.y = height;
        }
    }


    public void accelerate(double acceleration) {
        pull.x += (acceleration * Math.cos(Math.toRadians(rotation)));
        pull.y += (acceleration * Math.sin(Math.toRadians(rotation)));
    }


    public Bullet fire() {

        Point[] shipPoints = getPoints();
        Point front = shipPoints[2];


        Bullet bullet = new Bullet(front, rotation, pull);
        return bullet;
    }


    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            up = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            right = true;
        }
        if (key == KeyEvent.VK_LEFT) {
            left = true;
        }
        if (key == KeyEvent.VK_SPACE) {
            space = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            up = false;
        }
        if (key == KeyEvent.VK_RIGHT) {
            right = false;
        }
        if (key == KeyEvent.VK_LEFT) {
            left = false;
        }
        if (key == KeyEvent.VK_SPACE) {
            space = false;
        }
    }


    public void keyTyped(KeyEvent e) {

    }


    public boolean isFiring() {
        if (space) {
            space = false;
            return true;
        }
        return false;
    }


    public Point getVelocity() {
        return pull;
    }
}