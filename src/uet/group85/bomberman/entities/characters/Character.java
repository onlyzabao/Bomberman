package uet.group85.bomberman.entities.characters;

import javafx.scene.image.Image;

import uet.group85.bomberman.auxilities.Border;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.entities.blocks.Block;
import uet.group85.bomberman.entities.tiles.Tile;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;

import java.util.List;

public abstract class Character extends Entity {
    public enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE, TOTAL
    }
    enum FrameType {
        MOVING, INJURED, DYING
    }
    protected final double DYING_PERIOD = 1.5;
    protected boolean isDying;
    protected double deadTime;
    protected int stepLength;
    protected int stepDuration;
    protected int stepCounter;
    protected Direction stepDirection;
    protected boolean[] isBlocked;
    protected Image[] defaultFrame;
    protected Image[][] movingFrame;
    protected Image[] dyingFrame;
    protected double[] frameDuration;

    protected boolean isExist;

    public Character(Coordinate mapPos, Coordinate screenPos,
                     Rectangle solidArea,
                     int stepLength, int stepDuration, boolean isExist) {
        super(mapPos, screenPos, solidArea);

        this.stepLength = stepLength;
        this.stepDuration = stepDuration;
        this.stepCounter = 0;
        this.stepDirection = Direction.DOWN;

        frameDuration = new double[]{0.2, 0.3, 1.0};

        isBlocked = new boolean[Direction.TOTAL.ordinal()];
        isDying = false;

        this.isExist = isExist;
    }

    public boolean isCollided(Block other) {
        Coordinate unitPos = this.mapPos.add(solidArea.w / 2, solidArea.h / 2).divide(Sprite.SCALED_SIZE);
        return unitPos.equals(other.getMapPos().divide(Sprite.SCALED_SIZE));
    }

    public boolean isCollided(Character other) {
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

    public void checkDirection(List<Tile> tiles) {
        Coordinate thisUnitPos = mapPos.divide(Sprite.SCALED_SIZE);

        isBlocked[Direction.UP.ordinal()] = !(tiles.get(GameManager.mapCols * (thisUnitPos.y - 1)
                + (thisUnitPos.x))).isPassable();

        isBlocked[Direction.DOWN.ordinal()] = !(tiles.get(GameManager.mapCols * (thisUnitPos.y + 1)
                + (thisUnitPos.x))).isPassable();

        isBlocked[Direction.LEFT.ordinal()] = !(tiles.get(GameManager.mapCols * (thisUnitPos.y)
                + (thisUnitPos.x - 1))).isPassable();

        isBlocked[Direction.RIGHT.ordinal()] = !(tiles.get(GameManager.mapCols * (thisUnitPos.y)
                + (thisUnitPos.x + 1))).isPassable();

        isBlocked[Direction.NONE.ordinal()] = !(tiles.get(GameManager.mapCols * (thisUnitPos.y)
                + (thisUnitPos.x))).isPassable();
    }

    public void eliminateNow(double deadTime) {
        if (!isDying) {
            this.deadTime = deadTime;
            isDying = true;
        }
    }

    protected Image getFrame(Image[] frame, double time, FrameType type) {
        int index = 0;
        switch (type) {
            case MOVING -> index = (int) ((time % (frame.length * frameDuration[type.ordinal()]))
                    / frameDuration[type.ordinal()]);
            case DYING -> index = (int) (((time - deadTime) % DYING_PERIOD) / frameDuration[type.ordinal()]);
        }
        return frame[index];
    }

    public boolean isExist() {
        return isExist;
    }
}
