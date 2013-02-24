import java.util.*;
/**
 * This is HW8, ArrayMain
 * @author Joon Ki Hong
 * @version 3/14/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The ArrayMain class contains the main method that runs the program. It creates an array using the static method
 * of the ArrayHelper class and prompts the user with multiple options for which each option will execute a
 * different static method of the ArrayHelper class with the exception of quit and sort. 
 */
public class ArrayMain {
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		double[] dblArray;
		dblArray = ArrayHelper.createArray(); //creates an initial array
		int option;
		
		do {
			//prompts
			System.out.println("Pick an Option:");
			System.out.println("\t1: create array");
			System.out.println("\t2: minimum");
			System.out.println("\t3: maxmimum");
			System.out.println("\t4: average");
			System.out.println("\t5: standard deviation");
			System.out.println("\t6: increment (+)");
			System.out.println("\t7: percent increase (%)");
			System.out.println("\t8: sort");
			System.out.println("\t9: display");
			System.out.println("       10: quit");
			//end of prompts
			option = scan.nextInt();
			System.out.println();
			switch (option){
				case 1:
					dblArray = ArrayHelper.createArray();
					break;
				case 2:
					double minimum = ArrayHelper.minimum(dblArray);
					System.out.println("Minimum: "+minimum);
					break;
				case 3:
					double maximum = ArrayHelper.maximum(dblArray);
					System.out.println("Maximum: "+maximum);
					break;
				case 4:
					double average = ArrayHelper.average(dblArray);
					System.out.println("Average: "+average);
					break;
				case 5:
					double stdDev = ArrayHelper.standardDeviation(dblArray);
					System.out.println("Standard Deviation: "+stdDev);
					break;
				case 6:
					ArrayHelper.increment(dblArray);
					break;
				case 7:
					ArrayHelper.percentIncrease(dblArray);
					break;
				case 8:
					Arrays.sort(dblArray);
					break;
				case 9:
					ArrayHelper.print(dblArray);
					break;
				case 10:
					break;
			}
		} while (option != 10); //will loop as long as the user choose anything other than quit
		
	}
}
