package uet.group85.bomberman.entities.characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.auxilities.Border;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.blocks.Block;
import uet.group85.bomberman.entities.blocks.Brick;
import uet.group85.bomberman.entities.tiles.Grass;
import uet.group85.bomberman.entities.blocks.Bomb;
import uet.group85.bomberman.entities.tiles.Tile;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;
import uet.group85.bomberman.managers.ScreenManager;
import uet.group85.bomberman.graphics.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Character {

    public static final Coordinate screenMid = new Coordinate(
            ((ScreenManager.WIDTH - GameScreen.TRANSLATED_X) - Sprite.SCALED_SIZE) / 2,
            ((ScreenManager.HEIGHT - GameScreen.TRANSLATED_Y) - Sprite.SCALED_SIZE) / 2
    );
    public static final List<Bomb> bombs = new ArrayList<>();
    private final double COOLDOWN_PERIOD = 0.25;
    private boolean isCoolingDown;
    private double bombTime;
    private boolean isMoving;
    private boolean canPassBrick;
    private boolean canPassBomb;
    protected Tile obstacle1;
    protected Tile obstacle2;

    public Bomber() {
        super(new Coordinate(0, 0), new Coordinate(0, 0),
                new Rectangle(0, 0, (Sprite.SCALED_SIZE * 3) / 4, Sprite.SCALED_SIZE), 2, 3, true);

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

        isCoolingDown = false;
        isMoving = false;
        canPassBrick = false;
        canPassBomb = false;
        obstacle1 = null;
        obstacle2 = null;

        bombs.add(new Bomb(1));
    }

    private boolean isCollided(Tile other) {
        Border otherHitBox = other.getHitBox();
        // Check top side
        if ((hitBox.topY < otherHitBox.bottomY && hitBox.topY > otherHitBox.topY)
                && !((hitBox.leftX >= otherHitBox.rightX) || (hitBox.rightX <= otherHitBox.leftX))) {
            return true;
        }
        // Check bottom side
        if ((hitBox.bottomY > otherHitBox.topY && hitBox.bottomY < otherHitBox.bottomY)
                && !((hitBox.leftX >= otherHitBox.rightX) || (hitBox.rightX <= otherHitBox.leftX))) {
            return true;
        }
        // Check left side
        if ((hitBox.leftX < otherHitBox.rightX && hitBox.leftX > otherHitBox.leftX)
                && !((hitBox.topY >= otherHitBox.bottomY) || (hitBox.bottomY <= otherHitBox.topY))) {
            return true;
        }
        // Check right side
        if ((hitBox.rightX > otherHitBox.leftX && hitBox.rightX < otherHitBox.rightX)
                && !((hitBox.topY >= otherHitBox.bottomY) || (hitBox.bottomY <= otherHitBox.topY))) {
            return true;
        }
        return false;
    }

    private boolean isCollided() {
        this.hitBox.update(mapPos, solidArea);
        // Detect obstacles
        switch (stepDirection) {
            case UP -> {
                hitBox.topY -= stepLength;
                obstacle1 = GameManager.tiles.get(GameManager.mapCols * (hitBox.topY / Sprite.SCALED_SIZE) // Row size multiply Row index
                        + (hitBox.leftX / Sprite.SCALED_SIZE)); // add Column index -> One dimension index
                obstacle2 = GameManager.tiles.get(GameManager.mapCols * (hitBox.topY / Sprite.SCALED_SIZE)
                        + (hitBox.rightX / Sprite.SCALED_SIZE));
            }
            case DOWN -> {
                hitBox.bottomY += stepLength;
                obstacle1 = GameManager.tiles.get(GameManager.mapCols * (hitBox.bottomY / Sprite.SCALED_SIZE)
                        + (hitBox.leftX / Sprite.SCALED_SIZE));
                obstacle2 = GameManager.tiles.get(GameManager.mapCols * (hitBox.bottomY / Sprite.SCALED_SIZE)
                        + (hitBox.rightX / Sprite.SCALED_SIZE));
            }
            case LEFT -> {
                hitBox.leftX -= stepLength;
                obstacle1 = GameManager.tiles.get(GameManager.mapCols * (hitBox.topY / Sprite.SCALED_SIZE)
                        + (hitBox.leftX / Sprite.SCALED_SIZE));
                obstacle2 = GameManager.tiles.get(GameManager.mapCols * (hitBox.bottomY / Sprite.SCALED_SIZE)
                        + (hitBox.leftX / Sprite.SCALED_SIZE));
            }
            case RIGHT -> {
                hitBox.rightX += stepLength;
                obstacle1 = GameManager.tiles.get(GameManager.mapCols * (hitBox.topY / Sprite.SCALED_SIZE)
                        + (hitBox.rightX / Sprite.SCALED_SIZE));
                obstacle2 = GameManager.tiles.get(GameManager.mapCols * (hitBox.bottomY / Sprite.SCALED_SIZE)
                        + (hitBox.rightX / Sprite.SCALED_SIZE));
            }
        }
        assert obstacle1 != null;
        assert obstacle2 != null;

        boolean isBlocked1 = isCollided(obstacle1) && (!obstacle1.isPassable());
        boolean isBlocked2 = isCollided(obstacle2) && (!obstacle2.isPassable());

        if (obstacle1 instanceof Grass) {
            if (((Grass) obstacle1).hasOverlay()) {
                Block layer = ((Grass) obstacle1).getLayer();
                if (layer instanceof Bomb) {
                    isBlocked1 = isBlocked1 && !canPassBomb;
                } else if (layer instanceof Brick) {
                    isBlocked1 = isBlocked1 && !canPassBrick;
                }
            }
        }
        if (obstacle2 instanceof Grass) {
            if (((Grass) obstacle2).hasOverlay()) {
                Block layer = ((Grass) obstacle2).getLayer();
                if (layer instanceof Bomb) {
                    isBlocked2 = isBlocked2 && !canPassBomb;
                } else if (layer instanceof Brick) {
                    isBlocked2 = isBlocked2 && !canPassBrick;
                }
            }
        }

        return isBlocked1 || isBlocked2;
    }

    private void updateMapPos() {
        if (++stepCounter == stepDuration) {
            Coordinate unitPos = mapPos.add(12, 16).divide(Sprite.SCALED_SIZE);
            Grass belowGrass = (Grass) GameManager.tiles.get(GameManager.mapCols * (unitPos.y) + (unitPos.x));
            canPassBomb = belowGrass.hasOverlay();
            isMoving = false;
            for (int i = Direction.UP.ordinal(); i <= Direction.RIGHT.ordinal(); i++) {
                if (GameManager.events[i]) {
                    stepDirection = Direction.values()[i];
                    isMoving = true;
                    if (!isCollided()) {
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

    private void updateScreenPos() {
        if (mapPos.x < screenMid.x) {
            screenPos.x = mapPos.x + GameScreen.TRANSLATED_X;
        } else if ((GameManager.mapCols - 1) * Sprite.SCALED_SIZE - mapPos.x < screenMid.x) {
            screenPos.x = ScreenManager.WIDTH - GameManager.mapCols * Sprite.SCALED_SIZE + mapPos.x;
        } else {
            screenPos.x = screenMid.x + GameScreen.TRANSLATED_X;
        }

        if (mapPos.y < screenMid.y) {
            screenPos.y = mapPos.y + GameScreen.TRANSLATED_Y;
        } else if ((GameManager.mapRows - 1) * Sprite.SCALED_SIZE - mapPos.y < screenMid.y) {
            screenPos.y = ScreenManager.HEIGHT - GameManager.mapRows * Sprite.SCALED_SIZE + mapPos.y;
        } else {
            screenPos.y = screenMid.y + GameScreen.TRANSLATED_Y;
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
                if (GameManager.elapsedTime - bombTime > COOLDOWN_PERIOD) {
                    isCoolingDown = false;
                }
            } else{
                for (Bomb bomb : bombs) {
                    if (!bomb.isExist()) {
                        Coordinate bomberUnitPos = this.mapPos.add(12, 16).divide(Sprite.SCALED_SIZE);
                        Grass grass = (Grass) GameManager.tiles.get(GameManager.mapCols * (bomberUnitPos.y) + (bomberUnitPos.x));
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

    public void increaseSpeed() {
        this.stepLength += 2;
        this.stepDuration += 2;
    }

    public void increaseBomb() {
        bombs.add(new Bomb(bombs.get(0).getFlameLen()));
    }

    public void increaseFlame() {
        bombs.forEach(Bomb::increaseFlameLen);
    }

    @Override
    public void update() {
        if (!isExist) {
            if (GameManager.elapsedTime - deadTime > DYING_PERIOD + 2.0) {
                GameManager.status = GameManager.Status.LOSE;
            }
        } else if (!isDying) {
            updateMapPos();
            updateScreenPos();
            updateBomb();
        } else if (GameManager.elapsedTime - deadTime > DYING_PERIOD) {
            isExist = false;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isExist) {
            return;
        }
        if (!isDying) {
            if (isMoving) {
                gc.drawImage(getFrame(movingFrame[stepDirection.ordinal()], GameManager.elapsedTime, FrameType.MOVING),
                        screenPos.x, screenPos.y);
            } else {
                gc.drawImage(defaultFrame[stepDirection.ordinal()], screenPos.x, screenPos.y);
            }
        } else {
            gc.drawImage(getFrame(dyingFrame, GameManager.elapsedTime, FrameType.DYING), screenPos.x, screenPos.y);
        }
    }
}
