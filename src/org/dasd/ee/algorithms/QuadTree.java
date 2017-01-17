package org.dasd.ee.algorithms;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
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
 * Written By: brandon on 10/10/16
 */
public class QuadTree extends Rectangle implements IntersectionAlgorithm {

	@Setter
	private int MAX_OBJECTS = 10;
	@Setter
	private int MAX_LEVELS = 13;

	private int level;
	private List<Rectangle> objects;
	@Getter
	private QuadTree[] nodes;

	public QuadTree(int width, int height) {
		this(0, 0, 0, width, height);
	}

	private QuadTree(int pLevel, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.level = pLevel;
		this.objects = new ArrayList<>();
		this.nodes = new QuadTree[4];
	}

	@Override
	public void clear() {
		objects.clear();

		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] != null) {
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}

	@Override
	public void insert(Rectangle box) {
		if (isSplit()) {
			Integer[] indexs = getIndexs(box);

			if (indexs[0] != -1) {
				Stream.of(indexs).forEach(i -> nodes[i].insert(box));

				return;
			}
		}

		objects.add(box);

		if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
			if (!isSplit()) {
				split();
			}

			int i = 0;
			while (i < objects.size()) { //Reindex all objects
				Integer[] indexs = getIndexs(objects.get(i));
				if (indexs[0] != -1) {
					Rectangle boxToRemove = objects.remove(i);
					Stream.of(indexs).forEach(x -> nodes[x].insert(boxToRemove));
				} else {
					i++;
				}
			}
		}
	}

	@Override
	public List<Rectangle> retrieve(Rectangle box) {
		return this.retrieve(box, new ArrayList<>());
	}

	private List<Rectangle> retrieve(Rectangle box, List<Rectangle> returns) {
		Integer[] indexs = getIndexs(box);
		if (indexs[0] != -1 && isSplit()) {
			Stream.of(indexs).forEach(i -> nodes[i].retrieve(box, returns));
		}

		returns.addAll(objects);

		return returns;
	}

	/*
	 * Splits the node into 4 subnodes
	 */
	private void split() {
		int subWidth = (int) Math.floor(width / 2f), subWidthOffset = (int) Math.ceil(width / 2f);
		int subHeight = (int) Math.floor(height / 2f), subHeightOffset = (int) Math.ceil(height / 2f);

		nodes[0] = new QuadTree(level + 1, x + subWidth, y, subWidthOffset, subHeight);
		nodes[1] = new QuadTree(level + 1, x, y, subWidth, subHeight);
		nodes[2] = new QuadTree(level + 1, x, y + subHeight, subWidth, subHeightOffset);
		nodes[3] = new QuadTree(level + 1, x + subWidth, y + subHeight, subWidthOffset, subHeightOffset);
	}

	private boolean isSplit() {
		return nodes[0] != null;
	}

	/*
	 * Determine which node the object belongs to. -1 means object cannot
	 * completely fit within a child node and is part of the parent node
	 */
	private Integer[] getIndexs(Rectangle pRect) {
		if (!isSplit()) return new Integer[]{-1};
		List<Integer> indexs = new ArrayList<>();
		if (nodes[0].intersects(pRect) || nodes[0].contains(pRect)) indexs.add(0);
		if (nodes[1].intersects(pRect) || nodes[1].contains(pRect)) indexs.add(1);
		if (nodes[2].intersects(pRect) || nodes[2].contains(pRect)) indexs.add(2);
		if (nodes[3].intersects(pRect) || nodes[3].contains(pRect)) indexs.add(3);

		if (indexs.size() <= 0) {
			System.out.println("Help");
			return new Integer[]{-1};
		}

		return indexs.toArray(new Integer[indexs.size()]);
	}

	public List<Rectangle> getInnerBoxes() {
		return this.getInnerBoxes(new ArrayList<>());
	}

	private List<Rectangle> getInnerBoxes(List<Rectangle> boxes) {
		if (level != 0 || Stream.of(nodes).allMatch(x -> x == null)) {
			boxes.add(new Rectangle(x, y, width, height));
		}
		for (QuadTree tree : nodes) {
			if (tree == null) {
				continue;
			}
			boxes.addAll(tree.getInnerBoxes());
		}
		return boxes;
	}
}