/**
 * This is HW11, RedMonster
 * @author Joon Ki Hong
 * @version 4/14/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The RedMonster class extends the Monster class and overrides the action1 method to return true.
 */
public class RedMonster extends Monster{
	private final static String name = "Red Monster";
	public RedMonster(){
		super(name);
	}
	/**
	 * The action1 method of the RedMonster class returns true to demonstrate the fact that a
	 * Red monster is weak against action3. Therefore it returns true.
	 * @return boolean
	 */
	public boolean action1(){
		System.out.println("The Red Monster has been defeated.. proceed with caution.");
		return true;
	}
}
