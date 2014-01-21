package com.siciarek.automata;

import java.awt.Color;
import java.util.Vector;

import com.siciarek.gui.Window;

public class Wireworld extends CellularAutomaton {

	private static final long serialVersionUID = 1L;

	public Wireworld(int width, int height, Window window) {
		super(width, height, window);
	}

	public void init() {

		this.row = 0;
		this.clean();

		for (int c = 0; c < this.cols; c++) {
			this.buffer[this.rows / 2][c] = 3;
		}

		this.buffer[this.rows / 2 + 1][0] = 2;
		this.buffer[this.rows / 2][1] = 1;
	}

	public void configure() {

		this.states = 4;
		this.setColorMap();
	}

	protected void setColorMap() {

		mapValColor = new Vector<Integer>();
		mapValColor.add(new Color(0x000000).getRGB());
		mapValColor.add(new Color(0xFFFFFF).getRGB());
		mapValColor.add(new Color(0x909090).getRGB());
		mapValColor.add(new Color(0x404040).getRGB());

		backgroundColor = new Color(mapValColor.get(0));
	}

	protected boolean step() {

		for (int r = 0; r < this.rows; r++) {
			for (int c = 0; c < this.cols; c++) {
				if(this.buffer[r][c] != 0)
				this.buffer[r][c] = this.getNextValue(r, c);
			}
		}

		return true;
	}

	protected int getNextValue(int row, int col) {
		int value = this.grid[row][col];

		switch (value) {
		case 1:
			return 2;
		case 2:
			return 3;
		case 3:
			int[][] offsets = { { 0, 0 }, { -1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 },
					{ -1, -1 } };

			int val = 0;

			for (int i = 0; i < offsets.length; i++) {
				int r = row + offsets[i][0];
				int c = col + offsets[i][1];

				try {
					if (this.grid[r][c] == 1) {
						val++;
					}
				} catch (ArrayIndexOutOfBoundsException e) {

				}
			}

			return val == 1 || val == 2 ? 1 : 3;
		}

		return 0;
	}

}
