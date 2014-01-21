package com.siciarek.automata;

import java.awt.Color;
import java.util.Vector;

import com.siciarek.gui.Window;

public class Wireworld extends CellularAutomaton {

	final int ELECTRON_HEAD = 1;
	final int ELECTRON_TAIL = 2;
	final int CONDUCTOR = 3;
	final int INSULATOR = 0;

	private static final long serialVersionUID = 1L;

	public Wireworld(int width, int height, Window window) {
		super(width, height, window);
	}

	public void init() {

		this.clean();

		for (int c = 0; c < this.cols; c++) {
			this.buffer[this.rows / 2][c] = CONDUCTOR;
		}

		this.buffer[this.rows / 2][1] = ELECTRON_HEAD;
		this.buffer[this.rows / 2 + 1][0] = ELECTRON_TAIL;
	}

	protected void setColorMap() {

		mapValColor = new Vector<Integer>();
		mapValColor.add(new Color(0x000000).getRGB());
		mapValColor.add(new Color(0xFFFFFF).getRGB());
		mapValColor.add(new Color(0x909090).getRGB());
		mapValColor.add(new Color(0x404040).getRGB());

		backgroundColor = new Color(mapValColor.get(0));
	}

	protected int getNextValue(int row, int col) {
		
		int value = this.grid[row][col];
		int ret = INSULATOR;

		switch (value) {
		
		case ELECTRON_HEAD:
			ret = ELECTRON_TAIL;
			break;

		case ELECTRON_TAIL:
			ret = CONDUCTOR;
			break;

		case CONDUCTOR:
			int[][] offsets = { { 0, 0 }, { -1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 },
					{ -1, -1 } };

			int val = 0;

			for (int i = 0; i < offsets.length; i++) {
				int r = row + offsets[i][0];
				int c = col + offsets[i][1];

				try {
					if (this.grid[r][c] == ELECTRON_HEAD) {
						val++;
					}
				} catch (ArrayIndexOutOfBoundsException e) {

				}
			}

			ret = val == 1 || val == 2 ? ELECTRON_HEAD : CONDUCTOR;
			break;
		
		}

		return ret;
	}

}
