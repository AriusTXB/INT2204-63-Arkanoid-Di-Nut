package arkanoid.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import standards.main.StandardDraw;
import standards.controller.StandardFadeController;
import standards.model.StandardGameObject;
import standards.model.StandardID;

public class Paddle extends StandardGameObject {

    private final double LEFT_BORDER = 20;
    private final double RIGHT_BORDER = 30;
    private final double NORMAL_WIDTH = 100;
    private final double NORMAL_HEIGHT = 10;

    private final Color normal = StandardDraw.RED;
    private final StandardFadeController largeFade =
            new StandardFadeController(StandardDraw.MELON, StandardDraw.VIOLET, 0.05);

    private boolean isLarge = false;
    private int timer = 500;

    private double sceneWidth = 800;

    public Paddle(double x, double y) {
        super(x, y, StandardID.Player);
        this.setWidth((int) NORMAL_WIDTH);
        this.setHeight((int) NORMAL_HEIGHT);
    }

    public void tick() {
        // Power-up check
        if (isLarge) {
            largeFade.combine();
            timer--;
            if (timer <= 0) {
                isLarge = false;
                this.setWidth((int) NORMAL_WIDTH);
                timer = 500;
            }
        }

        // Border check
        if (this.getX() <= LEFT_BORDER)
            this.setX((int) LEFT_BORDER);
        if (this.getX() >= sceneWidth - this.getWidth() - RIGHT_BORDER)
            this.setX((int) (sceneWidth - this.getWidth() - RIGHT_BORDER));

        // Update position
        this.setX(this.getX() + this.getVelX());
        this.setY(this.getY() + this.getVelY());
    }

    public void render(GraphicsContext gc) {
        if (isLarge)
            gc.setFill(largeFade.combine());
        else
            gc.setFill(normal);

        gc.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public void moveLeft() { this.setVelX(-5); }
    public void moveRight() { this.setVelX(5); }
    public void stop() { this.setVelX(0); }

    public void setLarge(boolean isLarge) {
        this.isLarge = isLarge;
        if (isLarge) {
            this.setWidth(160);
            this.timer = 500;
        }
    }

    public boolean isLarge() { return this.isLarge; }

    public void setSceneWidth(double sceneWidth) { this.sceneWidth = sceneWidth; }
}
