package uet.group85.bomberman.entities.blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.graphics.Sprite;

public class Grass extends Entity {
    private final Image img;
    public Grass(Coordinate pos, Rectangle box) {
        super(pos, box);
        img = Sprite.grass.getFxImage();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, pos.x, pos.y);
    }
}

