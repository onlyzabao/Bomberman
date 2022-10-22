package uet.group85.bomberman.entities.blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.auxiliaries.Coordinate;
import uet.group85.bomberman.auxiliaries.Rectangle;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.entities.tiles.Grass;
import uet.group85.bomberman.entities.tiles.Tile;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;
import uet.group85.bomberman.managers.SoundManager;

public class Bomb extends Block {
    private final double COUNTDOWN_PERIOD = 2.0;
    private boolean isCountingDown;
    private double countDownTime;
    private int flameLen;
    // Animation
    private final Image[] countingDownImg;
    private final Image[][] explodingImg;

    public Bomb(int flameLen) {
        super(new Coordinate(0, 0), new Coordinate(0, 0),
                new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE), false);

        countingDownImg = new Image[]{
                Sprite.bomb.getFxImage(),
                Sprite.bomb_1.getFxImage(),
                Sprite.bomb_2.getFxImage()
        };
        explodingImg = new Image[][]{
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

        isCountingDown = false;
        this.flameLen = flameLen;
    }

    public void create(Coordinate mapPos, Coordinate screenPos) {
        SoundManager.playGameSound("Bomb", false);
        this.mapPos = mapPos;
        this.screenPos = screenPos;
        isExist = true;
        isCountingDown = true;
        countDownTime = GameManager.elapsedTime;
    }

    private void countDown() {
        if (GameManager.elapsedTime - countDownTime > COUNTDOWN_PERIOD) {
            SoundManager.playGameSound("Explosion", false);
            isCountingDown = false;
        }
    }

    private void explode() {
        Coordinate thisUnitPos = this.mapPos.divide(Sprite.SCALED_SIZE);
        for (int i = 1; i <= 4; i++) { // 4 directions
            for (int j = 0; j <= flameLen; j++) {
                int x = (i % 2 == 0 ? 1 : -1) * (i <= 2 ? 0 : 1);
                int y = (i % 2 == 0 ? 1 : -1) * (i <= 2 ? 1 : 0);

                Coordinate tmpUnitPos = new Coordinate(thisUnitPos.x + (j * x), thisUnitPos.y + (j * y));
                Grass belowTile = (Grass) GameManager.tiles.get(tmpUnitPos.y).get(tmpUnitPos.x);
                if (j == 0 && i == 1) {
                    belowTile.addLayer(new Flame(belowTile.getMapPos(), belowTile.getScreenPos(), explodingImg[6])); // Middle flame
                } else if (0 < j && j < flameLen) {
                    belowTile.addLayer(new Flame(belowTile.getMapPos(), belowTile.getScreenPos(), explodingImg[i <= 2 ? 5 : 4])); // Orientation
                } else if (j == flameLen) {
                    belowTile.addLayer(new Flame(belowTile.getMapPos(), belowTile.getScreenPos(), explodingImg[i - 1])); // Last flame
                }

                Tile nextTile = GameManager.tiles.get(tmpUnitPos.y + y).get(tmpUnitPos.x + x);
                if (!nextTile.isPassable() && j != flameLen) {
                    if (nextTile instanceof Grass) {
                        Entity layer = ((Grass) nextTile).getLayer();
                        if (layer instanceof Brick) {
                            ((Brick) layer).eliminateNow(GameManager.elapsedTime);
                        }
                        if (layer instanceof Bomb) {
                            ((Bomb) layer).setCountingDown(false);
                        }
                    }
                    break;
                }
            }
        }
        this.isExist = false;
    }

    public void setCountingDown(boolean countingDown) {
        isCountingDown = countingDown;
    }

    public int getFlameLen() {
        return flameLen;
    }

    public void increaseFlameLen() {
        this.flameLen += 1;
    }

    private Image getFrame(Image[] frame, double time) {
        double frameDuration = 0.15;
        int index = (int) ((time % (frame.length * frameDuration)) / frameDuration);
        return frame[index];
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
            gc.drawImage(getFrame(countingDownImg, GameManager.elapsedTime), this.screenPos.x, this.screenPos.y);
        }
    }
}
