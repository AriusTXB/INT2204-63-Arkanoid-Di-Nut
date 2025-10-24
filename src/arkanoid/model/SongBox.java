package arkanoid.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * A class that manages all audio in the Arkanoid game.
 * - Loads sound files from the directory: projectRoot/media/audio/
 * - Uses JavaFX {@link MediaPlayer} for playback
 * - Provides dedicated play methods for each sound effect
 */
public class SongBox {

    private final HashMap<String, MediaPlayer> sounds = new HashMap<>();

    public SongBox() {
        loadAllSounds();
    }

    /** Loads all required sound files. */
    private void loadAllSounds() {
        loadSound("menu", "media/audio/menu.mp3");
        loadSound("level", "media/audio/level.mp3");
        loadSound("explode", "media/audio/explode.mp3");
        loadSound("powerup", "media/audio/powerup.mp3");
        loadSound("gameover", "media/audio/gameover.mp3");
    }

    /**
     * Utility method to load a sound file with a specific key.
     */
    private void loadSound(String key, String path) {
        try {
            String fullPath = Paths.get(path).toAbsolutePath().toString();
            File file = new File(fullPath);

            if (!file.exists()) {
                System.err.println("Audio file not found: " + fullPath);
                return;
            }

            Media media = new Media(file.toURI().toString());
            MediaPlayer player = new MediaPlayer(media);

            // Set a global volume level for all sounds
            player.setVolume(0.5);

            sounds.put(key, player);
            System.out.println("Loaded sound: " + key);

        } catch (Exception e) {
            System.err.println("Error loading sound: " + key);
            e.printStackTrace();
        }
    }

    public void playMenu() { play("menu"); }

    public void playLevel() { play("level"); }

    public void playExplode() { play("explode"); }

    public void playPowerUp() { play("powerup"); }

    public void playGameOver() { play("gameover"); }

    /**
     * Plays a sound associated with the given key.
     */
    private void play(String key) {
        MediaPlayer player = sounds.get(key);
        if (player == null) {
            System.err.println("Sound not loaded: " + key);
            return;
        }

        // Reset to allow immediate replay
        player.stop();
        player.play();
    }

    /**
     * Loops a background track indefinitely.
     */
    public void loop(String key) {
        MediaPlayer player = sounds.get(key);
        if (player == null) return;

        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();
    }

    /**
     * Stops a specific sound.
     */
    public void stop(String key) {
        MediaPlayer player = sounds.get(key);
        if (player != null) player.stop();
    }

    /** Stops all currently playing sounds. */
    public void stopAll() {
        for (MediaPlayer player : sounds.values()) {
            player.stop();
        }
    }
}
