
import java.util.ArrayList;
import java.util.PriorityQueue;
/**
 * The HuffmanTree class represents the tree structure of the Huffman Encoding process. It
 * takes in a String object and creates the tree structure as well as the encoding for each
 * character in that string.
 * @author Joon Ki Hong
 *
 */
public class HuffmanTree {
	/**
	 * The priority queue used help create the tree structure.
	 */
	private PriorityQueue<HuffmanNode> queue;
	/**
	 * The number of valid character nodes in the tree.
	 */
	private int size;
	/**
	 * The list of Char objects that retain each unique character as well as their encoding.
	 */
	private ArrayList<Char> charList;
	/**
	 * The nodeList is a list of all references to each character node, used for retrieving
	 * the encoding.
	 */
	private ArrayList<HuffmanNode> nodeList;
	
	/**
	 * The constructor takes in a String object and creates the tree structure as well as the
	 * encoding.
	 * @param text -The String to be encoded.
	 */
	public HuffmanTree(String text){
		charList = new ArrayList<Char>();
		queue = new PriorityQueue<HuffmanNode>();
		nodeList = new ArrayList<HuffmanNode>();
		size = 0;
		for (int x=0;x<text.length();x++){
			HuffmanNode tempNode = new HuffmanNode(1,text.charAt(x));
			if (!queue.contains(tempNode)){
				HuffmanNode newNode = new HuffmanNode(1,text.charAt(x));
				nodeList.add(newNode);
				queue.add(newNode);
				charList.add(new Char(text.charAt(x)));
				size++;
			}
			else{
				for (HuffmanNode node:queue){
					if (node.getChar()==text.charAt(x)){
						node.incrementFreq();
					}
				}
			}
		}
		while (queue.size()!=1){
			HuffmanNode leftNode = queue.poll();
			HuffmanNode rightNode = queue.poll();
			HuffmanNode parentNode = new HuffmanNode();
			parentNode.setFreq(leftNode.getFreq()+rightNode.getFreq());
			parentNode.setLeft(leftNode);
			leftNode.setParent(parentNode);
			parentNode.setRight(rightNode);
			rightNode.setParent(parentNode);
			queue.add(parentNode);
		}
		for (Char charElement:charList){
			String encoding = "";
			for (HuffmanNode node:nodeList){
				if (node.getChar()==charElement.getChar()){
					boolean halt = false;
					HuffmanNode root = node;
					while (halt==false){
						if (root.getParent()!= null){
							encoding = encoding + root.getValue();
							root = root.getParent();
						}
						else {
							halt = true;
						}
					}
				}
			}
			String reverse = "";
			for (int y=encoding.length()-1;y>=0;y--){
				reverse = reverse+encoding.charAt(y);
			}
			encoding = reverse;
			charElement.setEncoding(encoding);
		}
		
		
	}
	/**
	 * The getTree method returns the tree structure within an ArrayList. After it has been
	 * encoded, there should only be one element in the queue.
	 * @return -The priority queue containing the tree structure.
	 */
	public PriorityQueue<HuffmanNode> getTree(){
		return queue;
	}
	/**
	 * The size method returns the number of nodes containing valid characters.
	 * @return -The size of the structure.
	 */
	public int size(){
		return size;
	}
	/**
	 * The getCharList method returns the array list of Char objects that contains the encoding
	 * for each character.
	 * @return -The array list of Char objects.
	 */
	public ArrayList<Char> getCharList(){
		return charList;
	}
	
}
