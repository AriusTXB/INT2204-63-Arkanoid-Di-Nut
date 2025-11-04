package standards.main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import standards.handler.StandardHandler;
import standards.model.StandardGameObject;
import standards.util.StdOps;

/**
 * {@code StandardDraw} provides a unified set of static helper methods for drawing
 * common 2D primitives, images, and text using JavaFX's {@link GraphicsContext}.
 * <p>
 * This class acts as an abstraction layer for simplified rendering calls, similar
 * to the AWT-based version but optimized for JavaFX. It supports drawing shapes,
 * images, and text with color and fill control.
 * </p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * GraphicsContext gc = canvas.getGraphicsContext2D();
 * StandardDraw.Renderer = gc;
 * 
 * StandardDraw.rect(50, 50, 100, 80, Color.BLUE, true);
 * StandardDraw.text("Hello, JavaFX!", 100, 200, "Arial", 20, Color.BLACK);
 * }</pre>
 *
 * @see javafx.scene.canvas.GraphicsContext
 * @see javafx.scene.paint.Color
 * @see javafx.scene.text.Font
 * Modified from Joshua Crotts & Andrew Matzureff's Standards Java Game Library Source Code
 */
public abstract class StandardDraw {

    /** The shared JavaFX rendering context used for all draw operations. */
    public static GraphicsContext Renderer;

    /**
     * Draws an image at the specified position.
     *
     * @param image the {@link Image} to draw
     * @param x     the x-coordinate of the image’s top-left corner
     * @param y     the y-coordinate of the image’s top-left corner
     */
    public static void image(Image image, double x, double y) {
        Renderer.drawImage(image, x, y);
    }

    /**
     * Draws a rectangle on the screen.
     *
     * @param x      x-coordinate of the top-left corner
     * @param y      y-coordinate of the top-left corner
     * @param width  rectangle width
     * @param height rectangle height
     * @param color  fill or stroke color (defaults to black if null)
     * @param fill   true to fill the rectangle, false to only draw its outline
     */
    public static void rect(double x, double y, double width, double height, Color color, boolean fill) {
        if (color == null) color = Color.BLACK;
        Renderer.setFill(color);
        Renderer.setStroke(color);

        if (fill) Renderer.fillRect(x, y, width, height);
        else Renderer.strokeRect(x, y, width, height);
    }

    /**
     * Draws an ellipse at the specified position.
     *
     * @param x      x-coordinate of the bounding box
     * @param y      y-coordinate of the bounding box
     * @param width  ellipse width
     * @param height ellipse height
     * @param color  fill or stroke color (defaults to black if null)
     * @param fill   true to fill the ellipse, false to draw only its outline
     */
    public static void ellipse(double x, double y, double width, double height, Color color, boolean fill) {
        if (color == null) color = Color.BLACK;
        Renderer.setFill(color);
        Renderer.setStroke(color);

        if (fill) Renderer.fillOval(x, y, width, height);
        else Renderer.strokeOval(x, y, width, height);
    }

    /**
     * Draws a polygon using the provided points.
     *
     * @param xPoints x-coordinates of vertices
     * @param yPoints y-coordinates of vertices
     * @param color   fill or stroke color (defaults to black if null)
     * @param fill    true to fill the polygon, false to draw only its outline
     */
    public static void polygon(double[] xPoints, double[] yPoints, Color color, boolean fill) {
        if (xPoints.length != yPoints.length)
            throw new IllegalArgumentException("Polygon coordinate arrays must be the same length.");

        if (color == null) color = Color.BLACK;
        Renderer.setFill(color);
        Renderer.setStroke(color);

        if (fill) Renderer.fillPolygon(xPoints, yPoints, xPoints.length);
        else Renderer.strokePolygon(xPoints, yPoints, xPoints.length);
    }

    /**
     * Draws a text string at the specified position using a custom font name and size.
     *
     * @param text  the text to display
     * @param x     the x-coordinate
     * @param y     the y-coordinate
     * @param font  the name of the font (e.g., "Arial")
     * @param size  font size
     * @param color the text color
     */
    public static void text(String text, double x, double y, String font, double size, Color color) {
        Renderer.setFill(color != null ? color : Color.BLACK);
        Renderer.setFont(Font.font(font != null ? font : "Arial", size));
        Renderer.fillText(text, x, y);
    }

    /**
     * Draws a text string at the specified position using a given {@link Font}.
     *
     * @param text  the text to draw
     * @param x     the x-coordinate
     * @param y     the y-coordinate
     * @param font  the {@link Font} instance (if null, defaults to Arial)
     * @param size  font size
     * @param color text color
     */
    public static void text(String text, double x, double y, Font font, double size, Color color) {
        Renderer.setFill(color != null ? color : Color.BLACK);
        Renderer.setFont(font != null ? Font.font(font.getName(), size) : Font.font("Arial", size));
        Renderer.fillText(text, x, y);
    }

    /**
     * Renders a {@link StandardGameObject} by calling its {@code render()} method.
     *
     * @param obj the game object to render
     */
    public static void Object(StandardGameObject obj) {
        obj.render(Renderer);
    }

    /**
     * Renders all elements in the given {@link StandardHandler}.
     *
     * @param handler the handler managing renderable objects
     */
    public static void Handler(StandardHandler handler) {
        handler.render(Renderer);
    }
    /**
     * Creates a JavaFX {@link Color} using AWT-style integer RGB values (0–255).
     *
     * @param r red component (0–255)
     * @param g green component (0–255)
     * @param b blue component (0–255)
     * @return a {@link Color} instance with full opacity (1.0)
     */
    public static Color Color(int r, int g, int b) {
        return Color.rgb(r, g, b, 1.0);
    }
    /**
     * Returns a randomly generated color.
     *
     * @return a {@link Color} with random RGB components
     */
    public static Color getRandomColor() {
        return Color.rgb(StdOps.randomInt(0, 255), StdOps.randomInt(0, 255), StdOps.randomInt(0, 255));
    }
    // --- Common color presets ---
  public static final Color RED = Color.RED;
  public static final Color PINK =  Color(255, 192, 203);
  public static final Color SALMON_PINK =  Color(255, 145, 164);
  public static final Color CORAL_PINK =  Color(248, 131, 121);
  public static final Color SALMON =  Color(250, 128, 114);
  public static final Color RED_PANTONE =  Color(237, 41, 57);
  public static final Color RED_CRAYOLA =  Color(238, 32, 77);
  public static final Color SCARLET =  Color(255, 36, 0);
  public static final Color RED_IMPERIAL =  Color(237, 41, 57);
  public static final Color INDIAN_RED =  Color(205, 92, 92);
  public static final Color SPANISH_RED =  Color(230, 0, 38);
  public static final Color DESIRE =  Color(234, 60, 83);
  public static final Color LUST =  Color(230, 32, 32);
  public static final Color CARMINE =  Color(150, 0, 24);
  public static final Color RUBY =  Color(224, 17, 95);
  public static final Color CRIMSON =  Color(220, 20, 60);
  public static final Color RUSTY_RED =  Color(218, 44, 67);
  public static final Color FIRE_ENGINE_RED =  Color(206, 32, 41);
  public static final Color CARDINAL_RED =  Color(196, 30, 58);
  public static final Color CHILI_RED =  Color(226, 61, 40);
  public static final Color CORNELL_RED =  Color(179, 27, 27);
  public static final Color FIRE_BRICK =  Color(178, 34, 34);
  public static final Color REDWOOD =  Color(164, 90, 82);
  public static final Color OU_CRIMSON_RED =  Color(153, 0, 0);
  public static final Color DARK_RED =  Color(139, 0, 0);
  public static final Color MAROON =  Color(128, 0, 0);
  public static final Color BARN_RED =  Color(124, 10, 2);
  public static final Color TURKEY_RED =  Color(169, 17, 1);

  public static final Color BLUE = Color.BLUE;
  public static final Color BABY_BLUE =  Color(137, 207, 240);
  public static final Color LIGHT_BLUE =  Color(176, 216, 230);
  public static final Color PERIWINKLE =  Color(204, 204, 255);
  public static final Color POWDER_BLUE =  Color(176, 224, 230);
  public static final Color MORNING_BLUE =  Color(141, 163, 153);
  public static final Color BLUE_MUNSELL =  Color(0, 147, 175);
  public static final Color BLUE_PANTONE =  Color(0, 24, 168);
  public static final Color BLUE_CRAYOLA =  Color(31, 117, 254);
  public static final Color BLUE_MEDIUM =  Color(0, 0, 205);
  public static final Color SPANISH_BLUE =  Color(0, 112, 184);
  public static final Color LIBERTY =  Color(84, 90, 167);
  public static final Color EGYPTIAN_BLUE =  Color(16, 52, 166);
  public static final Color ULTRAMARINE =  Color(63, 0, 255);
  public static final Color DARK_BLUE =  Color(0, 0, 139);
  public static final Color RESOLUTION_BLUE =  Color(0, 35, 185);
  public static final Color NAVY_BLUE =  Color(0, 0, 128);
  public static final Color MIDNIGHT_BLUE =  Color(25, 25, 112);
  public static final Color INDEPENDENCE =  Color(76, 81, 109);
  public static final Color SPACE_CADET =  Color(29, 41, 81);
  public static final Color CAROLINA_BLUE =  Color(123, 175, 212);
  public static final Color DUKE_BLUE =  Color(0, 0, 156);

  public static final Color GREEN = Color.GREEN;
  public static final Color ARTICHOKE =  Color(143, 151, 121);
  public static final Color ARTICHOKE_GREEN_PANTONE =  Color(75, 111, 68);
  public static final Color ASPARAGUS =  Color(135, 169, 107);
  public static final Color AVOCADO =  Color(86, 130, 3);
  public static final Color FERN_GREEN =  Color(79, 121, 66);
  public static final Color FERN =  Color(113, 188, 120);
  public static final Color FOREST_GREEN =  Color(34, 139, 34);
  public static final Color HOOKER_GREEN =  Color(73, 121, 107);
  public static final Color JUNGLE_GREEN =  Color(41, 171, 135);
  public static final Color LAUREL_GREEN =  Color(169, 186, 157);
  public static final Color LIGHT_GREEN =  Color(144, 238, 144);
  public static final Color MANTIS =  Color(116, 195, 101);
  public static final Color MOSS_GREEN =  Color(138, 154, 91);
  public static final Color DARK_MOSS_GREEN =  Color(74, 93, 35);
  public static final Color MYRTLE_GREEN =  Color(49, 120, 115);
  public static final Color MINT_GREEN =  Color(152, 251, 152);
  public static final Color PINE_GREEN =  Color(1, 121, 111);
  public static final Color SAP_GREEN =  Color(0, 158, 96);
  public static final Color IRISH_GREEN =  Color(0, 158, 96);
  public static final Color ST_PATRICK = IRISH_GREEN;
  public static final Color TEA_GREEN =  Color(208, 240, 192);
  public static final Color TEAL =  Color(0, 128, 128);
  public static final Color DARK_GREEN =  Color(0, 100, 0);
  public static final Color GREEN_PANTONE =  Color(0, 173, 131);
  public static final Color GREEN_CRAYOLA =  Color(28, 172, 120);
  public static final Color ARMY_GREEN =  Color(75, 83, 32);
  public static final Color BOTTLE_GREEN =  Color(0, 106, 78);
  public static final Color BRIGHT_GREEN =  Color(102, 255, 0);
  public static final Color BRIGHT_MINT =  Color(79, 255, 176);
  public static final Color BRUNSWICK_GREEN =  Color(27, 77, 62);
  public static final Color CELADON =  Color(173, 255, 175);
  public static final Color DARK_PASTEL_GREEN =  Color(3, 192, 160);
  public static final Color DARTMOUTH_GREEN =  Color(0, 105, 62);
  public static final Color EMERALD =  Color(80, 220, 100);
  public static final Color GREEN_YELLOW =  Color(173, 255, 47);
  public static final Color HARLEQUIN =  Color(63, 255, 0);
  public static final Color HUNTER_GREEN =  Color(53, 94, 59);
  public static final Color INDIA_GREEN =  Color(19, 136, 8);
  public static final Color ISLAMIC_GREEN =  Color(0, 144, 0);
  public static final Color JADE =  Color(0, 168, 107);
  public static final Color KELLY_GREEN =  Color(75, 187, 23);
  public static final Color MIDNIGHT_GREEN =  Color(0, 73, 83);
  public static final Color NEON_GREEN =  Color(57, 255, 20);
  public static final Color OFFICE_GREEN =  Color(0, 128, 0);
  public static final Color PERSIAN_GREEN =  Color(0, 166, 147);

  public static final Color YELLOW = Color.YELLOW;
  public static final Color CREAM =  Color(255, 255, 204);
  public static final Color YELLOW_MUNSELL =  Color(239, 204, 0);
  public static final Color YELLOW_PANTONE =  Color(254, 223, 0);
  public static final Color YELLOW_CRAYOLA =  Color(252, 232, 131);
  public static final Color UNMELLOW_YELLOW =  Color(255, 255, 102);
  public static final Color LEMON =  Color(253, 255, 0);
  public static final Color ROYAL_YELLOW =  Color(250, 219, 94);
  public static final Color GOLD =  Color(255, 215, 0);
  public static final Color CYBER_YELLOW =  Color(255, 211, 0);
  public static final Color SAFETY_YELLOW =  Color(238, 210, 2);
  public static final Color GOLDENROD =  Color(218, 165, 32);

  public static final Color ORANGE = Color.ORANGE;
  public static final Color ORANGE_WHEEL =  Color(255, 127, 0);
  public static final Color DARK_ORANGE =  Color(255, 140, 0);
  public static final Color ORANGE_PANTONE =  Color(255, 88, 0);
  public static final Color ORANGE_CRAYOLA =  Color(255, 117, 56);
  public static final Color PEACH =  Color(255, 229, 180);
  public static final Color LIGHT_ORANGE =  Color(254, 216, 177);
  public static final Color APRICOT =  Color(251, 206, 177);
  public static final Color MELON =  Color(253, 188, 180);
  public static final Color TEA_ROSE =  Color(248, 131, 121);
  public static final Color CARROT_ORANGE =  Color(237, 145, 33);
  public static final Color ORANGE_PEEL =  Color(255, 159, 0);
  public static final Color PRINCETON_ORANGE =  Color(245, 128, 37);
  public static final Color UT_ORANGE =  Color(255, 130, 0);
  public static final Color SPANISH_ORANGE =  Color(232, 97, 0);
  public static final Color PUMPKIN =  Color(255, 117, 24);
  public static final Color VERMILION =  Color(227, 66, 52);
  public static final Color TOMATO =  Color(255, 99, 71);
  public static final Color BURNT_ORANGE =  Color(191, 87, 0);

  public static final Color PURPLE =  Color(128, 0, 128);
  public static final Color ROYAL_PURPLE =  Color(120, 81, 169);
  public static final Color RED_VIOLET =  Color(199, 21, 133);
  public static final Color PURPLE_MEDIUM =  Color(147, 112, 219);
  public static final Color MUNSELL =  Color(159, 0, 197);
  public static final Color MAUVE =  Color(224, 176, 255);
  public static final Color ORCHID =  Color(218, 112, 214);
  public static final Color HELIOTROPE =  Color(223, 115, 255);
  public static final Color PHLOX =  Color(223, 0, 255);
  public static final Color PURPLE_PIZZAZZ =  Color(254, 78, 218);
  public static final Color MULBERRY =  Color(197, 75, 140);
  public static final Color PEARLY_PURPLE =  Color(183, 104, 162);
  public static final Color PURPUREUS =  Color(154, 78, 174);
  public static final Color MARDI_GRAS =  Color(136, 0, 137);
  public static final Color PANSY_PURPLE =  Color(120, 24, 74);
  public static final Color DARK_PURPLE =  Color(48, 25, 52);
  public static final Color VIOLET =  Color(127, 0, 255);

  public static final Color BLACK = Color.BLACK;

  public static void image(Image image, double x, double y, double width, double height) {
      Renderer.drawImage(image, x, y, width, height);
  }

}
