import javax.swing.JFrame;
/**
 * This is HW7, SlotMachine
 * @author Joon Ki Hong
 * @version 3/10/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The SlotMachine class instantiates a new User object, JFrame to house the GUI, and MainPanel to run the slot machine game
 */
public class SlotMachine {

	public static void main(String[] args){
		
		JFrame frame = new JFrame("Slot Machine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		User player = new User();
		MainPanel mainPanel = new MainPanel(player);
		
		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setVisible(true);
		}
}