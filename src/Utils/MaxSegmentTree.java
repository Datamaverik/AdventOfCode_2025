package Utils;

public class MaxSegmentTree {
    public long[] num;
    private final long[] seg;

    MaxSegmentTree(int size, long[] arr) {
        num = arr;
        seg = new long[4 * size];
    }

    public void build(int ind, int low, int high) {
        if(low == high) {
            seg[ind] = num[low];
            return;
        }

        int mid = low + (high - low) / 2;
        build(2 * ind + 1, low, mid);
        build(2 * ind + 2, mid + 1, high);
        seg[ind] = Math.max(seg[2 * ind + 1], seg[2 * ind + 2]);
    }

    public long query(int ind, int low, int high, int l, int r) {
        if(low >= l && high <= r)
            return seg[ind];
        if (high < l || low > r)
            return Long.MIN_VALUE;
        
        int mid =  low + (high - low) / 2;
        long left = query(2 * ind + 1, low, mid, l, r);
        long right = query(2 * ind + 2, mid + 1, high, l, r);
        return Math.max(left, right);
    }
}
