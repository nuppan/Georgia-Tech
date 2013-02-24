/**
 * The IndexLinkedList<T> class represents the Linked List intended
 * to represent the rows and columns for a sparse array.
 * CS 1332 A4 - jhong70
 * @author Joon Ki Hong
 * CS 1332 A4 - jhong70
 * @param <T>
 */
public class IndexLinkedList<T>{
	/**
	 * head - holds the first IndexNode that refers to the first row
	 * or column  of the sparse array.
	 */
	protected IndexNode head;
	
	/**
	 * The IndexLinkedList constructor does not take in any parameters
	 * and simply just initializes the head to null;
	 */
	public IndexLinkedList(){
		head=null;
	}
	/**
	 * The add method takes in an index and initializes an IndexNode
	 * and adds the IndexNode containing the index(row/column number) to 
	 * the IndexLinkedList instance.
	 * @param index - the index is the row/column number depending on
	 * what the Linked List represents (row/column).
	 */
	
	public void add(int index){
		boolean halt=false;
		IndexNode current = head;
		IndexNode previous=null;
		while(halt==false){
			if (current==head){
				if (head==null){
					head = new IndexNode(index);
					halt = true;
				}
				else if (index<head.index){
					IndexNode temp = new IndexNode(index);
					temp.setNext(head);
					this.head = temp;
					halt = true;
				}
				else{
					current = head.next;
					previous = head;
				}
			}
			else{
				if (current==null){
					IndexNode temp = new IndexNode(index);
					previous.setNext(temp);
					halt = true;
				}
				else if (index<current.index){
					IndexNode temp = new IndexNode(index);
					temp.setNext(current);
					previous.setNext(temp);
					halt = true;
				}
				else{
					current = current.next;
					previous = previous.next;
				}
			}
		}
	}
}
