import java.lang.Math;
import java.util.*;
/**
 * Candidate Solution class which stores a solution for a
 * Genetic Algorithm and its fitness for the first problem.
 *
 * @author Mark Browne
 * @version 1
 */
public class CandidateP1
{
    private double fitness;     // Fitness of solution
    private double[] solution;  // Solution values
    
    /**
     * Constructor for Candidate class for problem 2.
     */
    public CandidateP1(double[] sol) 
    {
        solution = sol;
        updateFitness();
    }
    
    /**
     * @return solution values.
     */
    public double[] getSol()
    {
        return solution;
    }
    
    /**
     * @return fitness of solution.
     */
    public double getFitness()
    {
        return fitness;
    }
    
    /**
     * Set solution to be new solution.
     * @param newSol new solution to be set.
     */
    public void setSol(double[] newSol)
    {
        solution = newSol;
        updateFitness();
    }
     
    /**
     * Update fitness for new solution.
     */
    public void updateFitness()
    {
        fitness = Assess.getTest1(solution);
    }
    
    /**
     * Change the values in the solution based on the rate.
     * @param rate Rate on which a value is to be changed.
     * @param addOrSub Whether to add or subtract from value.
     * @param change actual change to be added or subtracted to value.
     */
    public void mutate(double rate, boolean addOrSub, double change)
    {        
        if (!addOrSub) {
            change *= -1;
        }
        
        for (int i = 0; i < solution.length; i++) {
            double pick = Math.random();
            if (pick < rate) {
                double[] temp = solution;
                temp[i] += change;
                if (Assess.getTest1(temp) < fitness) {
                    solution[i] += change;
                    updateFitness();
                }
            }
        }
    } 
}
