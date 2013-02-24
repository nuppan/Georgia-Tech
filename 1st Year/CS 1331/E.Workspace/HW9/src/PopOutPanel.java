import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * This is HW9, PopOutPanel
 * @author Joon Ki Hong
 * @version 4/1/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The PopOutPanel class extends the JPanel class and implements the Winnable, Switchable, Addable, and 
 * Poppable interfaces. The class handles the logic, and buttons of the game while communicating with the
 * TilePanel class and using two instances of the Player class.
 */
public class PopOutPanel extends JPanel implements Winnable,Switchable,Addable,Poppable{
	private TilePanel[][] gameArray;
	private JButton addColumn0,addColumn1,addColumn2,addColumn3,addColumn4,addColumn5,addColumn6,removeColumn0,removeColumn1,removeColumn2,removeColumn3,removeColumn4,removeColumn5,removeColumn6;
	private Player player1,player2;
	private Player currentPlayer;
	private boolean legality;
	private boolean win;
	
	public PopOutPanel(){
		player1 = new Player(Color.red);
		player2 = new Player(Color.blue);
		currentPlayer = player1;
		legality = true;
		setLayout(new GridLayout(8,7));
		gameArray = new TilePanel[6][7];
		addColumn0 = new JButton("Add to Column 0");
		addColumn1 = new JButton("Add to Column 1");
		addColumn2 = new JButton("Add to Column 2");
		addColumn3 = new JButton("Add to Column 3");
		addColumn4 = new JButton("Add to Column 4");
		addColumn5 = new JButton("Add to Column 5");
		addColumn6 = new JButton("Add to Column 6");
		addColumn0.addActionListener(new ButtonListener(0,true));
		addColumn1.addActionListener(new ButtonListener(1,true));
		addColumn2.addActionListener(new ButtonListener(2,true));
		addColumn3.addActionListener(new ButtonListener(3,true));
		addColumn4.addActionListener(new ButtonListener(4,true));
		addColumn5.addActionListener(new ButtonListener(5,true));
		addColumn6.addActionListener(new ButtonListener(6,true));
		
		removeColumn0 = new JButton("Remove from Column 0");
		removeColumn1 = new JButton("Remove from Column 1");
		removeColumn2 = new JButton("Remove from Column 2");
		removeColumn3 = new JButton("Remove from Column 3");
		removeColumn4 = new JButton("Remove from Column 4");
		removeColumn5 = new JButton("Remove from Column 5");
		removeColumn6 = new JButton("Remove from Column 6");
		removeColumn0.addActionListener(new ButtonListener(0,false));
		removeColumn1.addActionListener(new ButtonListener(1,false));
		removeColumn2.addActionListener(new ButtonListener(2,false));
		removeColumn3.addActionListener(new ButtonListener(3,false));
		removeColumn4.addActionListener(new ButtonListener(4,false));
		removeColumn5.addActionListener(new ButtonListener(5,false));
		removeColumn6.addActionListener(new ButtonListener(6,false));
		for (int x=0;x<gameArray.length;x++){
			for (int y=0;y<gameArray[0].length;y++){
				gameArray[x][y] = new TilePanel();
			}
		}
		for (TilePanel[] row:gameArray){
			for (TilePanel rowElement:row){
				add(rowElement);
			}
		}
		add(addColumn0);
		add(addColumn1);
		add(addColumn2);
		add(addColumn3);
		add(addColumn4);
		add(addColumn5);
		add(addColumn6);
		add(removeColumn0);
		add(removeColumn1);
		add(removeColumn2);
		add(removeColumn3);
		add(removeColumn4);
		add(removeColumn5);
		add(removeColumn6);
	}
	
	/**
	 * The ButtonListener private class handles the button functionalities and handles the logic
	 * accordingly based on winning conditions.
	 * @author xjo0n
	 *
	 */
	private class ButtonListener implements ActionListener{
		private int column;
		private boolean buttonCondition;
		public ButtonListener(int column,boolean buttonCondition){
			this.column = column;
			this.buttonCondition = buttonCondition;			
		}
		public void actionPerformed(ActionEvent event){
			if (buttonCondition == true){
				addToColumn(column);
				if (legality == true){
					checkWinCondition();
					if (win==true){
						JOptionPane.showMessageDialog(null,"You have won! The game will now reset");
						for (int x = 0;x<gameArray.length;x++){
							for (int y = 0;y<gameArray[0].length;y++){
								gameArray[x][y].setOwner(null);
							}
						}
						win = false;
					}
					switchPlayers();
				}
			}
			else{
				removeFromColumn(column);
				checkWinCondition();
				if (legality == true){
					checkWinCondition();
					switchPlayers();
				}
			}
		}
	}
	
	/* 
	 * Checks to see if the player has won after his/her coin has been placed.
	 * @see Winnable#checkWinCondition()
	 */
	public void checkWinCondition(){
		int counter = 0;
		/**
		 * This for loop checks for horizontal winning conditions
		 */
		for (int x = 0;x<gameArray.length;x++){
			for (int y = 0;y<gameArray[0].length;y++){
				if (gameArray[x][y].getOwner() == currentPlayer){
					counter = counter+1;
				}
				else{
					counter = 0;
				}
				if (counter == 4){
					win = true;
					counter = 0;
				}
			}
		}
		counter = 0;
		/**
		 * This for loop checks for vertical winning conditions
		 */
		for (int x = 0;x<gameArray[0].length;x++){
			for (int y = 0;y<gameArray.length;y++){
				if(gameArray[y][x].getOwner() == currentPlayer){
					counter = counter+1;
				}
				else{
					counter = 0;
				}
				if (counter == 4){
					win = true;
					counter = 0;
				}
			}
		}
		counter = 0;
		/**
		 * This for loop checks for negative-sloped diagonals
		 */
		for (int x = 0;x<gameArray.length-3;x++){
			for (int y = 0;y<gameArray[0].length-3;y++){
				if (gameArray[x][y].getOwner() == currentPlayer &&
						gameArray[x+1][y+1].getOwner() == currentPlayer &&
						gameArray[x+2][y+2].getOwner() == currentPlayer &&
						gameArray[x+3][y+3].getOwner() == currentPlayer){
					win = true;
				}
			}
		}
		/**
		 * This for loop checks for positive-sloped diagonals
		 */
		for (int x = 0;x<gameArray.length-3;x++){
			for (int y = 6;y>gameArray[0].length-4;y--){
				if (gameArray[x][y].getOwner() == currentPlayer &&
						gameArray[x+1][y-1].getOwner() == currentPlayer &&
						gameArray[x+2][y-2].getOwner() == currentPlayer &&
						gameArray[x+3][y-3].getOwner() == currentPlayer){
					win = true;
				}
			}
		}
	}
	/* 
	 * The switchPlayers method switches the currentPlayer variable to the alternate player.
	 * @see Switchable#switchPlayers()
	 */
	public void switchPlayers(){
		if (currentPlayer == player1){
			currentPlayer = player2;
		}
		else{
			currentPlayer = player1;
		}
	}
	
	/* 
	 * The addToColumn adds a coin in the column of choice above any other coin currently in the
	 * column
	 * @see Addable#addToColumn(int)
	 */
	public void addToColumn(int column){
		if (gameArray[5][column].getOwner() == null){
			gameArray[5][column].setOwner(currentPlayer);
			legality = true;
		}
		else if (gameArray[4][column].getOwner() == null){
			gameArray[4][column].setOwner(currentPlayer);
			legality = true;
		}
		else if (gameArray[3][column].getOwner() == null){
			gameArray[3][column].setOwner(currentPlayer);
			legality = true;
		}
		else if (gameArray[2][column].getOwner() == null){
			gameArray[2][column].setOwner(currentPlayer);
			legality = true;
		}
		else if (gameArray[1][column].getOwner() == null){
			gameArray[1][column].setOwner(currentPlayer);
			legality = true;
		}
		else if (gameArray[0][column].getOwner() == null){
			gameArray[0][column].setOwner(currentPlayer);
			legality = true;
		}
		else{
			legality = false;
		}
	}
	
	/* 
	 * The removeFromColumn method removes a coin in the column of choice from the bottom.
	 * The player may only remove the coin if it belongs to him/her.
	 * @see Poppable#removeFromColumn(int)
	 */
	public void removeFromColumn(int column){
		if (gameArray[5][column].getOwner() == currentPlayer){
			gameArray[5][column].setOwner(gameArray[4][column].getOwner());
			gameArray[4][column].setOwner(gameArray[3][column].getOwner());
			gameArray[3][column].setOwner(gameArray[2][column].getOwner());
			gameArray[2][column].setOwner(gameArray[1][column].getOwner());
			gameArray[1][column].setOwner(gameArray[0][column].getOwner());
			gameArray[0][column].setOwner(null);
			legality = true;
		}
		else{
			legality = false;
		}
	}
}
