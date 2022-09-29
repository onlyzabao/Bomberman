package uet.group85.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.group85.bomberman.graphics.Sprite;

public abstract class Entity {
    protected Coordinate pos;
    protected Image img;

    public Entity(Coordinate pos, Image img) {
        this.pos = pos.multiply(Sprite.SCALED_SIZE); // TODO: Should I multiply with SCALED SIZE or not?
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, pos.getX(), pos.getY());
    }
    public abstract void update(); // TODO: Do I need another update method with parameters is input array?
}
