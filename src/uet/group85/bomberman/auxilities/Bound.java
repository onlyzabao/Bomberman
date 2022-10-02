package uet.group85.bomberman.auxilities;

import uet.group85.bomberman.entities.Entity;

public class Bound {
    public int topY;
    public int bottomY;
    public int leftX;
    public int rightX;

    public Bound(int topY, int bottomY, int leftX, int rightX) {
        this.topY = topY;
        this.bottomY = bottomY;
        this.leftX = leftX;
        this.rightX = rightX;
    }

    public static Bound createBound(Entity entity) {
        return new Bound(entity.getPos().y + entity.getBox().y,
                        entity.getPos().y + entity.getBox().y + entity.getBox().h,
                        entity.getPos().x + entity.getBox().x,
                        entity.getPos().x + entity.getBox().x + entity.getBox().w);
    }
}
