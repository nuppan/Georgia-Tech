
public class Board implements BoardInterface, WinCondition{
	
	public static final int ROW = 8;
	public static final int COL = 8;
	private Piece[][] board;
	
	public Board(){
		board = new Piece[COL][ROW];
	}

	@Override
	public boolean checkHorizontal(int row, int col) {
		if (board[col][row]==null){
			return false;
		}
		String currSymbol=board[col][row].toString();
		for(int c=col;c<=(col+3);c++){
			if (board[c][row]==null || board[c][row].toString().compareTo(currSymbol)!=0){
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean checkVertical(int row, int col) {
		if (board[col][row]==null){
			return false;
		}
		String currSymbol=board[col][row].toString();
		for(int r=row;r<=(row+3);r++){
			if (board[col][r]==null || board[col][r].toString().compareTo(currSymbol)!=0){
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean checkDiagonalRight(int row, int col) {
		if (board[col][row]==null){
			return false;
		}
		String currSymbol=board[col][row].toString();
		for(int i=0;i<4;i++){
			if (board[col][row]==null || board[col][row].toString().compareTo(currSymbol)!=0){
				return false;
			}
			row++;
			col++;
		}
		return true;
	}

	@Override
	public boolean checkDiagonalLeft(int row, int col) {
		if (board[col][row]==null){
			return false;
		}
		String currSymbol=board[col][row].toString();
		for(int i=0;i<4;i++){
			if (board[col][row]==null || board[col][row].toString().compareTo(currSymbol)!=0){
				return false;
			}
			row++;
			col--;
		}
		return true;
	}

	@Override
	public boolean checkForWin() {
		//Check horizontal
		for(int row=0;row<ROW;row++){
			for(int col=0;col<=COL-4;col++){
				if (checkHorizontal(row,col)==true){
					return true;
				}
			}
		}
		
		//Check vertical
		for(int col=0;col<COL;col++){
			for(int row=0;row<=ROW-4;row++){
				if (checkVertical(row,col)==true){
					return true;
				}
			}
		}
		
		//Check diagonal right
		for(int row=0;row<ROW-4;row++){
			for(int col=0;col<COL-4;col++){
				if (checkDiagonalRight(row,col)==true){
					return true;
				}
			}
		}
		
		//Check diagonal left
		for(int row=0;row<ROW-4;row++){
			for(int col=COL-1;col>=3;col--){
				if (checkDiagonalLeft(row,col)==true){
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean move(int col, Piece playerPiece) {
		for (int row=0;row<ROW;row++){
			if (board[col][row]==null){
				board[col][row] = playerPiece;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean dropColumn(int col) {
		boolean drop = false;
		System.out.println(board[col].length);
		boolean full = true;
		for(int r=0;r<ROW;r++){
			if (board[col][r]==null){
				full=false;
			}
		}
		if (full==true){
			for (int i=0;i<ROW;i++){
				board[col][i]=null;
			}
			drop = true;
		}
		return drop;
	}

	@Override
	public Piece[][] getState() {
		Piece[][] state = board;
		return state;
	}

	@Override
	public void reset() {
		board = new Piece[COL][ROW];
	}

}
