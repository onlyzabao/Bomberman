package uet.group85.bomberman.managers;

import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.entities.blocks.*;
import uet.group85.bomberman.entities.bomb.Bomb;
import uet.group85.bomberman.entities.characters.Bomber;
import uet.group85.bomberman.entities.items.BombItem;
import uet.group85.bomberman.entities.items.FlameItem;
import uet.group85.bomberman.entities.items.SpeedItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MapManager {
    private final BombermanGame engine;
    private final char WALL = '#';
    private final char BRICK = '*';
    private final char PORTAL = 'x';
    private final char BOMBER = 'p';
    private final char BALLOON = '1';
    private final char ONEAL = '2';
    private final char BOMB_ITEM = 'b';
    private final char FLAME_ITEM = 'f';
    private final char SPEED_ITEM = 's';

    public MapManager(BombermanGame engine) {
        this.engine = engine;
    }

    private void initWall(int x, int y) {
        engine.blocks.add(new Wall(new Coordinate(x, y)));
    }

    private void initGrass(int x, int y) {
        engine.blocks.add(new Grass(new Coordinate(x, y)));
    }

    private void initGrass(int x, int y, Entity[] layers) {
        Grass belowBlock = new Grass(new Coordinate(x, y));
        for (Entity layer : layers) {
            belowBlock.addLayer(layer);
        }
        engine.blocks.add(belowBlock);
    }

    private void initBrick(int x, int y) {
        initGrass(x, y, new Entity[]{new Brick(engine, new Coordinate(x, y))});
    }

    private void initPortal(int x, int y) {
        initGrass(x, y, new Entity[]{
                new Portal(engine, new Coordinate(x, y)),
                new Brick(engine, new Coordinate(x, y))
        });
    }

    private void initBomber(int x, int y) {
        initGrass(x, y);
        engine.bomberman = new Bomber(engine, new Coordinate(x, y));
        engine.bombs.add(new Bomb(engine, 1));
    }

    private void initBalloon(int x, int y) {
        initGrass(x, y);
    }

    private void initOneal(int x, int y) {
        initGrass(x, y);
    }

    private void initBombItem(int x, int y) {
        initGrass(x, y, new Entity[]{
                new BombItem(engine, new Coordinate(x, y)),
                new Brick(engine, new Coordinate(x, y))
        });
    }

    private void initFlameItem(int x, int y) {
        initGrass(x, y, new Entity[]{
                new FlameItem(engine, new Coordinate(x, y)),
                new Brick(engine, new Coordinate(x, y))
        });
    }

    private void initSpeedItem(int x, int y) {
        initGrass(x, y, new Entity[]{
                new SpeedItem(engine, new Coordinate(x, y)),
                new Brick(engine, new Coordinate(x, y))
        });
    }

    private void reset() {
        engine.blocks.clear();
        engine.enemies.clear();
        engine.bombs.clear();
        engine.bomberman = null;
    }

    public void loadMap(int level) {
        reset();
        String path = String.format("res/levels/Level%d.txt", level);
        try {
            File myFile = new File(path);
            Scanner myReader = new Scanner(myFile);
            int numOfRows = myReader.nextInt();
            int numOfCols = myReader.nextInt();
            myReader.nextLine();
            for (int j = 0; j < numOfRows; j++) {
                String line = myReader.nextLine();
                for (int i = 0; i < numOfCols; i++) {
                    switch (line.charAt(i)) {
                        // Blocks
                        case WALL -> initWall(i, j);
                        case BRICK -> initBrick(i, j);
                        case PORTAL -> initPortal(i, j);
                        // Characters
                        case BOMBER -> initBomber(i, j);
                        case BALLOON -> initBalloon(i, j);
                        case ONEAL -> initOneal(i, j);
                        // Items
                        case BOMB_ITEM -> initBombItem(i, j);
                        case FLAME_ITEM -> initFlameItem(i, j);
                        case SPEED_ITEM -> initSpeedItem(i, j);
                        default -> initGrass(i, j);
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
