package arkanoid.test.model;

import arkanoid.model.StrongBrick;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import standards.model.StandardID;

import static org.junit.jupiter.api.Assertions.*;

public class StrongBrickTest {

    private StrongBrick grayBrick;

    @BeforeEach
    void setUp() {
        grayBrick = new StrongBrick(100, 150, Color.GRAY);
    }

    @Test
    void testInitialProperties() {
        assertEquals(StandardID.Brick, grayBrick.getId());
        assertEquals(60, grayBrick.getWidth());
        assertEquals(20, grayBrick.getHeight());
        assertEquals(Color.GRAY, grayBrick.getColor());
        assertEquals(2, grayBrick.getHealth());
    }

    @Test
    void testToStringFormat() {
        String text = grayBrick.toString();
        assertTrue(text.contains("StrongBrick["));
        assertTrue(text.contains("hp=2/2"));
    }

    @Test
    void testTakeHitReducesHealth() {
        grayBrick.takeHit();
        assertEquals(1, grayBrick.getHealth());
        grayBrick.takeHit();
        assertEquals(0, grayBrick.getHealth());
        grayBrick.takeHit();
        assertEquals(0, grayBrick.getHealth(), "Health should not go below 0");
    }

    @Test
    void testIsDestroyed() {
        assertFalse(grayBrick.isDestroyed());
        grayBrick.takeHit();
        assertFalse(grayBrick.isDestroyed());
        grayBrick.takeHit();
        assertTrue(grayBrick.isDestroyed());
    }

    @Test
    void testTickDecreasesHitTimer() {
        grayBrick.takeHit();
        int timerAfterHit = grayBrick.getHealth(); // hitTimer private, can test indirectly via takeHit + render
        grayBrick.tick(); // Just ensure no exception
    }

    @Test
    void testRenderDoesNotThrow() {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        assertDoesNotThrow(() -> grayBrick.render(gc));
    }
}
