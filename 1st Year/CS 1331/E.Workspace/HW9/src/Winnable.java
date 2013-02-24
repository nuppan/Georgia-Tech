
/**
 * An object implementing the Winnable interface
 * can be won and thus needs to be able to check
 * to see if it has won.
 *
 * @author Aaron Clarke
 * @version 03/27/2011
 */
public interface Winnable
{
	/**
	 * This method checks to see if the Winnable
	 * has met its conditions for winning.
	 */
	public void checkWinCondition();
}