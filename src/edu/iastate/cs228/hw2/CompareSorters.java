package edu.iastate.cs228.hw2;

import java.io.File;

/**
 *  
 * @author Zach
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;

public class CompareSorters {
	/**
	 * Repeatedly take integer sequences either randomly generated or read from
	 * files. Use them as coordinates to construct points. Scan these points with
	 * respect to their median coordinate point four times, each time using a
	 * different sorting algorithm.
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException {

		Scanner scanner = new Scanner(System.in);
		Random rand = new Random();

		int round = 1;
		while (true) {
			System.out.println("Performances of Four Sorting Algorithms in Point Scanning");
			System.out.println("keys: 1 (random integers) 2 (file input) 3 (exit)");
			System.out.println("Trial " + round + ":");
			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();

			if (choice == 3) {
				break;
			}

			Point[] points;

			if (choice == 1) {
				System.out.print("Enter number of random points: ");
				int numPoints = scanner.nextInt();
				points = generateRandomPoints(numPoints, rand);
			} else if (choice == 2) {
				System.out.print("Points from a file\nFile name: ");
				String fileName = scanner.next();
				points = PointScanner.readPointsFromFile(fileName);
			} else {
				System.out.println("Invalid choice. Please enter 1 (random integers), 2 (file input), or 3 (exit).");
				continue;
			}

			System.out.println("algorithm size time (ns)");
			System.out.println("----------------------------------");

			for (Algorithm algo : Algorithm.values()) {
				PointScanner pointScanner = new PointScanner(points.clone(), algo);
				pointScanner.scan();
				String stats = pointScanner.stats();
				System.out.println(stats);
			}

			round++;
		}

		scanner.close();
	}

	/**
	 * This method generates a given number of random points. The coordinates of
	 * these points are pseudo-random numbers within the range [-50,50] � [-50,50].
	 * Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing.
	 * 
	 * @param numPts number of points
	 * @param rand   Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException {
		if (numPts < 1) {
			throw new IllegalArgumentException("Number of points must be at least 1.");
		}

		Point[] points = new Point[numPts];
		for (int i = 0; i < numPts; i++) {
			int x = rand.nextInt(101) - 50; 
			int y = rand.nextInt(101) - 50; 
			points[i] = new Point(x, y);
		}

		return points;
	}

}
