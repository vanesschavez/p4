/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016
// PROJECT:          p4
// FILE:             Interval.java
//
// TEAM:    Team 17
// Authors:
// Author1: (Sidney Smith, sbsmith5@wisc.edu, sbsmith5, 001)
// Author2: ()
//
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.List;

public class IntervalTree<T extends Comparable<T>> implements IntervalTreeADT<T> {
	
	// TODO declare any data members needed for this class
	private IntervalNode<T> root;
	
	/**
	 * Constructor to create a new IntervalTree. Set the root of the tree to null, to create the tree.
	 */
	public IntervalTree() {root = null;}

	@Override
	public IntervalNode<T> getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(IntervalADT<T> interval)
					throws IllegalArgumentException {
		
		//if the tree is empty, insert the interval at the root
		if (root == null) root.setInterval(interval);
		//if this is a duplicate interval, throw an exception
		else if (root.getInterval() == interval) throw new IllegalArgumentException();
		//else, insert the node using the insertHelper() method
		//(TODO! - Sid)
		insertHelper(root, interval).setInterval(interval);
		
	}
	
	public IntervalNode<T> insertHelper(IntervalNode<T> node, IntervalADT<T> interval){
		//(TODO - Sid)
		if (node == null) return node;
	}

	@Override
	public void delete(IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException {
		// TODO Auto-generated method stub
		if (root == null) return;
		
	}

	@Override
	public IntervalNode<T> deleteHelper(IntervalNode<T> node,
					IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException {
		// TODO Auto-generated method stub
	}

	@Override
	public List<IntervalADT<T>> findOverlapping(
					IntervalADT<T> interval) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<IntervalADT<T>> searchPoint(T point) {
		// TODO Auto-generated method stub
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean contains(IntervalADT<T> interval) {
		// TODO Auto-generated method stub
	}

	@Override
	public void printStats() {
		// TODO Auto-generated method stub

	}
	
	private boolean lookup(IntervalNode<T> node, IntervalADT<T> interval){
		//I added this method (Sid). See pages 3-6 in week 10 lecture notes as to why.
		
		if (node == null) return false;
		else if (node.getInterval() == interval) return true;
		//unsure if using "interval" as the second parameter passed to the recursive "lookup" call (below) is correct - Sid
		else if (node.getInterval().compareTo(interval) < 0) return lookup(node.getLeftNode(),interval); 
		else return lookup(node.getRightNode(),interval);
	}

}
