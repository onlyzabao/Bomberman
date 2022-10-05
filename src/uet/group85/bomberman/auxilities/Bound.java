package uet.group85.bomberman.auxilities;

import uet.group85.bomberman.entities.Entity;

public class Bound {
    public int topY;
    public int bottomY;
    public int leftX;
    public int rightX;

    public Bound() {
        this.topY = 0;
        this.bottomY = 0;
        this.leftX = 0;
        this.rightX = 0;
    }

    public Bound(Entity entity) {
        this.update(entity);
    }

    public void update(Entity entity) {
        this.topY = entity.getPos().y + entity.getSolidArea().y;
        this.bottomY = entity.getPos().y + entity.getSolidArea().y + entity.getSolidArea().h;
        this.leftX = entity.getPos().x + entity.getSolidArea().x;
        this.rightX = entity.getPos().x + entity.getSolidArea().x + entity.getSolidArea().w;
    }
}
