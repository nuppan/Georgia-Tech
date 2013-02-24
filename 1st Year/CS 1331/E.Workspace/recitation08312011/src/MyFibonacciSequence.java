
public class MyFibonacciSequence {
	
	public static int getFibonacciNumberIterative(int num) {
		double last = 0;
		double current = 1;
		iMyMath math = new MyMath();
		for (int i=1;i<num;i++) {
			double temp = current;
			current = math.add(current, last);
			last = temp;
		}
		return (int) current;
	}
	
	public static int getFibonacciNumberRecursive(int num) {
		if (num <= 1)
			return num;
		
		iMyMath math = new MyMath();
		
		double minus1 = math.subtract(num, 1);
		double minus2 = math.subtract(num, 2);
		
		minus1 = getFibonacciNumberRecursive((int)minus1);
		minus2 = getFibonacciNumberRecursive((int)minus2);
		return  (int)math.add(minus1, minus2);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Fibonacci Number: F(5) = " + getFibonacciNumberIterative(5));
		System.out.println("Fibonacci Number: F(5) = " + getFibonacciNumberRecursive(5));
	}

}
