/**
 * The IndexNode class represents a node within an IndexLinkedList
 * representing individual row nodes or column nodes. These IndexNodes
 * also refer an ArrayNode.
 * @author Joon Ki Hong
 * CS 1332 A4 - jhong70
 *
 */
public class IndexNode {
	/**
	 * index - the index refers to the column or row number
	 * that the IndexNode represents in the linked list.
	 */
	protected int index;
	/**
	 * next - the next variable is a pointer to the next
	 * IndexNode which corresponds to the next row or column
	 */
	protected IndexNode next;
	/**
	 * head - The head variable points to the first ArrayNode
	 * which contains the same index as the IndexNode.
	 */
	protected ArrayNode head;
	
	/**
	 * The IndexNode constructor takes in an integer and
	 * initializes the index variable and sets the next
	 * variable to null.
	 * @param index the index parameter passed in refers
	 * to the row/column number.
	 */
	public IndexNode(int index){
		this.index = index;
		next = null;
	}
	/**
	 * The setNext method takes in an IndexNode and sets it
	 * as the next variable essentially setting a pointer
	 * from this instance to the node passed in.
	 * @param node the node parameter refers to the node to
	 * initialize the next variable.
	 */
	public void setNext(IndexNode node){
		next = node;
	}
	
	
}
