package Board;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import Pieces.*;


public class Board {
	
	private Piece[][] pieceBoard = new Piece[8][8];
	
	public Board() {
		initializeBoard();
	}
	
	public void initializeBoard() {
		
		// Piece Board
		for (int i = 0; i < 8; i++) {
			pieceBoard[6][i] = new Pawn(this, 6, i, "wP");
			pieceBoard[1][i] = new Pawn(this, 1, i, "bP");
		}
		
		pieceBoard[0][0] = new Rook(this, 0, 0, "bR"); pieceBoard[0][7] = new Rook(this, 0, 7, "bR"); 
		pieceBoard[0][1] = new Knight(this, 0, 1, "bN"); pieceBoard[0][6] = new Knight(this, 0, 6, "bN"); 
		pieceBoard[0][2] = new Bishop(this, 0, 2, "bB"); pieceBoard[0][5] = new Bishop(this, 0, 5, "bB"); 
		pieceBoard[0][3] = new Queen(this, 0, 3, "bQ"); pieceBoard[0][4] = new Şah(this, 0, 4, "bK"); 
		
		pieceBoard[7][0] = new Rook(this, 7, 0, "wR"); pieceBoard[7][7] = new Rook(this, 7, 7, "wR"); 
		pieceBoard[7][1] = new Knight(this, 7, 1, "wN"); pieceBoard[7][6] = new Knight(this, 7, 6, "wN"); 
		pieceBoard[7][2] = new Bishop(this, 7, 2, "wB"); pieceBoard[7][5] = new Bishop(this, 7, 5, "wB"); 
		pieceBoard[7][3] = new Queen(this, 7, 3, "wQ"); pieceBoard[7][4] = new Şah(this, 7, 4, "wK"); 
	}
	
	/*
	 * Getter And Setter
	 */
	
	/*
	@Override
	public String toString() {
		String theReturned = "";
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8 ; j++) {
				if (stringBoard[i][j] != null) {
					theReturned += " " + stringBoard[i][j] + " ";
				}
				else {
					theReturned += " " + "--" + " ";
				}
			}
			theReturned += "\n";
		}
		return theReturned;
	}
	*/
	
	public ArrayList<int[]> getWhiteCastleMoves() {
		ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
		// Long Castle
		if (pieceBoard[7][4] != null && pieceBoard[7][4] instanceof Şah &&
				pieceBoard[7][0] != null && pieceBoard[7][0] instanceof Rook &&
				!((Rook) pieceBoard[7][0]).getHasMoved() && !((Şah) pieceBoard[7][4]).getHasMoved() &&
				!((Şah) pieceBoard[7][4]).checkCheckDirection(7, 2) && !((Şah) pieceBoard[7][4]).checkCheckDirection(7, 3) && !((Şah) pieceBoard[7][4]).checkCheckDirection(7, 4) &&
				pieceBoard[7][3] == null && pieceBoard[7][2] == null && pieceBoard[7][1] == null) {
			int[] move = new int[2];
			move[0] = 7; move[1] = 2;
			possibleMoves.add(move);
		}
		// Short Castle
		if (pieceBoard[7][4] != null && pieceBoard[7][4] instanceof Şah &&
				pieceBoard[7][7] != null && pieceBoard[7][7] instanceof Rook &&
				!((Rook) pieceBoard[7][7]).getHasMoved() && !((Şah) pieceBoard[7][4]).getHasMoved() &&
				!((Şah) pieceBoard[7][4]).checkCheckDirection(7, 5) && !((Şah) pieceBoard[7][4]).checkCheckDirection(7, 6) && !((Şah) pieceBoard[7][4]).checkCheckDirection(7, 4) &&
				pieceBoard[7][5] == null && pieceBoard[7][6] == null) {
			int[] move = new int[2];
			move[0] = 7; move[1] = 6;
			possibleMoves.add(move);
		}	
		return possibleMoves;
	}
	
	public ArrayList<int[]> getBlackCastleMoves() {
		ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
		// Long Castle
		if (pieceBoard[0][4] != null && pieceBoard[0][4] instanceof Şah &&
				pieceBoard[0][0] != null && pieceBoard[0][0] instanceof Rook &&
				!((Rook) pieceBoard[0][0]).getHasMoved() && !((Şah) pieceBoard[0][4]).getHasMoved() &&
				!((Şah) pieceBoard[0][4]).checkCheckDirection(0, 2) && !((Şah) pieceBoard[0][4]).checkCheckDirection(0, 3) && !((Şah) pieceBoard[0][4]).checkCheckDirection(0, 4) &&
				pieceBoard[0][3] == null && pieceBoard[0][2] == null && pieceBoard[0][1] == null) {
			int[] move = new int[2];
			move[0] = 0; move[1] = 2;
			possibleMoves.add(move);
		}
		// Short Castle
		if (pieceBoard[0][4] != null && pieceBoard[0][4] instanceof Şah &&
				pieceBoard[0][7] != null && pieceBoard[0][7] instanceof Rook &&
				!((Rook) pieceBoard[0][7]).getHasMoved() && !((Şah) pieceBoard[0][4]).getHasMoved() &&
				!((Şah) pieceBoard[0][4]).checkCheckDirection(0, 5) && !((Şah) pieceBoard[0][4]).checkCheckDirection(0, 6) && !((Şah) pieceBoard[0][4]).checkCheckDirection(0, 4) &&
				pieceBoard[0][5] == null && pieceBoard[0][6] == null) {
			int[] move = new int[2];
			move[0] = 0; move[1] = 6;
			possibleMoves.add(move);
		}	
		return possibleMoves;
	}
	
	public Piece[][] getPieceBoard() {
		return this.pieceBoard;
	}


	public void setPieceBoard(Piece[][] pieceBoard) {
		this.pieceBoard = pieceBoard;
	}
	
	
	public void makeWhitePawnDoubleMoveFalse() {
		for (int i = 0; i < 8; i++ ) {
			for (int j = 0; j < 8; j++ ) {
				if (pieceBoard[i][j] != null && pieceBoard[i][j].getPieceString().equals("wP")) {
					((Pawn) pieceBoard[i][j]).setDoubleMove(false);
				}
			}
		}
	}
	
	public void makeBlackPawnDoubleMoveFalse() {
		for (int i = 0; i < 8; i++ ) {
			for (int j = 0; j < 8; j++ ) {
				if (pieceBoard[i][j] != null && pieceBoard[i][j].getPieceString().equals("bP")) {
					((Pawn) pieceBoard[i][j]).setDoubleMove(false);
				}
			}
		}
	}
	
	public double calculateBoardPoint() {
		double[][] wKingMid = {{ -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
							   { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
							   { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
							   { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
							   { -2.0, -3.0, -3.0, -4.0, -4.0, -3.0, -3.0, -2.0},
							   { -1.0, -2.0, -2.0, -2.0, -2.0, -2.0, -2.0, -1.0},
							   {  2.0,  2.0,  0.0,  0.0,  0.0,  0.0,  2.0,  2.0},
							   {  2.0,  3.0,  1.0,  0.0,  0.0,  1.0,  3.0,  2.0}};
		
		double[][] wKingEnd = {{ -5.0, -4.0, -3.0, -2.0, -2.0, -3.0, -4.0, -5.0},
							   { -3.0, -1.0,  2.0,  3.0,  3.0,  2.0, -1.0, -3.0},
							   { -3.0, -1.0,  3.0,  4.0,  4.0,  3.0, -1.0, -3.0},
							   { -3.0, -1.0,  3.0,  4.0,  4.0,  3.0, -1.0, -3.0},
							   { -3.0, -1.0,  2.0,  3.0,  3.0,  2.0, -1.0, -3.0},
							   { -3.0 ,-3.0,  0.0,  0.0,  0.0,  0.0  -3.0, -3.0},
							   { -5.0, -3.0, -3.0, -3.0, -3.0, -3.0, -3.0, -5.0}};
		
		double[][] wQueen =   {{ -2.0, -1.0, -1.0, -5.0, -5.0, -1.0, -1.0, -2.0},
							   { -1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0},
							   { -1.0,  0.0,  5.0,  5.0,  5.0,  5.0,  0.0, -1.0},
							   { -5.0,  0.0,  5.0,  5.0,  5.0,  5.0,  0.0, -5.0},
							   {  0.0,  0.0,  5.0,  5.0,  5.0,  5.0,  0.0, -5.0},
							   { -1.0,  5.0,  5.0,  5.0,  5.0,  5.0,  0.0, -1.0},
							   { -1.0,  0.0,  5.0,  0.0,  0.0,  0.0,  0.0, -1.0},
							   { -2.0, -1.0, -1.0, -5.0, -5.0, -1.0, -1.0, -2.0}};
		
		double[][] wRook =    {{  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0},
							   {  5.0,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  5.0},
							   { -5.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -5.0},
							   { -5.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -5.0},
							   { -5.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -5.0},
							   { -5.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -5.0},
							   { -5.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -5.0},
							   {  0.0,  0.0,  0.0,  5.0,  5.0,  0.0,  0.0,  0.0}};
		
		double[][] wBishop =  {{ -2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0},
						       { -1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0},
						       { -1.0,  0.0,  5.0,  1.0,  1.0,  5.0,  0.0, -1.0},
							   { -1.0,  5.0,  5.0,  1.0,  1.0,  5.0,  5.0, -1.0},
							   { -1.0,  0.0,  1.0,  1.0,  1.0,  1.0,  0.0, -1.0},
							   { -1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0, -1.0},
							   { -1.0,  5.0,  0.0,  0.0,  0.0,  0.0,  5.0, -1.0},
							   { -2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0}};
		
		double[][] wKnight =  {{ -5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0},
							   { -4.0, -2.0,  0.0,  0.0,  0.0,  0.0, -2.0, -4.0},					       
							   { -3.0,  0.0,  1.0,  1.5,  1.5,  1.0,  0.0, -3.0},
							   { -3.0,  5.0,  1.5,  2.0,  2.0,  1.5,  5.0, -3.0},
							   { -3.0,  0.0,  1.5,  2.0,  2.0,  1.5,  0.0, -3.0},
							   { -3.0,  5.0,  1.0,  1.5,  1.5,  1.0,  5.0, -3.0},
							   { -4.0, -2.0,  0.0,  5.0,  5.0,  0.0, -2.0, -4.0},
						       { -5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0}};
		
		double[][] wPawn =    {{  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0},
							   {  5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0},
							   {  1.0,  1.0,  2.0,  3.0,  3.0,  2.0,  1.0,  1.0},
							   {  5.0,  5.0,  1.0,  2.5,  2.5,  1.0,  5.0,  5.0},
							   {  0.0,  0.0,  0.0,  2.0,  2.0,  0.0,  0.0,  0.0},
							   {  5.0, -5.0, -1.0,  0.0,  0.0, -1.0, -5.0,  5.0},
							   {  5.0,  1.0,  1.0, -2.0, -2.0,  1.0,  1.0,  5.0},
							   {  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0}};
		
		double boardPoint = 0.0;
		
		/*
		int pieceNumber = 0;
		for (int i = 0; i < 8; i++ ) {
			for (int j = 0; j < 8; j++ ) {
				if (pieceBoard[i][j] != null ) {
					pieceNumber += 1;
				}
			}
		}
		if (pieceNumber < 8 ) {
			wKingMid = wKingEnd;
		}*/
		
		for (int i = 0; i < 8; i++ ) {
			for (int j = 0; j < 8; j++ ) {
				if (pieceBoard[i][j] != null) {
					if (pieceBoard[i][j] instanceof Pawn) {
						if (pieceBoard[i][j].getPieceString().charAt(0) == 'w') {
							boardPoint += 1 + wPawn[i][j]*0.05;
						}
						else {
							boardPoint += -1 - wPawn[7-i][7-j]*0.05;
						}
					}
					else if (pieceBoard[i][j] instanceof Rook) {
						if (pieceBoard[i][j].getPieceString().charAt(0) == 'w') {
							boardPoint += 5 + wRook[i][j]*0.05;
						}
						else {
							boardPoint += -5 - wRook[7-i][7-j]*0.05;
						}
					}
					else if (pieceBoard[i][j] instanceof Knight) {
						if (pieceBoard[i][j].getPieceString().charAt(0) == 'w') {
							boardPoint += 3 + wKnight[i][j]*0.05;
						}
						else {
							boardPoint += -3 - wKnight[7-i][7-j]*0.05;
						}
					}
					else if (pieceBoard[i][j] instanceof Bishop) {
						if (pieceBoard[i][j].getPieceString().charAt(0) == 'w') {
							boardPoint += 3 + wBishop[i][j]*0.05;
						}
						else {
							boardPoint += -3 - wBishop[7-i][7-j]*0.05;
						}					
					}
					else if (pieceBoard[i][j] instanceof Queen) {
						if (pieceBoard[i][j].getPieceString().charAt(0) == 'w') {
							boardPoint += 9 + wQueen[i][j]*0.05;
						}
						else {
							boardPoint += -9 - wQueen[7-i][7-j]*0.05;
						}					
					}
					else if (pieceBoard[i][j] instanceof Şah) {
						if (pieceBoard[i][j].getPieceString().charAt(0) == 'w') {
							boardPoint += 900 + wKingMid[i][j]*0.05;
						}
						else {
							boardPoint += -900 - wKingMid[7-i][7-j]*0.05;
						}					
					}
				}
			}
		}
		
		return boardPoint;
		
	}
	
	public boolean gameOver() {
		boolean whiteKingThere = false;
		boolean blackKingThere = false;
		for (int i = 0; i<8; i++) {
			for (int j = 0; j<8; j++) {
				if (pieceBoard[i][j] != null && pieceBoard[i][j] instanceof Şah) {
					if (pieceBoard[i][j].getPieceString().equals("wK")) {
						whiteKingThere = true;
					}
					else {
						blackKingThere = true;
					}
				}
			}
		}
		
		return !(whiteKingThere && blackKingThere);
	}
	
	public boolean whiteWon() {
		boolean whiteKingThere = false;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j< 8; j++) {
				if (pieceBoard[i][j] != null && pieceBoard[i][j] instanceof Şah) {
					if (pieceBoard[i][j].getPieceString().equals("wK")) {
						whiteKingThere = true;
					}
					
				}
			}
		}
		return whiteKingThere;
	}
	
	public boolean blackWon() {
		boolean blackKingThere = false;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j< 8; j++) {
				if (pieceBoard[i][j] != null && pieceBoard[i][j] instanceof Şah) {
					if (pieceBoard[i][j].getPieceString().equals("bK")) {
						blackKingThere = true;
					}
					
				}
			}
		}
		return !blackKingThere;
	}
}
