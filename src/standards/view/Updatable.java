package standards.view;

/**
 * {@code Updatable} defines an interface for objects that require regular updates
 * over time — for example, in animation loops, simulations, or game logic systems.
 * <p>
 * Classes implementing this interface typically modify their internal state
 * (such as position, velocity, or animation frames) each time the {@link #tick()}
 * method is called.
 * </p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * public class Player implements Updatable {
 *     private double x, y, velocityX;
 *
 *     @Override
 *     public void tick() {
 *         // Update position
 *         x += velocityX;
 *     }
 * }
 * }</pre>
 *
 * <p>In a JavaFX application, {@code tick()} is often called within an
 * {@link javafx.animation.AnimationTimer} or scheduled loop.</p>
 *
 * @see javafx.animation.AnimationTimer
 */
public interface Updatable {

    /**
     * Updates the state of the implementing object.
     * <p>
     * This method should be called periodically — for example, once per frame
     * in a rendering loop or at a fixed interval in a simulation.
     * </p>
     */
    void tick();
}
