package standards.input;

import standards.commands.Command;

/**
 * The {@code InputDevice} class provides a base abstraction for input devices
 * such as keyboards, mice, or game controllers.
 * <p>
 * It manages a {@link BitArray} that tracks the on/off (boolean) state of each
 * input element, such as keys or buttons. The class also maintains an internal
 * alert counter to detect when input states change.
 * <p>
 * This class is designed to be extended by concrete device implementations.
 *
 * <h2>Example</h2>
 * <pre>{@code
 * public class Keyboard extends InputDevice {
 *     public Keyboard() {
 *         super("Default Keyboard", 256);
 *     }
 *
 *     public void pressKey(int code) {
 *         set(code, true);
 *     }
 *
 *     public void releaseKey(int code) {
 *         set(code, false);
 *     }
 * }
 * }</pre>
 *  Modified from Joshua Crotts & Andrew Matzureff's Standards Java Game Library Source Code
 */
public abstract class InputDevice {

  /** Stores the digital (on/off) states of all keys or buttons. */
  private final BitArray keys;

  /** The name assigned to this input device. */
  private final String name;

  /** Unique identifier registered via the {@link Command} system. */
  private final int id;

  /** Counter tracking the number of active alerts (state changes). */
  private short alert;

  /**
   * Constructs a new {@code InputDevice} with a given name and number of inputs.
   *
   * @param name the display name of the input device
   * @param size the number of input elements (e.g., keys or buttons)
   */
  public InputDevice(String name, int size) {
    keys = new BitArray(size);
    alert = 0;
    id = Command.register(this);
    this.name = name;
  }

  /**
   * Checks whether any input alerts are currently active.
   * <p>
   * An alert occurs when an input changes from pressed to released or vice versa.
   *
   * @return {@code true} if there are active alerts, {@code false} otherwise
   */
  public boolean alerted() {
    return alert != 0;
  }

  /**
   * Returns the number of current alerts as an unsigned 16-bit integer.
   *
   * @return the total count of active alerts
   */
  public int alerts() {
    return alert & 0xffff;
  }

  /**
   * Updates the alert counter and key state when an input value changes.
   *
   * @param k the index of the input key
   * @param v {@code true} if the key is pressed, {@code false} if released
   */
  protected void alert(int k, boolean v) {
    if (keys.get(k) != v) {
      if (v) {
        alert++;
      } else {
        alert--;
      }
      keys.set(k, v);
    }
  }

  /**
   * Returns a string representation of this input device, including its
   * unique ID and assigned name.
   *
   * @return a formatted string describing this input device
   */
  @Override
  public String toString() {
    return "InputDevice[" + id + "]: " + (name == null ? "Unknown" : name);
  }

  /**
   * Retrieves the current state of the specified input key.
   *
   * @param key the index of the input key
   * @return {@code true} if the key is currently pressed, {@code false} otherwise
   */
  public boolean get(int key) {
    if (key >= 0 && key < keys.size()) {
      return keys.get(key);
    }
    return false;
  }

  /**
   * Sets the state of a specific input key and triggers an alert if it changes.
   *
   * @param k the index of the input key
   * @param v {@code true} if the key is pressed, {@code false} if released
   */
  protected void set(int k, boolean v) {
    if (k >= 0 && k < keys.size()) {
      alert(k, v);
    }
  }
}
