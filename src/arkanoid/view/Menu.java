package arkanoid.view;

import arkanoid.controller.Game;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu {

    // Constructor to initialize Menu
    public Menu(Stage primaryStage) {
        // Menu UI setup code, to be used in ArkanoidRunner
        getMenuScene(primaryStage);
    }

    // This method will return the menu scene to ArkanoidRunner
    public void getMenuScene(Stage primaryStage) {
        // Create buttons for Play and Exit
        Button playButton = new Button("Play");
        Button exitButton = new Button("Exit");

        // Set actions for buttons
        playButton.setOnAction(e -> startGame(primaryStage));
        exitButton.setOnAction(e -> exitGame());

        // Layout to hold the buttons
        VBox buttonLayout = new VBox(20);
        buttonLayout.getChildren().addAll(playButton, exitButton);
        buttonLayout.setStyle("-fx-alignment: center;");

        // Create and return the scene
        Scene scene = new Scene(buttonLayout, 300, 250);
        primaryStage.setScene(scene);
    }

    public void startGame(Stage primaryStage) {
        // Game logic to transition to the game screen
        Game game = new Game(1);  // Start the game with difficulty level 1

        // Set up the game scene and start the game
        Scene gameScene = new Scene(game.getCanvas().getParent(), 800, 600);
        primaryStage.setScene(gameScene);
    }

    public void exitGame() {
        System.exit(0); // Exit the game
    }
}
