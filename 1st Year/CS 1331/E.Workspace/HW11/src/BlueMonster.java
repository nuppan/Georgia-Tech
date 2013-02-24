/**
 * This is HW11, BlueMonster
 * @author Joon Ki Hong
 * @version 4/14/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The BlueMonster class extends the Monster class and overrides the action3 method to return true.
 */
public class BlueMonster extends Monster{
	private final static String name = "Blue Monster";
	public BlueMonster(){
		super(name);
	}
	/**
	 * The action3 method of the BlueMonster class returns true to demonstrate the fact that a
	 * Blue monster is weak against action3. Therefore it returns true.
	 * @return boolean
	 */
	public boolean action3(){
		System.out.println("The Blue Monster has been defeated.. proceed with caution.");
		return true;
	}
}
