package uet.group85.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import uet.group85.bomberman.BombermanGame;
import uet.group85.bomberman.auxilities.Bound;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;

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

    protected Entity(BombermanGame engine,
                     Coordinate mapPos, Coordinate screenPos,
                     Rectangle solidArea) {
        this.engine = engine;
        this.mapPos = mapPos;
        this.screenPos = screenPos;
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

    public boolean isVisible() {
        if (screenPos.x < -32 || screenPos.x > BombermanGame.WIDTH) {
            return false;
        }
        if (screenPos.y < -32 || screenPos.y > BombermanGame.HEIGHT) {
            return false;
        }
        return true;
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
