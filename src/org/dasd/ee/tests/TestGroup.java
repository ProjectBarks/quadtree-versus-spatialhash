package org.dasd.ee.tests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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
 * Written By: brandon on 10/17/16
 */
@AllArgsConstructor
public class TestGroup {

	@Getter
	@Setter
	private TestSettings settings;

	@Getter
	@Setter
	private List<Test> tests;

	public TestGroup() {
		this.settings = new TestSettings();
		this.tests = new ArrayList<>();
	}

	public void addTest(Test test) {
		this.tests.add(test);
	}
}
