package arkanoid.controller;

import arkanoid.model.Brick;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Đại diện cho một màn chơi (Level) trong game Arkanoid.
 * Sinh layout Brick theo độ khó.
 */
public class Level {

    private int levelNumber;
    private int difficulty;
    private List<Brick> bricks;

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;

    public Level(int levelNumber, int difficulty) {
        this.levelNumber = levelNumber;
        this.difficulty = difficulty;
        init();
    }

    /** Khởi tạo danh sách Brick theo độ khó */
    private void init() {
        bricks = new ArrayList<>();

        switch (difficulty) {
            case 1 -> createEasyLayout();
            case 2 -> createNormalLayout();
            case 3 -> createHardLayout();
            default -> createEasyLayout();
        }
    }

    /** Layout dễ: ít gạch, kích thước lớn, khoảng cách thoáng */
    private void createEasyLayout() {
        int cols = 8, rows = 4;
        double brickWidth = 60;
        double brickHeight = 20;
        double gap = 5;
        double totalWidth = cols * brickWidth + (cols - 1) * gap;
        double startX = (SCREEN_WIDTH - totalWidth) / 2.0;
        double startY = 80;

        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.BLUE};

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double x = startX + c * (brickWidth + gap);
                double y = startY + r * (brickHeight + gap);
                Color color = colors[r % colors.length];
                bricks.add(new Brick(x, y, color));
            }
        }
    }

    /** Layout trung bình: nhiều hàng hơn, nhỏ hơn, sát nhau hơn */
    private void createNormalLayout() {
        int cols = 10, rows = 6;
        double brickWidth = 60;
        double brickHeight = 20;
        double gap = 4;
        double totalWidth = cols * brickWidth + (cols - 1) * gap;
        double startX = (SCREEN_WIDTH - totalWidth) / 2.0;
        double startY = 70;

        Color[] colors = {
                Color.BLUE, Color.GREEN, Color.ORANGE,
                Color.RED, Color.PINK, Color.PURPLE
        };

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double x = startX + c * (brickWidth + gap);
                double y = startY + r * (brickHeight + gap);
                Color color = colors[r % colors.length];
                bricks.add(new Brick(x, y, color));
            }
        }
    }

    /** Layout khó: dày đặc, nhiều cột hơn, chiếm gần hết chiều ngang */
    private void createHardLayout() {
        int cols = 12, rows = 8;
        double brickWidth = 60;
        double brickHeight = 20;
        double gap = 3;
        double totalWidth = cols * brickWidth + (cols - 1) * gap;
        double startX = (SCREEN_WIDTH - totalWidth) / 2.0;
        double startY = 60;

        Color[] colors = {
                Color.RED, Color.ORANGE, Color.YELLOW,
                Color.GREEN, Color.BLUE, Color.PURPLE, Color.PINK
        };

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double x = startX + c * (brickWidth + gap);
                double y = startY + r * (brickHeight + gap);
                Color color = colors[r % colors.length];
                bricks.add(new Brick(x, y, color));
            }
        }
    }

    /** Trả về danh sách Brick của level này */
    public List<Brick> getBricks() {
        return bricks;
    }

    /** Lấy số level hiện tại */
    public int getLevelNumber() {
        return levelNumber;
    }

    /** Lấy độ khó */
    public int getDifficulty() {
        return difficulty;
    }

    /** Sinh lại level từ đầu (reset bricks) */
    public void reset() {
        init();
    }
}
