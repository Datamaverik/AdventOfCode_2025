package Day3;

import Utils.MaxSegmentTree;

import java.util.Scanner;

public class SolveDay3 {
    private static final Scanner in = new Scanner(System.in);
    private static String[] banks;

    public static void main(String[] args) {
        readInput();
        long ans1 = part1();
        long ans2 = part2();
        System.out.println(ans1 +  " " + ans2);
    }

    private static void readInput() {
        var inputs = new StringBuilder();
        String input = in.nextLine();
        while(!input.equals("end")) {
            inputs.append(input).append(" ");
            input = in.nextLine();
        }
        banks = inputs.toString().trim().split(" ");
    }

    private static long part1() {
        long ans = 0;
        for(String bank : banks) {
            int len = bank.length();
            var suffixMax = new long[len];
            suffixMax[len - 1] = bank.charAt(len - 1) - '0';
            for(int i = len - 2; i >= 0; i--)
                suffixMax[i] = Math.max(suffixMax[i + 1], bank.charAt(i) - '0');

            long max = 0;
            for(int i = 0; i < len - 1; i++) {
                long num = ((long)(bank.charAt(i) - '0')) * 10 + suffixMax[i + 1];
                max =  Math.max(max, num);
            }

            ans += max;
        }
        return ans;
    }

    private static long part2() {
        long ans = 0;
        for(String bank : banks) {
            int len = bank.length();
            var arr = new long[len];
            for(int i = 0; i < len; i++) arr[i] = bank.charAt(i) - '0';

            var seg = new MaxSegmentTree(len, arr);
            seg.build(0, 0, len - 1);

            long max = 0;
            int digit = 12, lastDigitAt = -1;
            while(digit > 0) {
                long maxDigit = seg.query(0, 0, len - 1, lastDigitAt + 1, len - digit);
                for(int i = lastDigitAt + 1; i <= len - digit; i++)
                    if(arr[i] == maxDigit) {
                        lastDigitAt = i;
                        break;
                    }
                max *= 10;
                max += maxDigit;
                digit --;
            }
            ans += max;
        }
        return ans;
    }
}
