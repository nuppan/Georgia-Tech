/**
 * This is HW11, Monster
 * @author Joon Ki Hong
 * @version 4/14/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The Monster abstract class contains the non abstract methods that describes the generic structure of
 * a monster.
 */
public abstract class Monster {
	protected String name;
	
	public Monster(String name){
		this.name = name;
	}
	
	//Various "actions" the user may take to defeat a monster.
	public boolean action1(){
		System.out.println("The "+name+" is impervious to your futile action.");
		return false;
	}
	public boolean action2(){
		System.out.println("The "+name+" is impervious to your futile action.");
		return false;
	}
	public boolean action3(){
		System.out.println("The "+name+" is impervious to your futile action.");
		return false;
	}
	
	public String toString(){
		return ("a ferocious "+name);
	}

}
