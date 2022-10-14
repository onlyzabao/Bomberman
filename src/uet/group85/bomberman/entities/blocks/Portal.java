package uet.group85.bomberman.entities.blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;

public class Portal extends Block {
    // Animation
    private final Image img;

    public Portal(Coordinate mapPos, Coordinate screenPos) {
        super(mapPos, screenPos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE), true);

        img = Sprite.portal.getFxImage();
    }

    @Override
    public void update() { // TODO: Refactor
        if (!GameManager.bomber.isExist()) {
            System.out.println("Lose!");
        } else if (GameManager.enemies.size() == 0) {
            Coordinate bomberUnitPos = GameManager.bomber.getMapPos().add(12, 16).divide(Sprite.SCALED_SIZE);
            if (bomberUnitPos.equals(mapPos.divide(Sprite.SCALED_SIZE))) {
                System.out.println("Win!");
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, this.screenPos.x, this.screenPos.y);
    }
}