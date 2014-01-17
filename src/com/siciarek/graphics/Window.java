package com.siciarek.graphics;

import java.awt.Dimension;

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
		
		CellularAutomaton automaton;

		// Elementary Cellular Automaton:
		automaton = new ElementaryCellularAutomaton(size.width, size.height, this);
		automaton.setAutomatonName("Elementary Cellular Automaton");
		automaton.setDensity(0);

		// Automata defined by stringrule:
//		String stringrule;
//		
//		stringrule = "B3/S23";
//		stringrule = "B3/S12345/11";
//		stringrule = "B3/S1234/11";
//		stringrule = "B37/S1234/11";
//		
//		automaton = new CellularAutomaton(size.width, size.height, this, stringrule);
//		automaton.setAutomatonName("Game of Life");
//		int[][] pattern = { { 0, 1 }, { 1, 2 }, { 2, 0 }, { 2, 1 }, { 2, 2 } };
//		automaton.setPattern(pattern);

		automaton.setUp();
		
		
		this.add(automaton);

		// Animation start:
		while (true) {
			if (automaton.move() == false) {
				automaton.nextPattern();
			}
			Thread.sleep(100);
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

	private Dimension getRealSize() {
		int width = this.getWidth() - (this.getInsets().left + this.getInsets().right);
		int height = this.getHeight() - (this.getInsets().top + this.getInsets().bottom);

		return new Dimension(width, height);
	}
}