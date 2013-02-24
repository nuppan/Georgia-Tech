import java.util.Iterator;
import java.util.List;


public interface ISparseArray2D<T> {
	/**
	 * adds value to the given row and column slot.  If nothing is
	 * there already, then add the entry, otherwise overwrite the
	 * existing value with value
	 * 
	 * @param row the row of the entry
	 * @param col the column of the entry
	 * @param value the value to add
	 * @throws ArrayIndexOutOfBoundsException throws an exception is row or column is outside valid range
	 *  
	 */
	void putAt(int row, int col, T value) throws ArrayIndexOutOfBoundsException;
	
	/**
	 * Remove the array item at row, col.  If there is nothing in the array at 
	 * those coordinates, return null, otherwise return the value at that location.
	 * 
	 * @param row the row of the entry to remove
	 * @param col the col of the entry to remove
	 * @return null if nothing at that entry, otherwise the value of the removed entry
	 * @throws ArrayIndexOutOfBoundsException if row or col is invalid
	 */
	T removeAt(int row, int col) throws ArrayIndexOutOfBoundsException;
	
	/**
	 * Returns the value at the given row and column.  If nothing is in array at
	 * that coordinate, then return null.  If row and column are out of valid range, then
	 * throw exception.
	 * 
	 * @param row the row of the entry to find
	 * @param col the column of the entry to find
	 * @return the value at row, col if there, otherwise null
	 * @throws ArrayIndexOutOfBoundsException if row or col is invalid
	 */
	T getAt(int row, int col) throws ArrayIndexOutOfBoundsException;
	
	/**
	 * Returns a list of values stored in that row.  Return only the values for entries,
	 * not nulls.  You do not need to preserve the column locations, just pack the values
	 * into a list.  The columns however should be in the order they occur. Thus if I call getRowFor(3), and row three has data in columns 4, 100,
	 * and 5000 of "A", "B" and "C", then I would return a simple list containing ("A","B","C");
	 * 
	 * For this method, you may use the Java built-in classes (like ArrayList)
	 *  to return your values in.
	 * 
	 * @param row the row to retrieve
	 * @return a List of all the existing row entries
	 * @throws ArrayIndexOutOfBoundsException if row is invalid
	 */
	List<T> getRowFor(int row) throws ArrayIndexOutOfBoundsException;
	
	/**
	 * returns a list of values stored in that column.  Return only the values for entries,
	 * not nulls.  You do not need to preserve the row locations, just pack the values
	 * into a list.  They should be in the order they occur. Thus if I call getColumnFor(3), and column three has data in rows 4, 100,
	 * and 5000 of "A", "B" and "C", then I would return a simple list containing ("A","B","C");
	 * 
	 * For this method, you may use the Java built-in classes (like ArrayList)
	 *  to return your values in.
	 * @param col the column to retrieve
	 * @return a list of all the existing column entries
	 * @throws ArrayIndexOutOfBoundsException if col is invalid
	 */
	List<T> getColumnFor(int col) throws ArrayIndexOutOfBoundsException;
	
}
