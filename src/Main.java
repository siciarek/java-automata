import java.awt.Toolkit;

import com.siciarek.automata.ElemantaryCellularAutomaton;
import com.siciarek.graphics.Window;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		Toolkit tk = Toolkit.getDefaultToolkit();
		int width = ((int) tk.getScreenSize().getWidth());
		int height = ((int) tk.getScreenSize().getHeight());

//		width = 100;
//		height = 100;
		
		byte[][] grid = new byte[height][width];

		ElemantaryCellularAutomaton automaton = new ElemantaryCellularAutomaton(width, height);
		
		Window win = new Window(automaton);
	}
}
