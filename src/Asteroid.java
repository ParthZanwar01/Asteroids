
import java.awt.*;
import java.util.Random;

class Asteroid extends Polygon {
    private int size;
    private int width, height;
    private double rotationSpeed;


    public Asteroid(int width, int height, int size) {
        super(createAsteroidShape(size), createRandomPosition(width, height), new Random().nextDouble() * 360
        );

        this.width = width;
        this.height = height;
        this.size = size;


        Random random = new Random();
        rotationSpeed = (random.nextDouble() * 2 - 1) * 2;


        double speed = (4 - size) * 0.5;
        double angle = random.nextDouble() * 2 * Math.PI;
        setVelocity(new Point(speed * Math.cos(angle), speed * Math.sin(angle)
        ));
    }


    private static Point[] createAsteroidShape(int size) {
        Random random = new Random();


        int vertices = 8 + size;
        Point[] shape = new Point[vertices];


        double radius = size * 10;


        for (int i = 0; i < vertices; i++) {
            double angle = 2 * Math.PI * i / vertices;


            double radiusVariation = radius * 0.2;
            double currentRadius = radius + (random.nextDouble() * radiusVariation) - (radiusVariation / 2);

            double x = currentRadius * Math.cos(angle);
            double y = currentRadius * Math.sin(angle);

            shape[i] = new Point(x, y);
        }

        return shape;
    }


    private static Point createRandomPosition(int width, int height) {
        Random random = new Random();

        double x, y;

        do {
            x = random.nextDouble() * width;
            y = random.nextDouble() * height;
        } while (Math.abs(x - width/2) < 100 && Math.abs(y - height/2) < 100);

        return new Point(x, y);
    }

    @Override
    public void paint(Graphics brush) {
        brush.setColor(Color.GRAY);
        super.paint(brush);
    }

    @Override
    public void move() {

        super.move();


        rotate((int)rotationSpeed);


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


    public int getSize() {
        return size;
    }


    public Asteroid[] split() {
        if (size <= 1) {

            return new Asteroid[0];
        }


        Asteroid[] children = new Asteroid[2];
        children[0] = new Asteroid(width, height, size - 1);
        children[1] = new Asteroid(width, height, size - 1);


        children[0].position = new Point(position.x + 10, position.y + 10);
        children[1].position = new Point(position.x - 10, position.y - 10);


        Point vel = getVelocity();
        children[0].setVelocity(new Point(vel.x * 1.5, vel.y * 0.5));
        children[1].setVelocity(new Point(vel.x * 0.5, vel.y * 1.5));

        return children;
    }
}