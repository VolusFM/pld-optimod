package main.model.tsp;

import java.util.ArrayList;
import java.util.Iterator;

public class TSP1 extends TemplateTSP {

	@Override
	protected Iterator<Integer> iterator(Integer currentNode, ArrayList<Integer> unseenNodes, double[][] costMatrix, int[] duration) {
		return new IteratorSeq(unseenNodes, currentNode);
	}

	@Override
	protected int bound(Integer currentNode, ArrayList<Integer> unseenNodes, double[][] costMatrix, int[] duration) {
		return 0;
	}
}
