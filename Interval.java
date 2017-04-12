/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016
// PROJECT:          p4
// FILE:             Interval.java
//
// TEAM:    Team 17
// Authors:
// Author1: (Vanessa Chavez, vchavez2@wisc.edu, chavez, 001)
// Author2: (Sidney Smith, sbsmith5@wisc.edu, sbsmith5, 001)
// Author3: ()
//
//
//////////////////////////// 80 columns wide //////////////////////////////////
/**
 * This class implements IntervalADT.java methods to be used in IntervalTreeMain.java class.
 */
public class Interval<T extends Comparable<T>> implements IntervalADT<T> {

	private T start; //start of date interval
	private T end; //end of date interval
	private String label; //name of project/schedule

	/**
	 * This method initializes the starting global variables.
	 */
	public Interval(T start, T end, String label) {
		// Initialize data members
		this.start = start;
		this.end = end;
		this.label = label;
	}

	/**
	 * This method returns the start value of the interval.
	 */
	public T getStart() {
		return this.start;
	}

	/**
	 * This method returns the end value of the interval.
	 */
	public T getEnd() {
		return this.end;

	}

	/**
	 * This method returns the project/schedule name of the interval.
	 */
	public String getLabel() {
		return this.label;

	}

	/**
	 * This method checks whether or not the intervals overlap with each other.
	 * @param other - IntervalADT of type T, used to be compared with current interval
	 * @return true - if intervals do overlap
	 * @return false - if intervals do not overlap
	 */
	public boolean overlaps(IntervalADT<T> other) {
		//FROM JAVADOCS, THIS IS WHAT METHOD IS SUPPOSED TO DO:
		//Return true if this interval overlaps with the other interval.
		//Note: two intervals [a, b], [c, d] will NOT overlap if either b < c or d < a.
		//
		//In all other cases, they will overlap.

		//check if they don't overlap
		if (this.getEnd().compareTo(other.getStart()) < 0 || this.getStart().compareTo(other.getEnd()) > 0) {
			return false;
		}

		return true;
	}

	/**
	 * This method checks whether or not a point is contained in the current interval.
	 * @point - of type T, the point passed in to see if it is contained in the interval.
	 * @return true - if it is in the interval
	 * @return false - if it is not in the interval
	 */
	public boolean contains(T point) {
		//checks if point is larger than or equal to start and if point is smaller than or equal to end
		return this.getStart().compareTo(point) <= 0 && this.getEnd().compareTo(point) >= 0;
	}

	/**
	 * This method compares the values of the two intervals. If starting value is the same, end value is then compared.
	 * @param other - an IntervalADT of type T, the interval to be used to compare to current
	 * @return -1 - if this value comes before other value
	 * @return 1 - if this value comes after other value
	 */
	public int compareTo(IntervalADT<T> other) {
		//NOTES FROM JAVADOCS, CHECK IF METHOD DOES THIS:
		//    	Compares this interval with the other and return a negative value
		//    			if this interval comes before the "other" interval. Intervals
		//    			are compared first on their start time. The end time is only
		//    			considered if the start time is the same.
		//  	[0,1] compared to [2,3]: returns -1 because 0 is before 2

		if (this.getStart().compareTo(other.getStart()) == 0) { //if the same beginning interval
			//compare end intervals
			if(this.getEnd().compareTo(other.getEnd()) < 0) {
				return -1;

			}
			else if (this.getEnd().compareTo(other.getEnd()) > 0) {
				return 1;
			}
		}

		if (this.getStart().compareTo(other.getStart()) < 0) { //this comes before other
			return -1;
		}

		return 1; //this comes after other

	}
}
