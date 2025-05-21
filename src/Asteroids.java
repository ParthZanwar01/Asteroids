/*
CLASS: Asteroids
DESCRIPTION: Main game class that manages all game elements and implements the game loop.
*/
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.Timer;

class Asteroids extends Game {

    private Ship ship;
    private ArrayList<Asteroid> asteroids;
    private ArrayList<Bullet> bullets;
    private ArrayList<Star> stars;

    private int score;
    private int lives;
    private boolean gameOver;

    private static final int INITIAL_ASTEROIDS = 5;
    private static final int MAX_STARS = 100;
    private int increaseNumAsteroids = 1;

    private Timer gameTimer;

    public Asteroids() {
        super("Asteroids", 800, 600);

        score = 0;
        lives = 3;
        gameOver = false;

        ship = new Ship(width, height);

        addKeyListener(ship);

        asteroids = new ArrayList<>();
        spawnAsteroids(INITIAL_ASTEROIDS);

        bullets = new ArrayList<>();

        stars = new ArrayList<>();
        for (int i = 0; i < MAX_STARS; i++) {
            stars.add(new Star(width, height));
        }

        setFocusable(true);
        requestFocus();

        // Replace Thread.sleep with Timer
        gameTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (on) {
                    repaint();
                }
            }
        });
        gameTimer.start();
    }

    public void paint(Graphics brush) {
        brush.setColor(Color.BLACK);
        brush.fillRect(0, 0, width, height);

        if (gameOver) {
            displayGameOver(brush);
            return;
        }

        if (ship.isFiring()) {
            bullets.add(ship.fire());
        }

        for (Star star : stars) {
            star.twinkle(ship.getVelocity(), width, height);
            star.paint(brush);
        }

        ship.move();
        ship.paint(brush);

        ArrayList<Bullet> expiredBullets = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.move();

            if (bullet.position.x < 0 || bullet.position.x > width ||
                    bullet.position.y < 0 || bullet.position.y > height) {
                expiredBullets.add(bullet);
            } else {
                bullet.paint(brush);
            }
        }
        bullets.removeAll(expiredBullets);

        ArrayList<Asteroid> destroyedAsteroids = new ArrayList<>();
        for (Asteroid asteroid : asteroids) {
            asteroid.move();
            asteroid.paint(brush);

            for (Bullet bullet : bullets) {
                if (asteroid.contains(bullet.position)) {
                    destroyedAsteroids.add(asteroid);
                    expiredBullets.add(bullet);

                    score += (4 - asteroid.getSize()) * 100;
                    break;
                }
            }

            if (!destroyedAsteroids.contains(asteroid) && asteroid.intersects(ship)) {
                destroyedAsteroids.add(asteroid);
                loseLife();
            }
        }

        if (destroyedAsteroids.size() == asteroids.size()){
            spawnAsteroids(5+increaseNumAsteroids);
            for (int i = 1; i < 5+increaseNumAsteroids; i++) {
                asteroids.add(new Asteroid(width, height, 3));
            }
            increaseNumAsteroids++;
        }

        bullets.removeAll(expiredBullets);

        ArrayList<Asteroid> newAsteroids = new ArrayList<>();
        for (Asteroid asteroid : destroyedAsteroids) {
            Collections.addAll(newAsteroids, asteroid.split());
            asteroids.remove(asteroid);
        }
        asteroids.addAll(newAsteroids);

        displayInfo(brush);
    }

    public void update(Graphics brush) {
        super.update(brush);
    }

    private void spawnAsteroids(int count) {
        for (int i = 0; i < count; i++) {
            asteroids.add(new Asteroid(width, height, 3));
        }
    }

    private void loseLife() {
        lives--;

        if (lives <= 0) {
            gameOver = true;
            gameTimer.stop(); // Stop the timer when game is over
        } else {
            ship = new Ship(width, height);
            addKeyListener(ship);

            for (Asteroid asteroid : asteroids) {
                if (Math.abs(asteroid.position.x - width/2) < 100 &&
                        Math.abs(asteroid.position.y - height/2) < 100) {
                    if (asteroid.position.x < width/2) {
                        asteroid.position.x -= 100;
                    } else {
                        asteroid.position.x += 100;
                    }

                    if (asteroid.position.y < height/2) {
                        asteroid.position.y -= 100;
                    } else {
                        asteroid.position.y += 100;
                    }
                }
            }

            bullets.clear();
        }
    }

    private void displayInfo(Graphics brush) {
        brush.setColor(Color.WHITE);
        brush.setFont(new Font("Arial", Font.PLAIN, 16));
        brush.drawString("Score: " + score, 20, 30);

        brush.drawString("Lives: ", width - 150, 30);
        for (int i = 0; i < lives; i++) {
            brush.fillOval(width - 100 + (i * 20), 22, 10, 10);
        }
    }

    private void displayGameOver(Graphics brush) {
        brush.setColor(Color.WHITE);
        brush.setFont(new Font("Arial", Font.BOLD, 36));

        String gameOverText = "GAME OVER";
        FontMetrics metrics = brush.getFontMetrics();
        int x = (width - metrics.stringWidth(gameOverText)) / 2;
        int y = height / 2 - 50;
        brush.drawString(gameOverText, x, y);

        brush.setFont(new Font("Arial", Font.PLAIN, 24));
        String scoreText = "Final Score: " + score;
        metrics = brush.getFontMetrics();
        x = (width - metrics.stringWidth(scoreText)) / 2;
        y = height / 2;
        brush.drawString(scoreText, x, y);

        brush.setFont(new Font("Arial", Font.PLAIN, 18));
        String restartText = "Press SPACE to restart";
        metrics = brush.getFontMetrics();
        x = (width - metrics.stringWidth(restartText)) / 2;
        y = height / 2 + 50;
        brush.drawString(restartText, x, y);

        for (KeyListener kl : getKeyListeners()) {
            if (kl instanceof Ship) {
                removeKeyListener(kl);
            }
        }

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    restart();
                }
            }
        });
    }

    private void restart() {
        for (KeyListener kl : getKeyListeners()) {
            removeKeyListener(kl);
        }

        score = 0;
        lives = 3;
        gameOver = false;

        ship = new Ship(width, height);
        addKeyListener(ship);

        asteroids.clear();
        spawnAsteroids(INITIAL_ASTEROIDS);

        bullets.clear();

        // Restart the timer
        if (!gameTimer.isRunning()) {
            gameTimer.start();
        }
    }

    public static void main(String[] args) {
        Asteroids game = new Asteroids();
        game.repaint();
    }
}