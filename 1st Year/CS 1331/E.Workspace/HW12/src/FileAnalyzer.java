import java.io.*;
/**
 * This is HW12, FileAnalyzer
 * @author Joon Ki Hong
 * @version 4/22/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The FileAnalyzer Class contains the main method that takes in command line arguments and passes those
 * arguments into an instance of an Analyzer object and proceeds to call methods to successfully write
 * the results (How many instances of a word/Longest sentence in the given file/First N number of unique 
 * words) into a new text file.
 */
public class FileAnalyzer {
	public static void main(String[] args){
		
		File inputFile = null;
		File outputFile = null;
		int wordsToAnalyze = 0;
		//Checks for varying number of arguments.
		if (args.length == 0){
			System.out.println("Incorrect number of arguments.");
		}
		else if (args.length>3){
			System.out.println("Incorrect number of arguments.");
		}
		else if (args.length == 1){
			inputFile = new File("hw10.txt");
			outputFile = new File("results.txt");			
			wordsToAnalyze = Integer.parseInt(args[0]);
			Analyzer analyzer = new Analyzer(wordsToAnalyze,inputFile,outputFile);
			try{
				analyzer.splitSentences();
				analyzer.analyzeSentences();
				analyzer.writeResults();
			}catch (IOException e){
				System.out.println("File was not found.");
			}
		}
		else if (args.length < 3){
			inputFile = new File(args[1]);
			outputFile = new File("results.txt");
			wordsToAnalyze = Integer.parseInt(args[0]);
			Analyzer analyzer = new Analyzer(wordsToAnalyze,inputFile,outputFile);
			try{
				analyzer.splitSentences();
				analyzer.analyzeSentences();
				analyzer.writeResults();
			}catch (IOException e){
				System.out.println("File was not found.");
			}
		}
		else {
			inputFile = new File(args[1]);
			outputFile = new File(args[2]);
			wordsToAnalyze = Integer.parseInt(args[0]);
			Analyzer analyzer = new Analyzer(wordsToAnalyze,inputFile,outputFile);
			try{
				analyzer.splitSentences();
				analyzer.analyzeSentences();
				analyzer.writeResults();
			}catch (IOException e){
				System.out.println("File was not found.");
			}
		}
	}
	
}
