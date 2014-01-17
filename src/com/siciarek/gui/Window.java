package com.siciarek.gui;

import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JFrame;

import com.siciarek.automata.*;

public class Window extends JFrame {

	private static final long serialVersionUID = 1398197640669922135L;

	private boolean fullscreen = true;

	public Integer scale = 1;

	public Window() throws InterruptedException {

		this.setScale(2);

		if (this.fullscreen == true) {
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		} else {
			this.setSize(640, 480);
		}

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		Dimension size = this.getRealSize();

		HashMap<String, String> automata = new HashMap<String, String>();

		automata.put("Game of Life", "B3/S23");
		automata.put("Maze", "B3/S1234/11");
		automata.put("Maze Full of Mice", "B37/S1234/11");
		automata.put("Mazectric", "B3/S12345/11");

		
		automata.put("BelZhab", "B1/S1");

		String name = null;

		name = "Langton's Ant";

		int[][][] patterns = {
				{ { 0, 0 } }, // Center
				{ { 0, 1 }, { 1, 2 }, { 2, 0 }, { 2, 1 }, { 2, 2 }, }, // Glider
		};

		CellularAutomaton automaton;

		switch (name) {

		case "Elementary Cellular Automaton":
			automaton = new ElementaryCellularAutomaton(size.width, size.height, this);
			automaton.setAutomatonName("Elementary Cellular Automaton");
			automaton.setDensity(0);
			break;

		case "Langton's Ant":
			automaton = new LangtonsAnt(size.width, size.height, this);
			automaton.setAutomatonName(name);
			automaton.setPattern(patterns[0]);
			break;

		default:
			automaton = new CellularAutomaton(size.width, size.height, this, automata.get(name));
			automaton.setAutomatonName(name);
			automaton.setPattern(patterns[1]);
		}

		automaton.setUp();

		this.add(automaton);

		// Animation start:
		while (true) {
			if (automaton.move() == false) {
				automaton.nextPattern();
			}
			Thread.sleep(10);
		}
	}

	/**
	 * Set scale
	 * 
	 * @param scale
	 */
	private void setScale(int scale) {
		this.scale = scale;
	}

	public void update() {
		repaint();
	}

	private Dimension getRealSize() {
		int width = this.getWidth() - (this.getInsets().left + this.getInsets().right);
		int height = this.getHeight() - (this.getInsets().top + this.getInsets().bottom);

		return new Dimension(width, height);
	}
}