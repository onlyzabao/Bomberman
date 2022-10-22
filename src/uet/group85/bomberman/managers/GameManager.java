package uet.group85.bomberman.managers;

import uet.group85.bomberman.entities.characters.Bomber;
import uet.group85.bomberman.entities.characters.Character;
import uet.group85.bomberman.entities.tiles.Tile;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameManager {
    public enum Status {
        PLAYING, WON, LOST
    }

    public static final boolean[] events = new boolean[5];

    public static final List<List<Tile>> tiles = new ArrayList<>();
    public static final List<Character> enemies = new ArrayList<>();

    public static Bomber bomber;
    public static int mapCols;
    public static int mapRows;
    public static Status status;
    public static int score;
    public static int level;
    public static int chance;

    public static double elapsedTime;

    public static void init(Map<String, Integer> data) {
        GameManager.status = GameManager.Status.PLAYING;
        GameManager.score = data.get("Score");
        GameManager.level = data.get("Level");
        GameManager.chance = data.get("Chance");
        GameManager.bomber = new Bomber(data);
        try {
            new MapManager();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clear() {
        GameManager.bomber = null;
        GameManager.enemies.clear();
        GameManager.tiles.forEach(List::clear);
        GameManager.tiles.clear();
    }
}
