package com.siciarek.automata;

import java.util.Vector;

import com.siciarek.gui.Window;

public class ElementaryCellularAutomaton extends CellularAutomaton {

	private static final long serialVersionUID = 1L;
	protected int rule = 30;
	protected Vector<Integer> rules = new Vector<Integer>(8);
	protected double density = 0;

	public ElementaryCellularAutomaton(int width, int height, Window window) {
		super(width, height, window);
	}

	public String getTitle() {
		return " " + this.automatonName + " (rule " + rule + ", step " + this.row + "/" + this.rows + ")";
	}

	public void init() {

		this.row = 0;
		this.setRules(this.rule);
		this.clean();

		if (this.density == 0) {
			this.grid[this.row][this.cols / 2] = 1;
		} else {
			for (int c = 0; c < this.cols; c++) {
				this.grid[this.row + 1][c] = Math.random() < this.density ? 0 : 1;
			}
		}
	}

	/**
	 * @Override
	 */
	protected void bufferToGrid() {
		
	}
	
	public void nextPattern() {
		this.rule = ++this.rule % 256;
		this.setUp();
	}

	protected boolean step() {

		for (int c = 0; c < this.cols; c++) {
			this.grid[this.row + 1][c] = this.getNextValue(this.row, c);
		}

		return (++row + 1) < this.rows;
	}

	protected int getNextValue(int row, int col) {

		int key = 0;
		int k = 0;

		do {
			int c = (this.cols + col - k + 1) % this.cols;
			key |= this.grid[row][c] << k++;
		} while (k < 3);

		return this.rules.get(key);
	}

	protected void setRules(int r) {
		rules = new Vector<Integer>(8);
		for (int i = 0; i < 8; i++) {
			this.rules.add((r >> i) % 2);
		}
	};
}
