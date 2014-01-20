import java.util.Vector;

import com.siciarek.automata.Definition;
import com.siciarek.gui.Window;


public class Main {

	public static void main(String[] args) {
		Vector<Definition> automata = new Vector<Definition>();
		automata.add(new Definition("Elementary Cellular Automaton", null));
		automata.add(new Definition("Langton's Ant", null));
		automata.add(new Definition("Game of Life", "B3/S23"));
		automata.add(new Definition("Maze", "B3/S1234/11"));
		automata.add(new Definition("Maze Full of Mice", "B37/S1234/11"));
		automata.add(new Definition("Mazectric", "B3/S12345/11"));
		automata.add(new Definition("Gnarl", "B1/S1"));

		try {
			Integer index = 1;
			Integer speed = 10;
			
			if(args.length > 0 &&  args[0].trim().matches("\\d+")) {
				index = Integer.parseInt(args[0].trim()) % automata.size();
			}

			if(args.length > 1 &&  args[1].trim().matches("\\d+")) {
				speed = Integer.parseInt(args[1].trim());
			}
			
			Window display = new Window();	
			display.run(automata.get(index), speed);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
