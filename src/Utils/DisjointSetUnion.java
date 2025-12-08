package Utils;

import java.util.ArrayList;

public class DisjointSetUnion {
    public ArrayList<Integer> parent = new ArrayList<>();
    public ArrayList<Integer> size = new ArrayList<>();
    public static int n;

    public DisjointSetUnion(int n) {
        DisjointSetUnion.n = n;
        for(int i = 0; i <= n; i++) {
            parent.add(i);
            size.add(1);
        }
    }

    public int findParent(int node) {
        if(parent.get(node) == node)
            return node;
        int parentNode = findParent(parent.get(node));
        parent.set(node, parentNode);
        return parentNode;
    }

    public void unionBySize(int u, int v) {
        int uRoot = findParent(u);
        int vRoot = findParent(v);
        if(uRoot == vRoot)
            return;
        if(size.get(uRoot) < size.get(vRoot)) {
            parent.set(uRoot, vRoot);
            size.set(vRoot, size.get(uRoot) + size.get(vRoot));
        }
        else {
            parent.set(vRoot, uRoot);
            size.set(uRoot, size.get(vRoot) + size.get(uRoot));
        }
    }
}
