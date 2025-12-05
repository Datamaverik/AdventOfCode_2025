package Day4;

import Utils.Node;

import java.util.*;

public class SolveDay4 {
    private static final Scanner in = new Scanner(System.in);

    private static String[] grid;

    private static final HashMap<Node, Node> map = new HashMap<>();
    private static final TreeSet<Node> set = new TreeSet<>((a, b) -> {
        if (a.count != b.count)
            return Integer.compare(a.count, b.count);
        if (a.i != b.i)
            return Integer.compare(a.i, b.i);
        return Integer.compare(a.j, b.j);
    });

    private static final int[] dx = {1, 1, 1, 0, 0, -1, -1, -1};
    private static final int[] dy = {-1, 0, 1, 1, -1, -1, 0, 1};
    private static int row;
    private static int col;

    public static void main(String[] args){
        readInput();
        long ans1 = part1();
        long ans2 = part2();
        System.out.println(ans1 + " " + ans2);
    }

    private static void readInput() {
        var inputs = new StringBuilder();
        String input = in.nextLine();
        while(!input.equals("end")) {
            inputs.append(input).append(" ");
            input = in.nextLine();
        }
        grid =  inputs.toString().trim().split(" ");
        col = grid.length;
        row = grid[0].length();
        process(grid);
    }

    private static void process(String[] grid) {
        for(int i = 0; i < col; i++){
            for(int j = 0; j < row; j++){
                if(grid[i].charAt(j) != '@')
                    continue;
                int count = 0;
                for(int k = 0; k < 8; k++) {
                    int ni = i + dx[k];
                    int nj = j + dy[k];

                    if(ni < 0 || nj < 0 || ni >= col || nj >= row) continue;
                    if(grid[ni].charAt(nj) == '@')
                        count++;
                }
                insertNode(i, j, count);
            }
        }
    }

    private static long part1() {
        Node query = new Node(0, 0, 4);
        return set.headSet(query, false).size();
    }

    private static long part2() {
        long ans = 0;
        while(true) {
            Node query = new Node(0, 0, 4);
            NavigableSet<Node> subset = set.headSet(query, false);

            if (subset.isEmpty())
                break;

            List<Node> toProcess = new ArrayList<>(subset);
            for (Node n : toProcess) {
                int i = n.i;
                int j = n.j;
                delNode(i, j);
                ans++;
                for(int k = 0; k < 8; k++) {
                    int ni = i + dx[k];
                    int nj = j + dy[k];
                    if(ni < 0 || nj < 0 || ni >= col || nj >= row || grid[i].charAt(j) != '@')
                        continue;

                    Node neigh = findNode(ni, nj);
                    if(neigh == null)
                        continue;

                    int nCount = neigh.count - 1;
                    delNode(neigh.i, neigh.j);
                    insertNode(ni, nj, nCount);
                }
            }
        }
        return ans;
    }

    private static void insertNode(int i, int j, int count) {
        var node = new Node(i, j, count);
        map.put(node, node);
        set.add(node);
    }

    private static Node findNode(int i, int j) {
        var node = new Node(i, j, 0);
        return map.get(node);
    }

    private static void delNode(int i, int j) {
        var node = new Node(i, j, 0);
        Node n = map.get(node);

        if (n != null) {
            set.remove(n);
            map.remove(n);
        }
    }
}
