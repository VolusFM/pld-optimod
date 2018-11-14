package main.model.tsp;

import java.util.Collection;
import java.util.Iterator;

/**
 * {@link IteratorSeq} provides an iterator on the unseenNodes of a graph.
 */
public class IteratorSeq implements Iterator<Integer> {

    private Integer[] candidates;
    private int numberOfCandidates;

    /**
     * Create an iterator to iterate on unseenNodes.
     * 
     * @param unseenNodes is a collection of unseen nodes' ids.
     * @param currentNode is the current node.
     */
    public IteratorSeq(Collection<Integer> unseenNodes, int currentNode) {
	this.candidates = new Integer[unseenNodes.size()];
	this.numberOfCandidates = 0;
	for (Integer s : unseenNodes) {
	    this.candidates[this.numberOfCandidates++] = s;
	}
    }

    @Override
    public boolean hasNext() {
	return this.numberOfCandidates > 0;
    }

    @Override
    public Integer next() {
	return this.candidates[--this.numberOfCandidates];
    }

    @Override
    public void remove() {
    }

}
