package uet.group85.bomberman.managers;

import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.entities.blocks.*;
import uet.group85.bomberman.entities.blocks.BombItem;
import uet.group85.bomberman.entities.blocks.FlameItem;
import uet.group85.bomberman.entities.blocks.SpeedItem;
import uet.group85.bomberman.entities.characters.Balloon;
import uet.group85.bomberman.entities.tiles.Grass;
import uet.group85.bomberman.entities.tiles.Wall;
import uet.group85.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MapManager {
    private final char WALL = '#';
    private final char BRICK = '*';
    private final char PORTAL = 'x';
    private final char BOMBER = 'p';
    private final char BALLOON = '1';
    private final char ONEAL = '2';
    private final char BOMB_ITEM = 'b';
    private final char FLAME_ITEM = 'f';
    private final char SPEED_ITEM = 's';
    private final char WALLPASS_ITEM = 'w';

    private final char BOMBPASS_ITEM = 'q';



    public MapManager(int score, int level, int chances, int[] items) throws FileNotFoundException {
        GameManager.status = GameManager.Status.PLAYING;
        GameManager.score = score;
        GameManager.level = level;
        GameManager.chance = chances;

        GameManager.bomber.reset(items);
        GameManager.enemies.clear();
        GameManager.tiles.clear();

        loadMap(level);
    }

    private void initWall(Coordinate mapPos) {
        GameManager.tiles.add(new Wall(mapPos, new Coordinate(mapPos)));
    }

    private void initGrass(Coordinate mapPos) {
        GameManager.tiles.add(new Grass(mapPos, new Coordinate(mapPos)));
    }

    private void initGrass(Coordinate mapPos, Coordinate screenPos, Block[] layers) {
        Grass belowBlock = new Grass(mapPos, screenPos);
        for (Block layer : layers) {
            belowBlock.addLayer(layer);
        }
        GameManager.tiles.add(belowBlock);
    }

    private void initBrick(Coordinate mapPos) {
        Coordinate screenPos = new Coordinate(mapPos);
        initGrass(mapPos, screenPos, new Block[]{new Brick(mapPos, screenPos)});
    }

    private void initPortal(Coordinate mapPos) {
        Coordinate screenPos = new Coordinate(mapPos);
        initGrass(mapPos, screenPos, new Block[]{
                new Portal(mapPos, screenPos),
                new Brick(mapPos, screenPos)
        });
    }

    private void initBomber(Coordinate mapPos) {
        initGrass(mapPos);
        GameManager.bomber.setMapPos(new Coordinate(mapPos));
        GameManager.bomber.setScreenPos(new Coordinate(mapPos));
    }

    private void initBalloon(Coordinate mapPos) {
        initGrass(mapPos);
        GameManager.enemies.add(new Balloon(new Coordinate(mapPos), new Coordinate(mapPos)));
    }

    private void initOneal(Coordinate mapPos) {
        initGrass(mapPos);
    }

    private void initBombItem(Coordinate mapPos) {
        Coordinate screenPos = new Coordinate(mapPos);
        initGrass(mapPos, screenPos, new Block[]{
                new BombItem(mapPos, screenPos),
                new Brick(mapPos, screenPos)
        });
    }

    private void initFlameItem(Coordinate mapPos) {
        Coordinate screenPos = new Coordinate(mapPos);
        initGrass(mapPos, screenPos,new Block[]{
                new FlameItem(mapPos, screenPos),
                new Brick(mapPos, screenPos)
        });
    }

    private void initSpeedItem(Coordinate mapPos) {
        Coordinate screenPos = new Coordinate(mapPos);
        initGrass(mapPos, screenPos, new Block[]{
                new SpeedItem(mapPos, screenPos),
                new Brick(mapPos, screenPos)
        });
    }
    private void initWallPassItem(Coordinate mapPos) {
        Coordinate screenPos = new Coordinate(mapPos);
        initGrass(mapPos, screenPos, new Block[]{
                new WallPassItem(mapPos, screenPos),
                new Brick(mapPos, screenPos)
        });
    }
    private void initBombPassItem(Coordinate mapPos) {
        Coordinate screenPos = new Coordinate(mapPos);
        initGrass(mapPos, screenPos, new Block[]{
                new BombPassItem(mapPos, screenPos),
                new Brick(mapPos, screenPos)
        });
    }

    public void loadMap(int level) throws FileNotFoundException {
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
                    // Blocks & Tiles
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
                    case WALLPASS_ITEM -> initWallPassItem(new Coordinate(i, j).multiply(Sprite.SCALED_SIZE));
                    case BOMBPASS_ITEM -> initBombPassItem(new Coordinate(i, j).multiply(Sprite.SCALED_SIZE));
                    // Grass as default
                    default -> initGrass(new Coordinate(i, j).multiply(Sprite.SCALED_SIZE));
                }
            }
        }
        myReader.close();
    }
}
