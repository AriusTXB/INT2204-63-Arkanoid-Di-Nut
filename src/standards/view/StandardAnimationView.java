package standards.view;

import standards.model.StandardGameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;

/**
 * {@code StandardAnimationView} is responsible for rendering animated
 * {@link StandardGameObject} instances in a JavaFX environment.
 * This class acts as the "View" component
 * in the MVC pattern, separating rendering logic from the animation model.
 *
 * Modified from Joshua Crotts & Andrew Matzureff's Standards Java Game Library Source Code
 */
public class StandardAnimationView implements Renderable {

    /** Array of frames that make up the animation sequence. */
    private final Image[] animationFrames;

    /** Parent game object to which this animation belongs. */
    private final StandardGameObject obj;

    /** The currently active frame being rendered. */
    private Image currentFrame;

    /**
     * Constructs a new {@code StandardAnimationView}.
     *
     * @param frames array of {@link Image} objects representing the animation frames.
     * @param obj    the parent {@link StandardGameObject} instance associated with this animation.
     */
    public StandardAnimationView(Image[] frames, StandardGameObject obj) {
        this.animationFrames = frames;
        this.obj = obj;
    }

    /**
     * Renders the current animation frame at the parent object's position.
     *
     * @param gc the {@link GraphicsContext} to draw on.
     */
    @Override
    public void render(GraphicsContext gc) {
        if (currentFrame == null) {
            return;
        }
        gc.drawImage(currentFrame, obj.getX(), obj.getY());
    }

    /**
     * Renders the current animation frame at the parent object's position
     * with a specified rotation.
     *
     * @param gc    the {@link GraphicsContext} to draw on.
     * @param theta the rotation angle in degrees.
     */
    public void render(GraphicsContext gc, double theta) {
        if (currentFrame == null) {
            return;
        }

        double x = obj.getX();
        double y = obj.getY();
        double width = currentFrame.getWidth();
        double height = currentFrame.getHeight();

        gc.save();
        gc.transform(new Affine(
                new javafx.scene.transform.Rotate(theta, x + width / 2.0, y + height / 2.0)
        ));
        gc.drawImage(currentFrame, x, y);
        gc.restore();
    }

    /**
     * Renders the current animation frame at a custom (x, y) position
     * with a specified rotation.
     *
     * @param gc    the {@link GraphicsContext} to draw on.
     * @param x     the x-coordinate to draw at.
     * @param y     the y-coordinate to draw at.
     * @param theta the rotation angle in degrees.
     */
    public void render(GraphicsContext gc, double x, double y, double theta) {
        if (currentFrame == null) {
            return;
        }

        double width = currentFrame.getWidth();
        double height = currentFrame.getHeight();

        gc.save();
        gc.transform(new Affine(
                new javafx.scene.transform.Rotate(theta, x + width / 2.0, y + height / 2.0)
        ));
        gc.drawImage(currentFrame, x, y);
        gc.restore();
    }

    /**
     * Renders the current animation frame at a specific position and size
     * with a rotation transformation.
     *
     * @param gc     the {@link GraphicsContext} to draw on.
     * @param x      the x-coordinate to draw at.
     * @param y      the y-coordinate to draw at.
     * @param width  the width to scale the frame to.
     * @param height the height to scale the frame to.
     * @param theta  the rotation angle in degrees.
     */
    public void render(GraphicsContext gc, double x, double y, double width, double height, double theta) {
        if (currentFrame == null) {
            return;
        }

        gc.save();
        gc.transform(new Affine(
                new javafx.scene.transform.Rotate(theta, x + width / 2.0, y + height / 2.0)
        ));
        gc.drawImage(currentFrame, x, y, width, height);
        gc.restore();
    }

    /**
     * Returns the array of all animation frames.
     *
     * @return an array of {@link Image} objects representing the animation frames.
     */
    public Image[] getFrames() {
        return this.animationFrames;
    }

    /**
     * Returns the currently active animation frame.
     *
     * @return the current {@link Image} frame being rendered.
     */
    public Image getCurrentFrame() {
        return this.currentFrame;
    }

    /**
     * Sets the current frame index to be displayed.
     *
     * @param frame the index of the animation frame in {@link #animationFrames}.
     * Must be within array bounds.
     */
    public void setCurrentFrameIndex(int frame) {
        if (frame >= 0 && frame < this.animationFrames.length) {
            this.currentFrame = this.animationFrames[frame];
        }
    }
}
