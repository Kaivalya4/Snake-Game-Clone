import javax.swing.JFrame;

public class gameFrame extends JFrame {

	gamePanel panel = new gamePanel();
	gameFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		//this.setLocationRelativeTo(null);
		
		this.add(panel);
		
		this.pack();
		this.setVisible(true);
	}

}
