import java.util.*;
/**
 * This is HW8, ArrayHelper
 * @author Joon Ki Hong
 * @version 3/1/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The ArrayHelper class contains a set of static methods that perform various operations and calculations
 * given an array. It can also create an array by prompting the user for the number of elements desired.
 */
public class ArrayHelper {

	/**
	 * The createArray static method prompts the user for the desired length of the array and then prompts for each
	 * element of that array. It then returns the array.
	 * @return returns the created array
	 */
	public static double[] createArray(){
		Scanner scan = new Scanner(System.in);
		System.out.print("How many doubles would you like to store?: ");
		int arrElements = scan.nextInt();
		System.out.println();
		double[] dblArray = new double[arrElements];
		for (int count=0; count<arrElements;count++){
			System.out.printf("Please enter the %dth element: " ,count);
			double tempEntry = scan.nextDouble();
			System.out.println();
			dblArray[count] = tempEntry;
		}
		return dblArray;
	}
	
	/**
	 * The minimum static method loops through the given array and finds the minimum element.
	 * @param dblArray Accepts a double array
	 * @return returns the the minimum (double) found within the array.
	 */
	public static double minimum(double[] dblArray){
		double minNum = 0;
		for (int counter=0; counter<dblArray.length-1;counter++){
			if (counter == 0){
				minNum = dblArray[counter];
			}
			else{
				if (minNum>=dblArray[counter+1]){
					minNum = dblArray[counter+1];
				}
			}
		}
		return minNum;
	}
	
	/**
	 * The maximum static method loops through the given array and finds the maximum element.
	 * @param dblArray Accepts a double array
	 * @return returns the maximum (double) found within the array.
	 */
	public static double maximum(double[] dblArray){
		double maxNum = 0;
		for (int counter=0; counter<dblArray.length-1;counter++){
			if (counter == 0){
				maxNum = dblArray[counter];
			}
			else{
				if (maxNum<=dblArray[counter+1]){
					maxNum = dblArray[counter+1];
				}
			}
		}
		return maxNum;
	}
	
	/**
	 * The average static method calculates the average of all of the elements of a given array.
	 * @param dblArray Accepts a double array
	 * @return returns the average (double) calculated 
	 */
	public static double average(double[] dblArray){
		double entries = 0;
		double total = 0;
		for (double x:dblArray){
			total = total+x;
			entries += 1;
		}
		return  (total/entries);
	}
	
	/**
	 * The standardDeviation static method calculates the standard deviation of a set of elements
	 * in a given array.
	 * @param dblArray Accepts a double array.
	 * @return returns the standard deviation (double) calculated.
	 */
	public static double standardDeviation(double[] dblArray){
		double average = average(dblArray);
		double numerator = 0;
		double denominator = dblArray.length;
		double standardDeviation;
		for (double x:dblArray){
			numerator = numerator + Math.pow((x-average),2);
		}
		standardDeviation = Math.sqrt((numerator/denominator));
		return standardDeviation;	
	}
	
	/**
	 * The increment static method increments each element by the increment established by the user
	 * through prompts
	 * @param dblArray Accepts a double array.
	 */
	public static void increment(double[] dblArray){
		Scanner scan = new Scanner(System.in);
		int increment;
		System.out.print("Enter an integer for which you wish to increment the array by: ");
		increment = scan.nextInt();
		for (int count=0;count<dblArray.length;count++){
			dblArray[count] = dblArray[count]+increment;
		}
	}
	
	/**
	 * The percentIncrease static method increases each element of the array by the user specified amount.
	 * @param dblArray Accepts a double array.
	 */
	public static void percentIncrease(double[] dblArray){
		Scanner scan = new Scanner(System.in);
		double percentIncrease;
		System.out.print("Enter in decimal form, the percentage increase: ");
		percentIncrease = 1+scan.nextDouble();
		for (int count=0;count<dblArray.length;count++){
			dblArray[count] = dblArray[count]*percentIncrease;
		}		
	}
	
	/**
	 * The print static method prints the array in a specific format to the command line.
	 * Format = "[x,x,x,x,x]"
	 * @param dblArray Accepts a double array
	 */
	public static void print(double[] dblArray){
		String output = "[";
		for (int count=0;count<dblArray.length;count++){
			if (count == dblArray.length - 1){
				output = output + Double.toString(dblArray[count]) + "]";
			}
			else{
				output = output + Double.toString(dblArray[count]) + ", ";
			}
		}
		System.out.println(output);
	}
}
