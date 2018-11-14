package main.model.tsp;

/**
 * Interface for a TSP resolution algorithm.
 */
public interface TSP {

    /**
     * Check whether the time limit has been reached.
     * 
     * @return true if searchSolution() ended because of the time limit, and it did
     *         not have time to search through the whole search space.
     */
    public Boolean isTimeLimitReached();

    /**
     * Search for a loop of the minimal length (in terms of time) passing by every
     * node (including 0 and nodeCount - 1).
     * 
     * @param timeLimit  is the limit (in milliseconds) on the runtime of this
     *                       method
     * @param nodeCount  is the count of nodes in the graph
     * @param costMatrix is the matrix of times to go from i to j, with 0 <= i <
     *                       nodeCount and 0 <= j < nodeConnt
     * @param duration   is the array of durations to visit the node i, with 0 <= i
     *                       < nodeCount
     */
    public void searchSolution(int timeLimit, int nodeCount, double[][] costMatrix, int[] duration);

    /**
     * Get a node in the best solution.
     * 
     * @param i is the index of the node to get.
     * @return the i-th node visited in the solution calculated by searchSolution.
     */
    public Integer getNodeInBestSolution(int i);

    /**
     * Get the total cost of the best solution.
     * 
     * @return the length (in terms of time) of the best solution calculated by
     *         searchSolution.
     */
    public double getBestSolutionCost();
}
