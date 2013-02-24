import javax.swing.*;
import java.util.*;
import java.awt.*;
/**
 * This is HW7, SlotMachine
 * @author Joon Ki Hong
 * @version 3/10/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The DisplayPanel class retains all visual elements regarding the slots and any methods related.
 */
public class DisplayPanel extends JPanel {
	
	ImageIcon bellImage, cherryImage, grapeImage, lineImage;
	JLabel slot1, slot2, slot3;
	
	/**
	 * The constructor creates a Grid Layout of 1x3 and sets a custom background color. It also adds 
	 * the slots (JLabels).
	 */
	public DisplayPanel(){
		setLayout(new GridLayout(1,3));
		setBackground(new Color(255,160,0));
		bellImage = new ImageIcon("bell.png");
		cherryImage = new ImageIcon("cherry.png");
		grapeImage = new ImageIcon("grape.png");
		lineImage = new ImageIcon("line.png");
		
		slot1 = new JLabel("Slot #1",lineImage,SwingConstants.CENTER);
		slot2 = new JLabel("Slot #2",lineImage,SwingConstants.CENTER);
		slot3 = new JLabel("Slot #3",lineImage,SwingConstants.CENTER);
		
		add(slot1);
		add(slot2);
		add(slot3);
	}
	
	/**
	 * The updateSlots method updates the images for each JLabel with the corresponding
	 * randomly generated symbol received from the parameter.
	 * @param randResult This parameter is the string returned from the User object that contains
	 * the three random symbols calculated. (EX. "CHERRY BELL LINE")
	 */
	public void updateSlots(String randResult){
		Scanner scan = new Scanner(randResult);
		String word = scan.next();
		if (word.equals("BELL")){
			slot1.setIcon(bellImage);
		}
		else if (word.equals("CHERRY")){
			slot1.setIcon(cherryImage);
		}
		else if (word.equals("GRAPE")){
			slot1.setIcon(grapeImage);
		}
		else if (word.equals("LINE")){
			slot1.setIcon(lineImage);
		}
		word = scan.next();
		if (word.equals("BELL")){
			slot2.setIcon(bellImage);
		}
		else if (word.equals("CHERRY")){
			slot2.setIcon(cherryImage);
		}
		else if (word.equals("GRAPE")){
			slot2.setIcon(grapeImage);
		}
		else if (word.equals("LINE")){
			slot2.setIcon(lineImage);
		}
		word = scan.next();
		if (word.equals("BELL")){
			slot3.setIcon(bellImage);
		}
		else if (word.equals("CHERRY")){
			slot3.setIcon(cherryImage);
		}
		else if (word.equals("GRAPE")){
			slot3.setIcon(grapeImage);
		}
		else if (word.equals("LINE")){
			slot3.setIcon(lineImage);
		}
	}
}
