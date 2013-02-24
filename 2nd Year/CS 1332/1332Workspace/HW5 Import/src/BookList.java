import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFileChooser;

/**
 * 
 */

/**
 * A class to represent a list of books,
 * Students will use these to select random books
 * to place in their hash table.
 * 
 * @author Robert Waters
 * 
 */
public class BookList {
	/** the list of books available */
    
	List<Book> books = new ArrayList<Book>();
	
	/** our random number generator, seed with 
	 * known value so repeatable.
	 */
	Random rand = new Random(5432);
	
	/**
	 * Return a book at a particular index
	 * @param index the index of the book to get
	 * @return the book at that index
	 */
	public Book getBookAt(int index) {
		return books.get(index);
	}
	
	/**
	 * Read and parse Library of Congress book files
	 * NOT A ROBUST and general parser of these files
	 * Just a quick and dirty one for student homeworks.
	 * All the compiler students forgive me :)
	 * 
	 * Limitations: Only uses first line of data. Some
	 *              long titles and authors are truncated
	 *              Many data fields are ignored
	 *              Last line in file must be ===== end of book with no CR
	 * 
	 * @param filename the name of the file with book data
	 */
	public void populateBooksFromFile(String filename) {
		int lineno = 0;
		String line = null;
		String title = null;
		String author = null;
		String callno = null;
		String isbn = null;
		String control = null;
		try {
			//set up the file reader
			BufferedReader br = new BufferedReader(new FileReader(filename));
			//grab the first line
			line = br.readLine();
			lineno = 0;
			//while we are not at end of file loop
			while (line != null) {
				lineno++;
			     //skip empty lines
				while(line.equals("")) {
					line = br.readLine();
					lineno++;
				}
				//line is not empty, check for end of book
				if (line.charAt(0) == '=') {
					//end of book def so make a new book, reset
					if (title == null) title = getRandomString();
					if (author == null) author =  getRandomString();
					if (callno == null) callno = getRandomString();
					if (isbn == null) isbn = getRandomString();
					if (control == null) control = getRandomString();
					//default values and loop back to top
					Book book = new Book(title, author, isbn, callno, control);
					books.add(book);
					title = null;
					author = null;
					callno = null;
					isbn = null;
					control = null;
					line = br.readLine();
				} else {
					int index = line.indexOf(':') + 1;
					String sub = line.substring(0, 2);
					
					if (sub.equals("LC")) {
						control = line.substring(index).trim();
					} else if  (sub.equals("Ma")) {
						title = line.substring(index).trim();
					} else if (sub.equals("IS")) {
						isbn = line.substring(index).trim();
					} else if (sub.equals("Pe")) {
						author = line.substring(index).trim();
					} else if (sub.equals("CA"))  {
						callno = line.substring(index).trim();
					}
					line = br.readLine();
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Could not find file: " + filename + " in BookList::populateBooksFromFile");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error reading data in line: " + lineno);
			System.err.println("Line value was: " + line);		
			e.printStackTrace();
		}
	}

	/**
	 * Utility method since some books do not
	 * have entries for data we will be using.
	 * 
	 * @return a random string
	 */
	private String getRandomString() {
		
		int index = 0;
		StringBuilder result = new StringBuilder("R");
		for (int i = 0 ; i < 32 ; i++) {
		    index = rand.nextInt(100) + 32;
		    result.append(Character.toChars(index));
		}
		return result.toString();
	}
	
	/**
	 * Just a test for the parser
	 * 
	 * @param args command line not used
	 */
	public static void main(String[] args) {
		 BookList list = new BookList();
		 JFileChooser chooser = new JFileChooser();
		 int res = chooser.showOpenDialog(null);
		 if (res == JFileChooser.APPROVE_OPTION) {
			 System.out.println("Opening File: " + chooser.getSelectedFile().getName());
			 try {
				list.populateBooksFromFile(chooser.getSelectedFile().getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 for (int i = 0; i < list.size() ; i++) {
			 System.out.println(list.getBookAt(i));
		 }

	}
	
	/**
	 * 
	 * @return the number of books in list
	 */
	public int size() {
		return books.size();
	}

}
