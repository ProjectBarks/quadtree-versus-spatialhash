package org.dasd.ee.misc;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.dasd.ee.tests.TestGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
 * Written By: brandon on 10/17/16
 */
public class Util {

	public static String matchString(String prompt, String... options) {
		System.out.println("Options: " + String.join(", ", options));
		String chosen = null;
		Scanner scanner = new Scanner(System.in);
		while (chosen == null) {
			System.out.print(prompt + ": ");
			String test = scanner.nextLine();
			Optional<String> result = Stream.of(options).filter(x -> x.toLowerCase().equals(test.toLowerCase())).findFirst();
			if (result.isPresent()) {
				chosen = result.get();
			} else {
				System.out.println("Invalid Input!");
			}
		}
		System.out.println("Chosen: " + chosen + "\n");
		return chosen;
	}

	public static TestGroup loadSettings() {
		Gson gson = new Gson();
		File resources = new File("./resources/");
		if (!resources.exists()) {
			resources.mkdirs();
		}
		Map<String, File> resourceMap = Stream.of(resources.listFiles())
				.filter(x -> x.getName().contains(".json"))
				.collect(Collectors.toMap(x -> x.getName().substring(0, x.getName().lastIndexOf(".")), x -> x));
		File result = resourceMap.get(matchString("Settings File", resourceMap.keySet().toArray(new String[resourceMap.size()])));
		try {
			JsonReader reader = new JsonReader(new ProgressReader("Reading Settings", result));
			return gson.fromJson(reader, TestGroup.class);
		} catch (FileNotFoundException e) {
			return null;
		}
	}
}
