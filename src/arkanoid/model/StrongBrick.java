package arkanoid.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import standards.model.StandardID;

import java.util.Objects;

/**
 * Represents a durable brick that requires multiple hits to be destroyed.
 * Appears gray and plays a short hit animation instead of changing color.
 */
public class StrongBrick extends Brick {

    /** Current health points of the brick */
    private int health;

    /** Maximum health for reset or scaling */
    private final int maxHealth;

    /** Optional texture for hit animation */
    private Image hitSprite;

    /** Hit animation counter */
    private int hitTimer = 0;

    /**
     * Creates a StrongBrick (gray) at a specified position.
     */
    public StrongBrick(double x, double y, Color color) {
        super(x, y, color);
        this.setId(StandardID.Brick); // still treated as Brick for collision
        this.health = 2;              // requires 3 hits
        this.maxHealth = 2;
        loadHitSprite();
    }

    /**
     * Loads a special sprite for the hit animation (optional).
     */
    private void loadHitSprite() {
        String path = "/entities/Brick/graybrick.png"; // luôn dùng graybrick.png
        try {
            this.hitSprite = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
            super.sprite = hitSprite; // đặt luôn sprite chính
        } catch (Exception e) {
            this.hitSprite = null;
        }
    }


    /**
     * Called when the brick is hit by the ball.
     */
    public void takeHit() {
        if (health <= 0) return;
        health--;
        hitTimer = 8; // short flash animation duration
    }

    /**
     * Checks if the brick is completely destroyed.
     */
    public boolean isDestroyed() {
        return health <= 0;
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

        // Draw normal brick
        if (super.sprite != null) {
            gc.drawImage(super.sprite, this.getX(), this.getY(), getWidth(), getHeight());
        } else {
            gc.setFill(Color.GRAY);
            gc.fillRect(this.getX(), this.getY(), getWidth(), getHeight());
        }

        // Draw flash overlay if hit
        if (hitTimer > 0) {
            gc.setFill(new Color(1, 1, 1, 0.5)); // trắng bán trong suốt
            gc.fillRect(this.getX(), this.getY(), getWidth(), getHeight());
        }
    }



    @Override
    public String toString() {
        return String.format("StrongBrick[x=%.1f, y=%.1f, hp=%d/%d]", getX(), getY(), health, maxHealth);
    }

    public int getHealth() {
        return health;
    }

    protected void setHealth(int hp) {
        this.health = hp;
    }
}
