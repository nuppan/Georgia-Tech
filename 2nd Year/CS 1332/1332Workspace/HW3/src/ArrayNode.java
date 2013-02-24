/**
 * The ArrayNode<T> class represents a node that retains
 * the data of parameter T pertaining to a specific location
 * in the sparse array.
 * CS 1332 A4 - jhong70
 * @author Joon Ki Hong
 *
 * @param <T> 
 */
public class ArrayNode<T> {

	/**
	 * data - holds information in the ArrayNode of type T.
	 */
	protected T data;
	/**
	 * column - holds the column position of the ArrayNode
	 */
	protected int column;
	/**
	 * row - holds the row position of the ArrayNode
	 */
	protected int row;
	/**
	 * nextRow - the pointer to the next ArrayNode in in the
	 * next row of the sparse array.
	 */
	protected ArrayNode nextRow;
	/**
	 * nextCol - the pointer to the next ArrayNode in the
	 * next column of the sparse array.
	 */
	protected ArrayNode nextCol;
	
	
	
	
	/**
	 * The Array Node constructor takes in a int row, int col
	 * and T data and initializes the corresponding variables
	 * in a new instance of ArrayNode
	 * @param row the row location in the sparse array.
	 * @param col the column location in the sparse array.
	 * @param data the T data stored in the [row][col] location.
	 */
	public ArrayNode(int row, int col, T data){
		this.data=data;
		column = col;
		this.row = row;
	}
	/**
	 * The setColumn method takes in an integer representing 
	 * the column and sets the column of a particular ArrayNode
	 * to that integer.
	 * @param col column integer.
	 */
	protected void setColumn(int col){
		column = col;
	}
	/**
	 * The setRow method takes in an integer representing
	 * the row and sets the row of a particular ArrayNode
	 * to that integer
	 * @param row row integer.
	 */
	protected void setRow(int row){
		this.row = row;
	}
	/**
	 * The setNextRow method takes in an ArrayNode instance
	 * and creates a pointer to that pre-existing node and
	 * assigns it the nextRow variable
	 * @param node ArrayNode instance
	 */
	protected void setNextRow(ArrayNode node){
		nextRow = node;
	}
	/**
	 * The setNextCol takes in an ArrayNode instance
	 * and creates a pointer to that pre-existing node and
	 * assigns it the the nextCol variable.
	 * @param node ArrayNode instance
	 */
	protected void setNextCol(ArrayNode node){
		nextCol = node;
	}

}
