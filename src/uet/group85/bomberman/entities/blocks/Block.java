package uet.group85.bomberman.entities.blocks;

import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;

public abstract class Block extends Entity {
    boolean isPassable;

    public Block(BombermanGame engine, Coordinate mapPos, Rectangle solidArea) {
        super(engine, mapPos, solidArea);
    }

    public boolean isPassable() {
        return isPassable;
    }

    public void setPassable(boolean passable) {
        isPassable = passable;
    }
}
