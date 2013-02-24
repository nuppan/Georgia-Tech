//import solution.Driver.SortType;

/**
 * The Class Driver.
 */
public class Driver {
	
	/**
	 * The Enum SortType.
	 */
	public static enum SortType {
		ParallelMergeSort, 
		ParallelQuickSort, 
		SelectionSort, 
		InsertionSort,
		BubbleSort
	}

	/**
	 * Gets an int array filled with random data.
	 *
	 * @param size the size of the array
	 * @param maxNumber all numbers in the array will span from [0, maxNumber) exclusive.
	 * @return the random int array
	 */
	public static Integer[] getRandomIntArray(int size, int maxNumber) {
		Integer[] intCollection = new Integer[size];
		for (int i = 0; i < size; i++)
			intCollection[i] = (int) (Math.random() * maxNumber);
		return intCollection;
	}
	
	/**
	 * Clone an int array.
	 *
	 * @param arr the array to be cloned.
	 * @return the a deep copy of the array passed in.
	 */
	public static Integer[] cloneIntArray(Integer[] arr) {
		Integer[] rtn = new Integer[arr.length];
		System.arraycopy(arr, 0, rtn, 0, arr.length);
		return rtn;
	}
	
	/**
	 * Run integer sort. Performs a sor with the specified parameters 
	 * and displays some statistics about the run.
	 *
	 * @param type the type of sort to use
	 * @param baseSort the type of sort to use as the base case
	 * @param arr the array to be sorted
	 * @param minSplitSize the threshold for using the base case sort
	 */
	@SuppressWarnings("unchecked")
	public static void runIntegerSort(SortType type, SortType baseSort, Integer[] arr, int minSplitSize) {
		Sorter s;
		// Use the correct sort
		switch (type)
		{
		case ParallelMergeSort: s = new ParallelMergeSort<Integer>(arr, 0, arr.length-1, minSplitSize, baseSort); break;
		case ParallelQuickSort: s = new ParallelQuickSort<Integer>(arr, 0, arr.length-1, minSplitSize, baseSort); break;
		case SelectionSort: s = new ParallelMergeSort<Integer>(arr, 0, arr.length-1, arr.length+1, baseSort); break;
		case InsertionSort: s = new ParallelMergeSort<Integer>(arr, 0, arr.length-1, arr.length+1, baseSort); break;
		default: s = new ParallelMergeSort<Integer>(arr, 0, arr.length-1, minSplitSize, baseSort);
		}
		// make sure the split count is reset
		s.resetSplitCount();
		// start the thread and wait for it to finish
		long start = System.currentTimeMillis();
		try {
			s.start();
			s.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// calculate run time
		long end = System.currentTimeMillis();
		end = end - start;
		double exeTime = ((double)end) / 1000;
		// double check that array is sorted
		boolean sorted = true;
		for (int i=0;i<arr.length-1;i++) {
			sorted = sorted && arr[i].compareTo(arr[i+1]) <= 0;
			if (arr[i].compareTo(arr[i+1]) > 0)
				System.err.println(i + ": " + arr[i] + " <= " + arr[i+1]);
		}
		// print results to console
		System.out.println(type + " using " + baseSort);
		System.out.print("\tSorted = ");
		if (sorted)
			System.out.println("true");
		else
			System.err.println("false");
		System.out.println("\tSplit Count = " + s.getSplitCount());
		System.out.println("\tExe. Time = " + exeTime);
	}
	
	/**
	 * Run full experiment. Given a size and split theshold this method runs each
	 * sort with deep copies of a randomly generated array.
	 *
	 * @param size the size
	 * @param minSplitSize the min split size
	 */
	public static void runFullExperiment(int size, int minSplitSize, SortType baseCaseSort) {
		System.gc();
		String header = "New Experiment: Size=" + size + " and Divide Threshold=" + minSplitSize;
		System.out.println("\n");
		System.out.println(header);
		System.out.println(header.replaceAll(".", "-"));
		Integer[] arr = getRandomIntArray(size, Integer.MAX_VALUE);
		//runIntegerSort(SortType.ParallelMergeSort, baseCaseSort, cloneIntArray(arr), minSplitSize);
		//runIntegerSort(SortType.ParallelQuickSort, baseCaseSort, cloneIntArray(arr), minSplitSize);
		if (size < 50000) {
			runIntegerSort(SortType.SelectionSort, SortType.SelectionSort, cloneIntArray(arr), minSplitSize);
			runIntegerSort(SortType.InsertionSort, SortType.InsertionSort, cloneIntArray(arr), minSplitSize);
		} else {
			System.out.println("Data set is to large to sort with Selection Sort.");
			System.out.println("Data set is to large to sort with Insertion Sort.");
		}
		if (size < 5000)
			runIntegerSort(SortType.BubbleSort, SortType.BubbleSort, cloneIntArray(arr), minSplitSize);
		else
			System.out.println("Data set is to large to sort with Bubble Sort.");
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SortType baseCaseSort = SortType.SelectionSort;
		runFullExperiment(1000, 10, baseCaseSort);
		runFullExperiment(1000, 100, baseCaseSort);
		// These are used to show how detrimental n^2 runtimes can be.
		// v v v v v v v v v v v v v v v v v
		runFullExperiment(4000, 10, baseCaseSort);
		runFullExperiment(4000, 100, baseCaseSort);
		// ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^
		runFullExperiment(10000, 10, baseCaseSort);
		runFullExperiment(10000, 100, baseCaseSort);
		// These are used to show how detrimental n^2 runtimes can be.
		// v v v v v v v v v v v v v v v v v
		runFullExperiment(40000, 100, baseCaseSort);
		runFullExperiment(40000, 1000, baseCaseSort);
		// ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^
		runFullExperiment(100000, 100, baseCaseSort);
		runFullExperiment(100000, 1000, baseCaseSort);
	}
}
