package uet.group85.bomberman.entities.items;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.bomb.Bomb;
import uet.group85.bomberman.graphics.Sprite;

public class FlameItem extends Item {
    private final Image img;
    public FlameItem(BombermanGame engine, Coordinate mapPos, Coordinate screenPos) {
        super(engine, mapPos, screenPos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE));

        img = Sprite.powerup_flames.getFxImage();
    }

    @Override
    public void update() {
        Coordinate bomberUnitPos = engine.bomberman.getMapPos().add(12, 16).divide(Sprite.SCALED_SIZE);
        if (bomberUnitPos.equals(mapPos.divide(Sprite.SCALED_SIZE))) {
            engine.bombs.forEach(Bomb::increaseFlameLen);
            isExist = false;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, this.screenPos.x, this.screenPos.y);
    }
}
