package arkanoid.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class GUI extends Application {

    private Background background;

    @Override
    public void start(Stage primaryStage) {
        // Create the root Pane
        StackPane root = new StackPane();

        // Create the dynamic background (ensure Background class handles the Pane correctly)
        background = new Background(root);

        // Load the first background video
        background.loadVideo("resources/background_video1.mp4");

        // Create Play button to start or change background video
        Button playButton = new Button("Play");
        playButton.setOnAction(e -> {
            // Change the background video dynamically
            background.loadVideo("resources/background_video2.mp4");
        });

        // Create Exit button to close the application
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> primaryStage.close());  // Use close() to gracefully shut down

        // Layout the buttons in a vertical stack (VBox)
        VBox buttonLayout = new VBox(10);  // 10px spacing between buttons
        buttonLayout.getChildren().addAll(playButton, exitButton);
        buttonLayout.setAlignment(Pos.CENTER);  // Center the buttons in the window

        // Add the background and buttons to the root
        root.getChildren().addAll(background, buttonLayout);

        // Create the scene and add it to the stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Game GUI with Dynamic Video Background");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}
