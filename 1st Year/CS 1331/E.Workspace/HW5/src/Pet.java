import javax.swing.*;
import java.util.*;
/**
 * This is HW5, Pet
 * @author Joon Ki Hong
 * @version 2/25/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The Pet class contains all of the attributes and states for the pet object used in the pet panel. It has
 * getters for hunger, state, and current icons. 
 */
public class Pet {
	enum State {SLEEPING,EATING,DEAD,LAUGHING,NORMAL};
	private State state;
	private int hunger;
	private ImageIcon sleeping, normal, laughing, eating, dead;
	final public int FULL = 10;
	
	
	/**
	 * Instantiates new Image icons and initializes default state and hunger
	 */
	public Pet(){
		state = State.NORMAL;
		hunger = FULL;
		sleeping = new ImageIcon("sleeping.png");
		normal = new ImageIcon("normal.png");
		laughing = new ImageIcon("laughing.png");
		eating = new ImageIcon("eating.png");
		dead = new ImageIcon("dead.png");		
	}
	
	
	/**
	 * checks the state of the Pet object and returns the ImageIcon associated with the state
	 * @return returns the current ImageIcon object 
	 */
	public ImageIcon getCurrentIcon(){
		ImageIcon returnstate = null;
		switch (state){
		
			case SLEEPING:
				returnstate = sleeping;
				break;
			case EATING:
				returnstate = eating;
				break;
			case DEAD:
				returnstate = dead;
				break;
			case LAUGHING:
				returnstate = laughing;
				break;
			case NORMAL:
				returnstate = normal;
				break;
		}
		return returnstate;
	}
	
	/**
	 * checks if the current state is DEAD and for starvation. If not, then it subtracts 1 from the current hunger value and changes the state to LAUGHING
	 */
	public void poke(){
		checkForStarvation();
		if (state == State.DEAD){
			
		}
		else{
			state = State.LAUGHING;
			hunger -= 1;
		}
	}
	
	/**
	 *checks if the state is DEAD. If not it checks if the object is EATING or FULL then changes the current state to DEAD. If not the
	 *case then it changes the state to EATING and changes hunger to the constant FULL.
	 */
	public void feed(){
		if (state == State.DEAD){
		}
		else{
			if (state == State.EATING || hunger == FULL){
				state = State.DEAD;
			}
			else{
				state = State.EATING;
				hunger = FULL;				
			}
		}
	}
	
	/**
	 * checks if the the current state is DEAD. If not it changes the current state to SLEEPING.
	 */
	public void sleep(){
		if (state == State.DEAD){
		}
		else{
			state = State.SLEEPING;
		}
	}
	
	/**
	 * checks for starvation and if the state is DEAD. If not, then the method changes the state to NORMAL and subtracts 1 from the
	 * current hunger value.
	 */
	public void sit(){
		checkForStarvation();
		if (state == State.DEAD){
		}
		else{
			state = State.NORMAL;
			hunger -= 1;
		}
	}
	
	/**
	 *checks if the current state is DEAD. If not then it instantiates a new random object and uses the nextInt method of the
	 *Random class and randomly chooses a method(poke, sleep, sit).
	 */
	public void watch(){
		if (state == State.DEAD){
		}
		else{
			int randresult;
			Random rand = new Random();
			randresult = rand.nextInt(3);
			switch (randresult){
				case 0:
					poke();
					break;
				case 1:
					sleep();
					break;
				case 2:
					sit();
					break;
			}
		}
	}
	
	/**
	 *changes the current state to DEAD. 
	 */
	public void kill(){
		state = State.DEAD;
	}
	
	/**
	 *checks if the current value for hunger is 0. If so then it changes the current state to 0. 
	 */
	public void checkForStarvation(){
		if (hunger == 0){
			state = State.DEAD;
		}
	}
	
	/**
	 * gets the current value for state.
	 * @return returns the current state
	 */
	public State getState(){
		return state;
	}
	
	/**
	 * gets the current value for hunger
	 * @return returns the current value of hunger
	 */
	public int getHunger(){
		return hunger;
	}
	
}
