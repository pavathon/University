
import java.lang.Math;
import java.util.*;
/**
 * Algorithm used to solve the knapsack problem which has 100 boolean values.
 *
 * @author Mark Browne
 * @version 1
 */
public class GAProblem2
{
    private CandidateP2[] population;                       // Population which consists of candidate solutions
    private final int solSize, popSize;                     // Size of solutions and population
    private final double[] utility, weight, probabilityFit; // Utility, weight and probability of each solution
    private final double mutationRate, crossoverRate;       // Rate of mutation and rate of crossover applied to population
    private final Random rand;
    
    /**
     * Constructor for the second GA problem.
     */
    public GAProblem2()
    {
        solSize = 100;
        popSize = 1500;
        utility = new double[popSize];
        weight = new double[popSize];
        probabilityFit = new double[popSize];
        mutationRate = 0.001;
        crossoverRate = 0.75;        
        rand = new Random();                
    }
    
    /**
     * Generate initial population.
     */
    private void createPopulation()
    {
        population = new CandidateP2[popSize];
        for (int pop = 0; pop < popSize; pop++) {
            boolean[] sol = new boolean[solSize];
            for (int j = 0; j < solSize; j++) sol[j] = rand.nextBoolean();
            population[pop] = new CandidateP2(sol);
        }
        calculateUtilWeightAndProb();
    }
    
    /**
     * Calculates the utility, weight and probability of each candidate 
     * solution in the population.
     */
    private void calculateUtilWeightAndProb()
    {
        int pop;
        for (pop = 0; pop < popSize; pop++) {
            weight[pop] = population[pop].getWeight();
            utility[pop] = population[pop].getUtility();
            probabilityFit[pop] = utility[pop];
        }     
        
        // Gets the sum of all fitnesses.
        double sumFit = 0.0;
        for (pop = 0; pop < popSize; pop++) sumFit += utility[pop];
        
        // Get probability of each fitness
        for (pop = 0; pop < popSize; pop++) {
            probabilityFit[pop] = (utility[pop] / sumFit);
            
            // Get cumulative probability
            if (pop != 0) probabilityFit[pop] += probabilityFit[pop-1];
        }
    }
    
    /**
     * Main method used to run the algorithm on the population.
     * @return best from current population.
     */
    public boolean[] run()
    {
        createPopulation();
        int lastGen = 150;
        for (int gen = 0; gen < lastGen; gen++) {               
            int start = 2;
            CandidateP2[] newPopulation = new CandidateP2[popSize];
            CandidateP2[] elite = elitism();
            System.arraycopy(elite, 0, newPopulation, 0, elite.length);
            
            for (int pop = start; pop < popSize; pop += 2) {
                CandidateP2 parent1 = FPSelection();
                CandidateP2 parent2 = FPSelection();

                assert parent1 != null;
                assert parent2 != null;
                CandidateP2 offSpring1 = crossover(parent1, parent2);
                offSpring1 = mutation(offSpring1);
                newPopulation[pop] = offSpring1;
                
                CandidateP2 offSpring2 = crossover(parent2, parent1);
                offSpring2 = mutation(offSpring2);
                newPopulation[pop+1] = offSpring2;
            }
            
            population = newPopulation;
            calculateUtilWeightAndProb();
            
            CandidateP2 fittest = getFittest();
            if (fittest.getUtility() >= 203.0) return fittest.getSol();
        }        
        return getFittest().getSol();
    }
    
    /**
     * Fitness Proportional Selection.
     * Each candidate solution will have a probability
     * of being picked based on their fitness.
     * The lower the fitness, the higher the probability.
     * @return Selected solution.
     */
    private CandidateP2 FPSelection()
    {        
        // Use cumulative probability table to pick random
        // solution.
        double pick = Math.random();
        for (int pop = 0; pop < popSize; pop++) {
            if (pop == 0) {
                if (pick < probabilityFit[pop] && pick >= 0)
                    return population[pop];
            }
            else if (pick <= probabilityFit[pop] && pick > probabilityFit[pop-1])
                return population[pop];
        }       
        return null;
    }
    
    /**
     * Picks the n best solutions from the current population
     * and put them straight in the new population with no
     * mutation or crossover.
     * @return Array of the n best solutions.
     */
    public CandidateP2[] elitism()
    {
        List<CandidateP2> tempPop = Arrays.asList(population);
        tempPop.sort(Comparator.comparing(CandidateP2::getUtility));
        
        int len = tempPop.size();
        return new CandidateP2[] {tempPop.get(len - 1), tempPop.get(len - 2)};
    }
    
    /**
     * Produce a new Candidate Solution (offspring) from 
     * two parent solutions from the current population.
     * @param parent1 - First parent solution.
     * @param parent2 - Second parent solution.
     * @return offSpring of the two parent solutions.
     */
    private CandidateP2 crossover(CandidateP2 parent1, CandidateP2 parent2)
    {
        boolean[] sol1 = parent1.getSol();
        boolean[] sol2 = parent2.getSol();
        
        double pick = Math.random();   
        if (pick < crossoverRate) {
            boolean[] newSol = new boolean[solSize];
            int crossPoint = rand.nextInt(solSize - 1) + 1;
            for (int sol = 0; sol < solSize; sol++) {
                if (sol < crossPoint) newSol[sol] = sol1[sol];
                else newSol[sol] = sol2[sol];
            }
            return new CandidateP2(newSol);
        }
        else {
            parent1.updateUtilWeight();
            parent2.updateUtilWeight();
            if (parent1.getUtility() > parent2.getUtility()) 
                return new CandidateP2(sol1);
            else 
                return new CandidateP2(sol2);
        }
    }
    
    /**
     * If a solution is set to be mutated, one if its values
     * will be changed according to different parameters.
     * @param ca solution to be mutated.
     */
    private CandidateP2 mutation(CandidateP2 ca)
    {
        ca.updateUtilWeight();
        CandidateP2 newCA = new CandidateP2(ca.getSol());  
        newCA.mutate(mutationRate);          
        return newCA;
    }
    
    /**
     * Finds the best candidate solution from the current population.
     * @return Best candidate solution in current population.
     */
    public CandidateP2 getFittest()
    {
        CandidateP2 best = population[0];
        for (int pop = 1; pop < popSize; pop++) {
            if (population[pop].getUtility() > best.getUtility())
                best = population[pop];
        }
        return best;
    }   
}
