/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016
// PROJECT:          p4
// FILE:             Interval.java
//
// TEAM:    Team 17
// Authors:
// Author1: (Sidney Smith, sbsmith5@wisc.edu, sbsmith5, 001)
// Author2: (Aleysha Becker, ambecker5@wisc.edu, aleysha, 001)
// Author3: (Vanessa Chavez, vchavez2@wisc.edu, chavez, 001)
// Author3: (Roberto O'Dogherty, rodogherty@wisc.edu, o-dogherty, 001)
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.List;
import java.util.ArrayList;

public class IntervalTree<T extends Comparable<T>> implements IntervalTreeADT<T> {

	// TODO declare any data members needed for this class
	private IntervalNode<T> root;

	/**
	 * Constructor to create a new IntervalTree. Set the root of the tree to null, to create the tree.
	 */
	public IntervalTree() {
		root = null;
	}

	@Override
	public IntervalNode<T> getRoot() {
		return root;
	}

	@Override
	public void insert(IntervalADT<T> interval)
					throws IllegalArgumentException {
		root = insert(root, interval);
	}

	/*
	 * Inserts IntervalNode into the tree  
	 * @throws IllegalArgumentException
	 * @param intervalNode<T> node to add to tree
	 * @param IntervalADT<T> interval of class
	 * 
	 */
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
			//Check and possibly update maxEnd - Done (Sid) THIS MAY BE WRONG!
			node.setMaxEnd(recalculateMaxEnd(node));
			return node;
		}

		else {
			//add key to the right subtree
			node.setRightNode(insert(node.getRightNode(),interval));
			//Check and possibly update maxEnd - Done (Sid) THIS MAY BE WRONG!
			node.setMaxEnd(recalculateMaxEnd(node));
			return node;
		}
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
				//if right subtree is empty, the end of the interval of the current node is the maxEnd
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
			//update the maxEnd if necessary. This may be incorrect!!
			node.setMaxEnd(recalculateMaxEnd(node));
			return node;
		}
		else {
			node.setRightNode(deleteHelper(node.getRightNode(),interval));
			node.setMaxEnd(recalculateMaxEnd(node));
	        return node;
		}
	}

	private T recalculateMaxEnd(IntervalNode<T> nodeToRecalculate){
		//if there is no right child, nodeToRecalculate's interval contains the maxEnd
		if (nodeToRecalculate.getRightNode() == null){
			return nodeToRecalculate.getInterval().getEnd();
		}
		//otherwise, the right subtree of nodeToRecalculate contains the maxEnd
		else {
			return nodeToRecalculate.getRightNode().getMaxEnd();
		}
	}

	@Override
	public List<IntervalADT<T>> findOverlapping(IntervalADT<T> interval) {
		// TODO Auto-generated method stub - Done? (Aleysha)
		ArrayList<IntervalADT<T>> result = new ArrayList<IntervalADT<T>>();
		findOverlappingHelper(root, interval, result);
		return result;
	}

	private void findOverlappingHelper(IntervalNode<T> node, IntervalADT<T> interval, ArrayList<IntervalADT<T>> result) {
		if (node == null) return;
		// if (start1 < end2 and > start2) or (end1 > start2 and < end1) it overlaps
		if ((node.getInterval().getStart().compareTo(interval.getEnd()) <= 1
				&& node.getInterval().getStart().compareTo(interval.getStart()) >= 1)
				|| (node.getInterval().getEnd().compareTo(interval.getStart()) >= 1
						&& node.getInterval().getEnd().compareTo(interval.getEnd()) <= 1)){
			result.add(node.getInterval());
		}
		if (node.getLeftNode().getMaxEnd().compareTo(interval.getStart()) >= 1) {
			findOverlappingHelper(node.getLeftNode(), interval, result);
		}
		else if (node.getRightNode().getMaxEnd().compareTo(interval.getStart()) >= 1) {
			findOverlappingHelper(node.getLeftNode(), interval, result);
		}
	}

	@Override
	public List<IntervalADT<T>> searchPoint(T point) {
		// TODO Auto-generated method stub - Done? (Aleysha)
		ArrayList<IntervalADT<T>> result = new ArrayList<IntervalADT<T>>();
		searchPointHelper(root, point, result);
		return result;
	}

	private void searchPointHelper(IntervalNode<T> node, T point, ArrayList result) {
		if (node == null) return;
		// if (point >= start and <= end), it's in the interval
		if (node.getInterval().getStart().compareTo(point) <= 1
			&& node.getInterval().getEnd().compareTo(point) >= 1) {
			result.add(node);
			}
		if (node.getLeftNode().getMaxEnd().compareTo(point) >= 1) {
			searchPointHelper(node.getLeftNode(), point, result);
		}
		else if (node.getRightNode().getMaxEnd().compareTo(point) >= 1) {
			searchPointHelper(node.getRightNode(), point, result);
		}
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub - Done? (Aleysha)
		return getSizeHelper(root);
	}

	private int getSizeHelper(IntervalNode<T> node) {
		if (node == null) return 0;
		// include current node, left subtree, and right subtree in the count
		return 1 + getSizeHelper(node.getLeftNode()) + getSizeHelper(node.getRightNode());
	}

	//TODO only done if we can add methods to IntervalNode -Roberto
	@Override
	public int getHeight() {
		int toReturn = 0;
		
		if (root == null){
			return toReturn;
		}
		
		toReturn = root.getHeight();
				
		return toReturn;

	}

	//TODO only done if we can add methods to IntervalNode -Roberto
	@Override
	public boolean contains(IntervalADT<T> interval) {
		
		if(root == null){
			return false;
		}
		
		return root.contains(interval);
	}

	@Override
	public void printStats() {
		System.out.println("-----------------------------------------");
		System.out.println("Height: " + getHeight() );
		System.out.println("Size: " + getSize() );
		System.out.println("-----------------------------------------");

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
