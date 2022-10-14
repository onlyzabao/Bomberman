package uet.group85.bomberman.entities.blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;

public class Brick extends Block {
    // Animation
    private final Image normalImg;
    private final Image[] breakingImg;
    private final double frameDuration;
    // Specifications
    private boolean isBreaking;
    private double breakTime;
    private final double breakDuration;

    public Brick(Coordinate mapPos, Coordinate screenPos) {
        super(mapPos, screenPos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE),
                false);

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

    private Image getFrame(Image[] frame, double time) {
        int index = (int) (((time - breakTime) % breakDuration) / frameDuration);
        return frame[index];
    }

    @Override
    public void update() {
        if (isBreaking) {
            if (GameManager.elapsedTime - breakTime > breakDuration) {
                isExist = false;
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isBreaking) {
            gc.drawImage(normalImg, this.screenPos.x, this.screenPos.y);
        } else {
            gc.drawImage(getFrame(breakingImg, GameManager.elapsedTime), this.screenPos.x, this.screenPos.y);
        }
    }
}