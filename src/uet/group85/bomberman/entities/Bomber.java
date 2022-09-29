package uet.group85.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bomber extends Character {

    public Bomber(Coordinate pos, Image img) {
        super(pos, img, 2, new Coordinate(1,1));
    }

    @Override
    public void update() {

    }
}
