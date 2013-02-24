import java.util.*;

/**
 * Uses threads to perform merge sort on comparable data.
 *
 * @param <K> the key type extends Comparable
 * @author David Esposito
 */
public class ParallelMergeSort<K extends Comparable<K>> extends Sorter<K> {

	/**
	 * Instantiates a new parallel merge sort.
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
	public ParallelMergeSort(K[] arr, int begin, int end, int minSplitSize, Driver.SortType baseSort) {
		super(arr, begin, end, minSplitSize, baseSort);
	}
	
	/* (non-Javadoc)
	 * @see Sorter#setupSplit()
	 */
	@Override
	protected int setupSplit() {
		return getCenterIndex();
	}
	
	/* (non-Javadoc)
	 * @see Sorter#split(int)
	 */
	@Override
	protected void split(int mid) {
		/*
		 * Implement merge sort split
		 */
		ParallelMergeSort<K> thread1 = new ParallelMergeSort<K>(arr,0,mid-1,minSplitSize,baseSort);
		ParallelMergeSort<K> thread2 = new ParallelMergeSort<K>(arr,mid,arr.length-1,minSplitSize,baseSort);
		// TODO: see javadoc
	}
	
	/* (non-Javadoc)
	 * @see Sorter#merge()
	 */
	@Override
	protected void merge() {
		// TODO: see javadoc
	}
}
