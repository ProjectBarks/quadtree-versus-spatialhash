package org.dasd.ee.algorithms;


import java.awt.*;
import java.util.*;
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
 * Written By: brandon on 10/10/16
 */
public class SpatialHash implements IntersectionAlgorithm {

	public final static int DEFAULT_POWER_OF_TOW = 5;

	private int shift;
	private Map<String, ArrayList<Rectangle>> hash;

	public SpatialHash() {
		this(DEFAULT_POWER_OF_TOW);
	}

	public SpatialHash(int powerOfTwo) {
		this.shift = powerOfTwo;
		this.hash = new HashMap<>();
	}

	@Override
	public void insert(Rectangle box) {
		List<String> keys = this.getHash(box);
		for (String key : keys) {
			if (this.hash.containsKey(key)) {
				this.hash.get(key).add(box);
			} else {
				this.hash.put(key, new ArrayList<>(Collections.singletonList(box)));
			}
		}
	}

	@Override
	public List<Rectangle> retrieve(Rectangle box) {
		List<Rectangle> results = new ArrayList<>();
		this.getHash(box).stream().filter(key -> this.hash.containsKey(key)).forEach(key -> results.addAll(this.hash.get(key)));
		return results;
	}

	@Override
	public void clear() {
		this.hash.clear();
	}

	public List<String> getHash(Rectangle box) {
		int sx = (int) box.getX() >> shift,
				sy = (int) box.getY() >> shift,
				ex = (int) (box.getX() + box.getWidth()) >> shift,
				ey = (int) (box.getY() + box.getHeight()) >> shift;
		List<String> keys = new ArrayList<>();
		for (int y = sy; y <= ey; y++) {
			for (int x = sx; x <= ex; x++) {
				keys.add(x + ":" + y);
			}
		}
		return keys;
	}
}
