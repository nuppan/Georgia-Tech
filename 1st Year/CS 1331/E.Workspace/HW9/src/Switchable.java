
/**
 * An object implementing the Switchable
 * interface is saying that it has multiple
 * players and that they it can switch
 * between them, such as when taking turns.
 *
 * @author Aaron Clarke
 * @version 03/27/2011
 */
public interface Switchable
{
	/**
	 * This method switches between players
	 * who are taking turns in some sort of
	 * game.
	 */
	public void switchPlayers();
}