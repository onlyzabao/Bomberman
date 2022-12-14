package uet.group85.bomberman.entities.blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.group85.bomberman.uitilities.Coordinate;
import uet.group85.bomberman.uitilities.Rectangle;
import uet.group85.bomberman.entities.characters.Balloon;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;
import uet.group85.bomberman.managers.SoundManager;

public class BombItem extends Block implements Item {
    private final Image img;

    public BombItem(Coordinate mapPos, Coordinate screenPos) {
        super(mapPos, screenPos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE), true);
        this.img = Sprite.powerup_bombs.getFxImage();
    }

    @Override
    public void update() {
        if (GameManager.bomber.isCollided(this)) {
            GameManager.bomber.increaseBomb();
            SoundManager.playGameSound("Power_up", false);
            isExist = false;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, this.screenPos.x, this.screenPos.y);
    }

    @Override
    public void spawnEnemy() {
        for (int i = 0; i < 3; i++) {
            GameManager.enemies.add(new Balloon(new Coordinate(mapPos), new Coordinate(mapPos)));
        }
        isExist = false;
    }
}
