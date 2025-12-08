package Day8;

import Utils.DisjointSetUnion;
import Utils.Pair;
import Utils.Point3D;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SolveDay8 {
    private static final Scanner in = new Scanner(System.in);
    private static final int limit = 1000;
    private static final ArrayList<Point3D> points = new ArrayList<>();
    static Map<Double, Pair> sortedPoints;
    private static DisjointSetUnion dsu;

    public static void main(String[] args) {
        readInput();
        processInput();
        dsu = new DisjointSetUnion(points.size());
        long ans1 = part1();
        dsu = new DisjointSetUnion(points.size());
        long ans2 = part2();
        System.out.println(ans1 + " " + ans2);
    }

    private static void readInput() {
        String input = in.nextLine();
        while(!input.equals("end")) {
            String[] coordinate = input.split(",");
            int x =  Integer.parseInt(coordinate[0]);
            int y =  Integer.parseInt(coordinate[1]);
            int z =  Integer.parseInt(coordinate[2]);
            points.add(new Point3D(x, y, z));
            input = in.nextLine();
        }
    }

    public static void processInput() {
        sortedPoints = new TreeMap<>();

        for(int i = 0; i < points.size(); i++) {
            for(int j = i + 1; j < points.size(); j++) {
                double distance = points.get(i).distance(points.get(j));
                sortedPoints.put(distance, new Pair(i, j));
            }
        }
    }

    public static long part1() {
        AtomicInteger count = new AtomicInteger();
        sortedPoints.forEach((distance, p) -> {
            if(count.get() < limit) {
                int u = (int)p.l;
                int v = (int)p.r;
                int rootU = dsu.findParent(u);
                int rootV = dsu.findParent(v);
                count.getAndIncrement();

                if(rootV != rootU)
                    dsu.unionBySize(u, v);
            }
        });

        long ans = 1;
        var temp = new ArrayList<>(dsu.size);
        temp.sort(Comparator.naturalOrder());

        for(int i = temp.size() - 1; i >= temp.size() - 3; i--)
            ans *= temp.get(i);


        return ans;
    }

    public static long part2() {
        AtomicInteger numOfComponents = new AtomicInteger(points.size());
        AtomicInteger count = new AtomicInteger();
        AtomicLong ans = new AtomicLong();
        sortedPoints.forEach((distance, p) -> {
            if(numOfComponents.get() > 1) {
                int u = (int)p.l;
                int v = (int)p.r;
                int rootU = dsu.findParent(u);
                int rootV = dsu.findParent(v);
                count.getAndIncrement();

                if(rootV != rootU) {
                    dsu.unionBySize(u, v);
                    numOfComponents.getAndDecrement();
                }

                if(numOfComponents.get() == 1)
                    ans.set((long) points.get(u).x * points.get(v).x);
            }
        });

        return ans.get();
    }
}
