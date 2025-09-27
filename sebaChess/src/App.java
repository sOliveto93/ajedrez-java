import javax.swing.JFrame;

import gui.Panel;

public class App extends JFrame {

	public final int WIDTH = 800;
	public final int HEIGHT = 600;

	public App() {

		Panel p = new Panel(WIDTH, HEIGHT);
		this.add(p);
		this.setSize(WIDTH, HEIGHT);

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void main(String[] args) throws Exception {
		new App();

	}
}
