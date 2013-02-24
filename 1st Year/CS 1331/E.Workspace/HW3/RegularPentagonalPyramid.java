
/**
 * This is HW3, RegularPentagonalPyramid
 * Joon Ki Hong - xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * This program calculates the area and volume of a regular pentagonal pyramid using Object oriented programming, given the height
 * and length in the constructor
 */

public class RegularPentagonalPyramid {
	//global variables
	private int length;
	private int height;
	
	//Constructor that takes in an int length and int height
	public RegularPentagonalPyramid(int length, int height){
		this.length = length;
		this.height = height;
	}
	
	//calculates the area of the figure
	public double area(){
		double multiple = Math.sqrt(25+(10*Math.sqrt(5)));
		double sqrt = Math.pow(length,2);
		double area = (sqrt*multiple)/4.0;
		return area;	
		
	}
	
	//calculates the volume of the figure
	public double volume(){
		double volume = (area()*height)/3;
		return volume;
		
	}
	
	
}//end of class
