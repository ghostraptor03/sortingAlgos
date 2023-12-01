package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.lang.IllegalArgumentException;
import java.util.InputMismatchException;

/**
 *  
 * @author Zach
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.
 *
 */

public class MergeSorter extends AbstractSorter {
	// Other private instance variables if needed

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts input array of integers
	 */
	public MergeSorter(Point[] pts) {
		super(pts);
		this.algorithm = "Merge Sort";
	}

	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter.
	 * 
	 */
	@Override
	public void sort() {
		mergeSortRec(points);
	}

	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of
	 * points. One way is to make copies of the two halves of pts[], recursively
	 * call mergeSort on them, and merge the two sorted subarrays into pts[].
	 * 
	 * @param pts point array
	 */
	private void mergeSortRec(Point[] pts) {
		int n = pts.length;
		if (n <= 1) {
			return; // Base case
		}

		// Split the array into two halves
		int mid = n / 2;
		Point[] left = new Point[mid];
		Point[] right = new Point[n - mid];

		for (int i = 0; i < mid; i++) {
			left[i] = pts[i];
		}

		for (int i = mid; i < n; i++) {
			right[i - mid] = pts[i];
		}

		// Recursively sort the two halves
		mergeSortRec(left);
		mergeSortRec(right);
		int i = 0, j = 0, k = 0;
		while (i < left.length && j < right.length) {
			if (left[i].compareTo(right[j]) <= 0) {
				pts[k++] = left[i++];
			} else {
				pts[k++] = right[j++];
			}
		}

		// Copy any remaining elements from left and right 
		while (i < left.length) {
			pts[k++] = left[i++];
		}

		while (j < right.length) {
			pts[k++] = right[j++];
		}
	}

}

