package Day6;

import Utils.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

public class SolveDay6 {
    private static final Scanner in = new Scanner(System.in);
    private static final ArrayList<ArrayList<String>> nums =  new ArrayList<>();
    private static final ArrayList<String> given = new ArrayList<>();
    private static ArrayList<Pair> ranges = new ArrayList<>();
    private static int row;
    private static int col;
    private static int dig = 0;

    public static void main(String[] args) {
        readInput();
        long ans1 = part1();
        long ans2 = part2();
        System.out.println(ans1 + " " + ans2);
    }

    public static void readInput() {
        String input;
        while(in.hasNextLine()) {
            input = in.nextLine();
            given.add(input);
            input = input.trim();
            if(input.equals("end"))
                break;
            if(input.isEmpty())
                break;
            String[] row = input.split("\\s+");
            for(String num: row)
                dig = Math.max(dig, num.length());
            ArrayList<String> x = new ArrayList<>();
            Collections.addAll(x, row);
            nums.add(x);
        }
        row = nums.size();
        col = nums.getFirst().size();

        ranges = new ArrayList<>();
        for(int j = 0; j < col; j++)
            ranges.add(new Pair(Long.MAX_VALUE, Long.MIN_VALUE));

        for(int i = 0; i < row - 1; i++) {
            long l, r = 0;
            int j = 0;
            while(j < col) {
                while(r < given.get(i).length() && given.get(i).charAt((int) r) == ' ')
                    r++;
                if(r >= given.get(i).length())
                    break;
                l = r;
                while(r < given.get(i).length() &&
                        given.get(i).charAt((int) r) >= '0' &&
                        given.get(i).charAt((int) r) <= '9')
                    r++;
                r = Math.min(r, given.get(i).length());
                ranges.get(j).l = Math.min(l, ranges.get(j).l);
                ranges.get(j).r = Math.max(r,  ranges.get(j).r);
                j++;
            }
        }
    }

    private static long extractNum(int j) {
        long num = 0;
        for(int i = 0; i < row - 1; i++) {
            char digit = given.get(i).charAt(j);
            if(digit >= '0' && digit <= '9') {
                num *= 10;
                num += (digit - '0');
            }
        }
        return num;
    }

    public static long part1() {
        long ans = 0;
        for(int j = 0; j < col; j++) {
            String ch = nums.get(row - 1).get(j);
            long x;
            if(Objects.equals(ch, "*")) {
                x = 1;
                for(int i = 0; i < row - 1; i++) {
                    long num = Long.parseLong(nums.get(i).get(j));
                    x *= num;
                }
            }
            else {
                x = 0;
                for(int i = 0; i < row - 1; i++) {
                    long num = Long.parseLong(nums.get(i).get(j));
                    x += num;
                }
            }
            ans += x;
        }
        return ans;
    }

    public static long part2() {
        long ans = 0;
        for(int k = 0; k < col; k++) {
            String ch = nums.get(row - 1).get(k);
            long x;
            if(Objects.equals(ch, "*")) {
                x = 1;
                for(int j = (int)ranges.get(k).l; j < (int)ranges.get(k).r; j++) {
                    long num = extractNum(j);
                    x *= Math.max(1, num);
                }
            }
            else {
                x = 0;
                for(int j = (int)ranges.get(k).l; j < (int)ranges.get(k).r; j++) {
                    long num = extractNum(j);
                    x += num;
                }
            }
            ans += x;
        }
        return ans;
    }
}
