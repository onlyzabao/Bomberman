package uet.group85.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Flame extends Entity {
    private final BombermanGame engine;
    private final double explodeTime;
    private final double explodeDuration;
    // Sprites
    private final Image[] img;
    private final double frameDuration;

    public Flame(BombermanGame engine, Coordinate pos,Image[] img) {
        super(pos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE));

        this.engine = engine;
        this.img = img;

        frameDuration = 0.1;

        explodeDuration = 0.3;
        isExist = true;
        explodeTime = engine.elapsedTime;
    }

    public Image getFrame(Image[] frame, double time) {
        int index = (int) (((time - explodeTime) % explodeDuration) / frameDuration);
        return frame[index];
    }

    @Override
    public void update() {
        if (engine.elapsedTime - explodeTime > explodeDuration) {
            this.isExist = false;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(getFrame(img, engine.elapsedTime), pos.x, pos.y);
    }
}
