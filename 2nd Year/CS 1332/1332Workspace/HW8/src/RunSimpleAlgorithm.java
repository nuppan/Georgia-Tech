import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class RunSimpleAlgorithm {
	public static void main(String[] args) throws FileNotFoundException{
		String text = "abcdebeabcabebcde";
		Set<String> dictionary = new HashSet<String>();
		dictionary.add("a");
		dictionary.add("abc");
		dictionary.add("be");
		dictionary.add("bcd");
		dictionary.add("abcd");
		dictionary.add("cde");
		dictionary.add("abe");
		Map<String, Set<Integer>> map = AhoCorasick.matches(text, dictionary);
		System.out.println(map.keySet());
		System.out.println(map);
	}
}