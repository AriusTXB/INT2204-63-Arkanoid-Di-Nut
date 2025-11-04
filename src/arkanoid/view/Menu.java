package arkanoid.view;

import arkanoid.controller.Game;
import arkanoid.controller.LevelSelection;
import arkanoid.model.SongBox;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Menu {

    private final SongBox songBox = new SongBox();

    private static final String SECRET_USER = "BDQ123123";
    private static final String SECRET_PASS = "ImissNLPtask";

    public Menu(Stage primaryStage) {
        getMenuScene(primaryStage);
    }

    public void getMenuScene(Stage primaryStage) {

        // Background
        Background background = new Background("media/img/BG2.png");

        // Font
        Font.loadFont(getClass().getResourceAsStream("/font/PIPERLAND.ttf"), 36);

        // Buttons
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

        addHoverEffect(playButton, baseStyle);
        addHoverEffect(exitButton, baseStyle);

        playButton.setOnAction(e -> {
            songBox.playClick();
            startGame(primaryStage, 1); // Default level 1
        });

        exitButton.setOnAction(e -> {
            songBox.playClick();
            exitGame();
        });

        VBox buttonLayout = new VBox(30, playButton, exitButton);
        buttonLayout.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(background.getImageView(), buttonLayout);

        // Hidden button góc phải trên
        Button hiddenButton = new Button();
        hiddenButton.setOpacity(0); // tàng hình
        hiddenButton.setPrefSize(50, 50); // vùng nhấn
        hiddenButton.setStyle("-fx-background-color: transparent;");
        StackPane.setAlignment(hiddenButton, Pos.TOP_RIGHT);
        hiddenButton.setOnAction(e -> showSecretLogin(primaryStage));
        root.getChildren().add(hiddenButton);

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

    private void showSecretLogin(Stage primaryStage) {
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Secret Access");

        Label userLabel = new Label("User: ");
        Label passLabel = new Label("Pass: ");
        TextField userField = new TextField();
        PasswordField passField = new PasswordField();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(userLabel, 0, 0);
        grid.add(userField, 1, 0);
        grid.add(passLabel, 0, 1);
        grid.add(passField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return SECRET_USER.equals(userField.getText()) && SECRET_PASS.equals(passField.getText());
            }
            return false;
        });

        dialog.showAndWait().ifPresent(authenticated -> {
            if (authenticated) {
                songBox.playClick();
                showLevelSelection(primaryStage);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect credentials!");
                alert.showAndWait();
            }
        });
    }

    private void showLevelSelection(Stage primaryStage) {
        LevelSelection levelSelection = new LevelSelection(primaryStage);
        levelSelection.show();
    }

    public void startGame(Stage primaryStage, int level) {
        songBox.stopAll();
        Game game = new Game(primaryStage, level);
        game.StartGame();
    }

    public void exitGame() {
        songBox.stopAll();
        System.exit(0);
    }
}
