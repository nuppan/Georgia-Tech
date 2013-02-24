import java.util.*;
import java.util.regex.Pattern;
import java.io.*;
/**
 * This is HW12, Analyzer
 * @author Joon Ki Hong
 * @version 4/22/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The Analyzer Class handles the reading of a given text file and creates Sentence objects by using "?",
 * "!", and "." delimiters. It also writes the calculated results onto a new text file.
 */
public class Analyzer {
	private ArrayList<Sentence> sentenceList;
	private ArrayList<Word> wordList;
	private String longestSentence;
	private int wordsToAnalyze;
	private int wordsAnalyzed;
	private File inputFile;
	private File outputFile;
	private boolean repeatWord;
	public Analyzer(int wordsToAnalyze, File inputFile, File outputFile){
		sentenceList = null;
		repeatWord = false;
		longestSentence = "";
		this.wordsToAnalyze = wordsToAnalyze;
		wordsAnalyzed = 0;
		wordList = new ArrayList<Word>();
		sentenceList = new ArrayList<Sentence>();
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}	
	
	/**
	 * The splitSentences method parses the file's body of text and divides the text into sentences
	 * @throws IOException An IOException is thrown if the inputFile indicated does not exist.
	 */
	public void splitSentences() throws IOException{
		Scanner scan = new Scanner(inputFile);
		scan.useDelimiter(Pattern.compile("(\\?|!|\\.[^(edu|com|org|net)])+"));
		while (scan.hasNext() == true){
			sentenceList.add(new Sentence(scan.next()));
		}
	}
	
	/**
	 * The analyzeSentences processes the sentences and processes the Word objects associated with 
	 * each sentence object and forms an ArrayList of Word Objects from all Sentence Instances.
	 */
	public void analyzeSentences(){
		int highestCount=0;
		for (Sentence sentence:sentenceList){
			sentence.processWords(wordsToAnalyze);
			for (Word words:sentence.getWordList()){
				if (wordList.size() == 0){
					wordList.add(words);
					wordsAnalyzed = wordsAnalyzed + 1;
				}
				else{
					repeatWord = false;
					for (Word mainWords:wordList){
						if (words.equals(mainWords)){
							mainWords.addWordCount(words.getWordCount());
							repeatWord = true;
						}
					}
					if ((!repeatWord) && (wordsAnalyzed<wordsToAnalyze)){
						wordList.add(words);
						wordsAnalyzed = wordsAnalyzed + 1;
					}
				}
			}
		}
		for (Sentence sentence:sentenceList){ //Computes which Sentence instance has the longest length.
			if (highestCount == 0){
				highestCount = sentence.getLength();
				longestSentence = sentence.getSentence();
			}
			else{
				if (sentence.getLength()>highestCount){
					highestCount = sentence.getLength();
					longestSentence = sentence.getSentence();
				}
			}
		}
	}
	
	/**
	 * The writeResults method instantiates a new BufferedWriter and FileWriter to write the results in
	 * the specified outputFile.
	 * @throws IOException an IOException is thrown if the FileWriter cannot find the outputFile.
	 */
	public void writeResults() throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		writer.write("Word frequencies:");
		writer.newLine();
		for (Word words:wordList){
			writer.write(words.getWord()+": "+ words.getWordCount());
			writer.newLine();
		}
		longestSentence = longestSentence.replace('\n',' ');
		writer.write("Longest sentence: \""+longestSentence+".\"");
		writer.close();
	}
	
}
