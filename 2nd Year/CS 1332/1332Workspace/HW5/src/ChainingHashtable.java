/**
 * 
 * @author Joon Ki Hong, Robert
 * CS1332 A4
 * The following class contains various methods and java docing from the original Hashtable class
 * created by the author Robert since the differences are found within the insert, removal, and
 * lookUp methods.
 *
 * The ChainingHashtable class represents a coalesced hash table using modified insert, removal,
 * and lookUp methods.
 */
public class ChainingHashtable<K, V> implements IHashtable<K, V> {
    /**
     * Our actual table array
     */
    private CBucket<K, V>[] CBuckets;
    
    /**
     * the number of entries in the table
     */
    private int entryCount;
    
    /**
     * the number of entries within a chain
     */
    private int entryChainCount;
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
	public ChainingHashtable(int initialSize, double lf) {
    	CBuckets =  new CBucket[initialSize];
    	
    	//mark all the CBuckets as empty
    	for ( int i = 0; i < CBuckets.length ; i++)
    		CBuckets[i] = null;
    	
    	maxloadfactor = lf;
    	entryCount = 0;
    	collisions = 0;
    	regrowCount = 0;
    	entryChainCount = 0;
    }
    /* (non-Javadoc)
	 * @see IHashtable#loadfactor()
	 */
	@Override
	public double loadfactor() {
		return (entryCount * 1.0) / CBuckets.length;
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
		int index = Math.abs(code)  % CBuckets.length;
		boolean found = false;
		
		while (!found) {
			CBucket<K, V> CBucket = CBuckets[index];
			if (CBucket == null) {
				//not in table
				probeCount++;
				return null;
			}
			
			if (CBucket.key.equals(key)) {
				//found
				probeCount++;
				return CBucket.value;
			} else {
				//not at this index, try again
				probeCount++;
				CBucket<K, V> currentCBucket = CBucket;
				boolean foundChain = false;
				while (foundChain==false){
					if (currentCBucket.next==null){
						probeCount++;
						return null;
					}
					else if (currentCBucket.next.key.equals(key)){
						probeCount++;
						foundChain = true;
						return currentCBucket.next.value;
					}
					else{
						probeCount++;
						currentCBucket = currentCBucket.next;
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
		int index = Math.abs(code) % CBuckets.length;
		CBucket<K, V> b = new CBucket<K, V>(key, value);
		boolean found = false;
		
		while (! found) {	
			CBucket<K, V> CBucket = CBuckets[index];
			if (CBucket == null || !CBucket.valid) {
				CBuckets[index] = b;
				entryCount++;
				found = true;
			} else if (CBucket.key.equals(key)) {
				return 1;
			} else {
			  collisions++;
			  CBucket<K,V> currentCBucket = CBuckets[index];
			  boolean foundChain = false;
			  while(foundChain==false){
				  if (currentCBucket.next==null){
					  entryChainCount++;
					  foundChain=true;
				  }
				  else if (currentCBucket.next.key.equals(key)){
					  return 1;
				  }
				  else{
					  currentCBucket = currentCBucket.next;
				  }
			  }
			  currentCBucket.next = b;
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
		int index = Math.abs(code)  % CBuckets.length;
		boolean found = false;
		
		while (!found) {
			CBucket<K, V> CBucket = CBuckets[index];
			if (CBucket == null) {
				//not in table
				return false;
			}
			
			if (CBucket.key.equals(key)) {
				if (CBucket.next!=null){
					CBucket.next = CBuckets[index];
					entryChainCount--;
				}
				else{
					CBuckets[index] = null;
					entryCount--;
				}
				return true;
			} else {
				//not at this index, try again
				CBucket<K,V> currentCBucket = CBucket;
				boolean foundChain = false;
				while (foundChain==false){
					if (currentCBucket.next==null){
						foundChain = true;
						return false;
					}
					else if (currentCBucket.next.key.equals(key)){
						foundChain = true;
						if (currentCBucket.next.next!=null){
							currentCBucket.next=currentCBucket.next.next;
							entryChainCount--;
						}
						else {
							currentCBucket.next = null;
							entryChainCount--;
						}
						return true;
					}
					else{
						currentCBucket = currentCBucket.next;
					}
				}
			}			
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
		return entryCount+entryChainCount;
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
		CBucket<K, V>[] oldtable =  CBuckets;
		CBuckets = new CBucket[(CBuckets.length *2) + regrowModifier];
		entryCount = 0;
		entryChainCount = 0;
		for (int i = 0; i < oldtable.length; i++) {
			CBucket<K, V> CBucket = oldtable[i];
			if (CBucket != null && CBucket.valid) {
				insert(CBucket.key, CBucket.value);
				if (CBucket.next!=null){
					CBucket<K,V> curr = CBucket;
					while(curr.next!=null){
						insert(curr.next.key,curr.next.value);
						curr = curr.next;
					}
				}
			}
		}		
	}
	/**
	 * @return returns the list of CBuckets of the hash table.
	 */
	
	public CBucket<K,V>[] getBuckets(){
		return CBuckets;
	}
}
