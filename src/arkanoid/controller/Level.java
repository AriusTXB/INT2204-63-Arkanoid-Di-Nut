package arkanoid.controller;

import arkanoid.model.BrickFactory;
import arkanoid.model.Brick;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Đại diện cho một màn chơi (Level) trong game Arkanoid.
 * Sử dụng Factory Pattern để sinh Brick theo độ khó.
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
            case 4 -> createHellLayout();
            case 5 -> createImpossibleLayout();
            default -> createEasyLayout();
        }
    }

    /** Layout dễ */
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
                bricks.add(BrickFactory.createBrick("normal", x, y, color));
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

                String type = ((r == 0 && c == 0) || (r == 0 && c == cols - 1) ||
                        (r == rows - 1 && c == 0) || (r == rows - 1 && c == cols - 1))
                        ? "strong" : "normal";

                bricks.add(BrickFactory.createBrick(type, x, y, color));
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

                String type = (r % 2 == 0 && c % 2 == 0) ? "strong" : "normal";
                bricks.add(BrickFactory.createBrick(type, x, y, color));
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

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double x = startX + c * (brickWidth + gap);
                double y = startY + r * (brickHeight + gap);
                bricks.add(BrickFactory.createBrick("strong", x, y, Color.GRAY));
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

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double x = startX + c * (brickWidth + gap);
                double y = startY + r * (brickHeight + gap);
                bricks.add(BrickFactory.createBrick("hell", x, y, Color.BLACK));
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
