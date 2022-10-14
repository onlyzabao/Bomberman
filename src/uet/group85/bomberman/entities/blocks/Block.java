package uet.group85.bomberman.entities.blocks;

import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;

public abstract class Block extends Entity {
    boolean isPassable;

    public Block(BombermanGame engine,
                 Coordinate mapPos, Coordinate screenPos,
                 Rectangle solidArea,
                 boolean isPassable) {
        super(engine, mapPos, screenPos, solidArea);
        this.isPassable = isPassable;
    }

    public boolean isPassable() {
        return isPassable;
    }

    public void setPassable(boolean passable) {
        isPassable = passable;
    }
}
