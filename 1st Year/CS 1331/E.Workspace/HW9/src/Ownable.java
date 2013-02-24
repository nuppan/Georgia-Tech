
/**
 * An object implementing the Ownable
 * interface can be claimed by an
 * instance of the Player class.
 *
 * @author Aaron Clarke
 * @version 03/27/2011
 */
public interface Ownable
{
	/**
	 * A setter for the owner of this
	 * Ownable.
	 *
	 * @param p The new owner of this
	 *		Ownable. Can be null if
	 *		this Ownable is not to be
	 *		owned by anyone at this time.
	 */
	public void setOwner(Player p);
	
	/**
	 * A getter for the owner of this
	 * Ownable.
	 *
	 * @return the Player owning this
	 *		Ownable. Can be null if
	 *		no Player is currently in
	 *		possession of it.
	 */
	public Player getOwner();
}