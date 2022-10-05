package uet.group85.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.entities.blocks.Block;
import uet.group85.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    private final BombermanGame engine;

    enum FrameType {
        COUNTDOWN, EXPLODE
    }

    // Specifications
    private boolean isCountingDown;
    // Sprites
    private final Image[] img;
    private final double[] frameDuration;
    // Time
    private double countDownTime;
    private final double countDownDuration;

    public Bomb(BombermanGame engine) {
        super(new Coordinate(0, 0), new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE));

        this.engine = engine;

        img = new Image[]{
                Sprite.bomb.getFxImage(),
                Sprite.bomb_1.getFxImage(),
                Sprite.bomb_2.getFxImage()
        };

        frameDuration = new double[]{0.15, 1.0};

        isExist = false;
        isCountingDown = false;
        countDownDuration = 2.0;
    }

    public Image getFrame(Image[] frame, double time, double frameDuration) {
        int index = (int) ((time % (frame.length * frameDuration)) / frameDuration);
        return frame[index];
    }

    private void countDown() {
        if (engine.elapsedTime - countDownTime > countDownDuration) {
            isCountingDown = false;
            isExist = false;
        } else {
            Coordinate bomberUnitPos = engine.bomberman.getPos().add(12, 16).divide(Sprite.SCALED_SIZE);
            if (bomberUnitPos.equals(this.pos.divide(Sprite.SCALED_SIZE))) {
                Block belowBlock = engine.blocks.get(BombermanGame.WIDTH * (bomberUnitPos.y) + (bomberUnitPos.x));
                belowBlock.setPassable(true);
            }
        }
    }

    public void create(Coordinate pos) {
        this.pos = pos;
        isExist = true;
        isCountingDown = true;
        countDownTime = engine.elapsedTime;
    }

    @Override
    public void update() {
        if (isCountingDown) {
            countDown();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (isCountingDown) {
            gc.drawImage(getFrame(img, engine.elapsedTime,
                    frameDuration[FrameType.COUNTDOWN.ordinal()]), pos.x, pos.y);
        }
    }
}
