package uet.group85.bomberman.managers;

import javafx.scene.media.AudioClip;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    public enum GameSound {
        INTRO, WON, LOST,
        STEP, BOMB, EXPLOSION, POWER_UP, TOTAL
    }

    public static final Map<GameSound, AudioClip> gameSounds = new HashMap<>();

    public static void loadGameSound() {
//        gameSounds.put(GameSound.INTRO, new AudioClip(SoundManager.class.getResource(
//                "/sounds/intro.wav").toExternalForm()));
        gameSounds.put(GameSound.WON, new AudioClip(SoundManager.class.getResource(
                "/sounds/won.wav").toExternalForm()));
        gameSounds.put(GameSound.LOST, new AudioClip(SoundManager.class.getResource(
                "/sounds/lost.wav").toExternalForm()));
        gameSounds.put(GameSound.STEP, new AudioClip(SoundManager.class.getResource(
                "/sounds/step.wav").toExternalForm()));
        gameSounds.put(GameSound.BOMB, new AudioClip(SoundManager.class.getResource(
                "/sounds/bomb.wav").toExternalForm()));
        gameSounds.put(GameSound.EXPLOSION, new AudioClip(SoundManager.class.getResource(
                "/sounds/explosion.wav").toExternalForm()));
        gameSounds.put(GameSound.POWER_UP, new AudioClip(SoundManager.class.getResource(
                "/sounds/power_up.wav").toExternalForm()));
    }

    public static void clearGameSound() {
        gameSounds.clear();
    }
}
