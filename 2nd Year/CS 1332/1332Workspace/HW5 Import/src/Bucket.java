/*
 * Homework 5 file to support my linear probing hash table 
 */

/**
 * @author Robert
 * A bucket for our experiments (Assumes linear probing)
 */
public class Bucket<K, V> {
	/** the integer key */
	K key;
	
	/** the value of the Bucket */
	V value;
	
	/** the flag for deletion in linear probing*/
	boolean valid;
	
	/**
	 * Constructor
	 * @param pkey the key for this bucket
	 * @param pvalue the value for this bucket
	 */
	public Bucket(K pkey, V pvalue) {
		key = pkey;
		value = pvalue;
		valid = true;
	}
	
	/**
	 * @return the string representation for this bucket
	 */
	public String toString() {
		return "Bucket Key: " + key + " Value: " + 
		         value.toString() + " Valid: " + valid;
	}

}
