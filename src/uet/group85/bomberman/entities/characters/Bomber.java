package uet.group85.bomberman.entities.characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.KeyCode;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.blocks.Grass;
import uet.group85.bomberman.entities.bomb.Bomb;
import uet.group85.bomberman.graphics.Sprite;

public class Bomber extends Character {
    public static final Coordinate MIDDLE = new Coordinate(
            (BombermanGame.WIDTH - Sprite.SCALED_SIZE) / 2,
            (BombermanGame.HEIGHT - Sprite.SCALED_SIZE) / 2
    );
    enum FrameType {
        MOVING, DYING
    }
    // Specifications
    private boolean isMoving;
    private boolean isCoolingDown;
    private double bombTime;
    private final double coolDownDuration;

    // Constructor
    public Bomber(BombermanGame engine, Coordinate mapPos) {
        super(engine, mapPos, new Rectangle(0, 0, 24, 32), 2, 3);

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
    }

    private void move() {
        if (++stepCounter == stepDuration) {
            this.hitBox.update(mapPos, solidArea);
            isMoving = false;
            for (int i = 0; i < Direction.TOTAL.ordinal(); i++) {
                if (engine.keyPressed[i]) {
                    stepDirection = Direction.values()[i];
                    isMoving = true;
                    if (!isCollided(engine.blocks)) {
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

    private void bomb() {
        if (engine.keyPressed[KeyCode.X]) {
            if (isCoolingDown) {
                if (engine.elapsedTime - bombTime > coolDownDuration) {
                    isCoolingDown = false;
                }
            } else{
                for (Bomb bomb : engine.bombs) {
                    if (!bomb.isExist()) {
                        Coordinate bomberUnitPos = this.mapPos.add(12, 16).divide(Sprite.SCALED_SIZE);
                        Grass grass = (Grass) engine.blocks.get(BombermanGame.COLUMNS * (bomberUnitPos.y) + (bomberUnitPos.x));
                        if (!grass.hasOverlay()) {
                            bomb.create(grass.getMapPos(), grass.getScreenPos());
                            grass.addLayer(bomb);
                            isCoolingDown = true;
                            bombTime = engine.elapsedTime;
                        }
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void updateScreenPos() {
        if (mapPos.x < MIDDLE.x) {
            screenPos.x = mapPos.x;
        } else if ((BombermanGame.COLUMNS - 1) * Sprite.SCALED_SIZE - mapPos.x < MIDDLE.x) {
            screenPos.x = BombermanGame.WIDTH - BombermanGame.COLUMNS * Sprite.SCALED_SIZE + mapPos.x;
        } else {
            screenPos.x = MIDDLE.x;
        }
        if (mapPos.y < MIDDLE.y) {
            screenPos.y = mapPos.y;
        } else if ((BombermanGame.ROWS - 1) * Sprite.SCALED_SIZE - mapPos.y < MIDDLE.y) {
            screenPos.y = BombermanGame.HEIGHT - BombermanGame.ROWS * Sprite.SCALED_SIZE + mapPos.y;
        } else {
            screenPos.y = MIDDLE.y;
        }
    }

    @Override
    public void update() {
        if (isLiving) {
            move();
            bomb();
        } else if (engine.elapsedTime - deadTime > deadDuration) {
            isExist = false;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (isLiving) {
            if (isMoving) {
                gc.drawImage(getFrame(movingFrame[stepDirection.ordinal()], engine.elapsedTime,
                        frameDuration[FrameType.MOVING.ordinal()]), screenPos.x, screenPos.y);
            } else {
                gc.drawImage(defaultFrame[stepDirection.ordinal()], screenPos.x, screenPos.y);
            }
        } else {
            gc.drawImage(getFrame(dyingFrame, engine.elapsedTime,
                    frameDuration[FrameType.DYING.ordinal()]), screenPos.x, screenPos.y);
        }
    }
}
