/**
 * 
 */

/**
 * Linear probing hash table
 * @author Robert
 *
 */
public class Hashtable<K, V> implements IHashtable<K, V> {
    /**
     * Our actual table array
     */
    private Bucket<K, V>[] buckets;
    
    /**
     * the number of entries in the table
     */
    private int entryCount;
    
    /**
     * the number of collisions in the table
     */
    private int collisions;
    
    /**
     * the number of times we had to regrow table
     */
    private int regrowCount;
    
    /** 
     * the maximum loadfactor for this table
     */
    private double maxloadfactor;
    
    /**
     * the number of search probe we had
     */
	private int probeCount;
	
	/**
	 * the amount to add to new size on regrow
	 */
	private int regrowModifier;

	/**
	 * Make a new hashtable
	 * @param initialSize of our backing array
	 * @param lf max loadfactor
	 */
    @SuppressWarnings("unchecked")
    public Hashtable (int initialSize, double lf) {
    	buckets =  new Bucket[initialSize];
    	
    	//mark all the buckets as empty
    	for ( int i = 0; i < buckets.length ; i++)
    		buckets[i] = null;
    	
    	maxloadfactor = lf;
    	entryCount = 0;
    	collisions = 0;
    	regrowCount = 0;
    }
    
	/* (non-Javadoc)
	 * @see IHashtable#collisions()
	 */
	public int collisions() {
		return collisions;
	}

	/* (non-Javadoc)
	 * @see IHashtable#insert(java.lang.Object, java.lang.Object)
	 */
	public int insert(K key, V  value) {
		int code = key.hashCode();
		int index = Math.abs(code) % buckets.length;
		Bucket<K, V> b = new Bucket<K, V>(key, value);
		boolean found = false;
		
		while (! found) {	
			Bucket<K, V> bucket = buckets[index];
			if (bucket == null || !bucket.valid) {
				buckets[index] = b;
				entryCount++;
				found = true;
			} else if (bucket.key.equals(key)) {
				return 1;
			} else {
			  collisions++;
			  index = (index + 1) % buckets.length;
			}
		}
		
		if (loadfactor()> maxloadfactor) {
			regrow();
			regrowCount++;
		}
		
		return 0;
			
    }


    /**
     * regrow the hashtable after the loadfactor is reached
     * double the size and rehash entries
     */
	@SuppressWarnings("unchecked")
    private void regrow() {
		Bucket<K, V>[] oldtable =  buckets;
	    buckets = new Bucket[(buckets.length *2) + regrowModifier];
	    entryCount = 0;
		for (int i = 0; i < oldtable.length; i++) {
			Bucket<K, V> bucket = oldtable[i];
			if (bucket != null && bucket.valid) {
				insert(bucket.key, bucket.value);
				
			}
		}		
	}
	
	/* (non-Javadoc)
	 * @see IHashtable#isEmpty()
	 */
	public boolean isEmpty() {
		return entryCount == 0;
	}

	/* (non-Javadoc)
	 * @see IHashtable#loadfactor()
	 */
	public double loadfactor() {
		return (entryCount * 1.0) / buckets.length;
	}

	/* (non-Javadoc)
	 * @see IHashtable#lookup(java.lang.Object)
	 */
	public V lookup(K key) {
		int code = key.hashCode();
		int index = Math.abs(code)  % buckets.length;
		boolean found = false;
		
		while (!found) {
			Bucket<K, V> bucket = buckets[index];
			if (bucket == null) {
				//not in table
				probeCount++;
				return null;
			}
			
			if (bucket.key.equals(key)) {
				//found
				probeCount++;
				return bucket.value;
			} else {
				//not at this index, try again
				probeCount++;
				index = (index + 1) % buckets.length;
			}			
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see IHashtable#regrowCount()
	 */
	public int regrowCount() {
		return regrowCount;
	}

	/* (non-Javadoc)
	 * @see IHashtable#remove(java.lang.Object)
	 */
	public boolean remove(K key) {
		int code = key.hashCode();
		int index = Math.abs(code)  % buckets.length;
		boolean found = false;
		
		while (!found) {
			Bucket<K, V> bucket = buckets[index];
			
			if (bucket == null || !bucket.valid) 
                return false;
			
			if (bucket.key.equals(key))  {
				bucket.valid = false;
				entryCount--;
				return true;
			} else
				index = (index + 1) % buckets.length;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see IHashtable#size()
	 */
	public int size() {
		return entryCount;
	}
	/**
	 * main test routine.
	 * @param args
	 */
	public static void main(String[] args) {
		Hashtable<Integer, String> ht = new Hashtable<Integer, String> (10, .75);
		int errors = 0;
		
		if (!ht.isEmpty()) {
			errors++;
			System.err.println("isEmpty return on empty hash table");
		}
		
		if (ht.insert(10, "Duck")!= 0 ) {
			errors++;
			System.err.println("Improper duplicate (10) found in hash table");
		}
		
		String result = ht.lookup(10);
		if (!result.equals("Duck")) {
			errors++;
			System.err.println("lookup after 1 insert failed");
		}

		if (ht.insert(20, "Goose")!= 0 ) {
			errors++;
			System.err.println("Improper duplicate (10) found in hash table");
		}
		
		if (ht.collisions() != 1) {
			System.err.println("Collision count bad got: " + ht.collisions);
			errors++;
		}
		
		if (ht.insert(20, "Goose")!= 1) {
			errors++;
			System.err.println("Failed to report duplicate (20) found in hash table");
		}
		
		if (ht.remove(30)) {
			System.err.println("Bad remove for non-exist item");
			errors++;
		}
		
		if (!ht.remove(20)) {
			System.err.println("Bad remove for existing item");
			errors++;
		}
		
		ht.debugDumpTable();
		for (int i = 31; i < 51; i++) {
			ht.insert(i, "String " + i);
		}
		
		if (ht.regrowCount() != 2) {
			System.err.println("Bad regrow count: " + ht.regrowCount());
			errors++;
		}
		
		if (ht.size() != 21) {
			System.err.println("Incorrect size: " + ht.size());
			errors++;
		}
		
		if (!"String 31".equals(ht.lookup(31))) {
			System.err.println("Incorrect lookup: " + ht.lookup(31));
			errors++;
		}
		
		ht.debugDumpTable();
		System.out.println("Testing Complete Errors Found: " + errors);
	}
	
	/**
	 * debug routine to dump the table to console
	 *
	 */
	private void debugDumpTable() {
		System.out.println("**************************");
		System.out.println("Dumping the Table");
		for (int i = 0; i < buckets.length ; i++) {
			if (buckets[i] != null) {
				System.out.println("Index: " + i+ " " + buckets[i]);
						
			}
		}
		System.out.println("*************************");
	}

	/**
	 * @return the count of search probes
	 */
	public int probeCount() {
		return probeCount;
	}
	
	/**
	 * @param modifier the amount to add to size on regrow
	 */
	public void setRegrowModifier(int modifier) {
		regrowModifier = modifier;
		
	}

}
