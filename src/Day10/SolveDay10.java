package Day10;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SolveDay10 {
    private static final Scanner in = new Scanner(System.in);
    private static final ArrayList<ArrayList<ArrayList<Integer>>> buttonConfig = new ArrayList<>();
    private static final ArrayList<String> requiredConfigs = new ArrayList<>();
    private static final ArrayList<ArrayList<Integer>> voltages = new ArrayList<>();

    public static void main(String[] args){
        readInput();
        int ans1 = part1();
        long ans2 = part2();
        System.out.println(ans1 + " " + ans2);
    }

    private static void readInput(){
        String input = in.nextLine();
        while(!input.equals("end")) {
            String[] inputArr = input.split(" ");
            String requiredConfig = inputArr[0];
            String voltage = inputArr[inputArr.length-1];

            ArrayList<ArrayList<Integer>> buttonSet = new ArrayList<>();
            for(int i = 1; i <  inputArr.length - 1; i++){
                ArrayList<Integer> buttons = new ArrayList<>();
                Matcher m = Pattern.compile("\\d+").matcher(inputArr[i]);
                while (m.find())
                    buttons.add(Integer.parseInt(m.group()));

                buttonSet.add(buttons);
            }
            buttonConfig.add(buttonSet);
            requiredConfigs.add(requiredConfig.substring(1, requiredConfig.length() - 1));

            String[] voltageNums = voltage.substring(1, voltage.length() - 1).split(",");
            ArrayList<Integer> temp = new ArrayList<>();
            for(String num: voltageNums)
                temp.add(Integer.parseInt(num));
            voltages.add(temp);
            input = in.nextLine();
        }
    }

    private static int findMinimumPresses(ArrayList<ArrayList<Integer>> buttonSet, String config) {
        int size = buttonSet.size();
        int len = config.length();
        int limit = (1 << size);
        int ans = Integer.MAX_VALUE;

        for(int mask = 0; mask < limit; mask++){
            int[] indicator = new int[len];

            for(int bit = 0; bit < size; bit++) {
                if((mask & (1 << bit)) != 0) {
                    for(int pos: buttonSet.get(bit))
                        indicator[pos]++;
                }
            }

            boolean isValid = true;
            for(int pos = 0; pos < len; pos++) {
                if((config.charAt(pos) == '.' && indicator[pos] % 2 == 1) ||
                   (config.charAt(pos) == '#' && indicator[pos] % 2 == 0)) {
                    isValid = false;
                    break;
                }
            }
            if(isValid)
                ans = Math.min(ans, Long.bitCount(mask));
        }

        return ans;
    }

    private static int solveILP(ArrayList<Integer> target, ArrayList<ArrayList<Integer>> buttons) {
        int counters = target.size();
        int size = buttons.size();

        MPSolver solver = MPSolver.createSolver("SCIP");
        if(solver == null) return -1;

        MPVariable[] x = new MPVariable[size];
        for(int i = 0; i < size; i++)
            x[i] = solver.makeIntVar(0, 1000, "x" + i);

        for(int c = 0; c < counters; c++) {
            MPConstraint constraint = solver.makeConstraint(target.get(c), target.get(c));

            for(int b = 0; b < size; b++) {
                if(buttons.get(b).contains(c))
                    constraint.setCoefficient(x[b], 1);
            }
        }

        MPObjective objective = solver.objective();
        for(int i = 0; i < size; i++)
            objective.setCoefficient(x[i], 1);

        objective.setMinimization();
        solver.solve();

        int result = 0;
        for(MPVariable var: x)
            result += (int) var.solutionValue();

        return result;
    }

    private static int part1(){
        int ans = 0;
        int n = requiredConfigs.size();
        for(int i = 0; i < n; i++) {
            String requiredConfig = requiredConfigs.get(i);
            var buttons = buttonConfig.get(i);

            ans += findMinimumPresses(buttons, requiredConfig);
        }

        return ans;
    }

    private static long part2() {
        Loader.loadNativeLibraries();

        long ans = 0;
        int n = voltages.size();

        for(int i = 0; i < n; i++) {
            ArrayList<Integer> target = voltages.get(i);
            ArrayList<ArrayList<Integer>> buttons = buttonConfig.get(i);

            ans += solveILP(target, buttons);
        }
        return ans;
    }
}
