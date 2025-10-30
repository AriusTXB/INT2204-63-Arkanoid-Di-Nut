package arkanoid.controller;

import arkanoid.model.Ball;
import arkanoid.model.Brick;
import arkanoid.model.Paddle;
import arkanoid.model.SongBox;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import standards.handler.StandardHandler;
import standards.model.StandardTrail;
import standards.platform.StandardGame;
import standards.main.StandardDraw;

import java.util.ArrayList;
import java.util.List;

public class Game extends StandardGame {

    private StandardHandler handler;
    private Paddle paddle;
    private final List<Ball> balls = new ArrayList<>();
    private Level currentLevel;
    private int difficulty = 1;
    private boolean ballLaunched = false;
    private SongBox songBox;

    private int lives = 3;
    private boolean gameOver = false;

    private final List<StandardTrail> trailPool = new ArrayList<>();
    private static final int TRAIL_POOL_SIZE = 200;

    public Game(Stage stage) {
        super(800, 600, "Arkanoid Game", stage);
        initGame();
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    /** Initializes game logic. */
    public void initGame() {
        handler = new StandardHandler();
        songBox = new SongBox();

        // Create the trail pool
        trailPool.clear();
        for (int i = 0; i < TRAIL_POOL_SIZE; i++) {
            StandardTrail trail = new StandardTrail(handler);
            trailPool.add(trail);
            handler.addEntity(trail);
        }

        // Paddle & Ball
        paddle = new Paddle(getGameWidth() / 2.0 - 50, getGameHeight() - 40);
        paddle.setSceneWidth(getGameWidth());

        // Ball đầu tiên
        Ball mainBall = new Ball(
                paddle.getX() + paddle.getWidth() / 2 - 4,
                paddle.getY() - 10,
                difficulty,
                handler,
                this
        );
        mainBall.setSceneSize(getGameWidth(), getGameHeight());
        balls.add(mainBall);
        handler.addEntity(paddle);
        handler.addEntity(mainBall);

        // Load first level
        loadLevel(difficulty);

        // Keyboard input
        getWindow().getScene().setOnKeyPressed(e -> {
            if (gameOver) return;

            KeyCode code = e.getCode();
            if (code == KeyCode.LEFT) paddle.moveLeft();
            if (code == KeyCode.RIGHT) paddle.moveRight();
            if (code == KeyCode.SPACE && !ballLaunched) {
                ballLaunched = true;
                double speed = Math.abs(mainBall.getVelY());
                mainBall.setVelX(0);
                mainBall.setVelY(-speed);
            }
            if (code == KeyCode.L) paddle.setLarge(!paddle.isLarge());
        });

        getWindow().getScene().setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT) paddle.stop();
        });

        songBox.loop("level");
    }

    @Override
    public void tick() {
        if (gameOver) return;

        handler.tick();

        if (!ballLaunched) {
            // Gắn bóng chính (đầu tiên) vào paddle
            Ball first = balls.get(0);
            first.setX(paddle.getX() + paddle.getWidth() / 2 - first.getWidth() / 2);
            first.setY(paddle.getY() - first.getHeight() - 2);
        } else {
            handler.checkCollisions();
        }

        // Loại bỏ các bóng chết
        balls.removeIf(ball -> !ball.isAlive());

        // Nếu tất cả bóng đã mất => trừ mạng
        if (balls.isEmpty()) {
            loseLife();
            resetBall();
        }

        // Thêm trail cho tất cả bóng đang sống
        for (Ball b : balls) {
            if (ballLaunched && b.isAlive()) {
                for (StandardTrail trail : trailPool) {
                    if (!trail.isActive()) {
                        trail.reset(b.getX(), b.getY(), b.getWidth(), b.getHeight(),
                                b.getCurrentColor(), 0.1);
                        break;
                    }
                }
            }
        }

        boolean hasBricks = handler.getEntities().stream()
                .anyMatch(e -> e.getId().toString().equals("Brick"));
        if (!hasBricks) nextLevel();
    }

    @Override
    public void render() {
        // Background
        StandardDraw.rect(
                0, 0,
                getGameWidth(),
                getGameHeight(),
                Color.BLACK,
                true
        );

        // Entities
        StandardDraw.Handler(handler);

        // HUD
        StandardDraw.text("Level: " + difficulty, 20, 30, "Arial", 20, Color.WHITE);
        StandardDraw.text("Lives: " + lives, 20, 60, "Arial", 20, Color.WHITE);

        if (gameOver) {
            StandardDraw.text("GAME OVER", getGameWidth() / 2.0 - 120, getGameHeight() / 2.0,
                    "Arial", 50, Color.WHITE);
        } else if (!ballLaunched) {
            StandardDraw.text("Press SPACE to Launch Ball",
                    getGameWidth() / 2.0 - 120, getGameHeight() / 2.0,
                    "Arial", 18, Color.WHITE);
        }
    }

    /** Lose one life when ball falls. */
    private void loseLife() {
        lives--;
        if (lives <= 0) {
            gameOver = true;
            songBox.stopAll();
            songBox.loop("gameover");
            return;
        }
        resetBall();
    }

    /** Load a new level and its bricks */
    private void loadLevel(int levelNum) {
        int difficulty = switch (levelNum) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            default -> 1;
        };

        currentLevel = new Level(levelNum, difficulty);

        for (Brick brick : currentLevel.getBricks()) {
            handler.addEntity(brick);
        }
    }

    /** Reset the ball when lost */
    private void resetBall() {
        for (Ball b : balls) handler.removeEntity(b);
        balls.clear();

        Ball newBall = new Ball(
                paddle.getX() + paddle.getWidth() / 2 - 4,
                paddle.getY() - 10,
                difficulty,
                handler,
                this
        );
        newBall.setSceneSize(getGameWidth(), getGameHeight());
        handler.addEntity(newBall);
        balls.add(newBall);
        ballLaunched = false;
    }


    /** Move to the next level */
    private void nextLevel() {
        difficulty++;
        songBox.stopAll();
        songBox.loop("level");

        for (StandardTrail trail : trailPool) {
            trail.setActive(false);
        }

        handler.clearEntities();
        for (StandardTrail trail : trailPool) handler.addEntity(trail);
        handler.addEntity(paddle);

        balls.clear();
        Ball newBall = new Ball(
                paddle.getX() + paddle.getWidth() / 2 - 4,
                paddle.getY() - 10,
                difficulty,
                handler,
                this
        );
        newBall.setSceneSize(getGameWidth(), getGameHeight());
        handler.addEntity(newBall);
        balls.add(newBall);
        ballLaunched = false;

        loadLevel(difficulty);
    }

    public void addBall(Ball b) {
        balls.add(b);
    }
}
