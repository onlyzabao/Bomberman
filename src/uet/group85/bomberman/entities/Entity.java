package uet.group85.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.graphics.Sprite;

public abstract class Entity {
    // Position
    protected Coordinate pos;
    // Hit box
    protected Rectangle box;

    public Entity(Coordinate pos, Rectangle box) {
        this.pos = pos.multiply(Sprite.SCALED_SIZE);
        this.box = box;
    }

    public Coordinate getPos() {
        return pos;
    }

    public Rectangle getBox() {
        return box;
    }

    public abstract void update();
    public abstract void render(GraphicsContext gc);
}
