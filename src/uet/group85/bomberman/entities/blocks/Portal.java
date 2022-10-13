package uet.group85.bomberman.entities.blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.graphics.Sprite;

public class Portal extends Block {
    // Animation
    private final Image img;

    public Portal(BombermanGame engine, Coordinate pos) {
        super(engine, pos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE));
        isPassable = true;

        img = Sprite.portal.getFxImage();
    }

    @Override
    public void update() { // TODO: Refactor
        if (!engine.bomberman.isExist()) {
            System.out.println("Lose!");
        } else if (engine.enemies.size() == 0) {
            Coordinate bomberUnitPos = engine.bomberman.getMapPos().add(12, 16).divide(Sprite.SCALED_SIZE);
            if (bomberUnitPos.equals(mapPos.divide(Sprite.SCALED_SIZE))) {
                System.out.println("Win!");
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, mapPos.x, mapPos.y);
    }
}