/**
 * The RunHuffman is solely used to run the static method of the HuffmanEncoder.
 * @author Joon Ki Hong
 */
public class RunHuffman {

	/**
	 * The main method runs the static method of the HuffmanEncoder with the given
	 * string for the extra credit assignment ("a man a plan panama").
	 * @param args -Not used.
	 */
	public static void main(String[] args){
		System.out.println(HuffmanEncoder.encode("a man a plan panama"));
	}
}
