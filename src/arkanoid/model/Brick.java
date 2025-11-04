package arkanoid.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import standards.model.StandardGameObject;
import standards.model.StandardID;

import java.util.Objects;

/**
 * Represents a single brick in the Arkanoid game.
 *
 * Each brick has a color and corresponding sprite image.
 * Bricks are static obstacles that can be destroyed when hit by the ball.
 */
public class Brick extends StandardGameObject {

    /** Brick color used for rendering and sprite lookup. */
    private final Color color;

    /** Brick texture image. */
    protected Image sprite;

    /** Brick width and height. */
    private static final int WIDTH = 60;
    private static final int HEIGHT = 20;

    /**
     * Creates a new Brick at a specified position and color.
     *
     * @param x      X-coordinate position of the brick
     * @param y      Y-coordinate position of the brick
     * @param color  JavaFX {@link Color} of the brick
     */
    public Brick(double x, double y, Color color) {
        super(x, y, WIDTH, HEIGHT);
        this.setId(StandardID.Brick);
        this.color = color;
        loadSprite();
    }

    /**
     * Loads the sprite image based on the brick’s color.
     */
    private void loadSprite() {
        String colorName = getColorName(this.color);
        if (colorName == null) return;

        String path = "/entities/Brick/" + colorName + "brick.png";
        try {
            this.sprite = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        } catch (Exception e) {
            System.err.println("Failed to load brick sprite: " + path);
        }
    }

    /**
     * Bricks are static objects, so this method is currently unused.
     */
    @Override
    public void tick() {
        // Static object – no movement.
    }

    /**
     * Renders the brick on the JavaFX canvas.
     */
    @Override
    public void render(GraphicsContext gc) {
        if (sprite == null || color == null) return;

        // Draw semi-transparent shadow
        gc.setFill(new Color(0, 0, 0, 0.5));
        gc.fillRect(this.getX() + this.getWidth() / 3.0,
                this.getY() + this.getHeight() / 2.0,
                this.getWidth(),
                this.getHeight());

        // Draw brick image
        gc.drawImage(sprite, this.getX(), this.getY(), WIDTH, HEIGHT);
    }

    /**
     * Maps a {@link Color} to its corresponding name used in the sprite path.
     */
    private String getColorName(Color c) {
        if (c.equals(Color.RED)) return "red";
        if (c.equals(Color.BLUE)) return "blue";
        if (c.equals(Color.GREEN)) return "green";
        if (c.equals(Color.ORANGE)) return "orange";
        if (c.equals(Color.PINK)) return "pink";
        if (c.equals(Color.YELLOW)) return "yellow";
        if (c.equals(Color.MAGENTA) || c.equals(Color.PURPLE)) return "purple";
        if (c.equals(Color.GRAY)) return "gray";
        return null;
    }

    /**
     * Returns the brick’s color.
     */
    public Color getColor() {
        return this.color;
    }

    @Override
    public String toString() {
        return String.format("Brick[x=%.1f, y=%.1f, w=%d, h=%d, color=%s]",
                this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.color);
    }
}
