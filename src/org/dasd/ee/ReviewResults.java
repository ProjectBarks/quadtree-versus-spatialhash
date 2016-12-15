package org.dasd.ee;

import org.dasd.ee.misc.Util;
import org.dasd.ee.render.QuadTreeView;
import org.dasd.ee.render.RenderView;
import org.dasd.ee.render.Settings;
import org.dasd.ee.render.SpatialHashView;
import org.dasd.ee.tests.Test;
import org.dasd.ee.tests.TestGroup;

import java.awt.*;

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
public class ReviewResults {

	public static void main(String[] args) throws Exception {
		TestGroup group = Util.loadSettings();
		Test firstTest = group.getTests().get(0);

		RenderView sView = new SpatialHashView(firstTest, group.getSettings()), tView = new QuadTreeView(firstTest, group.getSettings());
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int y = (int) ((screen.getHeight() / 2) - (group.getSettings().getBound() / 2)), centerX = (int) (screen.getWidth() / 2);
		sView.display(centerX, y);
		tView.display(centerX - group.getSettings().getBound(), y);

		Settings settings = new Settings(group, sView, tView);
		settings.display(centerX - group.getSettings().getBound() - 172, y);
	}
}
