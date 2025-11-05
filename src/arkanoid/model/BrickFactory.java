package arkanoid.model;

import javafx.scene.paint.Color;

public class BrickFactory {

    public static Brick createBrick(String type, double x, double y, Color color) {
        return switch (type.toLowerCase()) {
            case "strong" -> new StrongBrick(x, y, color);
            case "hell" -> new HellBrick(x, y, color);
            default -> new Brick(x, y, color);
        };
    }
}
