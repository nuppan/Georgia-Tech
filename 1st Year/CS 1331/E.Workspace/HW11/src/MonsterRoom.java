import java.util.*;
/**
 * This is HW11, MonsterRoom
 * @author Joon Ki Hong
 * @version 4/14/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The MonsterRoom class extends the Room abstract class and overrides the toString method by calling the
 * super method and returning an additional string that is associated with a MonsterRoom object.
 */
public class MonsterRoom extends Room{
	Monster currentMonster;
	
	public MonsterRoom(){
		Random rand = new Random();
		//Generate a random subclass of the Monster abstract class and initialize the currentMonster variable.
		switch(rand.nextInt(3)){
			case 0:
				currentMonster = new RedMonster();
				break;
			case 1:
				currentMonster = new GreenMonster();
				break;
			case 2:
				currentMonster = new BlueMonster();
				break;
		}
	}
	
	/**
	 * The getMonster method returns the current monster associated to a an instance of the MonsterRoom object.
	 * @return Monster
	 */
	public Monster getMonster(){
		return currentMonster;
	}
	
	public String toString(){
		return (super.toString()+"and you encounter "+currentMonster.toString());
	}
}
