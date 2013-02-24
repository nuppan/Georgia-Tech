/**
 * Uses threads to perform merge sort on comparable data.
 *
 * @param <K> the key type extends Comparable
 * @author David Esposito
 */
public class ParallelQuickSort<K extends Comparable<K>> extends Sorter<K> {
	
	/**
	 * Instantiates a new parallel quick sort. 
	 * 
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
	public ParallelQuickSort(K[] arr, int begin, int end, int minSplitSize, Driver.SortType baseSort) {
		super(arr, begin, end, minSplitSize, baseSort);
	}

	/* (non-Javadoc)
	 * @see Sorter#setupSplit()
	 */
	@Override
	protected int setupSplit() {
		/*
		 * Implement the partition function for Quick Sort.
		 */
		return 0; // TODO: return the correct value
	}

	/* (non-Javadoc)
	 * @see Sorter#split(int)
	 */
	@Override
	protected void split(int mid) {
		/*
		 * Implement merge sort split
		 */
		// TODO: see javadoc
	}

	/* (non-Javadoc)
	 * @see Sorter#merge()
	 */
	@Override
	protected void merge() {
		// DO NOTHING
	}

}
