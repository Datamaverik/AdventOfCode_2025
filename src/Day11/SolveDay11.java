package Day11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SolveDay11 {
    private final static Scanner in = new Scanner(System.in);
    private final static Map<String, ArrayList<String>> adj = new HashMap<>();
    private static final Map<String, Long[][]> memo = new HashMap<>();
    private static int numOfPaths;

    public static void main(String[] args) {
        readInput();
        numOfPaths = 0;
        dfs("you");
        System.out.print(numOfPaths + " ");

        adj.forEach((u, neigh) -> memo.putIfAbsent(u, new Long[][]{{-1L, -1L}, {-1L, -1L}}));
        long ans2 = findDefectivePaths("svr", 0, 0);
        System.out.println(ans2);
    }

    private static void readInput() {
        String input = in.nextLine();
        while(!input.equals("end")) {
            String[] inputArr = input.split(" ");
            String u =  inputArr[0].substring(0, inputArr[0].length()-1);
            ArrayList<String> neighbors = new ArrayList<>();
            for(String v: inputArr) {
                if(u.equals(v.substring(0, v.length()-1)))
                    continue;
                neighbors.add(v);
            }
            adj.put(u, neighbors);
            input = in.nextLine();
        }
    }

    private static void dfs(String node) {
        if(node.equals("out")) {
            numOfPaths++;
            return;
        }

        for(String neighbor: adj.get(node))
            dfs(neighbor);
    }

    private static long findDefectivePaths(String node, int seenDAC, int seenFFT) {
        if(node.equals("out"))
            return seenDAC == 1 && seenFFT == 1 ? 1L : 0L;
        var arr = memo.get(node);
        if(arr[seenDAC][seenFFT] != -1L)
            return arr[seenDAC][seenFFT];

        if(node.equals("dac"))
            seenDAC = 1;
        if(node.equals("fft"))
            seenFFT = 1;

        long ans = 0;
        for(String neighbor: adj.get(node))
            ans += findDefectivePaths(neighbor, seenDAC, seenFFT);

        arr[seenDAC][seenFFT] = ans;
        memo.put(node, arr);
        return ans;
    }
}
