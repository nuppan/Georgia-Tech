import java.awt.*;
/**
 * This is HW9, Player
 * @author Joon Ki Hong
 * @version 4/1/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The Player class implements the Colorable interface. The Player object keeps track of what color is
 * associated with a particular instance of the the Player object.
 **/
public class Player implements Colorable{
	private Color playerColor;
	public Player(Color playerColor){
		this.playerColor = playerColor;
	}
	/* 
	 * The getColor method returns the Color associated to a particular instance of the Player object. The
	 * Color is indicated as a parameter of the constructor.
	 * @see Colorable#getColor()
	 */
	public Color getColor(){
		return playerColor;
	}
}
