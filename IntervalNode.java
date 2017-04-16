/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016
// PROJECT:          p4
// FILE:             Interval.java
//
// TEAM:    Team 17
// Authors:
// Author1: (Sidney Smith, sbsmith5@wisc.edu, sbsmith5, 001)
// Author2: (Roberto O'Dogherty, rodogherty@wisc.edu, o-dogherty, 001)
//
//
//////////////////////////// 80 columns wide //////////////////////////////////
/**
 * This class defines the IntervalNode for the IntervalTree. This node has three
 * components: 1) interval - the data that we want to store in this node 2)
 * maxEnd - this represents the maximum end of any interval stored in the tree
 * rooted at this node 3) leftNode and rightNode - the left and right node
 * references in the IntervalTree.
 * 
 * This class will be used while constructing the IntervalTree.
 *
 * @param <T>
 *            the template parameter for the data field - interval.
 */

public class IntervalNode<T extends Comparable<T>> {
	// Interval stored in the node.
	private IntervalADT<T> interval;

	// Each node stores the maxEnd of the interval in its subtree.
	private T maxEnd;

	// LeftNode and RightNode.
	private IntervalNode<T> leftNode, rightNode;

	/**
	 * Constructor to create a new IntervalNode. Set the interval data structure
	 * present as member variable above and maxEnd associated with the interval.
	 * Hint: Use interval.getEnd() to get the end of the interval. Note: In your
	 * intervalTree, this will get updated subsequently.
	 * 
	 * @param interval
	 *            the interval data member.
	 */
	public IntervalNode(IntervalADT<T> interval) {
		// TODO - I THINK THIS IS DONE (Sid)
		this.interval = interval;
		this.maxEnd = interval.getEnd();
		this.leftNode = null;
		this.rightNode = null;
	}

	/**
	 * Returns the next in-order successor of the BST. Hint: Return left-most
	 * node in the right subtree. Return null if there is no rightNode.
	 *
	 * @return in-order successor node
	 */
	public IntervalNode<T> getSuccessor() {
		// TODO - Done (Sid)
		if (this.rightNode == null){
			return null;
		}
		else {
			return getSuccessorHelper(this.rightNode);
		}
	}
	
	//Sid added. Referenced https://piazza.com/class/ixvb3zbhyqd29e?cid=467 for help
	private IntervalNode<T> getSuccessorHelper(IntervalNode<T> node) {
		if (node.getLeftNode() == null){
			return node;
		}
		else {
			return getSuccessorHelper(node.getLeftNode());
		}
	}

	/**
	 * Returns the interval associated with the node.
	 * 
	 * @return the interval data field.
	 */
	public IntervalADT<T> getInterval() {
		return interval;
	}

	/**
	 * Setter for the interval.
	 * 
	 * @param interval
	 *            the interval to be set at this node.
	 */
	public void setInterval(IntervalADT<T> interval) {
		this.interval = interval;
	}

	/**
	 * Setter for the maxEnd. This represents the maximum end point associated
	 * in any interval stored at the subtree rooted at this node (inclusive of
	 * this node).
	 * 
	 * @param maxEnd
	 *            the maxEnd associated with this node.
	 *
	 */
	public void setMaxEnd(T maxEnd) {
		this.maxEnd = maxEnd;
	}

	/**
	 * Getter for the maxEnd member variable.
	 * 
	 * @return the maxEnd.
	 */
	public T getMaxEnd() {
		return maxEnd;
	}

	/**
	 * Getter for the leftNode reference.
	 *
	 * @return the reference of leftNode.
	 */
	public IntervalNode<T> getLeftNode() {
		return leftNode;
	}

	/**
	 * Setter for the leftNode of this node.
	 * 
	 * @param leftNode
	 *            the left node.
	 */
	public void setLeftNode(IntervalNode<T> leftNode) {
		this.leftNode = leftNode;
	}

	/**
	 * Getter for the rightNode of this node.
	 * 
	 * @return the rightNode.
	 */
	public IntervalNode<T> getRightNode() {
		return rightNode;
	}

	/**
	 * Setter for the rightNode of this node.
	 * 
	 * @param rightNode
	 *            the rightNode of this node.
	 */
	public void setRightNode(IntervalNode<T> rightNode) {
		this.rightNode = rightNode;
	}
	
	/*
	 * TODO Roberto added this method because he thinks 
	 * we need it to check height from IntervalTree class
	 * returns height of tree
	 */
	public int getHeight(){
		int toReturn = 1; //because this nodes height
		int heightRight = 0;	//right height 
		int heightLeft = 0;		//left height
		
		if( this.leftNode != null){
			heightLeft += this.getLeftNode().getHeight();
		}
		
		if( this.rightNode != null){
			heightRight += this.getRightNode().getHeight();
		}
		
		//check which height is larger and add that to out height of 1
		if(heightRight > heightLeft){
			toReturn += heightRight;
		}else{
			toReturn +=heightLeft;
		}
				
		return toReturn;
	}

	/*
	 * TODO Roberto added this method because he thinks 
	 * we need it to check if it contains an interval from IntervalTree class
	 * returns if tree contains interval
	 * @param interval we're checking
	 */
	public boolean contains(IntervalADT<T> intervalToCheck) {
		
		if(this.getInterval() == intervalToCheck){
			return true;
		}
		
		if(intervalToCheck.compareTo(this.getInterval()) > 0){
			if(this.rightNode != null){
				return this.getRightNode().contains(intervalToCheck);
			}
			return false;
		}else{
			if(this.leftNode != null){
				return this.getLeftNode().contains(intervalToCheck);
			}
			return false;
		}
	}
	
}
