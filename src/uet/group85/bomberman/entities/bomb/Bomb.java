package uet.group85.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    private final BombermanGame engine;
    // Specifications
    private boolean isBombed;
    private boolean isCountingDown;
    // Sprites
    private final Image[] bomb;
    private final Image[] explosion;
    private final double frameDuration;
    // Time
    private double startTime;
    private final double countDownTime;

    public Bomb(BombermanGame engine) {
        super(new Coordinate(0, 0), new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE));

        this.engine = engine;

        isBombed = false;
        isCountingDown = false;

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
        frameDuration = 0.15;

        countDownTime = 2.0;
    }

    public Image getFrame(Image[] frame, double time) {
        int index = (int) ((time % (frame.length * frameDuration)) / frameDuration);
        return frame[index];
    }

    private void countDown() {
        if (isCountingDown) {
            if (engine.elapsedTime - startTime > countDownTime) {
                isCountingDown = false;
                isBombed = false;
            }
        } else {
            Coordinate bomberCenter = engine.bomberman.getPos().add(12, 16);
            Coordinate newBombPos = bomberCenter.divide(Sprite.SCALED_SIZE);
            boolean isSeparate = true;
            for (Bomb instance : engine.bombs) {
                Coordinate oldBombPos = instance.getPos().divide(Sprite.SCALED_SIZE);
                if (oldBombPos.equals(newBombPos)) {
                    isSeparate = false;
                    break;
                }
            }
            if (isSeparate) {
                this.setPos(newBombPos.multiply(Sprite.SCALED_SIZE));
                isCountingDown = true;
                startTime = engine.elapsedTime;;
            } else {
                isBombed = false;
            }
        }
    }

    public boolean isBombed() {
        return isBombed;
    }

    public void setBombed(boolean bombed) {
        isBombed = bombed;
    }

    @Override
    public void update() {
        if (isBombed) {
            countDown();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (isBombed) {
            if (isCountingDown) {
                gc.drawImage(getFrame(bomb, engine.elapsedTime), pos.x, pos.y);
            }
        }
    }

}
