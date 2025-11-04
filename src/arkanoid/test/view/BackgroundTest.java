package arkanoid.test.view;

import arkanoid.view.Background;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

class BackgroundTest {
    private Background background;
    private String validImagePath;
    private String invalidImagePath;

    @BeforeEach
    void setUp() {
        // Tạo ảnh giả lập cho test (nếu file tồn tại sẵn thì bỏ qua)
        validImagePath = "media/img/BG2.png";
        invalidImagePath = "media/img/invalid.png";

        // Đảm bảo file tồn tại để test không lỗi
        File f = new File(validImagePath);
        if (!f.exists()) {
            System.err.println("⚠️ Warning: Test image not found at " + validImagePath);
        }
    }

    @Test
    void testInitializeImageBackground_ValidPath() {
        background = new Background(validImagePath);
        assertNotNull(background, "Background object should be created.");

        assertFalse(background.getChildrenUnmodifiable().isEmpty(),
                "Background should contain ImageView when path is valid.");

        ImageView imageView = (ImageView) background.getChildrenUnmodifiable().get(0);
        assertNotNull(imageView.getImage(), "Image should be loaded.");
        assertEquals(validImagePath, background.getFilePath(),
                "File path should match the provided valid image path.");
    }

    @Test
    void testInitializeImageBackground_InvalidPath() {
        background = new Background(invalidImagePath);
        assertNotNull(background, "Background object should still be created.");
        assertTrue(background.getChildrenUnmodifiable().isEmpty(),
                "No ImageView should be added when path is invalid.");
    }

    @Test
    void testLoadImage_ValidPath() {
        background = new Background(validImagePath);
        String newPath = validImagePath;  // Giả sử cùng ảnh, test API

        background.loadImage(newPath);
        assertEquals(newPath, background.getFilePath(),
                "File path should update to new path after loadImage().");

        ImageView imageView = background.getImageView();
        assertNotNull(imageView.getImage(), "Image should be loaded after calling loadImage().");
    }

    @Test
    void testLoadImage_InvalidPath() {
        background = new Background(validImagePath);
        background.loadImage(invalidImagePath);

        // Đường dẫn hợp lệ không bị đổi vì ảnh mới lỗi
        assertEquals(validImagePath, background.getFilePath(),
                "File path should remain unchanged if new image path is invalid.");
    }

    @Test
    void testGetFilePath() {
        background = new Background(validImagePath);
        assertEquals(validImagePath, background.getFilePath(),
                "getFilePath() should return correct image path.");
    }
}
