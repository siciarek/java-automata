package com.siciarek.graphics;
import java.awt.Dimension;

import javax.swing.JFrame;

import com.siciarek.automata.ElemantaryCellularAutomaton;

public class Window extends JFrame {

	String title = "Cellular Automaton";
	Integer minWidth = 800;
	Integer minHeight = 600;
	Display display = new Display();
	ElemantaryCellularAutomaton automaton;

	private static final long serialVersionUID = -3713765476100328076L;

	public void setAutomaton(ElemantaryCellularAutomaton automaton) {
	}

	public Window(ElemantaryCellularAutomaton automaton) throws InterruptedException   {
		this.display.automaton = automaton;
		
		display.setAutomaton(automaton);

		this.setTitle(this.title);
		this.setMinimumSize(new Dimension(minWidth, minHeight));
//		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLocationRelativeTo(null);
		
		this.getContentPane().add(display);
		this.pack();
		this.setVisible(true);

		while(true) {
			System.out.print(".");
			display.repaint();
			Thread.sleep(500);
		}
	}
}