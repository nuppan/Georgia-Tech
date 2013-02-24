import java.util.ArrayList;
import java.util.List;
/**
 ** The SparseArray2DImpl<T> class implements the ISparseArray2D<T> interface.
 * This class handles the implementation of the sparse array and contains
 * the methods necessary to add, remove, insert, get, and list Nodes, columns,
 * and rows.
 * CS 1332 A4 - jhong70
 * @author Joon Ki Hong
 *
 * @param <T>
 */

public class SparseArray2DImpl<T> implements ISparseArray2D<T>{
	
	/**
	 * row - holds the number of the rows in the sparse array.
	 */
	private int row;
	/**
	 * column - holds the number of columns in the sparse array.
	 */
	private int column;
	/**
	 * The rowLinkedList variable holds the IndexLinkedList that
	 * refers to the rows of the sparse array.
	 */
	private IndexLinkedList<T> rowLinkedList;
	/**
	 * The colLinkedList variable holds the IndexLinkedList that
	 * refers to the columns of the sparse array.
	 */
	private IndexLinkedList<T> colLinkedList;
	
	
	
	
	/**
	 * The SparseArray2DImpl constructor takes in 2 integer values
	 * for the number of rows and columns and initializes the
	 * respective variables as well as the linked lists.
	 * @param row - the number of rows in the sparse array.
	 * @param column - the number of columns in the sparse array.
	 */
	public SparseArray2DImpl(int row, int column){
		this.row = row;
		this.column = column;
		rowLinkedList = new IndexLinkedList<T>();
		colLinkedList = new IndexLinkedList<T>();
	}
	@Override
	public void putAt(int row, int col, T value) throws ArrayIndexOutOfBoundsException {
		if (row>this.row || col>column){
			throw new ArrayIndexOutOfBoundsException();
		}
		else{
			IndexNode rowNode = checkForIndex(rowLinkedList,row);
			IndexNode colNode = checkForIndex(colLinkedList, col);
			if (rowNode==null){
				rowLinkedList.add(row);
				rowNode = checkForIndex(rowLinkedList,row);
			}
			if (colNode==null){
				colLinkedList.add(col);
				colNode = checkForIndex(colLinkedList, col);
			}
			ArrayNode<T> arrayNode = insertInRow(row,col,value,rowNode);
			insertInCol(row,col,colNode,arrayNode);
			}
	}
	
	private void insertInCol(int row,int col, IndexNode node, ArrayNode<T> arrayNode){
		ArrayNode<T> current = node.head;
		boolean halt = false;
		ArrayNode<T> previous=null;
		while(halt==false){
			if (current==node.head){
				if (node.head==null){
					node.head = arrayNode;
					arrayNode.setColumn(col);
					halt = true;
				}
				else if (row<node.head.row){
					ArrayNode<T> temp = arrayNode;
					temp.setNextRow(node.head);
					arrayNode.setColumn(col);
					node.head = temp;
					halt = true;
				}
				else if (row==node.head.row && col==node.head.column){
					halt = true;
				}
				else{
					current = node.head.nextRow;
					previous = node.head;
				}
			}
			else{
				if (current==null){
					ArrayNode<T> temp = arrayNode;
					previous.setNextRow(temp);
					arrayNode.setColumn(col);
					halt = true;
				}
				else if (row<current.row){
					ArrayNode<T> temp = arrayNode;
					temp.setNextRow(current);
					arrayNode.setColumn(col);
					previous.setNextRow(temp);
					halt = true;
				}
				else if (row==node.head.row && col==node.head.column){
					halt = true;
				}
				else{
					current = current.nextRow;
					previous = previous.nextRow;
				}
			}
		}
	}
	
	private ArrayNode<T> insertInRow(int row,int col, T value, IndexNode node){
		ArrayNode<T> current = node.head;
		boolean halt = false;
		ArrayNode<T> previous=null;
		while(halt==false){
			if (current==node.head){
				if (node.head==null){
					node.head =new ArrayNode(row,col,value);
					node.head.setRow(row);
					halt = true;
					return node.head;
				}
				else if (col<node.head.column){
					ArrayNode<T> temp = new ArrayNode(row,col,value);
					temp.setNextCol(node.head);
					temp.setRow(row);
					node.head = temp;
					halt = true;
					return temp;
				}
				else if (col==node.head.column && row==node.head.row){
					node.head.data = value;
					return node.head;
				}
				else{
					current = node.head.nextCol;
					previous = node.head;
				}
			}
			else{
				if (current==null){
					ArrayNode<T> temp = new ArrayNode(row,col,value);
					previous.setNextCol(temp);
					temp.setRow(row);
					halt = true;
					return temp;
				}
				else if (col<current.column){
					ArrayNode<T> temp = new ArrayNode(row,col,value);
					temp.setNextCol(current);
					previous.setNextCol(temp);
					temp.setRow(row);
					halt = true;
					return temp;
				}
				else if (col==current.column && row==current.row){
					current.data = value;
					return current;
				}
				else{
					current = current.nextCol;
					previous = previous.nextCol;
				}
			}
		}
		return null;
	}
	
	private IndexNode checkForIndex(IndexLinkedList<T> list, int index){
		IndexNode current = list.head;
		boolean halt = false;
		while(halt==false){
			if (current==null){
				return null;
			}
			else if (index==current.index){
				halt = true;
				return current;
			}
			else{
				current = current.next;
				}
			}
		return null;
	}

	@Override
	public T removeAt(int row, int col) throws ArrayIndexOutOfBoundsException {
		if (row>this.row || row<1 || col>column || col<1){
		    throw new ArrayIndexOutOfBoundsException();
		  }
		else if (isEmpty()==true){
			return null;
		}
		else{
		    T returnValue = removeFromRow(row,col);
		    removeFromCol(row,col);
		    
		    return returnValue;
		}
	}
	
	private IndexNode removeFromCol(int row, int col){
		IndexNode current = colLinkedList.head;
	    while (current!=null){
	    	if (current.index == col){
	    		int count = 0;
	    		ArrayNode<T> currently = current.head;
	    		ArrayNode<T> currPrevious = null;
	    		while (currently!=null){
	    			if (count>0){
	    				currPrevious = current.head;
	    			}
	    			if (currently.row == row && currently == current.head){
	    				current.head = currently.nextRow;
	    				return null;
	    			}
	    			else if (currently.row == row){
		    			currPrevious.nextRow = currently.nextRow;
		    			return null;
	    			}
	    			else{
	    				count+=1;
	    				currently = currently.nextRow;
	    				if (count>0){
	    					currPrevious = currPrevious.nextRow;
	    				}
	    			}
	    		}
	    	}
	    	else{
	    		current = current.next;
	    	}
	    }
	    return null;
	}
	
	private T removeFromRow(int row, int col){
		IndexNode current = rowLinkedList.head;
	    while (current!=null){
	    	if (current.index == row){
	    		int count = 0;
	    		ArrayNode<T> currently = current.head;
	    		ArrayNode<T> currPrevious = null;
	    		while (currently!=null){
	    			if (count>0){
	    				currPrevious = current.head;
	    			}
	    			if (currently.column == col && currently == current.head){
	    				T tempData = currently.data;
	    				current.head = currently.nextCol;
	    				return tempData;
	    			}
	    			else if (currently.column == col){
	    				T tempdata = currently.data;
		    			currPrevious.nextCol = currently.nextCol;
		    			return tempdata;
	    			}
	    			else{
	    				count+=1;
	    				currently = currently.nextCol;
	    				if (count>0){
	    					currPrevious = currPrevious.nextCol;
	    				}
	    			}
	    		}
	    	}
	    	else{
	    		current = current.next;
	    	}
	    }
	    return null;
	}
	
	private boolean isEmpty(){
		if (rowLinkedList.head == null && colLinkedList.head == null){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public T getAt(int row, int col) throws ArrayIndexOutOfBoundsException {
		if (row>this.row || col>column){
			throw new ArrayIndexOutOfBoundsException();
		}
		else{
			IndexNode current = rowLinkedList.head;
			while (current!=null){
				if (row==current.index){
					ArrayNode<T> currentNode = current.head;
					while (currentNode!=null){
						if (currentNode.column==col){
							return (T)(currentNode.data);
						}
						else{
							currentNode = currentNode.nextCol;
						}
					}
				}
				else{
					current = current.next;
				}
				
			}
		}
		return null;
	}

	@Override
	public List<T> getRowFor(int row) throws ArrayIndexOutOfBoundsException {
		if (row>this.row || row<0){
			throw new ArrayIndexOutOfBoundsException();
		}
	    
	    ArrayList<T> list = new ArrayList<T>();
	    IndexNode current = rowLinkedList.head;
	    while (current!=null){
	    	if (row==current.index){
	    		ArrayNode<T> currentNode = current.head;
	            while (currentNode!=null){
	            	list.add(currentNode.data);
	                currentNode = currentNode.nextCol;
	            }
	            current = current.next;
	        }
	        else{
	        	current = current.next;
	        }
	    }
		return list ;
	}

	@Override
	public List<T> getColumnFor(int col) throws ArrayIndexOutOfBoundsException {
		if (col>column || col<0){
			throw new ArrayIndexOutOfBoundsException();
		}
	    
	    ArrayList<T> list = new ArrayList<T>();
	    IndexNode current = colLinkedList.head;
	    while (current!=null){
	    	if (col==current.index){
	    		ArrayNode<T> currentNode = current.head;
	            while (currentNode!=null){
	            	list.add(currentNode.data);
	                currentNode = currentNode.nextRow;
	            }
	            current = current.next;
	        }
	        else{
	        	current = current.next;
	        }
	    }
		return list ;
	}

}
