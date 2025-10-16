package arkanoid.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import standards.main.StandardDraw;
import standards.controller.StandardFadeController;
import standards.model.StandardGameObject;
import standards.model.StandardID;

/**
 * Represents the player's paddle in the Arkanoid game.
 *
 * The paddle can move horizontally within screen boundaries, and
 * can temporarily increase in size when a power-up is active.
 * It also features a color fade animation when enlarged.
 */
public class Paddle extends StandardGameObject {

    /** Left boundary of paddle movement. */
    private final double LEFT_BORDER = 20;

    /** Right boundary offset from screen edge. */
    private final double RIGHT_BORDER = 20;

    /** Default paddle width. */
    private final double NORMAL_WIDTH = 100;

    /** Default paddle height. */
    private final double NORMAL_HEIGHT = 10;

    /** Default paddle color. */
    private final Color normal = StandardDraw.RED;

    /** Paddle movement speed. */
    private double speed = 8;


    /** Fade controller for the enlarged paddle effect. */
    private final StandardFadeController largeFade =
            new StandardFadeController(StandardDraw.MELON, StandardDraw.VIOLET, 0.05);

    /** Indicates whether the paddle is currently enlarged. */
    private boolean isLarge = false;

    /** Countdown timer for the large paddle duration. */
    private int timer = 500;

    /** Width of the scene to constrain paddle movement. */
    private double sceneWidth = 800;

    /**
     * Constructs a new Paddle at the given position.
     */
    public Paddle(double x, double y) {
        super(x, y, StandardID.Player);
        this.setWidth((int) NORMAL_WIDTH);
        this.setHeight((int) NORMAL_HEIGHT);
    }

    /**
     * Updates paddle state every frame.
     */
    public void tick() {
        this.setX(this.getX() + this.getVelX());
        this.setY(this.getY() + this.getVelY());

        // Xử lý biên
        if (this.getX() < LEFT_BORDER) {
            this.setX(LEFT_BORDER);
        }

        double rightLimit = sceneWidth - this.getWidth() - RIGHT_BORDER;
        if (this.getX() > rightLimit) {
            this.setX(rightLimit);
        }

        // Xử lý hiệu ứng large
        if (isLarge) {
            largeFade.combine();
            timer--;
            if (timer <= 0) {
                isLarge = false;
                this.setWidth((int) NORMAL_WIDTH);
                timer = 500;
            }
        }
    }


    /**
     * Renders the paddle on the given graphics context.
     */
    public void render(GraphicsContext gc) {
        if (isLarge)
            gc.setFill(largeFade.combine());
        else
            gc.setFill(normal);

        gc.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public void moveLeft() { this.setVelX(-speed); }
    public void moveRight() { this.setVelX(speed); }

    /**
     * Stops the paddle's horizontal movement.
     */
    public void stop() { this.setVelX(0); }

    /**
     * Enables or disables the large paddle power-up effect.
     */
    public void setLarge(boolean isLarge) {
        this.isLarge = isLarge;
        if (isLarge) {
            this.setWidth(160);
            this.timer = 500;
        }
    }

    /**
     * Checks whether the paddle is currently enlarged.
     */
    public boolean isLarge() { return this.isLarge; }

    /**
     * Sets the width of the game scene to restrict paddle movement.
     */
    public void setSceneWidth(double sceneWidth) { this.sceneWidth = sceneWidth; }
}
