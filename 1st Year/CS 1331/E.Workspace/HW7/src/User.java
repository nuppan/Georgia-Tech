import java.util.*;
/**
 * This is HW7, User
 * @author Joon Ki Hong
 * @version 3/10/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The User class contains any methods and attributes regarding the actual player of the slot machine
 * game.
 */
public class User {

	private int currentMoney;
	private int payOff;
	private int bet;
	private int lastPlayed;
	private String randResult;
	private Random rand = new Random();
	final private int MINIMUM_BET = 1;
	final private int STARTING_AMT = 50;
	

	
	/**
	 * The constructor initializes the current money with the default starting amount.
	 */
	public User(){
		currentMoney = STARTING_AMT;
	}
	
	/**
	 * The spin method generates random numbers depending on the boolean passed in and 
	 * initializes the randResult string object with a string representation of the
	 * symbols generated.
	 * @param testMode This boolean is passed in as a check to see whether or not to generate
	 * 3 random numbers or 6, depending on the mode set in the InputPanel class (test mode or
	 * regular mode)
	 */
	public void spin(boolean testMode){

		randResult = "";
		int slot;
		for (int slots = 1; slots<=3;slots++){
			if (testMode){
				slot = rand.nextInt(3)+1;      // ignores all line symbols (3 symbols)
			}
			else{
				slot = rand.nextInt(6)+1;      // takes into account the lines (6 symbols)
			}
			switch(slot){
			case 1:
				if (slots==3){
					randResult = randResult + "CHERRY";
				}
				else{
					randResult = randResult + "CHERRY ";
				}
				break;
			case 2:
				if (slots==3){
					randResult = randResult + "GRAPE";
				}
				else{
					randResult = randResult + "GRAPE ";
				}
				break;
			case 3:
				if (slots==3){
					randResult = randResult + "BELL";
				}
				else{
					randResult = randResult + "BELL ";
				}
				break;
			case 4:
				if (slots==3){
					randResult = randResult + "LINE";
				}
				else{
					randResult = randResult + "LINE ";
				}
				break;
			case 5:
				if (slots==3){
					randResult = randResult + "LINE";
				}
				else{
					randResult = randResult + "LINE ";
				}
				break;
			case 6:
				if (slots==3){
					randResult = randResult + "LINE";
				}
				else{
					randResult = randResult + "LINE ";
				}		
				break;
			}
		}
	}
	
	/**
	 * The updateMoney method updates the current money after the spin method has been called.
	 */
	public void updateMoney(){
		calculatePayOff();
		if (payOff > 0){
			currentMoney = currentMoney - bet +(payOff*bet); //update accounts for the amount the user bet initially
			lastPlayed = (payOff*bet) - bet;
		}
		else {
			currentMoney = currentMoney - bet;
			lastPlayed = (-1*bet);         //factor of -1 to indicate a loss
		}
	}
	/**
	 * The calculatePayOff method calculates the pay off multiplier by parsing randResult string representation using a
	 * predefined set of combinations that result in a set pay off.
	 */
	private void calculatePayOff(){
		int cherries=0,bells=0,grapes=0,lines=0;
		Scanner scan = new Scanner(randResult);
		//parses the string for symbols
		while (scan.hasNext()){
			String word = scan.next();
			if (word.equals("CHERRY")){
				cherries +=1;
			}
			else
				if (word.equals("BELL")){
					bells+=1;
				}
				else
					if (word.equals("GRAPE")){
						grapes+=1;
					}
					else{
						lines+=1;
					}
		}
		// calculates pay off based on accumulation of symbols
		if (bells==3){
			payOff = 12;
		}
		else
			if (grapes==3){
				payOff=8;
			}
			else 
				if (cherries==3){
					payOff=5;
				}
				else
					if (cherries==2){
						payOff=2;
					}
					else
						if (cherries==1){
							payOff=1;
						}
						else{
							payOff=0;
						}
	}
	
	/**
	 * The setBet method checks to see if is legal then sets the bet accordingly
	 * @param bet This parameter represents the bet input by the user.
	 */
	public void setBet(int bet){
		if (bet > currentMoney || bet < MINIMUM_BET){
			this.bet = 0;
		}
		else{
			this.bet = bet;
		}
	}
	
	/**
	 * The resetMoney method resets the current money to the default amount (50)
	 */
	public void resetMoney(){
		currentMoney = STARTING_AMT;
	}
	
	/**
	 * The getGainOrLoss method returns the winnings from the last spin
	 * @return lastPlayed
	 */
	public int getGainOrLoss(){
		return lastPlayed;
	}
	
	/**
	 * The getCurrentMoney method returns the current amount of money.
	 * @return currentMoney
	 */
	public int getCurrentMoney(){
		return currentMoney;
	}
	
	/**
	 * The getSlots method returns the string representation of the randomly generated symbols.
	 * @return randResult
	 */
	public String getSlots(){
		return randResult;
	}
	
	/**
	 * The play method is the method that simplifies the playing logic (methods in the class) into
	 * one method. It returns a boolean based on whether or not the play was legal (depending
	 * if the bet was legal or not).
	 * @param betNumber This parameter indicates whether or not the bet is legal
	 * @param testMode This parameter indicates the test mode currently set
	 * @return boolean
	 */
	public boolean play(int betNumber,boolean testMode){
		setBet(betNumber);
		if (bet == 0){
			return false;
		}
		else{
			spin(testMode);
			updateMoney();
			return true;
		}
	}
	
}
