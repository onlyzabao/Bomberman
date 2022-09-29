package uet.group85.bomberman.entities;

import javafx.scene.image.Image;

public abstract class Character extends Entity {
    protected enum State {
        ALIVE, DYING, DEAD
    }

    protected int speed;
    protected Coordinate vel;
    protected State state;

    public Character(Coordinate pos, Image img, int speed, Coordinate vel) {
        super(pos, img);
        this.speed = speed;
        this.vel = vel;
        this.state = State.ALIVE;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setVel(Coordinate vel) {
        this.vel = vel;
    }

    public Coordinate getVel() {
        return vel;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
