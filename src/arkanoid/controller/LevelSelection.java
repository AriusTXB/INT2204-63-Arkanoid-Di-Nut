package arkanoid.controller;

import arkanoid.view.Menu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Optional;

public class LevelSelection {

    private Stage stage;
    private Scene scene;

    public LevelSelection(Stage stage) {
        this.stage = stage;
        initScene();
    }

    private void initScene() {
        // GridPane để xếp các nút Level
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);

        Font font = Font.font("Arial", 24);

        // Tạo 10 nút Level
        for (int i = 1; i <= 10; i++) {
            Button btn = new Button("Level " + i);
            btn.setFont(font);
            btn.setPrefSize(140, 60);

            int level = i;
            btn.setOnAction(e -> {
                if (level >= 6) {
                    // Levels 6-10: Coming soon
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Coming Soon");
                    alert.setHeaderText(null);
                    alert.setContentText("Level " + level + " is coming soon!");
                    alert.showAndWait();
                } else {
                    // Level 1-5: Bắt đầu game
                    Game game = new Game(stage, level);

                    // SỬA LỖI: Cần gọi StartGame() để khởi động game loop
                    game.StartGame();

                    // Game tự init trong constructor, không cần gọi initGame() nữa
                    stage.setScene(game.getWindow().getScene());
                    stage.show();
                }
            });

            grid.add(btn, (i - 1) % 5, (i - 1) / 5);
        }

        // Nút ẩn góc phải trên
        Button hiddenButton = new Button();
        hiddenButton.setPrefSize(20, 20);
        hiddenButton.setStyle("-fx-background-color: transparent;");
        hiddenButton.setOnAction(e -> promptPassword());

        StackPane root = new StackPane(grid, hiddenButton);
        StackPane.setAlignment(hiddenButton, Pos.TOP_RIGHT);

        scene = new Scene(root, 800, 600);
    }

    /** Prompt mật khẩu user/pass */
    private void promptPassword() {
        TextInputDialog dialogUser = new TextInputDialog();
        dialogUser.setTitle("Admin Access");
        dialogUser.setHeaderText("Enter username:");
        dialogUser.setContentText("Username:");

        Optional<String> resultUser = dialogUser.showAndWait();
        if (resultUser.isEmpty() || !resultUser.get().equals("BDQ123123")) return;

        TextInputDialog dialogPass = new TextInputDialog();
        dialogPass.setTitle("Admin Access");
        dialogPass.setHeaderText("Enter password:");
        dialogPass.setContentText("Password:");

        Optional<String> resultPass = dialogPass.showAndWait();
        if (resultPass.isEmpty() || !resultPass.get().equals("ImissNLPtask")) return;

        // Nếu đúng user/pass
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Admin");
        alert.setHeaderText(null);
        alert.setContentText("Access granted!");
        alert.showAndWait();

        // Bạn có thể mở giao diện admin ở đây
        System.out.println("Admin mode unlocked!");
    }

    /** Phương thức hiển thị scene */
    public void show() {
        stage.setScene(scene);
        stage.show();
    }

    public Scene getScene() {
        return scene;
    }
}