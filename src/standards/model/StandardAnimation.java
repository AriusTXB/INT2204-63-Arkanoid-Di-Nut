package standards.model;

import standards.view.StandardAnimationView;
import javafx.scene.image.Image;

/**
 * {@code StandardAnimation} represents the model component of an animated
 * {@link StandardGameObject}. It stores animation state such as current frame,
 * playback speed, rotation, and mirror mode, and coordinates with the
 * {@link StandardAnimationView} to display the correct frame.
 *
 * It functions as the "Model" in the MVC paradigm, while the
 * {@code StandardAnimationView} acts as the "View".
 *
 * Modified from Joshua Crotts & Andrew Matzureff's Standards Java Game Library Source Code
 */
public final class StandardAnimation {

    /** The parent {@link StandardGameObject} associated with this animation. */
    private final StandardGameObject sgo;

    /** View responsible for rendering animation frames. */
    private final StandardAnimationView view;

    /** Index of the current frame within {@link #view}. */
    private int frameIndex;

    /** Timestamp of the last frame update (nanoseconds). */
    private long lastTime = 0;

    /** Frames per second (speed of animation). */
    private double fps;

    /** Current rotation. */
    private double rotation;

    /** Whether to mirror the image horizontally. */
    private boolean mirror = false;

    /** Start index for frame looping. */
    private int startFrameIndex = 0;

    /** End index for frame looping. */
    private int endFrameIndex = -1;

    /** Frame index where animation halts or loops from. */
    private int frameHaltPosition = -1;

    public StandardAnimation(StandardGameObject sgo, Image[] frames, double fps) {
        this(sgo, frames, fps, -1);
    }

    public StandardAnimation(StandardGameObject sgo, Image[] frames, double fps, int frameHaltPosition) {
        this.sgo = sgo;
        this.fps = fps;
        this.view = new StandardAnimationView(frames, sgo);
        this.endFrameIndex = frames.length;
        this.frameHaltPosition = frameHaltPosition;
        this.setCurrentFrameIndex(0);
        this.setDefaultDimensions();
    }

    /**
     * Sets the current frame of animation.
     * Updates the view to display the corresponding frame.
     *
     * @param frameIndex the index of the frame to set
     */
    private void setCurrentFrameIndex(int frameIndex) {
        if (frameIndex < this.startFrameIndex || frameIndex >= this.endFrameIndex) {
            // Loop or reset if out of range
            if (frameIndex >= this.endFrameIndex && this.frameHaltPosition != -1) {
                this.frameIndex = this.frameHaltPosition;
            } else {
                this.frameIndex = this.startFrameIndex;
            }
            this.view.setCurrentFrameIndex(this.frameIndex);
        } else {
            this.view.setCurrentFrameIndex(frameIndex);
            this.frameIndex = frameIndex;
        }
    }

    /**
     * Advances the animation to the next frame in sequence.
     * If the end frame is reached, loops back to {@link #startFrameIndex}.
     */
    public void advanceFrame() {
        this.setCurrentFrameIndex(this.getCurrentFrameIndex() + 1);
    }

    /**
     * Assigns the {@link StandardGameObject}'s dimensions to match the first frame.
     */
    private void setDefaultDimensions() {
        this.sgo.setWidth((int) this.view.getCurrentFrame().getWidth());
        this.sgo.setHeight((int) this.view.getCurrentFrame().getHeight());
    }

    /**
     * Defines the range of frames to loop between.
     *
     * @param startIndex inclusive start index
     * @param endIndex   exclusive end index
     */
    public void setFramePositions(int startIndex, int endIndex) {
        this.startFrameIndex = startIndex;
        this.endFrameIndex = endIndex;
    }

    /**
     * Stops the animation and resets it to the first frame.
     */
    public void stopAnimation() {
        this.frameIndex = this.startFrameIndex;
        this.setCurrentFrameIndex(0);
    }

    /** @return the parent {@link StandardGameObject}. */
    public StandardGameObject getStandardGameObject() {
        return this.sgo;
    }

    /** @return current frame index. */
    public int getCurrentFrameIndex() {
        return this.frameIndex;
    }

    /** @return the associated {@link StandardAnimationView}. */
    public StandardAnimationView getView() {
        return this.view;
    }

    /** Sets the playback speed in frames per second. */
    public void setFrameSpeed(double fps) {
        this.fps = fps;
    }

    /** @return current playback speed. */
    public double getFrameSpeed() {
        return this.fps;
    }

    /** @return last system time the frame was updated. */
    public long getLastTime() {
        return this.lastTime;
    }

    /** Sets the last frame update time. */
    public void setLastTime(long t) {
        this.lastTime = t;
    }

    /** Sets the rotation in degrees. */
    public void setRotation(double theta) {
        this.rotation = theta;
    }

    /** @return current rotation angle. */
    public double getRotation() {
        return this.rotation;
    }

    /** Sets the frame where the animation halts or loops back. */
    public void setFrameHaltPosition(int frame) {
        this.frameHaltPosition = frame;
    }

    /**
     * Enables or disables horizontal mirroring of the current frame.
     *
     * @param mirror whether to mirror the animation horizontally
     */
    public void setMirrored(boolean mirror) {
        this.mirror = mirror;
    }

    /** @return true if mirroring is enabled. */
    public boolean isMirrored() {
        return this.mirror;
    }
}
