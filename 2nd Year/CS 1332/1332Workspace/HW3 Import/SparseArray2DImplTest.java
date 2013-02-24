import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for Homework 3
 * 
 * @author Robert
 * @version 1.0
 * 
 */
public class SparseArray2DImplTest {
    // our actual test array
    private ISparseArray2D<String> array;

    @Before
    public void setUp() throws Exception {
        // This line will not compile till you make an implementation class
        // If this line throws a memory exception, then you trying to actually
        // make a huge  array which is wrong.  We are using linked structures!
        array = new SparseArray2DImpl<String>(10000, 10000);
    }

    @Test
    public void testGetAt() {
        // test out of bounds error
        try {
            array.getAt(10001, 1);
            // should not get here
            assertTrue(
                    "You failed to throw the exception properly for row out of bounds",
                    false);
        } catch (ArrayIndexOutOfBoundsException ae) {
            assertTrue(true);
        }
        try {
            array.getAt(1, 10001);
            // should not get here
            assertTrue(
                    "You failed to throw the exception properly for column out of bounds",
                    false);
        } catch (ArrayIndexOutOfBoundsException ae) {
            assertTrue(true);
        }
        // try to get cell from an empty array
        assertEquals("You did not return null from an empty array", null,
                array.getAt(1000, 1000));
        // add three things to array
        array.putAt(10, 100, "C");
        array.putAt(10, 150, "L");
        array.putAt(30, 150, "D");
        array.putAt(150, 10, "X");
        array.putAt(10, 20, "Z");
        array.putAt(5, 150, "Y");
        // can I find one
        assertEquals("You failed to return a correct entry", "C",
                array.getAt(10, 100));
        // test an empty cell
        assertEquals("You did not return null for an empty cell", null,
                array.getAt(50, 50));
        // check another cell
        assertEquals("You failed to return a correct entry", "D",
                array.getAt(30, 150));
        // check for another empty cell
        assertEquals("You did not return null for non-existent cell", null,
                array.getAt(100, 10));
        // check for another cell
        assertEquals("You failed to return a correct entry", "X",
                array.getAt(150, 10));
        assertEquals("Insert at head of row failed", "Z", array.getAt(10, 20));
        assertEquals("Insert at head of col failed", "Y", array.getAt(5, 150));
        //check for correct rows and columns after all these inserts
        List<String> list = array.getRowFor(10);
        assertEquals("Row count wrong after inserts", 3,list.size());
        assertEquals("Element order wrong", "Z", list.get(0));
        assertEquals("ELement order wrong", "C", list.get(1));
        assertEquals("Element order wrong", "L", list.get(2));
        
        list = array.getColumnFor(150);
        assertEquals("Col count wrong after inserts", 3, list.size());
        assertEquals("Element order wrong", "Y", list.get(0));
        assertEquals("ELement order wrong", "L", list.get(1));
        assertEquals("Element order wrong", "D", list.get(2));
    }

    @Test
    public void testGetColumnFor() {
        // test out of bounds error
        try {
            array.getColumnFor(10001);
            // should not get here
            assertTrue("You failed to detect column out bounds", false);
        } catch (ArrayIndexOutOfBoundsException ae) {
            assertTrue(true);
        }
        // try to get from empty array
        List<String> list = array.getColumnFor(100);
        assertTrue("You did not return an empty list for an empty array",
                list.isEmpty());
        // now add some items to the array
        array.putAt(10, 100, "C");
        array.putAt(10, 150, "L");
        array.putAt(30, 150, "D");
        array.putAt(90, 150, "Z");
        array.putAt(30, 100, "O");
        array.putAt(30, 200, "T");
        list = array.getColumnFor(150);
        assertEquals("Did not return correct entry count for column", 3,
                list.size());
        assertEquals("First element of column was wrong", "L", list.get(0));
        assertEquals("Second element of column was wrong", "D", list.get(1));
        assertEquals("Third element of column was wrong", "Z", list.get(2));
        list = array.getColumnFor(1000);
        assertTrue("You did not return empty for nonexistent column",
                list.isEmpty());
    }

    @Test
    public void testGetRowFor() {
        // test out of bounds error
        try {
            array.getRowFor(10001);
            // should not get here
            assertTrue("You failed to detect row out of bounds", false);
        } catch (ArrayIndexOutOfBoundsException ae) {
            assertTrue(true);
        }
        // try to get from empty array
        List<String> list = array.getRowFor(100);
        assertTrue("You failed to return an empty list for an empty array",
                list.isEmpty());
        array.putAt(10, 100, "C");
        array.putAt(10, 150, "L");
        array.putAt(30, 150, "D");
        array.putAt(90, 150, "Z");
        array.putAt(30, 100, "O");
        array.putAt(30, 200, "T");
        list = array.getRowFor(30);
        assertEquals("Returned row count incorrect", 3, list.size());
        assertEquals("First row item wrong", "O", list.get(0));
        assertEquals("Second row item wrong", "D", list.get(1));
        assertEquals("Third row item wrong", "T", list.get(2));
        list = array.getRowFor(1000);
        assertTrue("You failed to return an empty list for empty row",
                list.isEmpty());
    }

    @Test
    public void testPutAt() {
        try {
            array.putAt(10001, 1, "K");
            // should not get here
            assertTrue("Failed to detect row out of bounds", false);
        } catch (ArrayIndexOutOfBoundsException ae) {
            assertTrue(true);
        }
        try {
            array.putAt(1, 10001, "K");
            // should not get here
            assertTrue("Failed to detect col out of bounds", false);
        } catch (ArrayIndexOutOfBoundsException ae) {
            assertTrue(true);
        }
        array.putAt(10, 100, "C");
        array.putAt(30, 140, "D");
        assertEquals("Returned element is wrong", "C", array.getAt(10, 100));
        assertEquals("Returned element is wrong", "D", array.getAt(30, 140));
        array.putAt(10, 100, "Z");
        assertEquals("The overwritten value of a cell is wrong",
                array.getAt(10, 100), "Z");
    }

    @Test
    public void testRemoveAt() {
        try {
            array.removeAt(10001, 1);
            // should not get here
            assertTrue("Failed to detect row out of bounds", false);
        } catch (ArrayIndexOutOfBoundsException ae) {
            assertTrue(true);
        }
        try {
            array.removeAt(1, 10001);
            // should not get here
            assertTrue("Failed to detect col out of bounds", false);
        } catch (ArrayIndexOutOfBoundsException ae) {
            assertTrue(true);
        }
        array.putAt(10, 100, "C");
        array.putAt(30, 140, "D");
        array.putAt(10, 150, "L");
        array.putAt(30, 150, "E");
        array.putAt(7, 7, "T");
        
        assertEquals("Removed element from non-existent cell not null", null, array.removeAt(5, 5));
        assertEquals("Removed element wrong", "C", array.removeAt(10, 100));
        assertEquals("Removed element wrong", "L", array.removeAt(10, 150));
       
        List<String> list = array.getRowFor(10);
        assertTrue(list.isEmpty());
        list = array.getColumnFor(100);
        assertTrue(list.isEmpty());
       
        list = array.getColumnFor(150);
        assertTrue(list.size() == 1);
        assertTrue(list.contains("E"));
       
        assertEquals("Single element row, col remove wrong", "T", array.removeAt(7, 7));
        list = array.getRowFor(7);
        assertTrue(list.isEmpty());
        list = array.getColumnFor(7);
        assertTrue(list.isEmpty());

    }

}
