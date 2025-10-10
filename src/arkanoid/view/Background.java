package arkanoid.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Background {
    private Pane root;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private Timeline timeline;
    private double speed = 1.5;  // Speed of video movement (if needed)

    public Background(Pane root) {
        this.root = root;
        this.mediaView = new MediaView();
        root.getChildren().add(mediaView);  // Add MediaView to the root
        startBackgroundMovement();
    }

    // Method to load and play video dynamically based on provided media path
    public void loadVideo(String videoPath) {
        // If a video is already playing, stop it first
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        // Load the new video from the provided path
        Media media = new Media("file:" + videoPath);  // Provide the correct path
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);  // Make video loop indefinitely
        mediaPlayer.setAutoPlay(true);  // Start playing immediately

        mediaView.setMediaPlayer(mediaPlayer);  // Set the media player to media view
        mediaView.setFitWidth(800);  // Set the size of the video (adjust as needed)
        mediaView.setFitHeight(600);
    }

    // Start background movement if you want to apply any dynamic change or speed
    private void startBackgroundMovement() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.017), e -> updateBackgroundPosition())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    // Update background position (optional: for moving the video or any other effect)
    private void updateBackgroundPosition() {
        // You can apply transformations, such as moving or scaling the video dynamically
        // For example, you could move the video background horizontally
        mediaView.setX(mediaView.getX() - speed);
        if (mediaView.getX() <= -mediaView.getFitWidth()) {
            mediaView.setX(root.getWidth());  // Reset position if video has moved out of screen
        }
    }

    // Stop the background video (if needed)
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            timeline.stop();
        }
    }
}
