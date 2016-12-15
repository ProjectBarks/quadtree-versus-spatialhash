package org.dasd.ee;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.dasd.ee.misc.ProgressBar;
import org.dasd.ee.tests.Test;
import org.dasd.ee.tests.TestGroup;
import org.dasd.ee.tests.TestSettings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

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
 * Written By: brandon on 10/10/16
 */
public class DataGeneration {

	private static final TestGroup group = new TestGroup();
	private static final TestSettings settings = group.getSettings();

	private static Random random;

	private static int getNextInt() {
		return random.nextInt(settings.getBound() - settings.getSize() + 1);
	}

	private static Integer[][] genBoundingBoxes(int size) {
		random = new SecureRandom();
		return IntStream.range(0, size).mapToObj(value -> new Integer[]{getNextInt(), getNextInt()}).toArray(Integer[][]::new);
	}


	public static void main(String[] args) throws IOException, InterruptedException {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Total Test Samples: ");
		settings.setTotalSamples(scanner.nextInt());
		System.out.print("Tests per Sample: ");
		settings.setTestsPerSample(scanner.nextInt());
		System.out.print("Test Area Size: ");
		settings.setBound(scanner.nextInt());
		System.out.print("Object Size: ");
		settings.setSize(scanner.nextInt());
		System.out.println();

		int totalTests = new File("resources").list((dir, name) -> name.contains(".json")).length;
		File outputFile = new File("resources/testcase-" + totalTests + ".json");
		Writer writer = new FileWriter(outputFile);
		Gson gson = new GsonBuilder().create();
		ProgressBar progressBar = new ProgressBar("Generating", settings.getTotalSamples());
		for (int i = 0; i < settings.getTotalSamples(); i++) {
			Test testCase = new Test();
			testCase.setLevel(i);
			testCase.setPoints(genBoundingBoxes(i));
			testCase.setTestPoints(genBoundingBoxes(settings.getTestsPerSample()));
			group.addTest(testCase);
			progressBar.update(i + 1);
		}
		System.out.println("Saving File...");
		gson.toJson(group, writer);
		writer.close();
		System.out.println("Saved!");
	}
}
