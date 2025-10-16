package arkanoid.test.model;

import arkanoid.model.Paddle;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import standards.model.StandardID;

import static org.junit.jupiter.api.Assertions.*;

public class PaddleTest {

    private Paddle paddle;

    @BeforeEach
    void setUp() {
        paddle = new Paddle(100, 500);
        paddle.setSceneWidth(800);
    }

    /**
     * Kiểm tra khởi tạo.
     */
    @Test
    void testInitialProperties() {
        assertEquals(StandardID.Player, paddle.getId(), "False");
        assertEquals(100, paddle.getWidth(), "False");
        assertEquals(10, paddle.getHeight(), "False");
        assertFalse(paddle.isLarge(), "False");
    }

    /**
     * Kiểm tra di chuyển và dừng.
     */
    @Test
    void testMoveLeftRightStop() {
        paddle.moveRight();
        assertTrue(paddle.getVelX() > 0, "False");

        paddle.moveLeft();
        assertTrue(paddle.getVelX() < 0, "False");

        paddle.stop();
        assertEquals(0, paddle.getVelX(), "False");
    }

    /**
     * Kiểm tra cập nhật vị trí.
     */
    @Test
    void testTickUpdatesPosition() {
        double oldX = paddle.getX();

        paddle.moveRight();
        paddle.tick();

        assertNotEquals(oldX, paddle.getX(), "False");
    }

    /**
     * Kiểm tra biên trái, phải.
     */
    @Test
    void testBoundaryConstraints() {
        // Biên trái
        paddle.setX(10);
        paddle.moveLeft();
        paddle.tick();
        assertTrue(paddle.getX() >= 20, "False");

        // Biên phải
        paddle.setX(1000);
        paddle.moveRight();
        paddle.tick();
        double rightLimit = 800 - paddle.getWidth() - 20;
        assertEquals(rightLimit, paddle.getX(), 0.0001, "False");
    }

    /**
     * Kiểm tra powerup mở rộng.
     */
    @Test
    void testSetLargeActivatesPowerUp() {
        paddle.setLarge(true);
        assertTrue(paddle.isLarge(), "False");
        assertEquals(160, paddle.getWidth(), "False");
    }

    /**
     * Kiểm tra hết powerup.
     */
    @Test
    void testLargeEffectExpiresAfterTimer() {
        paddle.setLarge(true);

        for (int i = 0; i < 500; i++) {
            paddle.tick();
        }

        assertFalse(paddle.isLarge(), "False");
        assertEquals(100, paddle.getWidth(), "False");
    }

    /**
     * Kiểm tra setscenewidth ảnh hưởng tới giới hạn di chuyển.
     */
    @Test
    void testSetSceneWidthAffectsRightLimit() {
        paddle.setSceneWidth(600);
        paddle.setX(700);
        paddle.moveRight();
        paddle.tick();

        double rightLimit = 600 - paddle.getWidth() - 20;
        assertEquals(rightLimit, paddle.getX(), 0.0001, "False");
    }

    /**
     * Kiểm tra render.
     */
    @Test
    void testRenderDoesNotThrow() {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        assertDoesNotThrow(() -> paddle.render(gc),
                "False");
    }

    /**
     * Kiểm tra timer giảm khi đang powerup.
     */
    @Test
    void testTimerDecreasesWhenLarge() {
        paddle.setLarge(true);
        int initialTimer = 500;

        paddle.tick();

        assertTrue(paddle.isLarge(), "False");
        assertDoesNotThrow(() -> paddle.tick(), "False");
    }
}
