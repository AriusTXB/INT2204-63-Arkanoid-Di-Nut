package standards.model;

import standards.controller.StandardAnimatorController;
import standards.util.StdOps;
import standards.view.Renderable;
import standards.view.Updatable;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The {@code StandardGameObject} class is an abstract representation of a
 * drawable and updatable object within a JavaFX-based game engine.
 * <p>
 * It defines position, velocity, dimension, state, and rendering properties,
 * allowing subclasses to focus on implementing specific logic and drawing
 * behavior.
 * <p>
 * This class replaces the AWT-based implementation with pure JavaFX components.
 */
public abstract class StandardGameObject implements Renderable, Updatable {

    // === Position ===
    private double x = 0;
    private double y = 0;

    // === Velocity ===
    private double velX;
    private double velY;

    // === Dimensions ===
    private int width;
    private int height;

    // === State ===
    private boolean alive = true;
    private boolean interactable = false;
    private long death = 0L;

    // === Identifier ===
    private StandardID id;

    // === Animation controller ===
    private StandardAnimatorController activeAnimation;

    // === Image sprite ===
    private String fileLocation;
    private Image currentSprite;

    // === Collision bounds ===
    private Rectangle2D bounds;

    // ==================== Constructors ====================

    public StandardGameObject() {}

    public StandardGameObject(double x, double y, StandardID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public StandardGameObject(double x, double y, StandardID id, boolean interactable) {
        this(x, y, id);
        this.interactable = interactable;
    }

    public StandardGameObject(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public StandardGameObject(double x, double y, int width, int height, StandardID id) {
        this(x, y, width, height);
        this.id = id;
    }

    public StandardGameObject(double x, double y, int width, int height, StandardID id, boolean interactable) {
        this(x, y, width, height, id);
        this.interactable = interactable;
    }

    public StandardGameObject(double x, double y, String fileLocation) {
        this.x = x;
        this.y = y;
        this.fileLocation = fileLocation;
        this.currentSprite = StdOps.loadImage(this.fileLocation);
        this.width = (int) this.currentSprite.getWidth();
        this.height = (int) this.currentSprite.getHeight();
    }

    public StandardGameObject(double x, double y, String fileLocation, boolean interactable) {
        this(x, y, fileLocation);
        this.interactable = interactable;
    }

    public StandardGameObject(double x, double y, String fileLocation, StandardID id) {
        this(x, y, fileLocation);
        this.id = id;
    }

    public StandardGameObject(double x, double y, String fileLocation, StandardID id, boolean interactable) {
        this(x, y, fileLocation, id);
        this.interactable = interactable;
    }

    public StandardGameObject(double x, double y, Image image) {
        this.x = x;
        this.y = y;
        this.currentSprite = image;
        this.width = (int) image.getWidth();
        this.height = (int) image.getHeight();
    }

    public StandardGameObject(double x, double y, Image image, StandardID id) {
        this(x, y, image);
        this.id = id;
    }

    public StandardGameObject(double x, double y, Image image, StandardID id, boolean interactable) {
        this(x, y, image, id);
        this.interactable = interactable;
    }

    // ==================== Abstract Methods ====================

    /**
     * Updates the object’s physics or logic. Called once per frame by the game loop.
     * Implement this method in your subclass to define update behavior.
     */
    @Override
    public abstract void tick();

    /**
     * Renders the object using JavaFX’s {@link GraphicsContext}.
     * Implement this method in your subclass to define drawing behavior.
     *
     * @param gc the {@link GraphicsContext} used for rendering
     */
    @Override
    public abstract void render(GraphicsContext gc);

    // ==================== Utility Methods ====================

    /**
     * Updates the position of the object based on its velocity.
     * Equivalent to {@code x += velX; y += velY}.
     */
    public void updatePosition() {
        this.x += this.velX;
        this.y += this.velY;
    }

    // ==================== Accessors ====================

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public double getVelX() { return velX; }
    public void setVelX(double velX) { this.velX = velX; }

    public double getVelY() { return velY; }
    public void setVelY(double velY) { this.velY = velY; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public String getFileLocation() { return fileLocation; }
    public void setFileLocation(String fileLocation) { this.fileLocation = fileLocation; }

    public Image getCurrentSprite() { return currentSprite; }
    public void setCurrentSprite(Image currentSprite) { this.currentSprite = currentSprite; }

    public StandardID getId() { return id; }
    public void setId(StandardID id) { this.id = id; }

    public boolean isAlive() { return alive; }
    public void setAlive(boolean alive) { this.alive = alive; }

    public boolean isInteractable() { return interactable; }
    public void setInteractable(boolean interactable) { this.interactable = interactable; }

    public long getDeath() { return death; }
    public void setDeath(long death) { this.death = death; }

    public StandardAnimatorController getAnimationController() { return activeAnimation; }
    public void setAnimation(StandardAnimatorController animation) { this.activeAnimation = animation; }

    public double getRestitution() { return 1.0; }

    // ==================== Bounds ====================

    /**
     * Returns a {@link Rectangle2D} representing the collision area of this object.
     *
     * @return the object’s current bounding box
     */
    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, width, height);
    }

    public Rectangle2D getBounds(double offsetX, double offsetY, double offsetW, double offsetH) {
        return new Rectangle2D(x + offsetX, y + offsetY, width + offsetW, height + offsetH);
    }

    public Rectangle2D getLeftBounds() {
        return new Rectangle2D(x, y, 1, height);
    }

    public Rectangle2D getRightBounds() {
        return new Rectangle2D(x + width, y, 1, height);
    }

    public Rectangle2D getTopBounds() {
        return new Rectangle2D(x, y, width, 3);
    }

    public Rectangle2D getBottomBounds() {
        return new Rectangle2D(x, y + height, width, 1);
    }
}
