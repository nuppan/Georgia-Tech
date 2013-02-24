/**
 * This is HW11, EmptyRoom
 * @author Joon Ki Hong
 * @version 4/14/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The EmptyRoom class extends the Room abstract class and overrides the toString method by calling the
 * super method and returning an additional string that is associated with an EmptyRoom object.
 */
public class EmptyRoom extends Room{

	public String toString(){
		return (super.toString()+ "but it appears to be empty...");
	}
}
