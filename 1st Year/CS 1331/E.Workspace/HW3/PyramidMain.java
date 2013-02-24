
import java.util.*;
import java.text.DecimalFormat;
/**
 * This is HW3, RegularPentagonalPyramid
 * Joon Ki Hong - xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * This program calculates the area and volume of a regular pentagonal pyramid using Object oriented programming, given the height
 * and length in the constructor
 */

public class PyramidMain {

	// main method
	public static void main(String[] args){
		Random rand = new Random();
		Scanner scan = new Scanner(System.in);
		DecimalFormat fmt = new DecimalFormat("0.##");
		
		RegularPentagonalPyramid pyramid1 = new RegularPentagonalPyramid(rand.nextInt(100),rand.nextInt(100)); //instantiates a pyramid object using random integers as parameters
		double randvlm = pyramid1.volume(); //volume of rand pyramid
		
		//user input for pyramid2
		System.out.print("Input an integer for the length of a side for Pyramid 2: ");
		int ulength = scan.nextInt();
		System.out.print("Input an integer for the height of Pyramid 2: ");
		int uheight = scan.nextInt();
		//end user input
		
		RegularPentagonalPyramid pyramid2 = new RegularPentagonalPyramid(ulength,uheight); //instantiates a pyramid object using the user's input as parameters
		double uservlm = pyramid2.volume(); //volume of user pyramid
		
		//screen output of both pyramids using the decimal format
		System.out.println("Pyramid 1's Volume is: " + fmt.format(randvlm) + " units cubed");
		System.out.println("Pyramid 2's Volume is: " + fmt.format(uservlm) + " units cubed");
		//end screen output
		
		
	}//end of main method
	
}//end of class
