/**
 * These class contracts that the four possible winning conditions in
 * a game of 4-in-a-row are accounted for.
 * 
 * @author Elizabeth Johnson
 * @version 1.1
 */
public interface WinCondition {
	
	/**
	 * This method takes in a starting row and column position, and should
	 * check to see if there is a horizontal win to the right of that
	 * initial position. Meaning that the current piece is owned by
	 * the same player as the pieces 1, 2, and 3 columns to its right.
	 * If any of those pieces belong to the other player or are null,
	 * this method should return false. 
	 * 
	 * @param row row position to check
	 * @param col starting column position to check
	 * @return true if all 4 pieces are owned by the same player, false otherwise
	 */
	public abstract boolean checkHorizontal(int row, int col);
	
	/**
	 * This method takes in a starting row and column position, and
	 * should check to see if there is a vertical win below the
	 * initial position. Meaning that the current piece is owned by
	 * the same player as the pieces 1, 2, and 3 rows below it.
	 * If any of those pieces belong to the other player or are
	 * null, this method should return false
	 * 
	 * @param row starting row position to check
	 * @param col column position to check
	 * @return true if all 4 pieces are owned by the same player, false otherwise
	 */
	public abstract boolean checkVertical(int row, int col);
	
	/**
	 * This method takes in a starting row and column position, and should check to see if there is a
	 * diagonal win down and to the right. Meaning that the current piece is owned by the same player
	 * as the pieces 1,2, and 3 rows and columns down and to the right. If any of those pieces belong to the other
	 * player or are null, this method should return false
	 * 
	 * @param row starting row position to check
	 * @param col starting column position to check
	 * @return true if all 4 pieces are owned by the same player, false otherwise
	 */
	public abstract boolean checkDiagonalRight(int row, int col);
	
	/**
	 * This method takes in a starting row and column position, and
	 * should check to see if there is a diagonal win down and to the
	 * left. Meaning that the current piece is owned by the same player
	 * as the pieces 1,2, and 3 rows and columns down and to the left.
	 * If any of those pieces belong to the other player or are null,
	 * this method should return false
	 * 
	 * @param row starting row position to check
	 * @param col starting column position to check
	 * @return true if all 4 pieces are owned by the same player, false otherwise
	 */
	public abstract boolean checkDiagonalLeft(int row, int col);
}
