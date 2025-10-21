package arkanoid.test.view;

import arkanoid.view.Background;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

class BackgroundTest {
    private Background background;

    @BeforeEach
    void setUp() {
        // Cài đặt trước khi mỗi test case chạy
    }

    @Test
    void testInitializeVideoBackground_ValidPath() {
        // Đảm bảo rằng video được tải đúng khi đường dẫn hợp lệ
        background = new Background("path/to/valid/video.mp4", true);
        assertNotNull(background);
        // Kiểm tra rằng video đã được khởi tạo
        MediaView mediaView = (MediaView) background.getChildrenUnmodifiable().get(0);
        assertNotNull(mediaView);
        MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
        assertNotNull(mediaPlayer);
        assertTrue(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING);
    }

    @Test
    void testInitializeVideoBackground_InvalidPath() {
        // Đảm bảo rằng video không được tải khi đường dẫn không hợp lệ
        background = new Background("path/to/invalid/video.mp4", true);
        assertNull(background.getChildrenUnmodifiable().get(0));  // Không có MediaView được thêm vào
    }

    @Test
    void testInitializeImageBackground_ValidPath() {
        // Đảm bảo rằng hình ảnh được tải đúng khi đường dẫn hợp lệ
        background = new Background("path/to/valid/image.jpg", false);
        assertNotNull(background);
        Image image = ((ImageView) background.getChildrenUnmodifiable().get(0)).getImage();
        assertNotNull(image);
    }

    @Test
    void testInitializeImageBackground_InvalidPath() {
        // Đảm bảo rằng hình ảnh không được tải khi đường dẫn không hợp lệ
        background = new Background("path/to/invalid/image.jpg", false);
        assertNull(background.getChildrenUnmodifiable().get(0));  // Không có ImageView được thêm vào
    }

    @Test
    void testSetVideoSpeed() {
        // Đảm bảo rằng tốc độ video có thể thay đổi và ảnh hưởng đến việc di chuyển video
        background = new Background("path/to/valid/video.mp4", true);
        background.setVideoSpeed(2.0);  // Tăng tốc video gấp đôi
        assertEquals(2.0, background.getVideoSpeed());  // Kiểm tra tốc độ video mới

        // Kiểm tra xem timeline có được cập nhật lại với tốc độ mới không
        assertNotNull(background.getVideoTimeline());
    }

    @Test
    void testSetVideoSpeed_InvalidSpeed() {
        // Đảm bảo rằng tốc độ âm hoặc bằng 0 không hợp lệ
        background = new Background("path/to/valid/video.mp4", true);
        background.setVideoSpeed(0);  // Tốc độ không hợp lệ
        assertEquals(1.0, background.getVideoSpeed());  // Tốc độ phải giữ nguyên là 1.0
    }

    @Test
    void testLoadVideo_ValidPath() {
        // Kiểm tra tải lại video khi đường dẫn hợp lệ
        background = new Background("path/to/valid/video.mp4", true);
        background.loadVideo("path/to/another/video.mp4");

        MediaView mediaView = (MediaView) background.getChildrenUnmodifiable().get(0);
        MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
        assertNotNull(mediaPlayer);
        assertTrue(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING);
    }

    @Test
    void testLoadVideo_InvalidPath() {
        // Kiểm tra tải lại video khi đường dẫn không hợp lệ
        background = new Background("path/to/valid/video.mp4", true);
        background.loadVideo("path/to/invalid/video.mp4");

        assertNull(background.getChildrenUnmodifiable().get(0));  // Không có MediaView được thêm vào
    }
}
