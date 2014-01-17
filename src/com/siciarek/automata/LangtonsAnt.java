package com.siciarek.automata;

import java.util.HashMap;
import java.util.Vector;

import com.siciarek.gui.Window;

public class LangtonsAnt extends CellularAutomaton {

	private static final long serialVersionUID = 1L;

	HashMap<Integer, Vector<Integer>> offsets = new HashMap<Integer, Vector<Integer>>();

	protected int dir = 90;
	
	int[] dirs = { 0, 90, 180, 270 };
	int[][] _offsets = { { 1, 0 }, { 0, -1 }, { -1, 0 }, { 0, 1 }

	};

	// L (90° left), R (90° right), N (no turn) and U (180° U-turn)

	protected int L = 90;
	protected int R = -90;
	protected int N = 0;
	protected int U = 180;

	public LangtonsAnt(int width, int height, Window window) {
		super(width, height, window);

		for (int d = 0; d < this.dirs.length; d++) {
			Vector<Integer> v = new Vector<Integer>(2);
			v.add(_offsets[d][0]);
			v.add(_offsets[d][1]);
			offsets.put(dirs[d], v);
		}
	}

	protected boolean step() {
		this.clean();

		this.buffer[this.row][this.col] = this.getNextValue(this.row, this.col);

		this.row += offsets.get(this.dir).get(0);
		this.col += offsets.get(this.dir).get(1);
		this.col = (this.cols + this.col) % this.cols;
		this.row = (this.rows + this.row) % this.rows;

		this.computeDirection();

		this.generation++;

		return true;
	}

	protected void computeDirection() {
		this.dir += this.buffer[this.row][this.col] > 0 ? this.L : this.R;
		this.dir += 360;
		this.dir %= 360;
	};

	protected int getNextValue(int row, int col) {
		int value = this.buffer[row][col];
		
		return value > 0 ? 0 : 1;
	}
}
