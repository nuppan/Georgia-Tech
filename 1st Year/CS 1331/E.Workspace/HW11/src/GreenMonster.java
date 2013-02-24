/**
 * This is HW11, GreenMonster
 * @author Joon Ki Hong
 * @version 4/14/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The GreenMonster class extends the Monster class and overrides the action2 method to return true.
 */
public class GreenMonster extends Monster{
	private final static String name = "Green Monster";
	public GreenMonster(){
		super(name);
	}
	/**
	 * The action2 method of the GreenMonster class returns true to demonstrate the fact that a
	 * Green monster is weak against action2. Therefore it returns true.
	 * @return boolean
	 */
	public boolean action2(){
		System.out.println("The Green Monster has been defeated.. proceed with caution.");
		return true;
	}
}
