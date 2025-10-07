package standards.view;

import javafx.scene.canvas.GraphicsContext;

/**
 * {@code Renderable} defines an interface for objects that can be drawn on a JavaFX
 * rendering surface using a {@link GraphicsContext}.
 * <p>
 * Implementing classes may include visual elements such as text, images, shapes,
 * or custom graphics. This interface abstracts the rendering process to provide
 * a unified way to draw components in a JavaFX application.
 * </p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * public class Player implements Renderable {
 *     private double x, y;
 *     private Image sprite;
 *
 *     @Override
 *     public void render(GraphicsContext gc) {
 *         gc.drawImage(sprite, x, y);
 *     }
 * }
 * }</pre>
 *  Modified from Joshua Crotts & Andrew Matzureff's Standards Java Game Library Source Code
 */
public interface Renderable {

    /**
     * Renders the implementing object using the provided {@link GraphicsContext}.
     * <p>
     * This method is called whenever the scene or canvas needs to be updated,
     * typically from within an animation loop or a rendering manager.
     * </p>
     *
     * @param gc the {@code GraphicsContext} to draw onto. Must not be {@code null}.
     */
    void render(GraphicsContext gc);
}
