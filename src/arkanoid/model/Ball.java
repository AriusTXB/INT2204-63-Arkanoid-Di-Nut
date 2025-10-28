package arkanoid.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import standards.main.StandardDraw;
import standards.controller.StandardFadeController;
import standards.model.StandardGameObject;
import standards.model.StandardID;
import standards.util.StdOps;

/**
 * Represents the moving ball in the Arkanoid game.
 * The ball's sole responsibility is to move and collide.
 * Trail creation is handled externally by GameLogicTest for performance.
 */
public class Ball extends StandardGameObject {

    /** Fade controller for dynamic color blending during motion. */
    private final StandardFadeController stdFade =
            new StandardFadeController(StandardDraw.YELLOW, StandardDraw.RUSTY_RED, .008);

    /** Left screen boundary for collision detection. */
    private final double LEFT_BORDER = 20;

    /** Right screen boundary offset. */
    private final double RIGHT_BORDER = 20;

    /** Scene dimensions for boundary control. */
    private double sceneWidth = 800;
    private double sceneHeight = 600;

    /** Indicates whether the ball is active or lost. */
    private boolean isAlive = true;

    /** Velocity of the ball in the x and y directions */
    private double speedX, speedY;

    /**
     * Constructs a new Ball object at a given position and difficulty level.
     */
    public Ball(double x, double y, int difficulty) {
        super(x, y, 15, 15);  // Super class constructor for setting initial position and size
        this.setId(StandardID.Enemy);
        setFixedVelocity(difficulty);  // Set velocity based on difficulty
    }

    /**
     * Sets a fixed velocity for the ball based on the current difficulty level.
     */
    private void setFixedVelocity(int difficulty) {
        double speed;
        switch (difficulty) {
            case 1 -> speed = 4;
            case 2 -> speed = 6;
            case 3 -> speed = 8;
            default -> speed = 5;
        }

        // Randomize direction on both axes (-1, 0, 1)
        int dirX = StdOps.randomInt(-1, 1);
        int dirY = StdOps.randomInt(-1, 1);

        // Ensure the ball always moves
        if (dirX == 0) dirX = 1;
        if (dirY == 0) dirY = -1;

        this.setVelX(speed * dirX);
        this.setVelY(speed * dirY);
    }

    /**
     * Updates the ball’s position and handles wall collisions.
     */
    public void tick() {
        if (!isAlive) return;

        // Horizontal wall collisions
        if (this.getX() <= LEFT_BORDER || this.getX() >= sceneWidth - this.getWidth() - RIGHT_BORDER)
            this.setVelX(-this.getVelX()); // Reverse direction

        // Top wall collision
        if (this.getY() <= LEFT_BORDER)
            this.setVelY(-this.getVelY());  // Reverse direction

        // Fell below screen -> lose a life
        if (this.getY() > sceneHeight) {
            this.isAlive = false;
            return;
        }

        // Update position based on velocity
        this.setX(this.getX() + this.getVelX());
        this.setY(this.getY() + this.getVelY());
    }

    /**
     * Renders the ball with a fade effect on the given graphics context.
     */
    public void render(GraphicsContext gc) {
        if (!isAlive) return;
        gc.setFill(stdFade.combine());  // Apply fade effect
        gc.fillOval(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    /**
     * Gets the current color of the ball.
     */
    public Color getCurrentColor() {
        return stdFade.combine();
    }

    /**
     * Checks if the ball is still active.
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets the scene dimensions so that the ball can respect the boundaries.
     */
    public void setSceneSize(double width, double height) {
        this.sceneWidth = width;
        this.sceneHeight = height;
    }
}
