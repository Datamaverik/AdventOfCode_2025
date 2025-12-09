package Day9;

import Utils.Pair;

import java.util.ArrayList;
import java.util.Scanner;

public class SolveDay9 {
    private static final Scanner  in = new Scanner(System.in);
    private static final ArrayList<Pair> tiles = new ArrayList<>();

    public static void main(String[] args){
        readInput();
        long ans1 = part1();
        long ans2 = part2();
        System.out.println(ans1 + " " + ans2);
    }

    private static void readInput(){
        String input = in.nextLine();
        while(!input.equals("end")) {
            String[] coordinate = input.split(",");
            long x =  Long.parseLong(coordinate[0]);
            long y =  Long.parseLong(coordinate[1]);
            tiles.add(new Pair(x, y));
            input = in.nextLine();
        }
    }

    private static int orientation(Pair a, Pair b, Pair c) {
        long val = (b.l - a.l) * (c.r - a.r)
                - (b.r - a.r) * (c.l - a.l);

        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
    }

    private static boolean onSegment(Pair a, Pair b, Pair c) {
        return b.l >= Math.min(a.l, c.l) && b.l <= Math.max(a.l, c.l)
                && b.r >= Math.min(a.r, c.r) && b.r <= Math.max(a.r, c.r);
    }

    private static boolean segmentsIntersect(Pair a, Pair b, Pair c, Pair d) {
        int o1 = orientation(a, b, c);
        int o2 = orientation(a, b, d);
        int o3 = orientation(c, d, a);
        int o4 = orientation(c, d, b);

        if (o1 != o2 && o3 != o4)
            return true;

        if (o1 == 0 && onSegment(a, c, b)) return true;
        if (o2 == 0 && onSegment(a, d, b)) return true;
        if (o3 == 0 && onSegment(c, a, d)) return true;
        return o4 == 0 && onSegment(c, b, d);
    }

    private static boolean samePoint(Pair a, Pair b) {
        return a.l == b.l && a.r == b.r;
    }

    private static boolean lineIntersect(Pair a, Pair b) {
        int n = tiles.size();
        for (int i = 0; i < n; i++) {
            Pair p = tiles.get(i);
            Pair q = tiles.get((i + 1) % n);

            //  check if a-b intersects with p-q
            if (!segmentsIntersect(a, b, p, q)) continue;
            //  check if point 'a' or 'b' is on the ends side of p-q
            if (samePoint(a, p) || samePoint(a, q) || samePoint(b, p) || samePoint(b, q))
                continue;
            //  check if point 'a' or 'b' is on the side p-q
            if (onSegment(p, a, q) || onSegment(p, b, q))
                continue;
            if (onSegment(a, p, b) || onSegment(a, q, b))
                continue;
            return true;
        }
        return false;
    }

    private static boolean isPointInsidePolygon(Pair p) {
        int n = tiles.size();
        boolean inside = false;
        for (int i = 0; i < n; i++) {
            Pair a = tiles.get(i);
            Pair b = tiles.get((i + 1) % n);

            //  check if point 'p' is same as vertex, or it lies on the side of polygon
            if (samePoint(p, a) || samePoint(p, b))
                return true;
            if (orientation(a, b, p) == 0 && onSegment(a, p, b))
                return true;

            if (((a.r > p.r) != (b.r > p.r))) {
                double xAtY = a.l + (double)(b.l - a.l) * (p.r - a.r) / (double)(b.r - a.r);
                if (xAtY > p.l)
                    inside = !inside;
            }
        }

        return inside;
    }

    private static boolean isRectangleInside(ArrayList<Pair> rectangle) {
        for (Pair p : rectangle) {
            if (!isPointInsidePolygon(p))
                return false;
        }

        for (int j = 0; j < rectangle.size(); j++) {
            Pair a = rectangle.get(j);
            Pair b = rectangle.get((j + 1) % rectangle.size());
            if(lineIntersect(a, b))
                return false;
        }

        return true;
    }

    private static long part1() {
        long ans = 0L;
        for(int i = 0; i < tiles.size(); i++) {
            for(int j = i + 1; j < tiles.size(); j++) {
                long x1 = tiles.get(i).l;
                long y1 = tiles.get(i).r;

                long x2 = tiles.get(j).l;
                long y2 = tiles.get(j).r;

                ans = Math.max(ans, (Math.abs(x2 - x1) + 1L) * (Math.abs(y2 - y1) + 1L));
            }
        }
        return ans;
    }

    private static long part2() {
        long ans = 0L;
        for(int i = 0; i < tiles.size(); i++) {
            for(int j = 0; j < tiles.size(); j++) {
                if(i == j)
                    continue;
                long x1 = tiles.get(i).l;
                long y1 = tiles.get(i).r;

                long x2 = tiles.get(j).l;
                long y2 = tiles.get(j).r;

                ArrayList<Pair> rectangle = new ArrayList<>();
                rectangle.add(new Pair(x1, y1));
                rectangle.add(new Pair(x1, y2));
                rectangle.add(new Pair(x2, y2));
                rectangle.add(new Pair(x2, y1));

                boolean okay = isRectangleInside(rectangle);
                if(okay)
                    ans = Math.max(ans, (Math.abs(x2 - x1) + 1L) * (Math.abs(y2 - y1) + 1L));
            }
        }
        return ans;
    }
}
