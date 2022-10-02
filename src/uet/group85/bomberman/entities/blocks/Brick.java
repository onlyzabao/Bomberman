package uet.group85.bomberman.entities.blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.graphics.Sprite;

public class Brick extends Block {

    private final Image img;
    public Brick(Coordinate pos) {
        super(pos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE));
        isPassable = false;
        img = Sprite.brick.getFxImage();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, pos.x, pos.y);
    }
}