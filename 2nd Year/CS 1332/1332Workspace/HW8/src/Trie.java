import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
/**
 * The Trie class represents a Trie. It takes in a dictionary and builds itself a trie which is
 * then used to run the Aho Corasick algorithm
 * 
 * @author Joon Ki Hong
 * CS1332-A4
 */
public class Trie {

	/**
	 * The root of the trie, the state before any characters are processed
	 */
	private Node root;

	/**
	 * The current state of this trie
	 */
	private Node state;

	/**
	 * Constructor, builds a trie using the given dictionary (using buildTrie and buildFail), then
	 * sets the current state of the trie to be the root
	 * 
	 * @param dictionary
	 *            a dictionary of words
	 */
	public Trie(Set<String> dictionary) {
		root = new Node();
		state = root;
		buildTrie(dictionary);
		buildFail();
	}

	/**
	 * Resets the state of this trie to be the root
	 */
	public void reset() {
		state = root;
	}

	/**
	 * Advances the state along the path c, moving to the failure node WHILE necessary, and returns
	 * the set of Strings that should be output (potentially an empty set)
	 * 
	 * @param c
	 *            the character to move along
	 * @return what should be output at the new state
	 */
	public Set<String> next(char c) {
		boolean found = false;
		while (found==false){
			if (state.hasChild(c) || state==root){
				if (state.hasChild(c)){
					state = state.getChild(c);
				}
				found = true;
			}
			else {
				state = state.getFail();
			}
		}
		return state.getOut();
	}

	/**
	 * Sets all the children of the trie based on the given dictionary
	 * 
	 * @param dictionary
	 *            a dictionary of words
	 */
	public final void buildTrie(Set<String> dictionary) {
		for (String word:dictionary){
			for (char c:word.toCharArray()){
				if (state.hasChild(c)==true){
					state = state.getChild(c);
				}
				else{
					state.addChild(c);
					state = state.getChild(c);
				}
			}
			state.addOut(word);
			reset();
		}
	}

	/**
	 * Sets all the failures in the trie, should assume that buildTrie has already been called
	 */
	public final void buildFail() {
		LinkedList<Node> queue = new LinkedList<Node>();
		root.setFail(root);
		Collection<Node> rootChildren = root.getAllChildren();
		for (Node child:rootChildren){
			child.setFail(root);
			queue.add(child);
		}
		while (!queue.isEmpty()){
			Node nChild = queue.remove();
			Collection<Node> children = nChild.getAllChildren();
			for (Node cChild:children){
				Collection<Character> keys = nChild.getAllKeys();
				Iterator<Character> keyIterator = keys.iterator();
				boolean found = false;
				Character character = keyIterator.next();
				while (found==false){
					if (nChild.getChild(character)==cChild){
						found = true;
					}
					else{
						character = keyIterator.next();
					}
				}
				queue.add(cChild);
				cChild.setFail(nChild.getFail());
				while (cChild.getFail().hasChild(character)==false && cChild.getFail()!=root){
					cChild.setFail(cChild.getFail().getFail());
					System.out.println("hi");
				}
				if (cChild.getFail().hasChild(character)){
					cChild.setFail(cChild.getFail().getChild(character));
				}
				for (String output:cChild.getFail().getOut()){
					cChild.addOut(output);
				}
			}
		}
	}
	
	/**
	 * Simple getters and setters used for grading
	 */
	public Node getRoot() {
		return root;
	}
	
	/**
	 * Simple getters and setters used for grading
	 */
	public void setRoot(Node root) {
		this.root = root;
	}
	
	/**
	 * Simple getters and setters used for grading
	 */
	public Node getState() {
		return state;
	}
	
	/**
	 * Simple getters and setters used for grading
	 */
	public void setState(Node state) {
		this.state = state;
	}
}
