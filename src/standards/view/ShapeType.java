package standards.view;

/**
 * {@code ShapeType} defines basic geometric shape categories that can be used for
 * rendering or object representation within a JavaFX-based visualization or game engine.
 * <p>
 * Each value corresponds to a common 2D shape type that can be drawn using
 * JavaFX's {@link javafx.scene.canvas.GraphicsContext} or shape classes such as
 * {@link javafx.scene.shape.Circle} and {@link javafx.scene.shape.Rectangle}.
 * </p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * public void render(GraphicsContext gc, ShapeType shapeType) {
 *     gc.setFill(Color.BLUE);
 *     switch (shapeType) {
 *         case CIRCLE -> gc.fillOval(50, 50, 100, 100);
 *         case RECTANGLE -> gc.fillRect(50, 50, 100, 100);
 *     }
 * }
 * }</pre>
 *
 * @see javafx.scene.canvas.GraphicsContext
 * @see javafx.scene.shape.Circle
 * @see javafx.scene.shape.Rectangle
 */
public enum ShapeType {
    /**
     * Represents a circular shape.
     */
    CIRCLE,

    /**
     * Represents a rectangular shape.
     */
    RECTANGLE;
}
