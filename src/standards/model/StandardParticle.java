package standards.model;

import standards.handler.StandardHandler;
import javafx.scene.paint.Color;

/**
 * {@code StandardParticle} represents a basic particle object in the game engine.
 * It provides properties such as position, lifetime, color, and rotation angle.
 *
 * Modified from Joshua Crotts & Andrew Matzureff's Standards Java Game Library Source Code
 */
public abstract class StandardParticle extends StandardGameObject {

    /**
     * Color of the particle. Defaults to black if not specified.
     */
    private Color color = Color.BLACK;

    /**
     * Reference to the handler that manages this particle.
     */
    private StandardHandler handler;

    /**
     * Rotation angle for the particle’s orientation or movement.
     */
    private double rotationAngle;

    public StandardParticle(double x, double y, double life) {
        super((int) x, (int) y, StandardID.Particle);
        life *= 1.0E9D; // Convert seconds to nanoseconds
        this.setDeath(System.nanoTime() + (long) life);
    }

    public StandardParticle(double x, double y, StandardHandler handler) {
        super((int) x, (int) y, StandardID.Particle);
        this.handler = handler;
    }

    public StandardParticle(double x, double y, double life, StandardHandler handler) {
        this(x, y, handler);
        life *= 1.0E9D;
        this.setDeath(System.nanoTime() + (long) life);
    }

    public StandardParticle(double x, double y, double life, StandardHandler handler, Color color) {
        this(x, y, life, handler);
        this.color = color;
    }

    public StandardParticle(double x, double y, double life, StandardHandler handler, Color color, double rotationAngle) {
        this(x, y, life, handler, color);
        this.rotationAngle = rotationAngle;
    }

    /**
     * Returns the current rotation angle of the particle.
     *
     * @return the rotation angle in degrees.
     */
    public double getAngle() {
        return this.rotationAngle;
    }

    /**
     * Returns the handler managing this particle.
     *
     * @return the {@link StandardHandler} instance.
     */
    public StandardHandler getHandler() {
        return this.handler;
    }

    /**
     * Sets the color of this particle.
     *
     * @param color the new color.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Returns the color of this particle.
     *
     * @return the current {@link Color}.
     */
    public Color getColor() {
        return this.color;
    }
}
