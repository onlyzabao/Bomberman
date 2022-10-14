package uet.group85.bomberman.entities.blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;

public class Brick extends Block {
    private final double ELIMINATION_PERIOD = 0.3;
    private double eliminatedTime;
    private boolean isEliminating;
    private final Image normalImg;
    private final Image[] breakingImg;

    public Brick(Coordinate mapPos, Coordinate screenPos) {
        super(mapPos, screenPos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE), true);
        isEliminating = false;
        normalImg = Sprite.brick.getFxImage();
        breakingImg = new Image[]{
                Sprite.brick_exploded.getFxImage(),
                Sprite.brick_exploded1.getFxImage(),
                Sprite.brick_exploded2.getFxImage()
        };
    }

    public void eliminateNow(double time) {
        eliminatedTime = time;
        isEliminating = true;
    }

    private Image getFrame(Image[] frame, double time) {
        double frameDuration = 0.1;
        int index = (int) (((time - eliminatedTime) % ELIMINATION_PERIOD) / frameDuration);
        return frame[index];
    }

    @Override
    public void update() {
        if (isEliminating) {
            if (GameManager.elapsedTime - eliminatedTime > ELIMINATION_PERIOD) {
                isExist = false;
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isEliminating) {
            gc.drawImage(normalImg, this.screenPos.x, this.screenPos.y);
        } else {
            gc.drawImage(getFrame(breakingImg, GameManager.elapsedTime), this.screenPos.x, this.screenPos.y);
        }
    }
}