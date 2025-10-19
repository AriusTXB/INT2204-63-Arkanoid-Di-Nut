package arkanoid.test.model;

import arkanoid.model.Ball;
import arkanoid.model.Brick;
import arkanoid.model.Paddle;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import standards.handler.StandardHandler;
import standards.model.StandardTrail;
import standards.platform.StandardGame;
import standards.main.StandardDraw;

import java.util.ArrayList;
import java.util.List;

public class GameLogicTest extends StandardGame {

    private StandardHandler handler;
    private Paddle paddle;
    private Ball ball;
    private int level = 1;
    private boolean ballLaunched = false;

    // Object Pooling for Trails
    private final List<StandardTrail> trailPool = new ArrayList<>();
    private static final int TRAIL_POOL_SIZE = 200; // Create 200 trails up front for reuse

    public GameLogicTest(Stage stage) {
        super(800, 600, "Arkanoid Game Logic Test", stage);
        initGame();
    }

    /** Initializes game logic. */
    private void initGame() {
        handler = new StandardHandler();

        // Create the trail pool
        trailPool.clear();
        for (int i = 0; i < TRAIL_POOL_SIZE; i++) {
            StandardTrail trail = new StandardTrail(handler);
            trailPool.add(trail);
            handler.addEntity(trail); // Add them to the handler ONCE at the start
        }

        paddle = new Paddle(getGameWidth() / 2.0 - 50, getGameHeight() - 40);
        paddle.setSceneWidth(getGameWidth());

        ball = new Ball(paddle.getX() + paddle.getWidth() / 2 - 4,
                paddle.getY() - 10, level);
        ball.setSceneSize(getGameWidth(), getGameHeight());

        handler.addEntity(paddle);
        handler.addEntity(ball);
        loadBricks();

        // Keyboard events
        getWindow().getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) paddle.moveLeft();
            if (e.getCode() == KeyCode.RIGHT) paddle.moveRight();
            if (e.getCode() == KeyCode.SPACE && !ballLaunched) {
                ballLaunched = true;
                double speed = Math.abs(ball.getVelY());
                ball.setVelX(0);
                ball.setVelY(-speed);
            }
        });

        getWindow().getScene().setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT) paddle.stop();
        });
    }

    @Override
    public void tick() {
        // Update all existing entities
        handler.tick();

        // Activate a trail from the pool
        if (ballLaunched && ball.isAlive()) {
            for (StandardTrail trail : trailPool) {
                if (!trail.isActive()) { // Find an inactive trail
                    trail.reset(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight(),
                            ball.getCurrentColor(), 0.1);
                    break; // Activate one trail per frame and stop
                }
            }
        }

        if (!ballLaunched) {
            // Keep ball on paddle
            ball.setX(paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2);
            ball.setY(paddle.getY() - ball.getHeight() - 2);
        } else {
            handler.checkCollisions();
        }

        if (!ball.isAlive()) resetBall();

        boolean hasBricks = handler.getEntities().stream()
                .anyMatch(e -> e.getId().toString().equals("Brick"));

        if (!hasBricks) nextLevel();
    }

    @Override
    public void render() {
        StandardDraw.rect(0, 0, getGameWidth(), getGameHeight(), Color.BLACK, true);
        StandardDraw.Handler(handler);
        StandardDraw.text("Level: " + level, 20, 30, "Arial", 20, Color.WHITE);
        if (!ballLaunched) {
            StandardDraw.text("Press SPACE to Launch Ball", getGameWidth() / 2.0 - 120, getGameHeight() / 2.0, "Arial", 18, Color.WHITE);
        }
    }

    /** Loads bricks for the current level. */
    private void loadBricks() {
        int rows = 3 + level;
        int cols = 10;
        double startX = 100;
        double startY = 80;
        double gap = 5;
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.PINK, Color.PURPLE};
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double x = startX + c * (60 + gap);
                double y = startY + r * (20 + gap);
                Brick brick = new Brick(x, y, colors[r % colors.length]);
                handler.addEntity(brick);
            }
        }
    }

    /** Resets the ball when it's lost. */
    private void resetBall() {
        handler.removeEntity(ball);
        ball = new Ball(paddle.getX() + paddle.getWidth() / 2 - 4, paddle.getY() - 10, level);
        ball.setSceneSize(getGameWidth(), getGameHeight());
        handler.addEntity(ball);
        ballLaunched = false;
    }

    /** Proceeds to the next level. */
    private void nextLevel() {
        level++;
        // Deactivate all trails to clean up for the next level
        for (StandardTrail trail : trailPool) {
            trail.setActive(false);
        }
        handler.clearEntities();

        // Re-add pooled trails and persistent objects
        for(StandardTrail trail : trailPool) handler.addEntity(trail);
        handler.addEntity(paddle);

        ball = new Ball(paddle.getX() + paddle.getWidth() / 2 - 4, paddle.getY() - 10, level);
        ball.setSceneSize(getGameWidth(), getGameHeight());
        handler.addEntity(ball);
        loadBricks();
        ballLaunched = false;
    }

    public static class Launcher extends Application {
        @Override
        public void start(Stage stage) {
            new GameLogicTest(stage).StartGame();
        }
        public static void main(String[] args) { launch(args); }
    }
}
