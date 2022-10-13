package uet.group85.bomberman.auxilities;

public class Bound {
    public int topY;
    public int bottomY;
    public int leftX;
    public int rightX;

    public Bound(Coordinate mapPos, Rectangle solidArea) {
        this.update(mapPos, solidArea);
    }

    public void update(Coordinate mapPos, Rectangle solidArea) {
        this.topY = mapPos.y + solidArea.y;
        this.bottomY = mapPos.y + solidArea.y + solidArea.h;
        this.leftX = mapPos.x + solidArea.x;
        this.rightX = mapPos.x + solidArea.x + solidArea.w;
    }
}
