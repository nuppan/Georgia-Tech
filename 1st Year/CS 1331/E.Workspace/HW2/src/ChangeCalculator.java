import java.util.Scanner;

/**
 * This is HW2 2.3 Change Calculator
 * Joon Ki Hong - xjo0nn@gatech.edu
 * CS1331 B3
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 web site.
 * This program takes a double input (monetary amount) and divides that amount into 20's, 10's, 5's, 1's, quarters, dimes, nickels, and pennies. Precision
 * may be off by one cent. 
 */

public class ChangeCalculator {

	public static void main(String[] args)
	{
		//Constants for money values
		final double TWENTY = 20.00;
		final double TEN = 10.00;
		final double FIVE = 5.00;
		final double ONE = 1.00;
		final double QUARTER = .25;
		final double DIME = .10;
		final double NICKEL = .05;
		final double PENNY = .01;
		double buffer; //remaining change after subtraction
			
		Scanner scan = new Scanner(System.in);
		System.out.print("Amount: $");
		double input = scan.nextDouble();
		int twenties = (int)(input/TWENTY);
		buffer = input - (int)(twenties*TWENTY);
		int tens = (int)(buffer/TEN);
		buffer -=(tens*TEN);
		int fives = (int)(buffer/FIVE);
		buffer -=(fives*FIVE);
		int ones = (int)(buffer/ONE);
		buffer -=(ones*ONE);
		int quarters = (int)(buffer/QUARTER);
		buffer -=(quarters*QUARTER);
		int dimes = (int)(buffer/DIME);
		buffer -=(dimes*DIME);
		int nickels = (int)(buffer/NICKEL);
		buffer -=(nickels*NICKEL);
		int pennies = (int)(buffer/PENNY);
		buffer -=(pennies*PENNY);
		
		//Output
		System.out.println(twenties + " twenty dollar bills");
		System.out.println(tens + " ten dollar bills");
		System.out.println(fives + " five dollar bills");
		System.out.println(ones + " one dollar bills");
		System.out.println(quarters + " Quarters");
		System.out.println(dimes + " Dimes");
		System.out.println(nickels + " Nickels");
		System.out.println(pennies + " Pennies");
				
	}
}

