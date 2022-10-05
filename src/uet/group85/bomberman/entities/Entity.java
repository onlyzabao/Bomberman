package uet.group85.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import uet.group85.bomberman.auxilities.Bound;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.graphics.Sprite;

public abstract class Entity {
    // Position
    protected Coordinate pos;
    // Solid area
    protected Rectangle solidArea;
    // Hit box
    protected Bound hitBox;

    public Entity(Coordinate pos, Rectangle solidArea) {
        this.pos = pos.multiply(Sprite.SCALED_SIZE);
        this.solidArea = solidArea;
        this.hitBox = new Bound(this);
    }

    public Coordinate getPos() {
        return pos;
    }

    public void setPos(Coordinate pos) {
        this.pos = pos;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }
    public Bound getHitBox() {
        return hitBox;
    }

    public abstract void update();
    public abstract void render(GraphicsContext gc);
}
