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
import uet.group85.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MapManager {
    private final BombermanGame engine;
    private static final char WALL = '#';
    private static final char BRICK = '*';
    private static final char PORTAL = 'x';
    private static final char BOMBER = 'p';
    private static final char BALLOON = '1';
    private static final char ONEAL = '2';
    private static final char BOMB_ITEM = 'b';
    private static final char FLAME_ITEM = 'f';
    private static final char SPEED_ITEM = 's';

    public MapManager(BombermanGame engine) {
        this.engine = engine;
    }

    private void initWall(Coordinate mapPos) {
        engine.blocks.add(new Wall(engine, mapPos));
    }

    private void initGrass(Coordinate mapPos) {
        engine.blocks.add(new Grass(engine, mapPos, new Coordinate(mapPos)));
    }

    private void initGrass(Coordinate mapPos, Coordinate screenPos, Entity[] layers) {
        Grass belowBlock = new Grass(engine, mapPos, screenPos);
        for (Entity layer : layers) {
            belowBlock.addLayer(layer);
        }
        engine.blocks.add(belowBlock);
    }

    private void initBrick(Coordinate mapPos) {
        Coordinate screenPos = new Coordinate(mapPos);
        initGrass(mapPos, screenPos, new Entity[]{new Brick(engine, mapPos, screenPos)});
    }

    private void initPortal(Coordinate mapPos) {
        Coordinate screenPos = new Coordinate(mapPos);
        initGrass(mapPos, screenPos, new Entity[]{
                new Portal(engine, mapPos, screenPos),
                new Brick(engine, mapPos, screenPos)
        });
    }

    private void initBomber(Coordinate mapPos) {
        initGrass(mapPos);
        engine.bomberman = new Bomber(engine, new Coordinate(mapPos));
        engine.bombs.add(new Bomb(engine, 1));
    }

    private void initBalloon(Coordinate mapPos) {
        initGrass(mapPos);
    }

    private void initOneal(Coordinate mapPos) {
        initGrass(mapPos);
    }

    private void initBombItem(Coordinate mapPos) {
        Coordinate screenPos = new Coordinate(mapPos);
        initGrass(mapPos, screenPos, new Entity[]{
                new BombItem(engine, mapPos, screenPos),
                new Brick(engine, mapPos, screenPos)
        });
    }

    private void initFlameItem(Coordinate mapPos) {
        Coordinate screenPos = new Coordinate(mapPos);
        initGrass(mapPos, screenPos,new Entity[]{
                new FlameItem(engine, mapPos, screenPos),
                new Brick(engine, mapPos, screenPos)
        });
    }

    private void initSpeedItem(Coordinate mapPos) {
        Coordinate screenPos = new Coordinate(mapPos);
        initGrass(mapPos, screenPos, new Entity[]{
                new SpeedItem(engine, mapPos, screenPos),
                new Brick(engine, mapPos, screenPos)
        });
    }

    private void reset() {
        engine.blocks.clear();
        engine.enemies.clear();
        engine.bombs.clear();
        engine.bomberman = null;
    }

    public void loadMap(int level) throws FileNotFoundException {
        reset();
        String path = String.format("res/levels/Level%d.txt", level);
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
