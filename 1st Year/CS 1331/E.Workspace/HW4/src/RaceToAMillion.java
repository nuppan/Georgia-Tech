/**
 * This is HW4, RaceToAMillion
 * @author Joon Ki Hong
 * @version 2/17/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The RaceToAMillion class provides the basic functionalities for the game and creates instances for 2 players (Monica and Fisayo)
 */


public class RaceToAMillion {
	private Player player1;
	private Player player2;
	private int rndsPlayed;
	final public int TARGET_SCORE = 1000000;
	
	/**
	 * Initializes two Player instances, Monica and Fisayo.
	 */
	public RaceToAMillion(){
		player1 = new Player("Monica");
		player2 = new Player("Fisayo");
	}
	
	/**
	 * Calls the rollDie method for each player and updates the number of rounds played
	 */
	public void toss(){
		player1.rollDie();
		player2.rollDie();
		rndsPlayed += 1;
	}
	
	/**
	 * Checks if neither of the players have passed the TARGET_SCORE, if so then it calls the toss method
	 */
	public void tossUntilSomeoneWins(){
		while ( player1.getScore() < TARGET_SCORE && player2.getScore() < TARGET_SCORE ){
			toss();
		}
	}
	
	/**
	 * After the loop in the tossUntilSomeoneWins method is finished the determineWinner method prints the
	 * number of rounds played and determines which of the two players have won. If the two players pass 
	 * the target score within the same number of rounds, both players win.
	 */
	public void determineWinner(){
		System.out.println("After "+rndsPlayed+" rounds...");
		if (player1.getScore() >= TARGET_SCORE && player2.getScore() >= TARGET_SCORE){
			System.out.println("It's a tie! "+ player1.getName() +" has "+ player1.getScore() +" points and "+ player2.getName() +" has "+ player2.getScore() +" points");
		}
		else{
			if (player1.getScore() >= TARGET_SCORE){
				System.out.println(player1.getName() +" wins with "+ player1.getScore() +" points");
			}
			else {
				System.out.println(player2.getName() +" wins with "+ player2.getScore() +" points");
			}
		}
	}
	
	/**
	 * Calls both the tossUntilSomeoneWins and determineWinner methods
	 */
	public void go(){
		tossUntilSomeoneWins();
		determineWinner();
	}
}
