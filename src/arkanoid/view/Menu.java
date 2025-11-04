package arkanoid.view;

import arkanoid.controller.Game;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;

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
        Font customFont = Font.loadFont(
                new File("media/font/Pixeboy-z8XGD.ttf").toURI().toString(), 36
        );

        // Create buttons
        Button playButton = new Button("Play");
        Button exitButton = new Button("Exit");

        // Apply font and style
        playButton.setFont(customFont);
        exitButton.setFont(customFont);
        playButton.setStyle("-fx-text-fill: black; -fx-background-color: transparent;");
        exitButton.setStyle("-fx-text-fill: black; -fx-background-color: transparent;");

        // Actions
        playButton.setOnAction(e -> startGame(primaryStage));
        exitButton.setOnAction(e -> exitGame());

        // Layout to hold the buttons
        VBox buttonLayout = new VBox(20);

        StackPane root = new StackPane();
        root.getChildren().addAll(background.getImageView(), buttonLayout);

        buttonLayout.getChildren().addAll(playButton, exitButton);
        buttonLayout.setStyle("-fx-alignment: center;");

        // Create and return the scene
        Scene scene = new Scene(root, 800, 600);
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
