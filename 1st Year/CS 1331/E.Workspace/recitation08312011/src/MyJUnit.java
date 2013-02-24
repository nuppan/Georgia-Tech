import junit.framework.TestCase;


public class MyJUnit extends TestCase {
	
	public void testLoop() {
		int answer = NeedsDebuggingSolution.testLoop(100);
		int expected = 10000;
		assertEquals(expected, answer);
	}
	
	public void testFibonacci() {
		int answer = MyFibonacciSequence.getFibonacciNumberIterative(5);
		int expected = 5;
		assertEquals(expected, answer);
		
		answer = MyFibonacciSequence.getFibonacciNumberRecursive(5);
		expected = 5;
		assertEquals(expected, answer);
	}
}
