import com.siciarek.graphics.Window;

public class Main {

	public static void main(String[] args) {
		try {
			new Window();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
