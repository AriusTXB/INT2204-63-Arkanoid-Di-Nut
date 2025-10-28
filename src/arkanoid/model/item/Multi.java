package arkanoid.model.item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import standards.handler.StandardHandler;
import standards.model.StandardAnimation;
import standards.model.StandardGameObject;
import standards.model.StandardID;
import standards.model.StandardParticle;
import standards.util.StdOps;

import arkanoid.model.Ball;
import arkanoid.model.BasicParticle;
import arkanoid.controller.Game;
import arkanoid.model.SongBox;

/**
 * {@code Multi} represents a collectible item that grants the player extra balls
 * when collected. It falls vertically, animates its sprite frames, and disappears
 * when reaching the bottom or being picked up.
 *
 * Refactored for JavaFX integration.
 */
public class Multi extends Item {

    private Game stdGame;
    private StandardHandler stdHandler;
    private SongBox songBox;

    private static final int MAX_FRAMES = 6;
    private static final double FALL_SPEED = 2.0;
    private static final double FPS = 120.0;

    public Multi(double x, double y, Game stdGame, StandardHandler stdHandler, SongBox songBox) {
        super(x, y);
        this.setId(StandardID.Multi);

        this.stdGame = stdGame;
        this.stdHandler = stdHandler;
        this.songBox = songBox;

        Image[] frames = new Image[MAX_FRAMES];
        for (int i = 0; i < MAX_FRAMES; i++) {
            frames[i] = new Image("file:Resources/Sprites/Items/Multi/multi" + i + ".png");
        }

        this.setVelY(FALL_SPEED);
        this.setAnimation(new StandardAnimation(this, frames, FPS));
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
            this.getAnimation().updateAnimation();

            if (this.getY() >= 650) {
                for (StandardGameObject obj : this.stdHandler.getEntities()) {
                    if (obj.getId() == StandardID.Player &&
                        this.getBounds().intersects(obj.getBounds())) {

                        songBox.playPowerUp();

                        int difficulty = stdGame.getDifficulty();
                        for (int j = 0; j < 2; j++) {
                            this.stdHandler.addEntity(new Ball(
                                    StdOps.randomInt(300, 500),
                                    StdOps.randomInt(200, 300),
                                    difficulty
                            ));
                        }

                        this.stdHandler.removeEntity(this);
                        return;
                    }
                }
            }

            this.setY(this.getY() + this.getVelY());
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(this.getAnimation().getView().getCurrentFrame(), this.getX(), this.getY());
    }
}
