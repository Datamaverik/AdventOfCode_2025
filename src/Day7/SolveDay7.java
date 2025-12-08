package Day7;

import java.util.ArrayList;
import java.util.Scanner;

public class SolveDay7 {
    private static final Scanner in = new Scanner(System.in);
    private static final ArrayList<String> grid = new ArrayList<>();
    private static ArrayList<ArrayList<Boolean>> vis = new ArrayList<>();
    private static ArrayList<ArrayList<Long>> dp = new ArrayList<>();
    private static int row;
    private static int col;
    private static int count;

    public static void main(String[] args) {
        readInput();
        count = 1;
        part1();
        long ans = part2();
        System.out.println(count + " " + ans);
    }

    public static void readInput() {
        String input = in.nextLine();
        while(!input.equals("end")) {
            grid.add(input);
            input = in.nextLine();
        }
        row = grid.size();
        col = grid.getFirst().length();
    }

    public static void dfs(int i, int j) {
        if(i < 0 || j < 0 || i >= row || j >= col || vis.get(i).get(j))
            return;

        vis.get(i).set(j, true);
        if(grid.get(i).charAt(j) == '^') {
            count++;
            dfs(i, j + 1);
            dfs(i, j - 1);
        }
        else
            dfs(i + 1, j);
    }

    public static long dfs2(int i, int j) {
        if(i < 0 || j < 0 || i >= row || j >= col)
            return 0;
        if(i == row - 1)
            return 1L;

        if(dp.get(i).get(j) != -1)
            return dp.get(i).get(j);

        long ans = 0;
        if(grid.get(i).charAt(j) == '^') {
            ans += dfs2(i, j + 1);
            ans +=  dfs2(i, j - 1);
        }
        else
            ans += dfs2(i + 1, j);

        dp.get(i).set(j, ans);
        return ans;
    }

    public static void part1() {
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                if(grid.get(i).charAt(j) == 'S') {
                    vis = new ArrayList<>(row);
                    for(int x = 0; x < row; x++){
                        ArrayList<Boolean> row = new ArrayList<>();
                        for(int y = 0; y < col; y++)
                            row.add(false);
                        vis.add(row);
                    }
                    dfs(i, j);
                    return;
                }
            }
        }
    }

    public static long part2() {
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                if(grid.get(i).charAt(j) == 'S') {
                    dp = new ArrayList<>(row);
                    for(int x = 0; x < row; x++){
                        ArrayList<Long> row = new ArrayList<>();
                        for(int y = 0; y < col; y++)
                            row.add(-1L);
                        dp.add(row);
                    }
                    return dfs2(i, j);
                }
            }
        }
        return 0;
    }
}
