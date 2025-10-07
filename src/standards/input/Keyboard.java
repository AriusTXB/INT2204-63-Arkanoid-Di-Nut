package standards.input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * The {@code Keyboard} class represents a keyboard input device integrated with JavaFX.
 * <p>
 * It extends {@link InputDevice} and registers JavaFX event handlers to listen
 * for key press, release, and typed events from a {@link javafx.scene.Scene}.
 * Each key is tracked by its {@link KeyCode}.
 *
 * <p>Usage example:
 * <pre>
 * Scene scene = new Scene(root);
 * Keyboard keyboard = new Keyboard(scene);
 * </pre>
 *
 * Modified from Joshua Crotts & Andrew Matzureff's Standards Java Game Library Source Code
 */
public class Keyboard extends InputDevice {

  /**
   * The maximum number of character codes supported by this keyboard.
   * <p>
   * This corresponds to {@code 0x10000} (65,536), which covers the full Unicode Basic Multilingual Plane (BMP).
   */
  public static final int MAX_CHARS = 0x10000;

  /**
   * Constructs a new {@code Keyboard} and attaches key event listeners
   * to the specified JavaFX {@link Scene}.
   *
   * @param scene the JavaFX {@link Scene} to attach key listeners to
   */
  public Keyboard(Scene scene) {
    super("JavaFX Keyboard", MAX_CHARS);
    registerHandlers(scene);
  }

  /**
   * Registers JavaFX key event handlers for press, release, and typed events.
   *
   * @param scene the JavaFX {@link Scene} on which to listen for key events
   */
  private void registerHandlers(Scene scene) {
    // Key pressed → mark as active
    scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
      KeyCode code = e.getCode();
      set(code.getCode(), true);
    });

    // Key released → mark as inactive
    scene.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
      KeyCode code = e.getCode();
      set(code.getCode(), false);
    });

    // Key typed → log or process character input
    scene.addEventHandler(KeyEvent.KEY_TYPED, e -> {
      // You can add this to a key log or buffer if desired
      // Example: System.out.println("Typed: " + e.getCharacter());
    });
  }
}
