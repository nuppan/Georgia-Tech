import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


@SuppressWarnings("serial")
public class Panel4 extends JPanel{
	private Player p1;
	private Player p2;
	private JButton addColumn0,addColumn1,addColumn2,addColumn3,addColumn4,addColumn5,addColumn6,addColumn7;
	private boolean p1Turn;
	private Board board;
	private Piece[][] state;
	private Piece[][] visualBoard;
	
	public Panel4(){
		
		p1Turn = true;
		//Create board
		board = new Board();
		state = board.getState();
		visualBoard = new Piece[Board.COL][Board.ROW];
		
		setLayout(new GridLayout(Board.ROW+2,Board.COL));
		setPreferredSize(new Dimension(400,490));
		String p1Name,p2Name;
		char p1Symbol,p2Symbol;
		//Prompt player 1 and 2 for names and symbols
		
		p1Name = JOptionPane.showInputDialog("Please enter Player 1's name: ");
		p2Name = JOptionPane.showInputDialog("Please enter Player 2's name: ");
		String tempChar = JOptionPane.showInputDialog("Please enter Player 1's symbol as a character: ");
		p1Symbol = tempChar.charAt(0);
		tempChar = JOptionPane.showInputDialog("Please enter Player 2's symbol as a character: ");
		p2Symbol = tempChar.charAt(0);
		
		//Initialize players
		p1 = new Player(p1Name,p1Symbol);
		p1.setColor(Color.RED);
		p2 = new Player(p2Name,p2Symbol);
		p2.setColor(Color.BLACK);
		
		for(int r=Board.ROW-1;r>=0;r--){
			for(int c=Board.COL-1;c>=0;c--){
				visualBoard[c][r] = new Piece();
			}
		}
		for (Piece[] col:visualBoard){
			for (Piece colElement:col){
				add(colElement);
			}
		}
		
		addColumn0 = new JButton("Drop");
		addColumn1 = new JButton("Drop");
		addColumn2 = new JButton("Drop");
		addColumn3 = new JButton("Drop");
		addColumn4 = new JButton("Drop");
		addColumn5 = new JButton("Drop");
		addColumn6 = new JButton("Drop");
		addColumn7 = new JButton("Drop");
		addColumn0.addActionListener(new ButtonListener(0));
		addColumn1.addActionListener(new ButtonListener(1));
		addColumn2.addActionListener(new ButtonListener(2));
		addColumn3.addActionListener(new ButtonListener(3));
		addColumn4.addActionListener(new ButtonListener(4));
		addColumn5.addActionListener(new ButtonListener(5));
		addColumn6.addActionListener(new ButtonListener(6));
		addColumn7.addActionListener(new ButtonListener(7));
		add(addColumn0);
		add(addColumn1);
		add(addColumn2);
		add(addColumn3);
		add(addColumn4);
		add(addColumn5);
		add(addColumn6);
		add(addColumn7);
		
		
	}
	
	private class ButtonListener implements ActionListener{
		private int column;
		public ButtonListener(int column){
			this.column = column;		
		}
		public void actionPerformed(ActionEvent event){
			if(p1Turn==true){
				Piece piece = p1.addPiece();
				board.move(column,piece);
			}else{
				Piece piece = p2.addPiece();
				board.move(column,piece);
			}
			state = board.getState();
			drawBoard(state);
			//Check for a winning state
			if (board.checkForWin()==true){
				if (p1Turn==true){
					System.out.println(p1+" won!");
				}else{
					System.out.println(p2+" won!");
				}
				//Prompt user to play again
				String again = JOptionPane.showInputDialog("Play again? (y/n): ").trim();
				if(again.compareTo("y")==0){
					board.reset();
					p1Turn = true;
				}
			}else{
				board.dropColumn(column);
				p1Turn = !p1Turn;
			}
			//Update visual board
			state = board.getState();
			for (int c=Board.COL-1;c>=0;c--){
				for(int r=Board.ROW-1;r>=0;r--){
					if (state[c][r]!=null){
						if (p1Turn==true){
							visualBoard[c][r] = p1.addPiece();
						}
						else{
							visualBoard[c][r] = p2.addPiece();
						}
					}
				}
			}
		}
	}
	
	private static void drawBoard(Piece[][] state){
		int COL = Board.COL;
		int ROW = Board.ROW;
		for(int r=(ROW-1);r>=0;r--){
			String rowString = "";
			for(int c=0;c<COL;c++){
				if (state[c][r]!=null){
					rowString+=state[c][r].toString()+" ";
				}else{
					rowString+="* ";
				}
			}
			System.out.println(rowString);
		}
		String indexString = "";
		for (int c=0;c<COL;c++){
			indexString+=(c+" ");
		}
		System.out.println(indexString);
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame("Connect 4");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Panel4 panel = new Panel4();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
