import java.util.*;
/**
 * The AhoCorasick class has a static method that takes in a string and dictionary and creates a
 * trie from the dictionary. It then uses the given text and runs the Aho-Corasick algorithm.
 * 
 * @author Joon Ki Hong
 * CS1332-A4
 */
public class AhoCorasick {

	/**
	 * Gets the location of all appearances of each word in dictionary in the given text (locations
	 * should be 0 indexed an example: in the text "Apple Cider" the word "Apple" occurs at index 0)
	 * 
	 * @param text
	 *            the text to search
	 * @param dictionary
	 *            words to search for
	 * @return a map of words to a set of locations
	 */
	public static Map<String, Set<Integer>> matches(String text, Set<String> dictionary) {
		Trie trie = new Trie(dictionary);
		char[] charArray = text.toCharArray();
		Map<String, Set<Integer>> map = new HashMap<String, Set<Integer>>();
		for (int x=0;x<charArray.length;x++){
			Set<String> stringSet = trie.next(charArray[x]);
			for (String output:stringSet){
			
				if (map.containsKey(output)){
					Set<Integer> set = map.get(output);
					set.add(x-output.length()+1);
					map.put(output,set);
				}
				else {
					Set<Integer> set = new LinkedHashSet<Integer>();
					set.add(x-output.length()+1);
					map.put(output, set);
				}
			}
		}
		return map;
	}
}
