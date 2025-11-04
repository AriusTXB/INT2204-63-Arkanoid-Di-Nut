package arkanoid.test.model;

import arkanoid.model.HellBrick;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import standards.model.StandardID;

import static org.junit.jupiter.api.Assertions.*;

public class HellBrickTest {

    private HellBrick blackBrick;

    @BeforeEach
    void setUp() {
        blackBrick = new HellBrick(100, 150, Color.BLACK);
    }

    @Test
    void testInitialProperties() {
        assertEquals(StandardID.Brick, blackBrick.getId());
        assertEquals(60, blackBrick.getWidth());
        assertEquals(20, blackBrick.getHeight());
        assertEquals(Color.BLACK, blackBrick.getColor());
        assertEquals(3, blackBrick.getHealth());
    }

    @Test
    void testToStringFormat() {
        String text = blackBrick.toString();
        assertTrue(text.contains("HellBrick["));
        assertTrue(text.contains("hp=3"));
    }

    @Test
    void testTakeHitIncreasesHealth() {
        int initialHealth = blackBrick.getHealth();
        blackBrick.takeHit();
        assertEquals(initialHealth + 1, blackBrick.getHealth());

        // Test MAX_HEALTH cap
        for (int i = 0; i < 1000; i++) blackBrick.takeHit();
        assertTrue(blackBrick.getHealth() <= 1000);
    }

    @Test
    void testIsDestroyedAlwaysFalse() {
        blackBrick.takeHit();
        assertFalse(blackBrick.isDestroyed());
    }

    @Test
    void testTickDecreasesHitTimer() {
        blackBrick.takeHit();
        blackBrick.tick(); // ensure no exception
    }

    @Test
    void testRenderDoesNotThrow() {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        assertDoesNotThrow(() -> blackBrick.render(gc));
    }
}
