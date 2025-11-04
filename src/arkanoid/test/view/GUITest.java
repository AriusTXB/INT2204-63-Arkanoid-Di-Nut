package arkanoid.test.view;

import arkanoid.view.GUI;
import arkanoid.view.Background;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.api.FxRobot;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class GUITest extends ApplicationTest {

    private GUI gui;

    @Override
    public void start(Stage primaryStage) throws Exception {
        gui = new GUI();
        gui.start(primaryStage);
    }

    @Test
    void testPlayButtonChangesImage(FxRobot robot) {
        // Lấy nút Play
        Button playButton = (Button) robot.lookup(".button").queryAll().stream()
                .filter(node -> ((Button) node).getText().equals("Play"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Play button not found"));

        // Nhấn nút
        robot.clickOn(playButton);

        // Kiểm tra background đã tồn tại
        assertNotNull(gui.getBackground());

        // Kiểm tra rằng đường dẫn ảnh đã thay đổi (BG1.png thay vì BG2.png)
        String filePath = gui.getBackground().getFilePath();
        assertTrue(filePath.contains("BG1") || filePath.contains("bg1"),
                "Expected image path to contain 'BG1', but got: " + filePath);
    }

    @Test
    void testExitButtonClosesStage(FxRobot robot) {
        // Lấy nút Exit
        Button exitButton = (Button) robot.lookup(".button").queryAll().stream()
                .filter(node -> ((Button) node).getText().equals("Exit"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Exit button not found"));

        // Nhấn nút
        robot.clickOn(exitButton);

        // Kiểm tra stage đã đóng
        Stage stage = (Stage) exitButton.getScene().getWindow();
        assertFalse(stage.isShowing(), "Stage should be closed.");
    }

    @Test
    void testInitialBackgroundImageLoaded() {
        // Kiểm tra khởi tạo ban đầu
        assertNotNull(gui.getBackground(), "Background should be initialized.");

        String filePath = gui.getBackground().getFilePath();
        assertTrue(filePath.contains("BG2") || filePath.contains("bg2"),
                "Expected initial image path to contain 'BG2', but got: " + filePath);
    }
}
