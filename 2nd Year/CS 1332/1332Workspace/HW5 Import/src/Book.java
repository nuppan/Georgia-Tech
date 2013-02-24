
/**
 * @author Robert
 *
 */
public class Book {
	/**
	 * Codes for the different hash algorithms we will use
	 */
	public enum Hashtype {
		DEFAULT, /** the standard Java hashCode() function */ 
		ADLER32, /** the adler 32 checksum algorithm */ 
		SDBM,    /** the sdbm algorithm -- used by Berkley DB */ 
		INTEGER, /** returns a simple integer */
		SIMPLEADD, /** trivial hash adding each character */
		USER; /** the algorithm created by you */
	}
	
	/**
	 * Book data for the experiment
	 */
    String title;
    String isbn;
    String author;
    String callno;
    String control;
    
    /**
     * The hashtype for all books
     */
    public static Hashtype hashtype;
    
    /**
     * Constructor
     * @param t the title
     * @param a the author
     * @param i the ISBN
     * @param call the call number
     * @param cont the control number
     */
    public Book(String t, String a, String i, String call, String cont) {
    	title = t;
    	isbn = i;
    	author = a;
    	callno = call;
    	control = cont;
    }
    
    /**
     * Books are equal if their ISBN is equal
     * normally, but since we use different keys
     * we have to do this kludgy thing.
     * 
     * Remember that if two things are equal, their hashcodes
     * must be equal, so since we change hashing for the
     * experiments, we have to do something weird here to ensure
     * that equality constraints are maintained.
     */
    public boolean equals(Object o) {
    	Book b = (Book)o;
    	switch(hashtype) {
    	case DEFAULT:
    		return isbn.equals(b.isbn);
    	case ADLER32:
    		return author.equals(b.author);
    	case SDBM:
    		return callno.equals(b.callno);
    	case USER:
    		return isbn.equals(b.isbn);
    	case INTEGER:
    		return control.equals(b.control);
    	case SIMPLEADD:
    		return title.equals(b.title);
    	}
		return isbn.equals(b.isbn);  	
    }
    
    /**
     * return the hashcode for this book based upon the
     * type we want to use.  DO NOT change the type of 
     * code once you start inserting or you won't be 
     * able to find anything.
     */
    public int hashCode() {
    	//use the correct hashcode for this book
    	switch(hashtype) {
    	case DEFAULT:
    		return isbn.hashCode();
    	case ADLER32:
    		return hashAuthor();
    	case SDBM:
    		return hashCall();
    	case USER:
    		return hashISBN();
    	case INTEGER:
    		return hashControl();
    	case SIMPLEADD:
    		return hashTitle();
    	}
    	//if unknown, just return our standard hash code
    	return isbn.hashCode();
    }
    
    /**
     * COnvert the control number to an integer
     * @return
     */
    public int hashControl() {
    	return 1;
    }
    
    /**
     * Fill this in with your own hash function.  Try something
     * creative
     * 
     * @return
     */
    public int hashISBN() {
    	return 1;
    }
    
    /**
     * Fill this in to return the sum of the chars in the title
     * @return
     */
    public int hashTitle() {
    	return 1;
    }
    
    /**
     * Fill this in with the ADLER32 algorithm
     * @return
     */
    public int hashAuthor() {
    	return 1;
    }
    
    /**
     * Fill this in with the SDBM algorithm
     * @return
     */
    public int hashCall() {
      return 1;
    }
    
    public String toString() {
    	return "Book Title: " + title + "  Author: " + author + "  ISBN: " + isbn + 
    	       "  Call Number: " + callno + "  Control Number: " + control;
    }
}
