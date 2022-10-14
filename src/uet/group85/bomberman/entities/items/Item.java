package uet.group85.bomberman.entities.items;

import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;

public abstract class Item extends Entity {
    public Item(Coordinate mapPos, Coordinate screenPos, Rectangle solidArea) {
        super(mapPos, screenPos, solidArea);
    }
}
