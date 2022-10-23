package uet.group85.bomberman.entities.characters;

import javafx.scene.image.Image;

import uet.group85.bomberman.auxiliaries.Border;
import uet.group85.bomberman.auxiliaries.Coordinate;
import uet.group85.bomberman.auxiliaries.Rectangle;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.entities.blocks.Block;
import uet.group85.bomberman.entities.blocks.Bomb;
import uet.group85.bomberman.entities.blocks.Brick;
import uet.group85.bomberman.entities.tiles.Grass;
import uet.group85.bomberman.entities.tiles.Tile;
import uet.group85.bomberman.graphics.Sprite;

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
    protected boolean[] passableDirection;
    protected Image[] defaultFrame;
    protected Image[][] movingFrame;
    protected Image[] dyingFrame;
    protected double[] frameDuration;
    protected boolean isExist;
    protected boolean canPassBrick;
    protected boolean canPassBomb;

    public Character(Coordinate mapPos, Coordinate screenPos,
                     Rectangle solidArea,
                     int stepLength, int stepDuration, boolean isExist) {
        super(mapPos, screenPos, solidArea);

        this.stepLength = stepLength;
        this.stepDuration = stepDuration;
        this.stepCounter = 0;
        this.stepDirection = Direction.DOWN;

        frameDuration = new double[]{0.2, 0.3, 1.0};

        passableDirection = new boolean[Direction.TOTAL.ordinal()];
        isDying = false;

        this.isExist = isExist;
    }

    public boolean isCollided(Block other) {
        Coordinate unitPos = this.mapPos.add(solidArea.x + solidArea.w / 2,
                solidArea.y + solidArea.h / 2).divide(Sprite.SCALED_SIZE);
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

    public void checkDirection(List<List<Tile>> tiles) {
        Coordinate unitPos = mapPos.divide(Sprite.SCALED_SIZE);
        Tile[] tile = new Tile[]{
                tiles.get(unitPos.y - 1).get(unitPos.x),
                tiles.get(unitPos.y + 1).get(unitPos.x),
                tiles.get(unitPos.y).get(unitPos.x - 1),
                tiles.get(unitPos.y).get(unitPos.x + 1),
                tiles.get(unitPos.y).get(unitPos.x)
        };
        for (int i = 0; i < 5; i++) {
            // Check if direction are passable
            passableDirection[i] = tile[i].isPassable();
            if (tile[i] instanceof Grass) {
                if (((Grass) tile[i]).hasOverlay()) {
                    Block layer = ((Grass) tile[i]).getLayer();
                    if (layer instanceof Bomb) {
                        passableDirection[i] = passableDirection[i] || canPassBomb;
                    } else if (layer instanceof Brick) {
                        passableDirection[i] = passableDirection[i] || canPassBrick;
                    }
                }
            }
        }
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
