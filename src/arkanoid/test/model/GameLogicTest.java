package arkanoid.test.model;

import arkanoid.model.Ball;
import arkanoid.model.Brick;
import arkanoid.model.Paddle;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import standards.handler.StandardHandler;
import standards.platform.StandardGame;
import standards.main.StandardDraw;

public class GameLogicTest extends StandardGame {

    private StandardHandler handler;
    private Paddle paddle;
    private Ball ball;
    private int level = 1;

    private boolean ballLaunched = false;

    public GameLogicTest(Stage stage) {
        super(800, 600, "Arkanoid Game Logic Test", stage);
        initGame();
    }

    /** Khởi tạo logic game ban đầu. */
    private void initGame() {
        handler = new StandardHandler();

        paddle = new Paddle(getGameWidth() / 2.0 - 50, getGameHeight() - 40);
        paddle.setSceneWidth(getGameWidth());

        ball = new Ball(paddle.getX() + paddle.getWidth() / 2 - 4,
                paddle.getY() - 10, level);
        ball.setSceneSize(getGameWidth(), getGameHeight());

        handler.addEntity(paddle);
        handler.addEntity(ball);
        loadBricks();

        // Gán sự kiện bàn phím
        getWindow().getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) paddle.moveLeft();
            if (e.getCode() == KeyCode.RIGHT) paddle.moveRight();
            if (e.getCode() == KeyCode.SPACE && !ballLaunched) ballLaunched = true;
        });

        getWindow().getScene().setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT) paddle.stop();
        });
    }

    @Override
    public void tick() {
        paddle.tick();

        if (!ballLaunched) {
            // Giữ bóng trên paddle
            ball.setX(paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2);
            ball.setY(paddle.getY() - ball.getHeight() - 2);
        } else {
            ball.tick();
            handler.checkCollisions();
        }

        if (!ball.isAlive()) resetBall();

        boolean hasBricks = handler.getEntities().stream()
                .anyMatch(e -> e.getId().toString().equals("Brick"));

        if (!hasBricks) nextLevel();
    }

    @Override
    public void render() {
        // Vẽ nền đen
        StandardDraw.rect(
                0, 0,
                getGameWidth(),
                getGameHeight(),
                Color.BLACK,
                true
        );

        // Vẽ toàn bộ entity trong handler
        StandardDraw.Handler(handler);

        // HUD
        StandardDraw.text(
                "Level: " + level,
                20, 30,
                "Arial", 20,
                Color.WHITE
        );

        // Hướng dẫn nếu bóng chưa phóng
        if (!ballLaunched) {
            StandardDraw.text(
                    "Press SPACE to Launch Ball",
                    getGameWidth() / 2.0 - 120,
                    getGameHeight() / 2.0,
                    "Arial", 18,
                    Color.WHITE
            );
        }
    }


    /** Tải lại gạch cho mỗi level. */
    private void loadBricks() {
        int rows = 3 + level;
        int cols = 10;
        double startX = 100;
        double startY = 80;
        double gap = 5;

        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW,
                Color.GREEN, Color.BLUE, Color.PINK, Color.PURPLE};

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double x = startX + c * (60 + gap);
                double y = startY + r * (20 + gap);
                Brick brick = new Brick(x, y, colors[r % colors.length]);
                handler.addEntity(brick);
            }
        }
    }

    /** Reset bóng khi rơi xuống. */
    private void resetBall() {
        handler.removeEntity(ball);
        ball = new Ball(paddle.getX() + paddle.getWidth() / 2 - 4,
                paddle.getY() - 10, level);
        ball.setSceneSize(getGameWidth(), getGameHeight());
        handler.addEntity(ball);
        ballLaunched = false;
    }

    /** Qua màn mới. */
    private void nextLevel() {
        level++;
        handler.clearEntities();
        handler.addEntity(paddle);
        ball = new Ball(paddle.getX() + paddle.getWidth() / 2 - 4,
                paddle.getY() - 10, level);
        ball.setSceneSize(getGameWidth(), getGameHeight());
        handler.addEntity(ball);
        loadBricks();
        ballLaunched = false;
    }

    // Entry point JavaFX
    public static class Launcher extends Application {
        @Override
        public void start(Stage stage) {
            GameLogicTest game = new GameLogicTest(stage);
            game.StartGame();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
}
