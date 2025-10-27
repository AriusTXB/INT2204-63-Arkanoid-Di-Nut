package arkanoid.test.view;

import arkanoid.view.GUI;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.api.FxRobot;

public class GUITest extends ApplicationTest {

    private GUI gui;

    @Override
    public void start(Stage primaryStage) throws Exception {
        gui = new GUI();
        gui.start(primaryStage);  // Start the GUI application for testing
    }

    @BeforeEach
    public void setUp() {
        // Ensure the GUI starts fresh before each test
        try {
            start(gui::start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testPlayButtonChangesVideo(FxRobot robot) {
        // Ensure the play button switches the background video when clicked
        Button playButton = robot.lookup(".button").queryAll().stream()
                .filter(button -> button.getText().equals("Play"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Play button not found"));

        // Simulate clicking the Play button
        robot.clickOn(playButton);

        // Verify that the video path has changed or that the video load is triggered.
        // This can be verified by checking that the video file is being loaded.
        // Assuming Background class logs or updates the path, verify its status here.
        assertTrue(gui.getBackground().getVideoPath().contains("background_video2"));
    }

    @Test
    void testExitButtonClosesStage(FxRobot robot) {
        // Ensure the Exit button closes the window when clicked
        Button exitButton = robot.lookup(".button").queryAll().stream()
                .filter(button -> button.getText().equals("Exit"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Exit button not found"));

        // Simulate clicking the Exit button
        robot.clickOn(exitButton);

        // Assert that the stage is closed (check the stage is no longer visible)
        Stage stage = (Stage) exitButton.getScene().getWindow();
        assertFalse(stage.isShowing(), "Stage should be closed.");
    }

    @Test
    void testInitialBackgroundVideoLoaded() {
        // Check that the initial background video is loaded
        assertNotNull(gui.getBackground(), "Background should be initialized.");
        // Assuming `Background` has a method like `getVideoPath()` to get the current video.
        assertTrue(gui.getBackground().getVideoPath().contains("background_video1"),
                "Initial video should be background_video1.");
    }
}
