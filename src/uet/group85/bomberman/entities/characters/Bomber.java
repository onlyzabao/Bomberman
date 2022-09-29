package uet.group85.bomberman.entities.characters;

import javafx.scene.image.Image;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.KeyCode;
import uet.group85.bomberman.entities.characters.Character;

public class Bomber extends Character {

    public Bomber(Coordinate pos, Image img, int speed, Coordinate vel, State state) {
        super(pos, img, speed, vel, state);
    }

    public void move(boolean[] keyPressed) {
        vel.setX((keyPressed[KeyCode.RIGHT] ? 1 : 0) - (keyPressed[KeyCode.LEFT] ? 1 : 0));
        vel.setY((keyPressed[KeyCode.DOWN] ? 1 : 0) - (keyPressed[KeyCode.UP] ? 1 : 0));
        pos.add(vel.multiply(speed));
    }
    @Override
    public void update() {

    }
}
