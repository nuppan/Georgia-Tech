/**
 * 
 * @author Joon Ki Hong, Robert
 * CS1332 A4
 * The following class contains various methods and java docing from the original Bucket class
 * created by the author Robert since the differences are very minimal.
 *
 * The Bucket class used for the coalesced and chaining hash tables.
 */
public class CBucket<K,V> {
	/** the integer key */
	K key;
	
	/** the value of the Bucket */
	V value;
	
	/** the flag for deletion in linear probing*/
	boolean valid;
	CBucket<K,V> next;
	
	/**
	 * Constructor
	 * @param pkey the key for this bucket
	 * @param pvalue the value for this bucket
	 */
	public CBucket(K pkey, V pvalue) {
		key = pkey;
		value = pvalue;
		valid = true;
		next = null;
	}
	
	/**
	 * @return the string representation for this bucket
	 */
	public String toString() {
		return "Bucket Key: " + key + " Value: " + 
		         value.toString() + " Valid: " + valid;
	}

}


