import java.util.*;
/**
 * This is HW11, TreasureRoom
 * @author Joon Ki Hong
 * @version 4/14/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The TreasureRoom class extends the Room abstract class and overrides the toString method by calling the
 * super method and returning an additional string that is associated with an TreasureRoom object.
 */
public class TreasureRoom extends Room{
	private int treasure;
	//An array of possible treasure values.
	private final static int[] treasureList = new int[] {10,20,30,40,50,100};
	
	public TreasureRoom(){
		Random rand = new Random();
		//randomly initializes a predefined value for treasure
		treasure = treasureList[rand.nextInt(6)];
	}
	
	/**
	 * The getTreasure method returns the treasure value of a particular instance of a TreasureRoom object.
	 * @return int
	 */
	public int getTreasure(){
		return treasure;
	}
	
	public String toString(){
		return (super.toString()+"and you find "+treasure+" gold.");
	}
}
