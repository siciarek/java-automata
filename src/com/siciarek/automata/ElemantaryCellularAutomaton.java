package com.siciarek.automata;

public class ElemantaryCellularAutomaton {

	byte[][] grid;
	
	public ElemantaryCellularAutomaton(int width, int height) {
		this.grid = new byte[height][width];
	}
	
	public byte[][] getGrid() {
		return this.grid;
	}

	public byte[][] iterate() {
		return this.grid;
	}
}
