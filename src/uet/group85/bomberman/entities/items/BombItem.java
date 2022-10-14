package uet.group85.bomberman.entities.items;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.bomb.Bomb;
import uet.group85.bomberman.graphics.Sprite;

public class BombItem extends Item {
    private final Image img;
    public BombItem(BombermanGame engine, Coordinate mapPos, Coordinate screenPos) {
        super(engine, mapPos, screenPos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE));

        this.img = Sprite.powerup_bombs.getFxImage();
    }

    @Override
    public void update() {
        Coordinate bomberUnitPos = engine.bomberman.getMapPos().add(12, 16).divide(Sprite.SCALED_SIZE);
        if (bomberUnitPos.equals(mapPos.divide(Sprite.SCALED_SIZE))) {
            engine.bombs.add(new Bomb(engine, engine.bombs.get(0).getFlameLen()));
            isExist = false;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, this.screenPos.x, this.screenPos.y);
    }
}
