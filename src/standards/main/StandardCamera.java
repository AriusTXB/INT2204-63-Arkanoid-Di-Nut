package standards.main;

import standards.model.StandardGameObject;
import standards.model.StandardID;
import standards.platform.StandardGame;
import javafx.scene.canvas.GraphicsContext;

/**
 * Represents a camera in the game world that follows a target
 * {@link StandardGameObject} and controls the visible region of the game.
 *
 * <p>
 * The camera's position interpolates toward its subject (the object being
 * followed) with a configurable smoothing factor {@code snap}.
 * </p>
 *
 * Modified from Joshua Crotts & Andrew Matzureff's Standards Java Game Library Source Code
 */
public class StandardCamera extends StandardGameObject {

    /** The object the camera follows. */
    private StandardGameObject subject;

    /** The main game instance. */
    private StandardGame sg;

    /** How quickly the camera "snaps" to the target (0 < snap ≤ 1). */
    private double snap = 1.0D;

    /** Half of the viewport width and height. */
    private int vpw = 0, vph = 0;

    /** Viewport bounds restrictions. */
    private int maxX, maxY = this.maxX = Integer.MAX_VALUE;
    private int minX, minY = this.minX = Integer.MIN_VALUE;

    /** The visible area bounds around the subject. */
    private int objectMinXBounds, objectMaxXBounds;
    private int objectMinYBounds, objectMaxYBounds;

    /** Controls how far beyond the viewport objects remain active. */
    private final double VIEW_PORT_FACTOR = 1.0;

    /**
     * Constructs a new camera.
     *
     * @param sg   the game instance
     * @param sgo  the object to follow
     * @param snap how tightly the camera follows (higher = faster)
     * @param vpw  viewport width
     * @param vph  viewport height
     */
    public StandardCamera(StandardGame sg, StandardGameObject sgo, double snap, int vpw, int vph) {
        super(sgo.getX(), sgo.getY(), StandardID.Camera);
        this.vpw = vpw >> 1;
        this.vph = vph >> 1;
        this.subject = sgo;
        this.snap = snap;
        this.sg = sg;
        this.setObjectBounds();
    }

    /**
     * Constrains the camera movement to a rectangular area.
     *
     * @param maxx right bound
     * @param maxy bottom bound
     * @param minx left bound
     * @param miny top bound
     */
    public void restrict(int maxx, int maxy, int minx, int miny) {
        this.maxX = maxx;
        this.maxY = maxy;
        this.minX = minx;
        this.minY = miny;
    }

    /** Updates the camera position smoothly toward the subject. */
    @Override
    public void tick() {
        this.tickX();
        this.tickY();
        this.setObjectBounds();
    }

    /**
     * Applies the camera translation to the provided {@link GraphicsContext}.
     * <p>
     * This method should be called <b>before</b> rendering game objects.
     * </p>
     *
     * @param gc the JavaFX {@code GraphicsContext}
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.translate(-this.getX() + this.vpw, -this.getY() + this.vph);
    }

    /** Updates the camera’s horizontal position. */
    private void tickX() {
        double location = this.subject.getX();
        if (location > this.maxX) {
            location = this.maxX;
        } else if (location < this.minX) {
            location = this.minX;
        }
        this.setVelX((location - this.getX()) * this.snap);
        this.setX(this.getX() + this.getVelX());
    }

    /** Updates the camera’s vertical position. */
    private void tickY() {
        double location = this.subject.getY();
        if (location > this.maxY) {
            location = this.maxY;
        } else if (location < this.minY) {
            location = this.minY;
        }
        this.setVelY((location - this.getY()) * this.snap);
        this.setY(this.getY() + this.getVelY());
    }

    /** Updates the bounding box area around the subject. */
    private void setObjectBounds() {
        this.objectMinXBounds = (int) ((this.subject.getX() - this.sg.getGameWidth()) * VIEW_PORT_FACTOR);
        this.objectMaxXBounds = (int) ((this.subject.getX() + this.sg.getGameWidth()) * VIEW_PORT_FACTOR);
        this.objectMinYBounds = (int) ((this.subject.getY() - this.sg.getGameHeight()) * VIEW_PORT_FACTOR);
        this.objectMaxYBounds = (int) ((this.subject.getY() + this.sg.getGameHeight()) * VIEW_PORT_FACTOR);
    }

    /**
     * Determines whether another {@link StandardGameObject} is within the camera's
     * visible bounds.
     *
     * @param other the object to check
     * @return {@code true} if within bounds, {@code false} otherwise
     */
    public boolean SGOInBounds(StandardGameObject other) {
        return ((other.getX() > this.objectMinXBounds) && (other.getX() < this.objectMaxXBounds))
                && ((other.getY() > this.objectMinYBounds) && (other.getY() < this.objectMaxYBounds));
    }

    // -------------------------------
    // Getters and Setters
    // -------------------------------
    public StandardGameObject getSubject() { return subject; }
    public void setSubject(StandardGameObject subject) { this.subject = subject; }

    public double getSnap() { return snap; }
    public void setSnap(double snap) { this.snap = snap; }

    public int getVpw() { return vpw; }
    public void setVpw(int vpw) { this.vpw = vpw; }

    public int getVph() { return vph; }
    public void setVph(int vph) { this.vph = vph; }

    public int getMaxX() { return maxX; }
    public void setMaxX(int maxX) { this.maxX = maxX; }

    public int getMaxY() { return maxY; }
    public void setMaxY(int maxY) { this.maxY = maxY; }

    public int getMinX() { return minX; }
    public void setMinX(int minX) { this.minX = minX; }

    public int getMinY() { return minY; }
    public void setMinY(int minY) { this.minY = minY; }
}
