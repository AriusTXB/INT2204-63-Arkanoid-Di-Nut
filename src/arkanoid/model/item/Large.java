package arkanoid.model.item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;

import standards.model.StandardAnimation;
import standards.main.StandardDraw;
import standards.handler.StandardHandler;
import standards.model.StandardID;
import arkanoid.model.BasicParticle;
import arkanoid.model.Paddle;
import arkanoid.controller.Game;
import arkanoid.model.SongBox;

/**
 * Represents the "Large" item collectible in the game.
 * <p>
 * When collected by the player (a {@link Paddle}), it increases the paddle’s width,
 * triggers a sound effect, and removes itself from the game world.
 * </p>
 * <p>
 * This class is a JavaFX-based version of the original AWT implementation,
 * utilizing {@link javafx.scene.image.Image} for sprite frames and
 * {@link javafx.scene.canvas.GraphicsContext} for rendering.
 * </p>
 */
public class Large extends Item {

  /** Reference to the game instance managing global state. */
  private Game stdGame;

  /** The main handler managing all entities in the scene. */
  private StandardHandler stdHandler;

  /** Handles sound playback and effects for this item. */
  private SongBox songBox;

  /**
   * Constructs a {@code Large} power-up item at the specified position.
   *
   * @param x          the x-coordinate of the item
   * @param y          the y-coordinate of the item
   * @param stdGame    reference to the main {@link Game} instance
   * @param stdHandler the global {@link StandardHandler} managing all entities
   * @param songBox    the {@link SongBox} responsible for sound effects
   */
  public Large(int x, int y, Game stdGame, StandardHandler stdHandler, SongBox songBox) {
    super(x, y);

    this.setId(StandardID.Large);
    this.stdGame = stdGame;
    this.stdHandler = stdHandler;
    this.songBox = songBox;

    // Load animation frames
    for (int i = 0; i < this.MAX_FRAMES; i++) {
      try {
        this.frames.add(new Image(new File("Resources/Sprites/Items/Large/large" + i + ".png").toURI().toString()));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    this.setVelY(2);
    if (!this.frames.isEmpty()) {
        this.setAnimation(new StandardAnimation(this, this.frames.toArray(new Image[0]), 120));
    }
  }

  /**
   * Updates the item each frame.
   * <p>
   * Handles falling motion, collision detection with the player,
   * and removal when out of bounds.
   * </p>
   */
  @Override
  public void tick() {
    if (this.getY() > 800) {
      // Create explosion of particles when falling off-screen
      for (int i = 0; i < 100; i++) {
        this.stdHandler.addEntity(new BasicParticle(
            (int) this.getX() + (this.getWidth() / 2),
            (int) this.getY(),
            200f,
            this.stdHandler,
            StandardDraw.CARDINAL_RED,
            0.0));
      }

      this.stdHandler.removeEntity(this);
      this.setAlive(false);
    }

    if (this.isAlive()) {
      this.getAnimation().updateAnimation();

      // Check collision only when near player level
      if (this.getY() >= 650) {
        for (int i = 0; i < stdHandler.size(); i++) {
          if ((this.stdHandler.get(i).getId() == StandardID.Player)
              && (this.getBounds().intersects(this.stdHandler.get(i).getBounds()))) {

            songBox.playPowerUp();

            Paddle player = (Paddle) this.stdHandler.get(i);
            player.setLarge(true);
            player.setWidth(player.getWidth() + 20); // Activate power-up effect

            this.stdHandler.removeEntity(this);
            break;
          }
        }
      }

      // Apply velocity to Y position
      this.setY((int) this.getY() + (int) this.getVelY());
    }
  }

  /**
   * Renders the item to the screen using the provided JavaFX {@link GraphicsContext}.
   *
   * @param gc the graphics context used to draw this item
   */
  @Override
  public void render(GraphicsContext gc) {
    gc.drawImage(this.getCurrentSprite(), this.getX(), this.getY());
  }
}

