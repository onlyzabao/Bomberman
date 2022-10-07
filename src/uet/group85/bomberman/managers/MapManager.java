package uet.group85.bomberman.managers;

import javafx.scene.canvas.GraphicsContext;
import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.entities.blocks.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class MapManager {
    //    List<Brick> bricks = new ArrayList<>();
//    List<Wall> walls = new ArrayList<>();
//    List<Grass> grasses = new ArrayList<>();
//    List<Block> blocks;
    /*
    We already have a list that manager blocks so just pass in BombermanGame class
    as an attribute, that will be more flexible to use.
     */
    private final BombermanGame engine;
    public int[][] mapTileNum = new int[15][25];

    public MapManager(BombermanGame engine) {
        this.engine = engine;
        loadMap();
    }

//    protected void initBrick(int x, int y) {
//        Coordinate cr = new Coordinate(x, y);
//        Brick br = new Brick(cr);
//        engine.blocks.add(br);
//    }

    protected void initWall(int x, int y) {
        Coordinate cr = new Coordinate(x, y);
        Wall wa = new Wall(cr);
        engine.blocks.add(wa);
    }

    protected void initGrass(int x, int y) {
        Coordinate cr = new Coordinate(x, y);
        Grass gr = new Grass(cr);
        engine.blocks.add(gr);
    }
//    protected void initPortal(int x, int y) {
//        Coordinate cr = new Coordinate(x, y);
////        Portal po = new Portal(cr);
////        engine.blocks.add(po);
//    }

    public void loadMap() {
        try {
            File myFile = new File("res/levels/Level1.txt");
            Scanner myReader = new Scanner(myFile);
            int col = 0;
            int row = 0;
            while (col < 25 && row < 15) {
                String line = myReader.nextLine();
                while (col < 25) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[row][col] = num;
                    switch (mapTileNum[row][col]) {
                        case 0 -> initGrass(col, row);
                        case 2 -> initWall(col, row);
//                        case 3 -> initBrick(col, row);
//                        case 6 -> initPortal(col, row);
                    }
                    col++;
                }
                if (col == 25) {
                    col = 0;
                    row++;
                }
            }
            myReader.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

//    public void makeMap() {
//        int col = 0;
//        int row = 0;
//        while (col < 25 && row < 15) {
//            String line = myReader.nextLine();
//            while (col < 25) {
//                String numbers[] = line.split(" ");
//                int num = Integer.parseInt(numbers[col]);
//                mapTileNum[row][col] = num;
//                col++;
//            }
//            if (col == 25) {
//                col = 0;
//                row++;
//            }
//        }
//    }

    /*
    Because of adding BombermanGame class, we don't need an extra method to render.
     */
//    public void drawMap(GraphicsContext gc) {
//        for (Entity g : blocks) {
//            g.render(gc);
//        }
//    }
}
