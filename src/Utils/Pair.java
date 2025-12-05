package Utils;

public class Pair {
    public long l, r;
    public Pair(String l, String r) {
        this.l = Long.parseLong(l);
        this.r = Long.parseLong(r);
    }

    public Pair(long l, long r) {
        this.l = l;
        this.r = r;
    }
}
