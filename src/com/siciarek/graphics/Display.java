package com.siciarek.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;

import com.siciarek.automata.ElemantaryCellularAutomaton;

public class Display extends JPanel {

	private static final long serialVersionUID = -3387174008993555290L;

	ElemantaryCellularAutomaton automaton;
	BufferedImage img;
	int width;
	int height;

	public void setAutomaton(ElemantaryCellularAutomaton automaton) {
		this.automaton = automaton;
		this.width = automaton.getGrid()[0].length;
		this.height = automaton.getGrid().length;

		this.img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		
		System.out.println(this.width + "|" + this.height);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		System.out.println(this.width + "|" + this.height);

		for (int rc = 0; rc < this.width; rc++) {
			for (int cc = 0; cc < this.height; cc++) {
//				System.out.println(cc + "|" + rc);
				img.setRGB(cc, rc, Color.red.getRGB());
			}
		}

		((Graphics2D) g).drawImage(img, null, 0, 0);
	}
}
