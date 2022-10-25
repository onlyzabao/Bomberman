package uet.group85.bomberman.managers;

import uet.group85.bomberman.entities.Entity;
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

    // ------------------ Control ----------------------
    public static final boolean[] events = new boolean[6];
    // ------------------ Objects ----------------------

    public static final List<List<Tile>> tiles = new ArrayList<>();
    public static final List<Character> enemies = new ArrayList<>();

    public static Bomber bomber;
    // ------------------ Map --------------------------
    public static int mapCols;
    public static int mapRows;
    // ------------------ Specifications ---------------
    public static Status status;
    public static int score;
    public static int level;
    public static int chance;
    // ------------------ Timeline ---------------------
    public static double elapsedTime;

    /**
     * Initialize game environment.
     * @param data game's data from previous play
     */
    public static void init(Map<String, Integer> data) throws FileNotFoundException {
        // Set up specifications
        GameManager.status = GameManager.Status.PLAYING;
        GameManager.score = data.get("Score");
        GameManager.level = data.get("Level");
        GameManager.chance = data.get("Chance");
        // Set up map & objects
        GameManager.bomber = new Bomber(data);
        (new MapManager()).loadMap(data.get("Level"));
    }

    /**
     * Update objects behaviors.
     */
    public static void update() {
        GameManager.bomber.update();
        GameManager.enemies.removeIf(enemy -> !enemy.isExist());
        GameManager.enemies.forEach(Entity::update);
        GameManager.tiles.forEach(tile -> tile.forEach(Entity::update));
    }

    /**
     * Draw objects.
     */
    public static void render() {
        GameManager.tiles.forEach(tile -> tile.forEach(block -> {
            if (block.isVisible()) block.render(ScreenManager.gc);
        }));
        GameManager.enemies.forEach(enemy -> {
            if (enemy.isVisible()) enemy.render(ScreenManager.gc);
        });
        GameManager.bomber.render(ScreenManager.gc);
    }

    /**
     * Delete game environment.
     */
    public static void clear() {
        // Delete maps & objects
        GameManager.bomber = null;
        GameManager.enemies.clear();
        GameManager.tiles.forEach(List::clear);
        GameManager.tiles.clear();
    }
}
