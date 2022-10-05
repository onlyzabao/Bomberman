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
    // Specifications
    private boolean isBombed;
    private boolean isCountingDown;
    private Block belowBlock;
    // Sprites
    private final Image[] bomb;
    private final Image[] explosion;
    private final double frameDuration;
    // Time
    private double countDownTime;
    private final double countDownDuration;

    public Bomb(BombermanGame engine) {
        super(new Coordinate(0, 0), new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE));

        this.engine = engine;

        isBombed = false;
        isCountingDown = false;
        belowBlock = null;

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

        countDownDuration = 2.0;
    }

    public Image getFrame(Image[] frame, double time) {
        int index = (int) ((time % (frame.length * frameDuration)) / frameDuration);
        return frame[index];
    }

    private void countDown() {
        Coordinate bomberUnitPos = engine.bomberman.getPos().add(12, 16).divide(Sprite.SCALED_SIZE);
        if (isCountingDown) {
            if (engine.elapsedTime - countDownTime > countDownDuration) {
                belowBlock.setPassable(true);
                this.pos.reset();
                isCountingDown = false;
                isBombed = false;
            } else if (!bomberUnitPos.equals(this.pos.divide(Sprite.SCALED_SIZE))) {
                belowBlock.setPassable(false);
            }
        } else {
            Coordinate thisBombPos = new Coordinate(bomberUnitPos).multiply(Sprite.SCALED_SIZE);
            boolean isSeparate = true;
            for (Bomb instance : engine.bombs) {
                Coordinate otherBombPos = instance.getPos();
                if (otherBombPos.equals(thisBombPos)) {
                    isSeparate = false;
                    break;
                }
            }
            if (isSeparate) {
                this.setPos(thisBombPos);
                isCountingDown = true;
                Coordinate bombUnitPos = this.pos.divide(Sprite.SCALED_SIZE);
                belowBlock = engine.blocks.get(BombermanGame.WIDTH * (bombUnitPos.y) + (bombUnitPos.x));
                countDownTime = engine.elapsedTime;;
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
