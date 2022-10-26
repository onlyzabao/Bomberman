package uet.group85.bomberman.uitilities;

public class Node implements Comparable<Node> {
    public Coordinate parent;
    public int f;
    public int g;
    public int h;

    public Node(Coordinate parent, int f, int g, int h) {
        this.parent = parent;
        this.f = f;
        this.g = g;
        this.h = h;
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.f, o.f);
    }
}
