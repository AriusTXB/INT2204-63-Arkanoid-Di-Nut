package arkanoid.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import standards.handler.StandardHandler;
import standards.model.StandardParticle;

/**
 * {@code BasicParticle} is a simple, renderable particle implementation.
 * It fades or disappears after its lifetime expires.
 */
public class BasicParticle extends StandardParticle {

    public BasicParticle(double x, double y, double life, StandardHandler handler, Color color) {
        super(x, y, life, handler, color);
    }

    public BasicParticle(double x, double y, double life, StandardHandler handler, Color color, double rotationAngle) {
        super(x, y, life, handler, color, rotationAngle);
    }

    @Override
    public void tick() {
        // Simple lifetime logic (can be expanded with motion, fading, etc.)
        if (System.nanoTime() > getDeath()) {
            getHandler().removeEntity(this);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(getColor());
        gc.fillOval(getX(), getY(), 3, 3); // draw a small particle
    }
}
