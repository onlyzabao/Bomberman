package uet.group85.bomberman.entities.blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.entities.Entity;
import uet.group85.bomberman.entities.bomb.Bomb;
import uet.group85.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Grass extends Block {
    // Animation
    private final Image img;
    // Specifications
    private final List<Entity> overlay;

    public Grass(BombermanGame engine, Coordinate mapPos, Coordinate screenPos) {
        super(engine, mapPos, screenPos, new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE),
                true);

        img = Sprite.grass.getFxImage();

        overlay = new ArrayList<>();
    }

    public void addLayer(Entity layer) {
        this.overlay.add(layer);
    }

    public Entity getLayer() {
        return overlay.get(overlay.size() - 1);
    }

    public boolean hasOverlay() {
        return overlay.size() != 0;
    }

    @Override
    public void update() {
        this.screenPos.x = mapPos.x - engine.bomberman.getMapPos().x + engine.bomberman.getScreenPos().x;
        this.screenPos.y = mapPos.y - engine.bomberman.getMapPos().y + engine.bomberman.getScreenPos().y;
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

