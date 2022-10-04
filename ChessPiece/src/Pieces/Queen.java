package Pieces;

import java.util.ArrayList;

import Board.Board;

public class Queen extends Piece{

	public Queen(Board board, int xLoc, int yLoc, String piece) {
		super(board, xLoc, yLoc, piece);
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
	
	public void possibleMoves(ArrayList<int[]> possibleMoves) {
		possibleMoves.clear();
		Piece[][] board = this.board.getPieceBoard();
		
		int[][] direction = initializeDirection();
		
		for (int j = 0; j < 8 ; j ++) {
		
				for (int i = 1; i < 8 ; i++) {
					if (xLoc + direction[j][0]*i <= 7 && xLoc + direction[j][0]*i >= 0 &&
							yLoc + direction[j][1]*i <= 7 && yLoc + direction[j][1]*i >= 0 ) {
						if (board[xLoc + direction[j][0]*i][yLoc + direction[j][1]*i] == null) {
							int[] curMove = new int[2];
							curMove[0] = xLoc + direction[j][0]*i; curMove[1] =  yLoc + direction[j][1]*i;
							possibleMoves.add(curMove);
						}
						else if (board[xLoc + direction[j][0]*i][yLoc + direction[j][1]*i].getPieceString().charAt(0) != this.pieceString.charAt(0)) {
							int[] curMove = new int[2];
							curMove[0] = xLoc + direction[j][0]*i; curMove[1] =  yLoc + direction[j][1]*i;
							possibleMoves.add(curMove);
							break;
						}
						else {
							break;
						}
					}
				}	
				
		}
	}
	
	public Queen clone() {
		Queen theReturned = new Queen(board, xLoc, yLoc, pieceString);
		return theReturned;
	}

}
