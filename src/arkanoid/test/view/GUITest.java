package arkanoid.test.view;

import arkanoid.view.GUI;
import arkanoid.view.Background;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class GUITest {

    private static JFXPanel fxPanel; // Khởi động JavaFX Toolkit
    private GUI gui;
    private Stage stage;

    @BeforeAll
    static void initFX() {
        fxPanel = new JFXPanel(); // Bắt buộc để khởi động JavaFX runtime trong môi trường test
    }

    @BeforeEach
    void setUp() throws Exception {
        Platform.runLater(() -> {
            stage = new Stage();
            gui = new GUI();
            gui.start(stage);
        });
        Thread.sleep(1000); // Đợi JavaFX init xong
    }

    @Test
    void testInitialBackgroundImageLoaded() throws Exception {
        Platform.runLater(() -> {
            Background bg = gui.getBackground();
            assertNotNull(bg, "Background should be initialized.");
            assertTrue(
                    bg.getFilePath().contains("background_video1") ||
                            bg.getFilePath().contains("BG1"),
                    "Initial background should be loaded correctly."
            );
        });
        Thread.sleep(500);
    }

    @Test
    void testPlayButtonChangesBackground() throws Exception {
        Platform.runLater(() -> {
            Scene scene = stage.getScene();
            // Lấy nút "Play" từ danh sách button
            Button playBtn = (Button) scene.getRoot().lookupAll(".button").stream()
                    .map(node -> (Button) node)
                    .filter(btn -> "Play".equals(btn.getText()))
                    .findFirst()
                    .orElse(null);

            assertNotNull(playBtn, "Play button should exist.");
            playBtn.fire(); // Giả lập click

            Background bg = gui.getBackground();
            assertNotNull(bg);
            assertTrue(
                    bg.getFilePath().contains("background_video2") ||
                            bg.getFilePath().contains("BG2"),
                    "Background should change after Play button pressed."
            );
        });
        Thread.sleep(500);
    }

    @Test
    void testExitButtonClosesStage() throws Exception {
        Platform.runLater(() -> {
            Scene scene = stage.getScene();
            // Lấy nút "Exit"
            Button exitBtn = (Button) scene.getRoot().lookupAll(".button").stream()
                    .map(node -> (Button) node)
                    .filter(btn -> "Exit".equals(btn.getText()))
                    .findFirst()
                    .orElse(null);

            assertNotNull(exitBtn, "Exit button should exist.");
            exitBtn.fire();
            assertFalse(stage.isShowing(), "Stage should close after Exit button pressed.");
        });
        Thread.sleep(500);
    }

    @AfterEach
    void tearDown() throws Exception {
        Platform.runLater(() -> {
            if (stage != null) stage.close();
        });
        Thread.sleep(500);
    }
}
