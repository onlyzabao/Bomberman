package uet.group85.bomberman.entities.blocks;

import javafx.scene.canvas.GraphicsContext;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;

public abstract class Block extends Entity {
    boolean isPassable;

    public Block(Coordinate pos, Rectangle solidArea) {
        super(pos, solidArea);
    }

    public boolean isPassable() {
        return isPassable;
    }

    public void setPassable(boolean passable) {
        isPassable = passable;
    }
}
