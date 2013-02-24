
/**
 * An object implementing the Addable
 * interface is saying that it has the
 * ability to add something to a
 * particular column
 *
 * @author Aaron Clarke
 * @version 03/27/2011
 */
public interface Addable
{
	/**
	 * This method adds something to a
	 * particular column.
	 *
	 * @param column An int specifying
	 *			which column to add to.
	 */
	public void addToColumn(int column);
}