package uet.group85.bomberman.auxilities;

import uet.group85.bomberman.entities.Entity;

public class Bound {
    public int topY;
    public int bottomY;
    public int leftX;
    public int rightX;

    public Bound(Entity entity) {
        this.update(entity);
    }

    public void update(Entity entity) {
        this.topY = entity.getMapPos().y + entity.getSolidArea().y;
        this.bottomY = entity.getMapPos().y + entity.getSolidArea().y + entity.getSolidArea().h;
        this.leftX = entity.getMapPos().x + entity.getSolidArea().x;
        this.rightX = entity.getMapPos().x + entity.getSolidArea().x + entity.getSolidArea().w;
    }
}
