package arkanoid.test.model;

import arkanoid.model.Brick;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import standards.model.StandardID;

import static org.junit.jupiter.api.Assertions.*;

public class BrickTest {

    private Brick redBrick;

    @BeforeEach
    void setUp() {
        redBrick = new Brick(100, 150, Color.RED);
    }

    /**
     * Kiểm tra khởi tạo.
     */
    @Test
    void testInitialProperties() {
        assertEquals(StandardID.Brick, redBrick.getId(), "False");
        assertEquals(60, redBrick.getWidth(), "False");
        assertEquals(20, redBrick.getHeight(), "False");
        assertEquals(Color.RED, redBrick.getColor(), "False");
    }

    /**
     * Kiểm tra toString().
     */
    @Test
    void testToStringFormat() {
        String text = redBrick.toString();
        assertTrue(text.contains("Brick["), "False");
        assertTrue(text.contains("color=0xff0000ff"), "False");
    }

    /**
     * Kiểm tra gọi màu.
     */
    @Test
    void testGetColorNameMapping() throws Exception {
        // Dùng reflection để gọi private method
        var method = Brick.class.getDeclaredMethod("getColorName", Color.class);
        method.setAccessible(true);

        assertEquals("red", method.invoke(redBrick, Color.RED));
        assertEquals("blue", method.invoke(redBrick, Color.BLUE));
        assertEquals("green", method.invoke(redBrick, Color.GREEN));
        assertEquals("orange", method.invoke(redBrick, Color.ORANGE));
        assertEquals("pink", method.invoke(redBrick, Color.PINK));
        assertEquals("yellow", method.invoke(redBrick, Color.YELLOW));
        assertEquals("purple", method.invoke(redBrick, Color.MAGENTA));
        assertNull(method.invoke(redBrick, Color.BROWN), "False");
    }

    /**
     * Kiểm tra tick không thay đổi vị trí.
     */
    @Test
    void testTickDoesNothing() {
        double oldX = redBrick.getX();
        double oldY = redBrick.getY();

        redBrick.tick();

        assertEquals(oldX, redBrick.getX(), "False");
        assertEquals(oldY, redBrick.getY(), "False");
    }

    /**
     * Kiểm tra render.
     */
    @Test
    void testRenderDoesNotThrow() {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        assertDoesNotThrow(() -> redBrick.render(gc), "False");
    }

    /**
     * Kiểm tra loadSprite().
     */
    @Test
    void testLoadSpriteHandlesInvalidColorGracefully() throws Exception {
        Brick invalidBrick = new Brick(0, 0, Color.BROWN);

        var method = Brick.class.getDeclaredMethod("loadSprite");
        method.setAccessible(true);

        assertDoesNotThrow(() -> method.invoke(invalidBrick), "False");
    }
}
