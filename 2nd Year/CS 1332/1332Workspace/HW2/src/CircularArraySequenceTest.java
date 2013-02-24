import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for the sequence implementation
 * @author Robert
 * @version 1.0
 *
 */
public class CircularArraySequenceTest  {
    /** the main test sequence */
	private Sequence<String> seq;
    
	@Before
	public void setUp() {
	    //This line will cause an error until you create the class
		seq = new CircularArraySequence<String>(10);
	}

	@Test
	public void testAppend() {
		assertTrue("Your sequence was not empty when created", seq.isEmpty());
		seq.append("a");
		assertFalse("Your sequence claims its empty after appending something", seq.isEmpty());
		assertEquals("The head of your sequence was not the thing appended on first append", "a", seq.peekHead());
		seq.append("b");
		assertEquals("The last of your sequence was not the thing appended on second append", "b", seq.peekLast());
		assertEquals("The head of your sequence modified on the second append", "a", seq.peekHead());
		seq.append("c");
		seq.append("d");
		assertEquals("The head of your sequence modified after multiple appends", "a", seq.peekHead());
		assertEquals("The last of your sequence was not the last thing appended", "d", seq.peekLast());
	}

	@Test
	public void testConcat() {
		seq.append("a");
		seq.append("b");
		seq.append("c");
		Sequence<String> seq2 = new CircularArraySequence<String>(10);
		seq2.append("x");
		seq2.append("y");
		Sequence<String> seq3 = seq.concat(seq2);
		assertEquals(5, seq3.length());
		assertEquals("a", seq3.peekHead());
		assertEquals("y", seq3.peekLast());
	}

	@Test
	public void testContainsSequenceOfT() {
	
		seq.append("a");
		seq.append("b");
		seq.append("c");
		seq.append("e");
		CircularArraySequence<String> cas = new CircularArraySequence<String>(10);
		cas.append("b");
		cas.append("c");
		assertTrue(seq.contains(cas));
	
	}

	@Test
	public void testContainsT() {
		 
		seq.append("A");
		seq.append("B");
		seq.append("C");
		seq.append("D");
		assertTrue(seq.contains("A"));
		assertFalse(seq.contains("X"));
		assertTrue(seq.contains("D"));
	}
 
	@Test
	public void testFront() {
	   Sequence<String> seq2 = seq.front();
	   assertTrue("Front of empty seq did not return empty", seq2.isEmpty());
       seq.append("a");
       seq.append("b");
       seq.append("c");
       seq.append("d");
       seq.append("e");
       Sequence<String> seq1 = seq.front();
       assertEquals("Length of the sequence after taking front was wrong", 4 , seq1.length());
       assertEquals("Head of the front sequence wrong", "a", seq1.peekHead());
       assertEquals("Last element of front sequence wrong", "d", seq1.peekLast());
       assertEquals("Original sequence length wrong", 5, seq.length());
       assertEquals("Original sequence head wrong = don't modify the original when taking front", "a", seq.peekHead());
       assertEquals("Original sequence last element wrong, don't modify the original", "e", seq.peekLast());
	}

	@Test
	public void testHead() {
		assertTrue("New sequence not reporting its empty", seq.isEmpty());
		assertEquals("Head of the new  sequence not null", null, seq.head());
		seq.append("a");
		assertEquals("Head returned incorrect when one element", "a", seq.head());
		assertEquals("Head returned incorrect when no element", null, seq.head());
		seq.append("s");
		seq.append("r");
		seq.append("t");
		seq.append("i");
		assertEquals("Head returned incorrect value after several appends", "s", seq.head());
		seq.last();
		assertEquals("After removing last element, the head changed", "r", seq.head());
	}

	@Test
	public void testIsEmpty() {
		assertTrue(seq.isEmpty());
		seq.append("A");
		assertFalse(seq.isEmpty());
		seq.last();
		assertTrue(seq.isEmpty());
	}

	@Test
	public void testLast() {
		assertTrue("New sequence not reporting empty", seq.isEmpty());
		assertEquals("Taking last of an new empty sequence did not return null", null, seq.last());
		seq.append("a");
		seq.append("b");
		seq.append("c");
		assertEquals("Last element returned not correct", "c", seq.last());
		assertEquals("Number of elements wrong after last called", 2, seq.length());
		assertEquals("last element returned not correct", "b", seq.last());
		assertEquals("Number of elements wrong after last called", 1, seq.length());
		assertEquals("Last element returned not correct", "a", seq.last());
		assertEquals("Taking last of an empty sequence didn't return null", null, seq.last());
	}

	@Test
	public void testPeekHead() {
	    assertEquals("Peek should return null for empty sequence", null, seq.peekHead());
		seq.append("a");
		assertEquals("Peeking at head did not return correct value", "a", seq.peekHead());
		seq.append("b");
		assertEquals("Peeking at head after appending 2 things did not return correct value", "a", seq.peekHead());
		seq.prepend("x");
		assertEquals("x", seq.peekHead());

	}

	@Test
	public void testPeekLast() {
	    assertEquals("Peek last should return null if empty", null, seq.peekLast());
		seq.append("a");
		assertEquals("Peek last returns wrong value for 1 element", "a", seq.peekLast());
		seq.append("b");
		assertEquals("Peek last returns wrong value for 2 elements", "b", seq.peekLast());
		seq.append("x");
		assertEquals("Peek last returns wrong value for multiple elements","x", seq.peekLast());
		seq.append("z");
		seq.prepend("D");
		seq.prepend("C");
		seq.last();
		seq.last();
		seq.last();
		seq.last();
		seq.last();
		assertEquals("Peek doesn't wrap past zero", "C",  seq.peekLast());

	}

	@Test
	public void testPrepend() {
		seq.append("x");
		seq.prepend("a");
		assertEquals("Prepend did not put new item in front", "a", seq.peekHead());
		assertEquals("Length of sequence wrong after prepend", 2, seq.length());
		seq.prepend("b");
		assertEquals("Prepend did not put new item in front on second call", "b", seq.peekHead());
		seq.prepend("c");
		assertEquals("Prepend did not put new item in front on third call", "c", seq.peekHead());
		assertEquals("Length of prepend sequence incorrect", 4, seq.length());
		assertEquals("ToString value of sequence incorrect after prepends", "cbax" , seq.toString());
	}

	@Test
	public void testTail() {
	       Sequence<String> seq2 = seq.tail();
	       assertTrue("empty sequence tail should empty seq", seq2.isEmpty());
	       seq.append("a");
	       seq.append("b");
	       seq.append("c");
	       seq.append("d");
	       seq.append("e");
	       Sequence<String> seq1 = seq.tail();
	       assertEquals("Length of tail sequence incorrect", 4 , seq1.length());
	       assertEquals("Head of tail sequence incorrect", "b", seq1.peekHead());
	       assertEquals("Last element of tail sequence incorrect", "e", seq1.peekLast());
	       assertEquals("Length of original sequence changed when taking tail", 5, seq.length());
	       assertEquals("Original sequence head changed when taking tail", "a", seq.peekHead());
	       assertEquals("Original sequence last changed when taking tail", "e", seq.peekLast());
	}

	@Test
	public void testIterator() {
	    Iterator<String> ite = seq.iterator();
	    assertFalse("Empty sequence iterator not empty", ite.hasNext());
		seq.append("a");
		seq.append("b");
		seq.append("c");
		Iterator<String> it = seq.iterator();
		assertTrue("Iterator over data thinks it is empty", it.hasNext());
		assertEquals("Iterator did not return correct element at first", "a", it.next());
		assertEquals("Iterator did not return correct second element", "b", it.next());
		assertEquals("Iterator did not return correct third element", "c", it.next());
		assertFalse("Iterator should not have anything left, but think it does", it.hasNext());
		assertEquals("Original sequence length modified after iterator", 3 , seq.length());
	
	}

	@Test
	public void testEqualsSequenceOfT() {
		/* Uncomment this if you implement
		Sequence<String> seq2 = new CircularArraySequence<String>(10);
		seq.append("a");
		seq.append("b");
		seq.append("c");
		seq2.append("a");
		seq2.append("b");
		seq2.append("c");
		assertTrue(seq.equals(seq2));
	    seq2.append("x");
	    assertFalse(seq.equals(seq2));
	    seq.append("y");
	    assertFalse(seq.equals(seq2));
	    */
	}

	@Test
	public void testLength() {
		assertEquals("Length of the new sequence not zero", 0, seq.length());
		seq.append("a");
		assertEquals("Length after 1 append not 1", 1, seq.length());
		seq.prepend("b");
		assertEquals("Length after 2 appends not 2", 2, seq.length());
		seq.last();
		seq.last();
		assertEquals("Length after removing 2 things from a 2 element seq not zero", 0, seq.length());
	}
	
	@Test
	public void testToString() {
	    assertEquals("To string of empty sequence wrong", "",seq.toString());
		seq.append("a");
		seq.append("b");
		seq.append("c");
		
        assertEquals("ToString of 3 element seq wrong", "abc", seq.toString());
        seq.prepend("d");
        seq.prepend("e");
        seq.prepend("f");
        
        assertEquals("toString of 6 element seq wrong", "fedabc", seq.toString());
	}
	
	@Test
	public void testRegrow() {
		for(int i=0; i < 15 ; i++)
			seq.append("a");
		seq.prepend("e");
		seq.append("x");
		assertEquals("Error on regrow", 17, seq.length());
		assertEquals("Head wrong after regrow", "e", seq.peekHead());
		assertEquals("Last wrong after regrow", "x" , seq.peekLast());
		assertEquals("String rep wrong on regrow", "eaaaaaaaaaaaaaaax", seq.toString());
	}

}
