import java.util.ArrayList;
/**
 * The HuffmanEncoder class initializes a HuffmanTree and feeds it the text to be processed.
 * It then extracts the necessary data from the tree structure and creates the huffman encoding
 * for the given text.
 * @author Joon Ki Hong
 *
 */
public class HuffmanEncoder {
	/**
	 * The encode static method takes in a string and returns the Huffman encoding of that string.
	 * @param text -The String object to be processed
	 * @return -The resulting encoding.
	 */
	public static String encode(String text){
		HuffmanTree tree = new HuffmanTree(text);
		String encoding = "";
		for (int x = 0;x<text.length();x++){
			char character = text.charAt(x);
			ArrayList<Char> charList = tree.getCharList();
			for (Char charElement:charList){
				if (charElement.getChar()==character){
					encoding = encoding+ charElement.getEncoding();
				}
			}
		}
		return encoding;
	}
}
