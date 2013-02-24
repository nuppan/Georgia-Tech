import java.util.*;
/**
 * This is HW11, DungeonCrawler
 * @author Joon Ki Hong
 * @version 4/14/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The DungeonCrawler class provides the logic of the game itself and contains the various methods that
 * allow the game to work in the way it does. It uses Polymorphism and ArrayLists.
 */
public class DungeonCrawler {
	private ArrayList<Room> roomArray = new ArrayList<Room>();
	private int roomsAdded;
	private int totalTreasure;
	private int monsterSeen;
	private int monsterBeaten;
	private int monsterLost;
	public DungeonCrawler(){
		roomsAdded = 0;
		totalTreasure = 0;
		monsterSeen = 0;
		monsterBeaten = 0;
		monsterLost = 0;
		//calls the addRandomRoom method 10 times.
		for (int x = 0;x<10;x++){
			addRandomRoom();
		}
	}
	
	/**
	 * The addRandomRoom method adds a randomly selected instance of a subclass of a Room object and
	 * adds that instance to the end of the roomArray ArrayList. 
	 */
	public void addRandomRoom(){
		Random rand = new Random();
		switch (rand.nextInt(3)){
			case 0:
				roomArray.add(new EmptyRoom());
				roomsAdded = roomsAdded + 1;
				break;
			case 1:
				roomArray.add(new TreasureRoom());
				roomsAdded = roomsAdded + 1;
				break;
			case 2:
				roomArray.add(new MonsterRoom());
				roomsAdded = roomsAdded + 1;
				break;
		}
	}
	
	/**
	 * The pickUpTreasure method updates the totalTreasure variable by appending the treasure gained from
	 * a TreasureRoom instance.
	 * @param tresRoom
	 */
	public void pickUpTreasure(TreasureRoom tresRoom){
		totalTreasure = totalTreasure + tresRoom.getTreasure();
		
	}
	/**
	 * The chooseAction method takes in a MonsterRoom object and prompts the user to choose one of the three
	 * given actions. Once the action is chosen, the appropriate variables are updated for each case.
	 * @param monstRoom
	 */
	public void chooseAction(MonsterRoom monstRoom){
		Scanner scan = new Scanner(System.in);
		System.out.println("Select an action to take against the foe.");
		System.out.println("1) Drop Kick");
		System.out.println("2) Face Palm");
		System.out.println("3) Poke");
		//actions taken for each depending case.
		switch (scan.nextInt()){
			case 1:
				if (monstRoom.getMonster().action1()){
					monsterSeen = monsterSeen + 1;
					monsterBeaten = monsterBeaten + 1;
				}
				else {
					monsterSeen = monsterSeen + 1;
					monsterLost = monsterLost + 1;
					addRandomRoom();
				}
				break;
			case 2:
				if (monstRoom.getMonster().action2()){
					monsterSeen = monsterSeen + 1;
					monsterBeaten = monsterBeaten + 1;
				}
				else {
					monsterSeen = monsterSeen + 1;
					monsterLost = monsterLost + 1;
					addRandomRoom();
				}
				break;
			case 3:
				if (monstRoom.getMonster().action3()){
					monsterSeen = monsterSeen + 1;
					monsterBeaten = monsterBeaten + 1;
				}
				else {
					monsterSeen = monsterSeen + 1;
					monsterLost = monsterLost + 1;
					addRandomRoom();
				}
				break;
		}
		
	}
	
	/**
	 * The nextRoom method takes in the first Room object from the roomArray ArrayList and either calls
	 * pickUpTreasure or chooseAction depending on the type of Room the first element is. It then
	 * removes that Room object after processing is completed.
	 */
	public void nextRoom(){
		Room tempRoom = roomArray.get(0);
		System.out.println(tempRoom.toString());
		if (tempRoom instanceof TreasureRoom){
			pickUpTreasure((TreasureRoom)tempRoom);
		}
		else if (tempRoom instanceof MonsterRoom){
			chooseAction((MonsterRoom)tempRoom);
		}
		roomArray.remove(0);
	}
	
	/**
	 * The hasNextRoom method checks to see if there are any more elements remaining in the roomArray
	 * ArrayList and returns a boolean.
	 * @return boolean
	 */
	public boolean hasNextRoom(){
		if (roomArray.isEmpty()){
			return false;
		}
		else{
			return true;
		}
	}
	/**
	 * The showResults method prints out various information regarding the players' progress in the game.
	 */
	public void showResults(){
		System.out.println("You passed through "+roomsAdded+" rooms.");
		System.out.println("You fought "+monsterSeen+" monsters.");
		System.out.println("Of which you beat "+monsterBeaten+" monsters...");
		System.out.println("... and lost to "+monsterLost+" monsters.");
		System.out.println("And as a reward to yourself, you made it out with "+totalTreasure+" gold.");
	}
}
