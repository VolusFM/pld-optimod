package main.model.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * TemplateTSP is an super-class with convenient methods to execute a TSP
 */
public abstract class TemplateTSP implements TSP {

    private Integer[] bestSolution;
    private double bestSolutionCost = 0;
    private Boolean timelimitReached;

    public Boolean isTimeLimitReached() {
	return timelimitReached;
    }

    public void searchSolution(int timelimit, int numberOfNodes, double[][] cost, int[] duration) {
	timelimitReached = false;
	bestSolutionCost = Integer.MAX_VALUE;
	bestSolution = new Integer[numberOfNodes];
	ArrayList<Integer> unseenNodes = new ArrayList<Integer>();
	for (int i = 1; i < numberOfNodes; i++)
	    unseenNodes.add(i);
	ArrayList<Integer> seenNodes = new ArrayList<Integer>(numberOfNodes);
	seenNodes.add(0); // first visited node is 0
	branchAndBound(0, unseenNodes, seenNodes, 0, cost, duration, System.currentTimeMillis(), timelimit);
    }

    public Integer getBestSolution(int i) {
	if ((bestSolution == null) || (i < 0) || (i >= bestSolution.length))
	    return null;
	return bestSolution[i];
    }

    /**
     * Get the nodes corresponding to the best solution for the execution of
     * this TSP
     * 
     * @return an array of integers corresponding to the nodes to travel to, in
     *         order
     */
    public Integer[] getBestSolution() {
	return bestSolution;
    }

    public double getBestSolutionCost() {
	return bestSolutionCost;
    }

    /**
     * Must be redefined by subclasses.
     * 
     * @param currentNode : the current node
     * @param unseenNodes : table of nodes we still have to visit.
     * @param cost : cost[i][j] = duration to go from i to j, with 0 <= i <
     *            numberOfNodes and 0 <= j < numberOfNodes.
     * @param duration : duration[i] = duration to visit i node with 0 <= i <
     *            numberOfNodes.
     * @return a lower bound of the permutations' cost, starting from
     *         currentNode, containing each unseen node exactly one time and
     *         finishing by 0 node.
     */
    protected abstract int bound(Integer currentNode, ArrayList<Integer> unseenNodes, double[][] cost, int[] duration);

    /**
     * Must be redefined by subclasses.
     * 
     * @param sommetCrt : the current node.
     * @param unseenNodes : table of nodes we still have to visit.
     * @param cost : cost[i][j] = duration to go from i to j, with 0 <= i <
     *            numberOfNodes and 0 <= j < numberOfNodes.
     * @param duration : duration[i] = duration to visit i node with 0 <= i <
     *            numberOfNodes.
     * @return iterator to iterate on all unseen nodes.
     */
    protected abstract Iterator<Integer> iterator(Integer sommetCrt, ArrayList<Integer> unseenNodes, double[][] cost,
	    int[] duration);

    /**
     * Defines the template of a branch and bound resolution of the TSP.
     * 
     * @param sommetCrt the current node.
     * @param unseenNodes table of nodes we still have to visit.
     * @param seenNodes table of nodes we already have visited.
     * @param seenCost the sum of the arcs' cost of the path going through all
     *            seen nodes, plus the duration of the seen nodes.
     * @param cost : cost[i][j] = duration to go from i to j, with 0 <= i <
     *            numberOfNodes and 0 <= j < numberOfNodes.
     * @param duration : duration[i] = duration to visit i node with 0 <= i <
     *            numberOfNodes.
     * @param startingTime : moment when resolution started.
     * @param timelimit : timelimit for the resolution.
     */
    void branchAndBound(int sommetCrt, ArrayList<Integer> unseenNodes, ArrayList<Integer> seenNodes, double seenCost,
	    double[][] cost, int[] duration, long startingTime, int timelimit) {
	if (System.currentTimeMillis() - startingTime > timelimit) {
	    timelimitReached = true;
	    return;
	}
	if (unseenNodes.size() == 0) {
	    // all nodes have been visited
	    seenCost += cost[sommetCrt][0];
	    if (seenCost < bestSolutionCost) {
		// we found a better solution than bestSolution
		seenNodes.toArray(bestSolution);
		bestSolutionCost = seenCost;
	    }
	} else if (seenCost + bound(sommetCrt, unseenNodes, cost, duration) < bestSolutionCost) {
	    Iterator<Integer> it = iterator(sommetCrt, unseenNodes, cost, duration);
	    while (it.hasNext()) {
		Integer nextNode = it.next();
		seenNodes.add(nextNode);
		unseenNodes.remove(nextNode);
		branchAndBound(nextNode, unseenNodes, seenNodes,
			seenCost + cost[sommetCrt][nextNode] + duration[nextNode], cost, duration, startingTime,
			timelimit);
		seenNodes.remove(nextNode);
		unseenNodes.add(nextNode);
	    }
	}
    }

    /**
     * Helper method that takes everything needed and calculate the solution
     * 
     * @param timeLimit : limit (in milliseconds) on the executing time of this
     *            method
     * @param nodeCount : count of nodes in the graph
     * @param costMatrix : costMatrix[i][j] = time to go from i to j, with 0 <=
     *            i < nodeCount and 0 <= j < nodeConnt
     * @param duration : duration[i] = time to visit the node i, with 0 <= i <
     *            nodeCount
     */
    public void searchAndDisplayBestSolution(int timeLimit, int nodeCount, double[][] costMatrix, int[] duration) {
	searchSolution(timeLimit, nodeCount, costMatrix, duration);

	if (isTimeLimitReached()) {
	    System.err.println("Not enough time to find best solution");
	    return;
	}

	System.out.println("Best solution cost: " + getBestSolutionCost());
	System.out.println("Best solution: " + Arrays.toString(getBestSolution()));

	System.out.println();
	System.out.println("Best solution: ");
	int currentNode = 0;
	int initialNode = 0;
	int nextNode;
	int i;
	for (i = 1; i < nodeCount; i++) {
	    System.out.println("Étape " + i + " :");
	    nextNode = getBestSolution(i);
	    System.out.println("Aller de " + currentNode + " à " + nextNode + " (pour un coût de "
		    + costMatrix[currentNode][nextNode] + ", et une attente de " + duration[nextNode]
		    + " à l'arrivée)");
	    currentNode = nextNode;
	    System.out.println();

	}
	System.out.println("Étape " + i + " (finale : retour au dépot) :");
	System.out.println("Aller de " + currentNode + " à " + initialNode + " (pour un coût de "
		+ costMatrix[currentNode][initialNode] + ")");
    }

}
