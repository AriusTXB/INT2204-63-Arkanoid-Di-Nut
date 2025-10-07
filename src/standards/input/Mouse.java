package standards.input;

import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * The {@code Mouse} class represents a mouse input device in a JavaFX environment.
 * <p>
 * It extends {@link InputDevice} and listens for mouse press, release, movement,
 * drag, and scroll events. Each mouse button is tracked as a binary state
 * (pressed or released), and the cursor position (relative and absolute) is updated
 * on every event.
 *
 * <p>Usage example:
 * <pre>
 * Scene scene = new Scene(root);
 * Mouse mouse = new Mouse(scene);
 * </pre>
 *
 * <p>This implementation mimics the logic of the AWT version but is fully compatible
 * with JavaFX’s event-driven architecture.
 *
 * Modified from Joshua Crotts & Andrew Matzureff's Standards Java Game Library Source Code
 */
public class Mouse extends InputDevice {

  /** The maximum number of mouse buttons or tracked inputs supported. */
  private static final int MAX_BUTTONS = 0x10;

  /** X position relative to the JavaFX scene. */
  private int x = -1;

  /** Y position relative to the JavaFX scene. */
  private int y = -1;

  /** X position on the user’s screen (absolute). */
  private int ax = -1;

  /** Y position on the user’s screen (absolute). */
  private int ay = -1;

  /**
   * Constructs a new {@code Mouse} instance and attaches JavaFX event handlers
   * to the given {@link Scene}.
   *
   * @param scene the JavaFX {@link Scene} on which mouse events will be listened to
   */
  public Mouse(Scene scene) {
    super("JavaFX Mouse", MAX_BUTTONS);
    registerHandlers(scene);
  }

  /**
   * Registers all mouse-related event handlers to the given JavaFX {@link Scene}.
   *
   * @param scene the JavaFX scene to attach handlers to
   */
  private void registerHandlers(Scene scene) {
    scene.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onPressed);
    scene.addEventHandler(MouseEvent.MOUSE_RELEASED, this::onReleased);
    scene.addEventHandler(MouseEvent.MOUSE_MOVED, this::onMoved);
    scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::onDragged);
    scene.addEventHandler(MouseEvent.MOUSE_ENTERED, this::onEntered);
    scene.addEventHandler(MouseEvent.MOUSE_EXITED, this::onExited);
    scene.addEventHandler(ScrollEvent.SCROLL, this::onScroll);
  }

  /**
   * Updates mouse position data using the given {@link MouseEvent}.
   *
   * @param e the {@link MouseEvent} to extract position data from
   */
  private void update(MouseEvent e) {
    x = (int) e.getSceneX();
    y = (int) e.getSceneY();
    ax = (int) e.getScreenX();
    ay = (int) e.getScreenY();
  }

  /**
   * Called when a mouse button is pressed.
   *
   * @param e the {@link MouseEvent} containing button and position data
   */
  private void onPressed(MouseEvent e) {
    update(e);
    MouseButton button = e.getButton();
    set(button.ordinal(), true);
  }

  /**
   * Called when a mouse button is released.
   *
   * @param e the {@link MouseEvent} containing button and position data
   */
  private void onReleased(MouseEvent e) {
    update(e);
    MouseButton button = e.getButton();
    set(button.ordinal(), false);
  }

  /**
   * Called when the mouse moves within the scene.
   *
   * @param e the {@link MouseEvent} containing movement data
   */
  private void onMoved(MouseEvent e) {
    update(e);
  }

  /**
   * Called when the mouse is dragged (moved with a button pressed).
   *
   * @param e the {@link MouseEvent} containing drag data
   */
  private void onDragged(MouseEvent e) {
    update(e);
  }

  /**
   * Called when the mouse enters the scene window.
   *
   * @param e the {@link MouseEvent} representing the enter event
   */
  private void onEntered(MouseEvent e) {
    update(e);
  }

  /**
   * Called when the mouse exits the scene window.
   *
   * @param e the {@link MouseEvent} representing the exit event
   */
  private void onExited(MouseEvent e) {
    update(e);
  }

  /**
   * Called when the mouse wheel is scrolled.
   * <p>
   * This implementation currently logs the scroll delta but does not modify internal state.
   *
   * @param e the {@link ScrollEvent} containing scroll information
   */
  private void onScroll(ScrollEvent e) {
    // You can add scroll delta handling here if needed
    // Example: System.out.println("Scrolled: " + e.getDeltaY());
  }

  /**
   * Returns the current horizontal position of the mouse relative to the scene.
   *
   * @return the scene-relative X coordinate
   */
  public int getMouseX() {
    return this.x;
  }

  /**
   * Returns the current vertical position of the mouse relative to the scene.
   *
   * @return the scene-relative Y coordinate
   */
  public int getMouseY() {
    return this.y;
  }

  /**
   * Returns the absolute horizontal position of the mouse on the user’s screen.
   *
   * @return the screen-space X coordinate
   */
  public int getAbsoluteMouseX() {
    return this.ax;
  }

  /**
   * Returns the absolute vertical position of the mouse on the user’s screen.
   *
   * @return the screen-space Y coordinate
   */
  public int getAbsoluteMouseY() {
    return this.ay;
  }
}
