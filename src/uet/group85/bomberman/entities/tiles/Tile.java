package uet.group85.bomberman.entities.tiles;

import uet.group85.bomberman.auxiliaries.Coordinate;
import uet.group85.bomberman.auxiliaries.Rectangle;
import uet.group85.bomberman.entities.Entity;

public abstract class Tile extends Entity {
    protected boolean isPassable;

    protected Tile(Coordinate mapPos, Coordinate screenPos, Rectangle solidArea, boolean isPassable) {
        super(mapPos, screenPos, solidArea);
        this.isPassable = isPassable;
    }

    public boolean isPassable() {
        return isPassable;
    }
}
