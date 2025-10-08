package standards.model;

import javafx.scene.media.AudioClip;
import java.net.URL;

/**
 * {@code StandardAudioFX} represents a single playable audio clip within the
 * Standards JavaFX Game Library. It encapsulates the {@link AudioClip} object
 * from the JavaFX Media API.
 *
 * Modified from Joshua Crotts & Andrew Matzureff's Standards Java Game Library Source Code
 */
public final class StandardAudio {

    /** The JavaFX AudioClip instance used for playback. */
    private AudioClip audioClip;

    /** The URL path of the audio resource file. */
    private String filePath;

    /** Infinite loop indicator. Equivalent to looping continuously. */
    public static final int INFINITELY = -1;

    public StandardAudio(String filePath) {
        this.load(filePath);
    }

    /**
     * Loads an audio clip from a given path and prepares it for playback.
     *
     * @param filePath the path or resource name of the audio file
     */
    public void load(String filePath) {
        this.filePath = filePath;

        try {
            URL resource = getClass().getResource(filePath);
            if (resource == null) {
                throw new IllegalArgumentException("Audio file not found: " + filePath);
            }

            this.audioClip = new AudioClip(resource.toExternalForm());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load audio clip: " + filePath, e);
        }
    }

    /**
     * Starts playing the loaded audio clip.
     * <p>
     * If the clip is already playing, this method has no effect.
     * </p>
     */
    public void start() {
        if (this.audioClip != null && !this.audioClip.isPlaying()) {
            this.audioClip.play();
        }
    }

    /**
     * Stops playback of the current audio clip.
     * <p>
     * The clip will stop immediately and return to the beginning.
     * </p>
     */
    public void stop() {
        if (this.audioClip != null && this.audioClip.isPlaying()) {
            this.audioClip.stop();
        }
    }

    /**
     * Loops the clip a specified number of times.
     * <p>
     * If {@code n == StandardAudioFX.INFINITELY}, the clip will loop continuously.
     * </p>
     *
     * @param n the number of times to loop, or {@code INFINITELY} for infinite looping
     */
    public void loop(int n) {
        if (this.audioClip == null) {
            throw new IllegalStateException("Audio clip not loaded.");
        }

        if (n == INFINITELY) {
            this.audioClip.setCycleCount(AudioClip.INDEFINITE);
        } else if (n >= 0) {
            this.audioClip.setCycleCount(n);
        } else {
            throw new IllegalArgumentException("Loop count cannot be negative.");
        }

        this.audioClip.play();
    }

    /**
     * Sets the playback volume of the current audio clip.
     *
     * @param volume the desired volume (0.0 to 1.0)
     */
    public void setVolume(double volume) {
        if (this.audioClip != null) {
            this.audioClip.setVolume(Math.max(0.0, Math.min(1.0, volume)));
        }
    }

    /**
     * Returns whether the clip is currently playing.
     *
     * @return {@code true} if the clip is playing, {@code false} otherwise
     */
    public boolean isPlaying() {
        return this.audioClip != null && this.audioClip.isPlaying();
    }

    /**
     * Returns the file path of the loaded audio clip.
     *
     * @return the audio clip's file path or resource name
     */
    public String getFilePath() {
        return this.filePath;
    }

    /**
     * Stops and releases the audio clip for garbage collection.
     * <p>
     * Should be called when the sound resource is no longer needed.
     * </p>
     */
    public void dispose() {
        if (this.audioClip != null) {
            this.audioClip.stop();
            this.audioClip = null;
        }
    }

}
