package standards.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

/**
 * {@code StdOps} is a utility class providing common mathematical and graphical operations
 * similar to {@link java.lang.Math}. It includes random number generation, value clamping,
 * normalization, mouse position checking, font loading, image loading, and fast approximations
 * for square roots and inverse square roots.
 *
 * <p>It has been refactored for JavaFX, replacing AWT dependencies with JavaFX classes such as
 * {@link javafx.scene.text.Font} and {@link javafx.scene.image.Image}.
 *
 * <p><b>Key Features:</b>
 * <ul>
 *   <li>Random integer and double generation</li>
 *   <li>Range clamping and normalization</li>
 *   <li>Font loading from file or input stream</li>
 *   <li>Image loading using JavaFX {@link Image}</li>
 *   <li>Fast square root and inverse square root approximations</li>
 * </ul>
 */
public abstract class StdOps {

  private static final int SQRT_MAGIC = 0x5f3759df;

  /**
   * Returns a random integer between {@code min} and {@code max}, inclusive.
   *
   * @param min minimum value
   * @param max maximum value
   * @return random integer between {@code min} and {@code max}
   * @throws IllegalArgumentException if {@code min >= max}
   */
  public static int randomInt(int min, int max) {
    if (min >= max) {
      throw new IllegalArgumentException("Max must be larger than min.");
    }
    return ThreadLocalRandom.current().nextInt(min, max + 1);
  }

  /**
   * Returns a random double between {@code min} and {@code max}, inclusive.
   *
   * @param min minimum value
   * @param max maximum value
   * @return random double between {@code min} and {@code max}
   * @throws IllegalArgumentException if {@code min >= max}
   */
  public static double randomDouble(double min, double max) {
    if (min >= max) {
      throw new IllegalArgumentException("Max must be larger than min.");
    }
    return ThreadLocalRandom.current().nextDouble(min, max + 1);
  }

  /**
   * Returns a random double between {@code 0.0} (inclusive) and {@code 1.0} (exclusive).
   *
   * @return random double between {@code 0.0} and {@code 1.0}
   */
  public static double randomDouble() {
    return ThreadLocalRandom.current().nextDouble();
  }

  /**
   * Checks whether a point ({@code mx}, {@code my}) lies within the given rectangle.
   *
   * @param mx mouse x-coordinate
   * @param my mouse y-coordinate
   * @param x rectangle x position
   * @param y rectangle y position
   * @param width rectangle width
   * @param height rectangle height
   * @return {@code true} if the point lies inside the rectangle; {@code false} otherwise
   */
  public static boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
    return ((mx > x) && (mx < x + width)) && ((my > y) && (my < y + height));
  }

  /**
   * Clamps a given value {@code num} within the range {@code [min, max]}.
   *
   * @param num the value to clamp
   * @param min minimum allowable value
   * @param max maximum allowable value
   * @return the clamped value
   */
  public static int clamp(int num, int min, int max) {
    return Math.max(min, Math.min(num, max));
  }

  /**
   * Normalizes a number {@code n} from the range {@code [oldMin, oldMax]} to {@code [newMin, newMax]}.
   *
   * @param n the number to normalize
   * @param oldMin the old minimum value
   * @param oldMax the old maximum value
   * @param newMin the new minimum value
   * @param newMax the new maximum value
   * @return normalized value in the new range
   * @throws IllegalArgumentException if {@code n} is outside the old range
   */
  public static double normalize(double n, double oldMin, double oldMax, double newMin, double newMax) {
    if (n < oldMin || n > oldMax) {
      throw new IllegalArgumentException("Number cannot be outside the range [" + oldMin + ", " + oldMax + "].");
    }
    return (((n - oldMin) * (newMax - newMin)) / (oldMax - oldMin)) + newMin;
  }

  /**
   * Normalizes a number {@code n} from the range {@code [oldMin, oldMax]} to {@code [0.0, 1.0]}.
   *
   * @param n the number to normalize
   * @param oldMin the old minimum value
   * @param oldMax the old maximum value
   * @return normalized value between {@code 0.0} and {@code 1.0}
   */
  public static double normalize(double n, double oldMin, double oldMax) {
    return normalize(n, oldMin, oldMax, 0.0, 1.0);
  }

  /**
   * Loads a JavaFX {@link Font} from a given file path with a specified size.
   *
   * @param path file path to the font
   * @param size desired font size
   * @return loaded {@link Font} object, or {@code null} if loading fails
   */
  public static Font initFont(String path, double size) {
    try {
      return Font.loadFont(new FileInputStream(path), size);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Loads a JavaFX {@link Font} from an {@link InputStream} with a specified size.
   *
   * @param stream input stream of the font
   * @param size desired font size
   * @return loaded {@link Font} object, or {@code null} if loading fails
   */
  public static Font initFont(InputStream stream, double size) {
    try {
      return Font.loadFont(stream, size);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Loads a JavaFX {@link Image} from a file path.
   *
   * @param path path to the image file
   * @return loaded {@link Image}, or {@code null} if loading fails
   */
  public static Image loadImage(String path) {
    try {
      return new Image(new FileInputStream(new File(path)));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Loads a JavaFX {@link Image} from an {@link InputStream}.
   *
   * @param stream input stream containing image data
   * @return loaded {@link Image}, or {@code null} if loading fails
   */
  public static Image loadImage(InputStream stream) {
    try {
      return new Image(stream);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Fast square root approximation using a bit-level hack similar to Quake III’s method.
   *
   * @param x input value
   * @return approximate square root of {@code x}
   */
  public static float fastSqrt(float x) {
    float xhalf = 0.5f * x;
    int i = Float.floatToIntBits(x);
    i = SQRT_MAGIC - (i >> 1);
    float y = Float.intBitsToFloat(i);
    y = y * (1.5f - xhalf * y * y);
    return x * y;
  }

  /**
   * Carmack’s fast inverse square root approximation.
   *
   * @param x input value
   * @return approximate inverse square root of {@code x}
   */
  public static double fastInvSqrt(double x) {
    double xhalf = 0.5d * x;
    long i = Double.doubleToLongBits(x);
    i = 0x5fe6ec85e7de30daL - (i >> 1);
    x = Double.longBitsToDouble(i);
    x *= (1.5d - xhalf * x * x);
    return x;
  }
}
