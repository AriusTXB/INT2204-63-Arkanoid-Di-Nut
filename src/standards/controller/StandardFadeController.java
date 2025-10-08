package standards.controller;

import standards.util.StdOps;
import javafx.scene.paint.Color;

/**
 * The {@code StandardFadeController} class provides a mechanism to continuously
 * interpolate between two colors over time. It can be used to produce smooth
 * fade-in and fade-out color transitions for visual effects in games or GUIs.
 *
 * Modified from Joshua Crotts & Andrew Matzureff's Standards Java Game Library Source Code
 */
public class StandardFadeController {

    /**
     * Current interpolation progress between color one and color two.
     * Ranges from 0.0 to 1.0.
     */
    private double time;

    /**
     * Boolean flag to track the direction of the fade:
     * <ul>
     *   <li>{@code true} - transitioning from COLOR_ONE to COLOR_TWO</li>
     *   <li>{@code false} - transitioning back from COLOR_TWO to COLOR_ONE</li>
     * </ul>
     */
    private boolean firstColor;

    /**
     * Determines how fast the transition between colors occurs.
     * A smaller value results in a slower fade, while a larger value speeds it up.
     */
    private final double ALPHA;

    /** The first color used in the fade transition. */
    private final Color COLOR_ONE;

    /** The second color used in the fade transition. */
    private final Color COLOR_TWO;

    /**
     * Constructs a {@code StandardFadeController} instance that smoothly
     * transitions between two colors.
     *
     * @param c1 the first color to fade from
     * @param c2 the second color to fade to
     * @param alpha the fade speed (typically between 0.01 and 0.1)
     */
    public StandardFadeController(Color c1, Color c2, double alpha) {
        this.time = 0.0;
        this.firstColor = true;
        this.COLOR_ONE = c1;
        this.COLOR_TWO = c2;
        this.ALPHA = alpha;
    }

    /**
     * Computes the next interpolated color in the fade sequence.
     * Each call updates the internal time and direction to create
     * a continuous back-and-forth fade effect.
     *
     * @return the interpolated {@link Color} between {@code COLOR_ONE} and {@code COLOR_TWO}
     */
    public Color combine() {
        // Update interpolation progress based on fade direction
        if (firstColor) {
            time += ALPHA;
            if (time >= 1.0) {
                time = 1.0;
                firstColor = false;
            }
        } else {
            time -= ALPHA;
            if (time <= 0.0) {
                time = 0.0;
                firstColor = true;
            }
        }

        // Interpolate RGB components
        double r = (1 - time) * COLOR_ONE.getRed() + time * COLOR_TWO.getRed();
        double g = (1 - time) * COLOR_ONE.getGreen() + time * COLOR_TWO.getGreen();
        double b = (1 - time) * COLOR_ONE.getBlue() + time * COLOR_TWO.getBlue();

        // Clamp values and return a new color
        return Color.color(
                StdOps.clamp(r, 0.0, 1.0),
                StdOps.clamp(g, 0.0, 1.0),
                StdOps.clamp(b, 0.0, 1.0)
        );
    }

    /**
     * @return the current interpolation progress (0.0–1.0)
     */
    public double getTime() {
        return this.time;
    }

    /**
     * @return {@code true} if the controller is fading from COLOR_ONE to COLOR_TWO
     */
    public boolean isFirstColor() {
        return this.firstColor;
    }

    /**
     * @return the fade speed coefficient
     */
    public double getAlpha() {
        return this.ALPHA;
    }

    /**
     * @return the first base color
     */
    public Color getColorOne() {
        return this.COLOR_ONE;
    }

    /**
     * @return the second base color
     */
    public Color getColorTwo() {
        return this.COLOR_TWO;
    }
}
