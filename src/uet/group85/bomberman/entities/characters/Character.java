package uet.group85.bomberman.entities.characters;

import javafx.scene.image.Image;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.entities.Entity;

public abstract class Character extends Entity {
    public enum State {
        ALIVE, DYING, DEAD
    }

    protected int speed;
    protected Coordinate vel;
    protected State state;

    public Character(Coordinate pos, Image img, int speed, Coordinate vel, State state) {
        super(pos, img);
        this.speed = speed;
        this.vel = vel;
        this.state = state;
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
