package edu.iastate.cs228.hw2;

import java.io.File;

/**
 * 
 * @author Zach
 *
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * This class sorts all the points in an array of 2D points to determine a
 * reference point whose x and y coordinates are respectively the medians of the
 * x and y coordinates of the original points.
 * 
 * It records the employed sorting algorithm as well as the sorting time for
 * comparison.
 *
 */
public class PointScanner {
	private String outputFileName;
	private Point[] points;

	private Point medianCoordinatePoint; // point whose x and y coordinates are respectively the medians of
											// the x coordinates and y coordinates of those points in the array
											// points[].
	private Algorithm sortingAlgorithm;

	protected long scanTime; // execution time in nanoseconds.

	/**
	 * This constructor accepts an array of points and one of the four sorting
	 * algorithms as input. Copy the points into the array points[].
	 * 
	 * @param pts input array of points
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException {
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
		sortingAlgorithm = algo;
	}

	/**
	 * This constructor reads points from a file.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException if the input file contains an odd number of
	 *                                integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo)
			throws FileNotFoundException, InputMismatchException, IllegalArgumentException {
		if (algo == null) {
			throw new IllegalArgumentException("Sorting algorithm cannot be null.");
		}

		points = readPointsFromFile(inputFileName);
		sortingAlgorithm = algo;
	}

// helper method used in point scanner to help read the points from a file
	static Point[] readPointsFromFile(String inputFileName) throws FileNotFoundException, InputMismatchException {
		List<Point> pointList = new ArrayList<>();
		try (Scanner scanner = new Scanner(new File(inputFileName))) {
			while (scanner.hasNextInt()) {
				int x = scanner.nextInt();
				if (scanner.hasNextInt()) {
					int y = scanner.nextInt();
					pointList.add(new Point(x, y));
				} else {
					throw new InputMismatchException("Input file contains an odd number of integers.");
				}
			}
		}
		return pointList.toArray(new Point[0]);
	}

	/**
	 * Carry out two rounds of sorting using the algorithm designated by
	 * sortingAlgorithm as follows:
	 * 
	 * a) Sort points[] by the x-coordinate to get the median x-coordinate. b) Sort
	 * points[] again by the y-coordinate to get the median y-coordinate. c)
	 * Construct medianCoordinatePoint using the obtained median x- and
	 * y-coordinates.
	 * 
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter,
	 * InsertionSorter, MergeSorter, or QuickSorter to carry out sorting.
	 * 
	 * @param algo
	 * @return
	 */
	public void scan() {
		AbstractSorter aSorter;
		if (points == null || points.length == 0) {
			throw new IllegalStateException("No points to scan.");
		}

		switch (sortingAlgorithm) {
		case SelectionSort:
			aSorter = new SelectionSorter(points);
			break;
		case InsertionSort:
			aSorter = new InsertionSorter(points);
			break;
		case MergeSort:
			aSorter = new MergeSorter(points);
			break;
		case QuickSort:
			aSorter = new QuickSorter(points);
			break;
		default:
			throw new IllegalArgumentException("Invalid sorting algorithm.");

		}
		aSorter.sort();
		int medianX = points[points.length / 2].getX();

		// Sort by y-coordinate
		aSorter.setComparator(1); // Set the comparator
		aSorter.sort();
		int medianY = points[points.length / 2].getY();

		medianCoordinatePoint = new Point(medianX, medianY);
	}

	/**
	 * Outputs performance statistics in the format:
	 * 
	 * <sorting algorithm> <size> <time>
	 * 
	 * For instance,
	 * 
	 * selection sort 1000 9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description.
	 */
	public String stats() {
		if (sortingAlgorithm == null || points == null) {
			throw new IllegalStateException("Sorting algorithm or points array is not initialized.");
		}

		int size = points.length;

		// Start the timer
		long startTime = System.nanoTime();

		// Perform sorting
		scan();

		long endTime = System.nanoTime();
		long elapsedTime = endTime - startTime;

		String performanceStats = sortingAlgorithm.toString().toLowerCase() + " " + size + " " + elapsedTime;

		return performanceStats;
	}

	/**
	 * Write MCP after a call to scan(), in the format "MCP: (x, y)" The x and y
	 * coordinates of the point are displayed on the same line with exactly one
	 * blank space in between.
	 */
	@Override
	public String toString() {
		if (medianCoordinatePoint == null) {
			return "MCP: (N/A, N/A)";
		} else {
			return "MCP: (" + medianCoordinatePoint.getX() + ", " + medianCoordinatePoint.getY() + ")";
		}
	}

	/**
	 * 
	 * This method, called after scanning, writes point data into a file by
	 * outputFileName. The format of data in the file is the same as printed out
	 * from toString(). The file can help you verify the full correctness of a
	 * sorting result and debug the underlying algorithm.
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile(String outputFileName) throws FileNotFoundException {
		try (PrintWriter writer = new PrintWriter(outputFileName)) {
			if (medianCoordinatePoint == null) {
				writer.println("MCP: (N/A, N/A)");
			} else {
				writer.println("MCP: (" + medianCoordinatePoint.getX() + ", " + medianCoordinatePoint.getY() + ")");
			}
		}
	}

}
