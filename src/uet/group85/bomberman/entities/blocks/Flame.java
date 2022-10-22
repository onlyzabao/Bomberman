package uet.group85.bomberman.entities.blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.auxiliaries.Coordinate;
import uet.group85.bomberman.auxiliaries.Rectangle;
import uet.group85.bomberman.entities.characters.Character;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;

public class Flame extends Block {
    private final double EXPLOSION_PERIOD = 0.3;
    private final double explodedTime;
    private final Image[] img;

    public Flame(Coordinate mapPos, Coordinate screenPos, Image[] img) {
        super(mapPos, screenPos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE), true);
        this.img = img;
        explodedTime = GameManager.elapsedTime;
    }


    private void checkCollision() {
        if (GameManager.bomber.isCollided(this)) {
            GameManager.bomber.eliminateNow(GameManager.elapsedTime);
        }
        for (Character enemy : GameManager.enemies) {
            if (enemy.isCollided(this)) {
                enemy.eliminateNow(GameManager.elapsedTime);
            }
        }
    }

    private Image getFrame(Image[] frame, double time) {
        double frameDuration = 0.1;
        int index = (int) (((time - explodedTime) % EXPLOSION_PERIOD) / frameDuration);
        return frame[index];
    }

    @Override
    public void update() {
        if (GameManager.elapsedTime - explodedTime > EXPLOSION_PERIOD) {
            this.isExist = false;
        } else {
            checkCollision();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(getFrame(img, GameManager.elapsedTime), this.screenPos.x, this.screenPos.y);
    }
}
