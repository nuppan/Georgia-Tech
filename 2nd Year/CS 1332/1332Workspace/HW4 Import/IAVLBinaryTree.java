import java.util.List;

/*
 * IAVLBinaryTree.java
 *
 * Version 1.0
 * Copyright 2011 CS1332
 */

/**
 * @author Robert
 * @version 1.0
 * K = the type of the key, this is what we use for adding, searching etc.  That's why it is comparable
 * V = the type of the actual data stored.  for the homework we don't use this for anything.
 * You could imagine in a real application storing GT students in here where the Key would be student
 * numbers and the Value would be objects representing the students.
 */
public interface IAVLBinaryTree<K extends Comparable<? super K>, V> {
    
    /**
     * @return true if this tree is empty, false otherwise
     */
    boolean isEmpty();
    
    /**
     * @return the number of elements currently in this tree
     */
    int size();
    
    /**
     * Adds an item to the tree
     * @param data the item to add to the tree
     */
    void add(K key, V data);
    
    /**
     * Removes an item with the provided key from the tree
     * This method always chooses the predecessor for case 3.
     * @param key the key of the item to remove
     * @return true if something removed, false if data not in tree
     */
    boolean remove(K key);
    
    /**
     * The minimum key in the tree
     * @return the minimum key value
     */
    K min();
    
    /**
     * 
     * @return the maximum key value
     */
    K max();
    
    /**
     * @return the height of the tree
     */
    int height();
    
    /**
     * 
     * @return the key at the root of the tree
     */
    K root();
    
    /**
	 * This is solely for use with drawing tool, we would not normally expose this node.
	 *
     * @return the actual root node == use only for the drawing tool
	 *
     */
    BSTNode getRootNode();
    
    /**
     * find the value with the given key
     * @param key the key to lookup
     * @return the value (data) for this key (or null if key not found)
     */
    V find(K key);
    
    /**
     * @return a list of the Keys in the tree in preorder
     */
    List<K> preorderTraversal();
    
    /**
     * 
     * @return a list of the Keys in the tree in post order
     */
    List<K> postorderTraversal();
    
    /**
     * 
     * @return a list of the keys in inorder
     */
    List<K> inorderTraversal();
    
    /**
     * 
     * @return a list of the keys in level order
     */
    List<K> levelorderTraversal();
    

}
