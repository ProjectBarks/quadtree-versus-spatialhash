package org.dasd.ee.render;

import org.dasd.ee.tests.Test;
import org.dasd.ee.tests.TestGroup;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.util.Arrays;
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
 * Written By: brandon on 10/13/16
 */
public class Settings {
	private JPanel panel1;
	private JButton previousButton;
	private JButton nextButton;
	private JLabel testId;
	private JFormattedTextField caseSelector;
	private JButton renderButton;
	private JButton exportAsPNGButton;

	private TestGroup group;
	private List<RenderView> views;

	public Settings(TestGroup group, RenderView... views) {
		this(group, 1, views);
	}

	public Settings(TestGroup group, int chosenCase, RenderView... views) {
		this.group = group;
		this.views = Arrays.asList(views);

		this.previousButton.addActionListener(a -> offsetTestPosition(-1));
		this.nextButton.addActionListener(a -> offsetTestPosition(1));
		this.renderButton.addActionListener(a -> setCase(Integer.valueOf(String.valueOf(caseSelector.getValue()))));

		this.caseSelector.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getNumberInstance())));
		setCase(chosenCase);
		offsetTestPosition(0);
	}

	public void display(int x, int y) {
		JFrame frame = new JFrame("Settings");
		frame.setContentPane(panel1);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setBounds(x, y, frame.getWidth(), frame.getHeight());
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void setCase(int position) {
		Test chosenCase = group.getTests().get(Math.max(0, Math.min(position, group.getTests().size() - 1)));
		for (RenderView view : views) {
			view.setCase(chosenCase);
		}
		caseSelector.setColumns((int) Math.ceil(Math.log10(chosenCase.getTestPoints().length)));
		offsetTestPosition(0);
	}

	public void offsetTestPosition(Integer offset) {
		for (RenderView view : views) {
			view.setTestPosition(view.getTestPosition() + offset);
		}
		testId.setText(String.valueOf(views.get(0).getTestPosition() + 1));
	}
}
