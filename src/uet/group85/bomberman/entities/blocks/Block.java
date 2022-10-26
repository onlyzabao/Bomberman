package uet.group85.bomberman.entities.blocks;

import uet.group85.bomberman.uitilities.Coordinate;
import uet.group85.bomberman.uitilities.Rectangle;
import uet.group85.bomberman.entities.Entity;

public abstract class Block extends Entity {
    protected boolean isExist;
    public Block(Coordinate mapPos, Coordinate screenPos, Rectangle solidArea, boolean isExist) {
        super(mapPos, screenPos, solidArea);
        this.isExist = isExist;
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }
}
