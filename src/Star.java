/*
CLASS: Star
DESCRIPTION: A background star for visual effect.
*/
import java.awt.*;
import java.util.Random;

class Star extends Circle {
    private double twinklePhase;
    private double twinkleRate;
    private double parallaxFactor;
    private static final Random random = new Random();

    public Star(int width, int height) {
        super(new Point(random.nextDouble() * width, random.nextDouble() * height), random.nextDouble() * 1.5 + 0.5, new Color(240, 240, 255));


        twinklePhase = random.nextDouble() * 2 * Math.PI;
        twinkleRate = random.nextDouble() * 0.1 + 0.05;


        parallaxFactor = getRadius() / 2;
    }

    @Override
    public void paint(Graphics brush) {

        double brightness = 0.7 + 0.3 * Math.sin(twinklePhase);
        int colorValue = (int)(brightness * 255);


        Color starColor = new Color(Math.min(colorValue, 255), Math.min(colorValue, 255), Math.min(255, colorValue + 10));

        brush.setColor(starColor);


        int size = (int)(getRadius() * (0.8 + 0.4 * brightness));
        brush.fillOval((int)(position.x - (double) size / 2), (int)(position.y - (double) size / 2), size, size);
    }


    public void twinkle(Point shipVelocity, int width, int height) {

        twinklePhase += twinkleRate;


        position.x -= shipVelocity.x * parallaxFactor;
        position.y -= shipVelocity.y * parallaxFactor;


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
}