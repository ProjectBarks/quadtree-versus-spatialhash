package org.dasd.ee;

import org.dasd.ee.algorithms.IntersectionAlgorithm;
import org.dasd.ee.algorithms.QuadTree;
import org.dasd.ee.algorithms.SpatialHash;
import org.dasd.ee.misc.ProgressBar;
import org.dasd.ee.misc.Util;
import org.dasd.ee.tests.Test;
import org.dasd.ee.tests.TestGroup;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * Written By: brandon on 10/11/16
 */
public class Benchmark {

	private static String algorithm;

	/*
	 * Based off the type selected a intersection algorithm will be tested
	 * @return Intersection Type @QuadTree or @SpatialHash
	 */
	public static IntersectionAlgorithm getAlgorithm(TestGroup group) {
		switch (algorithm) {
			case "SpatialHash":
				return new SpatialHash();
			case "QuadTree":
				int bound = group.getSettings().getBound();
				return new QuadTree(bound, bound);
			default:
				return null;
		}
		//return new QuadTree(Constants.bound, Constants.bound);
	}


	public static void main(String[] args) throws IOException {
		algorithm = Util.matchString("Algorithm", "SpatialHash", "QuadTree");
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		//Start a results file;
		File result = new File("resources/results-" + algorithm.toLowerCase() + ".csv");
		//Clear Old Results
		result.delete();
		//Get GSON data
		TestGroup group = Util.loadSettings();
		int rectSize = group.getSettings().getSize();
		Writer writer = new FileWriter(result);
		ProgressBar printer = new ProgressBar("Benchmarking", group.getTests().size());
		for (Test test : group.getTests()) {
			long totalRetrieveTime = 0, totalResults = 0, totalReal = 0, totalInsertTime = 0;
			for (Integer[] testPoint : test.getTestPoints()) {
				//Cleanup
				writer.flush();
				Rectangle testBox = new Rectangle(testPoint[0], testPoint[1], rectSize, rectSize);

				//Start Info
				long initialTime = System.nanoTime();

				//Test the algorithm
				IntersectionAlgorithm algorithm = getAlgorithm(group);
				for (Integer[] point : test.getPoints()) {
					assert algorithm != null;
					algorithm.insert(new Rectangle(point[0], point[1], rectSize, rectSize));
				}

				//Get the total insert time
				totalInsertTime += System.nanoTime() - initialTime;

				//reset the timer
				initialTime = System.nanoTime();
				//retrieve the algorithm
				List<Rectangle> results = algorithm.retrieve(testBox);

				//End Info
				totalRetrieveTime += System.nanoTime() - initialTime;
				totalResults += results.size();
				totalReal += results.stream().filter(box -> box.intersects(testBox)).count();
			}

			//Export Data and Report Info
			writer.write((totalInsertTime / test.getTestPoints().length) + ","
					+ (totalRetrieveTime / test.getTestPoints().length) + ","
					+ (totalResults / test.getTestPoints().length)
					+ "," + (totalResults == 0 ? 0 : ((double) totalReal) / ((double) totalResults))
					+ "\n");
			printer.tick();
		}

		System.out.println("Cleaning Up...");
		writer.flush();
		writer.close();
	}

}
