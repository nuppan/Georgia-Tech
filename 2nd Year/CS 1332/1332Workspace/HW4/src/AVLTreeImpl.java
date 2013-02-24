import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import CS1332utils.Display.Draw;
/**
 * The AVLTreeImpl class represents a implementation of an AVL Tree. It can be configured
 * to balance itself out (rotations) or not by passing in a boolean value through the
 * constructor.
 * Collaboration Statement: I worked on this assignment alone.
 * @author Joon Ki Hong
 * CS1332 A4
 * @param <K> The key value used for traversals and comparisons.
 * @param <V> The data stored in each node of the AVL tree.
 */
public class AVLTreeImpl<K extends Comparable<K>, V> implements IAVLBinaryTree<K, V> {
    
	/** A count of the approximate number of compares done */
    private int opsCount;
    /** A boolean value that determines whether or not the tree will be balanced*/
    private boolean rotate;
    /** The root node of the AVL tree*/
    private BSTNode<K,V> root;
    /** The parent node of the predecessor used exclusively for deletion*/
    private BSTNode<K,V> predParent;
    
	/**
	 * Create a new object
	 * @param rotate true if this tree should do rotations, false otherwise
	 */
    public AVLTreeImpl(boolean rotate) {
       this.rotate=rotate;
       root = null;
       predParent=null;
    }
    
	/**
	 * reset the opsCount to zero for a fresh experiment
	 */
    public void resetOpsCount() {
        opsCount = 0;
    }
    
	/**
	 * @return an integer representing the approximate number of compares done
	 */
    public int getOpsCount() {
        return opsCount;
    }

    /**
     * @return true if this tree is empty, false otherwise
     */
	@Override
	public boolean isEmpty() {
		if (root==null){
			return true;
		}
		return false;
	}
	/**
     * Adds an item to the tree
     * @param data the item to add to the tree
     */
	@Override
	public int size() {
		int size = 0;
		if (root==null){
			return 0;
		}
		if (root.getLeft()==null && root.getRight()==null){
			return 1;
		}
		if (root.getLeft()!=null){
			size+= 1+size(root.getLeft());
		}
		if (root.getRight()!=null){
			size+= 1+size(root.getRight());
		}
		return size;
	}
	private int size(BSTNode<K,V> node){
		int size = 0;
		if (node.getLeft()==null && node.getRight()==null){
			return 1;
		}
		if (node.getLeft()!=null){
			size+=size(node.getLeft())+1;
		}
		if (node.getRight()!=null){
			size+=size(node.getRight())+1;
		}
		return size;
	}
	/**
     * Adds an item to the tree
     * @param data the item to add to the tree
     */
	@Override
	public void add(K key, V data) {
		opsCount++;
		if (root==null){
			root = new BSTNode(key,data);
		}
		else{
			addEntry(root,key,data);
			root.height();
			root.balanceFactor();
			if (rotate==true){
				root = rebalance(root);
			}
		}
	}
	private void addEntry(BSTNode<K,V> node, K key, V data){
		opsCount++;
		if (key.compareTo(node.key)<0){
			if (node.getLeft()!=null){
				addEntry(node.getLeft(),key,data);
				node.height();
				node.balanceFactor();
				if (rotate==true){
					node.setLeft(rebalance(node.getLeft()));
				}
			}
			else{
				node.setLeft(new BSTNode(key,data));
			}
		}
		else{
			if (node.getRight()!=null){
				addEntry(node.getRight(),key,data);
				node.height();
				node.balanceFactor();
				if (rotate==true){
					node.setRight(rebalance(node.getRight()));
				}
			}
			else{
				node.setRight(new BSTNode(key,data));
			}
		}
	}
	/**
     * Removes an item with the provided key from the tree
     * This method always chooses the predecessor for case 3.
     * @param key the key of the item to remove
     * @return true if something removed, false if data not in tree
     */
	@Override
	public boolean remove(K key) {
		if (find(key)==null){
			return false;
		}
		else{
			root = remove(root,key);
			return true;
		}
	}
	private BSTNode<K,V> remove(BSTNode<K,V> node, K key){
		if (node!=null){
			if (node.key.compareTo(key)>0){
				node.setLeft(remove(node.getLeft(),key));
				node.height();
				node.balanceFactor();
				if (rotate==true){
					node = rebalance(node);
				}
			}
			else if (node.key.compareTo(key)<0){
				node.setRight(remove(node.getRight(),key));
				node.height();
				node.balanceFactor();
				if (rotate==true){
					node = rebalance(node);
				}
			}
			else{
				if (node.getLeft()==null){
					node = node.getRight();
				}
				else if (node.getRight()==null){
					node = node.getLeft();
				}
				else{
					BSTNode<K,V> pred = getPred(node);
					node.key = pred.key;
					node.data = pred.data;
					predParent.setRight(null);
					predParent=null;
					node.setRight(remove(node.getRight(),node.key));
					node.height();
					node.balanceFactor();
					if (rotate==true){
						node = rebalance(node);
					}
				}
			}
		}
		return node;
	}
	
	private BSTNode<K,V> getPred(BSTNode<K,V> node){
		if (node.getLeft().getRight()==null){
			return node.getLeft();
		}
		else{
			return getPredHelper(node.getLeft());
		}
	}
	private BSTNode<K,V> getPredHelper(BSTNode<K,V> node){
		if (predParent==null && node.getRight().getRight()==null){
			predParent = node;
		}
		if (node.getRight()==null){
			return node;
		}
		else {
			return getPredHelper(node.getRight());
		}
	}

	private BSTNode<K,V> rebalance(BSTNode<K,V> node){
		if (node.balanceFactor()>1){
			if (node.getLeft().balanceFactor()>=0){
				node = rotateRight(node);
			}
			else{
				node = rotateLeftRight(node);
			}
		}
		else if (node.balanceFactor()<-1){
			if (node.getRight().balanceFactor()<=0){
				node = rotateLeft(node);
			}
			else{
				node = rotateRightLeft(node);
			}
		}
		return node;
	}
	
	private BSTNode<K,V> rotateLeft(BSTNode<K,V> node){
		BSTNode<K,V> newRoot = node.getRight();
		node.setRight(newRoot.getLeft());
		newRoot.setLeft(node);
		newRoot.getLeft().height();
		newRoot.getLeft().balanceFactor();
		newRoot.height();
		newRoot.balanceFactor();
		node.height();
		node.balanceFactor();
		return newRoot;
	}
	private BSTNode<K,V> rotateRight(BSTNode<K,V> node){
		BSTNode<K,V> newRoot = node.getLeft();
		node.setLeft(newRoot.getRight());
		newRoot.setRight(node);
		newRoot.getRight().height();
		newRoot.getRight().balanceFactor();
		newRoot.height();
		newRoot.balanceFactor();
		return newRoot;
	}
	private BSTNode<K,V> rotateLeftRight(BSTNode<K,V> node){
		BSTNode<K,V> newRoot = node.getLeft().getRight();
		node.getLeft().setRight(newRoot.getLeft());
		newRoot.setLeft(node.getLeft());
		node.setLeft(newRoot);
		
		
		node.getLeft().getLeft().height();
		node.getLeft().getLeft().balanceFactor();
		node.getLeft().height();
		node.getLeft().balanceFactor();
		node.height();
		node.balanceFactor();
		node = rotateRight(node);
		return node;
	}
	private BSTNode<K,V> rotateRightLeft(BSTNode<K,V> node){
		BSTNode<K,V> newRoot = node.getRight().getLeft();
		node.getRight().setLeft(newRoot.getRight());
		newRoot.setRight(node.getRight());
		node.setRight(newRoot);
		node.getRight().getRight().height();
		node.getRight().getRight().balanceFactor();
		node.getRight().height();
		node.getRight().balanceFactor();
		node.height();
		node.balanceFactor();
		node = rotateLeft(node);
		return node;
	}
	
	@Override
	public K min() {
		opsCount++;
		if (root==null){
			return null;
		}
		else {
			return min(root,root.key);
		}
	}
	/**
     * The minimum key in the tree
     * @return the minimum key value
     */
	private K min(BSTNode<K,V> node, K key){
		opsCount++;
		if (node.getLeft()==null){
			return key;
		}
		else{
			return min(node.getLeft(),node.getLeft().key);
		}
	}
	 /**
     * 
     * @return the maximum key value
     */
	@Override
	public K max() {
		opsCount++;
		if (root==null){
			return null;
		}
		else {
			return max(root,root.key);
		}
	}
	private K max(BSTNode<K,V> node, K key){
		opsCount++;
		if (node.getRight()==null){
			return key;
		}
		else{
			return max(node.getRight(),node.getRight().key);
		}
	}
	/**
     * @return the height of the tree
     */
	@Override
	public int height() {
		if (root==null){
			return -1;
		}
		else {
			return root.getHeight();
		}
	}
	/**
     * 
     * @return the key at the root of the tree
     */
	@Override
	public K root() {
		if (root==null){
			return null;
		}
		else{
			return root.key;
		}
	}
	/**
	 * This is solely for use with drawing tool, we would not normally expose this node.
	 *
     * @return the actual root node == use only for the drawing tool
	 *
     */
	@Override
	public BSTNode<K,V> getRootNode() {
		return root;
	}
	/**
     * find the value with the given key
     * @param key the key to lookup
     * @return the value (data) for this key (or null if key not found)
     */
	@Override
	public V find(K key) {
		opsCount++;
		if (root==null){
			return null;
		}
		else if (key.compareTo(root.key)==0){
			return root.data;
		}
		else if (key.compareTo(root.key)<0){
			return find(root.getLeft(),key);
		}
		else{
			return find(root.getRight(),key);
		}
	}
	private V find(BSTNode<K,V> node, K key){
		opsCount++;
		if (node==null){
			return null;
		}
		else if (key.compareTo(node.key)==0){
			return node.data;
		}
		else if (key.compareTo(node.key)<0){
			return find(node.getLeft(),key);
		}
		else{
			return find(node.getRight(),key);
		}
	}
    /**
     * @return a list of the Keys in the tree in preorder
     */
	@Override
	public List<K> preorderTraversal() {
		List<K> keyList = new ArrayList<K>();
		preorderTraversal(root,keyList);
		return keyList;
	}
	private void preorderTraversal(BSTNode<K,V> node, List<K> list){
		if (node!=null){
			list.add(node.key);
			preorderTraversal(node.getLeft(),list);
			preorderTraversal(node.getRight(),list);
		}
	}
	/**
     * 
     * @return a list of the Keys in the tree in post order
     */
	@Override
	public List<K> postorderTraversal() {
		List<K> keyList = new ArrayList<K>();
		postorderTraversal(root,keyList);
		return keyList;
	}
	private void postorderTraversal(BSTNode<K,V> node, List<K> list){
		if (node!=null){
			postorderTraversal(node.getLeft(),list);
			postorderTraversal(node.getRight(),list);
			list.add(node.key);
		}
	}
	/**
     * 
     * @return a list of the keys in inorder
     */
	@Override
	public List<K> inorderTraversal() {
		List<K> keyList = new ArrayList<K>();
		inorderTraversal(root,keyList);
		return keyList;
	}
	private void inorderTraversal(BSTNode<K,V> node, List<K> list){
		if (node!=null){
			inorderTraversal(node.getLeft(),list);
			list.add(node.key);
			inorderTraversal(node.getRight(),list);
		}
	}
	/**
     * 
     * @return a list of the keys in level order
     */
	@Override
	public List<K> levelorderTraversal() {
		List<K> keyList = new ArrayList<K>();
		return levelorderTraversal(root,keyList);
	}
	private List<K> levelorderTraversal(BSTNode<K,V> node,List<K> list){
		if (root==null){
			return list;
		}
		LinkedList<BSTNode<K,V>> linkedList = new LinkedList<BSTNode<K,V>>();
		linkedList.add(node);
		while(!linkedList.isEmpty()){
			node = linkedList.remove(0);
			list.add(node.key);
			if (node.getLeft()!=null){
				linkedList.add(node.getLeft());
			}
			if (node.getRight()!=null){
				linkedList.add(node.getRight());
			}
		}
		return list;
	}

   
    
	/**
	 * Run some experiments and draw the trees.  This code does not require removal to be working
	 */
    public static void main(String[] args) {
        AVLTreeImpl<Integer, String> regTree = new AVLTreeImpl<Integer, String>(false);
        AVLTreeImpl<Integer, String> avlTree = new AVLTreeImpl<Integer, String>(true);
        
        System.out.println("**************************************");
        System.out.println("  RANDOM DATA");
		System.out.println("**************************************");
		//use a canned seed here so we always get same randoms
        Random rand = new Random(34539);
        int n;
		//add 50 random numbers
        for (int i = 0; i < 50 ; i++) {
            n = rand.nextInt(1000);
            regTree.add(n, "a" + i);
            avlTree.add(n, "a" + i);
        }
        
        System.out.println("Regular Tree Add Count: " + regTree.getOpsCount());
        System.out.println("AVL Tree Add Count: " + avlTree.getOpsCount());
        regTree.resetOpsCount();
        avlTree.resetOpsCount();
        Draw.dispTree(regTree.root);
        Draw.dispTree(avlTree.root);
        
        regTree.min();
        avlTree.min();
        System.out.println("Regular Tree Min Count: " + regTree.getOpsCount());
        System.out.println("AVL Tree Min Count: " + avlTree.getOpsCount());
        regTree.resetOpsCount();
        avlTree.resetOpsCount();
        
		//Now try to find 50 random numbers
        for (int i = 0; i < 50; i++) {
            n = rand.nextInt(1000);
            regTree.find(n);
            avlTree.find(n);
        }
        
        System.out.println("Regular Tree Find Count: " + regTree.getOpsCount());
        System.out.println("AVL Tree Find Count: " + avlTree.getOpsCount());
        regTree.resetOpsCount();
        avlTree.resetOpsCount();
        
        System.out.println("Regular Tree Height = " + regTree.height());
        System.out.println("AVL Tree Height = " + avlTree.height());
        
        System.out.println("******************************");
        System.out.println("  SORTED DATA");
		System.out.println("******************************");
        
        regTree = new AVLTreeImpl<Integer, String>(false);
        avlTree = new AVLTreeImpl<Integer, String>(true);
        
		//Now add 50 numbers in order
        for (int i = 1; i < 50; i++) {
            regTree.add(i, "a" + i);
            avlTree.add(i, "a" + i);
        }
        
        System.out.println("Regular Tree Add Count: " + regTree.getOpsCount());
        System.out.println("AVL Tree Add Count: " + avlTree.getOpsCount());
        regTree.resetOpsCount();
        avlTree.resetOpsCount();
        
        Draw.dispTree(regTree.root);
        Draw.dispTree(avlTree.root);
        
        regTree.max();
        avlTree.max();
        System.out.println("Regular Tree Max Count: " + regTree.getOpsCount());
        System.out.println("AVL Tree Max Count: " + avlTree.getOpsCount());
        regTree.resetOpsCount();
        avlTree.resetOpsCount();
        
		//Now find 50 random numbers 
        for (int i = 0; i < 50; i++) {
            n = rand.nextInt(50);
            regTree.find(n);
            avlTree.find(n);
        }
        
        System.out.println("Regular Tree Find Count: " + regTree.getOpsCount());
        System.out.println("AVL Tree Find Count: " + avlTree.getOpsCount());
        regTree.resetOpsCount();
        avlTree.resetOpsCount();
        
        System.out.println("Regular Tree Height = " + regTree.height());
        System.out.println("AVL Tree Height = " + avlTree.height());
            
    }

}