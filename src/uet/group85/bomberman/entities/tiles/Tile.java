package uet.group85.bomberman.entities.tiles;

import javafx.scene.canvas.GraphicsContext;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
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

    public void setPassable(boolean passable) {
        isPassable = passable;
    }
}
