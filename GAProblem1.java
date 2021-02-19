import java.lang.Math;
import java.util.*;

/**
* Algorithm used to solve a Black Box problem which has 20 dials which have
* real values ranging from -5.0 to 5.0.
*
* @author Mark Browne
* @version 1
*/
public class GAProblem1
{
    private CandidateP1[] population;                   // Population which consists of candidate solutions
    private final int solSize, popSize;                 // Size of solutions and population
    private double[] fitness, probabilityFit;           // One stores the fitness of each solution, other stores probability of each solution being picked.
    private final double mutationRate, crossoverRate;   // Rate of mutation and rate of crossover applied to population
    private Random rand;                        
    
    /**
     * Constructor for the first GA problem.
     */
    public GAProblem1()
    {
        solSize = 20;
        popSize = 25;
        fitness = new double[popSize];
        probabilityFit = new double[popSize];
        mutationRate = 0.1;
        crossoverRate = 0.6;
        rand = new Random();
    }    
    
    /**
     * Generate initial population.
     */
    private void createPopulation()
    {
        population = new CandidateP1[popSize];
        for (int i = 0; i < popSize; i++) {
            double[] sol = new double[solSize];
            for (int j = 0; j < solSize; j++) {
                sol[j] = (10.0 * Math.random()) - 5.0;
            }
            population[i] = new CandidateP1(sol);
        }        
        calculateFitnessAndProb();
    }
    
    /**
     * Calculates the fitness and probability of each candidate solution
     * in the population.
     */
    private void calculateFitnessAndProb()
    {
        for (int i = 0; i < popSize; i++) {
            fitness[i] = population[i].getFitness();              
        }     
        
        // Gets the sum of all fitnesses.             
        double sumFit = 0.0;
        for (int i = 0; i < popSize; i++) {
            sumFit += fitness[i];
        }
        
        // Get probability of each fitness
        double numer = sumFit / popSize;
        double denom = sumFit * popSize;
        for (int i = 0; i < popSize; i++) {
            probabilityFit[i] = ((sumFit - fitness[i]) + numer) / denom;
            
            // Get cumulative probability
            if (i != 0) {
                probabilityFit[i] += probabilityFit[i-1];
            }
        }       
    }
    
    /**
     * Main method used to run the algorithm on the population.
     * @return best from current population.
     */
    public double[] run()
    {
        createPopulation();
        int lastGen = 80000;
        for (int gen = 1; gen <= lastGen; gen++) {             
            int start = 2;
            CandidateP1[] newPopulation = new CandidateP1[popSize];
            CandidateP1[] elite = elitism(start);
            System.arraycopy(elite, 0, newPopulation, 0, elite.length);
            
            for (int i = start; i < popSize; i++) {
                CandidateP1 parent1 = FPSelection();
                CandidateP1 parent2 = FPSelection();
                
                CandidateP1 offSpring = crossover(parent1, parent2);
                offSpring = mutation(offSpring, gen, lastGen);
                newPopulation[i] = offSpring;               
            }
            
            population = newPopulation;
            calculateFitnessAndProb();
            CandidateP1 fittest = getFittest();
            if (fittest.getFitness() == 0.0) {
                return fittest.getSol();
            }
        }        
        return getFittest().getSol();
    }
    
    /**
     * Fitness Proportional Selection.
     * Each candidate solution will have a probability
     * of being picked based on their fitness.
     * The lower the fitness, the higher the probability of being picked.
     * @return the picked candidate solution.
     */
    private CandidateP1 FPSelection()
    {        
        // Use cumulative probability table to pick random
        // solution.
        double pick = Math.random();
        for (int i = 0; i < popSize; i++) {
            if (i == 0) {
                if (pick < probabilityFit[i] && pick >= 0) {
                    return population[i];
                }
            }
            else if (pick <= probabilityFit[i] && pick > probabilityFit[i-1]) {
                return population[i];
            }
        }       
        // This shouldn't happen if probability is implemented
        // correctly.
        throw new ArithmeticException("" + pick);
    }   
        
    /**
     * Picks the n best solutions from the current population
     * and put them straight in the new population with no
     * mutation or crossover.
     * @param num - Number of best solutions to be sent to 
     * the new population.
     * @return Array of the n best solutions.
     */
    public CandidateP1[] elitism(int num)
    {
        List<CandidateP1> tempPop = Arrays.asList(population);
        tempPop.sort(Comparator.comparing(CandidateP1::getFitness));
        
        CandidateP1[] bestCS = new CandidateP1[num];
        int len = tempPop.size();
        for (int i = 0; i < num; i++) {
            bestCS[i] = tempPop.get(i);
        }
        return bestCS;
    }
        
    /**
     * Calculates the value in which the solution should be mutated by.
     * @param curGen - current generation of the population.
     * @param maxGen - max generation of the population.
     * @param value - Current value in the solution to be mutated.
     * @return value in which the solution should be mutated by.
     */
    private double mutFunc(int curGen, int maxGen, double value)
    {
        double power = 1 - (curGen / maxGen);
        double randConst = Math.random();
        double randPow = 1 - (Math.pow(randConst, power));
        double raised = Math.pow(randPow, 200);
        return value * raised;
    }
    
    /**
     * If a solution is set to be mutated, one if its values
     * will be changed according to different parameters.
     * Paramaters include current generation, max generation
     * and the actual range of the values.
     * @param curGen - current generation of the population.
     * @param maxGen - max generation of the population.
     */
    private CandidateP1 mutation(CandidateP1 cs, int curGen, int maxGen)
    {           
        cs.updateFitness();
        CandidateP1 newCS = new CandidateP1(cs.getSol());
                
        double pick = Math.random();
        if (pick < mutationRate) {
            double tau = Math.random();
            int index = rand.nextInt(solSize);
            double value = newCS.getSol()[index];
            if (tau < 0.5) 
                newCS.mutate(index, false, mutFunc(curGen, maxGen, (value + 5.0)));
            else 
                newCS.mutate(index, true, mutFunc(curGen, maxGen, (5.0 - value)));           
        }                                  
        return newCS;
    }
    
    /**
     * Produce a new Candidate Solution (offspring) from 
     * two parent solutions from the current population.
     * @param parent1 - First parent solution.
     * @param parent2 - Second parent solution.
     * @return offSpring of the two parent solutions.
     */
    private CandidateP1 crossover(CandidateP1 parent1, CandidateP1 parent2)
    {             
        double[] sol1 = parent1.getSol();
        double[] sol2 = parent2.getSol();                
           
        double pick = Math.random();
        if (pick < crossoverRate) {
            double[] newSol = new double[solSize];
            for (int i = 0; i < solSize; i++) {
                double alpha = 0.1;
                double max = Math.max(sol1[i], sol2[i]);
                double min = Math.min(sol1[i], sol2[i]);
                double range = max - min;
                double lowBound = min - (range * alpha);
                double upBound = max + (range * alpha);
                newSol[i] = lowBound + (upBound - lowBound) * rand.nextDouble();
            }
            return new CandidateP1(newSol);
        }
        else {
            parent1.updateFitness();
            parent2.updateFitness();
            if (parent1.getFitness() < parent2.getFitness()) 
                return new CandidateP1(sol1);
            else 
                return new CandidateP1(sol2);
        }
    }
    
    /**
     * Finds the best candidate solution from the current population.
     * @return Best candidate solution in current population.
     */
    public CandidateP1 getFittest()
    {
        CandidateP1 best = population[0];
        for (int i = 1; i < popSize; i++) {
            if (population[i].getFitness() < best.getFitness()) 
                best = population[i];
        }
        return best;
    }
}
