package main.model.tsp;

public interface TSP {

	/**
	 * @return true if searchSolution() ended because of the time limit, and it
	 *         did not have time to search through the whole search space
	 */
	public Boolean isTimeLimitReached();

	/**
	 * Search for a loop of the minimal length (in terms of time) passing by
	 * every ndoe (including 0 and nodeCount-1)
	 * 
	 * @param timeLimit
	 *            : limit (in milliseconds) on the executing time of this method
	 * @param nodeCount
	 *            : count of nodes in the graph
	 * @param costMatrix
	 *            : costMatrix[i][j] = time to go from i to j, with 0 <= i <
	 *            nodeCount and 0 <= j < nodeConnt
	 * @param duration
	 *            : duration[i] = time to visit the node i, with 0 <= i <
	 *            nodeCount
	 */
	public void searchSolution(int timeLimit, int nodeCount, double[][] costMatrix, int[] duration);

	/**
	 * @param i
	 * @return the i-th node visited in the solution calculated by
	 *         searchSolution
	 */
	public Integer getBestSolution(int i);

	/**
	 * @return the length (in terms of time) of the best solution calculated by
	 *         searchSolution
	 */
	public double getBestSolutionCost();
}
