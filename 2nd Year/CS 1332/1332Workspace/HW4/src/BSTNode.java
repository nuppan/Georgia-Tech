import CS1332utils.Interfaces.BinNode32;
/**
 * The BSTNode class takes in two parameters, one as the key and the other as
 * the value to hold in the node. The class itself represents a node in an
 * AVL tree implementation.
 * Collaboration Statement: I worked on this assignment alone.
 * @author Joon Ki Hong
 * CS1332 A4
 * @param <K> The key value used for traversals and comparisons.
 * @param <V> The data stored in each node of the AVL tree.
 */

public class BSTNode<K extends Comparable<K>, V> implements
CS1332utils.Interfaces.BinNode32<Comparable>
 {
	/** The key value used for comparisons in traversals */
	protected K key;
	/** The data value the node holds */
	protected V data;
	/** The left child node of this instance of the BSTNode */
	private BSTNode<K,V> leftChild;
	/** The right child node of this instance of the BSTNode */
	private BSTNode<K,V> rightChild;
	/** The height of the node in the AVL tree */
	private int height;
	/** The balance factor of the node in the AVL tree */
	private int balanceFactor;
	
	/**
	 * The constructor initializes the key and data as the values passed
	 * in the parameters, sets the height and balance factor to 0, and
	 * the left and right child as null.
	 * @param key the key value used for comparisons in traversals
	 * @param data the data value the node holds.
	 */
    //you will need to add any instance data you think you need here
	public BSTNode(K key, V data){
		this.key=key;
		this.data=data;
		height=0;
		balanceFactor=0;
		leftChild=null;
		rightChild=null;
	}
    
	/**
	 * @return an integer representing the height of this node
	 */
    public int height() {
       if (leftChild==null && rightChild==null){
    	   height = 0;
       }
       else if (leftChild==null){
    	   height = rightChild.height()+1;
       }
       else if (rightChild==null){
    	   height = leftChild.height()+1;
       }
       else{
    	   height = Math.max(leftChild.height(), rightChild.height())+1;
       }
       return height;
    }
    /**
     * A getter for the height.
     * @return an integers representing the height. Returns the already stored value.
     */
    public int getHeight(){
    	return height;
    }
    
	/**
	 * @return an integer representing the balance factor of this node
	 */
    public int balanceFactor() {
        if (leftChild==null && rightChild==null){
        	balanceFactor = 0;
        }
        else if (leftChild==null){
        	balanceFactor = -1-rightChild.height;
        }
        else if (rightChild==null){
        	balanceFactor = leftChild.height+1;
        }
        else{
        	balanceFactor = leftChild.height-rightChild.height;
        }
        return balanceFactor;
    }
    
	/**
	 * @return true if this node is a leaf node, false otherwise
	 */
    public boolean isLeaf() {
        if (leftChild==null && rightChild==null){
        	return true;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see CS1332utils.Interfaces.BinNode32#getContents()
     */
    @Override
    public Comparable getContents() {
    	return key;
    }

    /* (non-Javadoc)
     * @see CS1332utils.Interfaces.BinNode32#getLeft()
     */
    @Override
    public BSTNode<K, V> getLeft() {
    	return leftChild;
    }

    /* (non-Javadoc)
     * @see CS1332utils.Interfaces.BinNode32#getRight()
     */
    @Override
    public BSTNode<K, V> getRight() {
    	return rightChild;
    }

    /* (non-Javadoc)
     * @see CS1332utils.Interfaces.BinNode32#setContents(java.lang.Comparable)
     */
    @Override
    public void setContents(Comparable arg0) {
    	if(arg0==null||arg0.getClass().equals(key.getClass()))
    		key = (K) arg0;
    }

    /* (non-Javadoc)
     * @see CS1332utils.Interfaces.BinNode32#setLeft(CS1332utils.Interfaces.BinNode32)
     */
    @Override
    public void setLeft(BinNode32<Comparable> arg0) {
    	if(arg0 == null || arg0.getClass().equals(this.getClass()))
    		leftChild = (BSTNode<K, V>) arg0;
    }

    /* (non-Javadoc)
     * @see CS1332utils.Interfaces.BinNode32#setRight(CS1332utils.Interfaces.BinNode32)
     */
    @Override
    public void setRight(BinNode32<Comparable> arg0) {
    	if(arg0 == null || arg0.getClass().equals(this.getClass()))
    		rightChild = (BSTNode<K, V>) arg0;
    }

   
}
