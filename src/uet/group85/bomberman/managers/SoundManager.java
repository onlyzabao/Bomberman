package uet.group85.bomberman.managers;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SoundManager {
    // -------------- Specifications -----------------
    public static boolean isSoundMuted = false;
    public static boolean isMusicMuted = false;
    public static double volume = 1.0;
    // --------------- Music & Sounds ----------------
    private static final Map<String, AudioClip> gameSounds = new HashMap<>();
    private static final Map<String, MediaPlayer> gameMusic = new HashMap<>();

    public static void loadGameSound() {
        gameSounds.put("Won", new AudioClip(Objects.requireNonNull(SoundManager.class.getResource(
                "/sounds/won.mp3")).toExternalForm()));

        gameSounds.put("Lost", new AudioClip(Objects.requireNonNull(SoundManager.class.getResource(
                "/sounds/lost.mp3")).toExternalForm()));

        gameSounds.put("Stage_finished", new AudioClip(Objects.requireNonNull(SoundManager.class.getResource(
                "/sounds/stage_finished.wav")).toExternalForm()));

        gameSounds.put("Stage_start", new AudioClip(Objects.requireNonNull(SoundManager.class.getResource(
                "/sounds/stage_start.mp3")).toExternalForm()));

        gameSounds.put("Bomber_dead", new AudioClip(Objects.requireNonNull(SoundManager.class.getResource(
                "/sounds/bomber_dead.wav")).toExternalForm()));

        gameSounds.put("Bomb", new AudioClip(Objects.requireNonNull(SoundManager.class.getResource(
                "/sounds/bomb.wav")).toExternalForm()));

        gameSounds.put("Explosion", new AudioClip(Objects.requireNonNull(SoundManager.class.getResource(
                "/sounds/explosion.wav")).toExternalForm()));

        gameSounds.put("Power_up", new AudioClip(Objects.requireNonNull(SoundManager.class.getResource(
                "/sounds/power_up.wav")).toExternalForm()));

        gameSounds.put("Mod_dead", new AudioClip(Objects.requireNonNull(SoundManager.class.getResource(
                "/sounds/mod_dead.mp3")).toExternalForm()));
    }

    public static void loadGameMusic() {
        gameMusic.put("BGM", new MediaPlayer(new Media(Objects.requireNonNull(SoundManager.class.getResource(
                "/sounds/Main_BGM.mp3")).toExternalForm())));
        gameMusic.get("BGM").setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                gameMusic.get("BGM").seek(Duration.ZERO);
            }
        });
    }

    public static void playGameSound(String sound, boolean isDistinct) {
        if (isSoundMuted) {
            return;
        }
        if (!gameSounds.get(sound).isPlaying() || !isDistinct) {
            gameSounds.get(sound).play(volume);
        }
    }

    public static void playGameMusic(String music) {
        if (isMusicMuted) {
            return;
        }
        if (gameMusic.get(music).getStatus() != MediaPlayer.Status.PLAYING) {
            gameMusic.get(music).play();
        }
    }

    public static void stopGameMusic(String music) {
        if (isMusicMuted) {
            return;
        }
        if (gameMusic.get(music).getStatus() != MediaPlayer.Status.STOPPED) {
            gameMusic.get(music).stop();
        }
    }

    public static void pauseGameMusic(String music) {
        if (isMusicMuted) {
            return;
        }
        if (gameMusic.get(music).getStatus() != MediaPlayer.Status.PAUSED) {
            gameMusic.get(music).pause();
        }
    }

    public static void clearGameSound() {
        gameSounds.clear();
    }
}
