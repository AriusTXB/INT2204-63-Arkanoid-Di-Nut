import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Menu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button playButton = new Button("Play");
        Button exitButton = new Button("Exit");

        playButton.setOnAction(e -> startGame(primaryStage));
        exitButton.setOnAction(e -> exitGame());

        VBox buttonLayout = new VBox(20);
        buttonLayout.getChildren().addAll(playButton, exitButton);
        buttonLayout.setStyle("-fx-alignment: center;");

        // Dùng VBox làm layout chính, không dùng StackPane nữa
        Scene scene = new Scene(buttonLayout, 300, 250);

        primaryStage.setTitle("Game Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void startGame(Stage primaryStage) {
        // Chuyển sang màn hình nền đen
        StackPane blackScreenLayout = new StackPane();
        blackScreenLayout.setStyle("-fx-background-color: black;");
        Scene blackScreen = new Scene(blackScreenLayout, 800, 600);
        primaryStage.setScene(blackScreen);

        // Tạo Timeline để đếm 10 giây và tắt màn hình
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(10), e -> {
                    System.exit(0);  // Tắt ứng dụng sau 10 giây
                })
        );
        timeline.setCycleCount(1);
        timeline.play();  // Chạy timeline

        primaryStage.setTitle("Black Screen - 10 seconds");
    }

    private void exitGame() {
        System.out.println("Game Exited");
        System.exit(0);
    }
}