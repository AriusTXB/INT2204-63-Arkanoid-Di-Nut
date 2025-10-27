package arkanoid.main;

import arkanoid.view.Menu;
import arkanoid.controller.*;
import arkanoid.view.GUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ArkanoidRunner extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Initialize the main menu
        Menu menu = new Menu();

        // Start the menu scene
        Scene menuScene = menu.getMenuScene();
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Arkanoid Game");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
