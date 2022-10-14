package uet.group85.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;

public class Flame extends Entity {
    // Specifications
    private final double explodeTime;
    private final double explodeDuration;
    // Animation
    private final Image[] img;
    private final double frameDuration;

    public Flame(Coordinate mapPos, Coordinate screenPos, Image[] img) {
        super(mapPos, screenPos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE));

        this.img = img;
        frameDuration = 0.1;

        explodeDuration = 0.3;
        explodeTime = GameManager.elapsedTime;
    }

    private Image getFrame(Image[] frame, double time) {
        int index = (int) (((time - explodeTime) % explodeDuration) / frameDuration);
        return frame[index];
    }

    private void checkCollision() {
        Coordinate bomberUnitPos = GameManager.bomber.getMapPos().add(12, 16).divide(Sprite.SCALED_SIZE);
        if (bomberUnitPos.equals(mapPos.divide(Sprite.SCALED_SIZE))) {
            GameManager.bomber.eliminateNow(GameManager.elapsedTime);
        }
        // TODO: Check with enemies
    }

    @Override
    public void update() {
        if (GameManager.elapsedTime - explodeTime > explodeDuration) {
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
