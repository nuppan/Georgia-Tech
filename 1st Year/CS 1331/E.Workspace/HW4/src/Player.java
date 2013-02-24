/**
 * This is HW4, Player
 * @author Joon Ki Hong
 * @version 2/17/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The Player class contains the basic attributes and methods of a player in a game of Race to a Million.
 */
public class Player {
	
	private int score;
	private String name;
	private Die die;
	
	/**
	 * Initializes the name of the player
	 * @param name The name of the player
	 */
	public Player(String name){
		this.name = name;
		Die die = new Die();
		this.die = die;
	}
	
	/**
	 * Rolls the die and adds the original sum to the rolled face value
	 */
	public void rollDie(){
		this.score = score + die.roll();
	}
	
	/**
	 * Functions as a getter and returns the name of the player
	 * @return The name of the player
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Functions as a getter and returns the current score of the player
	 * @return The current score
	 */
	public int getScore(){
		return score;
	}
	
	
}
