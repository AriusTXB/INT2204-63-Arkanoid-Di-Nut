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

        // [MODIFIED] Create the dynamic background using image API (not video)
        // If your Background supports Background(String) use that; otherwise use loadImage().
        // Using constructor that accepts filepath (preferred).
        background = new Background("media/img/BG2.png"); // [MODIFIED]

        // If your Background only has no-arg constructor and loadImage method, use:
        // background = new Background(root);
        // background.loadImage("media/img/BG2.png"); // [MODIFIED alternative]

        // Create Play button to start or change background image (was video)
        Button playButton = new Button("Play");
        playButton.setOnAction(e -> {
            // Change the background image dynamically
            background.loadImage("media/img/BG1.png"); // [MODIFIED] use loadImage instead of loadVideo
            // or background.setImage("media/img/BG1.png");
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
        primaryStage.setTitle("Game GUI with Dynamic Image Background");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }

    public Background getBackground() {
        return background;
    }
}
