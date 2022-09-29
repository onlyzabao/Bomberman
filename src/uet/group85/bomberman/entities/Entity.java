package uet.group85.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.graphics.Sprite;

public abstract class Entity {
    protected Coordinate pos;
    protected Image img;

    public Entity(Coordinate pos, Image img) {
        this.pos = pos.multiply(Sprite.SCALED_SIZE);
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, pos.getX(), pos.getY());
    }
    public abstract void update();
}
