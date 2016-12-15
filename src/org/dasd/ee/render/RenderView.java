package org.dasd.ee.render;

import lombok.Getter;
import org.dasd.ee.algorithms.IntersectionAlgorithm;
import org.dasd.ee.tests.Test;
import org.dasd.ee.tests.TestSettings;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;
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
 * Written By: brandon on 10/11/16
 */
public abstract class RenderView<T extends IntersectionAlgorithm> extends Container {

	protected JFrame window;
	protected T algorithm;
	private Test testCase;
	@Getter
	private int testPosition;
	private String title;
	private TestSettings settings;

	public RenderView(Test test, TestSettings settings, String title) {
		this.testPosition = 0;
		this.settings = settings;
		this.title = title;
		this.testCase = test;
		this.window = new JFrame();
	}

	public void display(int x, int y) {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(x, y, settings.getBound(), settings.getBound());
		window.getContentPane().add(this);
		setLayout(null);
		window.setTitle(title);
		window.setResizable(false);

		//Display
		window.setVisible(true);
		window.toFront();
	}

	protected abstract T getAlgorithm(Integer width, Integer height);

	protected void render(Graphics2D g) {
	}

	public void setCase(Test testCase) {
		this.testCase = testCase;
		repaint();
	}

	public void setTestPosition(Integer testId) {
		testPosition = Math.max(0, Math.min(testCase.getTestPoints().length - 1, testId));
		repaint();
	}

	@Override
	public final void paint(Graphics g) {
		super.paint(g);
		//Initialize Code
		Graphics2D g2 = (Graphics2D) g;
		algorithm = this.getAlgorithm(window.getWidth(), window.getHeight());
		Integer[] testPoint = testCase.getPoints()[testPosition];
		Rectangle testBox = new Rectangle(testPoint[0], testPoint[1], settings.getSize(), settings.getSize());
		Set<Rectangle> allBoxes = Stream.of(testCase.getPoints())
				.map(x -> new Rectangle(x[0], x[1], settings.getSize(), settings.getSize()))
				.peek(algorithm::insert)
				.collect(Collectors.toSet());

		//Clear the Background and previous drawings
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, window.getWidth(), window.getHeight());

		//Just in case there wants to be custom render code
		render(g2);

		//Draw the test point
		g2.setColor(Color.RED);
		g2.fill(testBox);

		//Draw the first points and insert it into the algorithm
		g2.setColor(Color.BLACK);
		allBoxes.parallelStream().forEach(g2::draw);

		//Overlay all potential intersecting points
		g2.setColor(Color.ORANGE);
		g2.setStroke(new BasicStroke(2));
		List<Rectangle> potentialPoints = algorithm.retrieve(testBox);
		potentialPoints.parallelStream().forEach(g2::draw);

		//Overlay all intersecting points
		g2.setColor(Color.BLUE);
		potentialPoints.parallelStream().filter(x -> x.intersects(testBox)).forEach(g2::draw);
	}
}
