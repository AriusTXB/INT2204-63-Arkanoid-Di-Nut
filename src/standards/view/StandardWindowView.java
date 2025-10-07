package standards.view;

import standards.platform.StandardGame;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

/**
 * {@code StandardWindowView} manages the JavaFX window (Stage) that hosts
 * the game scene.
 *
 * Modified from Joshua Crotts & Andrew Matzureff's Standards Java Game Library Source Code
 */
public class StandardWindowView {

    /** The JavaFX stage (window) for the game. */
    private final Stage stage;

    /** The JavaFX scene graph container for game content. */
    private final Scene scene;

    /** The root layout pane that holds the game node. */
    private final Pane rootPane;

    /** Title of the window. */
    private String title;

    /** Width of the window in pixels. */
    private int width;

    /** Height of the window in pixels. */
    private int height;

    /**
     * Constructs a new game window for a given {@link StandardGame} instance.
     *
     * @param stage the JavaFX {@link Stage} (typically provided by Application.start()).
     * @param width the window width in pixels.
     * @param height the window height in pixels.
     * @param title the window title string.
     * @param game the {@link StandardGame} instance to render.
     */
    public StandardWindowView(Stage stage, int width, int height, String title, StandardGame game) {
        this.stage = stage;
        this.title = title;
        this.width = width;
        this.height = height;

        // Set up root layout and attach the game as a JavaFX Node
        this.rootPane = new StackPane();
        this.rootPane.getChildren().add(game);

        this.scene = new Scene(rootPane, width, height, Color.BLACK);
        this.stage.setScene(scene);
        this.stage.setTitle(title);
        this.stage.setResizable(false);

        centerWindow();
        this.stage.show();
    }

    /**
     * Centers the window on the user's screen.
     */
    private void centerWindow() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.stage.setX((screenBounds.getWidth() - width) / 2);
        this.stage.setY((screenBounds.getHeight() - height) / 2);
    }

    /**
     * Sets the background color of the window.
     *
     * @param color a {@link Color} to set as the window background.
     */
    public void setBackgroundColor(Color color) {
        Platform.runLater(() -> rootPane.setBackground(new Background(
                new BackgroundFill(color, null, null))));
    }

    /**
     * Returns the window width.
     *
     * @return the width in pixels.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets a new window width.
     *
     * @param width new width in pixels.
     */
    public void setWidth(int width) {
        this.width = width;
        Platform.runLater(() -> stage.setWidth(width));
    }

    /**
     * Returns the window height.
     *
     * @return the height in pixels.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets a new window height.
     *
     * @param height new height in pixels.
     */
    public void setHeight(int height) {
        this.height = height;
        Platform.runLater(() -> stage.setHeight(height));
    }

    /**
     * Returns the window title.
     *
     * @return the title string.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets a new window title.
     *
     * @param title new title string.
     */
    public void setTitle(String title) {
        this.title = title;
        Platform.runLater(() -> stage.setTitle(title));
    }

    /**
     * Returns the JavaFX {@link Stage} for this window.
     *
     * @return the primary stage.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Returns the root {@link Pane} used to contain the game node.
     *
     * @return the root pane.
     */
    public Pane getRootPane() {
        return rootPane;
    }

    /**
     * Returns the JavaFX {@link Scene} associated with this window.
     *
     * @return the current scene.
     */
    public Scene getScene() {
        return scene;
    }
}
