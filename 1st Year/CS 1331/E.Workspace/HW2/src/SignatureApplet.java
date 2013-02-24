
import javax.swing.JApplet;
import java.awt.*;

/**
 * This is HW2, 2.2 Signature Applet
 * Joon Ki Hong - xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * This program uses the java.awt and javax.swing packages to draw a forum signature using a Graphics object.
 */


public class SignatureApplet extends JApplet {
	
	// Draws a Signature using a java applet
	
	public void init()
	{
		getContentPane().setBackground(Color.white);
	}
	
	public void paint(Graphics signature)
	{
		super.paint(signature);
		Color customyellow = new Color(255,200,0); //creates a new color object called customyellow
		Font txtfont = new Font(null,7,50); //creates a new font object named txtfont
		setBackground(Color.black);
		
	
		//Draws the GT symbol in the signature using shapes
		signature.setColor(customyellow);
		signature.fillArc(10,10,110,100,60,260);
		signature.fillRect(60,60,80,20);
		signature.fillRect(92,70,25,60);
		signature.setColor(Color.white);
		signature.fillArc(41,35,50,50,60,210);
		
		//Draws the additional cyan shape for design
		signature.setColor(Color.cyan);
		signature.fillOval(180,130,300,15);
		
		//Draws String Content of the signature
		signature.setColor(Color.black);
		signature.drawString("Joon Ki Hong", 200,90);
		signature.setColor(Color.orange);
		signature.drawString("Contact me: xjo0nn@gatech.edu",200,120);
		signature.setColor(Color.blue);
		signature.setFont(txtfont); //sets the graphic's context font to the custom font defined above
		signature.drawString("Computer Science",180,65);
			
		
	}// End of main method
	
		

}// End of class SignatureApplet
