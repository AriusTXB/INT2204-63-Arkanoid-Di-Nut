package arkanoid.controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Đại diện cho một màn chơi (Level) trong game Arkanoid.
 * Quản lý toàn bộ brick, paddle, ball và trạng thái thắng/thua.
 */
public class Level {

    private int levelNumber;
    private int difficulty;
    private boolean completed;
    private boolean failed;

    private Paddle paddle;
    private Ball ball;
    private List<Brick> bricks;

    public Level(int levelNumber, int difficulty) {
        this.levelNumber = levelNumber;
        this.difficulty = difficulty;
        init();
    }

    /** Khởi tạo các đối tượng của level */
    public void init() {
        completed = false;
        failed = false;
        bricks = new ArrayList<>();

        // Kích thước màn hình giả định
        int screenWidth = 800;
        int screenHeight = 600;

        // Paddle và ball cơ bản
        paddle = new Paddle(screenWidth / 2 - 50, screenHeight - 50, 100, 10);
        ball = new Ball(screenWidth / 2, screenHeight - 70, 10, -4, -4);

        // Sinh brick tùy theo độ khó
        switch (difficulty) {
            case 1 -> createEasyLayout(screenWidth);
            case 2 -> createNormalLayout(screenWidth);
            case 3 -> createHardLayout(screenWidth);
        }
    }

    /** Layout dễ: ít brick, dễ phá */
    private void createEasyLayout(int screenWidth) {
        int cols = 6, rows = 3;
        int brickWidth = screenWidth / cols;
        int brickHeight = 25;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                bricks.add(new Brick(col * brickWidth, 50 + row * brickHeight, brickWidth - 5, brickHeight - 5, 1));
            }
        }
    }

    /** Layout trung bình: vừa phải */
    private void createNormalLayout(int screenWidth) {
        int cols = 8, rows = 4;
        int brickWidth = screenWidth / cols;
        int brickHeight = 25;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                bricks.add(new Brick(col * brickWidth, 40 + row * brickHeight, brickWidth - 4, brickHeight - 4, 2));
            }
        }
    }

    /** Layout khó: nhiều brick và bền hơn */
    private void createHardLayout(int screenWidth) {
        int cols = 10, rows = 5;
        int brickWidth = screenWidth / cols;
        int brickHeight = 25;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                bricks.add(new Brick(col * brickWidth, 30 + row * brickHeight, brickWidth - 3, brickHeight - 3, 3));
            }
        }
    }

    /** Cập nhật mỗi frame */
    public void update() {
        ball.update();
        paddle.update();

        // Va chạm paddle
        if (ball.getRect().intersects(paddle.getRect())) {
            ball.bounceVertical();
        }

        // Va chạm brick
        for (Brick brick : new ArrayList<>(bricks)) {
            if (brick.isAlive() && ball.getRect().intersects(brick.getRect())) {
                ball.bounceVertical();
                brick.hit();
                if (!brick.isAlive()) bricks.remove(brick);
                break;
            }
        }

        // Kiểm tra trạng thái
        if (bricks.isEmpty()) completed = true;
        if (ball.getY() > 600) failed = true;
    }

    /** Vẽ toàn bộ level */
    public void render(Graphics g) {
        paddle.render(g);
        ball.render(g);
        for (Brick brick : bricks) brick.render(g);
    }

    /** Reset lại level */
    public void reset() {
        init();
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isFailed() {
        return failed;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public int getDifficulty() {
        return difficulty;
    }
}

/** ==== Các class phụ trợ cơ bản cho Level ==== */

class GameObject {
    protected int x, y, width, height;

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }
}

class Paddle extends GameObject {
    private int speed = 8;

    public Paddle(int x, int y, int width, int height) {
        this.x = x; this.y = y; this.width = width; this.height = height;
    }

    public void update() {
        // TODO: lấy input từ Game nếu có, tạm thời không xử lý
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }
}

class Ball extends GameObject {
    private int dx, dy;

    public Ball(int x, int y, int size, int dx, int dy) {
        this.x = x; this.y = y; this.width = size; this.height = size;
        this.dx = dx; this.dy = dy;
    }

    public void update() {
        x += dx;
        y += dy;

        // Va chạm biên
        if (x < 0 || x + width > 800) dx = -dx;
        if (y < 0) dy = -dy;
    }

    public void bounceVertical() {
        dy = -dy;
    }

    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, width, height);
    }

    public int getY() { return y; }
}

class Brick extends GameObject {
    private int hp;

    public Brick(int x, int y, int width, int height, int hp) {
        this.x = x; this.y = y; this.width = width; this.height = height; this.hp = hp;
    }

    public void hit() {
        hp--;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void render(Graphics g) {
        if (!isAlive()) return;
        g.setColor(new Color(200 - hp * 50, 50 + hp * 40, 100));
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }
}
