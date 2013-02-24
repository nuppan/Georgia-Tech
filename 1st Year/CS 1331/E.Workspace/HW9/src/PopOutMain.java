import javax.swing.*;
/**
 * This is HW9, PopOutMain
 * @author Joon Ki Hong
 * @version 4/1/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The PopOutMain class initializes a JFrame and adds an instance of the TilePanel.
 */
public class PopOutMain {
	public static void main(String[] args){
		JFrame frame = new JFrame("CS 1331 Pop Out Connect 4 By: Joon Ki Hong Credits: Aaron Clarke");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		PopOutPanel panel = new PopOutPanel();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
