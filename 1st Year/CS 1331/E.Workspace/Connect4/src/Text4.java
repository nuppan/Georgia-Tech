import java.util.Scanner;


public class Text4 {
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
		Scanner scan = new Scanner(System.in);
		String p1Name,p2Name;
		char p1Symbol,p2Symbol;
		//Prompt player 1 and 2 for names and symbols
		System.out.print("Please enter Player 1's name: ");
		p1Name = scan.next();
		System.out.print("Please enter Player 2's name: ");
		p2Name = scan.next();
		System.out.print("Please enter Player 1's symbol as a character: ");
		p1Symbol = scan.next().charAt(0);
		System.out.print("Please enter Player 2's symbol as a character: ");
		p2Symbol = scan.next().charAt(0);
		
		//Initialize players
		Player p1 = new Player(p1Name,p1Symbol);
		Player p2 = new Player(p2Name,p2Symbol);
		
		//Create board
		Board board = new Board();
		
		//Initialize game instance
		boolean continuePlay = true;
		while(continuePlay==true){
			boolean p1Turn = true;
			boolean gameFinished = false;
			while(gameFinished==false){
				Piece[][] state = board.getState();
				drawBoard(state);
				int colEntry = 0;
				if(p1Turn==true){
					System.out.print(p1+" enter a column number: ");
					colEntry=scan.nextInt();
				}else{
					System.out.print(p2+" enter a column number: ");
					colEntry=scan.nextInt();
				}
				//add entry to the board
				if(p1Turn==true){
					Piece piece = p1.addPiece();
					board.move(colEntry,piece);
				}else{
					Piece piece = p2.addPiece();
					board.move(colEntry,piece);
				}
				//check if the board has a winning state.
				if (board.checkForWin()==true){
					gameFinished=true;
				}else{
					board.dropColumn(colEntry);
					p1Turn = !p1Turn;
				}
			}
			if (p1Turn==true){
				System.out.println(p1+" won!");
			}else{
				System.out.println(p2+" won!");
			}
			//Prompt user to play again
			System.out.print("Play again? (y/n): ");
			String again = scan.next().trim();
			if(again.compareTo("y")!=0){
				continuePlay=false;
			}
			board.reset();
		}
	}
}
