import java.awt.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class Piece extends JPanel{
	private char symbol;
	private Color color;
	boolean filled;
	
	public Piece(char symbol, Color color,boolean filled){
		this.symbol = symbol;
		this.color = color;
		this.filled = true;
		setPreferredSize(new Dimension(50,50));
	}
	public Piece(char symbol){
		this(symbol,null,false);
	}
	public Piece(){
		this.filled = false;
	}
	public String toString(){
		return symbol+"";
	}
	
	public void paintComponent(Graphics page){
		super.paintComponent(page);
		page.drawRect(0,0,50,50);
		if (filled==true){
			page.setColor(color);
			page.fillOval(0,0,50,50);
		}
	}
	
}
