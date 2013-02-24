import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class RunAlgorithm {
	public static void main(String[] args) throws FileNotFoundException{
		Scanner scanner = new Scanner(new File("raven.txt"));
		String text = "";
		int counter = 0;
		scanner.nextLine();
		while (counter<=125){
			text = text + scanner.nextLine() + "\n";
			counter++;
		}
		Set<String> dictionary = new HashSet<String>();
		dictionary.add("door");
		dictionary.add("float");
		dictionary.add("raven");
		Map<String, Set<Integer>> map = AhoCorasick.matches(text, dictionary);
		System.out.println(map.keySet());
		System.out.println(map);
	}
}
