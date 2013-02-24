import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author Joon Ki Hong
 * CS1332 A4
 * The JUnit4 Test class that tests whether or not the CoalescedHashtable and the
 * ChainingHashtable works.
 */
public class HashtableTest {
	private ChainingHashtable<Integer, String> chainHashtable;
	private CoalescedHashtable<Integer,String> coalescedHashtable;
	@Before
	public void setUp(){
		chainHashtable = new ChainingHashtable<Integer, String>(10,.5);
		coalescedHashtable = new CoalescedHashtable<Integer,String>(10,.5);
	}
	
	@Test
	public void testChain(){
		chainHashtable.setRegrowModifier(0);
		chainHashtable.insert(1, "1");
		chainHashtable.insert(2, "2");
		chainHashtable.insert(3, "3");
		chainHashtable.insert(4, "4");
		chainHashtable.insert(5, "5");
		chainHashtable.insert(11, "11");
		chainHashtable.insert(13, "13");
		chainHashtable.insert(21, "21");
		assertEquals("Should be 1 at index 1","1",chainHashtable.getBuckets()[1].value);
		assertEquals("Should be 11 at index 1","11",chainHashtable.getBuckets()[1].next.value);
		assertEquals("Should be 21 at index 1","21",chainHashtable.getBuckets()[1].next.next.value);
		assertEquals("Should be 2 at index 2","2",chainHashtable.getBuckets()[2].value);
		assertEquals("Should be 3 at index 3", "3",chainHashtable.getBuckets()[3].value);
		assertEquals("Should be 13 at index 3","13",chainHashtable.getBuckets()[3].next.value);
		assertEquals("Should be 4 at index 4","4",chainHashtable.getBuckets()[4].value);
		assertEquals("Should be 5 at index 5","5",chainHashtable.getBuckets()[5].value);
		assertEquals("Should be null at index 0",null,chainHashtable.getBuckets()[0]);
		chainHashtable.insert(6, "6");
		chainHashtable.insert(31, "31");
		assertEquals("Should be 1 at index 1","1",chainHashtable.getBuckets()[1].value);
		assertEquals("Should be 11 at index 11","11",chainHashtable.getBuckets()[11].value);
		assertEquals("Should be 21 at index 1","21",chainHashtable.getBuckets()[1].next.value);
		assertEquals("Should be 2 at index 2","2",chainHashtable.getBuckets()[2].value);
		assertEquals("Should be 3 at index 3", "3",chainHashtable.getBuckets()[3].value);
		assertEquals("Should be 13 at index 13","13",chainHashtable.getBuckets()[13].value);
		assertEquals("Should be 4 at index 4","4",chainHashtable.getBuckets()[4].value);
		assertEquals("Should be 5 at index 5","5",chainHashtable.getBuckets()[5].value);
		assertEquals("Should be null at index 0",null,chainHashtable.getBuckets()[0]);
		assertEquals("Should be 31 at index 11","31",chainHashtable.getBuckets()[11].next.value);
		System.out.println("Chain Hash table size before removals: "+ chainHashtable.size());
		chainHashtable.remove(31);
		assertEquals("Should be null",null,chainHashtable.getBuckets()[11].next);
		chainHashtable.remove(11);
		assertEquals("Should be null",null,chainHashtable.getBuckets()[11]);
		System.out.println("Chain Hash table size after removal: "+ chainHashtable.size());
	}
	@Test
	public void testCoalesced(){
		coalescedHashtable.setRegrowModifier(0);
		coalescedHashtable.insert(1, "1");
		coalescedHashtable.insert(11, "11");
		coalescedHashtable.insert(13, "13");
		coalescedHashtable.insert(4, "4");
		coalescedHashtable.insert(14, "14");
		assertEquals("Should be 1 at index 1","1",coalescedHashtable.getBuckets()[1].value);
		assertEquals("Should be 11 at index 2","11",coalescedHashtable.getBuckets()[2].value);
		assertEquals("Should be 11 for bucket[1].next","11",coalescedHashtable.getBuckets()[1].next.value);
		assertEquals("Should be 13 at index 3", "13",coalescedHashtable.getBuckets()[3].value);
		assertEquals("Should be 4 at index 4","4",coalescedHashtable.getBuckets()[4].value);
		assertEquals("Should be 14 at index 5","14",coalescedHashtable.getBuckets()[5].value);
		assertEquals("Should be 14 for bucket[4].next","14",coalescedHashtable.getBuckets()[4].next.value);
		assertEquals("Should be null at index 0",null,coalescedHashtable.getBuckets()[0]);
		coalescedHashtable.insert(34, "34");
		coalescedHashtable.insert(31, "31");
		assertEquals("Should be 1 at index 1","1",coalescedHashtable.getBuckets()[1].value);
		assertEquals("Should be 11 at index 11","11",coalescedHashtable.getBuckets()[11].value);
		assertEquals("Should be 13 at index 13", "13",coalescedHashtable.getBuckets()[13].value);
		assertEquals("Should be 4 at index 4","4",coalescedHashtable.getBuckets()[4].value);
		assertEquals("Should be 14 at index 14","14",coalescedHashtable.getBuckets()[14].value);
		assertEquals("Should be 34 for bucket[14].next","34",coalescedHashtable.getBuckets()[14].next.value);
		assertEquals("Should be 31 for bucket[11].next","31",coalescedHashtable.getBuckets()[11].next.value);
		assertEquals("Should be null at index 0",null,coalescedHashtable.getBuckets()[0]);
		System.out.println("Coalesced Hash table size before removal: "+ coalescedHashtable.size());
		coalescedHashtable.remove(31);
		assertEquals("Should be invalid",false,coalescedHashtable.getBuckets()[11].next.valid);
		System.out.println("Coalesced Hash table size after removal: "+ coalescedHashtable.size());
	
	}
}
