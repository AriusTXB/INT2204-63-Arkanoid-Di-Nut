package standards.model;

import standards.handler.StandardHandler;
import standards.view.ShapeType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * The {@code StandardTrail} class creates a fading visual trail behind
 * a {@link StandardGameObject}. It can either render a simple geometric
 * shape (circle or rectangle) or a translucent copy of the object's sprite.
 * <p>
 * The trail fades based on its {@code life} and {@code alpha} values, giving
 * the illusion of motion blur or speed trails.
 * </p>
 *
 * Modified from Joshua Crotts & Andrew Matzureff's Standards Java Game Library Source Code
 */
public class StandardTrail extends StandardGameObject {

    private double alpha;
    private double life;
    private Color color;
    private ShapeType shape;
    private final StandardHandler stdHandler;
    private boolean active = false; // Is the trail currently being used?

    // Constructor for pooling. Initial values don't matter much.
    public StandardTrail(StandardHandler stdHandler) {
        super(0, 0, 0, 0, StandardID.Trail);
        this.stdHandler = stdHandler;
        this.shape = ShapeType.CIRCLE;
    }

    /**
     * Resets the trail to a new position and state, making it active.
     * This is the core of the object pooling pattern.
     */
    public void reset(double x, double y, double width, double height, Color color, double life) {
        this.setX(x);
        this.setY(y);
        this.setWidth((int) width);
        this.setHeight((int) height);
        this.setColor(color);
        this.setLife(life);
        this.setAlpha(1.0); // Start fully opaque
        this.setActive(true);
    }

    /**
     * Updates the state of the trail, reducing its transparency over time.
     * When the alpha value drops below the life threshold, the trail is removed.
     */
    @Override
    public void tick() {
        if (!active) return; // Don't update if inactive

        if (this.alpha > this.life) {
            this.alpha -= this.life;
        } else {
            this.active = false; // Instead of removing, just deactivate it.
        }
    }

    /**
     * Renders the trail using the provided {@link GraphicsContext}.
     * Depending on {@code isImage}, this method draws either a shape
     * or the object's sprite with transparency applied.
     */
    @Override
    public void render(GraphicsContext gc) {
        if (!active) return; // Don't draw if inactive

        gc.save();
        gc.setGlobalAlpha(this.alpha);
        gc.setFill(this.color);
        if (this.shape == ShapeType.CIRCLE) {
            gc.fillOval(getX(), getY(), getWidth(), getHeight());
        } else {
            gc.fillRect(getX(), getY(), getWidth(), getHeight());
        }
        gc.restore();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setLife(double life) {
        this.life = life;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
