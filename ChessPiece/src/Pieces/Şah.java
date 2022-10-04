package Pieces;

import java.util.ArrayList;

import Board.Board;

public class Şah extends Piece{
	
	private int[][] checkDirection;
	private boolean check1 = false;
	private boolean hasMoved = false;

	public Şah(Board board, int xLoc, int yLoc, String piece) {
		super(board, xLoc, yLoc, piece);
		checkDirection = new int[16][2];
		initalizeCheckDirection();
	}
	
	
	public boolean checkCheck() { // checkCheck(xLoc, yLoc)
		Piece[][] board = this.board.getPieceBoard();
		check1 = false;
		
		for (int j = 0; j < 8 ; j ++) {
			
			for (int i = 1; i < 8 ; i++) { // Taşa çarpınca durmuyor 
				if (xLoc + checkDirection[j][0]*i <= 7 && xLoc + checkDirection[j][0]*i >= 0 &&
						yLoc + checkDirection[j][1]*i <= 7 && yLoc + checkDirection[j][1]*i >= 0 ) {
					if (board[xLoc + checkDirection[j][0]*i][yLoc + checkDirection[j][1]*i] == null) {
						continue;
					}
					else if (board[xLoc + checkDirection[j][0]*i][yLoc + checkDirection[j][1]*i].getPieceString().charAt(0) != this.pieceString.charAt(0)) {
						if (j >= 0 && j<=3 ) { // Vertical 
							 if (board[xLoc + checkDirection[j][0]*i][yLoc + checkDirection[j][1]*i].getPieceString().charAt(1) == 'R' ||
									 board[xLoc + checkDirection[j][0]*i][yLoc + checkDirection[j][1]*i].getPieceString().charAt(1) == 'Q' ) {
								 check();
								 //System.out.println("Vertical := Rook or Queen " + " j := " + j + " i := " + i );
								 break;
							 }
							 if (board[xLoc + checkDirection[j][0]*i][yLoc + checkDirection[j][1]*i].getPieceString().charAt(1) == 'K' && i == 1) {
								 check();
								 //System.out.println("Vertical := King" + " j := " + j + " i := " + i );
								 break;
							 }
						}
						else { // Orthogonal
							 if (board[xLoc + checkDirection[j][0]*i][yLoc + checkDirection[j][1]*i].getPieceString().charAt(1) == 'B' ||
									 board[xLoc + checkDirection[j][0]*i][yLoc + checkDirection[j][1]*i].getPieceString().charAt(1) == 'Q') {
								 check();
								 //System.out.println("Orthogonal := Bishop or Queen"  + " j := " + j + " i := " + i );
								 break;
							 }
							 if (board[xLoc + checkDirection[j][0]*i][yLoc + checkDirection[j][1]*i].getPieceString().charAt(1) == 'K' && i == 1) {
								 check();
								 //System.out.println("Orthogonal := King"  + " j := " + j + " i := " + i );
								 break;
							 }
							 if (board[xLoc + checkDirection[j][0]*i][yLoc + checkDirection[j][1]*i].getPieceString().charAt(1) == 'P' && i == 1 ) {
								 if ((this.pieceString.charAt(0) == 'w' && xLoc >= xLoc + checkDirection[j][0]*i) || 
										 (this.pieceString.charAt(0) == 'b' && xLoc <= xLoc + checkDirection[j][0]*i) ) {
									 check();
									 //System.out.println("Orthogonal := Pawn"  + " j := " + j + " i := " + i );
									 break;
								 }
									 
								 
							 }
						}
						break;
					}
					else {
						break;
					}
				}
			}	
			
		}
		for (int i = 8; i < 16; i++) {
			if (xLoc + checkDirection[i][0] >= 0 && xLoc + checkDirection[i][0] <= 7 &&
					yLoc + checkDirection[i][1] >= 0 && yLoc + checkDirection[i][1] <= 7 ) {
				if (board[xLoc + checkDirection[i][0]][yLoc + checkDirection[i][1]] == null) {
					continue;
				}
				else if (board[xLoc + checkDirection[i][0]][yLoc + checkDirection[i][1]].getPieceString().charAt(0) != this.pieceString.charAt(0) ) {
					if (board[xLoc + checkDirection[i][0]][yLoc + checkDirection[i][1]].getPieceString().charAt(1) == 'N') {
						check();
						//System.out.println("Non orthodox := Knight" + " xLoc := " (xLoc + checkDirection));
					}
				}
				else {
					continue;
				}
			}
		}
		
		return check1;
	}
	
	private void check() {
		if (check1 == false ) {
			check1 = true;
		}
	}
	
	public void possibleMoves(ArrayList<int[]> possibleMoves) {
		possibleMoves.clear();
		Piece[][] board = this.board.getPieceBoard();
		
		int[][] direction = initializeDirection();
		
		for (int j = 0; j < 8 ; j ++) {
		
			if (xLoc + direction[j][0] <= 7 && xLoc + direction[j][0] >= 0 &&
					yLoc + direction[j][1] <= 7 && yLoc + direction[j][1] >= 0 ) {
				//System.out.println("King Pos := xLoc = " + (xLoc + direction[j][0]) + " yLoc = " + (yLoc + direction[j][1]) );
				if (board[xLoc + direction[j][0]][yLoc + direction[j][1]] == null && !checkCheckDirection(xLoc + direction[j][0],yLoc + direction[j][1])) { // && !checkCheck()
					int[] curMove = new int[2];
					curMove[0] = xLoc + direction[j][0]; curMove[1] =  yLoc + direction[j][1];
					possibleMoves.add(curMove);
				}
				else if (board[xLoc + direction[j][0]][yLoc + direction[j][1]] != null && board[xLoc + direction[j][0]][yLoc + direction[j][1]].getPieceString().charAt(0) != this.pieceString.charAt(0) && !checkCheckDirection(xLoc + direction[j][0],yLoc + direction[j][1])) { // && !checkCheck()
					int[] curMove = new int[2];
					curMove[0] = xLoc + direction[j][0]; curMove[1] =  yLoc + direction[j][1];
					possibleMoves.add(curMove);
				}
				else {
					continue;
				}
			}
					
				
		}
	}
	
	public void initalizeCheckDirection() {
		//Rook
		checkDirection[0][0] =  1; checkDirection[0][1] =  0; 
		checkDirection[1][0] = -1; checkDirection[1][1] =  0; 
		checkDirection[2][0] =  0; checkDirection[2][1] =  1; 
		checkDirection[3][0] =  0; checkDirection[3][1] = -1; 
		//Bishop
		checkDirection[4][0] =  1; checkDirection[4][1] =  1; 
		checkDirection[5][0] =  1; checkDirection[5][1] = -1; 
		checkDirection[6][0] = -1; checkDirection[6][1] =  1; 
		checkDirection[7][0] = -1; checkDirection[7][1] = -1;   
		//Knight
		checkDirection[8][0]  =  2; checkDirection[8][1]  =  1; 
		checkDirection[9][0]  =  2; checkDirection[9][1]  = -1; 
		checkDirection[10][0] = -2; checkDirection[10][1] =  1; 
		checkDirection[11][0] = -2; checkDirection[11][1] = -1; 
		checkDirection[12][0] =  1; checkDirection[12][1] =  2; 
		checkDirection[13][0] =  1; checkDirection[13][1] = -2; 
		checkDirection[14][0] = -1; checkDirection[14][1] =  2; 
		checkDirection[15][0] = -1; checkDirection[15][1] = -2; 
	}
	
	public int[][] initializeDirection() {
		int[][] direction = new int[8][2];
		//Rook
		direction[0][0] =  1; direction[0][1] =  0; 
		direction[1][0] = -1; direction[1][1] =  0; 
		direction[2][0] =  0; direction[2][1] =  1; 
		direction[3][0] =  0; direction[3][1] = -1;
		//Bishop
		direction[4][0] =  1; direction[4][1] =  1; // 1 e 1
		direction[5][0] =  1; direction[5][1] = -1; 
		direction[6][0] = -1; direction[6][1] =  1; 
		direction[7][0] = -1; direction[7][1] = -1; 
		return direction;
	}
	
	public boolean checkCheckDirection(int xLoc, int yLoc) { // checkCheck(xLoc, yLoc)
		Piece[][] board = this.board.getPieceBoard();
		
		for (int j = 0; j < 8 ; j ++) {
			
			for (int i = 1; i < 8 ; i++) { // Taşa çarpınca durmuyor 
				if (xLoc + checkDirection[j][0]*i <= 7 && xLoc + checkDirection[j][0]*i >= 0 &&
						yLoc + checkDirection[j][1]*i <= 7 && yLoc + checkDirection[j][1]*i >= 0 ) {
					if (board[xLoc + checkDirection[j][0]*i][yLoc + checkDirection[j][1]*i] == null) {
						continue;
					}
					else if (board[xLoc + checkDirection[j][0]*i][yLoc + checkDirection[j][1]*i].getPieceString().charAt(0) != this.pieceString.charAt(0)) {
						if (j >= 0 && j<=3 ) { // Vertical 
							 if (board[xLoc + checkDirection[j][0]*i][yLoc + checkDirection[j][1]*i].getPieceString().charAt(1) == 'R' ||
									 board[xLoc + checkDirection[j][0]*i][yLoc + checkDirection[j][1]*i].getPieceString().charAt(1) == 'Q' ) {
								 //System.out.println("Vertical := Rook or Queen" + " xLoc := " + (xLoc +  checkDirection[i][0]*i ) + " yLoc := " + (yLoc + checkDirection[i][1]*i));
								 return true;								 
							 }
							 if (board[xLoc + checkDirection[j][0]*i][yLoc + checkDirection[j][1]*i].getPieceString().charAt(1) == 'K' && i == 1) {
								 //System.out.println("Vertical := King" + " xLoc := " + (xLoc +  checkDirection[i][0]*i ) + " yLoc := " + (yLoc + checkDirection[i][1]*i));
								 return true;
							 }
						}
						else { // Orthogonal
							 if (board[xLoc + checkDirection[j][0]*i][yLoc + checkDirection[j][1]*i].getPieceString().charAt(1) == 'B' ||
									 board[xLoc + checkDirection[j][0]*i][yLoc + checkDirection[j][1]*i].getPieceString().charAt(1) == 'Q') {
								 int x = xLoc + checkDirection[j][0]*i ;
									int y = yLoc + checkDirection[j][1]*i;
								 //System.out.println("Orthogonal := Bishop or Queen"  + " xLoc := " + x + " yLoc := " + y );
								 return true;
							 }
							 if (board[xLoc + checkDirection[j][0]*i][yLoc + checkDirection[j][1]*i].getPieceString().charAt(1) == 'K' && i == 1) {
								 //System.out.println("Orthogonal := King"  + " xLoc := " + (xLoc +  checkDirection[i][0]*i ) + " yLoc := " + (yLoc + checkDirection[i][1]*i));
								 return true;
							 }
							 if (board[xLoc + checkDirection[j][0]*i][yLoc + checkDirection[j][1]*i].getPieceString().charAt(1) == 'P' && i == 1 ) {
								 if ((this.pieceString.charAt(0) == 'w' && xLoc >= xLoc + checkDirection[j][0]*i) || 
										 (this.pieceString.charAt(0) == 'b' && xLoc <= xLoc + checkDirection[j][0]*i) ) {
									 //System.out.println("Orthogonal := Pawn"  + " xLoc := " + (xLoc +  checkDirection[i][0]*i ) + " yLoc := " + (yLoc + checkDirection[i][1]*i));
									 return true;
								 }
									 
								 
							 }
						}
						break;
					}
					else {
						if (board[xLoc + checkDirection[j][0]*i][yLoc + checkDirection[j][1]*i].getPieceString().equals(this.pieceString) && i == 1) {
							continue;
						}
						break;
					}
				}
			}	
			
		}
		for (int i = 8; i < 16; i++) {
			if (xLoc + checkDirection[i][0] >= 0 && xLoc + checkDirection[i][0] <= 7 &&
					yLoc + checkDirection[i][1] >= 0 && yLoc + checkDirection[i][1] <= 7 ) {
				if (board[xLoc + checkDirection[i][0]][yLoc + checkDirection[i][1]] == null) {
					continue;
				}
				else if (board[xLoc + checkDirection[i][0]][yLoc + checkDirection[i][1]].getPieceString().charAt(0) != this.pieceString.charAt(0) ) {
					if (board[xLoc + checkDirection[i][0]][yLoc + checkDirection[i][1]].getPieceString().charAt(1) == 'N') {
						//System.out.println("Non orthodox := Knight" + " xLoc := " + (xLoc +  checkDirection[i][0]*i ) + " yLoc := " + (yLoc + checkDirection[i][1]*i));
						return true;
					}
				}
				else {
					continue;
				}
			}
		}
		
		return false;
	}
	
	
	/*
	 * Getter And Setter
	 */
	
	
	public Şah clone() {
		Şah theReturned = new Şah(board, xLoc, yLoc, pieceString);
		theReturned.checkDirection = checkDirection;
		theReturned.check1 = check1;
		return theReturned;
	}
	
	public boolean getHasMoved() {
		return hasMoved;
	}

	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	
}
