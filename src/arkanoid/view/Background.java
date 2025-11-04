package arkanoid.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import java.io.File;

public class Background extends Region {
    private ImageView imageView;
    private String filePath;

    public Background(String filePath) {
        this.filePath = filePath;
        initializeImageBackground();
    }


    /** Load image background */
    private void initializeImageBackground() {
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("Image file not found at path: " + filePath);
            return;
        }

        Image image = new Image(file.toURI().toString());
        imageView = new ImageView(image);

        this.getChildren().add(imageView);
        imageView.setFitWidth(getPrefWidth());
        imageView.setFitHeight(getPrefHeight());
        imageView.setPreserveRatio(false);
    }

    /** Change background image dynamically */
    public void loadImage(String newImagePath) {
        File file = new File(newImagePath);
        if (!file.exists()) {
            System.err.println("Image file not found at path: " + newImagePath);
            return;
        }

        filePath = newImagePath;
        Image newImage = new Image(file.toURI().toString());
        imageView.setImage(newImage);
    }

    /** Get current background path */
    public String getFilePath() {
        return filePath;
    }

    /** Optional getter (for testing, assertions, etc.) */
    public ImageView getImageView() {
        return imageView;
    }
}
