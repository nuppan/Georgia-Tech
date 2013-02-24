
/**
 * An object implementing the Poppable
 * interface is saying that it has the
 * ability to remove something from a
 * particular column
 *
 * @author Aaron Clarke
 * @version 03/27/2011
 */
public interface Poppable
{
	/**
	 * This method removes something from a
	 * particular column.
	 *
	 * @param column An int specifying
	 *			which column to remove from.
	 */
	public void removeFromColumn(int column);
}