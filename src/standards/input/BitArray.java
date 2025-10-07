package standards.input;

/**
 * The {@code BitArray} class represents a compact array of bits.
 * It allows bit-level access and manipulation using an underlying {@code byte[]} array.
 * <p>
 * This implementation provides methods to:
 * <ul>
 *   <li>Get or set individual bits</li>
 *   <li>Print the binary representation of stored bytes</li>
 *   <li>Inspect size, length, and unused bit remainder</li>
 * </ul>
 *
 * <p>Usage example:
 * <pre>
 * BitArray bits = new BitArray(16);
 * bits.set(3, true);
 * boolean val = bits.get(3); // returns true
 * bits.print();
 * </pre>
 *
 * Modified from Joshua Crotts & Andrew Matzureff's Standards Java Game Library Source Code
 */
public class BitArray {

  /** The array of bytes storing bit data. */
  private final byte[] bytes;

  /** The number of bytes required to store the bit array. */
  private final int length;

  /** The total number of bits represented by this array. */
  private final int size;

  /**
   * Constructs a {@code BitArray} with the specified bit capacity.
   *
   * @param size the number of bits this array can hold
   */
  public BitArray(int size) {
    length = length(size);
    bytes = new byte[length];
    this.size = size;
  }

  /**
   * Calculates the number of bytes required to store the given number of bits.
   *
   * @param bits number of bits
   * @return the number of bytes needed
   */
  private int length(int bits) {
    if (bits < 1) {
      return 0;
    }
    return (bits >> 3) + (bits % 8 == 0 ? 0 : 1);
  }

  /**
   * Computes the byte index corresponding to a given bit index.
   *
   * @param bits bit index
   * @return byte index within the internal byte array
   */
  private int index(int bits) {
    if (bits < 1) {
      return 0;
    }
    return bits >> 3;
  }

  /**
   * Returns the binary string representation of a byte value.
   *
   * @param bits integer value representing one byte (0–255)
   * @return an 8-character binary string (e.g., "01101001")
   */
  private String binaryString(int bits) {
    char[] buf = new char[8];
    for (int offset = 0; offset < 8; offset++) {
      buf[offset] = (char) ('0' + ((bits >> (7 - offset)) & 1));
    }
    return new String(buf, 0, 8);
  }

  /**
   * Returns the number of bytes used to represent this bit array.
   *
   * @return the length in bytes
   */
  public int length() {
    return length;
  }

  /**
   * Returns the number of bits this array can store.
   *
   * @return the total bit capacity
   */
  public int size() {
    return size;
  }

  /**
   * Returns the number of unused bits (padding bits)
   * that extend beyond the declared {@code size()}.
   *
   * @return the remainder bits count
   */
  public int remainder() {
    return (length(size) << 3) - size;
  }

  /**
   * Retrieves the value of a bit at a specified position.
   *
   * @param bit the bit index (0-based)
   * @return {@code true} if the bit is set, {@code false} otherwise
   */
  public boolean get(int bit) {
    if (bit >= size + remainder() || bit < 0) {
      return false;
    }
    int index = index(bit);
    int offset = bit - (index << 3);
    return (bytes[index] & (byte) (128 >>> offset)) != 0;
  }

  /**
   * Sets or clears the bit at the specified index.
   *
   * @param bit the bit index (0-based)
   * @param val {@code true} to set the bit, {@code false} to clear it
   */
  public void set(int bit, boolean val) {
    if (bit >= size + remainder() || bit < 0) {
      return;
    }
    int index = index(bit);
    int offset = (bit - (index << 3));
    if (val) {
      bytes[index] |= (byte) (0x80 >>> offset);
    } else {
      bytes[index] &= (byte) (~(0x80 >>> offset));
    }
  }

  /**
   * Prints the binary representation of the byte containing the given bit index.
   *
   * @param bit the bit index
   */
  public void printByte(int bit) {
    bit = index(bit);
    System.out.println("\n[" + bit + "]:\t\t[" + binaryString(0xff & bytes[bit]) + "]\n");
  }

  /**
   * Prints the state of a single bit, including its position,
   * byte index, and boolean value.
   *
   * @param bit the bit index
   */
  public void printBit(int bit) {
    boolean value = get(bit);
    int bucket = index(bit);
    int position = bit - (bucket << 3);
    if (bit % 8 == 0) {
      printByte(bit);
    }
    System.out.println(" " + position + "\t\t\t\t" + value + "\t" + bit);
  }

  /**
   * Prints a summary of the bit array, including its size,
   * length, remainder, and each bit's state.
   */
  public void print() {
    double valueb;
    char prefb;
    double unitb;
    double valueB;
    char prefB;
    double unitB;

    // Compute bit units (b, Kb, Mb, Gb)
    switch (Integer.toString(size).length()) {
      case 1:
      case 2:
      case 3:
        unitb = 1;
        prefb = (char) 0;
        break;
      case 4:
      case 5:
      case 6:
        unitb = 1000;
        prefb = 'K';
        break;
      case 7:
      case 8:
      case 9:
        unitb = 1000000;
        prefb = 'M';
        break;
      default:
        unitb = 1000000000;
        prefb = 'G';
    }

    valueb = size / unitb;

    // Compute byte units (B, KB, MB, GB)
    switch (Integer.toString(length).length()) {
      case 1:
      case 2:
      case 3:
        unitB = 1;
        prefB = (char) 0;
        break;
      case 4:
      case 5:
      case 6:
        unitB = 1000;
        prefB = 'K';
        break;
      case 7:
      case 8:
      case 9:
        unitB = 1000000;
        prefB = 'M';
        break;
      default:
        unitB = 1000000000;
        prefB = 'G';
    }

    valueB = length / unitB;

    System.out.println("\n\n\nSize: " + valueb + prefb + 'b' + " (" + valueB + prefB + 'B' + " +" + remainder() + 'b' + ")");
    System.out.println("Length: " + (int) Math.ceil(valueB) + prefB + 'B');
    System.out.println("\n\nPosition\tBucket\t\tValue\tIndex\n");

    for (int i = 0; i < size; i++) {
      printBit(i);
    }
  }

  /**
   * Simple test method to demonstrate the usage of {@link BitArray}.
   * <p>
   * This method:
   * <ul>
   *   <li>Creates two {@code BitArray} objects of different sizes</li>
   *   <li>Sets specific bit patterns</li>
   *   <li>Prints their binary representation before and after modification</li>
   * </ul>
   */
  public static void test() {
    BitArray bits0 = new BitArray(256);
    BitArray bits1 = new BitArray(50);

    for (int i = 0; i < bits0.size() + bits0.remainder(); i++) {
      if (i % 4 != 0) {
        bits0.set(i, true);
      }
    }
    bits0.print();

    for (int i = 0; i < bits0.size() + bits0.remainder(); i++) {
      if (i % 2 == 0) {
        bits0.set(i, false);
      }
    }
    bits0.print();

    for (int i = 0; i < bits1.size() + bits1.remainder(); i++) {
      if (i % 4 != 0) {
        bits1.set(i, true);
      }
    }
    bits1.print();

    for (int i = 0; i < bits1.size() + bits1.remainder(); i++) {
      if (i % 2 == 0) {
        bits1.set(i, false);
      }
    }
    bits1.print();
  }
}
