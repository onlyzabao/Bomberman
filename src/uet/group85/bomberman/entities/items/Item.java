package uet.group85.bomberman.entities.items;

import javafx.scene.image.Image;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.entities.Entity;

public abstract class Item extends Entity {
    public Item(Coordinate pos, Image img) {
        super(pos, img);
    }

    @Override
    public void update() {

    }
}
