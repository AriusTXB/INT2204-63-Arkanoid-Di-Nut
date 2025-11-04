package arkanoid.view;

import arkanoid.controller.Game;
import arkanoid.model.SongBox;
import javafx.animation.ScaleTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Menu {

    private final SongBox songBox = new SongBox();

    // Constructor
    public Menu(Stage primaryStage) {
        getMenuScene(primaryStage);
    }

    // Hiển thị menu
    public void getMenuScene(Stage primaryStage) {

        // Background
        Background background = new Background("media/img/BG2.png");

        // Font
        Font.loadFont(getClass().getResourceAsStream("/font/PIPERLAND.ttf"), 36);

        // Nút
        Button playButton = new Button("Play");
        Button exitButton = new Button("Exit");

        String baseStyle = """
            -fx-background-color: transparent;
            -fx-text-fill: #00FFFF;
            -fx-font-weight: bold;
            -fx-font-size: 36px;
            -fx-font-family: 'PIPERLAND';
            -fx-effect: dropshadow(gaussian, #0044FF, 10, 0.5, 0, 0);
            -fx-cursor: hand;
        """;

        playButton.setStyle(baseStyle);
        exitButton.setStyle(baseStyle);

        // Hover + âm thanh
        addHoverEffect(playButton, baseStyle);
        addHoverEffect(exitButton, baseStyle);

        // Click action
        playButton.setOnAction(e -> {
            songBox.playClick();
            startGame(primaryStage);
        });

        exitButton.setOnAction(e -> {
            songBox.playClick();
            exitGame();
        });

        // Layout
        VBox buttonLayout = new VBox(30, playButton, exitButton);
        buttonLayout.setStyle("-fx-alignment: center;");

        StackPane root = new StackPane(background.getImageView(), buttonLayout);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        songBox.loop("menu");
    }

    private void addHoverEffect(Button button, String baseStyle) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(150), button);
        scaleUp.setToX(1.15);
        scaleUp.setToY(1.15);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(150), button);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);

        button.setOnMouseEntered(e -> {
            songBox.playClick();
            button.setStyle(baseStyle.replace("#00FFFF", "#FFFFFF"));
            scaleUp.playFromStart();
        });

        button.setOnMouseExited(e -> {
            button.setStyle(baseStyle);
            scaleDown.playFromStart();
        });
    }

    public void startGame(Stage primaryStage) {
        songBox.stopAll();
        Game game = new Game(primaryStage);
        game.StartGame();
    }

    public void exitGame() {
        songBox.stopAll();
        System.exit(0);
    }
}
