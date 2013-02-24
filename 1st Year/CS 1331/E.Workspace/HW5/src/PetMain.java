import javax.swing.*;
/**
 * This is HW5, PetMain
 * @author Joon Ki Hong
 * @version 2/25/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The PetMain class contains the main method that creates a new JFrame and adds the PetPanel to the content pane
 */
public class PetMain {

	public static void main(String[] args){
		
		JFrame frame = new JFrame("My Pet!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Pet mypet = new Pet();
		PetPanel petpanel = new PetPanel(mypet);
		
		frame.getContentPane().add(petpanel);
		frame.pack();
		frame.setVisible(true);
		
		
	}
}
