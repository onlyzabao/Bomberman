package uet.group85.bomberman.managers;

import javafx.scene.media.AudioClip;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    public static boolean isSoundMuted = false;
    public static boolean isMusicMuted = false;
    public static double volume = 1.0;
    private static final Map<String, AudioClip> gameSounds = new HashMap<>();

    public static void loadGameSound() {
        gameSounds.put("Won", new AudioClip(SoundManager.class.getResource(
                "/sounds/won.mp3").toExternalForm()));
        gameSounds.put("Lost", new AudioClip(SoundManager.class.getResource(
                "/sounds/lost.mp3").toExternalForm()));
        gameSounds.put("Stage_finished", new AudioClip(SoundManager.class.getResource(
                "/sounds/stage_finished.wav").toExternalForm()));
        gameSounds.put("Stage_start", new AudioClip(SoundManager.class.getResource(
                "/sounds/stage_start.mp3").toExternalForm()));
        gameSounds.put("Bomber_dead", new AudioClip(SoundManager.class.getResource(
                "/sounds/bomber_dead.wav").toExternalForm()));
        gameSounds.put("Bomb", new AudioClip(SoundManager.class.getResource(
                "/sounds/bomb.wav").toExternalForm()));
        gameSounds.put("Explosion", new AudioClip(SoundManager.class.getResource(
                "/sounds/explosion.wav").toExternalForm()));
        gameSounds.put("Power_up", new AudioClip(SoundManager.class.getResource(
                "/sounds/power_up.wav").toExternalForm()));
    }

    public static void playGameSound(String sound, boolean isDistinct) {
        if (!isSoundMuted) {
            if (!gameSounds.get(sound).isPlaying() || !isDistinct) {
                gameSounds.get(sound).play(volume);
            }
        }
    }

    public static void clearGameSound() {
        gameSounds.clear();
    }
}
