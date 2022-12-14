package uet.group85.bomberman.entities.blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.uitilities.Coordinate;
import uet.group85.bomberman.uitilities.Rectangle;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;
import uet.group85.bomberman.managers.SoundManager;

public class Portal extends Block {
    private final double WAITING_PERIOD = 2.0;
    private final Image img;
    private double waitedTime;
    private boolean isFinished;

    public Portal(Coordinate mapPos, Coordinate screenPos) {
        super(mapPos, screenPos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE), true);
        img = Sprite.portal.getFxImage();
        isFinished = false;
    }

    @Override
    public void update() {
        if (isFinished) {
            if (GameManager.elapsedTime - waitedTime > WAITING_PERIOD) {
                GameManager.status = GameManager.Status.WON;
            }
            return;
        }
        if (GameManager.bomber.isCollided(this)) {
            if (GameManager.bomber.isLiving() && GameManager.enemies.isEmpty()) {
                waitedTime = GameManager.elapsedTime;
                SoundManager.playGameSound("Stage_finished", true);
                isFinished = true;
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, this.screenPos.x, this.screenPos.y);
    }
}