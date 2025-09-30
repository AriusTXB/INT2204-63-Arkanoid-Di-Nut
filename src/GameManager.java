package Project;

import java.util.ArrayList;
import java.util.List;

class GameManager {
    private Paddle paddle;
    private Ball ball;
    private List<Brick> bricks;
    private List<PowerUp> powerUps;
    private int score;
    private int lives;
    private String gameState;

    public GameManager(Paddle paddle, Ball ball, List<Brick> bricks, int score, int lives) {
        this.paddle = paddle;
        this.ball = ball;
        this.bricks = bricks;
        this.powerUps = new ArrayList<>();
        this.score = score;
        this.lives = lives;
        this.gameState = "READY";
    }

    public void startGame() {
        gameState = "RUNNING";
    }

    public void updateGame() {

    }

    public void handleInput() {

    }

    public void checkCollisions() {

    }

    public void gameOver() {
        gameState = "GAMEOVER";
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public Ball getBall() {
        return ball;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }
}

