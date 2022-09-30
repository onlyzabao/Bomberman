package uet.group85.bomberman.auxilities;

public class Coordinate {
    public int x;
    public int y;

    // Constructor
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Copy constructor
    public Coordinate(Coordinate other) {
        this.x = other.x;
        this.y = other.y;
    }

    // Add method
    public Coordinate add(Coordinate other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    // Multiply method
    public Coordinate multiply(int k) {
        this.x *= k;
        this.y *= k;
        return this;
    }
}
