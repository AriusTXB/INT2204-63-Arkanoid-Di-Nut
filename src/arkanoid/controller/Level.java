package arkanoid.controller;

import arkanoid.model.Brick;
import arkanoid.model.StrongBrick;
import arkanoid.model.HellBrick;
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
            // case 1 -> createEasyLayout();
            case 1 -> createHellLayout();
            case 2 -> createNormalLayout();
            case 3 -> createHardLayout();
            case 4 -> createHellLayout();
            case 5 -> createImpossibleLayout();
            //default -> createEasyLayout();
            default -> createHellLayout();
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

    /** Layout trung bình: thêm 4 StrongBrick ở 4 góc */
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

                // 4 góc StrongBrick
                if ((r == 0 && c == 0) || (r == 0 && c == cols - 1) ||
                        (r == rows - 1 && c == 0) || (r == rows - 1 && c == cols - 1)) {
                    bricks.add(new StrongBrick(x, y, Color.GRAY));
                } else {
                    bricks.add(new Brick(x, y, color));
                }
            }
        }
    }

    /** Layout khó: so le nửa StrongBrick */
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

                // So le: hàng chẵn StrongBrick, hàng lẻ Brick
                if (r % 2 == 0 && c % 2 == 0) {
                    bricks.add(new StrongBrick(x, y, Color.GRAY));
                } else {
                    bricks.add(new Brick(x, y, color));
                }
            }
        }
    }

    /** Layout Hell: full StrongBrick */
    private void createHellLayout() {
        int cols = 12, rows = 8;
        double brickWidth = 60;
        double brickHeight = 20;
        double gap = 3;
        double totalWidth = cols * brickWidth + (cols - 1) * gap;
        double startX = (SCREEN_WIDTH - totalWidth) / 2.0;
        double startY = 60;

        Color[] colors = {
                Color.DARKRED, Color.FIREBRICK, Color.CRIMSON,
                Color.DARKMAGENTA, Color.DARKVIOLET
        };

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double x = startX + c * (brickWidth + gap);
                double y = startY + r * (brickHeight + gap);
                Color color = colors[(r + c) % colors.length];
                bricks.add(new StrongBrick(x, y, Color.GRAY));
            }
        }
    }

    /** Layout Impossible: full HellBrick */
    private void createImpossibleLayout() {
        int cols = 12, rows = 8;
        double brickWidth = 60;
        double brickHeight = 20;
        double gap = 3;
        double totalWidth = cols * brickWidth + (cols - 1) * gap;
        double startX = (SCREEN_WIDTH - totalWidth) / 2.0;
        double startY = 60;

        Color[] colors = {
                Color.BLACK, Color.DARKRED, Color.MAROON, Color.DARKSLATEGRAY
        };

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double x = startX + c * (brickWidth + gap);
                double y = startY + r * (brickHeight + gap);
                Color color = colors[(r + c) % colors.length];
                bricks.add(new HellBrick(x, y, Color.BLACK));
            }
        }
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void reset() {
        init();
    }
}
