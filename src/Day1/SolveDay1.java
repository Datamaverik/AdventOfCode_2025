package Day1;

import java.util.Scanner;

public class SolveDay1 {
    private static final Scanner in = new Scanner(System.in);
    private static String[] movements;

    public static void main(String[] args) {
        getInput();
        int ans1 = part1();
        int ans2 = part2();
        System.out.println(ans1 + " " + ans2);
    }

    private static void getInput() {
        StringBuilder inputs = new StringBuilder();
        String input = in.nextLine();
        while(!input.equals("end")) {
            inputs.append(input).append(" ");
            input = in.nextLine();
        }
        movements = inputs.toString().trim().split(" ");
    }

    private static int part1() {
        int current = 50, count = 0;
        for(String movement: movements)
        {
            int num = Integer.parseInt(movement.substring(1));
            int pos = current;
            num = num % 100;
            if (movement.charAt(0) == 'L')
                pos = current - num;
            else
                pos = current + num;

            if (pos < 0) pos += 100;
            if (pos > 99) pos -= 100;
            current = pos;
            if (pos == 0) count++;
        }
        return count;
    }

    private static int part2() {
        int current = 50, count = 0;
        for(String movement: movements)
        {
            int num = Integer.parseInt(movement.substring(1));
            int pos = current;
            count += num / 100;
            num = num % 100;
            if (movement.charAt(0) == 'L')
                pos = current - num;
            else
                pos = current + num;

            if ((pos <= 0 || pos >= 100) && current != 0) count++;
            if (pos < 0) pos += 100;
            if (pos > 99) pos -= 100;
            current = pos;
        }
        return count;
    }
}
