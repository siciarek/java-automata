package com.siciarek.automata;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JPanel;

import com.siciarek.gui.Window;

public class CellularAutomaton extends JPanel {

	protected static final long serialVersionUID = 1L;

	protected String automatonName = "Cellular Automaton";
	public int[][] pattern;
	protected int generation = 0;
	protected int rows = 0;
	protected int cols = 0;
	protected int row = 0;
	protected int col = 0;
	protected double density = 0;
	protected String rulestring = "B3/S23";
	protected Integer states = 2;

	Window window;

	protected int[][] grid;
	protected int[][] buffer;

	Color backgroundColor;
	protected BufferedImage screen;
	protected Vector<Integer> mapValColor;
	protected HashMap<Integer, Boolean> born = null;
	protected HashMap<Integer, Boolean> survive = null;

	final Color[] colors = { new Color(255, 255, 255), new Color(255, 0, 0), new Color(255, 69, 0), new Color(255, 127, 0),
			new Color(255, 140, 0), new Color(255, 165, 0), new Color(255, 215, 0), new Color(255, 255, 0),
			new Color(255, 255, 224), new Color(255, 255, 240), new Color(0, 0, 0), };

	/**
	 * Constructor
	 * 
	 * @param width
	 * @param height
	 * @param window
	 */
	public CellularAutomaton(int width, int height, Window window, String rulestring) {

		this.window = window;
		Integer times = this.window.getScale();
		this.rows = height / times;
		this.cols = width / times;
		this.rulestring = rulestring;

		this.grid = new int[this.rows][this.cols];
		this.buffer = new int[this.rows][this.cols];

		this.configure();

		screen = new BufferedImage(this.cols * times, this.rows * times,
				BufferedImage.TYPE_INT_RGB);
	}

	public CellularAutomaton(int width, int height, Window window) {
		this(width, height, window, "B3/S23");
	}

	// Initialization:

	public void configure() {

		String[] r = this.rulestring.split("/");

		this.states = r.length < 3 ? 2 : Integer.parseInt(r[2]);

		this.setColorMap();
	}

	protected void setColorMap() {

		mapValColor = new Vector<Integer>();

		for (int c = 0; c < this.colors.length; c++) {
			if (c < this.states - 1) {
				mapValColor.add(this.colors[c].getRGB());
			} else {
				mapValColor.add(this.colors[this.colors.length - 1].getRGB());
				break;
			}
		}

		backgroundColor = new Color(mapValColor.get(0));
	}

	// Graphics:

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int times = this.window.getScale();

		Graphics2D g2 = this.screen.createGraphics();
		g2.setColor(backgroundColor);
		g2.fillRect(0, 0, this.screen.getWidth(), this.screen.getHeight());

		for (int r = 0; r < this.screen.getHeight(); ++r) {
			for (int c = 0; c < this.screen.getWidth(); ++c) {
				int value = this.grid[r / times][c / times];
				if (value == 0) {
					continue;
				}

				this.screen.setRGB(c, r, this.mapValColor.get(value));
			}
		}

		g.drawImage(this.screen, 0, 0, this);
		g.dispose();
	}

	// Presentation:

	public void setAutomatonName(String name) {
		this.automatonName = name;
	}

	public String getTitle() {
		return " " + this.automatonName + ", gen. " + this.generation;
	}

	public void updateTitle() {
		this.window.setTitle(this.getTitle());
	}

	// Logic:

	protected boolean born(int n) {

		if (this.born == null) {
			this.born = new HashMap<Integer, Boolean>();
			String[] temp = this.rulestring.split("/")[0].split("");
			for (int i = 2; i < temp.length; i++) {
				this.born.put(Integer.parseInt(temp[i]), true);
			}
		}

		return this.born.containsKey(n);
	}

	protected boolean survive(int n) {

		if (this.survive == null) {
			this.survive = new HashMap<Integer, Boolean>();
			String[] temp = this.rulestring.split("/")[1].split("");
			for (int i = 2; i < temp.length; i++) {
				this.survive.put(Integer.parseInt(temp[i]), true);
			}
		}

		return this.survive.containsKey(n);
	}

	/**
	 * Returns neighborhood based on given offsets, actual cell value on first
	 * position
	 * 
	 * @param row
	 * @param col
	 * @param offsets
	 * @return Vector<Integer> [C, ... ]
	 */
	protected Vector<Integer> fetchNeighborhood(int row, int col, int[][] offsets) {

		Vector<Integer> n = new Vector<Integer>();

		for (int i = 0; i < offsets.length; i++) {
			int r = row + offsets[i][0];
			int c = col + offsets[i][1];

			// Loop if board ends
			c = (this.cols + c) % this.cols;
			r = (this.rows + r) % this.rows;

			n.add(this.grid[r][c]);
		}

		return n;
	}

	/**
	 * Returns More neighborhood
	 * 
	 * @param row
	 * @param col
	 * @return Vector<Integer> [C, N, NE, E, ES, S, SW, W, NW]
	 */
	protected Vector<Integer> fetchMooreNeighborhood(int row, int col) {

		int[][] offsets = { { 0, 0 }, { -1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 },
				{ -1, -1 } };

		return this.fetchNeighborhood(row, col, offsets);
	}

	/**
	 * Returns von Neuman neighborhood
	 * 
	 * @param row
	 * @param col
	 * @return Vector<Integer> [C, N, E, S, W]
	 */
	protected Vector<Integer> fetchVonNeumanNeighborhood(int row, int col) {

		int[][] offsets = { { 0, 0 }, { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

		return this.fetchNeighborhood(row, col, offsets);
	}

	public void setUp() {
		this.grid = new int[this.rows][this.cols];
		this.buffer = new int[this.rows][this.cols];

		this.init();
		this.bufferToGrid();
	}

	protected void bufferToGrid() {
		this.clean();

		for (int y = 0; y < this.rows; ++y) {
			for (int x = 0; x < this.cols; ++x) {
				int value = this.buffer[y][x];
				if (value == 0) {
					continue;
				}

				this.grid[y][x] = this.buffer[y][x];
			}
		}
	}

	protected void clean() {
		this.grid = new int[this.rows][this.cols];
	}

	public boolean move() {

		revalidate();
		repaint();

		++this.generation;
		this.updateTitle();

		boolean result = this.step();
		this.bufferToGrid();

		return result;
	}

	protected boolean step() {

		for (int r = 0; r < this.rows; r++) {
			for (int c = 0; c < this.cols; c++) {
				this.buffer[r][c] = this.getNextValue(r, c);
			}
		}

		return true;
	}

	protected int getNextValue(int row, int col) {

		Vector<Integer> n = this.fetchMooreNeighborhood(row, col);
		Integer value = n.get(0);
		Integer nb = 0;

		for (int i = 1; i < n.size(); i++) {
			nb += n.get(i) > 0 ? 1 : 0;
		}

		if (value == 0 && this.born(nb) || value > 0 && this.survive(nb)) {
			return (value + 1) < this.states ? value + 1 : this.states - 1;
		}

		return 0;
	}

	protected void init() {
		this.clean();

		// Drawing initial pattern:
		for (int i = 0; i < this.pattern.length; i++) {
			int r = this.rows / 2 + this.pattern[i][0];
			int c = this.cols / 2 + this.pattern[i][1];
			this.row = r;
			this.col = c;
			this.buffer[r][c] = this.states - 1;
			this.grid[r][c] = this.states - 1;
		}
	}

	public void setPattern(int[][] pattern) {
		this.pattern = pattern;
	}

	public void setDensity(double density) {
		this.density = density;
	}
	
	protected void normalizeCoordinates() {
		this.col = (this.cols + this.col) % this.cols;
		this.row = (this.rows + this.row) % this.rows;
	}

	// Abstract:

	public void nextPattern() {

	}

}
