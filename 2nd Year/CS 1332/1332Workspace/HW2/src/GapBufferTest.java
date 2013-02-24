import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



/**
 * Test for a Gap Buffer
 * @author Robert
 * @version 1.0
 *
 */
public class GapBufferTest  {
    /** our test buffer */
    private GapBuffer<String> buff;
    
    @Before
	public void setUp() {
        //This will cause an error until you implement the class
		buff = new SequenceGapBuffer<String>();
	}

    @Test
	public void testInsert() {
		buff.insert("a");
		buff.insert("b");
		buff.insert("c");
		assertEquals("Buffer incorrect after 3 inserts", "abc^", buff.toString());
	
	}

    @Test
	public void testCursorLeft() {
		buff.insert("a");
		buff.insert("b");
		buff.insert("c");
		buff.cursorLeft();
		assertEquals("Cursor did not move left correctly", "ab^c", buff.toString());
		for (int i = 0; i<10 ; i++)
		    buff.cursorLeft();
		assertEquals("Cursor did not move left correctly", "^abc", buff.toString());
	}

    @Test
	public void testDelete() {
		buff.delete();
		assertEquals("Delete from empty buffer wrong", "^", buff.toString());
		buff.insert("a");
		buff.insert("b");
		buff.insert("c");
		buff.delete();
		assertEquals("Delete from 3 element buffer wrong", "ab^", buff.toString());
	}

	@Test
	public void testCursorRight() {
		buff.insert("a");
		buff.insert("b");
		buff.insert("c");
		buff.cursorLeft();
		buff.cursorLeft();
		buff.cursorRight();
		
		assertEquals("Moving cursor right wrong", "ab^c", buff.toString());
		for (int i = 0; i<10 ; i++)
		    buff.cursorRight();
		assertEquals("Multiple right cursor incorrect", "abc^", buff.toString());
		
	}

	@Test
	public void testToString() {
		buff.insert("a");
		buff.insert("b");
		buff.insert("c");
		assertEquals("toString incorrect after 3 inserts", "abc^", buff.toString());
		buff.cursorLeft();
		assertEquals("toString incorrect for cursor left", "ab^c", buff.toString());
	}

}
