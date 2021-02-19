import java.lang.Math;
import java.util.*;
/**
 * Candidate Solution class which stores a solution for a
 * Genetic Algorithm and its fitness for the second problem.
 *
 * @author Mark Browne
 * @version 1
 */
public class CandidateP2
{
    private static final double MAX_WEIGHT = 500.0; // Max weight for solutions
    private boolean[] solution;                     // Solution values
    private double utility, weight;                 // Utility and weight of solutions
    private Random rand;
    
    /**
     * Constructor for Candidate class for problem 2.
     */
    public CandidateP2(boolean[] sol) {
        solution = sol;
        updateUtilWeight();
        rand = new Random();
        checkIfOverMaxWeight();
    }
    
    /**
     * @return solution values.
     */
    public boolean[] getSol()
    {
        return solution;
    }
    
    /**
     * @return weight of solution.
     */
    public double getWeight()
    {
        return weight;
    }
    
    /**
     * @return utility of solution.
     */
    public double getUtility()
    {
        return utility;
    }        
    
    /**
     * Updates to new solution.
     * @param newSol new solution to be updated to.
     */
    public void setSol(boolean[] newSol)
    {
        solution = newSol;
        updateUtilWeight();
        checkIfOverMaxWeight();
    }
    
    /**
     * Utility and weight are updated for new solution.
     */
    public void updateUtilWeight()
    {
        double[] utilWeight = Assess.getTest2(solution);  
        weight = utilWeight[0];
        utility = utilWeight[1];
    }
    
    /**
     * Changes the values in solution
     * @param mutationRate Rate in which a value will mutate.
     */
    public void mutate(double mutationRate)
    {
        boolean[] temp = solution;
        double pick = Math.random();
        for (int i = 0; i < solution.length; i++) {
            if (pick < mutationRate) {
                temp[i] = !temp[i];
            }
        }
        
        if (Assess.getTest2(temp)[1] > getUtility()) {
            solution = temp;
            updateUtilWeight();
            checkIfOverMaxWeight();
        }
    }
    
    /**
     * Check if solution is over the max weight.
     */
    private void checkIfOverMaxWeight()
    {
        while (weight > MAX_WEIGHT) {
            int index;
            boolean changed = false;
            do {
                index = rand.nextInt(solution.length);
                if (solution[index]) {
                    solution[index] = false;
                    changed = true;
                }
            } while (!changed);
            updateUtilWeight();
        }
    }
}
