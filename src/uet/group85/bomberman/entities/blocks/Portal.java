package uet.group85.bomberman.entities.blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;

public class Portal extends Block {

    public Portal(Coordinate pos, Rectangle solidArea) {
        super(pos, solidArea);
        isPassable = true;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {

    }
}