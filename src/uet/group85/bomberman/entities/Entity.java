package uet.group85.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Bound;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.graphics.Sprite;

public abstract class Entity {
    protected final BombermanGame engine;
    // Position
    protected Coordinate mapPos;
    protected Coordinate screenPos;
    // Solid area
    protected final Rectangle solidArea;
    // Hit box
    protected Bound hitBox;
    protected boolean isExist;

    public Entity(BombermanGame engine, Coordinate mapPos, Rectangle solidArea) {
        this.engine = engine;
        this.mapPos = mapPos;
        this.screenPos = new Coordinate(mapPos);
        this.solidArea = solidArea;
        this.hitBox = new Bound(mapPos, solidArea);
        this.isExist = true;
    }

    public Coordinate getMapPos() {
        return mapPos;
    }

    public Coordinate getScreenPos() {
        return screenPos;
    }

    public void updateScreenPos() {
        this.screenPos.x = mapPos.x - engine.bomberman.mapPos.x + engine.bomberman.screenPos.x;
        this.screenPos.y = mapPos.y - engine.bomberman.mapPos.y + engine.bomberman.screenPos.y;
    }

    public boolean isOutOfScreen() {
        if (screenPos.x < -32 || screenPos.x > BombermanGame.WIDTH) {
            return true;
        }
        if (screenPos.y < -32 || screenPos.y > BombermanGame.HEIGHT) {
            return true;
        }
        return false;
    }

    public Bound getHitBox() {
        return hitBox;
    }

    public boolean isExist() {
        return isExist;
    }

    public abstract void update();
    public abstract void render(GraphicsContext gc);
}
