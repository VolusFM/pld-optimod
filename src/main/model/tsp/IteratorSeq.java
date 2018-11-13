package main.model.tsp;

import java.util.Collection;
import java.util.Iterator;

/**
 * {@link IteratorSeq} represents an iterator on the unseenNodes of a graph
 */
public class IteratorSeq implements Iterator<Integer> {

    private Integer[] candidates;
    private int numberOfCandidates;

    /**
     * Create an iterator to iterate on unseenNodes
     * 
     * @param unseenNodes
     * @param currentNode
     */
    public IteratorSeq(Collection<Integer> unseenNodes, int currentNode) {
	// FIXME why is currentNode here ?
	this.candidates = new Integer[unseenNodes.size()];
	numberOfCandidates = 0;
	for (Integer s : unseenNodes) {
	    candidates[numberOfCandidates++] = s;
	}
    }

    @Override
    public boolean hasNext() {
	return numberOfCandidates > 0;
    }

    @Override
    public Integer next() {
	return candidates[--numberOfCandidates];
    }

    @Override
    public void remove() {
    }

}
