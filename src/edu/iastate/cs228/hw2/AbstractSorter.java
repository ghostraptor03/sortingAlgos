package edu.iastate.cs228.hw2;

/**
 *  
 * @author Zach
 *
 */

import java.util.Comparator;
import java.io.FileNotFoundException;
import java.lang.IllegalArgumentException;
import java.util.InputMismatchException;

/**
 * 
 * This abstract class is extended by SelectionSort, InsertionSort, MergeSort,
 * and QuickSort. It stores the input (later the sorted) sequence.
 *
 */
public abstract class AbstractSorter {

	protected Point[] points; // array of points operated on by a sorting algorithm.
								// stores ordered points after a call to sort().

	protected String algorithm = null; // "selection sort", "insertion sort", "mergesort", or
										// "quicksort". Initialized by a subclass constructor.

	protected Comparator<Point> pointComparator = null;

	// Add other protected or private instance variables you may need.

	/**
	 * This constructor accepts an array of points as input. Copy the points into
	 * the array points[].
	 * 
	 * @param pts input array of points
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	protected AbstractSorter(Point[] pts) throws IllegalArgumentException {
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException("Input array is null or empty.");
		}

		// Copy the points into the array 
		this.points = new Point[pts.length];
		for (int i = 0; i < pts.length; i++) {
			if (pts[i] != null) {
				this.points[i] = new Point(pts[i].getX(), pts[i].getY());
			} else {
				throw new IllegalArgumentException("Input array contains null elements.");
			}
		}
	}

	/**
	 * Generates a comparator on the fly that compares by x-coordinate if order ==
	 * 0, by y-coordinate if order == 1. Assign the comparator to the variable
	 * pointComparator.
	 * 
	 * 
	 * @param order 0 by x-coordinate 1 by y-coordinate
	 * 
	 * 
	 * @throws IllegalArgumentException if order is less than 0 or greater than 1
	 * 
	 */
	public void setComparator(int order) throws IllegalArgumentException {

		if (order < 0 || order > 1) {
			throw new IllegalArgumentException("Order must be 0 or 1.");
		}

		// Generate a comparator
		pointComparator = (p1, p2) -> {
			if (order == 0) {
				// Compare by x-coordinate
				return Integer.compare(p1.getX(), p2.getX());
			} else {
				// Compare by y-coordinate
				return Integer.compare(p1.getY(), p2.getY());
			}
		};

	}

	/**
	 * Use the created pointComparator to conduct sorting.
	 * 
	 * Should be protected. Made public for testing.
	 */
	public abstract void sort();

	/**
	 * Obtain the point in the array points[] that has median index
	 * 
	 * @return median point
	 */
	public Point getMedian() {
		return points[points.length / 2];
	}

	/**
	 * Copys the array points[] onto the array pts[].
	 * 
	 * @param pts
	 */
	public void getPoints(Point[] pts) {
		if (pts.length >= points.length) {
			for (int i = 0; i < points.length; i++) {
				pts[i] = points[i];
			}
		} else {
			throw new IllegalArgumentException("Destination array 'pts' is too small to hold all points.");
		}
	}

	/**
	 * Swaps the two elements indexed at i and j respectively in the array points[].
	 * 
	 * @param i
	 * @param j
	 */
	protected void swap(int i, int j) {
		if (points != null && i >= 0 && i < points.length && j >= 0 && j < points.length) {
			Point thing = points[i];
			points[i] = points[j];
			points[j] = thing;
		}
	}
}
