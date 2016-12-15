package org.dasd.ee.tests;

import lombok.Getter;
import lombok.Setter;

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
public class Test {

	@Getter
	@Setter
	private Integer level;
	@Getter
	@Setter
	private Integer[][] points;
	@Getter
	@Setter
	private Integer[][] testPoints;

}
