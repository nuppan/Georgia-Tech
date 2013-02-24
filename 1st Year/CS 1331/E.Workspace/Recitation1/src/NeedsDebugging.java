import java.util.*;

public class NeedsDebugging {
	
	public static List<String> testList;

	public static int testLoop(int max) {
		int temp = 0;
		for(int i=0;i<max;i++)
			for(int j=0;j<max;j++)
				temp++;
		return temp;
	}
	
	public static String testRecursion(int num) {
		System.out.println("Countdown: " + num);
		if (num <= 0)
			return "Complete!";
		return testRecursion(--num);
	}
	
	public static String fillList(String day) {
		testList.add(day);
		StringBuilder sb = new StringBuilder();
		for (String str : testList)
			sb.append(str).append(", ");
		return sb.delete(sb.length()-2, sb.length()).toString();
	}
	
	public static boolean testStringMatch(String a) {
		return a == "Hello, World!";
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test the number of iterations in a nested loop.
		System.out.println("Starting Test 1");
		System.out.println("Number of iterations: " + testLoop(100));
		System.out.println("Test 1 Finished\n\n");
		
		// Use recursion to count down to zero.
		System.out.println("Starting Test 2");
		testRecursion(15);
		System.out.println("Test 2 Finished\n\n");
		
		// Fill a list with the days of the week then print the list as a string.
		System.out.println("Starting Test 3");
		fillList("Monday");
		fillList("Tuesday");
		fillList("Wednesday");
		fillList("Thursday");
		System.out.println(fillList("Friday"));
		System.out.println("Test 3 Finished\n\n");
		
		// Test the equality of strings.
		System.out.println("Starting Test 4");
		StringBuilder hWorld = new StringBuilder("Hello, World!");
		System.out.println("The String Match: " + testStringMatch(hWorld.toString()));
		System.out.println("Test 4 Finished\n\n");
	}

}
