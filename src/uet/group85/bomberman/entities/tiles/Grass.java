package uet.group85.bomberman.entities.tiles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.auxiliaries.Coordinate;
import uet.group85.bomberman.auxiliaries.Rectangle;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.entities.blocks.Block;
import uet.group85.bomberman.entities.blocks.Brick;
import uet.group85.bomberman.entities.blocks.Bomb;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.GameManager;

import java.util.ArrayList;
import java.util.List;

public class Grass extends Tile {
    // Animation
    private final Image img;
    // Specifications
    private final List<Block> overlay;

    public Grass(Coordinate mapPos, Coordinate screenPos) {
        super(mapPos, screenPos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE), true);
        img = Sprite.grass.getFxImage();
        overlay = new ArrayList<>();
    }

    public void addLayer(Block layer) {
        this.overlay.add(layer);
    }

    public Block getTopLayer() {
        return overlay.get(overlay.size() - 1);
    }

    public Block getBottomLayer() {
        return overlay.get(0);
    }

    public boolean hasOverlay() {
        return overlay.size() != 0;
    }

    @Override
    public void update() {
        this.screenPos.x = mapPos.x - GameManager.bomber.getMapPos().x + GameManager.bomber.getScreenPos().x;
        this.screenPos.y = mapPos.y - GameManager.bomber.getMapPos().y + GameManager.bomber.getScreenPos().y;
        if (hasOverlay()) {
            Entity topLayer = overlay.get(overlay.size() - 1);
            isPassable = !(topLayer instanceof Bomb || topLayer instanceof Brick);
            topLayer.update();
            overlay.removeIf(entity -> !entity.isExist());
        } else {
            isPassable = true;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, this.screenPos.x, this.screenPos.y);
        if (hasOverlay()) {
            overlay.get(overlay.size() - 1).render(gc);
        }
    }
}

