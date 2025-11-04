package arkanoid.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Objects;

/**
 * Represents a cursed brick that grows stronger when hit.
 * Each hit increases its health instead of decreasing it.
 * The brick never changes color, only flashes on hit.
 */
public class HellBrick extends StrongBrick {

    /** Maximum allowed health to prevent overflow */
    private static final int MAX_HEALTH = 1000;

    /** Flash duration after each hit */
    private int hitTimer = 8;

    /**
     * Creates a HellBrick with initial health of 3, color dark red.
     */
    public HellBrick(double x, double y, Color color) {
        super(x, y, color);
        this.setHealth(1000);

        try {
            super.sprite = new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream("/entities/Brick/blackbrick.png")));
        } catch (Exception e) {
            System.err.println("Failed to load HellBrick sprite");
            super.sprite = null;
        }
    }

    /**
     * When hit, the brick becomes stronger instead of weaker.
     */
    @Override
    public void takeHit() {
        int newHealth = Math.min(getHealth() + 1, MAX_HEALTH);
        setHealth(newHealth);
        hitTimer = 8;
    }

    @Override
    public boolean isDestroyed() {
        // HellBrick never dies (unless manually removed)
        return false;
    }

    @Override
    public void tick() {
        if (hitTimer > 0) hitTimer--;
    }

    @Override
    public void render(GraphicsContext gc) {
        // Shadow
        gc.setFill(new Color(0, 0, 0, 0.5));
        gc.fillRect(this.getX() + this.getWidth() / 3.0,
                this.getY() + this.getHeight() / 2.0,
                this.getWidth(),
                this.getHeight());

        // Vẽ sprite chính nếu có
        if (super.sprite != null) {
            gc.drawImage(super.sprite, this.getX(), this.getY(), getWidth(), getHeight());
        } else {
            gc.setFill(Color.BLACK); // fallback
            gc.fillRect(this.getX(), this.getY(), getWidth(), getHeight());
        }

        // Overlay flash khi bị hit
        if (hitTimer > 0) {
            gc.setFill(new Color(1, 1, 1, 0.5)); // flash trắng bán trong suốt
            gc.fillRect(this.getX(), this.getY(), getWidth(), getHeight());
        }
    }


    @Override
    public String toString() {
        return String.format("HellBrick[x=%.1f, y=%.1f, hp=%d]", getX(), getY(), getHealth());
    }
}
