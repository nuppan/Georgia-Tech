import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
/**
 * This is HW7, InputPanel
 * @author Joon Ki Hong
 * @version 3/10/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The InputPanel class deals with all input and output elements of the Slot Machine game. These include
 * buttons, text fields, and labels associated with I/O elements.
 */
public class InputPanel extends JPanel {
	
	JButton spinButton;
	JButton resetButton;
	JRadioButton Test, Regular;
	JTextField betText;
	JLabel moneyText, winnings, inputLabel;
	private boolean testMode;
	User player;
	DisplayPanel displayPanel;
	
	/**
	 * This constructor instantiates all visual I/O and related elements in a Grid Layout.
	 * @param displayPanel The DisplayPanel object passed in. The object was passed in because of the
	 * close relationship between the DisplayPanel and the RadioButton elements of InputPanel
	 * @param player The User object passed in 
	 */
	public InputPanel(DisplayPanel displayPanel, User player){
		this.player = player;
		this.displayPanel = displayPanel;
		setLayout(new GridLayout(3,3,5,5));
		
		spinButton = new JButton("Spin!");
		resetButton = new JButton("Reset Money");
		betText = new JTextField(10);
		moneyText = new JLabel("Current Money: $50");
		inputLabel = new JLabel("Enter your bet below:");
		winnings = new JLabel("Winnings: $0");
		Regular = new JRadioButton("Regular Mode", true);
		Test = new JRadioButton("Test Mode");
		
		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(Regular);
		radioGroup.add(Test);
		
		ButtonListener listener = new ButtonListener();
		spinButton.addActionListener(listener);
		resetButton.addActionListener(listener);
		betText.addActionListener(listener);
		
		TestListener testListener = new TestListener();
		Regular.addActionListener(testListener);
		Test.addActionListener(testListener);
		
		add(spinButton);
		add(resetButton);
		add(inputLabel);
		add(moneyText);
		add(winnings);
		add(betText);
		add(Regular);
		add(Test);
	}
	
	/**
	 * This is the ButtonListner class that implements the ActionListener interface.
	 * This class listens and reacts to all events for the spin button and reset button.
	 */
	private class ButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			if (e.getSource() == spinButton){ // SPIN ACTIONS
				String text = betText.getText();
				int betNumber = Integer.parseInt(text);
				boolean legalPlay = player.play(betNumber,testMode);	
				if (legalPlay == false){ 			//checks if the bet was legal or not using the returned boolean from the player.play method
					if (player.getCurrentMoney() == 0){   //shows dialog if the bet was illegal, and if money was 0
						JOptionPane.showMessageDialog(null,"You've ran out of money!");
					}
					else{                                // else it was only illegal input
						JOptionPane.showMessageDialog(null,"The bet you inputted is illegal");
					}
				}
				else {                              //condition was legal
					if (player.getCurrentMoney() == 0){           //shows dialog if current money is 0
						String randResult = player.getSlots();
						displayPanel.updateSlots(randResult);
						moneyText.setText("Current Money: $" + Integer.toString(player.getCurrentMoney()));
						winnings.setText("Winnings: $" + Integer.toString(player.getGainOrLoss()));
						JOptionPane.showMessageDialog(null,"You've ran out of money!");
					}
					else{                                     //if not update visual elements 
						String randResult = player.getSlots();
						displayPanel.updateSlots(randResult);
						moneyText.setText("Current Money: $" + Integer.toString(player.getCurrentMoney()));
						winnings.setText("Winnings: $" + Integer.toString(player.getGainOrLoss()));
					}
				}
			}
			else                               // RESET ACTIONS
				if (e.getSource() == resetButton){
					int again = JOptionPane.showConfirmDialog(null,"Are you sure you want to reset your money?");
					if (again == JOptionPane.YES_OPTION){
						player.resetMoney();
						moneyText.setText("Current Money: $" + Integer.toString(player.getCurrentMoney()));
						winnings.setText("Winnings: $0");
					}
				}
		}
	}

	/**
	 * This TestListener class implements the ActionListener interface.
	 * It listens and reacts to all events regarding the radio buttons to switch between
	 * test mode and regular mode.
	 */
	private class TestListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (e.getSource() == Regular){
				testMode = false;
			}
			else if (e.getSource() == Test){
				testMode = true;
			}
		}
	}	
}
