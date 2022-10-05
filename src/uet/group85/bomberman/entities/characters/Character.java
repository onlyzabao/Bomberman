package uet.group85.bomberman.entities.characters;

import javafx.scene.image.Image;
import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Bound;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.entities.blocks.Block;
import uet.group85.bomberman.graphics.Sprite;

import java.util.List;

public abstract class Character extends Entity {
    public enum State {
        ALIVE, DYING, DEAD
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT, TOTAL
    }

    // Character state
    protected State state;

    // Character movement
    protected int stepLength;
    protected int stepDuration;
    protected int stepCounter;
    protected Direction stepDirection;
    // Character collision
    protected Bound hitBox;
    protected Block obstacle1;
    protected Block obstacle2;

    // Character sprite
    protected Image[] defaultFrame;
    protected Image[][] movingFrame;
    protected Image[] dyingFrame;
    protected double frameDuration;

    // Constructor
    public Character(Coordinate pos, Rectangle solidArea, int stepLength, int stepDuration) {
        super(pos, solidArea);
        this.state = State.ALIVE;

        this.stepLength = stepLength;
        this.stepDuration = stepDuration;
        this.stepCounter = 0;
        this.stepDirection = Direction.DOWN;

        this.hitBox = new Bound();
        obstacle1 = null;
        obstacle2 = null;

        this.frameDuration = 0.2;
    }

    public Image getFrame(Image[] frame, double time) {
        int index = (int) ((time % (frame.length * frameDuration)) / frameDuration);
        return frame[index];
    }

    public boolean isCollided(Entity other) {
        Bound otherHitBox = other.getHitBox();
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

    public boolean isCollided(List<Block> blocks) {
        this.hitBox.update(this);
        // Detect obstacles
        switch (stepDirection) {
            case UP -> {
                hitBox.topY -= stepLength;
                obstacle1 = blocks.get(BombermanGame.WIDTH * (hitBox.topY / Sprite.SCALED_SIZE) // Row size multiply Row index
                        + (hitBox.leftX / Sprite.SCALED_SIZE)); // add Column index -> One dimension index
                obstacle2 = blocks.get(BombermanGame.WIDTH * (hitBox.topY / Sprite.SCALED_SIZE)
                        + (hitBox.rightX / Sprite.SCALED_SIZE));
            }
            case DOWN -> {
                hitBox.bottomY += stepLength;
                obstacle1 = blocks.get(BombermanGame.WIDTH * (hitBox.bottomY / Sprite.SCALED_SIZE)
                        + (hitBox.leftX / Sprite.SCALED_SIZE));
                obstacle2 = blocks.get(BombermanGame.WIDTH * (hitBox.bottomY / Sprite.SCALED_SIZE)
                        + (hitBox.rightX / Sprite.SCALED_SIZE));
            }
            case LEFT -> {
                hitBox.leftX -= stepLength;
                obstacle1 = blocks.get(BombermanGame.WIDTH * (hitBox.topY / Sprite.SCALED_SIZE)
                        + (hitBox.leftX / Sprite.SCALED_SIZE));
                obstacle2 = blocks.get(BombermanGame.WIDTH * (hitBox.bottomY / Sprite.SCALED_SIZE)
                        + (hitBox.leftX / Sprite.SCALED_SIZE));
            }
            case RIGHT -> {
                hitBox.rightX += stepLength;
                obstacle1 = blocks.get(BombermanGame.WIDTH * (hitBox.topY / Sprite.SCALED_SIZE)
                        + (hitBox.rightX / Sprite.SCALED_SIZE));
                obstacle2 = blocks.get(BombermanGame.WIDTH * (hitBox.bottomY / Sprite.SCALED_SIZE)
                        + (hitBox.rightX / Sprite.SCALED_SIZE));
            }
        }
        assert obstacle1 != null;
        assert obstacle2 != null;
        return isCollided(obstacle1) && (!obstacle1.isPassable())
                || isCollided(obstacle2) && (!obstacle2.isPassable());
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
