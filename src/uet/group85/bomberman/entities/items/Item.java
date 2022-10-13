package uet.group85.bomberman.entities.items;

import javafx.scene.image.Image;
import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;

public abstract class Item extends Entity {
    public Item(BombermanGame engine, Coordinate pos, Rectangle solidArea) {
        super(engine, pos, solidArea);
    }
}
