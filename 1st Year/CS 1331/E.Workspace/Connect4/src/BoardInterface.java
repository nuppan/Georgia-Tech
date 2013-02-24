/**
 * This interface lists the methods necessary for the Board class
 * to interact with a UI. All UI interaction should be done solely
 * utilizing these methods.
 * 
 * @author Sundeep Ghuman
 * @version 1.0
 */
public interface BoardInterface
{
	/**
	 * This method should iterate through the entire board, and call each of
	 * the win condition checks listed in the WinCondition interface.
	 * 
	 * @return true if any of the win checks are true, false otherwise
	 */
	public abstract boolean checkForWin();
	
	/**
	 * Handles the placement of a single piece into the board. 
	 * 
	 * @param col the column that the piece is going into
	 * @param playerPiece a Piece belonging to the current player
	 * @return true if the move was valid, false if not
	 */
	public abstract boolean move(int col, Piece playerPiece);
	
	/**
	 * Drops a column if it has become full. This method should only
	 * be executed if the last move did not result in a win.
	 * 
	 * @param col the column number to drop
	 * @return true if the column was cleared, false otherwise
	 */
	public abstract boolean dropColumn(int col);
	
	/**
	 * This method is used by the UI to gather the state of the
	 * board so that it may represent it. This method should not return
	 * the actual board array, but rather should copy the values
	 * into a new array and return that.
	 * 
	 * @return a copy of the 2D Piece array representing the state of the board
	 */
	 public abstract Piece[][] getState();
	 
	 /**
	  * This method allows you to reset the board to start the game over.
	  */
	 public abstract void reset();
}
