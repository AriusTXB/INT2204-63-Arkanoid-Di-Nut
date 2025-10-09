package arkanoid.test.model;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Queue;

public class GameLogicTest extends Application {

    private double paddleX = 250;
    private double paddleY = 600;
    private final double paddleWidth = 100;
    private final double paddleHeight = 15;

    private double ballX;
    private double ballY;
    private final double ballRadius = 10;

    private double velX = 0;
    private double velY = 0;
    private double speed;

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean spacePressed = false;
    private boolean ballLaunched = false;

    private final double WIDTH = 600;
    private final double HEIGHT = 800;

    private int level = 1;
    private final int MAX_LEVEL = 5;

    private static final int MAX_TRAIL = 10;
    private final Queue<double[]> trail = new LinkedList<>();

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Scene scene = new Scene(new javafx.scene.Group(canvas));
        stage.setScene(scene);
        stage.show();

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) leftPressed = true;
            if (e.getCode() == KeyCode.RIGHT) rightPressed = true;
            if (e.getCode() == KeyCode.SPACE) spacePressed = true;
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT) leftPressed = false;
            if (e.getCode() == KeyCode.RIGHT) rightPressed = false;
        });

        resetBall();

        // Game loop
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render(gc);
            }
        }.start();
    }

    private void update() {
        // Paddle movement
        if (leftPressed) paddleX -= 6;
        if (rightPressed) paddleX += 6;
        paddleX = Math.max(0, Math.min(paddleX, WIDTH - paddleWidth));

        // Nếu bóng chưa phóng, dính paddle
        if (!ballLaunched) {
            ballX = paddleX + paddleWidth / 2 - ballRadius;
            ballY = paddleY - ballRadius * 2;

            if (spacePressed) {
                launchBallStraightUp();
                spacePressed = false;
            }
            return;
        }

        // Ball movement
        ballX += velX;
        ballY += velY;

        // Thêm vị trí hiện tại vào trail
        trail.add(new double[]{ballX, ballY});
        if (trail.size() > MAX_TRAIL) trail.poll();

        // Wall collisions
        if (ballX <= 0 || ballX >= WIDTH - ballRadius * 2) {
            velX = -velX;
            normalizeVelocity();
        }
        if (ballY <= 0) {
            velY = -velY;
            normalizeVelocity();
        }

        // Paddle collision
        if (ballY + ballRadius * 2 >= paddleY &&
                ballY + ballRadius * 2 <= paddleY + paddleHeight &&
                ballX + ballRadius >= paddleX &&
                ballX <= paddleX + paddleWidth) {

            // góc bật phụ thuộc vị trí va chạm trên paddle
            double hitPos = (ballX + ballRadius - (paddleX + paddleWidth / 2)) / (paddleWidth / 2);
            double angle = Math.toRadians(75 * hitPos);
            velX = speed * Math.sin(angle);
            velY = -Math.abs(speed * Math.cos(angle));
        }

        // Ball falls off screen
        if (ballY > HEIGHT) {
            nextLevel();
        }
    }

    private void resetBall() {
        ballLaunched = false;
        trail.clear();
        ballX = paddleX + paddleWidth / 2 - ballRadius;
        ballY = paddleY - ballRadius * 2;
        velX = 0;
        velY = 0;
        speed = 3 + (level - 1);
    }

    private void launchBallStraightUp() {
        ballLaunched = true;
        velX = 0;
        velY = -speed;
    }

    private void nextLevel() {
        if (level < MAX_LEVEL) level++;
        resetBall();
    }

    private void normalizeVelocity() {
        double mag = Math.sqrt(velX * velX + velY * velY);
        velX = (velX / mag) * speed;
        velY = (velY / mag) * speed;
    }

    private void render(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        // Paddle
        gc.setFill(Color.RED);
        gc.fillRect(paddleX, paddleY, paddleWidth, paddleHeight);

        int i = 0;
        for (double[] pos : trail) {
            double alpha = (double) i / MAX_TRAIL; // từ mờ → rõ
            gc.setFill(Color.rgb(255, 200, 50, alpha * 0.7));
            gc.fillOval(pos[0], pos[1], ballRadius * 2, ballRadius * 2);
            i++;
        }

        // Ball
        gc.setFill(Color.ORANGE);
        gc.fillOval(ballX, ballY, ballRadius * 2, ballRadius * 2);

        gc.setFill(Color.WHITE);
        gc.fillText("Level: " + level + "   Speed: " + speed, 10, 20);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
