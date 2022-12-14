package uet.group85.bomberman.uitilities;

import uet.group85.bomberman.graphics.Sprite;

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
        return new Coordinate(this.x + other.x, this.y + other.y);
    }

    public Coordinate add(int x, int y) {
        return new Coordinate(this.x + x, this.y + y);
    }

    public Coordinate multiply(int k) {
        return new Coordinate(this.x * k, this.y * k);
    }

    public Coordinate divide(int k) throws RuntimeException {
        if (k == 0) {
            throw new RuntimeException("Divided by zero!");
        } else {
            return new Coordinate(this.x / k, this.y / k);
        }
    }

    public boolean equals(Coordinate other) {
        return (this.x == other.x && this.y == other.y);
    }

    public int manhattanDist(Coordinate other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }
}
