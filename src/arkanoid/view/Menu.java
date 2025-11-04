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

        Background background = new Background("media/img/BG2.png");
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
        Scene scene = new Scene(buttonLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void startGame(Stage primaryStage) {
        Game game = new Game(primaryStage);
        game.StartGame();
    }

    public void exitGame() {
        System.exit(0); // Exit the game
    }
}
