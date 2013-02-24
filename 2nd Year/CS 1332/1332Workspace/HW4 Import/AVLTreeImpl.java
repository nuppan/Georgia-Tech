

public class AVLTreeImpl<K extends Comparable<K>, V> 
               implements IAVLBinaryTree<K, V> {
    
	/** A count of the approximate number of compares done */
    private int opsCount;
    
	/**
	 * Create a new object
	 * @param rotate true if this tree should do rotations, false otherwise
	 */
    public AVLTreeImpl(boolean rotate) {
       
    }
    
	/**
	 * reset the opsCount to zero for a fresh experiment
	 */
    public void resetOpsCount() {
        opsCount = 0;
    }
    
	/**
	 * @return an integer representing the approximate number of compares done
	 */
    public int getOpsCount() {
        return opsCount;
    }

   
    
	/**
	 * Run some experiments and draw the trees.  This code does not require removal to be working
	 */
    public static void main(String[] args) {
        AVLTreeImpl<Integer, String> regTree = new AVLTreeImpl<Integer, String>(false);
        AVLTreeImpl<Integer, String> avlTree = new AVLTreeImpl<Integer, String>(true);
        
        System.out.println("**************************************");
        System.out.println("  RANDOM DATA");
		System.out.println("**************************************");
		//use a canned seed here so we always get same randoms
        Random rand = new Random(34539);
        int n;
		//add 50 random numbers
        for (int i = 0; i < 50 ; i++) {
            n = rand.nextInt(1000);
            regTree.add(n, "a" + i);
            avlTree.add(n, "a" + i);
        }
        
        System.out.println("Regular Tree Add Count: " + regTree.getOpsCount());
        System.out.println("AVL Tree Add Count: " + avlTree.getOpsCount());
        regTree.resetOpsCount();
        avlTree.resetOpsCount();
        CS1332utils.Display.Draw.dispTree(regTree.getRootNode());
        CS1332utils.Display.Draw.dispTree(avlTree.getRootNode());
        
        regTree.min();
        avlTree.min();
        System.out.println("Regular Tree Min Count: " + regTree.getOpsCount());
        System.out.println("AVL Tree Min Count: " + avlTree.getOpsCount());
        regTree.resetOpsCount();
        avlTree.resetOpsCount();
        
		//Now try to find 50 random numbers
        for (int i = 0; i < 50; i++) {
            n = rand.nextInt(1000);
            regTree.find(n);
            avlTree.find(n);
        }
        
        System.out.println("Regular Tree Find Count: " + regTree.getOpsCount());
        System.out.println("AVL Tree Find Count: " + avlTree.getOpsCount());
        regTree.resetOpsCount();
        avlTree.resetOpsCount();
        
        System.out.println("Regular Tree Height = " + regTree.height());
        System.out.println("AVL Tree Height = " + avlTree.height());
        
        System.out.println("******************************");
        System.out.println("  SORTED DATA");
		System.out.println("******************************");
        
        regTree = new AVLTreeImpl<Integer, String>(false);
        avlTree = new AVLTreeImpl<Integer, String>(true);
        
		//Now add 50 numbers in order
        for (int i = 1; i < 50; i++) {
            regTree.add(i, "a" + i);
            avlTree.add(i, "a" + i);
        }
        
        System.out.println("Regular Tree Add Count: " + regTree.getOpsCount());
        System.out.println("AVL Tree Add Count: " + avlTree.getOpsCount());
        regTree.resetOpsCount();
        avlTree.resetOpsCount();
        
        CS1332utils.Display.Draw.dispTree(regTree.getRootNode());
        CS1332utils.Display.Draw.dispTree(avlTree.getRootNode());
        
        regTree.max();
        avlTree.max();
        System.out.println("Regular Tree Max Count: " + regTree.getOpsCount());
        System.out.println("AVL Tree Max Count: " + avlTree.getOpsCount());
        regTree.resetOpsCount();
        avlTree.resetOpsCount();
        
		//Now find 50 random numbers 
        for (int i = 0; i < 50; i++) {
            n = rand.nextInt(50);
            regTree.find(n);
            avlTree.find(n);
        }
        
        System.out.println("Regular Tree Find Count: " + regTree.getOpsCount());
        System.out.println("AVL Tree Find Count: " + avlTree.getOpsCount());
        regTree.resetOpsCount();
        avlTree.resetOpsCount();
        
        System.out.println("Regular Tree Height = " + regTree.height());
        System.out.println("AVL Tree Height = " + avlTree.height());
            
    }
}
