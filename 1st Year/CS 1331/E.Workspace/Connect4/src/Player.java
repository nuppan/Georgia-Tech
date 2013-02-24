import java.awt.Color;


public class Player {
	private String name;
	private char symbol;
	private Color color;
	
	public Player(String name,char symbol){
		this.name = name;
		this.symbol = symbol;
	}
	
	public Piece addPiece(){
		return (new Piece(symbol,color,true));
	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	public String toString(){
		return name;
	}
}
