package uet.group85.bomberman.entities.characters;

import javafx.scene.image.Image;
import uet.group85.bomberman.auxilities.Bound;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.graphics.Sprite;

import javax.swing.*;

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

    // Character sprite
    protected Image[] defaultFrame;
    protected Image[][] movingFrame;
    protected Image[] dyingFrame;
    protected double frameDuration;

    // Constructor
    public Character(Coordinate pos, Rectangle box, int stepLength, int stepDuration) {
        super(pos, box);
        this.state = State.ALIVE;
        this.stepLength = stepLength;
        this.stepDuration = stepDuration;
        this.stepCounter = 0;
        this.stepDirection = Direction.DOWN;
        this.frameDuration = 0.2;
    }

    public Image getFrame(Image[] frame, double time) {
        int index = (int) ((time % (frame.length * frameDuration)) / frameDuration);
        return frame[index];
    }

    public boolean isCollided(Bound thisBound, Bound otherBound) {
        // Check top side
        if ((thisBound.topY < otherBound.bottomY && thisBound.topY > otherBound.topY)
                && !((thisBound.leftX >= otherBound.rightX) || (thisBound.rightX <= otherBound.leftX))) {
            return true;
        }
        // Check bottom side
        if ((thisBound.bottomY > otherBound.topY && thisBound.bottomY < otherBound.bottomY)
                && !((thisBound.leftX >= otherBound.rightX) || (thisBound.rightX <= otherBound.leftX))) {
            return true;
        }
        // Check left side
        if ((thisBound.leftX < otherBound.rightX && thisBound.leftX > otherBound.leftX)
                && !((thisBound.topY >= otherBound.bottomY) || (thisBound.bottomY <= otherBound.topY))) {
            return true;
        }
        // Check right side
        if ((thisBound.rightX > otherBound.leftX && thisBound.rightX < otherBound.rightX)
                && !((thisBound.topY >= otherBound.bottomY) || (thisBound.bottomY <= otherBound.topY))) {
            return true;
        }
        return false;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
