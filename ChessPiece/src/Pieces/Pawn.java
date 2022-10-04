package Pieces;

import java.util.ArrayList;

import Board.Board;

public class Pawn extends Piece{

	private boolean doubleMove = false;
	
	public Pawn(Board board, int xLoc, int yLoc, String piece) {
		super(board, xLoc, yLoc, piece);
	}
	
	
	public int[][] initializeDirection() {
		int[][] direction= new int[4][2];
		if (this.pieceString.charAt(0) == 'w') {
			direction[0][0] = -1; direction[0][1] =  0; 
			direction[1][0] = -2; direction[1][1] =  0; 
			direction[2][0] = -1; direction[2][1] = -1; 
			direction[3][0] = -1; direction[3][1] =  1;
		}
		else {
			direction[0][0] =  1; direction[0][1] =  0; 
			direction[1][0] =  2; direction[1][1] =  0; 
			direction[2][0] =  1; direction[2][1] = -1; 
			direction[3][0] =  1; direction[3][1] =  1;
		}
		return direction;
	}
	
	public void possibleMoves(ArrayList<int[]> possibleMoves) { // En passant must be implemented with the PieceBoard
		possibleMoves.clear();
		Piece[][] board = this.board.getPieceBoard();
		
		int[][] direction = initializeDirection();
		
		// 1 forward
		if (xLoc + direction[0][0] <= 7 && xLoc + direction[0][0] >= 0 &&
				yLoc + direction[0][1] <= 7 && yLoc + direction[0][1] >= 0 ) { 
			if (board[xLoc + direction[0][0]][yLoc + direction[0][1]] == null) {
				int[] curMove = new int[2];
				curMove[0] = xLoc + direction[0][0]; curMove[1] =  yLoc + direction[0][1];
				possibleMoves.add(curMove);
			}
		}
		// Two forward
		if ((this.pieceString.charAt(0) == 'w' && xLoc == 6) || (this.pieceString.charAt(0) == 'b' && xLoc == 1) ) { 
			if (board[xLoc + direction[0][0]][yLoc + direction[0][1]] == null &&
					board[xLoc + direction[1][0]][yLoc + direction[1][1]] == null) {
				int[] curMove = new int[2];
				curMove[0] = xLoc + direction[1][0]; curMove[1] =  yLoc + direction[1][1];
				possibleMoves.add(curMove);
			}
		}
		// Takes 1 direction
		if (xLoc + direction[2][0] <= 7 && xLoc + direction[2][0] >= 0 &&
				yLoc + direction[2][1] <= 7 && yLoc + direction[2][1] >= 0 ) { 
			if (board[xLoc + direction[2][0]][yLoc + direction[2][1]] != null && board[xLoc + direction[2][0]][yLoc + direction[2][1]].getPieceString().charAt(0) != this.pieceString.charAt(0)) {
				int[] curMove = new int[2];
				curMove[0] = xLoc + direction[2][0]; curMove[1] =  yLoc + direction[2][1];
				possibleMoves.add(curMove);
			}
		}
		// Takes other direction
		if (xLoc + direction[3][0] <= 7 && xLoc + direction[3][0] >= 0 &&
				yLoc + direction[3][1] <= 7 && yLoc + direction[3][1] >= 0 ) { 
			if (board[xLoc + direction[3][0]][yLoc + direction[3][1]] != null && board[xLoc + direction[3][0]][yLoc + direction[3][1]].getPieceString().charAt(0) != this.pieceString.charAt(0)) {
				int[] curMove = new int[2];
				curMove[0] = xLoc + direction[3][0]; curMove[1] =  yLoc + direction[3][1];
				possibleMoves.add(curMove);
			}
		}
		// En passant 1 direction IS NOT WORKING WROPERLY after 1 move it just be adjusted
		if (xLoc + direction[2][0] <= 7 && xLoc + direction[2][0] >= 0 &&
				yLoc + direction[2][1] <= 7 && yLoc + direction[2][1] >= 0 ) {
			if (board[xLoc][yLoc - 1] != null && board[xLoc][yLoc - 1].getPieceString().charAt(0) != this.pieceString.charAt(0) 
				&& board[xLoc][yLoc - 1].getPieceString().charAt(1) == 'P' && this.board.getPieceBoard()[xLoc][yLoc - 1] instanceof Pawn && ((Pawn) this.board.getPieceBoard()[xLoc][yLoc - 1]).getDoubleMove() == true) {
				int[] curMove = new int[2];
				curMove[0] = xLoc + direction[2][0]; curMove[1] =  yLoc + direction[2][1];
				possibleMoves.add(curMove);
			}
		}
		// En Passant other direction IS NOT WORKING WROPERLY // just 4 and 5 cloumn
		if (xLoc + direction[3][0] <= 7 && xLoc + direction[3][0] >= 0 &&
				yLoc + direction[3][1] <= 7 && yLoc + direction[3][1] >= 0 ) {
			if (board[xLoc][yLoc + 1] != null && board[xLoc][yLoc + 1].getPieceString().charAt(0) != this.pieceString.charAt(0) 
				&& board[xLoc][yLoc + 1].getPieceString().charAt(1) == 'P' && this.board.getPieceBoard()[xLoc][yLoc + 1] instanceof Pawn && ((Pawn) this.board.getPieceBoard()[xLoc][yLoc + 1]).getDoubleMove() == true) {
				int[] curMove = new int[2];
				curMove[0] = xLoc + direction[3][0]; curMove[1] =  yLoc + direction[3][1];
				possibleMoves.add(curMove);
			}
		}
	}

	/*
	 * Getter And Setter
	 */

	public Pawn clone() {
		Pawn theReturned = new Pawn(board, xLoc, yLoc, pieceString);
		theReturned.setDoubleMove(doubleMove);
		return theReturned;
	}
	
	public boolean getDoubleMove() {
		return doubleMove;
	}

	public void setDoubleMove(boolean doubleMove) {
		this.doubleMove = doubleMove;
	}
	
}
