package standards.commands;

import java.util.LinkedList;
import standards.input.InputDevice;

/**
 * The {@code Command} class represents an abstract input command that can bind to
 * one or more {@link InputDevice}s (e.g., keyboard, mouse, controller) and track
 * their button/key states over time. It provides bit-level control over binding,
 * activation, focus grouping, and event execution (press, release, down, double-tap).
 * <p>
 * Commands are updated via {@link #update(float)}, which executes all registered
 * commands each frame. This class also supports multi-device binding, focus
 * management, and tap interval detection.
 * </p>
 *
 * <p><b>Core Concepts:</b></p>
 * <ul>
 *   <li>Each command can bind up to three device inputs simultaneously.</li>
 *   <li>Devices are registered and managed statically through {@link #DEVICES}.</li>
 *   <li>Each bit field in {@link #keys} encodes device, key, state, and focus information.</li>
 * </ul>
 *
 * <p>This design allows for high-performance polling and state tracking suitable
 * for real-time applications such as games or simulations.</p>
 *
 *  Modified from Joshua Crotts & Andrew Matzureff's Standards Java Game Library Source Code
 */
public abstract class Command {

  /** Registered Input Devices (max 8). */
  public static final InputDevice[] DEVICES = new InputDevice[8];

  /** Global list of all active commands. */
  public static final LinkedList<Command> COMMANDS = new LinkedList<>();

  /** Bit masks and offsets for key, device, and focus management. */
  public static final long KEY_MASK = 0xffffL;     // 2-byte key range
  public static final long DEVICE_MASK = 0x7L;     // 3-bit device range
  public static final long FOCUS_MASK = 0x7L;      // 3-bit group range

  /** Key bind slot 0 offsets. */
  public static final int KEY_0_OFF = 0;
  public static final int DID_0_OFF = 48;
  public static final int ON_0_OFF = 57;

  /** Key bind slot 1 offsets. */
  public static final int KEY_1_OFF = 16;
  public static final int DID_1_OFF = 51;
  public static final int ON_1_OFF = 58;

  /** Key bind slot 2 offsets. */
  public static final int KEY_2_OFF = 32;
  public static final int DID_2_OFF = 54;
  public static final int ON_2_OFF = 59;

  /** Focus group and pause state offsets. */
  public static final int FOCUS_OFF = 60;
  public static final int PAUSE_OFF = 63;
  public static final int DK_OFF = 16;

  /** Focus group identifiers (0–7). */
  public static final long FOCUS_GROUP_0 = 0;
  public static final long FOCUS_GROUP_1 = 1;
  public static final long FOCUS_GROUP_2 = 2;
  public static final long FOCUS_GROUP_3 = 3;
  public static final long FOCUS_GROUP_4 = 4;
  public static final long FOCUS_GROUP_5 = 5;
  public static final long FOCUS_GROUP_6 = 6;
  public static final long FOCUS_GROUP_7 = 7;

  /** Current active focus group mask. */
  public static int FOCUSED = -1;

  /** Human-readable name of the command. */
  protected String name;

  /** Strike frequency required for double-click/tap recognition. */
  protected long interval = 0;

  /** 64-bit bitstring of most recent key/button states. */
  protected long states = 0;

  /** Encoded key binding and focus bit field. */
  protected long keys = 0;

  /** Current number of active bits (pressed inputs). */
  protected byte bits = 0;

  // ---------------------------------------------------------------------------
  // Accessors and Focus Management
  // ---------------------------------------------------------------------------

  /** @return The display name of this command. */
  public String getName() {
    return name;
  }

  /** @return All currently registered input devices. */
  public static InputDevice[] getInputDevices() {
    return DEVICES;
  }

  /**
   * Temporarily disables (masks off) one or more focus groups.
   *
   * @param groups bitmask of focus groups to suspend
   */
  public static void suspend(int groups) {
    FOCUSED &= ~groups;
  }

  /**
   * Re-enables (unmasks) one or more focus groups.
   *
   * @param groups bitmask of focus groups to resume
   */
  public static void resume(int groups) {
    FOCUSED |= groups;
  }

  /**
   * Sets the current active focus groups explicitly.
   *
   * @param groups bitmask representing active focus groups
   */
  public static void setFocused(int groups) {
    FOCUSED = groups;
  }

  // ---------------------------------------------------------------------------
  // Device Registration
  // ---------------------------------------------------------------------------

  /**
   * Registers an input device within the global device table.
   *
   * @param id the input device to register
   * @return the assigned index (0–7) or -1 if registration failed
   */
  public static int register(InputDevice id) {
    if (id == null) {
      return -1;
    }
    int vacant = -1;
    for (int i = 0; i < DEVICES.length; i++) {
      if (DEVICES[i] == id) {
        return i;
      } else if (DEVICES[i] == null) {
        vacant = i;
      }
    }
    if (vacant >= 0) {
      DEVICES[vacant] = id;
    }
    return vacant;
  }

  /**
   * Deregisters an input device and clears it from all bound commands.
   *
   * @param id the device to remove
   * @return {@code true} if the device was successfully removed, {@code false} otherwise
   */
  public static boolean deregister(InputDevice id) {
    if (id == null) return false;
    for (int i = 0; i < DEVICES.length; i++) {
      if (DEVICES[i] == id) {
        for (Command c : COMMANDS) {
          c.clear(i);
        }
        DEVICES[i] = null;
        return true;
      }
    }
    return false;
  }

  // ---------------------------------------------------------------------------
  // Binding and State Management
  // ---------------------------------------------------------------------------

  /** Clears all states and resets this command to default. */
  public void clear() {
    states = 0;
    bits = 0;
    setFlags(0, ON_0_OFF, DEVICE_MASK);
  }

  /** Internal helper to clear bindings for a specific device index. */
  private void clear(int id) {
    if (checkFlags(id, DID_0_OFF, DEVICE_MASK)) setFlags(0, ON_0_OFF, 1);
    if (checkFlags(id, DID_1_OFF, DEVICE_MASK)) setFlags(0, ON_1_OFF, 1);
    if (checkFlags(id, DID_2_OFF, DEVICE_MASK)) setFlags(0, ON_2_OFF, 1);
    else return;

    int k1 = (int) getFlags(KEY_1_OFF, KEY_MASK);
    int k2 = (int) getFlags(KEY_2_OFF, KEY_MASK);
    int d1 = (int) getFlags(DID_1_OFF, DEVICE_MASK);
    int d2 = (int) getFlags(DID_2_OFF, DEVICE_MASK);

    switch ((int) getFlags(ON_0_OFF, 7)) {
      case 0b010 -> {
        setFlags(k1, KEY_0_OFF, KEY_MASK);
        setFlags(0b001, ON_0_OFF, 7);
        setFlags(d1, DID_0_OFF, DEVICE_MASK);
      }
      case 0b100 -> {
        setFlags(k2, KEY_0_OFF, KEY_MASK);
        setFlags(0b001, ON_0_OFF, 7);
        setFlags(d2, DID_0_OFF, DEVICE_MASK);
      }
      case 0b101 -> {
        setFlags(k2, KEY_1_OFF, KEY_MASK);
        setFlags(0b011, ON_0_OFF, 7);
        setFlags(d2, DID_1_OFF, DEVICE_MASK);
      }
      case 0b110 -> {
        setFlags(k1, KEY_0_OFF, KEY_MASK);
        setFlags(k2, KEY_1_OFF, KEY_MASK);
        setFlags(0b011, ON_0_OFF, 7);
        setFlags(d1, DID_0_OFF, DEVICE_MASK);
        setFlags(d2, DID_1_OFF, DEVICE_MASK);
      }
      default -> {}
    }
  }

  /** Removes a command from the global list. */
  public boolean remove(Command i) {
    return COMMANDS.remove(i);
  }

  /** Bitwise flag comparison helper. */
  protected boolean checkFlags(long val, int offset, long mask) {
    return (((keys >>> offset) & mask) == val);
  }

  /** Bitwise flag setter helper. */
  protected void setFlags(long val, int offset, long mask) {
    keys = (keys & ~(mask << offset)) | (val << offset);
  }

  /** Bitwise flag retrieval helper. */
  protected long getFlags(int offset, long mask) {
    return ((keys >>> offset) & mask);
  }

  // ---------------------------------------------------------------------------
  // Binding Setup
  // ---------------------------------------------------------------------------

  /**
   * Binds a command to a given device and key with default flags.
   *
   * @param id  the input device to bind
   * @param key the key code to bind
   * @return {@code true} if binding was successful
   */
  public boolean bind(InputDevice id, int key) {
    return bind(id, key & (int) KEY_MASK, 0x8000_0000_0000_0000L);
  }

  /**
   * Binds a command to a given device and key with custom flags.
   *
   * @param id    the input device to bind
   * @param key   the key code to bind
   * @param flags additional bitwise binding flags
   * @return {@code true} if binding was successful
   */
  public boolean bind(InputDevice id, int key, long flags) {
    int registered = register(id);
    if (registered >= 0) {
      switch ((int) getFlags(ON_0_OFF, 7)) {
        case 0b000 -> {
          setFlags(key, KEY_0_OFF, KEY_MASK);
          setFlags(1, ON_0_OFF, 1);
          setFlags(registered, DID_0_OFF, DEVICE_MASK);
        }
        case 0b001 -> {
          setFlags(key, KEY_1_OFF, KEY_MASK);
          setFlags(1, ON_1_OFF, 1);
          setFlags(registered, DID_1_OFF, DEVICE_MASK);
        }
        case 0b011 -> {
          setFlags(key, KEY_2_OFF, KEY_MASK);
          setFlags(1, ON_2_OFF, 1);
          setFlags(registered, DID_2_OFF, DEVICE_MASK);
        }
        case 0b111 -> {
          int k1 = (int) getFlags(KEY_1_OFF, KEY_MASK);
          int k2 = (int) getFlags(KEY_2_OFF, KEY_MASK);
          setFlags(k1, KEY_0_OFF, KEY_MASK);
          setFlags(k2, KEY_1_OFF, KEY_MASK);
          setFlags(key, KEY_2_OFF, KEY_MASK);
          int d1 = (int) getFlags(DID_1_OFF, DEVICE_MASK);
          int d2 = (int) getFlags(DID_2_OFF, DEVICE_MASK);
          setFlags(d1, DID_0_OFF, DEVICE_MASK);
          setFlags(d2, DID_1_OFF, DEVICE_MASK);
          setFlags(registered, DID_2_OFF, DEVICE_MASK);
        }
        default -> { return false; }
      }
      COMMANDS.add(this);
      keys |= flags;
      return true;
    }
    return false;
  }

  // ---------------------------------------------------------------------------
  // Event Hooks (Override in Subclasses)
  // ---------------------------------------------------------------------------

  protected void pressed(float delta) {}
  protected void down(float delta) {}
  protected void released(float delta) {}
  protected void up(float delta) {}
  protected void doubleTapped(float delta) {}
  protected void tick(float delta) {}

  // ---------------------------------------------------------------------------
  // Tap Interval and Execution Logic
  // ---------------------------------------------------------------------------

  private void setTapInterval(int ticks) {
    if ((ticks & 63) != ticks) return;
    interval = 8L << ticks;
  }

  private void setTapInterval(float timeScale) {
    setTapInterval((int) (timeScale * 60));
  }

  private void processTap(float delta) {
    boolean lookingFor = false;
    int gestures = 0;
    long interval = this.interval & ~0xfL;
    for (int i = 4; i != 0; i <<= 1) {
      if (i > interval) {
        released(delta);
        return;
      }
      boolean press = (states & i) != 0;
      if (lookingFor == press) {
        lookingFor = !lookingFor;
        gestures++;
        if (gestures == 3) {
          doubleTapped(delta);
          return;
        }
      }
    }
  }

  /**
   * Executes the command’s input logic for the current frame.
   * @param delta the time elapsed since the last update
   */
  protected void execute(float delta) {
    int input0 = (int) getFlags(DID_0_OFF, DEVICE_MASK);
    int input1 = (int) getFlags(DID_1_OFF, DEVICE_MASK);
    int input2 = (int) getFlags(DID_2_OFF, DEVICE_MASK);
    int key = 0;
    int doubleTapEnabled = 0x1;

    if (DEVICES[input0] != null && checkFlags(1, ON_0_OFF, 1))
      key |= DEVICES[input0].get((int) getFlags(KEY_0_OFF, KEY_MASK)) ? 1 : 0;
    if (DEVICES[input1] != null && checkFlags(1, ON_1_OFF, 1))
      key |= DEVICES[input1].get((int) getFlags(KEY_1_OFF, KEY_MASK)) ? 1 : 0;
    if (DEVICES[input2] != null && checkFlags(1, ON_2_OFF, 1))
      key |= DEVICES[input2].get((int) getFlags(KEY_2_OFF, KEY_MASK)) ? 1 : 0;

    bits = (byte) ((bits & 0xff) - (int) (states >>> 63) + key);
    states = (states << 1) | key;

    switch ((int) states & 0b11) {
      case 0b01 -> pressed(delta);
      case 0b10 -> {
        if ((interval & doubleTapEnabled) != 0) processTap(delta);
        else released(delta);
      }
      case 0b11 -> down(delta);
      default -> up(delta);
    }
    tick(delta);
  }

  /**
   * Updates all active commands for the current frame.
   *
   * @param delta time elapsed since the last update
   */
  public static void update(float delta) {
    for (Command c : COMMANDS) {
      c.execute(delta);
    }
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder(((name == null) ? "Unknown" : name) + ": ");
    boolean o0 = checkFlags(1, ON_0_OFF, 1);
    boolean o1 = checkFlags(1, ON_1_OFF, 1);
    boolean o2 = checkFlags(1, ON_2_OFF, 1);
    long i0 = getFlags(DID_0_OFF, DEVICE_MASK);
    long i1 = getFlags(DID_1_OFF, DEVICE_MASK);
    long i2 = getFlags(DID_2_OFF, DEVICE_MASK);
    String del = ((o0 && o1) || (o0 && o2) || (o1 && o2)) ? ", " : "";
    String k0 = (o0 ? DEVICES[(int) i0] + " (" + (char) getFlags(KEY_0_OFF, KEY_MASK) + ")" + del : "");
    String k1 = (o1 ? DEVICES[(int) i1] + " (" + (char) getFlags(KEY_1_OFF, KEY_MASK) + ")" + del : "");
    String k2 = (o2 ? DEVICES[(int) i2] + " (" + (char) getFlags(KEY_2_OFF, KEY_MASK) + ") " : "");
    str.append("Binds <").append(k0).append(k1).append(k2).append("> Group ")
        .append(getFlags(FOCUS_OFF, FOCUS_MASK)).append("; ").append(keys < 0);
    str.append(" (").append(Long.toBinaryString(keys)).append(")");
    return str.toString();
  }
}
