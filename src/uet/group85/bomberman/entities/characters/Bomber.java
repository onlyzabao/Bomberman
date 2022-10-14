package uet.group85.bomberman.entities.characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.blocks.Grass;
import uet.group85.bomberman.entities.bomb.Bomb;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;
import uet.group85.bomberman.managers.ScreenManager;
import uet.group85.bomberman.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Character {
    public static final Coordinate screenMid = new Coordinate(
            (ScreenManager.WIDTH - Sprite.SCALED_SIZE) / 2,
            (ScreenManager.HEIGHT - Sprite.SCALED_SIZE) / 2
    );
    public static final List<Bomb> bombs = new ArrayList<>();
    enum FrameType {
        MOVING, DYING
    }
    // Specifications
    private boolean isMoving;
    private boolean isCoolingDown;
    private double bombTime;
    private final double coolDownDuration;

    // Constructor
    public Bomber() {
        super(new Coordinate(0, 0), new Coordinate(0, 0),
                new Rectangle(0, 0, 24, 32), 2, 3);

        defaultFrame = new Image[]{
                Sprite.player_up.getFxImage(),
                Sprite.player_down.getFxImage(),
                Sprite.player_left.getFxImage(),
                Sprite.player_right.getFxImage()
        };
        dyingFrame = new Image[]{
                Sprite.player_dead1.getFxImage(),
                Sprite.player_dead2.getFxImage(),
                Sprite.player_dead3.getFxImage()
        };
        movingFrame = new Image[][]{
                {Sprite.player_up_1.getFxImage(), Sprite.player_up_2.getFxImage()},
                {Sprite.player_down_1.getFxImage(), Sprite.player_down_2.getFxImage()},
                {Sprite.player_left_1.getFxImage(), Sprite.player_left_2.getFxImage()},
                {Sprite.player_right_1.getFxImage(), Sprite.player_right_2.getFxImage()}
        };
        frameDuration = new double[] {0.2, 0.4};

        isLiving = true;
        isMoving = false;
        isCoolingDown = false;
        coolDownDuration = 0.25;

        bombs.add(new Bomb(1));
    }

    private void updateMapPos() {
        if (++stepCounter == stepDuration) {
            this.hitBox.update(mapPos, solidArea);
            isMoving = false;
            for (int i = 0; i < Direction.TOTAL.ordinal(); i++) {
                if (GameManager.events[i]) {
                    stepDirection = Direction.values()[i];
                    isMoving = true;
                    if (!isCollided(GameManager.blocks)) {
                        step();
                    } else {
                        if (obstacle1.isPassable() ^ obstacle2.isPassable()) {
                            autoStep();
                        }
                    }
                }
            }
            stepCounter = 0;
        }
    }

    private void step() {
        switch (stepDirection) {
            case UP -> mapPos.y -= stepLength;
            case DOWN -> mapPos.y += stepLength;
            case LEFT -> mapPos.x -= stepLength;
            case RIGHT -> mapPos.x += stepLength;
        }
    }

    private void autoStep() {
        if (stepDirection == Direction.UP || stepDirection == Direction.DOWN) {
            if ((hitBox.leftX + 12) / Sprite.SCALED_SIZE == obstacle1.getMapPos().x / Sprite.SCALED_SIZE) {
                mapPos.x += (obstacle1.isPassable() ? -1 : 0) * stepLength / 2;
            } else {
                mapPos.x += (obstacle2.isPassable() ? 1 : 0) * stepLength / 2;
            }
        } else {
            if ((hitBox.topY + 16) / Sprite.SCALED_SIZE == obstacle1.getMapPos().y / Sprite.SCALED_SIZE) {
                mapPos.y += (obstacle1.isPassable() ? -1 : 0) * stepLength / 2;
            } else {
                mapPos.y += (obstacle2.isPassable() ? 1 : 0) * stepLength / 2;
            }
        }
    }

    private void updateBomb() {
        if (GameManager.events[GameScreen.BOMB]) {
            if (isCoolingDown) {
                if (GameManager.elapsedTime - bombTime > coolDownDuration) {
                    isCoolingDown = false;
                }
            } else{
                for (Bomb bomb : bombs) {
                    if (!bomb.isExist()) {
                        Coordinate bomberUnitPos = this.mapPos.add(12, 16).divide(Sprite.SCALED_SIZE);
                        Grass grass = (Grass) GameManager.blocks.get(GameManager.mapCols * (bomberUnitPos.y) + (bomberUnitPos.x));
                        if (!grass.hasOverlay()) {
                            bomb.create(grass.getMapPos(), grass.getScreenPos());
                            grass.addLayer(bomb);
                            isCoolingDown = true;
                            bombTime = GameManager.elapsedTime;
                        }
                        break;
                    }
                }
            }
        }
    }
    private void updateScreenPos() {
        if (mapPos.x < screenMid.x) {
            screenPos.x = mapPos.x;
        } else if ((GameManager.mapCols - 1) * Sprite.SCALED_SIZE - mapPos.x < screenMid.x) {
            screenPos.x = ScreenManager.WIDTH - GameManager.mapCols * Sprite.SCALED_SIZE + mapPos.x;
        } else {
            screenPos.x = screenMid.x;
        }

        if (mapPos.y < screenMid.y) {
            screenPos.y = mapPos.y;
        } else if ((GameManager.mapRows - 1) * Sprite.SCALED_SIZE - mapPos.y < screenMid.y) {
            screenPos.y = ScreenManager.HEIGHT - GameManager.mapRows * Sprite.SCALED_SIZE + mapPos.y;
        } else {
            screenPos.y = screenMid.y;
        }
    }

    @Override
    public void update() {
        if (isLiving) {
            updateMapPos();
            updateScreenPos();
            updateBomb();
        } else if (GameManager.elapsedTime - deadTime > deadDuration) {
            isExist = false;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (isLiving) {
            if (isMoving) {
                gc.drawImage(getFrame(movingFrame[stepDirection.ordinal()], GameManager.elapsedTime,
                        frameDuration[FrameType.MOVING.ordinal()]), screenPos.x, screenPos.y);
            } else {
                gc.drawImage(defaultFrame[stepDirection.ordinal()], screenPos.x, screenPos.y);
            }
        } else {
            gc.drawImage(getFrame(dyingFrame, GameManager.elapsedTime,
                    frameDuration[FrameType.DYING.ordinal()]), screenPos.x, screenPos.y);
        }
    }
}
