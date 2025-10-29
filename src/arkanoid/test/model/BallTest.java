package arkanoid.test.model;

import arkanoid.controller.Game;
import arkanoid.model.Ball;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import standards.handler.StandardHandler;
import standards.model.StandardID;

import static org.junit.jupiter.api.Assertions.*;

public class BallTest {

    private Ball ball;

    private StandardHandler handler;
    private Game game;

    @BeforeEach
    void setUp() {
        ball = new Ball(100, 100, 2, handler, game);
        ball.setSceneSize(800, 600);
    }

    /**
     * Kiểm tra khởi tạo.
     */
    @Test
    void testInitialProperties() {
        assertEquals(StandardID.Enemy, ball.getId(), "False");
        assertTrue(ball.isAlive(), "False");
        assertEquals(15, ball.getWidth());
        assertEquals(15, ball.getHeight());
    }

    /**
     * Kiểm tra di chuyển bình thường, không va chạm.
     */
    @Test
    void testTickUpdatesPosition() {
        double oldX = ball.getX();
        double oldY = ball.getY();

        ball.tick();

        assertNotEquals(oldX, ball.getX());
        assertNotEquals(oldY, ball.getY());
    }

    /**
     * Kiểm tra va chạm tường trái phải.
     */
    @Test
    void testBounceOnLeftRightWalls() {
        ball.setX(15);
        double oldVelX = ball.getVelX();

        ball.tick();

        assertEquals(-oldVelX, ball.getVelX(), 0.0001);
    }

    /**
     * Kiểm tra va chạm tường trên.
     */
    @Test
    void testBounceOnTopWall() {
        ball.setY(10);
        double oldVelY = ball.getVelY();

        ball.tick();

        assertEquals(-oldVelY, ball.getVelY(), 0.0001);
    }

    /**
     * Kiểm tra bóng rơi ra ngoài màn hình.
     */
    @Test
    void testBallFallsBelowScreen() {
        ball.setY(601);
        ball.tick();

        assertFalse(ball.isAlive(), "False");
    }

    /**
     * Kiểm tra không di chuyển khi bóng đã chết.
     */
    @Test
    void testNoMovementWhenDead() {
        ball.setY(700);
        ball.tick();
        double x = ball.getX();
        double y = ball.getY();

        ball.tick();

        assertEquals(x, ball.getX(), "False");
        assertEquals(y, ball.getY(), "False");
    }

    /**
     * Kiểm tra setscenesize.
     */
    @Test
    void testSetSceneSize() {
        ball.setSceneSize(1024, 768);
        ball.setY(769);
        ball.tick();
        assertFalse(ball.isAlive(), "False");
    }
}
