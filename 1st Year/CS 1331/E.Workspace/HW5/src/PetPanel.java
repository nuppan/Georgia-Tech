import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This is HW5, PetPanel.java
 * @author Joon Ki Hong
 * @version 2/25/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The PetPanel class creates the JPanel that contains all of the labels, icons, and buttons for the pet object.
 */
public class PetPanel extends JPanel {
	private Pet mypet = null;
	private JLabel hungerlabel;
	private JLabel petpic;
	private JButton feed, poke, watch, kill;

	/**
	 * The constructor that instantiates a new JPanel, labels, and buttons and adds them to the panel.
	 * The buttons are arranged in a grid layout within another panel.
	 * @param mypet the Pet object
	 */
	public PetPanel(Pet mypet) {
		this.mypet = mypet;
		JPanel buttons = new JPanel(); //new panel for the buttons
		buttons.setLayout(new GridLayout(2,2,5,5)); //grid layout for the buttons
		setPreferredSize(new Dimension(250, 300));
		hungerlabel = new JLabel("Hunger: "+Integer.toString(mypet.getHunger()));
		petpic = new JLabel(mypet.getCurrentIcon());
		
		//buttons
		feed = new JButton("Feed");
		poke = new JButton("Poke");
		watch = new JButton("Watch");
		kill = new JButton("Kill");
		
		//adds action listeners
		ActionListener listener = new ButtonListener();
		feed.addActionListener(listener);
		poke.addActionListener(listener);
		watch.addActionListener(listener);
		kill.addActionListener(listener);
		
		//adds objects to the panel
		add(hungerlabel);
		add(petpic);
		buttons.add(feed);
		buttons.add(poke);
		buttons.add(watch);
		buttons.add(kill);
		add(buttons);
	}
	
	private class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == feed) {
					mypet.feed();
					refresh();
				}
				if (e.getSource() == poke) {
					mypet.poke();
					refresh();
				}
				if (e.getSource() == watch){
					mypet.watch();
					refresh();
				}
				if (e.getSource() == kill){
					mypet.kill();
					refresh();
				}
					
			}
		}
	
	private void refresh(){
		hungerlabel.setText("Hunger: "+Integer.toString(mypet.getHunger()));
		petpic.setIcon(mypet.getCurrentIcon());
		
	}
	
}
