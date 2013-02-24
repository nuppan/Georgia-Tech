
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/*
 * BSTNodeTest.java
 *
 * Version 1.0
 * Copyright 2011 BobSoft Inc
 */

/**
 * @author Robert
 * @version 1.0
 *
 */
public class BSTNodeTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }
    
    @Test
    public void testHeightAndBalanceFactor() {
        BSTNode<String, String> root = new BSTNode<String, String>("S", "S");
        BSTNode<String, String> node = new BSTNode<String, String>("X", "X");
        assertEquals("Height of leaf wrong", 0, root.height());
        assertEquals("Balance factor of leaf wrong", 0, root.balanceFactor());
        root.setLeft(node);
        assertEquals("Height of left heavy node wrong", 1, root.height());
        assertEquals(1, root.balanceFactor());
        node = new BSTNode<String, String>("Z", "Z");
        root.getLeft().setLeft(node);
        assertEquals(2, root.height());
        assertEquals(2, root.balanceFactor());
        root.setRight(root.getLeft());
        root.getRight().setRight(root.getRight().getLeft());
        root.setLeft(null);
        assertEquals(2, root.height());
        assertEquals(-2, root.balanceFactor());
        
    }

}
