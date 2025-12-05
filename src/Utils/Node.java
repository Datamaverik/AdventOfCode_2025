package Utils;

public class Node {
    public int i;
    public int j;
    public int count;

    Node(int i, int j, int count) {
        this.i = i;
        this.j = j;
        this.count = count;
    }

    @Override
    public int hashCode() {
        return 140 * i + j;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node other)) return false;
        return this.i == other.i && this.j == other.j;
    }
}
