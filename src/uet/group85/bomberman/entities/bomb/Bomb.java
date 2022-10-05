package uet.group85.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.entities.blocks.Grass;
import uet.group85.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    private final BombermanGame engine;
    enum FrameType {
        COUNTDOWN, EXPLODE
    }
    // Specifications
    private boolean isCountingDown;
    // Sprites
    private final Image[] bomb;
    private final Image[] explosion;
    private final double[] frameDuration;
    // Time
    private double countDownTime;
    private final double countDownDuration;

    public Bomb(BombermanGame engine) {
        super(new Coordinate(0, 0), new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE));

        this.engine = engine;

        bomb = new Image[]{
                Sprite.bomb.getFxImage(),
                Sprite.bomb_1.getFxImage(),
                Sprite.bomb_2.getFxImage()
        };
        explosion = new Image[]{
                Sprite.bomb_exploded.getFxImage(),
                Sprite.bomb_exploded1.getFxImage(),
                Sprite.bomb_exploded2.getFxImage()
        };

        frameDuration = new double[] {0.15, 1.0};

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
            gc.drawImage(getFrame(bomb, engine.elapsedTime,
                    frameDuration[FrameType.COUNTDOWN.ordinal()]), pos.x, pos.y);
        }
    }
}
