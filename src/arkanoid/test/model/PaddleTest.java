package arkanoid.test.model;

import arkanoid.model.Paddle;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PaddleTest extends Application {
    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Paddle paddle = new Paddle(350, 550);
        paddle.setLarge(true); // Bật chế độ fade để test

        Scene scene = new Scene(new Pane(canvas));
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) paddle.moveLeft();
            if (e.getCode() == KeyCode.RIGHT) paddle.moveRight();
        });
        scene.setOnKeyReleased(e -> paddle.stop());

        stage.setScene(scene);
        stage.setTitle("Paddle Fade Test");
        stage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, 800, 600);
                paddle.tick();
                paddle.render(gc);
            }
        }.start();
    }

    public static void main(String[] args) {
        launch();
    }
}
