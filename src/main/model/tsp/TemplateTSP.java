package main.model.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public abstract class TemplateTSP implements TSP {

	private Integer[] bestSolution;
	private double bestSolutionCost = 0;
	private Boolean timelimitReached;

	public Boolean getTempsLimiteAtteint() {
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
	 * @param cost        : cost[i][j] = duration to go from i to j, with 0 <= i <
	 *                    numberOfNodes and 0 <= j < numberOfNodes.
	 * @param duration    : duration[i] = duration to visit i node with 0 <= i <
	 *                    numberOfNodes.
	 * @return a lower bound of the permutations' cost, starting from currentNode,
	 *         containing each unseen node exactly one time and finishing by 0 node.
	 */
	protected abstract int bound(Integer currentNode, ArrayList<Integer> unseenNodes, double[][] cost, int[] duration);

	/**
	 * Must be redefined by subclasses.
	 * 
	 * @param sommetCrt   : the current node.
	 * @param unseenNodes : table of nodes we still have to visit.
	 * @param cost        : cost[i][j] = duration to go from i to j, with 0 <= i <
	 *                    numberOfNodes and 0 <= j < numberOfNodes.
	 * @param duration    : duration[i] = duration to visit i node with 0 <= i <
	 *                    numberOfNodes.
	 * @return iterator to iterate on all unseen nodes.
	 */
	protected abstract Iterator<Integer> iterator(Integer sommetCrt, ArrayList<Integer> unseenNodes, double[][] cost,
			int[] duration);

	/**
	 * Defines the template of a branch and bound resolution of the TSP.
	 * 
	 * @param sommetCrt    the current node.
	 * @param unseenNodes  table of nodes we still have to visit.
	 * @param seenNodes    table of nodes we already have visited.
	 * @param seenCost     the sum of the arcs' cost of the path going through all
	 *                     seen nodes, plus the duration of the seen nodes.
	 * @param cost         : cost[i][j] = duration to go from i to j, with 0 <= i <
	 *                     numberOfNodes and 0 <= j < numberOfNodes.
	 * @param duration     : duration[i] = duration to visit i node with 0 <= i <
	 *                     numberOfNodes.
	 * @param startingTime : moment when resolution started.
	 * @param timelimit    : timelimit for the resolution.
	 */
	void branchAndBound(int sommetCrt, ArrayList<Integer> unseenNodes, ArrayList<Integer> seenNodes, double seenCost,
			double[][] cost, int[] duration, long startingTime, int timelimit) {
		if (System.currentTimeMillis() - startingTime > timelimit) {
			timelimitReached = true;
			return;
		}
		if (unseenNodes.size() == 0) { // all nodes have been visited
			seenCost += cost[sommetCrt][0];
			if (seenCost < bestSolutionCost) { // we found a better solution than bestSolution
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
						seenCost + cost[sommetCrt][nextNode] + duration[nextNode], cost, duration,
						startingTime, timelimit);
				seenNodes.remove(nextNode);
				unseenNodes.add(nextNode);
			}
		}
	}

	/**
	 * Helper method that takes everything needed and calculate the solution
	 */
	public void searchAndDisplayBestSolution(int maxTime, int numberOfNodes, double[][] graph, int[] duration) {
		searchSolution(maxTime, numberOfNodes, graph, duration);

		if (getTempsLimiteAtteint()) {
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
		for (i = 1; i < numberOfNodes; i++) {
			System.out.println("Etape " + i + " :");
			nextNode = getBestSolution(i);
			System.out.println("Aller de " + currentNode + " à " + nextNode + " (pour un coût de "
					+ graph[currentNode][nextNode] + ", et une attente de " + duration[nextNode]
					+ " à l'arrivee)");
			currentNode = nextNode;
			System.out.println();

		}
		System.out.println("Etape " + i + " (finale : retour au depot) :");
		System.out.println("Aller de " + currentNode + " à " + initialNode + " (pour un coût de "
				+ graph[currentNode][initialNode] + ")");
	}

}
