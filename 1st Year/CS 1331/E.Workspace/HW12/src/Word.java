/**
 * This is HW12, Word
 * @author Joon Ki Hong
 * @version 4/22/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The Word class represents a single word in a given text file. Word objects may withhold information about
 * how many occurrences there are of a particular word.
 */
public class Word {
	private String word;
	private int wordCount;
	public Word(String word){
		this.word = word;
		wordCount = 1;
	}
	
	/**
	 * The getWord method returns the word(String) of a particular Word object
	 * @return word
	 */
	public String getWord(){
		return word;
	}
	/**
	 * The addWordCount method simply increases wordCount by 1 each time it is called. It indicates how many
	 * times the word associated to a particular instance of a Word object exists.
	 */
	public void addWordCount(){
		wordCount = wordCount + 1;
	}
	/**
	 * Overloaded addWordCount method that takes in a specified increment.
	 * @param increment
	 */
	public void addWordCount(int increment){
		wordCount = wordCount + increment;
	}
	/**
	 * The getWordCount method returns the wordCount variable for an instance of a Word object.
	 * @return wordCount
	 */
	public int getWordCount(){
		return wordCount;
	}
	
	public boolean equals(Word w2)
	{
		if(this.word.equals(w2.getWord()))
			return true;
		return false;
	}
	public String toString(){
		return word+" "+wordCount;
	}
}
