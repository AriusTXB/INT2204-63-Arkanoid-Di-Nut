package arkanoid.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.layout.Region;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.util.Duration;
import java.io.File;

public class Background extends Region {
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private ImageView imageView;
    private Pane root;
    private String filePath;
    private Timeline videoTimeline;
    private double videoSpeed = 1.0;  // Mặc định tốc độ video là 1x (bình thường)

    public Background(Pane root) {
        this.root = root;
    }

    public Background(String filePath, boolean isVideo) {
        this.filePath = filePath;
        if (isVideo) {
            initializeVideoBackground();
        } else {
            initializeImageBackground();
        }
    }

    // Tải video
    private void initializeVideoBackground() {
        // Kiểm tra xem tệp video có tồn tại không
        if (!new File(filePath).exists()) {
            System.err.println("Video file not found at path: " + filePath);
            return;
        }

        // Tải và phát video
        Media media = new Media(new File(filePath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView = new MediaView(mediaPlayer);

        // Thêm MediaView vào màn hình
        this.getChildren().add(mediaView);
        mediaView.setFitWidth(this.getPrefWidth());
        mediaView.setFitHeight(this.getPrefHeight());

        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Lặp lại video
        mediaPlayer.play();

        // Tạo Timeline để di chuyển video (nếu cần) và điều chỉnh tốc độ
        videoTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.1 / videoSpeed), e -> updateVideoPosition())
        );
        videoTimeline.setCycleCount(Timeline.INDEFINITE);
        videoTimeline.play();
    }

    // Tải hình ảnh
    private void initializeImageBackground() {
        // Kiểm tra xem tệp hình ảnh có tồn tại không
        if (!new File(filePath).exists()) {
            System.err.println("Image file not found at path: " + filePath);
            return;
        }

        // Tải và hiển thị hình ảnh
        Image image = new Image(new File(filePath).toURI().toString());
        imageView = new ImageView(image);

        // Thêm ImageView vào màn hình
        this.getChildren().add(imageView);
        imageView.setFitWidth(this.getPrefWidth());
        imageView.setFitHeight(this.getPrefHeight());
    }

    // Cập nhật vị trí video (nếu cần)
    private void updateVideoPosition() {
        if (mediaView.getLayoutX() + mediaView.getFitWidth() <= 0) {
            mediaView.setLayoutX(this.getPrefWidth());
        }
        mediaView.setLayoutX(mediaView.getLayoutX() - 1);  // Di chuyển video sang trái
    }

    // Thiết lập tốc độ video
    public void setVideoSpeed(double speed) {
        if (speed <= 0) {
            System.err.println("Speed must be greater than 0.");
            return;
        }

        videoSpeed = speed;
        if (videoTimeline != null) {
            // Dừng Timeline cũ và tạo Timeline mới với tốc độ mới
            videoTimeline.stop();
            videoTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.1 / videoSpeed), e -> updateVideoPosition())
            );
            videoTimeline.setCycleCount(Timeline.INDEFINITE);
            videoTimeline.play();
        }
    }

    // Tải lại video (nếu cần)
    public void loadVideo(String newVideoPath) {
        if (!new File(newVideoPath).exists()) {
            System.err.println("Video file not found at path: " + newVideoPath);
            return;
        }

        filePath = newVideoPath;
        Media newMedia = new Media(new File(filePath).toURI().toString());
        mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(newMedia);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    // Tải lại hình ảnh (nếu cần)
    public void loadImage(String newImagePath) {
        if (!new File(newImagePath).exists()) {
            System.err.println("Image file not found at path: " + newImagePath);
            return;
        }

        filePath = newImagePath;
        Image newImage = new Image(new File(filePath).toURI().toString());
        imageView.setImage(newImage);
    }

    public double getVideoSpeed() {
        return videoSpeed ;
    }

    public Object getVideoTimeline() {
        return videoTimeline ;
    }
}
