package arkanoid.model.item;

import arkanoid.model.Paddle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import standards.handler.StandardHandler;
import standards.model.StandardGameObject;
import standards.model.StandardID;
import standards.util.StdOps;

import arkanoid.model.Ball;
import arkanoid.model.BasicParticle;
import arkanoid.controller.Game;

/**
 * {@code Multi} represents a collectible item that grants the player extra balls
 * when collected. It falls vertically, animates its sprite frames, and disappears
 * when reaching the bottom or being picked up.
 *
 * Refactored for JavaFX integration.
 */
public class Multi extends Item {

    private final Game stdGame;
    private final StandardHandler stdHandler;

    private static final double FALL_SPEED = 2.0;
    private static final Image SPRITE =
            new Image(Multi.class.getResourceAsStream("/entities/PowerUp/multi.png"));

    public Multi(double x, double y, Game stdGame, StandardHandler stdHandler) {
        super(x, y);
        this.setId(StandardID.Multi);

        this.stdGame = stdGame;
        this.stdHandler = stdHandler;

        this.setVelY(FALL_SPEED);
    }

    @Override
    public void tick() {
        if (this.getY() > 800) {
            for (int i = 0; i < 100; i++) {
                this.stdHandler.addEntity(new BasicParticle(
                        this.getX() + this.getWidth() / 2.0,
                        this.getY(),
                        200f,
                        stdHandler,
                        Color.MEDIUMPURPLE
                ));
            }

            this.stdHandler.removeEntity(this);
            this.setAlive(false);
            return;
        }

        if (this.isAlive()) {
            for (StandardGameObject obj : new java.util.ArrayList<>(stdHandler.getEntities())) {
                if (obj.getId() == StandardID.Player &&
                        this.getBounds().intersects(obj.getBounds())) {

                    Paddle player = (Paddle) obj;
                    applyEffect(player);

                    this.stdHandler.removeEntity(this);
                    return;
                }
            }

            this.setY(this.getY() + this.getVelY());
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(SPRITE, this.getX(), this.getY());
    }

    @Override
    public void applyEffect(Paddle paddle) {
        Ball refBall = stdGame.getBalls().get(0);
        double refSpeedX = refBall.getVelX();
        double refSpeedY = refBall.getVelY();

        for (int j = 0; j < 2; j++) {
            Ball newBall = new Ball(
                    StdOps.randomInt(300, 500),
                    StdOps.randomInt(200, 300),
                    stdGame.getDifficulty(),
                    stdHandler,
                    stdGame
            );

            newBall.setSceneSize(stdGame.getGameWidth(), stdGame.getGameHeight());
            stdHandler.addEntity(newBall);
            stdGame.addBall(newBall);
        }
    }


}
