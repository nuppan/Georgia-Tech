/**
 * This is HW11, DungeonMain
 * @author Joon Ki Hong
 * @version 4/14/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The DungeonMain class contains the main method that instantiates a DungeonCrawler instance and continues
 * to run the game as long as there are more Room objects to process.
 */
public class DungeonMain {
	
	public static void main(String[] args){
		DungeonCrawler gameInstance = new DungeonCrawler();
		
		while (gameInstance.hasNextRoom()){
			gameInstance.nextRoom();
		}
		gameInstance.showResults();
	}
}
