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
		root = insert(root, interval);
		
	}
	
	private IntervalNode<T> insert(IntervalNode<T> node, IntervalADT<T> interval)
					throws IllegalArgumentException	{
		//if node is null, we have an empty tree
		if (node == null)
			return new IntervalNode<T>(interval);
		
		//no duplicates allowed
		if (interval.compareTo(node.getInterval()) == 0)
			throw new IllegalArgumentException();
		
		if (interval.compareTo(node.getInterval()) < 0){
			//add key to the left subtree
			node.setLeftNode(insert(node.getLeftNode(),interval));
			return node;
		}
		
		else {
			//add key to the right subtree
			node.setRightNode(insert(node.getRightNode(),interval));
			return node;
		}
		//TODO - Check and possibly update maxEnd
		
	}

	@Override
	public void delete(IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException {
		// TODO Auto-generated method stub
		root = deleteHelper(root, interval);
	}

	@Override
	public IntervalNode<T> deleteHelper(IntervalNode<T> node,
					IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException {
		// TODO Auto-generated method stub
		if (node == null){
			throw new IntervalNotFoundException(interval.toString());
		}
		if (interval.equals(node.getInterval())){
			//node is to be removed
			//code to be added here - Sid
			//two cases: (1)right child exists (2) right child does not exist
			//see IntervalTreeADT method header for notes
			if (node.getRightNode() == null){
				return node.getLeftNode();
			}
			else {
				node.setInterval(node.getSuccessor().getInterval());
				deleteHelper(node.getSuccessor(),interval);
				//if right subtree is empty, current node's interval's max is the maxEnd
				if (node.getRightNode() == null){
					node.setMaxEnd(node.getInterval().getEnd());
				}
				//otherwise, the right subtree contains the maxEnd
				else{
					node.setMaxEnd(node.getRightNode().getMaxEnd()); //may need null check in case right subtree is null
				}
				return node;
			}
		}
		else if (interval.compareTo(node.getInterval()) < 0){
			node.setLeftNode(deleteHelper(node.getLeftNode(),interval));
			return node;
		}
		else {
			node.setRightNode(deleteHelper(node.getRightNode(),interval));
	        return node;
		}
	}
	
	private T recalculateMaxEnd(IntervalNode<T> nodeToRecalculate){
		
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
