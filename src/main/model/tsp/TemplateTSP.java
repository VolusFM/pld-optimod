package main.model.tsp;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * TemplateTSP is an super-class with convenient methods to resolve a TSP.
 */
public abstract class TemplateTSP implements TSP {

    private Integer[] bestSolution;
    private double bestSolutionCost = 0;
    private Boolean timelimitReached;

    @Override
    public Boolean isTimeLimitReached() {
	return this.timelimitReached;
    }

    @Override
    public void searchSolution(int timelimit, int numberOfNodes, double[][] cost, int[] duration) {
	this.timelimitReached = false;
	this.bestSolutionCost = Integer.MAX_VALUE;
	this.bestSolution = new Integer[numberOfNodes];
	ArrayList<Integer> unseenNodes = new ArrayList<Integer>();
	for (int i = 1; i < numberOfNodes; i++)
	    unseenNodes.add(i);
	ArrayList<Integer> seenNodes = new ArrayList<Integer>(numberOfNodes);
	seenNodes.add(0); // first visited node is 0
	branchAndBound(0, unseenNodes, seenNodes, 0, cost, duration, System.currentTimeMillis(), timelimit);
    }

    @Override
    public Integer getNodeInBestSolution(int i) {
	if ((this.bestSolution == null) || (i < 0) || (i >= this.bestSolution.length))
	    return null;
	return this.bestSolution[i];
    }

    /**
     * Get the nodes corresponding to the best solution for the execution of this
     * TSP
     * 
     * @return Array, an array of integers corresponding to the nodes to travel to,
     *         in order
     */
    public Integer[] getBestSolution() {
	return this.bestSolution;
    }

    @Override
    public double getBestSolutionCost() {
	return this.bestSolutionCost;
    }

    /**
     * Must be redefined by subclasses.
     * 
     * @param currentNode is the current node
     * @param unseenNodes is the list of nodes we still have to visit.
     * @param cost        is the matrix of durations to go from i to j, with 0 <= i
     *                        < numberOfNodes and 0 <= j < numberOfNodes.
     * @param duration    is the array of durations to visit i node with 0 <= i <
     *                        numberOfNodes.
     * @return Integer, a lower bound of the permutations' cost, starting from
     *         currentNode, containing each unseen node exactly one time and
     *         finishing by 0 node.
     */
    protected abstract int bound(Integer currentNode, ArrayList<Integer> unseenNodes, double[][] cost, int[] duration);

    /**
     * Must be redefined by subclasses.
     * 
     * @param currentNode is the current node.
     * @param unseenNodes is the list of nodes we still have to visit.
     * @param cost        is the matrix of durations to go from i to j, with 0 <= i
     *                        < numberOfNodes and 0 <= j < numberOfNodes.
     * @param duration    is the array of durations to visit i node with 0 <= i <
     *                        numberOfNodes.
     * @return Iterator, an iterator to iterate on all unseen nodes.
     */
    protected abstract Iterator<Integer> iterator(Integer currentNode, ArrayList<Integer> unseenNodes, double[][] cost,
	    int[] duration);

    /**
     * Defines the template of a branch and bound resolution of the TSP.
     * 
     * @param currentNode  is the current node.
     * @param unseenNodes  is the list of nodes we still have to visit.
     * @param seenNodes    is the list of nodes we already have visited.
     * @param seenCost     is the sum of the arcs' cost of the path going through
     *                         all seen nodes, plus the duration of the seen nodes.
     * @param cost         is the matrix of durations to go from i to j, with 0 <= i
     *                         < numberOfNodes and 0 <= j < numberOfNodes.
     * @param duration     is the array of durations to visit i node with 0 <= i <
     *                         numberOfNodes.
     * @param startingTime is the moment when the resolution started.
     * @param timelimit    is the time limit for the resolution.
     */
    protected void branchAndBound(int currentNode, ArrayList<Integer> unseenNodes, ArrayList<Integer> seenNodes,
	    double seenCost, double[][] cost, int[] duration, long startingTime, int timelimit) {
	if (System.currentTimeMillis() - startingTime > timelimit) {
	    this.timelimitReached = true;
	    return;
	}
	if (unseenNodes.size() == 0) {
	    // All nodes have been visited
	    seenCost += cost[currentNode][0];
	    if (seenCost < this.bestSolutionCost) {
		// We found a better solution than bestSolution
		seenNodes.toArray(this.bestSolution);
		this.bestSolutionCost = seenCost;
	    }
	} else if (seenCost + bound(currentNode, unseenNodes, cost, duration) < this.bestSolutionCost) {
	    Iterator<Integer> it = iterator(currentNode, unseenNodes, cost, duration);
	    while (it.hasNext()) {
		Integer nextNode = it.next();
		seenNodes.add(nextNode);
		unseenNodes.remove(nextNode);
		branchAndBound(nextNode, unseenNodes, seenNodes,
			seenCost + cost[currentNode][nextNode] + duration[nextNode], cost, duration, startingTime,
			timelimit);
		seenNodes.remove(nextNode);
		unseenNodes.add(nextNode);
	    }
	}
    }
}
