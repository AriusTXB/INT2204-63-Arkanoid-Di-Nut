package arkanoid.model.item;

import arkanoid.model.Paddle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;

import standards.model.StandardAnimation;
import standards.model.StandardGameObject;

/**
 * The {@code Item} class is an abstract representation of a collectible or
 * interactive object within the game world. Each item may contain animation
 * frames, an {@link ItemList} identifier, and a flag indicating if the item
 * is active ("alive").
 * <p>
 * Subclasses must implement both {@link #tick()} for updates and
 * {@link #render(GraphicsContext)} for visual representation.
 * </p>
 */
public abstract class Item extends StandardGameObject {

    /** Animator controlling item-specific animations. */
    private StandardAnimation animation;

    /** List of image frames used in the item’s animation sequence. */
    protected ArrayList<Image> frames;

    /** The maximum number of animation frames per item. */
    protected byte MAX_FRAMES = 8;

    /** Constant rarity values for certain item categories. */
    public static final int LARGE_RARITY = 8;
    public static final int MULTI_RARITY = 8;

    /** Whether the item is currently active (visible and interactive). */
    private boolean alive = true;

    /** The specific item type identifier from {@link ItemList}. */
    private ItemList id;

    /**
     * Constructs a new {@code Item} with the given world coordinates.
     *
     * @param x the x-coordinate of the item
     * @param y the y-coordinate of the item
     */
    public Item(double x, double y) {
        super(x, y, 44, 22);
        this.frames = new ArrayList<>();
    }

    /**
     * Updates the item’s logic each game tick.
     * <p>
     * Subclasses must define specific behavior (e.g., animation, interactions).
     * </p>
     */
    @Override
    public abstract void tick();

    /**
     * Renders the item to the screen using the provided JavaFX {@link GraphicsContext}.
     *
     * @param gc the graphics context used for rendering
     */
    @Override
    public abstract void render(GraphicsContext gc);

    /**
     * Returns whether the item is currently active or visible in the game world.
     *
     * @return {@code true} if the item is active; {@code false} otherwise
     */
    public boolean isAlive() {
        return this.alive;
    }

    /**
     * Sets the alive status of the item.
     *
     * @param b {@code true} to mark the item as active; {@code false} to deactivate
     */
    public void setAlive(boolean b) {
        this.alive = b;
    }

    /**
     * Returns the item’s type identifier from {@link ItemList}.
     *
     * @return the item’s {@link ItemList} ID
     */
    public ItemList getItemID() {
        return id;
    }

    /**
     * Assigns a new identifier to this item.
     *
     * @param id the {@link ItemList} ID to set
     */
    public void setItemId(ItemList id) {
        this.id = id;
    }

    /**
     * Returns the current {@link StandardAnimator} associated with this item.
     *
     * @return the item’s animator
     */
    public StandardAnimation getAnimation() {
        return animation;
    }

    /**
     * Sets a new {@link StandardAnimator} for this item.
     *
     * @param animation the animator to assign
     */
    public void setAnimation(StandardAnimation animation) {
        this.animation = animation;
    }

    public abstract void applyEffect(Paddle paddle);
}
