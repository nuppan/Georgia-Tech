import java.awt.*;
/**
 * This is HW6, Dart
 * @author Joon Ki Hong
 * @version 3/1/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The Dart class defines the attributes of a dart object. It can draw itself onto a Graphics component
 * and has a method to calculate the score based on a 500x500 window.
 */
public class Dart {
	private int x;
	private int y;
	public Dart(int x, int y){
		this.x = x;
		this.y = y;
	}	
	public void draw(Graphics g){
		//draws an "X" as a visual representation for the dart
		g.drawLine(x-10,y+10,x+10,y-10);
		g.drawLine(x+10,y+10,x-10,y-10);
	}	
	public int score(){
		//returns the Euclidean distance from the center which is 250x250.
		double score = Math.sqrt(Math.pow(x-250,2)+Math.pow(y-250,2));
		return (int)score;
	}
}

