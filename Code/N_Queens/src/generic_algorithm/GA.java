package generic_algorithm;/* generic_algorithm.GA.java
 *
 * Runs generic_algorithm.GeneticAlgorithm.java and logs the results into a file using generic_algorithm.Writer.java.
 * GA testing setup is according to pass/fail criteria
 * Pass criteria - 50 success
 * Fail criteria - 100 failures
 *
 * @author: James M. Bayon-on
 * @version: 1.3
 */

public class GA
{
    private Writer logWriter;
    private GeneticAlgorithm ga;
    private int MAX_RUN;
    private int MAX_LENGTH;
    private long[] runTimes;

    // =================================================================================================================

    /* Instantiates the generic_algorithm.GA class
     *
     */
    public GA(int maxRun)
    {
        logWriter = new Writer();
        MAX_RUN = maxRun;
        runTimes = new long[MAX_RUN];
    }

    // =================================================================================================================

    /* Test method accepts the N/max length, and parameters mutation rate and max epoch to set for the GA accordingly.
     *
     * @param: max length/n
     * @param: mutation rate for GA
     * @param: max epoch for GA
     */
    public void run(int maxLength, double mutationRate, int maxEpoch)
    {
        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();

        MAX_LENGTH = maxLength;
        ga = new GeneticAlgorithm(MAX_LENGTH, maxEpoch, mutationRate);                                        //define ga here
        ga.setMutation(mutationRate);
        ga.setEpoch(maxEpoch);
        long testStart = System.nanoTime();
        String filepath = "GA-N" + MAX_LENGTH + "-" + mutationRate + "-" + maxEpoch + "_" + System.currentTimeMillis() + ".txt";
        long startTime, endTime, totalTime;
        int fail = 0;
        int success = 0;

        logParameters();

        // run 50 success to pass passing criteria
        for (int i = 0; i < MAX_RUN; )
        {
            startTime = System.nanoTime();

            if (ga.algorithm())
            {
                endTime = System.nanoTime();
                totalTime = endTime - startTime;

                System.out.println("Done");
                System.out.println("run " + (i + 1));
                System.out.println("time in nanoseconds: " + totalTime);
                System.out.println("Success!\n");

                runTimes[i] = totalTime;
                i++;
                success++;

                // write to log
                logWriter.add("Run: " + i);
                logWriter.add("Runtime in nanoseconds: " + totalTime);
                logWriter.add("Found at epoch: " + ga.getEpoch());
                logWriter.add("Population size: " + ga.getPopSize());
                logWriter.add("");

                // write solutions to log file
                for (Chromosome c : ga.getSolutions())
                {
                    logWriter.add(c);
                    logWriter.add("");
                }

            }
            // count failures for failing criteria
            else
            {
                fail++;
                System.out.println("Fail!");
            }

            if (fail >= 100)
            {
                System.out.println("Cannot find solution with these params");
                break;
            }
        }


        long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long actualMemUsed = afterUsedMem - beforeUsedMem;

        System.out.println("Statistics");
        System.out.println("==========");
        System.out.println("Number of Success: " + success);
        System.out.println("Number of failures: " + fail);
        System.out.println("==========\n");

        logWriter.add("Runtime summary");
        logWriter.add("");

        // print runtime summary
        for (long runtime : runTimes) {
            logWriter.add(Long.toString(runtime));
        }

        long testEnd = System.nanoTime();
        logWriter.add(Long.toString(testStart));
        logWriter.add(Long.toString(testEnd));
        logWriter.add(Long.toString(testEnd - testStart));


        logWriter.add("\nActual Memory Used: " + actualMemUsed + " Bytes");

        logWriter.writeFile(filepath);
        printRuntimes();
    }

    // =================================================================================================================
    /* Converts the parameters of GA to string and adds it to the string list in the writer class
     *
     */
    private void logParameters()
    {
        logWriter.add("Genetic Algorithm");
        logWriter.add("Parameters");
        logWriter.add("MAX_LENGTH/N: " + MAX_LENGTH);
        logWriter.add("STARTING_POPULATION: " + ga.getStartSize());
        logWriter.add("MAX_EPOCHS: " + ga.getMaxEpoch());
        logWriter.add("MATING_PROBABILITY: " + ga.getMatingProb());
        logWriter.add("MUTATION_RATE: " + ga.getMutationRate());
        logWriter.add("MIN_SELECTED_PARENTS: " + ga.getMinSelect());
        logWriter.add("MAX_SELECTED_PARENTS: " + ga.getMaxSelect());
        logWriter.add("OFFSPRING_PER_GENERATION: " + ga.getOffspring());
        logWriter.add("MINIMUM_SHUFFLES: " + ga.getShuffleMin());
        logWriter.add("MAXIMUM_SHUFFLES: " + ga.getShuffleMax());
        logWriter.add("");
    }

    // =================================================================================================================
    /* Prints the runtime summary in the console
     *
     */
    private void printRuntimes() {
        int i = 1;
        for (long x : runTimes) {
            System.out.println("Iter " + i++ + " run with time: " + x + " nanoseconds");
        }
    }

    // =================================================================================================================

//		ONE EPOCH:

//		One pass through the algorithm which includes the creation of a new population of individuals (solutions).
//      There are many variation to this but here is the basic algorithm:
//
//		Step 1: Genesis -   create an initial population with genetic diversity.
//
//		Step 2: Mating -    select two individuals at a time and mate them to produce two new offspring. Continue until
//                          entire population has been mated.
//
//		Step 3: Mutate -    randomly select the number of individuals that will me mutated then select each one them and
//                          mutate a random number of genes in each.
//
//		Step 4: Rank the fitness of all individuals in the population. If the desired fitness is not achieved,
//      (highest or lowest rank depending on the design of your fitness function) go to Step 2 (THIS COMPLETS AN EPOCH).


    // =================================================================================================================

}
