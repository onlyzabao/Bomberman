package uet.group85.bomberman.managers;

import javafx.scene.canvas.GraphicsContext;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.entities.characters.Bomber;
import uet.group85.bomberman.entities.characters.Character;
import uet.group85.bomberman.entities.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    public enum Event {
        MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT, BOMB, TOTAL
    }
    public static int mapCols;
    public static int mapRows;

    public static final List<Tile> tiles = new ArrayList<>();
    public static final List<Character> enemies = new ArrayList<>();

    public static final Bomber bomber = new Bomber();

    public static final boolean[] events = new boolean[Event.TOTAL.ordinal()];

    public static double elapsedTime;

    public static void update(double elapsedTime) {
        GameManager.elapsedTime = elapsedTime;
        if (bomber.isExist()) {
            bomber.update();
        }

        enemies.forEach(enemy -> {
            if (enemy.isExist()) {
                enemy.update();
            }
        });

        tiles.forEach(Entity::update);
    }

    public static void render(GraphicsContext gc) {
        tiles.forEach(block -> {
            if (block.isVisible()) {
                block.render(gc);
            }
        });

        enemies.forEach(enemy -> {
            if (enemy.isExist() && enemy.isVisible()) {
                enemy.render(gc);
            }
        });

        if (bomber.isExist()) {
            bomber.render(gc);
        }
    }
}
