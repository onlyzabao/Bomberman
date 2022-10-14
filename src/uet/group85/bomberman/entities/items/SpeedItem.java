package uet.group85.bomberman.entities.items;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.graphics.Sprite;

public class SpeedItem extends Item {
    private final Image img;
    public SpeedItem(BombermanGame engine, Coordinate mapPos, Coordinate screenPos) {
        super(engine, mapPos, screenPos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE));

        img = Sprite.powerup_speed.getFxImage();
    }

    @Override
    public void update() {
        Coordinate bomberUnitPos = engine.bomberman.getMapPos().add(12, 16).divide(Sprite.SCALED_SIZE);
        if (bomberUnitPos.equals(mapPos.divide(Sprite.SCALED_SIZE))) {
            engine.bomberman.increaseSpeed();
            isExist = false;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, this.screenPos.x, this.screenPos.y);
    }
}
