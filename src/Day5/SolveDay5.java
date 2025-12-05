package Day5;

import Day2.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SolveDay5 {
    private static final Scanner in = new Scanner(System.in);
    private static final List<Pair> ranges = new ArrayList<>();
    private static final List<Long> items = new ArrayList<>();

    public static void main(String[] args) {
        readInput();
        long ans1 = part1();
        long ans2 = part2();
        System.out.println(ans1 +  " " + ans2);
    }

    private static void readInput() {
        String input;
        ArrayList<Pair> temp = new ArrayList<>();
        while(in.hasNextLine()) {
            input = in.nextLine().trim();
            if(input.isEmpty())
                break;
            String[] range = input.split("-");
            if(range.length != 2)
                break;
            Pair pair = new Pair(range[0], range[1]);
            temp.add(pair);
        }

        while(in.hasNextLine()) {
            input = in.nextLine().trim();
            if(input.equals("end"))
                break;
            long item =  Long.parseLong(input);
            items.add(item);
        }

        temp.sort((a, b) -> {
            if (a.l != b.l) return Long.compare(a.l, b.l);
            return Long.compare(a.r, b.r);
        });
        items.sort(Long::compareTo);

        long l =  temp.getFirst().l;
        long r = temp.getFirst().r;
        for(Pair pair : temp) {
            if(pair.l > r) {
                ranges.add(new Pair(l, r));
                l = pair.l;
            }
            r = Math.max(r, pair.r);
        }
        ranges.add(new  Pair(l, r));
    }

    private static long part1() {
        long ans = 0;
        int ind = 0;
        for(long item: items) {
            while(ind < ranges.size() && item > ranges.get(ind).r)
                ind++;
            if(ind < ranges.size() && item >= ranges.get(ind).l && item <= ranges.get(ind).r)
                ans++;
        }
        return ans;
    }

    private static long part2() {
        long ans = 0;
        for(Pair pair: ranges)
            ans += pair.r - pair.l + 1;

        return ans;
    }
}
