import java.awt.*;
import javax.swing.*;

/**
 * This is HW9, TilePanel
 * @author Joon Ki Hong
 * @version 4/1/11
 * xjo0nn@gatech.edu
 * CS1331 B3 
 * I did this assignment alone using only the resources provided to me on the 2011 Spring CS1331 website
 * The TilePanel class extends JPanel and implements the Ownable interface. The class represents the visual
 * aspects of a single tile and has methods to change its visual aspects accordingly to the game's progress.
 */
public class TilePanel extends JPanel implements Ownable{
	private Color tileColor;
	private Player player;
	
	public TilePanel(){
		setPreferredSize(new Dimension(100,100));
		player = null;
		tileColor = null;
	}
	
	/* 
	 * The getOwner method returns a player objects that is associated with an instance of the TilePanel object.
	 * @see Ownable#getOwner()
	 */
	public Player getOwner(){
		return player;
	}
	
	/* 
	 * The setOwner method takes in a Player object as a parameter and sets the current player of a particular instance
	 * of the TilePanel object
	 * @see Ownable#setOwner(Player)
	 */
	public void setOwner(Player player){
		if (player == null){
			tileColor = null;
			this.player = player;
		}
		else{
			this.player = player;
			if (player.getColor() == Color.red){
				tileColor = Color.red;
			}
			else{
				tileColor = Color.blue;
			}
		}
		repaint();
	}
	
	/* The paintComponent method is extended from the JPanel class. It handles any visual aspects of the tile.
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics graphics){
		super.paintComponent(graphics);
		graphics.setColor(Color.gray);
		graphics.drawRect(0,0,180,180);
		if (tileColor == null){
			removeAll();
		}
		else{
			graphics.setColor(player.getColor());
			graphics.fillOval(7,0,150,90);
		}
	}
}
