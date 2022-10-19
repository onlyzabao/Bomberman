package uet.group85.bomberman.managers;

import uet.group85.bomberman.entities.characters.Bomber;
import uet.group85.bomberman.entities.characters.Character;
import uet.group85.bomberman.entities.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    public enum Data {
        SCORE, LEVEL, CHANCE, BONUS_BOMBS, FLAME_LEN, SPEED, BOMB_PASS, WALL_PASS, TOTAL
    }
    public enum Event {
        MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT, BOMB, TOTAL
    }
    public enum Status {
        PLAYING, WON, LOST
    }
    public static final boolean[] events = new boolean[Event.TOTAL.ordinal()];

    public static final List<Tile> tiles = new ArrayList<>();
    public static final List<Character> enemies = new ArrayList<>();

    public static Bomber bomber;
    public static int mapCols;
    public static int mapRows;
    public static Status status;
    public static int score;
    public static int level;
    public static int chance;

    public static double elapsedTime;
}
