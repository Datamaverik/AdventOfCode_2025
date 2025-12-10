import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

public class Test {
    static { Loader.loadNativeLibraries(); }

    public static void main(String[] args) {
        MPSolver solver = MPSolver.createSolver("SCIP");

        MPVariable x = solver.makeIntVar(0, 10, "x");
        MPVariable y = solver.makeIntVar(0, 10, "y");

        MPConstraint c = solver.makeConstraint(0, 12);
        c.setCoefficient(x, 1);
        c.setCoefficient(y, 2);

        MPObjective obj = solver.objective();
        obj.setCoefficient(x, 3);
        obj.setCoefficient(y, 1);
        obj.setMaximization();

        solver.solve();

        System.out.println("x = " + x.solutionValue());
        System.out.println("y = " + y.solutionValue());
    }
}
