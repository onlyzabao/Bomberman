package uet.group85.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.graphics.Sprite;

public class Flame extends Entity {
    private final BombermanGame engine;
    // Specifications
    private final double explodeTime;
    private final double explodeDuration;
    // Animation
    private final Image[] img;
    private final double frameDuration;

    public Flame(BombermanGame engine, Coordinate pos,Image[] img) {
        super(pos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE));
        isExist = true;

        this.engine = engine;

        this.img = img;
        frameDuration = 0.1;

        explodeDuration = 0.3;
        explodeTime = engine.elapsedTime;
    }

    private Image getFrame(Image[] frame, double time) {
        int index = (int) (((time - explodeTime) % explodeDuration) / frameDuration);
        return frame[index];
    }

    private void checkCollision() {
        Coordinate bomberUnitPos = engine.bomberman.getPos().add(12, 16).divide(Sprite.SCALED_SIZE);
        if (bomberUnitPos.equals(pos.divide(Sprite.SCALED_SIZE))) {
            engine.bomberman.eliminateNow(engine.elapsedTime);
        }
        // TODO: Check with enemies
    }

    @Override
    public void update() {
        if (engine.elapsedTime - explodeTime > explodeDuration) {
            this.isExist = false;
        } else {
            checkCollision();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(getFrame(img, engine.elapsedTime), pos.x, pos.y);
    }
}
