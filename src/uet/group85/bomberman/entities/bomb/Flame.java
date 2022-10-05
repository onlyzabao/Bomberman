package uet.group85.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.graphics.Sprite;

public class Flame extends Entity {
    private final Image[][] img;

    public Flame(Coordinate pos) {
        super(pos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE));

        img = new Image[][]{
                {
                        Sprite.bomb_exploded.getFxImage(),
                        Sprite.bomb_exploded1.getFxImage(),
                        Sprite.bomb_exploded2.getFxImage()
                },
                {
                        Sprite.explosion_horizontal.getFxImage(),
                        Sprite.explosion_horizontal1.getFxImage(),
                        Sprite.explosion_horizontal2.getFxImage()
                },
                {

                }

        };
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {

    }
}
