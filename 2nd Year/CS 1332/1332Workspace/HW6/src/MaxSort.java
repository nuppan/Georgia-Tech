import java.util.ArrayList;

/**
 * The MaxSort class extends the SortingAlgorithm class and represents a max heap
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

public class MaxSort<T extends Process> extends SortingAlgorithm<Process> {

	/**
	 * The ArrayList that will hold all stack elements
	 */
	private ArrayList<T> heap;
	
	/**
	 * The MaxSort constructor initializes the ArrayList.
	 */
	public MaxSort(){
		heap = new ArrayList<T>();
	}
	
	/**
	 * The add method adds a Process object onto the max heap and resorts the
	 * heap if necessary having the maximum object as the 'root'.
	 * @param The Process object to be added into the queue.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void add(Process object) {
		heap.add((T)object);
		maxSort(heap.size()-1);
	}
	private void maxSort(int index){
		if(heap.get((index)/2).getCyclesToComplete()<heap.get(index).getCyclesToComplete()){
			T parent = heap.get(index/2);
			T child = heap.get(index);
			heap.set(index/2, child);
			heap.set(index, parent);
			if (index!=(index/2)){
				maxSort(index/2);
			}
		}
	}

	/**
	 * The remove method removes the root object and replaces it with the last 
	 * object. The heap then resorts itself accordingly to maintain the 
	 * attributes of a max heap.
	 * @return returns the Process object that is currently being removed
	 */
	@Override
	public Process remove() {
		Process returnProc = heap.get(0);
		heap.set(0, heap.get(heap.size()-1));
		heap.remove(heap.size()-1);
		heapify(0);
		return returnProc;
	}
	
	public void heapify(int index){
		if (2*index<heap.size()-1){
			int childIndex;
			if (heap.get(2*index).getCyclesToComplete()>heap.get((2*index)+1).getCyclesToComplete()){
				childIndex = (2*index);
			}
			else{
				childIndex = (2*index)+1;
			}
			if (heap.get(index).getCyclesToComplete()<heap.get(childIndex).getCyclesToComplete()){
				T parent = heap.get(index);
				T child = heap.get(childIndex);
				heap.set(childIndex, parent);
				heap.set(index, child);
				heapify(childIndex);
			}
			
		}
		
	}

	/**
	 * The size method returns the size of the max heap.
	 * @return an int representing the current size of the max heap.
	 */
	@Override
	public int size() {
		return heap.size();
	}

}


