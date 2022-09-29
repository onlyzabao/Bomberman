package uet.group85.bomberman.entities.characters;

import javafx.scene.image.Image;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.entities.characters.Character;

public class Bomber extends Character {

    public Bomber(Coordinate pos, Image img, int speed, Coordinate vel, State state) {
        super(pos, img, speed, vel, state);
    }

    @Override
    public void update() {

    }
}
