package uet.group85.bomberman.entities.characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;

public class Balloon extends Character {
    enum FrameType {
        MOVING, INJURED, DYING
    }
    private boolean isMoving;

    public Balloon(Coordinate mapPos, Coordinate screenPos) {
        super(mapPos, screenPos, new Rectangle(0, 0, 32, 32), 2, 3, true);

        defaultFrame = new Image[] {
                Sprite.balloom_dead.getFxImage()
        };
        dyingFrame = new Image[] {
                Sprite.mob_dead1.getFxImage() ,
                Sprite.mob_dead2.getFxImage(),
                Sprite.mob_dead3.getFxImage()
        };
        movingFrame = new Image[][] {
                {Sprite.balloom_left1.getFxImage(), Sprite.balloom_left2.getFxImage(), Sprite.balloom_left3.getFxImage()},
                {Sprite.balloom_right1.getFxImage(), Sprite.balloom_right2.getFxImage(), Sprite.balloom_right3.getFxImage()}
        };
        frameDuration = new double[] {0.2, 0.3, 1.0};

        isMoving = true;
    }

    private void updateMapPos() {
        if (++stepCounter == stepDuration) {
            this.hitBox.update(mapPos, solidArea);
            if (mapPos.x % Sprite.SCALED_SIZE == 0 && mapPos.y % Sprite.SCALED_SIZE == 0) {
                checkDirection(GameManager.tiles);
                if (isBlocked[stepDirection.ordinal()] || isBlocked[Direction.NONE.ordinal()]) {
                    for (int i = 0; i < 4; i++) {
                        if (!isBlocked[i]) {
                            stepDirection = Direction.values()[i];
                            break;
                        }
                    }
                }
            }
            step();
            stepCounter = 0;
        }
    }

    private void updateScreenPos() {
        this.screenPos.x = mapPos.x - GameManager.bomber.getMapPos().x + GameManager.bomber.getScreenPos().x;
        this.screenPos.y = mapPos.y - GameManager.bomber.getMapPos().y + GameManager.bomber.getScreenPos().y;
    }

    private void step() {
        switch (stepDirection) {
            case UP -> mapPos.y -= stepLength;
            case DOWN -> mapPos.y += stepLength;
            case LEFT -> mapPos.x -= stepLength;
            case RIGHT -> mapPos.x += stepLength;
        }
    }

    @Override
    public void update() {
        if (isExist) {
            if (!isDying) {
                updateMapPos();
                updateScreenPos();
            } else if (GameManager.elapsedTime - deadTime > DYING_PERIOD) {
                GameManager.enemies.remove(this);
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isDying) {
            if (isMoving) {
                gc.drawImage(getFrame(movingFrame[stepDirection.ordinal() < 2 ? 0 : 1], GameManager.elapsedTime,
                    frameDuration[Balloon.FrameType.MOVING.ordinal()]), screenPos.x, screenPos.y);
            }
        } else {
            // TODO: Render dead frame
            gc.drawImage(getFrame(dyingFrame, GameManager.elapsedTime,
                    frameDuration[Balloon.FrameType.DYING.ordinal()]), screenPos.x, screenPos.y);
        }
    }
}
