package uet.group85.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import uet.group85.bomberman.uitilities.Border;
import uet.group85.bomberman.uitilities.Coordinate;
import uet.group85.bomberman.uitilities.Rectangle;
import uet.group85.bomberman.graphics.Sprite;
import uet.group85.bomberman.managers.ScreenManager;
import uet.group85.bomberman.graphics.GameScreen;

public abstract class Entity {
    protected Coordinate mapPos;
    protected Coordinate screenPos;
    protected Rectangle solidArea;
    protected Border hitBox;

    protected Entity(Coordinate mapPos, Coordinate screenPos, Rectangle solidArea) {
        this.mapPos = mapPos;
        this.screenPos = screenPos;
        this.solidArea = solidArea;
        this.hitBox = new Border(mapPos, solidArea);
    }

    public Coordinate getMapPos() {
        return mapPos;
    }

    public Coordinate getScreenPos() {
        return screenPos;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public Border getHitBox() {
        return hitBox;
    }

    public void setMapPos(Coordinate mapPos) {
        this.mapPos = mapPos;
    }

    public void setScreenPos(Coordinate screenPos) {
        this.screenPos = screenPos;
    }

    public boolean isVisible() {
        return (screenPos.y >= -Sprite.SCALED_SIZE + GameScreen.TRANSLATED_Y && screenPos.y <= ScreenManager.HEIGHT)
                && (screenPos.x >= -Sprite.SCALED_SIZE + GameScreen.TRANSLATED_X && screenPos.x <= ScreenManager.WIDTH);
    }

    public abstract void update();

    public abstract void render(GraphicsContext gc);
}
