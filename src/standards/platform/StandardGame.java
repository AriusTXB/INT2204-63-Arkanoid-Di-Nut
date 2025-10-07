package standards.platform;

import standards.commands.Command;
import standards.input.Keyboard;
import standards.input.Mouse;
import standards.main.StandardDraw;
import standards.view.StandardWindowView;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class is the barebones blueprint for a game in Java. It includes the
 * render-loop, frames/updates per second information, keyboard/mouse input, the
 * screen, and other information.
 *
 * Modified from Joshua Crotts & Andrew Matzureff's Standards Java Game Library Source Code
 */
public abstract class StandardGame extends Canvas {

    /* Window encapsulation (Stage + Scene). */
    private final StandardWindowView window;

    /* Input handlers */
    private final Keyboard keyboard;
    private final Mouse mouse;

    /* JavaFX Animation loop */
    private AnimationTimer gameLoop;

    /* Game state */
    private boolean running = false;
    private int currentFPS = 0;
    private boolean consoleFPS = true;
    private boolean titleFPS = true;

    /**
     * Creates a StandardGame in JavaFX.
     *
     * @param width  window width
     * @param height window height
     * @param title  window title
     * @param stage  JavaFX Stage
     */
    public StandardGame(int width, int height, String title, javafx.stage.Stage stage) {
        super(width, height);

        this.window = new StandardWindowView(stage, width, height, title, this);
        this.keyboard = new Keyboard(window.getScene());
        this.mouse = new Mouse(window.getScene());

        // Optional: improve sharpness on high DPI
        this.setFocusTraversable(true);
    }

    /**
     * Starts the game loop using JavaFX's AnimationTimer.
     */
    public void StartGame() {
        if (running) return;

        running = true;
        final GraphicsContext gc = this.getGraphicsContext2D();
        StandardDraw.Renderer = gc;

        gameLoop = new AnimationTimer() {
            private long lastTime = System.nanoTime();
            private long fpsTimer = System.currentTimeMillis();
            private int frames = 0;
            private int updates = 0;
            private double delta = 0.0;
            private final double nsPerTick = 1_000_000_000.0 / 60.0; // ~60 updates/sec

            @Override
            public void handle(long now) {
                delta += (now - lastTime) / nsPerTick;
                lastTime = now;

                boolean renderable = false;
                while (delta >= 1) {
                    Command.update((float) delta);
                    tick();
                    updates++;
                    delta--;
                    renderable = true;
                }

                if (renderable) {
                    frames++;

                    // Clear screen
                    gc.setFill(Color.BLACK);
                    gc.fillRect(0, 0, getGameWidth(), getGameHeight());

                    // Render scene
                    render();
                }

                // FPS counter
                if (System.currentTimeMillis() - fpsTimer >= 1000) {
                    fpsTimer += 1000;
                    currentFPS = frames;

                    if (titleFPS)
                        window.setTitle(window.getTitle() + " | " + updates + " ups, " + frames + " fps");

                    if (consoleFPS)
                        System.out.println(window.getTitle() + " | " + updates + " ups, " + frames + " fps");

                    frames = 0;
                    updates = 0;
                }
            }
        };

        gameLoop.start();
    }

    /**
     * Stops the JavaFX game loop.
     */
    public void StopGame() {
        if (!running) return;
        gameLoop.stop();
        running = false;
    }

    /** Update game logic each tick. */
    public abstract void tick();

    /** Render game content each frame. */
    public abstract void render();

    /** Returns window view (Stage + Scene). */
    public StandardWindowView getWindow() {
        return window;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public int getFPS() {
        return currentFPS;
    }

    public int getGameWidth() {
        return (int) getWidth();
    }

    public int getGameHeight() {
        return (int) getHeight();
    }

    public void printFramesToConsole(boolean print) {
        this.consoleFPS = print;
    }

    public void printFramesToTitle(boolean print) {
        this.titleFPS = print;
    }
}
