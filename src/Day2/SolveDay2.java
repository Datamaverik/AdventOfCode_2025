package Day2;

import java.util.Scanner;

public class SolveDay2 {
    private static final Scanner in = new Scanner(System.in);
    private static Pair[] pairs;

    public static void main(String[] args) {
        extractPairs();
        long ans1 = part1();
        long ans2 = part2();
        System.out.println(ans1 + " " + ans2);
    }

    private static String getInput() {
        return in.nextLine();
    }

    private static void extractPairs() {
        String input = getInput();
        String[] ranges = input.split(",");
        pairs = new Pair[ranges.length];
        int ind = 0;
        for(String range: ranges) {
            String[] split = range.split("-");
            var pair = new Pair(split[0], split[1]);
            pairs[ind++] = pair;
        }
    }

    public static long part1() {
        long count = 0;
        for(Pair pair: pairs) {
            for(long num = pair.l; num <= pair.r; num++) {
                String numStr = String.valueOf(num);
                if(numStr.length() % 2 == 1) continue;
                String num1 = numStr.substring(0, numStr.length() / 2);
                String num2 = numStr.substring(numStr.length() / 2);
                if(num1.equals(num2)) count += num;
            }
        }
        return count;
    }

    public static long part2() {
        long count = 0;
        for(Pair pair: pairs) {
            boolean ok = false;
            for(long num = pair.l; num <= pair.r; num++) {
                String numStr = String.valueOf(num);
                for(int repeatingLen = 1; repeatingLen <= numStr.length() / 2; repeatingLen++) {
                    if(numStr.length() % repeatingLen != 0) continue;

                    String temp = numStr.substring(0, repeatingLen);
                    StringBuilder num2 = new StringBuilder(temp);
                    for(int j = repeatingLen; j < numStr.length(); j += repeatingLen)
                        num2.append(temp);

                    if(numStr.contentEquals(num2)) {
                        count += num;
                        ok = true;
                        break;
                    }
                }
            }
        }
        return count;
    }
}
