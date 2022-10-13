package uet.group85.bomberman.entities.blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.graphics.Sprite;

public class Wall extends Block {
    // Animation
    private final Image img;

    public Wall(BombermanGame engine, Coordinate mapPos) {
        super(engine, mapPos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE));
        isPassable = false;

        img = Sprite.wall.getFxImage();
    }

    @Override
    public void update() {}

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, this.screenPos.x, this.screenPos.y);
    }
}
