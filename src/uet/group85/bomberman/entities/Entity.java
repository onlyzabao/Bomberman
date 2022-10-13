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
        this.mapPos = mapPos.multiply(Sprite.SCALED_SIZE);
        this.screenPos = new Coordinate(0, 0);
        this.solidArea = solidArea;
        this.hitBox = new Bound(this);
        this.isExist = true;
    }

    public Coordinate getMapPos() {
        return mapPos;
    }

    public void setMapPos(Coordinate mapPos) {
        this.mapPos = mapPos;
    }

    public Coordinate getScreenPos() {
        return screenPos;
    }

    public void setScreenPos(Coordinate screenPos) {
        this.screenPos = screenPos;
    }

    public Rectangle getSolidArea() {
        return solidArea;
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
