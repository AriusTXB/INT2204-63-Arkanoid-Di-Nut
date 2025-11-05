package arkanoid.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * Quản lý âm thanh trong Arkanoid.
 */
public class SongBox {

    private final HashMap<String, MediaPlayer> sounds = new HashMap<>();

    public SongBox() {
        loadAllSounds();
    }

    private void loadAllSounds() {
        loadSound("menu", "media/audio/menu.mp3");
        loadSound("level", "media/audio/level.mp3");
        loadSound("explode", "media/audio/explode.mp3");
        loadSound("gameover", "media/audio/gameover.mp3");
        loadSound("click", "media/audio/click.wav");

        loadSound("christmas", "media/audio/christmas.mp3");
        loadSound("tet", "media/audio/tet.mp3");
        loadSound("national_day", "media/audio/quoc_khanh.mp3");
    }

    /**
     * Tải file âm thanh.
     */
    private void loadSound(String key, String path) {
        try {
            File file = new File(Paths.get(path).toAbsolutePath().toString());
            if (!file.exists()) {
                return;
            }

            Media media = new Media(file.toURI().toString());
            MediaPlayer player = new MediaPlayer(media);
            player.setVolume(0.5);
            sounds.put(key, player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Phát âm thanh chung **/
    public void playClick() { play("click"); }
    public void playExplode() { play("explode"); }
    public void playGameOver() { play("gameover"); }
    public void playLevel() { play("level"); }

    /** Phát âm thanh dựa theo ngày lễ **/
    public void playSeasonalMusic() {
        String key = getSeasonalMusicKey();
        loop(key);
    }

    /** Xác định nhạc tương ứng với ngày**/
    private String getSeasonalMusicKey() {
        LocalDate today = LocalDate.now();
        int day = today.getDayOfMonth();
        int month = today.getMonthValue();

        if (month == 12 && day >= 20 && day <= 26) return "christmas";

        if (month == 9 && day == 2) return "national_day";

        if (month == 2 && day >= 1 && day <= 10) return "tet";

        return "level";
    }

    /** Phát âm thanh 1 lần **/
    private void play(String key) {
        MediaPlayer player = sounds.get(key);
        if (player == null) {
            return;
        }
        player.stop();
        player.play();
    }

    /** Lặp nhạc nền **/
    public void loop(String key) {
        MediaPlayer player = sounds.get(key);
        if (player == null) {
            return;
        }
        player.stop();
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();
    }

    /** Dừng nhạc **/
    public void stop(String key) {
        MediaPlayer player = sounds.get(key);
        if (player != null) player.stop();
    }

    /** Dừng tất cả nhạc **/
    public void stopAll() {
        for (MediaPlayer player : sounds.values()) {
            player.stop();
        }
    }
}
