import javax.swing.*;
import java.awt.*;
import java.util.*;
/**
 * This is HW6, DartboardPanel
 * @author Joon Ki Hong
 * @version 3/1/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The DartboardPanel acts as an extension to the JPanel object. It creates Dart instances iteratively
 * and draws them onto the panel. It also draws the dart board onto the panel
 */
public class DartboardPanel extends JPanel{
	private int dartsThrown;
	private int totalScore;
	private Random rand = new Random();
	
	
	/**
	 * The DartboardPanel constructor that sets the preferred size of the panel, sets the background color
	 * to cyan and initializes dartsThrown to the global variable.
	 * @param dartsThrown the (int)number of darts that are to be thrown
	 */
	public DartboardPanel(int dartsThrown){
		this.dartsThrown = dartsThrown;
		setPreferredSize(new Dimension(500,500));
		setBackground(Color.cyan);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * The paintComponent is responsible for drawing the dart board, calling the draw method for the Dart
	 * objects to draw each individual dart onto the Graphics component, and printing the number of darts
	 * currently thrown, the total score, and the average score per dart.
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int diameter = 500, x=0,y=0;
		double average;
		g.setColor(Color.white);
		
		//for loop that creates the dart board
		for (int count=0 ; count<9 ; count=count+1){
			//alternates color of ring after each iteration
			if (g.getColor() == Color.gray){
				g.setColor(Color.white);
			}
			else{
				g.setColor(Color.gray);
			}
			
			g.fillOval(x,y,diameter,diameter);
			diameter = diameter - (2*25);
			x = x+25;
			y = y+25;
		}
		//draws the bullseye after the loop ends
		g.setColor(Color.red);
		g.fillOval(x,y,diameter,diameter);
		
		//for loop that draws each Dart instance and prints the score, # of darts thrown, and average per dart.
		for (int count=1;count<=dartsThrown;count++){
			Dart tempDart = new Dart(rand.nextInt(501),rand.nextInt(501));
			totalScore = totalScore + tempDart.score();
			tempDart.draw(g);
			System.out.println("Darts thrown: "+count+" darts");
			System.out.println("Total score is: " + totalScore+" points");
			average = (double)totalScore/count;
			System.out.println("The average score per dart is: "+average+" points");
		}
		
	}
}
