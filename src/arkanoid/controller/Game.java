package arkanoid.controller;

import arkanoid.model.Ball;
import arkanoid.model.Paddle;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Game {
    private Ball ball;
    private Paddle paddle;
    private Canvas canvas;
    private GraphicsContext gc;
    private boolean gameOver = false;
    private int difficulty;

    // Constructor to initialize the game with the Canvas and difficulty level
    public Game(int difficulty) {
        this.difficulty = difficulty ;
        canvas = new Canvas(800, 600); // Create a Canvas with specific width and height
        gc = canvas.getGraphicsContext2D(); // Get the GraphicsContext from the Canvas
        ball = new Ball(400, 300, difficulty); // Initialize Ball at position (400, 300)
        paddle = new Paddle(350, 550); // Initialize Paddle at position (350, 550)

        // Set up the scene and key event handling
        setUpKeyEvents();
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void startGame() {
        // Set up the game loop to update the game state
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameOver) {
                    updateGame();
                }
            }
        };

        gameLoop.start();
    }

    private void updateGame() {
        // Update Ball position and handle collision with walls
        ball.tick();

        // Update Paddle position and handle boundary checks
        paddle.tick();

        // Check if the ball is still alive (i.e., if it didn't fall off the screen)
        if (!ball.isAlive()) {
            gameOver = true;
            // Handle game over logic (e.g., display message)
        }

        // Update the ball and paddle rendering
        ball.render(gc);
        paddle.render(gc);
    }

    private void setUpKeyEvents() {
        // Key event handling for paddle movement
        canvas.setOnKeyPressed(new javafx.event.EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    paddle.moveLeft();
                } else if (event.getCode() == KeyCode.RIGHT) {
                    paddle.moveRight();
                }
            }
        });
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
