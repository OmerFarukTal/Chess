package Pieces;

import java.util.ArrayList;

import Board.Board;

public class Knight extends Piece{
	
	public Knight(Board board, int xLoc, int yLoc, String piece) {
		super(board, xLoc, yLoc, piece);
	}
	
	public int[][] initializeDirection() {
		int[][] direction = new int[8][2];
		direction[0][0] =  2; direction[0][1] =  1; 
		direction[1][0] =  2; direction[1][1] = -1; 
		direction[2][0] = -2; direction[2][1] =  1; 
		direction[3][0] = -2; direction[3][1] = -1; 
		direction[4][0] =  1; direction[4][1] =  2; 
		direction[5][0] =  1; direction[5][1] = -2; 
		direction[6][0] = -1; direction[6][1] =  2; 
		direction[7][0] = -1; direction[7][1] = -2; 
		return direction;
	}
	
	public void possibleMoves(ArrayList<int[]> possibleMoves) {
		possibleMoves.clear();
		Piece[][] board = this.board.getPieceBoard();
		
		int[][] direction = initializeDirection();
		
		for (int i = 0; i < 8; i++) {
			if (xLoc + direction[i][0] >= 0 && xLoc + direction[i][0] <= 7 &&
					yLoc + direction[i][1] >= 0 && yLoc + direction[i][1] <= 7 ) {
				
				if (board[xLoc + direction[i][0]][yLoc + direction[i][1]] == null) {
					int[] curMove = new int[2];
					curMove[0] = xLoc + direction[i][0]; curMove[1] =  yLoc + direction[i][1];
					possibleMoves.add(curMove);
				}
				else if (board[xLoc + direction[i][0]][yLoc + direction[i][1]].getPieceString().charAt(0) != this.pieceString.charAt(0) ) {
					int[] curMove = new int[2];
					curMove[0] = xLoc + direction[i][0]; curMove[1] =  yLoc + direction[i][1];
					possibleMoves.add(curMove);
				}
				else {
					continue;
				}
			}
		}
		
	}
	
	public Knight clone() {
		Knight theReturned = new Knight(board, xLoc, yLoc, pieceString);
		return theReturned;
	}
	
	
}
