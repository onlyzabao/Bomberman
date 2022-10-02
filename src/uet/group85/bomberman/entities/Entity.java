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

    public Entity(Coordinate pos, Rectangle solidArea) {
        this.pos = pos.multiply(Sprite.SCALED_SIZE);
        this.solidArea = solidArea;
    }

    public Coordinate getPos() {
        return pos;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }
    public Bound getHitBox() {
        Bound hitBox = new Bound();
        hitBox.update(this);
        return hitBox;
    }

    public abstract void update();
    public abstract void render(GraphicsContext gc);
}
