package com.siciarek.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.siciarek.automata.CellularAutomaton;
import com.siciarek.automata.Definition;
import com.siciarek.automata.ElementaryCellularAutomaton;
import com.siciarek.automata.LangtonsAnt;
import com.siciarek.automata.Wireworld;

public class Window extends JFrame {

	private static final long serialVersionUID = 1398197640669922135L;
	private Integer scale = 2;
	private boolean fullscreen = true;
	private Integer speed;

	public void run(Definition definition) throws InterruptedException {
		this.run(definition, this.speed);
	}

	public void run(Definition definition, Integer speed) throws InterruptedException {
		
		this.speed = speed;
		
		String name = definition.getName();
		String rule = definition.getRule();

		int[][][] patterns = {
				{ { 0, 0 } }, // Center
				{ { 0, 1 }, { 1, 2 }, { 2, 0 }, { 2, 1 }, { 2, 2 }, }, // Glider
		};
		
		if (this.fullscreen == true) {
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		} else {
			this.setSize(640, 480);
		}

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		Dimension size = this.getRealSize();
		
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

		case "Wireworld":
			automaton = new Wireworld(size.width, size.height, this);
			automaton.setAutomatonName(name);
			break;

		default:
			automaton = new CellularAutomaton(size.width, size.height, this, rule);
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
			Thread.sleep(speed);
		}
	}

	public void update() {
		repaint();
	}

	private Dimension getRealSize() {
		int width = this.getWidth() - (this.getInsets().left + this.getInsets().right);
		int height = this.getHeight() - (this.getInsets().top + this.getInsets().bottom);

		return new Dimension(width, height);
	}

	/**
	 * @return the scale
	 */
	public Integer getScale() {
		return this.scale;
	}

	/**
	 * @param scale the scale to set
	 */
	public void setScale(Integer scale) {
		this.scale = scale;
	}
}