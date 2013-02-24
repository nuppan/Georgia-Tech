import java.io.IOException;
import java.util.Random;

import javax.swing.JFileChooser;

/**
 * 
 */

/**
 * @author Robert
 * Simulate book requests and turn-ins
 * for our Hashtable experiments
 */
public class Simulator {
	BookList books = new BookList();
	//Use a set seed so randoms the same each run
	Random rand = new Random(54342);
	//number of collisions
	int collisions;
	//number of times table regrown
	int regrows;
	//number of probes when looking something up
	int probes;
	//the number of duplicate insertions attempted
	int duplicates;
	
	/**
	 * Constructor, make the books
	 *
	 */
	public Simulator () {
        initializeBooks();
	}
	
	/**
	 * Called from constructor to initialize our booklist
	 *
	 */
	private void initializeBooks() {
		 JFileChooser chooser = new JFileChooser();
		 int res = chooser.showOpenDialog(null);
		 if (res == JFileChooser.APPROVE_OPTION) {
			 System.out.println("Opening File: " + chooser.getSelectedFile().getName());
			 try {
				books.populateBooksFromFile(chooser.getSelectedFile().getCanonicalPath());
			} catch (IOException e) {
				System.err.println("An exception occurred while initializing our book list");
				e.printStackTrace();
			}
		 }
	}
	
	/**
	 * Grab a random book (might be duplicate) to insert into
	 * the hashtable
	 * @return the random book from the table
	 */
	private Book getRandomBook() {
		int index = rand.nextInt(books.size() - 1);
		return books.getBookAt(index);
	}
	
	/**
	 * Execute an experiment with a given set of parameters
	 * Use the default java string hash algorithm and use the 
	 * isbn as the key
	 * 
	 * @param size the initial size of the hashtable to use
	 * @param loadfactor the loadfactor to maintain
	 * @param mod the regrow modifier (normally 0 or 1) to use.
	 * 
	 * @return the total number of collisions for this experiment
	 */
	public int doOneExperiment(int size, double loadfactor, int mod) {
		duplicates = 0;
	    Hashtable<String, Book> table = new Hashtable<String, Book>(size, loadfactor);
	    table.setRegrowModifier(mod);
	    
	    Book.hashtype=Book.Hashtype.DEFAULT;
	    //insert 1000 random books
	    for (int i = 0; i < 1000; i++) {
	    	Book b = getRandomBook();
	    	duplicates += table.insert(b.isbn, b);
	    }
	    
	    //lookup 1000 random books
	    for (int i = 0; i < 1000; i++) {
	    	Book b = getRandomBook();
	    	table.lookup(b.isbn);
	    }
	    
	    collisions = table.collisions();
	    probes = table.probeCount();
	    regrows = table.regrowCount();
	    int entry = table.size();
	    
	    System.out.println("*******************************");
		System.out.println("Experiment Params, Load Factor= " + loadfactor + " initial table size " + size +
				" table grow modifier " + mod);
		System.out.println("Collsions: " + collisions);
		System.out.println("Regrows: " + regrows);
		System.out.println("Search Probes: " + probes);
		System.out.println("Entry Count: " + entry);
		System.out.println("Duplicate Inserts attempted: " + duplicates);
		System.out.println("*******************************");
	    return collisions;
	} 
	
	public int doOneExperimentChain(int size, double loadfactor, int mod){
		duplicates = 0;
	    ChainingHashtable<Book, Book> table = new ChainingHashtable<Book, Book>(size, loadfactor);
	    table.setRegrowModifier(mod);
	    
	    Book.hashtype=Book.Hashtype.DEFAULT;
	    //insert 1000 random books
	    for (int i = 0; i < 1000; i++) {
	    	Book b = getRandomBook();
	    	duplicates += table.insert(b, b);
	    }
	    
	    //lookup 1000 random books
	    for (int i = 0; i < 1000; i++) {
	    	Book b = getRandomBook();
	    	table.lookup(b);
	    }

	    
	    collisions = table.collisions();
	    probes = table.probeCount();
	    regrows = table.regrowCount();
	    int entry = table.size();
	    
	    System.out.println("*******************************");
		System.out.println("Experiment Params, Load Factor= " + loadfactor + " initial table size " + size +
				" table grow modifier " + mod);
		System.out.println("Collsions: " + collisions);
		System.out.println("Regrows: " + regrows);
		System.out.println("Search Probes: " + probes);
		System.out.println("Entry Count: " + entry);
		System.out.println("Duplicate Inserts attempted: " + duplicates);
		System.out.println("*******************************");
	    return collisions;
	}
	public int doOneExperimentCoalesced(int size, double loadfactor, int mod){
		duplicates = 0;
	    CoalescedHashtable<Book, Book> table = new CoalescedHashtable<Book, Book>(size, loadfactor);
	    table.setRegrowModifier(mod);
	    
	    Book.hashtype=Book.Hashtype.DEFAULT;
	    //insert 1000 random books
	    for (int i = 0; i < 1000; i++) {
	    	Book b = getRandomBook();
	    	duplicates += table.insert(b, b);
	    }
	    
	    //lookup 1000 random books
	    for (int i = 0; i < 1000; i++) {
	    	Book b = getRandomBook();
	    	table.lookup(b);
	    }
	    
	    collisions = table.collisions();
	    probes = table.probeCount();
	    regrows = table.regrowCount();
	    int entry = table.size();
	    
	    System.out.println("*******************************");
		System.out.println("Experiment Params, Load Factor= " + loadfactor + " initial table size " + size +
				" table grow modifier " + mod);
		System.out.println("Collsions: " + collisions);
		System.out.println("Regrows: " + regrows);
		System.out.println("Search Probes: " + probes);
		System.out.println("Entry Count: " + entry);
		System.out.println("Duplicate Inserts attempted: " + duplicates);
		System.out.println("*******************************");
	    return collisions;
	}

	/**
	 * Main routine to run experiments and print the results
	 * @param args
	 */
	public static void main(String[] args) {
		Simulator sim = new Simulator();
		
		sim.doOneExperimentCoalesced(100, .75, 0);
		sim.doOneExperimentCoalesced(100, .90, 0);
		sim.doOneExperimentCoalesced(100, .5, 0);
		
		sim.doOneExperimentCoalesced(89, .75, 1);
		sim.doOneExperimentCoalesced(89, .9, 1);
		sim.doOneExperimentCoalesced(89, .5, 1);
	

	}

}
