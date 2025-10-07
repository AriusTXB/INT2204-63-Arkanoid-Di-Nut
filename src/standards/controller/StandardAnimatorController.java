package standards.controller;

import standards.model.StandardAnimation;
import standards.model.StandardGameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;

/**
 * Controller responsible for updating and rendering a {@link StandardAnimation}
 * model in the game loop. It communicates between the {@code StandardAnimation}
 * model and its {@code StandardAnimationView}.
 *
 * Modified from Joshua Crotts & Andrew Matzureff's Standards Java Game Library Source Code
 */
public class StandardAnimatorController {

    /**
     * The animation model this controller manipulates.
     */
    private final StandardAnimation animation;

    /**
     * Creates a new controller for the given {@link StandardAnimation}.
     *
     * @param animation the animation model to control
     */
    public StandardAnimatorController(StandardAnimation animation) {
        this.animation = animation;
        this.animation.getView().setCurrentFrameIndex(0);
    }

    /**
     * Updates the animation state.
     * <p>
     * Checks the time elapsed since the last frame update. If enough time has
     * passed, advances to the next frame and resets the timer.
     * </p>
     */
    public void tick() {
        if (System.nanoTime() > this.animation.getLastTime()
                + (1_000_000_000 / this.animation.getFrameSpeed())) {

            this.animation.advanceFrame();
            this.animation.setLastTime(System.nanoTime());
        }
    }

    /**
     * Renders the current frame of animation to the given JavaFX
     * {@link GraphicsContext}.
     *
     * @param gc the {@code GraphicsContext} used to draw the animation
     */
    public void renderFrame(GraphicsContext gc) {
        StandardGameObject sgo = this.animation.getStandardGameObject();
        double x = sgo.getX();
        double y = sgo.getY();
        double width = sgo.getWidth();
        double height = sgo.getHeight();
        double rotation = this.animation.getRotation();

        gc.save();

        // Apply rotation about the center of the sprite
        Affine transform = new Affine();
        transform.appendRotation(Math.toDegrees(rotation), x + width / 2.0, y + height / 2.0);
        gc.setTransform(transform);

        // Handle mirroring
        if (this.animation.isMirrored()) {
            gc.scale(-1, 1);
            gc.drawImage(this.animation.getView().getCurrentFrame(), -x - width, y, width, height);
        } else {
            gc.drawImage(this.animation.getView().getCurrentFrame(), x, y, width, height);
        }

        gc.restore();
    }

    /**
     * Stops the current animation and resets it to the first frame.
     */
    public void stopAnimation() {
        this.animation.stopAnimation();
    }

    /**
     * Returns the underlying animation model.
     *
     * @return the {@link StandardAnimation} controlled by this instance
     */
    public StandardAnimation getStandardAnimation() {
        return this.animation;
    }
}
