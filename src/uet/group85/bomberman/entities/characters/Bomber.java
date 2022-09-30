package uet.group85.bomberman.entities.characters;

import javafx.scene.image.Image;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.KeyCode;
import uet.group85.bomberman.entities.characters.Character;

public class Bomber extends Character {

    public Bomber(Coordinate pos, Image img, State state) {
        super(pos, img, state, 2, 3);
    }

    public void move(boolean[] keyPressed) {
        if (++stepCount == stepDuration) {
            stepDirection.setX((keyPressed[KeyCode.RIGHT] ? 1 : 0) - (keyPressed[KeyCode.LEFT] ? 1 : 0));
            stepDirection.setY((keyPressed[KeyCode.DOWN] ? 1 : 0) - (keyPressed[KeyCode.UP] ? 1 : 0));
            pos.add(stepDirection.multiply(stepLength));
            stepCount = 0;
        }
    }
    @Override
    public void update() {

    }
}
