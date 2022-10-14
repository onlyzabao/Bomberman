package uet.group85.bomberman.managers;

import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.entities.blocks.*;
import uet.group85.bomberman.entities.blocks.BombItem;
import uet.group85.bomberman.entities.blocks.FlameItem;
import uet.group85.bomberman.entities.blocks.SpeedItem;
import uet.group85.bomberman.entities.tiles.Grass;
import uet.group85.bomberman.entities.tiles.Wall;
import uet.group85.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MapManager {
    private static final char WALL = '#';
    private static final char BRICK = '*';
    private static final char PORTAL = 'x';
    private static final char BOMBER = 'p';
    private static final char BALLOON = '1';
    private static final char ONEAL = '2';
    private static final char BOMB_ITEM = 'b';
    private static final char FLAME_ITEM = 'f';
    private static final char SPEED_ITEM = 's';

    private static void initWall(Coordinate mapPos) {
        GameManager.tiles.add(new Wall(mapPos, new Coordinate(mapPos)));
    }

    private static void initGrass(Coordinate mapPos) {
        GameManager.tiles.add(new Grass(mapPos, new Coordinate(mapPos)));
    }

    private static void initGrass(Coordinate mapPos, Coordinate screenPos, Block[] layers) {
        Grass belowBlock = new Grass(mapPos, screenPos);
        for (Block layer : layers) {
            belowBlock.addLayer(layer);
        }
        GameManager.tiles.add(belowBlock);
    }

    private static void initBrick(Coordinate mapPos) {
        Coordinate screenPos = new Coordinate(mapPos);
        initGrass(mapPos, screenPos, new Block[]{new Brick(mapPos, screenPos)});
    }

    private static void initPortal(Coordinate mapPos) {
        Coordinate screenPos = new Coordinate(mapPos);
        initGrass(mapPos, screenPos, new Block[]{
                new Portal(mapPos, screenPos),
                new Brick(mapPos, screenPos)
        });
    }

    private static void initBomber(Coordinate mapPos) {
        initGrass(mapPos);
        GameManager.bomber.setMapPos(new Coordinate(mapPos));
        GameManager.bomber.setScreenPos(new Coordinate(mapPos));
    }

    private static void initBalloon(Coordinate mapPos) {
        initGrass(mapPos);
    }

    private static void initOneal(Coordinate mapPos) {
        initGrass(mapPos);
    }

    private static void initBombItem(Coordinate mapPos) {
        Coordinate screenPos = new Coordinate(mapPos);
        initGrass(mapPos, screenPos, new Block[]{
                new BombItem(mapPos, screenPos),
                new Brick(mapPos, screenPos)
        });
    }

    private static void initFlameItem(Coordinate mapPos) {
        Coordinate screenPos = new Coordinate(mapPos);
        initGrass(mapPos, screenPos,new Block[]{
                new FlameItem(mapPos, screenPos),
                new Brick(mapPos, screenPos)
        });
    }

    private static void initSpeedItem(Coordinate mapPos) {
        Coordinate screenPos = new Coordinate(mapPos);
        initGrass(mapPos, screenPos, new Block[]{
                new SpeedItem(mapPos, screenPos),
                new Brick(mapPos, screenPos)
        });
    }

    private static void reset() {
        GameManager.tiles.clear();
        GameManager.enemies.clear();
    }

    public static void loadMap(int level) throws FileNotFoundException {
        reset();
        String path = String.format("res/levels/Level%d.txt", level);
        File myFile = new File(path);
        Scanner myReader = new Scanner(myFile);
        GameManager.mapRows = myReader.nextInt();
        GameManager.mapCols = myReader.nextInt();
        myReader.nextLine();
        for (int j = 0; j < GameManager.mapRows; j++) {
            String line = myReader.nextLine();
            for (int i = 0; i < GameManager.mapCols; i++) {
                switch (line.charAt(i)) {
                    // Blocks
                    case WALL -> initWall(new Coordinate(i, j).multiply(Sprite.SCALED_SIZE));
                    case BRICK -> initBrick(new Coordinate(i, j).multiply(Sprite.SCALED_SIZE));
                    case PORTAL -> initPortal(new Coordinate(i, j).multiply(Sprite.SCALED_SIZE));
                    // Characters
                    case BOMBER -> initBomber(new Coordinate(i, j).multiply(Sprite.SCALED_SIZE));
                    case BALLOON -> initBalloon(new Coordinate(i, j).multiply(Sprite.SCALED_SIZE));
                    case ONEAL -> initOneal(new Coordinate(i, j).multiply(Sprite.SCALED_SIZE));
                    // Items
                    case BOMB_ITEM -> initBombItem(new Coordinate(i, j).multiply(Sprite.SCALED_SIZE));
                    case FLAME_ITEM -> initFlameItem(new Coordinate(i, j).multiply(Sprite.SCALED_SIZE));
                    case SPEED_ITEM -> initSpeedItem(new Coordinate(i, j).multiply(Sprite.SCALED_SIZE));
                    default -> initGrass(new Coordinate(i, j).multiply(Sprite.SCALED_SIZE));
                }
            }
        }
        myReader.close();
    }
}
