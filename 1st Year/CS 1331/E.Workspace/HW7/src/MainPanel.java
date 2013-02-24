import javax.swing.*;
import java.awt.*;
/**
 * This is HW7, MainPanel
 * @author Joon Ki Hong
 * @version 3/10/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The MainPanel has two sub panels (DisplayPanel and InputPanel). These are constructed using a Border Layout.
 * A User object is passed in as a parameter which is then passed into the InputPanel object.
 */
public class MainPanel extends JPanel{
	
	/**
	 * The constructor sets the BorderLayout layout and sets a preferred size of 480x350px. It also instantiates
	 * a DisplayPanel object and a InputPanel object to house.
	 * @param player The User object which is passed in in order to pass the same object into InputPanel
	 */
	public MainPanel(User player){
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(480,350));
		
		DisplayPanel displayPanel = new DisplayPanel();
		InputPanel inputPanel = new InputPanel(displayPanel,player);
		JLabel title = new JLabel("Slot Machine!");
		title.setFont(new Font("Helvetica",Font.ITALIC, 36));
		
		
		add(title,BorderLayout.NORTH);
		add(inputPanel,BorderLayout.SOUTH);
		add(displayPanel,BorderLayout.CENTER);
		
	}
	
	
	
}
