package uet.group85.bomberman.auxilities;

public class Coordinate {
    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Coordinate add(Coordinate other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Coordinate multiply(int k) {
        this.x *= k;
        this.y *= k;
        return this;
    }
}
