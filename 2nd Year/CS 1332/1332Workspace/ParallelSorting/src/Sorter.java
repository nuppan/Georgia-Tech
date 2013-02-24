/**
 * The Class Sorter. Abstract sorter class which contains 
 * base functionality for parallelized sorts.
 *
 * @param <K> the key type extends comparable.
 */
public abstract class Sorter<K extends Comparable<K>> extends Thread {
	
	/** The number of times the divide and conquer algorithm has split. */
	protected static int splitCount;
	
	static {
		splitCount = 0;
	}
	
	/**
	 * Gets the number of times the divide and conquer algorithm has split.
	 *
	 * @return the split count
	 */
	public int getSplitCount() {
		return splitCount;
	}
	
	/**
	 * Reset split count.
	 */
	public void resetSplitCount() {
		splitCount = 0;
	}

	/** The array. */
	protected K[] arr;
	
	/** The starting index of the current window. */
	protected int begin;
	
	/** The ending index of the current window. */
	protected int end;
	
	/** The threshold which decides whether to divide or to use base case. */
	protected int minSplitSize;
	
	/** The base sort the sort to use for the base case. Selection sort is default. */
	protected Driver.SortType baseSort;
	
	/**
	 * Gets the center index for the current window. This method gives the index 
	 * of the first element for the right half of the current window.
	 *
	 * @return the center index
	 */
	protected int getCenterIndex() {
		return arr.length/2; // TODO: see javadoc
	}
	
	/** 
	 * After instantiation, the user should call start() on this object. The user
	 * must call join() on this thread to make sure the array is sorted before use of
	 * its contents.
	 *
	 * @param arr the array to be sorted
	 * @param begin the starting index of the current window in the array
	 * @param end the ending index of the current window in the array
	 * @param minSplitSize the threshold which decides whether to divide or to use base case.
	 * @param baseSort the base sort the sort to use for the base case.
	 */
	public Sorter(K[] arr, int begin, int end, int minSplitSize, Driver.SortType baseSort) {
		this.arr = arr;
		this.begin = begin;
		this.end = end;
		this.minSplitSize = minSplitSize;
		this.baseSort = baseSort;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// TODO
		/*
		 * If needs to split, then use set up split to get the get the index
		 * for where to split. After splitting the array, merge the array.
		 * 
		 * Other wise, call the correct base case sort.
		 */
		if (arr.length>minSplitSize){
			int divider = setupSplit();
			split(divider);
			merge();
		}
		else{
			switch(baseSort){
			case BubbleSort:
				bubbleSort();
				break;
			case InsertionSort:
				insertionSort();
				break;
			case SelectionSort:
				selectionSort();
				break;
			}
		}
	}
	
	/**
	 * Prepare the window for a split. This method will only be 
	 * used for Quick Sort. Merge sort will use this method to return
	 * the center index to prepare for dividing. 
	 *
	 * @return the int
	 */
	protected abstract int setupSplit();
	
	/**
	 * Divide the array and start new threads with the correct windows. Remember
	 * to call start() on each of the threads then join() on each of the threads 
	 * in that order. 
	 *
	 * @param mid the index to divide on. This index is included in the right half.
	 */
	protected abstract void split(int mid);
	
	/**
	 * Merge.
	 */
	protected abstract void merge();
	
	/**
	 * This method should swap the data in the provided indicies.
	 * No other changes should be made to the array.
	 *
	 * @param index1 the index1
	 * @param index2 the index2
	 */
	protected void swap(int index1, int index2) {
		K tempA = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = tempA;
	}
	
	/**
	 * Performs selection sort on the data in the
	 * current window. Indicies outside of the current window should 
	 * remain unchanged.
	 */
	protected void selectionSort() {
		int length = arr.length;
		int currPos;
		int minPos;
		for (currPos=0;currPos<length;currPos++){
			minPos = currPos;
			for (int i = currPos+1;i<length;i++){
				if (arr[i].compareTo(arr[minPos])==-1){
					minPos = i;
				}
			}
			if (minPos!=currPos){
				swap(currPos,minPos);
			}
		}
	}
	
	/**
	 * Performs insertion sort on the data in the
	 * current window. Indicies outside of the current window should 
	 * remain unchanged.
	 */
	protected void insertionSort() {
		int length = arr.length;
		for (int i=1;i<length;i++){
			K value = arr[i];
			int j = i-1;
			boolean done = false;
			while (done==false){
				if (arr[j].compareTo(value)==1){
					arr[j+1] = arr[j];
					j = j-1;
					if (j < 0){
						done = true;
					}
				}
				else {
					done = true;
				}
			}
			arr[j+1] = value;
		}
	}
	
	/**
	 * Performs bubble sort on the data in the
	 * current window. Indicies outside of the current window should 
	 * remain unchanged.
	 */
	protected void bubbleSort() {
		int length = arr.length;
		boolean swapped = false;
		while (swapped==false){
			for (int i=1;i<length;i++){
				if (arr[i-1].compareTo(arr[i])==1){
					swap(i-1,i);
					swapped = true;
				}
			}
			length--;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{ ");
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i]).append(", ");
			if ((i + 1) % 10 == 0)
				sb.append("\n");
		}
		return sb.delete(
				sb.length() - ((sb.charAt(sb.length() - 1) == '\n') ? 3 : 2),
				sb.length()).append(" }").toString();
	}

}
