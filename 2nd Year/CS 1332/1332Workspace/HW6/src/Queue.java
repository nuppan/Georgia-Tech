import java.util.ArrayList;
/**
 * The Queue class extends the SortingAlgorithm class and represents a queue
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

public class Queue<T extends Process> extends SortingAlgorithm<Process> {
	
	/**
	 * The ArrayList that will hold all stack elements
	 */
	private ArrayList<T> queue;
	
	/**
	 * The Queue constructor initializes the ArrayList.
	 */
	public Queue(){
		queue = new ArrayList<T>();
	}
	
	/**
	 * The add method adds a Process object onto the queue (last).
	 * @param The Process object to be added into the queue.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void add(Process object) {
		queue.add((T)object);
	}

	/**
	 * The remove method removes the first element from the queue.
	 * @return returns the Process object that is currently being removed
	 */
	@Override
	public Process remove() {
		Process removeProc = queue.get(0);
		queue.remove(0);
		return removeProc;
	}

	/**
	 * The size method returns the size of the queue.
	 * @return an int representing the current size of the queue.
	 */
	@Override
	public int size() {
		return queue.size();
	}

}
