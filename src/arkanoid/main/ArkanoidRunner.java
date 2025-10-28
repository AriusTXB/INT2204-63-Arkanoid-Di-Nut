package arkanoid.main;

import arkanoid.view.Menu;
import javafx.application.Application;
import javafx.stage.Stage;

public class ArkanoidRunner extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Initialize the main menu and pass primaryStage
        Menu menu = new Menu(primaryStage);

        // Show the initial menu
        primaryStage.setTitle("Arkanoid Game");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
