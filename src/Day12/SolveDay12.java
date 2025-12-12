package Day12;

import java.util.ArrayList;
import java.util.Scanner;

public class SolveDay12 {
    private static final Scanner in = new Scanner(System.in);
    private static final ArrayList<ArrayList<String>> shapes = new ArrayList<>();
    private static final ArrayList<Integer> areas = new ArrayList<>();
    private static final ArrayList<ArrayList<Integer>> required = new ArrayList<>();

    public static void main(String[] args) {
        readInput();
        int ans1 = part1();
        System.out.println(ans1);
    }

    private static void readInput() {
        String input = in.nextLine();
        var shape = new ArrayList<String>();
        while(!input.equals("stop")) {
            if(input.charAt(0) >= '0' && input.charAt(0) <= '9') {
                if(!shape.isEmpty())
                    shapes.add(shape);
                shape = new ArrayList<>();
            }
            else
                shape.add(input);

            input = in.nextLine();
        }
        if(!shape.isEmpty())
            shapes.add(shape);

        input = in.nextLine();
        while(!input.equals("end")) {
            String[] parts = input.split(" ");
            String[] inputs = parts[0].split("x");
            int len = Integer.parseInt(inputs[0]);
            int breadth = Integer.parseInt(inputs[1].substring(0, inputs[1].length()-1));
            areas.add(len * breadth);

            var req = new ArrayList<Integer>();
            for(int i = 1; i < parts.length; i++)
                req.add(Integer.parseInt(parts[i]));

            required.add(req);
            input = in.nextLine();
        }
    }

    private static int part1() {
        ArrayList<Integer> shapeArea = new ArrayList<>();
        shapes.forEach(shape -> {
            int area = 0;
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(shape.get(i).charAt(j) == '#')
                        area++;
                }
            }
            shapeArea.add(area);
        });

        int ans = 0;
        int len = areas.size();
        for(int i = 0; i < len; i++) {
            int totalArea = areas.get(i);
            int requiredArea = 0;
            int size =  required.get(i).size();
            for(int j = 0; j < size; j++)
                requiredArea += required.get(i).get(j) * shapeArea.get(j);

            System.out.println(requiredArea + " " + totalArea);
            if(requiredArea <= totalArea)
                ans++;
        }

        return ans;
    }
}
