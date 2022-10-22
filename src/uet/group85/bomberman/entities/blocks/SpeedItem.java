package uet.group85.bomberman.entities.blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.group85.bomberman.auxiliaries.Coordinate;
import uet.group85.bomberman.auxiliaries.Rectangle;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;
import uet.group85.bomberman.managers.SoundManager;

public class SpeedItem extends Block {
    private final Image img;

    public SpeedItem(Coordinate mapPos, Coordinate screenPos) {
        super(mapPos, screenPos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE), true);
        img = Sprite.powerup_speed.getFxImage();
    }

    @Override
    public void update() {
        if (GameManager.bomber.isCollided(this)) {
            GameManager.bomber.increaseSpeed();
            SoundManager.gameSounds.get(SoundManager.GameSound.POWER_UP).play();
            isExist = false;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, this.screenPos.x, this.screenPos.y);
    }
}
