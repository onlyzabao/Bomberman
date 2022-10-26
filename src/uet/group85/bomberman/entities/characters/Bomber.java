package uet.group85.bomberman.entities.characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.uitilities.Border;
import uet.group85.bomberman.uitilities.Coordinate;
import uet.group85.bomberman.uitilities.Rectangle;
import uet.group85.bomberman.entities.blocks.Block;
import uet.group85.bomberman.entities.blocks.Brick;
import uet.group85.bomberman.entities.tiles.Grass;
import uet.group85.bomberman.entities.blocks.Bomb;
import uet.group85.bomberman.entities.tiles.Tile;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;
import uet.group85.bomberman.managers.ScreenManager;
import uet.group85.bomberman.graphics.GameScreen;
import uet.group85.bomberman.managers.SoundManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bomber extends Character {
    // Screen center position
    public static final Coordinate screenMid = new Coordinate(
            ((ScreenManager.WIDTH - GameScreen.TRANSLATED_X) - Sprite.SCALED_SIZE) / 2,
            ((ScreenManager.HEIGHT - GameScreen.TRANSLATED_Y) - Sprite.SCALED_SIZE) / 2
    );
    // Manage bombs
    private final List<Bomb> bombs = new ArrayList<>();
    private final double COOLDOWN_PERIOD = 0.25;
    private boolean isCoolingDown;
    private boolean hasDetonator;
    private double bombTime;
    // Manage movement
    private boolean isMoving;
    private int bonusSpeed;
    private final Tile[] obstacle;
    private final boolean[] isBlocked;

    public Bomber(Map<String, Integer> data) {
        super(new Coordinate(0, 0), new Coordinate(0, 0),
                new Rectangle(0, 0, (Sprite.SCALED_SIZE * 3) / 4, Sprite.SCALED_SIZE),
                2, 3, true);
        // Init sprites
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
        // Setup specifications
        isCoolingDown = false;
        for (int i = 0; i < data.get("Bomb"); i++) {
            bombs.add(new Bomb(data.get("Flame")));
        }

        bonusSpeed = data.get("Speed") * 2;
        stepLength += bonusSpeed;
        stepDuration += bonusSpeed;

        canPassBomb = data.get("BombPass") != 0;

        canPassBrick = data.get("WallPass") != 0;

        hasDetonator = data.get("Detonator") != 0;

        isMoving = false;
        obstacle = new Tile[2];
        isBlocked = new boolean[2];
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
                obstacle[0] = GameManager.tiles.get(hitBox.topY / Sprite.SCALED_SIZE).get(hitBox.leftX / Sprite.SCALED_SIZE); // add Column index -> One dimension index
                obstacle[1] = GameManager.tiles.get(hitBox.topY / Sprite.SCALED_SIZE).get(hitBox.rightX / Sprite.SCALED_SIZE);
            }
            case DOWN -> {
                hitBox.bottomY += stepLength;
                obstacle[0] = GameManager.tiles.get(hitBox.bottomY / Sprite.SCALED_SIZE).get(hitBox.leftX / Sprite.SCALED_SIZE);
                obstacle[1] = GameManager.tiles.get(hitBox.bottomY / Sprite.SCALED_SIZE).get(hitBox.rightX / Sprite.SCALED_SIZE);
            }
            case LEFT -> {
                hitBox.leftX -= stepLength;
                obstacle[0] = GameManager.tiles.get(hitBox.topY / Sprite.SCALED_SIZE).get(hitBox.leftX / Sprite.SCALED_SIZE);
                obstacle[1] = GameManager.tiles.get(hitBox.bottomY / Sprite.SCALED_SIZE).get(hitBox.leftX / Sprite.SCALED_SIZE);
            }
            case RIGHT -> {
                hitBox.rightX += stepLength;
                obstacle[0] = GameManager.tiles.get(hitBox.topY / Sprite.SCALED_SIZE).get(hitBox.rightX / Sprite.SCALED_SIZE);
                obstacle[1] = GameManager.tiles.get(hitBox.bottomY / Sprite.SCALED_SIZE).get(hitBox.rightX / Sprite.SCALED_SIZE);
            }
        }
        Coordinate unitPos = mapPos.add(12, 16).divide(Sprite.SCALED_SIZE);
        for (int i = 0; i < 2; i++) {
            assert obstacle[i] != null;
            // Check if obstacles are passable
            isBlocked[i] = isCollided(obstacle[i]) && (!obstacle[i].isPassable());
            // Check if bomber have ability to pass obstacles
            if (obstacle[i] instanceof Grass) {
                if (((Grass) obstacle[i]).hasOverlay()) {
                    Block layer = ((Grass) obstacle[i]).getTopLayer();
                    if (layer instanceof Bomb) {
                        if (unitPos.equals(layer.getMapPos().divide(Sprite.SCALED_SIZE))) {
                            isBlocked[i] = false;
                        } else {
                            isBlocked[i] = isBlocked[i] && !canPassBomb;
                        }
                    } else if (layer instanceof Brick) {
                        isBlocked[i] = isBlocked[i] && !canPassBrick;
                    }
                }
            }
        }
        return isBlocked[0] || isBlocked[1];
    }

    private void updateMapPos() {
        if (++stepCounter == stepDuration) {
            isMoving = false;
            for (int i = Direction.UP.ordinal(); i <= Direction.RIGHT.ordinal(); i++) {
                if (GameManager.events[i]) {
                    stepDirection = Direction.values()[i];
                    isMoving = true;
                    if (!isCollided()) {
                        step();
                    } else {
                        if (isBlocked[0] ^ isBlocked[1]) {
                            autoStep();
                        }
                    }
                }
            }
            stepCounter = 0;
        }
    }

    private void updateScreenPos() {
        // Update x position to render on screen
        if (mapPos.x < screenMid.x) {
            screenPos.x = mapPos.x + GameScreen.TRANSLATED_X;
        } else if ((GameManager.mapCols - 1) * Sprite.SCALED_SIZE - mapPos.x < screenMid.x) {
            screenPos.x = ScreenManager.WIDTH - GameManager.mapCols * Sprite.SCALED_SIZE + mapPos.x;
        } else {
            screenPos.x = screenMid.x + GameScreen.TRANSLATED_X;
        }
        // Update y position to render on screen
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
            // Vertical check
            if ((hitBox.leftX + 12) / Sprite.SCALED_SIZE == obstacle[0].getMapPos().x / Sprite.SCALED_SIZE) {
                mapPos.x += (isBlocked[0] ? 0 : -1) * stepLength / 2;
            } else {
                mapPos.x += (isBlocked[1] ? 0 : 1) * stepLength / 2;
            }
        } else {
            // Horizontal check
            if ((hitBox.topY + 16) / Sprite.SCALED_SIZE == obstacle[0].getMapPos().y / Sprite.SCALED_SIZE) {
                mapPos.y += (isBlocked[0] ? 0 : -1) * stepLength / 2;
            } else {
                mapPos.y += (isBlocked[1] ? 0 : 1) * stepLength / 2;
            }
        }
    }

    private void updateBomb() {
        if (GameManager.events[GameScreen.BOMB]) {
            if (isCoolingDown) {
                if (GameManager.elapsedTime - bombTime > COOLDOWN_PERIOD) {
                    isCoolingDown = false;
                }
            } else {
                for (Bomb bomb : bombs) {
                    if (!bomb.isExist()) {
                        Coordinate bomberUnitPos = this.mapPos.add(12, 16).divide(Sprite.SCALED_SIZE);
                        Grass grass = (Grass) GameManager.tiles.get(bomberUnitPos.y).get(bomberUnitPos.x);
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
        bonusSpeed += 2;
        stepLength += bonusSpeed;
        stepDuration += bonusSpeed;
    }

    public void increaseBomb() {
        bombs.add(new Bomb(bombs.get(0).getFlameLen()));
    }

    public void increaseFlame() {
        bombs.forEach(Bomb::increaseFlameLen);
    }

    public void setCanPassBrick(boolean canPassBrick) {
        this.canPassBrick = canPassBrick;
    }

    public void setCanPassBomb(boolean canPassBomb) {
        this.canPassBomb = canPassBomb;
    }
    public void setHasDetonator(boolean hasDetonator) {
        this.hasDetonator = hasDetonator;
    }

    public int getNumOfBombs() {
        return bombs.size();
    }

    public int getFlameLen() {
        return bombs.get(0).getFlameLen();
    }

    public int getBonusSpeed() {
        return bonusSpeed / 2;
    }

    public boolean canPassBomb() {
        return canPassBomb;
    }

    public boolean canPassBrick() {
        return canPassBrick;
    }

    public boolean hasDetonator() {
        return hasDetonator;
    }

    @Override
    public void update() {
        if (!isLiving) {
            if (GameManager.elapsedTime - deadTime > DYING_PERIOD + 2.0) {
                GameManager.status = GameManager.Status.LOST;
            }
        } else {
            if (!isDying) {
                updateMapPos();
                updateScreenPos();
                updateBomb();
            } else if (GameManager.elapsedTime - deadTime > DYING_PERIOD) {
                isLiving = false;
            } else {
                SoundManager.playGameSound("Bomber_dead", true);
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isLiving) {
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