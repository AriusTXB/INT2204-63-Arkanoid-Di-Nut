package arkanoid.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUI extends Application {

    private Background background;

    @Override
    public void start(Stage primaryStage) {
        // Create the root Pane
        Pane root = new Pane();

        // Create the dynamic background
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
        exitButton.setOnAction(e -> System.exit(0));

        // Layout the buttons in a vertical stack (VBox)
        StackPane buttonLayout = new StackPane();
        buttonLayout.getChildren().addAll(playButton, exitButton);

        // Set the position of buttons (e.g., centered in the middle of the screen)
        buttonLayout.setTranslateX(400);  // Horizontal center
        buttonLayout.setTranslateY(300);  // Vertical center

        // Add buttons and background to the root
        root.getChildren().add(buttonLayout);

        // Create the scene and add it to the stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Game GUI with Dynamic Video Background");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
