/**
 * 
 * @author Joon Ki Hong, Robert
 * CS1332 A4
 * The following class contains various methods and java docing from the original Hashtable class
 * created by the author Robert since the differences are found within the insert, removal, and
 * lookUp methods.
 *
 * The CoalescedHashtable class represents a coalesced hash table using modified insert, removal,
 * and lookUp methods.
 */
public class CoalescedHashtable<K, V> implements IHashtable<K, V> {
    /**
     * Our actual table array
     */
    private CBucket<K, V>[] buckets;
    
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
	public CoalescedHashtable(int initialSize, double lf) {
    	buckets =  new CBucket[initialSize];
    	
    	//mark all the buckets as empty
    	for ( int i = 0; i < buckets.length ; i++)
    		buckets[i] = null;
    	
    	maxloadfactor = lf;
    	entryCount = 0;
    	collisions = 0;
    	regrowCount = 0;
    }
    /* (non-Javadoc)
	 * @see IHashtable#loadfactor()
	 */
	@Override
	public double loadfactor() {
		return (entryCount * 1.0) / buckets.length;
	}
	/**
	 * @param modifier the amount to add to size on regrow
	 */
	@Override
	public void setRegrowModifier(int modifier) {
		regrowModifier = modifier;
	}
	/* (non-Javadoc)
	 * @see IHashtable#lookup(java.lang.Object)
	 */
	@Override
	public V lookup(K key) {
		int code = key.hashCode();
		int index = Math.abs(code)  % buckets.length;
		boolean found = false;
		
		while (!found) {
			CBucket<K, V> bucket = buckets[index];
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
				CBucket<K,V> currentBucket = bucket;
				boolean foundChain = false;
				while (foundChain==false){
					if (currentBucket.next==null){
						probeCount++;
						return null;
					}
					else if (currentBucket.next.key.equals(key)){
						probeCount++;
						foundChain = true;
						return currentBucket.next.value;
					}
					else{
						probeCount++;
						currentBucket = currentBucket.next;
					}
				}
			}			
		}	
		return null;
	}
	/* (non-Javadoc)
	 * @see IHashtable#insert(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int insert(K key, V  value) {
		int code = key.hashCode();
		int index = Math.abs(code) % buckets.length;
		CBucket<K, V> b = new CBucket<K, V>(key, value);
		boolean found = false;
		boolean collided = false;
		while (! found) {	
			CBucket<K, V> bucket = buckets[index];
			if (bucket == null || !bucket.valid) {
				buckets[index] = b;
				entryCount++;
				found = true;
			} else if (bucket.key.equals(key)) {
				return 1;
			} else {
				collided = true;
				collisions++;
				CBucket<K,V> currentBucket = buckets[index];
				boolean foundChain = false;
				while(foundChain==false){
					if (currentBucket.next==null){
						entryCount++;
						foundChain=true;
					}
					else if (currentBucket.next.key.equals(key)){
						return 1;
					}
					else{
						currentBucket = currentBucket.next;
					}
				}
				currentBucket.next = b;
				if (collided==true){
					boolean foundIndex = false;
					while (foundIndex==false){
						if (buckets[index]!=null){
							index = (index+1)%buckets.length;
						}
						else{
							buckets[index] = b;
							foundIndex=true;
						}
					}
				}
				found = true;
			}
		}
		
		if (loadfactor()> maxloadfactor) {
			regrow();
			regrowCount++;
		}
		
		return 0;
			
    }
	/* (non-Javadoc)
	 * @see IHashtable#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(K key) {
		int code = key.hashCode();
		int index = Math.abs(code)  % buckets.length;
		boolean found = false;
		
		while (!found) {
			CBucket<K, V> bucket = buckets[index];
			
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
	 * @see IHashtable#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return entryCount == 0;
	}
	/* (non-Javadoc)
	 * @see IHashtable#size()
	 */
	@Override
	public int size() {
		return entryCount;
	}
	/* (non-Javadoc)
	 * @see IHashtable#collisions()
	 */
	@Override
	public int collisions() {
		return collisions;
	}
	/* (non-Javadoc)
	 * @see IHashtable#regrowCount()
	 */
	@Override
	public int regrowCount() {
		return regrowCount;
	}
	/**
	 * @return the count of search probes
	 */
	@Override
	public int probeCount() {
		return probeCount;
	}
	 /**
     * regrow the hashtable after the loadfactor is reached
     * double the size and rehash entries
     */
	@SuppressWarnings("unchecked")
	private void regrow() {
		CBucket<K, V>[] oldtable =  buckets;
		buckets = new CBucket[(buckets.length *2) + regrowModifier];
		entryCount = 0;
		for (int i = 0; i < oldtable.length; i++) {
			CBucket<K, V> bucket = oldtable[i];
			if (oldtable[i]!=null){
				oldtable[i].next = null;
			}
			if (bucket != null && bucket.valid) {
				insert(bucket.key, bucket.value);
			}
		}		
	}
	/**
	 * @return returns the list of buckets of the hash table.
	 */
	public CBucket<K,V>[] getBuckets(){
		return buckets;
	}
}
