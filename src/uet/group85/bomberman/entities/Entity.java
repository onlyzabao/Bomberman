package uet.group85.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import uet.group85.bomberman.auxilities.Border;
import uet.group85.bomberman.auxilities.Coordinate;
import uet.group85.bomberman.auxilities.Rectangle;
import uet.group85.bomberman.managers.ScreenManager;

public abstract class Entity {
    protected Coordinate mapPos;
    protected Coordinate screenPos;
    protected final Rectangle solidArea;
    protected Border hitBox;
    protected boolean isExist;

    protected Entity(Coordinate mapPos, Coordinate screenPos, Rectangle solidArea) {
        this.mapPos = mapPos;
        this.screenPos = screenPos;
        this.solidArea = solidArea;
        this.hitBox = new Border(mapPos, solidArea);
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

    public boolean isVisible() {
        if (screenPos.x < -32 || screenPos.x > ScreenManager.WIDTH) {
            return false;
        }
        if (screenPos.y < -32 || screenPos.y > ScreenManager.HEIGHT) {
            return false;
        }
        return true;
    }

    public Border getHitBox() {
        return hitBox;
    }

    public boolean isExist() {
        return isExist;
    }

    public abstract void update();
    public abstract void render(GraphicsContext gc);
}
