package arkanoid.model;

import arkanoid.controller.Game;
import arkanoid.model.item.Large;
import arkanoid.model.item.Multi;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import standards.main.StandardDraw;
import standards.controller.StandardFadeController;
import standards.model.StandardGameObject;
import standards.model.StandardID;
import standards.util.StdOps;
import standards.handler.StandardHandler;

public class Ball extends StandardGameObject {

    /** Fade controller for dynamic color blending during motion. */
    private final StandardFadeController stdFade =
            new StandardFadeController(StandardDraw.YELLOW, StandardDraw.RUSTY_RED, .008);

    /** Left screen boundary for collision detection. */
    private final double LEFT_BORDER = 20;

    /** Right screen boundary offset. */
    private final double RIGHT_BORDER = 20;

    /** Scene dimensions for boundary control. */
    private double sceneWidth = 800;
    private double sceneHeight = 600;

    /** Indicates whether the ball is active or lost. */
    private boolean isAlive = true;

    /** Velocity of the ball in the x and y directions */
    private double speedX, speedY;

    private StandardHandler handler;
    private Game game;
    private SongBox songBox;

    /**
     * Constructs a new Ball object at a given position and difficulty level.
     */
    public Ball(double x, double y, int difficulty, StandardHandler handler, Game game) {
        super(x, y, 15, 15);
        this.setId(StandardID.Enemy);
        this.handler = handler;
        this.game = game;
        setFixedVelocity(difficulty);  // Set velocity based on difficulty
    }

    /**
     * Sets a fixed velocity for the ball based on the current difficulty level.
     */
    private void setFixedVelocity(int difficulty) {
        double speed;
        switch (difficulty) {
            case 1 -> speed = 4;
            case 2 -> speed = 6;
            case 3 -> speed = 8;
            default -> speed = 5;
        }

        // Randomize direction on both axes (-1, 0, 1)
        int dirX = StdOps.randomInt(-1, 1);
        int dirY = StdOps.randomInt(-1, 1);

        // Ensure the ball always moves
        if (dirX == 0) dirX = 1;
        if (dirY == 0) dirY = -1;

        this.setVelX(speed * dirX);
        this.setVelY(speed * dirY);
    }

    /**
     * Updates the ball’s position and handles wall collisions.
     */
    public void tick() {
        if (!isAlive) return;

        // Horizontal wall collisions
        if (this.getX() <= LEFT_BORDER || this.getX() >= sceneWidth - this.getWidth() - RIGHT_BORDER)
            this.setVelX(-this.getVelX()); // Reverse direction

        // Top wall collision
        if (this.getY() <= LEFT_BORDER)
            this.setVelY(-this.getVelY());  // Reverse direction

        // Fell below screen -> lose a life
        if (this.getY() > sceneHeight) {
            this.isAlive = false;
            return;
        }

        // Update position based on velocity
        this.setX(this.getX() + this.getVelX());
        this.setY(this.getY() + this.getVelY());
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isAlive) return;
        gc.setFill(stdFade.combine());
        gc.fillOval(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public void handleCollisions(StandardHandler handler) {
        for (int i = 0; i < handler.getEntities().size(); i++) {
            StandardGameObject obj = handler.getEntities().get(i);

            if (obj == this) continue;

            if (!this.getBounds().intersects(obj.getBounds())) continue;

            // BALL - PADDLE
            if (obj.getId() == StandardID.Player) {
                double ballCenter = this.getX() + this.getWidth() / 2.0;
                double paddleCenter = obj.getX() + obj.getWidth() / 2.0;
                double relativeIntersect = (ballCenter - paddleCenter) / (obj.getWidth() / 2.0);

                relativeIntersect = Math.max(-1.0, Math.min(1.0, relativeIntersect));

                double maxAngle = Math.toRadians(60);
                double bounceAngle = relativeIntersect * maxAngle;

                double speed = Math.sqrt(getVelX() * getVelX() + getVelY() * getVelY());
                setVelX(speed * Math.sin(bounceAngle));
                setVelY(-Math.abs(speed * Math.cos(bounceAngle)));

                setY(obj.getY() - getHeight() - 1);
            }

            // BALL - BRICK
            else if (obj.getId() == StandardID.Brick) {
                double ballLeft = this.getX();
                double ballRight = this.getX() + this.getWidth();
                double ballTop = this.getY();
                double ballBottom = this.getY() + this.getHeight();

                double brickLeft = obj.getX();
                double brickRight = obj.getX() + obj.getWidth();
                double brickTop = obj.getY();
                double brickBottom = obj.getY() + obj.getHeight();

                double overlapLeft = ballRight - brickLeft;
                double overlapRight = brickRight - ballLeft;
                double overlapTop = ballBottom - brickTop;
                double overlapBottom = brickBottom - ballTop;

                double minOverlapX = Math.min(overlapLeft, overlapRight);
                double minOverlapY = Math.min(overlapTop, overlapBottom);

                if (minOverlapX < minOverlapY) {
                    if (overlapLeft < overlapRight)
                        setX(brickLeft - getWidth() - 0.5);
                    else
                        setX(brickRight + 0.5);

                    setVelX(-getVelX());
                } else {
                    if (overlapTop < overlapBottom)
                        setY(brickTop - getHeight() - 0.5);
                    else
                        setY(brickBottom + 0.5);

                    setVelY(-getVelY());
                }

                handler.removeEntity(obj);

                SongBox songBox = new SongBox();
                songBox.playExplode();

                spawnRandomItem((Brick)obj);

                break;
            }
        }
    }

    private void spawnRandomItem(Brick brick) {
        if (Math.random() < 0.3) {
            double x = brick.getX() + brick.getWidth() / 2.0;
            double y = brick.getY() + brick.getHeight() / 2.0;

            if (Math.random() < 0.5) {
                handler.addEntity(new Large((int)x, (int)y, game, handler));
            } else {
                handler.addEntity(new Multi(x, y, game, handler));
            }
        }
    }


    public Color getCurrentColor() {
        return stdFade.combine();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setSceneSize(double width, double height) {
        this.sceneWidth = width;
        this.sceneHeight = height;
    }

    public void resetVelocity(int difficulty) {
        setFixedVelocity(difficulty);
    }
}
