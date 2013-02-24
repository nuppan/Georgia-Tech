/**
 * The HuffmanNode class represents a either a parent or leaf node in a Huffman tree 
 * structure. 
 * @author Joon Ki Hong
 *
 */
public class HuffmanNode implements Comparable<HuffmanNode>{
	/**
	 * The frequency of the character
	 */
	private int frequency;
	/**
	 * The character of the node.
	 */
	private char character;
	/**
	 * If this is a parent node, the left variable represents the left child node.
	 */
	private HuffmanNode left;
	/**
	 * If this a parent node, the right variable represents the right child node.
	 */
	private HuffmanNode right;
	/**
	 * Holds the encoding value of the node (either a 1 or 0).
	 */
	private String value;
	/**
	 * The parent node of this instance.
	 */
	private HuffmanNode parentNode;
	
	/**
	 * The constructor takes in the frequency and the character, initializes them and
	 * sets the left, right, and parent node to null.
	 * @param freq -The frequency of the character.
	 * @param character -The character of the node being assigned.
	 */
	public HuffmanNode(int freq, char character){
		parentNode=null;
		frequency = freq;
		this.character = character;
		left = null;
		right = null;
	}
	/**
	 * This secondary constructor is used for parent nodes that do not contain any
	 * characters. For these instances, the '/' character is used to indicate that 
	 * it is a parent node.
	 */
	public HuffmanNode(){
		parentNode=null;
		frequency = 0;
		left = null;
		right = null;
		character = '/';
	}
	
	/**
	 * The equals method overrides the Object equals method and uses the character of
	 * each node to compare whether or not the HuffmanNode objects are equal
	 * @param -A HuffmanNode object used for comparison
	 * @return -The boolean value that describes the equality of the two objects.
	 */
	@Override
	public boolean equals(Object node){
		HuffmanNode hNode = (HuffmanNode)node;
		if (hNode.getChar()==this.getChar()){
			return true;
		}
		return false;
	}

	/**
	 * The compareTo method uses frequency and ascii values of each character/node to 
	 * determine whether or not this instance of a HuffmanNode is greater, less, or equal
	 * to the node being passed in.
	 * @param -The HuffmanNode used for comparison
	 * @return -The integer describing the relationship between the two nodes.
	 */
	@Override
	public int compareTo(HuffmanNode node) {
		if (frequency<node.getFreq()){
			return -1;
		}
		else if (frequency>node.getFreq()){
			return 1;
		}
		else if (frequency==node.getFreq() && character<node.getChar()){
			return -2;
		}
		else if (frequency==node.getFreq() && character>node.getChar()){
			return 2;
		}
		return 0;
	}
	
	//Getters and setters below
	
	/**
	 * The getFreq method returns the frequency.
	 * @return returns -The frequency.
	 */
	public int getFreq(){
		return frequency;
	}
	/**
	 * The getChar method returns the character of the node.
	 * @return -The character.
	 */
	public char getChar(){
		return character;
	}
	/**
	 * The incrementFreq method increments the frequency by one.
	 */
	public void incrementFreq(){
		frequency+=1;
	}
	/**
	 * The setLeft method sets a node as the left child of this instance. It also sets
	 * the value of the left child as 0.
	 * @param node -The HuffmanNode to be set as the left child.
	 */
	public void setLeft(HuffmanNode node){
		left = node;
		node.setValue("0");
	}
	/**
	 * The setRight method sets a node as the right child of this instance. It also sets
	 * the value of the right child as 1.
	 * @param node -The HuffmanNode to be set as the right child.
	 */
	public void setRight(HuffmanNode node){
		right = node;
		node.setValue("1");
	}
	/**
	 * The getLeft method returns the left child.
	 * @return -The left child node.
	 */
	public HuffmanNode getLeft(){
		return left;
	}
	/**
	 * The getRight method returns the right child.
	 * @return -The right child node.
	 */
	public HuffmanNode getRight(){
		return right;
	}
	/**
	 * The getValue method returns the value of this node.
	 * @return -The value of the node.
	 */
	public String getValue(){
		return value;
	}
	/**
	 * The setValue method sets the value of the node.
	 * @param value -The value to be set.
	 */
	public void setValue(String value){
		this.value = value;
	}
	/**
	 * The setFreq method sets the frequency of the node.
	 * @param freq -The frequency to be set.
	 */
	public void setFreq(int freq){
		frequency = freq;
	}
	/**
	 * The getParent method returns the parent node.
	 * @return -The parent node.
	 */
	public HuffmanNode getParent(){
		return parentNode;
	}
	/**
	 * The setParent method sets the parent node with the one passed in.
	 * @param node -The node set to be the parent.
	 */
	public void setParent(HuffmanNode node){
		parentNode=node;
	}
}
