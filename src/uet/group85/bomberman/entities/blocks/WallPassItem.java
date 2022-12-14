package uet.group85.bomberman.entities.blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.group85.bomberman.entities.characters.Kondoria;
import uet.group85.bomberman.entities.characters.Minvo;
import uet.group85.bomberman.uitilities.Coordinate;
import uet.group85.bomberman.uitilities.Rectangle;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;
import uet.group85.bomberman.managers.SoundManager;

public class WallPassItem extends Block implements Item {
    private final Image img;

    public WallPassItem(Coordinate mapPos, Coordinate screenPos) {
        super(mapPos, screenPos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE), true);
        img = Sprite.powerup_wallpass.getFxImage();
    }

    @Override
    public void update() {
        if (GameManager.bomber.isCollided(this)) {
            GameManager.bomber.setCanPassBrick(true);
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
            GameManager.enemies.add(new Kondoria(new Coordinate(mapPos), new Coordinate(mapPos)));
        }
        isExist = false;
    }
}
