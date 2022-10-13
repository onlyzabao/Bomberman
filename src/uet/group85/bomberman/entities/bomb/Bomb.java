package uet.group85.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.entities.blocks.Block;
import uet.group85.bomberman.entities.blocks.Brick;
import uet.group85.bomberman.entities.blocks.Grass;
import uet.group85.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    // Specifications
    private boolean isCountingDown;
    private double countDownTime;
    private final double countDownDuration;
    private int flameLen;
    // Animation
    private final Image[] countDownImg;
    private final Image[][] explodeImg;
    private final double frameDuration;

    public Bomb(BombermanGame engine, int flameLen) {
        super(engine, new Coordinate(0, 0), new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE));
        isExist = false;

        countDownImg = new Image[]{
                Sprite.bomb.getFxImage(),
                Sprite.bomb_1.getFxImage(),
                Sprite.bomb_2.getFxImage()
        };
        explodeImg = new Image[][]{
                {
                        Sprite.explosion_vertical_top_last.getFxImage(),
                        Sprite.explosion_vertical_top_last1.getFxImage(),
                        Sprite.explosion_vertical_top_last2.getFxImage()
                },
                {
                        Sprite.explosion_vertical_down_last.getFxImage(),
                        Sprite.explosion_vertical_down_last1.getFxImage(),
                        Sprite.explosion_vertical_down_last2.getFxImage()
                },
                {
                        Sprite.explosion_horizontal_left_last.getFxImage(),
                        Sprite.explosion_horizontal_left_last1.getFxImage(),
                        Sprite.explosion_horizontal_left_last2.getFxImage()
                },
                {
                        Sprite.explosion_horizontal_right_last.getFxImage(),
                        Sprite.explosion_horizontal_right_last1.getFxImage(),
                        Sprite.explosion_horizontal_right_last2.getFxImage()
                },
                {
                        Sprite.explosion_horizontal.getFxImage(),
                        Sprite.explosion_horizontal1.getFxImage(),
                        Sprite.explosion_horizontal2.getFxImage()
                },
                {
                        Sprite.explosion_vertical.getFxImage(),
                        Sprite.explosion_vertical1.getFxImage(),
                        Sprite.explosion_vertical2.getFxImage()
                },
                {
                        Sprite.bomb_exploded.getFxImage(),
                        Sprite.bomb_exploded1.getFxImage(),
                        Sprite.bomb_exploded2.getFxImage()
                }
        };
        frameDuration = 0.15;

        isCountingDown = false;
        countDownDuration = 2.0;
        this.flameLen = flameLen;
    }

    private Image getFrame(Image[] frame, double time) {
        int index = (int) ((time % (frame.length * frameDuration)) / frameDuration);
        return frame[index];
    }

    public void create(Coordinate mapPos, Coordinate screenPos) {
        this.mapPos = mapPos;
        this.screenPos = screenPos;
        isExist = true;
        isCountingDown = true;
        countDownTime = engine.elapsedTime;
    }

    private void countDown() {
        if (engine.elapsedTime - countDownTime > countDownDuration) {
            isCountingDown = false;
        } else {
            Coordinate bomberUnitPos = engine.bomberman.getMapPos().add(12, 16).divide(Sprite.SCALED_SIZE);
            if (bomberUnitPos.equals(this.mapPos.divide(Sprite.SCALED_SIZE))) {
                Block belowBlock = engine.blocks.get(BombermanGame.COLUMNS * (bomberUnitPos.y) + (bomberUnitPos.x));
                belowBlock.setPassable(true);
            }
        }
    }

    private void explode() {
        Coordinate thisUnitPos = this.mapPos.divide(Sprite.SCALED_SIZE);
        for (int i = 1; i <= 4; i++) { // 4 directions
            for (int j = 0; j <= flameLen; j++) {
                int x = (i % 2 == 0 ? 1 : -1) * (i <= 2 ? 0 : 1);
                int y = (i % 2 == 0 ? 1 : -1) * (i <= 2 ? 1 : 0);
                Coordinate tmpUnitPos = new Coordinate(thisUnitPos.x + (j * x), thisUnitPos.y + (j * y));
                Grass belowBlock = (Grass) engine.blocks.get(BombermanGame.COLUMNS * (tmpUnitPos.y) + (tmpUnitPos.x));
                if (j == 0 && i == 1) {
                    belowBlock.addLayer(new Flame(engine, belowBlock.getMapPos(),
                            belowBlock.getScreenPos(), explodeImg[6])); // Middle flame
                } else if (0 < j && j < flameLen) {
                    belowBlock.addLayer(new Flame(engine, belowBlock.getMapPos(),
                            belowBlock.getScreenPos(), explodeImg[i <= 2 ? 5 : 4])); // Orientation
                } else if (j == flameLen) {
                    belowBlock.addLayer(new Flame(engine, belowBlock.getMapPos(),
                            belowBlock.getScreenPos(), explodeImg[i - 1])); // Last flame
                }
                Block nextBlock = engine.blocks.get(BombermanGame.COLUMNS * (tmpUnitPos.y + y) + (tmpUnitPos.x + x));
                if (!nextBlock.isPassable() && j != flameLen) {
                    if (nextBlock instanceof Grass) {
                        Entity layer = ((Grass) nextBlock).getLayer();
                        if (layer instanceof Brick) {
                            ((Brick) layer).breakNow(engine.elapsedTime);
                        }
                    }
                    break;
                }
            }
        }
        this.isExist = false;
    }

    public int getFlameLen() {
        return flameLen;
    }

    public void increaseFlameLen() {
        this.flameLen += 1;
    }

    @Override
    public void update() {
        if (isCountingDown) {
            countDown();
        } else {
            explode();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (isCountingDown) {
            gc.drawImage(getFrame(countDownImg, engine.elapsedTime), this.screenPos.x, this.screenPos.y);
        }
    }
}
