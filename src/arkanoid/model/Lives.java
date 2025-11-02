package arkanoid.model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import standards.main.StandardDraw;

/**
 * Quản lý và vẽ số mạng sống (Lives) dưới dạng các icon trái tim.
 */
public class Lives {

    private int lives;
    private final int maxLives;
    private final Image heartImage;

    private final double startX;
    private final double startY;
    private final double heartSize;

    public Lives(int initialLives, double startX, double startY) {
        this.lives = initialLives;
        this.maxLives = initialLives;
        this.startX = startX;
        this.startY = startY;
        this.heartSize = 24;

        this.heartImage = new Image("/entities/Lives/lives.png", 20, 20, true, true);
    }

    public int getLives() {
        return lives;
    }

    public void loseLife() {
        if (lives > 0) {
            lives--;
        }
    }

    public boolean isDead() {
        return lives <= 0;
    }

    public void reset() {
        lives = maxLives;
    }

    public void render() {
        for (int i = 0; i < lives; i++) {
            double x = startX + i * heartSize;
            StandardDraw.image(heartImage, x, startY);
        }
    }
}
