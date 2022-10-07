package uet.group85.bomberman.entities.blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.graphics.Sprite;

public class Brick extends Block {
    private final BombermanGame engine;
    private final Image normalImg;
    private final Image[] breakingImg;
    private final double frameDuration;
    private boolean isBreaking;
    private double breakTime;
    private final double breakDuration;
    public Brick(BombermanGame engine, Coordinate pos) {
        super(pos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE));

        this.engine = engine;

        isPassable = false;

        normalImg = Sprite.brick.getFxImage();

        breakingImg = new Image[]{
                Sprite.brick_exploded.getFxImage(),
                Sprite.brick_exploded1.getFxImage(),
                Sprite.brick_exploded2.getFxImage()
        };

        frameDuration = 0.1;
        breakDuration = 0.3;
        isBreaking = false;
    }

    public void breakNow(double breakTime) {
        this.breakTime = breakTime;
        isBreaking = true;
    }

    public Image getFrame(Image[] frame, double time) {
        int index = (int) (((time - breakTime) % breakDuration) / frameDuration);
        return frame[index];
    }

    @Override
    public void update() {
        if (isBreaking) {
            if (engine.elapsedTime - breakTime > breakDuration) {
                isExist = false;
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isBreaking) {
            gc.drawImage(normalImg, pos.x, pos.y);
        } else {
            gc.drawImage(getFrame(breakingImg, engine.elapsedTime), pos.x, pos.y);
        }

    }
}