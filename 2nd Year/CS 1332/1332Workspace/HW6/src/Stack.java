import java.util.ArrayList;
/**
 * The Stack class extends the SortingAlgorithm class and represents a stack
 * data structure.
 * 
 * I worked on this assignment alone using only the resources provided to me
 * in the Fall 2011 CS1332 class resources.
 * 
 * @author Joon Ki Hong
 * CS1332 A4
 *
 * @param <T> A Process object
 */
public class Stack<T extends Process> extends SortingAlgorithm<Process> {
	/**
	 * The ArrayList that will hold all stack elements
	 */
	private ArrayList<T> stack;
	
	/**
	 * The Stack constructor initializes the ArrayList.
	 */
	public Stack(){
		stack = new ArrayList<T>();
	}
	
	/**
	 * The add method adds a Process object onto the stack (top/last).
	 * @param The Process object to be added into the stack.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void add(Process object) {
		stack.add((T)object);
	}

	/**
	 * The remove method removes the last (top) element from the stack.
	 * @return returns the Process object that is currently being removed
	 */
	@Override
	public Process remove() {
		Process returnProc = stack.get(stack.size()-1);
		stack.remove(stack.size()-1);
		return returnProc;
	}

	/**
	 * The size method returns the size of the stack.
	 * @return an int representing the current size of the stack.
	 */
	@Override
	public int size() {
		return stack.size();
	}

}
