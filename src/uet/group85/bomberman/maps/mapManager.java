package uet.group85.bomberman.maps;

import javafx.scene.canvas.GraphicsContext;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.entities.blocks.Block;
import uet.group85.bomberman.entities.blocks.Brick;
import uet.group85.bomberman.entities.blocks.Grass;
import uet.group85.bomberman.entities.blocks.Wall;
import uet.group85.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class mapManager {
    //    List<Brick> bricks = new ArrayList<>();
//    List<Wall> walls = new ArrayList<>();
//    List<Grass> grasses = new ArrayList<>();
    List<Block> blocks;
    public int[][] mapTileNum = new int[15][25];

    public mapManager(List<Block> blocks) {
        this.blocks = blocks;
        loadMap();
    }

    protected void initBrick(int x, int y) {
        Coordinate cr = new Coordinate(x, y);
        Brick br = new Brick(cr);
        blocks.add(br);
    }

    protected void initWall(int x, int y) {
        Coordinate cr = new Coordinate(x, y);
        Wall wa = new Wall(cr);
        blocks.add(wa);
    }

    protected void initGrass(int x, int y) {
        Coordinate cr = new Coordinate(x, y);
        Grass gr = new Grass(cr);
        blocks.add(gr);
    }

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
                        case 0:
                            initGrass(col, row);
                            break;
                        case 2:
                            initWall(col, row);
                            break;
                        case 3:
                            initBrick(col, row);
                            break;
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

    public void drawMap(GraphicsContext gc) {
        for (Entity g : blocks) {
            g.render(gc);
        }
    }
}
