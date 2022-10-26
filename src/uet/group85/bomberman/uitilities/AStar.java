package uet.group85.bomberman.uitilities;

import uet.group85.bomberman.entities.blocks.Block;
import uet.group85.bomberman.entities.blocks.Bomb;
import uet.group85.bomberman.entities.blocks.Brick;
import uet.group85.bomberman.entities.characters.Character;
import uet.group85.bomberman.entities.tiles.Grass;
import uet.group85.bomberman.entities.tiles.Tile;
import uet.group85.bomberman.entities.tiles.Wall;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;

import java.util.*;

public class AStar {
    private final PriorityQueue<Coordinate> openNodes;
    private final Set<Coordinate> closeNodes;
    private Node[][] nodes;
    private final Character chaser;
    private Coordinate src;
    private final Coordinate dest;

    public AStar(Character chaser, Character runner) {
        openNodes = new PriorityQueue<>(Comparator.comparingInt(pos -> nodes[pos.y][pos.x].f));
        closeNodes = new HashSet<>();

        this.nodes = new Node[GameManager.mapRows][GameManager.mapCols];
        for (int j = 0; j < GameManager.mapRows; j++) {
            for (int i = 0; i < GameManager.mapCols; i++) {
                nodes[j][i] = new Node();
            }
        }

        this.chaser = chaser;

        this.src = chaser.getMapPos().divide(Sprite.SCALED_SIZE);
        this.dest = runner.getMapPos().add(12, 14).divide(Sprite.SCALED_SIZE);
    }

    public Character.Direction findPath() {
        nodes[src.y][src.x] = new Node(new Coordinate(src), 0, 0, 0);
        openNodes.add(src);
        while (!openNodes.isEmpty()) {
            Coordinate pos = openNodes.poll();
            closeNodes.add(pos);
            if (isDest(pos)) {
                return tracePath();
            }
            checkSuccessor(pos, new Coordinate(pos.x, pos.y - 1));
            checkSuccessor(pos, new Coordinate(pos.x, pos.y + 1));
            checkSuccessor(pos, new Coordinate(pos.x - 1, pos.y));
            checkSuccessor(pos, new Coordinate(pos.x + 1, pos.y ));
        }
        return Character.Direction.NONE;
    }

    public Character.Direction tracePath() {
        Stack<Coordinate> path = new Stack<>();
        Coordinate pos = new Coordinate(dest);
        while (!(nodes[pos.y][pos.x].parent.equals(pos))) {
            path.add(new Coordinate(pos));
            int tmp_x = nodes[pos.y][pos.x].parent.x;
            int tmp_y = nodes[pos.y][pos.x].parent.y;
            pos.x = tmp_x;
            pos.y = tmp_y;
        }
        if (!path.isEmpty()) {
            Coordinate pos_ = path.pop();
            if (pos_.x < src.x) {
                return Character.Direction.LEFT;
            } else if (pos_.x > src.x) {
                return Character.Direction.RIGHT;
            } else if (pos_.y < src.y) {
                return Character.Direction.UP;
            } else if (pos_.y > src.y) {
                return Character.Direction.DOWN;
            }
        }
        return Character.Direction.NONE;
    }

    private void checkSuccessor(Coordinate pos, Coordinate adjPos) {
        int f;
        int g;
        int h;
        if (isValid(adjPos)) {
            if (!closeNodes.contains(adjPos) && !isBlocked(adjPos)) {
                g = nodes[adjPos.y][adjPos.x].g + 1;
                h = adjPos.manhattanDist(dest);
                f = g + h;
                if (nodes[adjPos.y][adjPos.x].f > f) {
                    openNodes.add(adjPos);
                    nodes[adjPos.y][adjPos.x].parent = new Coordinate(pos);
                    nodes[adjPos.y][adjPos.x].g = g;
                    nodes[adjPos.y][adjPos.x].h = h;
                    nodes[adjPos.y][adjPos.x].f = f;
                }
            }
        }
    }

    private boolean isValid(Coordinate pos) {
        return (0 <= pos.x && pos.x < GameManager.mapCols) && (0 <= pos.y && pos.y < GameManager.mapRows);
    }

    private boolean isDest(Coordinate pos) {
        return pos.equals(dest);
    }

    private boolean isBlocked(Coordinate pos) {
        Tile tile = GameManager.tiles.get(pos.y).get(pos.x);
        if (tile instanceof Wall) {
            return true;
        }
        if (tile instanceof Grass grass) {
            if (grass.hasOverlay()) {
                Block block = grass.getTopLayer();
                if (block instanceof Bomb) {
                    return !chaser.canPassBomb();
                } else if (block instanceof Brick) {
                    return !chaser.canPassBrick();
                }
            }
        }
        return false;
    }
}
