import java.util.*;
/**
 * This is HW12, Sentence
 * @author Joon Ki Hong
 * @version 4/22/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The Sentence class represents a sentence (Strings delimited by ".","!", and "?". This class contains methods
 * that further breaks down a passed in "sentence" into Word objects.
 */
public class Sentence {
	private String sentence;
	private int length;
	private ArrayList<Word> wordList;
	private boolean wordRepeat;
	
	/**
	 * Analyzes a sentence
	 * @param sentence The sentence to be analyzed
	 */
	public Sentence(String sentence){
		this.sentence = sentence.trim().toLowerCase();
		wordList = new ArrayList<Word>();
		length = 0;
		wordRepeat = false;
	}
	/**
	 * The processWords method takes a String or a "sentence" of an instance of a sentence object and processes
	 * the words from a sentence into individual Word objects and keeps track of how many words are in a
	 * particular sentence.
	 * @param wordsToAnalyze an int of how many unique words designated by the user to be examined.
	 * @param wordsAnalyzed an int of how many unique words were examined. It also returns an updated number of
	 * words analyzed after this method is called each time.
	 * @return processWords
	 */
	public void processWords(int wordsToAnalyze){
		Scanner scan = new Scanner(sentence);
		String word = null;
		scan.useDelimiter("\\s");
		while (scan.hasNext() == true){
			word = scan.next();
			if(word.length()!=0)
			{
				if (wordList.size() == 0){ //Adds the first word
					wordList.add(new Word(word));
				}
				else{ 
					wordRepeat = false; //reset the repeat variable.
					for (int x = 0; x<wordList.size(); x++){ //Checks every Word object and compares with the current word
						if (word.equals(wordList.get(x).getWord())){
							wordList.get(x).addWordCount();
							wordRepeat = true; //If wordRepeat is true then that means a repeated word was processed.
						}
					}
					if ((wordRepeat == false)){
						wordList.add(new Word(word));
					}
				}
				
				length = length + 1;	//update the number of total words in a sentence.
			}
		}
	}
	
	/**
	 * The getLength method returns the length (number of words) of a sentence.
	 * @return lengthCount the length of a sentence
	 */
	public int getLength(){
		return length;
	}
	/**
	 * The getWordList method returns the ArrayList of processed Word objects
	 * @return wordList The arrayList containing all the word in the sentence
	 */
	public ArrayList<Word> getWordList(){
		return wordList;
	}
	/**
	 * The getSentence method returns the sentence with the first letter capitalized.
	 * @return line with capitalized first letter.
	 */
	public String getSentence(){
		sentence = sentence.trim();
		char firstLetter = sentence.charAt(0);
		if(firstLetter>=97&&firstLetter<=122)
		{
			firstLetter = (char)((byte)firstLetter-(byte)32);
		}
		String remainingLine = sentence.substring(1);
		return firstLetter+""+remainingLine;
	}
	
}
