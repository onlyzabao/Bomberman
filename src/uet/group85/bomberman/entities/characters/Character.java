package uet.group85.bomberman.entities.characters;

import javafx.scene.image.Image;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.entities.Entity;

public abstract class Character extends Entity {
    public enum State {
        ALIVE, DYING, DEAD
    }

    protected int stepLength;
    protected int stepDuration;
    protected int stepCount;
    protected Coordinate stepDirection;
    protected State state;

    public Character(Coordinate pos, Image img, State state, int stepLength, int stepDuration) {
        super(pos, img);
        this.state = state;
        this.stepLength = stepLength;
        this.stepDuration = stepDuration;
        this.stepCount = 0;
        this.stepDirection = new Coordinate(0, 0);
    }

    public int getStepLength() {
        return stepLength;
    }

    public void setStepLength(int stepLength) {
        this.stepLength = stepLength;
    }

    public int getStepDuration() {
        return stepDuration;
    }

    public void setStepDuration(int stepDuration) {
        this.stepDuration = stepDuration;
    }

    public Coordinate getStepDirection() {
        return stepDirection;
    }

    public void setStepDirection(Coordinate stepDirection) {
        this.stepDirection = stepDirection;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
