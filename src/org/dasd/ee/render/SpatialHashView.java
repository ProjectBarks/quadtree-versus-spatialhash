package org.dasd.ee.render;

import org.dasd.ee.algorithms.SpatialHash;
import org.dasd.ee.tests.Test;
import org.dasd.ee.tests.TestSettings;

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
 * Written By: brandon on 10/12/16
 */
public class SpatialHashView extends RenderView<SpatialHash> {

	private static final String title = "Spatial Hash";

	public SpatialHashView(Test test, TestSettings settings) {
		super(test, settings, title);
	}

	@Override
	protected SpatialHash getAlgorithm(Integer width, Integer height) {
		return new SpatialHash();
	}

	@Override
	protected void render(Graphics2D g, TestSettings settings, SpatialHash algorithm, Set<Rectangle> allBoxes, Rectangle testBox) {
		int bound = settings.getBound();
		int increment = (int) (bound / Math.floor(bound / Math.pow(2, SpatialHash.DEFAULT_POWER_OF_TOW)));

		g.setColor(Color.GRAY);
		allBoxes.stream().map(algorithm::getHash).forEach(x -> drawGroup(g, x, increment));

		g.setColor(Color.decode("#349e35"));
		g.setStroke(new BasicStroke(2));
		drawGroup(g, algorithm.getHash(testBox), increment);
	}

	private void drawGroup(Graphics2D g,  List<String> hash, int increment) {
		hash.stream().map(x -> x.split(":")).forEach(x -> g.drawRect(Integer.parseInt(x[0]) * increment, Integer.parseInt(x[1]) * increment, increment, increment));
	}
}
