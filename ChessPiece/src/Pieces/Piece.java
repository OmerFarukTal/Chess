package Pieces;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Board.Board;

public class Piece {
	protected Board board;
	protected int xLoc;
	protected int yLoc;
	protected String pieceString;
	
	public Piece(Board board, int xLoc, int yLoc, String piece) {
		this.board = board;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		this.pieceString = piece;
	}
	
	
	
	public void possibleMoves(ArrayList<int[]> possibleMoves) {}
	
	/*
	 * Getter And Setter
	 */
	
	@Override
	public String toString() {
		return "Piece : " + pieceString + " at  : " + xLoc + " " +  yLoc;
	}
	
	public ArrayList<int[]> getPossibleMoves() {
		ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
		possibleMoves(possibleMoves);
		return possibleMoves;
	}

	public void setxLoc(int xLoc) {
		this.xLoc = xLoc;
	}

	public void setyLoc(int yLoc) {
		this.yLoc = yLoc;
	}
	
	public int getxLoc() {
		return xLoc;
	}

	public int getyLoc() {
		return yLoc;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
	
	public String getPieceString() {
		return this.pieceString;
	}
}
