package arkanoid.model.item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import standards.main.StandardDraw;
import standards.handler.StandardHandler;
import standards.model.StandardID;
import arkanoid.model.BasicParticle;
import arkanoid.model.Paddle;
import arkanoid.controller.Game;

/**
 * Represents the "Large" item collectible in the game.
 * <p>
 * When collected by the player (a {@link Paddle}), it increases the paddle’s width,
 * triggers a sound effect, and removes itself from the game world.
 * </p>
 * <p>
 * This class is a JavaFX-based version of the original AWT implementation,
 * utilizing {@link javafx.scene.image.Image} for sprite frames and
 * {@link javafx.scene.canvas.GraphicsContext} for rendering.
 * </p>
 */
public class Large extends Item {

    /** Reference to the game instance managing global state. */
    private Game stdGame;

    /** The main handler managing all entities in the scene. */
    private StandardHandler stdHandler;

    private static final double FALL_SPEED = 2.0;
    private static final Image SPRITE =
            new Image(Large.class.getResourceAsStream("/entities/PowerUp/large.png"));

    /**
     * Constructs a {@code Large} power-up item at the specified position.
     *
     * @param x          the x-coordinate of the item
     * @param y          the y-coordinate of the item
     * @param stdGame    reference to the main {@link Game} instance
     * @param stdHandler the global {@link StandardHandler} managing all entities
     */
    public Large(int x, int y, Game stdGame, StandardHandler stdHandler) {
        super(x, y);
        this.setId(StandardID.Large);

        this.stdGame = stdGame;
        this.stdHandler = stdHandler;
        this.setVelY(FALL_SPEED);
    }

    /**
     * Updates the item each frame.
     * <p>
     * Handles falling motion, collision detection with the player,
     * and removal when out of bounds.
     * </p>
     */
    @Override
    public void tick() {
        if (this.getY() > 800) {
            for (int i = 0; i < 100; i++) {
                this.stdHandler.addEntity(new BasicParticle(
                        (int) this.getX() + (this.getWidth() / 2),
                        (int) this.getY(),
                        200f,
                        this.stdHandler,
                        StandardDraw.CARDINAL_RED,
                        0.0));
            }

            this.stdHandler.removeEntity(this);
            this.setAlive(false);
            return;
        }

        if (this.isAlive()) {
            for (int i = 0; i < stdHandler.size(); i++) {
                if (this.stdHandler.get(i).getId() == StandardID.Player &&
                        this.getBounds().intersects(this.stdHandler.get(i).getBounds())) {

                    Paddle player = (Paddle) this.stdHandler.get(i);
                    applyEffect(player);

                    this.stdHandler.removeEntity(this);
                    break;
                }
            }

            this.setY(this.getY() + this.getVelY());
        }
    }

    /**
     * Renders the item to the screen using the provided JavaFX {@link GraphicsContext}.
     *
     * @param gc the graphics context used to draw this item
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(SPRITE, this.getX(), this.getY());
    }

    @Override
    public void applyEffect(Paddle paddle) {
        paddle.setLarge(true);
    }
}

