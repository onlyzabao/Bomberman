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

    public Wall(BombermanGame engine, Coordinate mapPos, Coordinate screenPos) {
        super(engine, mapPos, screenPos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE),
                false);

        img = Sprite.wall.getFxImage();
    }

    @Override
    public void update() {
        this.screenPos.x = mapPos.x - engine.bomberman.getMapPos().x + engine.bomberman.getScreenPos().x;
        this.screenPos.y = mapPos.y - engine.bomberman.getMapPos().y + engine.bomberman.getScreenPos().y;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, this.screenPos.x, this.screenPos.y);
    }
}
