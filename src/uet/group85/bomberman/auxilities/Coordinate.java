package uet.group85.bomberman.auxilities;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate add(Coordinate other) {
        this.x += other.getX();
        this.y += other.getY();
        return this;
    }

    public Coordinate multiply(int k) {
        this.x *= k;
        this.y *= k;
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
