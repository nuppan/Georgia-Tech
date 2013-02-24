import javax.swing.*;
/**
 * This is HW6, DartboardMain
 * @author Joon Ki Hong
 * @version 3/1/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * the DartboardMain class contains the main method that creates a JFrame instance, Dartboard Panel instance
 * and adds it to the frame. 
 */
public class DartboardMain {
	public static void main(String[] args){
		String inputStr;
		int input;
		JFrame frame = new JFrame("Dartboard");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//JOptionPane that prompts the user for the number of darts to throw
		inputStr = JOptionPane.showInputDialog("How many darts do you want to throw?");
		input = Integer.parseInt(inputStr);
		if (input < 0){
			input = input *(-1);
		}
		DartboardPanel dartPanel = new DartboardPanel(input);
		frame.getContentPane().add(dartPanel);
		frame.pack();
		frame.setVisible(true);
	}
}
