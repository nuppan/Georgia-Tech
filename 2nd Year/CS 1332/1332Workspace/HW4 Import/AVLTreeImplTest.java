import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/*
 * AVLTreeImplTest.java
 *
 * Version 1.0
 * Copyright 2011 BobSoft Inc
 */

/**
 * @author Robert
 * @version 1.0
 *
 */
public class AVLTreeImplTest {
    private IAVLBinaryTree<Integer, String> tree;
    private IAVLBinaryTree<Integer, String> avlTree;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        tree = new AVLTreeImpl<Integer, String>(false);
        avlTree = new AVLTreeImpl<Integer, String>(true);
    }

    /**
     * Test method for {@link AVLTreeImpl#isEmpty()}.
     */
    @Test
    public void testIsEmpty() {
       assertTrue("Empty tree did not return true", tree.isEmpty());
       tree.add(20, "S");
       avlTree.add(20, "S");
       assertFalse("Tree thinks its empty after one insert", tree.isEmpty());
       assertFalse("AVL Tree thinks its empty after one insert", avlTree.isEmpty());
       tree.add(30, "X");
       tree.remove(30);
       assertFalse("Tree thinks its empty after one remove", tree.isEmpty());
       tree.remove(20);
       assertTrue("Tree thinks it still has something after removal", tree.isEmpty());
    }

    /**
     * Test method for {@link AVLTreeImpl#size()}.
     */
    @Test
    public void testSize() {
        assertEquals("Size of new tree not 0", 0, tree.size());
        tree.add(20, "S");
        assertEquals("Size after 1 insert wrong", 1, tree.size());
        tree.remove(20);
        assertEquals("Size after last remove not 0", 0, tree.size());
        for (int i = 0; i < 10 ; i++) {
            tree.add(i + i, "S");
        }
        assertEquals("Size after multiple inserts wrong", 10, tree.size());
        
    }

    /**
     *
     */
    @Test
    public void testAddNoRotations() {
        tree.add(20, "S");
        assertEquals("Root on first add wrong", (Integer) 20, tree.root());
        tree.add(30, "R");
        tree.add(35, "Z");
        tree.add(10, "P");
        tree.add(1, "AG");
        tree.add(40, "fr");
        assertEquals("Root changed on inserts", (Integer) 20, tree.root());
        assertEquals("Leftmost node not correct", (Integer) 1, tree.min());
        assertEquals("Rightmost node not correct", (Integer) 40, tree.max());
    }
    
    @Test
    public void testAddRotations() {
        avlTree.add(20, "S");
        assertEquals("Root on first add wrong", (Integer) 20, avlTree.root());
        avlTree.add(30, "R");
        avlTree.add(35, "Z");
        avlTree.add(10, "P");
        avlTree.add(5, "AG");
        avlTree.add(40, "fr");
        assertEquals("Root changed on inserts", (Integer) 30, avlTree.root());
        assertEquals("Leftmost node not correct", (Integer) 5, avlTree.min());
        assertEquals("Rightmost node not correct", (Integer) 40, avlTree.max());
        //now check double rotations
        avlTree.add(37, "x");
        assertEquals("Leftmost node after double rotation wrong", (Integer) 40, avlTree.max());
        assertEquals(2, avlTree.height());
     }

    /**
     * Test method for {@link AVLTreeImpl#remove(java.lang.Comparable)}.
     */
    @Test
    public void testRemoveNoRotations() {
        tree.add(20, "GZH");
        assertTrue(tree.remove(20));
        assertTrue("Failed to remove root", tree.isEmpty());
        assertNull("Root not deleted", tree.root());
        
        tree.add(20, "JK");
        tree.add(15, "JFR");
        tree.add(10, "MLK");
        tree.add(17, "POI");
        tree.add(5, "KOP");
        tree.add(30, "IOIJ");
        tree.add(40, "LOI");
        
        assertTrue(tree.remove(40));
        assertEquals("Leftmost not correct", (Integer) 30, tree.max());
       assertEquals(3, tree.height());
        assertTrue(tree.remove(10));
        assertEquals("rightmost not correct", (Integer) 5, tree.min());
        assertEquals(2, tree.height());
        assertTrue(tree.remove(20));
        assertEquals((Integer) 17, tree.root());
        assertTrue(tree.remove(30));
        assertTrue(tree.remove(17));
        assertTrue(tree.remove(5));
        assertTrue(tree.remove(15));
        assertEquals(0, tree.size());
        System.out.println("Root: " + tree.root());
        assertTrue(tree.isEmpty());
    }
    
    @Test
    public void testRemoveRotations() {
        avlTree.add(20, "S");
        assertEquals("Root on first add wrong", (Integer) 20, avlTree.root());
        avlTree.add(30, "R");
        avlTree.add(35, "Z");
        avlTree.add(10, "P");
        avlTree.add(1, "AG");
        avlTree.add(40, "fr");
        assertEquals("Root changed on inserts", (Integer) 30, avlTree.root());
        assertEquals("Leftmost node not correct", (Integer) 1, avlTree.min());
        assertEquals("Rightmost node not correct", (Integer) 40, avlTree.max());
        avlTree.remove(40);
        assertEquals("Root on leaf remove should be same", (Integer) 30, avlTree.root());
        avlTree.remove(35);
        assertEquals("Root after rotations wrong", (Integer) 10, avlTree.root());
    }

    /**
     * Test method for {@link AVLTreeImpl#min()}.
     */
    @Test
    public void testMin() {
        assertNull("Empty tree min was not null", tree.min());
        tree.add(20, "LD");
        assertEquals("One element tree min wrong", (Integer) 20, tree.min());
        tree.add(30, "df");
        tree.add(15, "kjd");
        tree.add(10, "kjdf");
        tree.add(5, "kdjf");
        tree.add(1, "esd");
        assertEquals("Multi-element tree min wrong", (Integer) 1, tree.min());
    }

    /**
     * Test method for {@link AVLTreeImpl#max()}.
     */
    @Test
    public void testMax() {
        assertNull("Empty tree max was not null", tree.max());
        tree.add(20, "SS");
        assertEquals("One element tree max wrong", (Integer) 20, tree.max());
        tree.add(30, "SS");
        tree.add(35, "LL");
        tree.add(40, "JJ");
        tree.add(37, "df");
        tree.add(36, "wer");
        assertEquals("Multi-element tree max wrong", (Integer) 40, tree.max());
    }
    
    @Test
    public void testFind() {
        assertNull(tree.find(40));
        tree.add(20, "s");
        tree.add(10, "c");
        assertEquals("s", tree.find(20));
        assertEquals("c", tree.find(10));
        tree.add(30, "x");
        assertEquals("x", tree.find(30));
        
    }

    /**
     * Test method for {@link AVLTreeImpl#height()}.
     */
    @Test
    public void testHeight() {
        assertEquals("Height of empty tree wrong", -1, tree.height());
        tree.add(20, "lk");
        assertEquals("Height wrong for 1 entry", 0, tree.height());
        tree.add(1, "lkd");
        assertEquals("Height wrong for 2 entry", 1, tree.height());
        tree.add(5, "ksjdf");
        tree.add(28, "skdjf");
        tree.add(32, "sd");
        tree.add(30, "d");
        tree.add(40, "zz");
        assertEquals("Height wrong for mult entries", 3, tree.height());
    }

    /**
     * Test method for {@link AVLTreeImpl#root()}.
     */
    @Test
    public void testRoot() {
        assertNull(tree.root());
        tree.add(20, "dd");
        assertEquals((Integer) 20, tree.root());
        tree.remove(20);
        assertNull(tree.root());
    }

    /**
     * Test method for {@link AVLTreeImpl#preorderTraversal()}.
     */
    @Test
    public void testPreorderTraversal() {
        List<Integer> list = tree.preorderTraversal();
        assertTrue(list.isEmpty());
        tree.add(13, "sdf");
        tree.add(9, "wer");
        tree.add(16,"sdxv");
        tree.add(5, "wer");
        tree.add(10, "sdf");
        tree.add(1, "werf");
        tree.add(6, "pk");
        tree.add(14, "nn");
        tree.add(26, "dd");
        list = tree.preorderTraversal();
        System.out.println("Pre: " + list.toString());
        assertEquals("Wrong number of elements", 9 , list.size());
        assertEquals("First element wrong", (Integer) 13, list.get(0) );
        assertEquals((Integer) 9,list.get(1));
        assertEquals((Integer) 5 , list.get(2));
        assertEquals((Integer) 26, list.get(8));
    }

    /**
     * Test method for {@link AVLTreeImpl#postorderTraversal()}.
     */
    @Test
    public void testPostorderTraversal() {
        List<Integer> list = tree.postorderTraversal();
        assertTrue(list.isEmpty());
        tree.add(13, "sdf");
        tree.add(9, "wer");
        tree.add(16,"sdxv");
        tree.add(5, "wer");
        tree.add(10, "sdf");
        tree.add(1, "werf");
        tree.add(6, "pk");
        tree.add(14, "nn");
        tree.add(26, "dd");
        list = tree.postorderTraversal();
        System.out.println("Post: " + list.toString());
        assertEquals("Wrong number of elements", 9 , list.size());
        assertEquals("First element wrong", (Integer) 1, list.get(0) );
        assertEquals((Integer) 6,list.get(1));
        assertEquals((Integer) 5, list.get(2));
        assertEquals((Integer) 13, list.get(8));
    }

    /**
     * Test method for {@link AVLTreeImpl#inorderTraversal()}.
     */
    @Test
    public void testInorderTraversal() {
        List<Integer> list = tree.inorderTraversal();
        assertTrue(list.isEmpty());
        tree.add(13, "sdf");
        tree.add(9, "wer");
        tree.add(16,"sdxv");
        tree.add(5, "wer");
        tree.add(10, "sdf");
        tree.add(1, "werf");
        tree.add(6, "pk");
        tree.add(14, "nn");
        tree.add(26, "dd");
        list = tree.inorderTraversal();
        System.out.println("In: " + list.toString());
        assertEquals("Wrong number of elements", 9 , list.size());
        assertEquals("First element wrong", (Integer) 1, list.get(0) );
        assertEquals((Integer) 5,list.get(1));
        assertEquals((Integer) 6, list.get(2));
        assertEquals((Integer) 26, list.get(8));
    }

    /**
     * Test method for {@link AVLTreeImpl#levelorderTraversal()}.
     */
    @Test
    public void testLevelorderTraversal() {
        List<Integer> list = tree.levelorderTraversal();
        assertTrue(list.isEmpty());
        tree.add(13, "sdf");
        tree.add(9, "wer");
        tree.add(16,"sdxv");
        tree.add(5, "wer");
        tree.add(10, "sdf");
        tree.add(1, "werf");
        tree.add(6, "pk");
        tree.add(14, "nn");
        tree.add(26, "dd");
        list = tree.levelorderTraversal();
        System.out.println("Level: " + list.toString());
        assertEquals("Wrong number of elements", 9 , list.size());
        assertEquals("First element wrong", (Integer) 13, list.get(0) );
        assertEquals((Integer) 9,list.get(1));
        assertEquals((Integer) 16, list.get(2));
        assertEquals((Integer) 6, list.get(8));
    }

}
