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

    /** The current rotation angle of the trail. */
    private double angle = 0.0;

    /** The current transparency value (1.0 = fully opaque, 0.0 = invisible). */
    private double alpha = 1.0;

    /** The lifespan of the trail (lower = faster fade). */
    private double life = -1.0;

    /** Determines if this trail should render as an image instead of a shape. */
    private boolean isImage = false;

    /** The color of the trail when rendering shapes. */
    private Color color;

    /** The shape type of the trail. */
    private ShapeType shape;

    /** The {@link StandardGameObject} this trail is attached to. */
    private final StandardGameObject obj;

    /** Reference to the {@link StandardHandler} responsible for managing entities. */
    private final StandardHandler stdHandler;

    public StandardTrail(double x, double y, double width, double height, double angle, double life, Color color,
                         StandardGameObject obj, StandardHandler stdHandler, ShapeType shape) {
        super(x, y, (int) width, (int) height, StandardID.Trail);
        this.color = color;
        this.life = life;
        this.shape = shape;
        this.angle = angle;
        this.stdHandler = stdHandler;
        this.isImage = false;
        this.obj = obj;

        this.checkNullShape();
    }

    public StandardTrail(double x, double y, double angle, double life, StandardGameObject obj,
                         StandardHandler stdHandler) {
        super(x, y, StandardID.Trail);

        this.obj = obj;
        this.stdHandler = stdHandler;
        this.angle = angle;
        this.life = life;
        this.isImage = true;

        Image sprite = this.obj.getCurrentSprite();
        this.setWidth((int) sprite.getWidth());
        this.setHeight((int) sprite.getHeight());
    }

    /**
     * Updates the state of the trail, reducing its transparency over time.
     * When the alpha value drops below the life threshold, the trail is removed.
     */
    @Override
    public void tick() {
        if (this.alpha > this.life) {
            this.alpha -= this.life;
        } else {
            this.stdHandler.removeEntity(this);
        }
    }

    /**
     * Renders the trail using the provided {@link GraphicsContext}.
     * Depending on {@code isImage}, this method draws either a shape
     * or the object's sprite with transparency applied.
     *
     * @param gc The {@link GraphicsContext} used for rendering.
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.save();
        gc.setGlobalAlpha(this.alpha);

        if (!this.isImage && this.shape != null) {
            gc.setFill(this.color);
            if (this.shape == ShapeType.CIRCLE) {
                gc.fillOval(getX(), getY(), getWidth(), getHeight());
            } else {
                gc.fillRect(getX(), getY(), getWidth(), getHeight());
            }
        } else {
            gc.drawImage(this.obj.getCurrentSprite(), getX(), getY());
        }

        gc.restore();
    }

    /**
     * Ensures that the shape is valid. If the shape is null and the trail
     * is not image-based, defaults to {@code ShapeType.RECTANGLE}.
     */
    private void checkNullShape() {
        if (this.shape == null && !this.isImage) {
            System.err.println("Shape is NULL in a Trail. Defaulting to type RECTANGLE.");
            this.shape = ShapeType.RECTANGLE;
        }
    }

    /** @return the current alpha (transparency) value. */
    public double getAlpha() {
        return this.alpha;
    }

    /** @param alpha sets the current alpha value. */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    /** @return the trail's lifespan factor. */
    public double getLife() {
        return this.life;
    }

    /** @param life sets how fast the trail fades away. */
    public void setLife(double life) {
        this.life = life;
    }

    /** @return the trail color when rendering shapes. */
    public Color getColor() {
        return this.color;
    }

    /** @param color sets the trail's color. */
    public void setColor(Color color) {
        this.color = color;
    }

    /** @return the shape type of the trail. */
    public ShapeType getShape() {
        return this.shape;
    }

    /** @param shape sets the shape type of the trail. */
    public void setShape(ShapeType shape) {
        this.shape = shape;
    }

    /** @return true if the trail renders an image instead of a shape. */
    public boolean isImage() {
        return this.isImage;
    }

    /** @param isImage determines whether the trail is image-based. */
    public void setImage(boolean isImage) {
        this.isImage = isImage;
    }

    /** @return the current rotation angle of the trail. */
    public double getAngle() {
        return this.angle;
    }
}
