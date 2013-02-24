import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;

public class AhoCorasickTests {
	private String text;
	private Set<String> dictionary;
	
	@Before
	public void setUp(){
		text = "abcdebeabcabebcde";
		dictionary = new HashSet<String>();
		dictionary.add("a");
		dictionary.add("abc");
		dictionary.add("be");
		dictionary.add("bcd");
		dictionary.add("abcd");
		dictionary.add("cde");
		dictionary.add("abe");
	}
	@Test
	public void TestAlgorithm(){
		Map<String, Set<Integer>> map = AhoCorasick.matches(text, dictionary);
		assertEquals("The map's output is incorrect - TestAlgorithm","{abc=[0, 7], abe=[10], a=[0, 7, 10], bcd=[1, 13], abcd=[0], be=[5, 11], cde=[2, 14]}",map.toString());
	}
	
	@Test
	public void Test1(){
		dictionary.add("hello!");
		Map<String, Set<Integer>> map = AhoCorasick.matches(text, dictionary);
		assertEquals("The map's output is incorrect - Test1","{abc=[0, 7], abe=[10], a=[0, 7, 10], bcd=[1, 13], abcd=[0], be=[5, 11], cde=[2, 14]}",map.toString());
	}
	
	@Test
	public void Test2(){
		Set<String> emptyDict = new HashSet<String>();
		Map<String, Set<Integer>> map = AhoCorasick.matches(text, emptyDict);
		assertEquals("The map's output is incorrect - Test2","{}",map.toString());
	}
	
	@Test
	public void Test3(){
		String emptyText = "";
		Map<String, Set<Integer>> map = AhoCorasick.matches(emptyText, dictionary);
		assertEquals("The map's output is incorrect - Test3","{}",map.toString());
	}
	
	@Test
	public void Test4(){
		String textNewLine = "\n"+"abcdebeabcabebcde"+"\n";
		Map<String, Set<Integer>> map = AhoCorasick.matches(textNewLine, dictionary);
		assertEquals("The map's output is incorrect - Test4","{abc=[1, 8], abe=[11], a=[1, 8, 11], bcd=[2, 14], abcd=[1], be=[6, 12], cde=[3, 15]}",map.toString());
	}
	
	@Test
	public void Test5(){
		String textNewLine = "\n"+"abcdebeabcabebcde"+"\n";
		Set<String> dictNewLine = new HashSet<String>();
		dictNewLine.add("\n"+"a");
		dictNewLine.add("a");
		dictNewLine.add("abc");
		dictNewLine.add("be");
		dictNewLine.add("bcd");
		dictNewLine.add("abcd");
		dictNewLine.add("cde");
		dictNewLine.add("abe");
		Map<String, Set<Integer>> map = AhoCorasick.matches(textNewLine, dictNewLine);
		assertEquals("The map's output is incorrect - Test5","{abc=[1, 8], abe=[11], a=[1, 8, 11], bcd=[2, 14], abcd=[1], \na=[0], be=[6, 12], cde=[3, 15]}",map.toString());
	}
	
	@Test
	public void Test6(){
		String textExtra = "abcdebeabcabebcdehello!";
		Map<String, Set<Integer>> map = AhoCorasick.matches(textExtra, dictionary);
		assertEquals("The map's output is incorrect - Test6","{abc=[0, 7], abe=[10], a=[0, 7, 10], bcd=[1, 13], abcd=[0], be=[5, 11], cde=[2, 14]}",map.toString());
	}
}
