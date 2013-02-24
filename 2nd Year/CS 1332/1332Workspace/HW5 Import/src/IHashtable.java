/**
 * 
 */

/**
 * Interface for a Hashtable with key-value pairs
 * 
 * @author Robert
 *
 */
public interface IHashtable<K, V> {
	/**
	 * 
	 * @return the current load factor of the table
	 */
	public double loadfactor();
	
	/**
	 * Sets the regrow modifier.  The default should be 1.
	 * The hashtable uses this to add to the new tablesize
	 * when doubling.  Normally we add 1 to keep an odd size,
	 * but for the experiments, we might want to set it to 0.
	 * 
	 * @param modifier  The number to add when we double the table size
	 */
	public void setRegrowModifier(int modifier);
	
	/**
	 * 
	 * @param key the key to lookup in the hashtable
	 * @return the value corresponding to the key 
	 */
	public V lookup ( K key);
	
	/**
	 * Insert a key value pair into the hashtable
	 * @param key the key of the data to insert
	 * @param value the value of data corresponding to key
	 * 
	 * @return 0 if inserted or 1 if this was a duplicate (key already in table)
	 */
	public int insert (K key, V value);
	
	/**
	 * remove the entry corresponding to a key
	 * @param key the key of the entry
	 * @return true if key found and removed, false otherwise
	 */
	public boolean remove(K key);
	
	/**
	 * 
	 * @return true if the table is empty, false otherwise
	 */
	public boolean isEmpty();
	
	/**
	 * 
	 * @return the number of entries in the table
	 */
	public int size();
	
	/**
	 * 
	 * @return the number of collisions that have occured
	 */
	public int collisions();
	
    /**
     * 
     * @return the number of times the table was grown
     */
	public int regrowCount();
	
	/**
	 * returns the total number of comparisons necessary to find
	 * something.  The closer we are to O(1), the closer this
	 * will be to the number of searches.  If I did 500 lookups and 
	 * probeCount is 500, then we had O(1) exactly.  If the probeCount
	 * was 5000, then I had to do about 10 probes per search to find
	 * things.
	 * @return the number of probes on this table.
	 * 
	 */
	public int probeCount();

}
