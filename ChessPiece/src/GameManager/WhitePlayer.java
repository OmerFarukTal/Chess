package GameManager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import Board.Board;
import Pieces.Bishop;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;
import Pieces.Şah;

public class WhitePlayer {
	
	private Board board;
	private Şah whiteKing;
	
	public WhitePlayer(Board board) {
		this.board = board;
		//putImages(this.board);
	}
	
	public void checkMove(Piece piece, ArrayList<int[]> moveSet, int currentX, int currentY) {
		
		if (!piece.getPieceString().equals("wK")) {
			for (int i = moveSet.size() -1; i>= 0; i--) {
				Board newBoard = new Board();
				newBoard.setPieceBoard(copyPieceBoard());
				adjustPieces(newBoard);
				
				
				Piece pieceN = newBoard.getPieceBoard()[currentX][currentY];
				newBoard.getPieceBoard()[currentX][currentY] = null;
				
				int xLoc = moveSet.get(i)[0];
				int yLoc = moveSet.get(i)[1];
				
				newBoard.getPieceBoard()[xLoc][yLoc] = pieceN;
				
				whiteKing.checkCheck();
				if (whiteKing.checkCheck()) {
					moveSet.remove(i);
				}
			}
		}
		
	}
	
	
	private void adjustPieces(Board newBoard) {
		for (int i = 0; i< 8 ; i++) {
			for (int j = 0; j< 8 ; j++) {
				if (newBoard.getPieceBoard()[i][j] != null) {
					newBoard.getPieceBoard()[i][j].setBoard(newBoard);
				}
			}
		}
	}
	
	
	private Piece[][] copyPieceBoard() {
		Piece[][] copyBoard = new Piece[8][8];
		for (int i = 0; i< 8 ; i++) {
			for (int j = 0; j< 8 ; j++) {
				 if (board.getPieceBoard()[i][j] != null) {
					 if (board.getPieceBoard()[i][j] instanceof Pawn) {
						 copyBoard[i][j] = ((Pawn) board.getPieceBoard()[i][j]).clone();
					 }
					 else if (board.getPieceBoard()[i][j] instanceof Rook) {
						 copyBoard[i][j] = ((Rook) board.getPieceBoard()[i][j]).clone();
					 }
					 else if (board.getPieceBoard()[i][j] instanceof Knight) {
						 copyBoard[i][j] = ((Knight) board.getPieceBoard()[i][j]).clone();
					 }
					 else if (board.getPieceBoard()[i][j] instanceof Bishop)  {
						 copyBoard[i][j] = ((Bishop) board.getPieceBoard()[i][j]).clone();
					 }
					 else if (board.getPieceBoard()[i][j] instanceof Queen) {
						 copyBoard[i][j] = ((Queen) board.getPieceBoard()[i][j]).clone();
					 }
					 else if (board.getPieceBoard()[i][j] instanceof Şah) {
						 Şah currKing = ((Şah) board.getPieceBoard()[i][j]).clone();
						 copyBoard[i][j] = currKing;
						 if (currKing.getPieceString().equals("wK")) {
							 whiteKing = currKing;
						 }
					 }
				 }
			}
		}
		return copyBoard;
	}
	/*
	public void putImages (Board board) {
		Piece[][] pieceBoard = board.getPieceBoard();
		BufferedImage curImage;
		// Pawn
		try {
			curImage = ImageIO.read(new FileImageInputStream(new File("Assets/wP.png")));
			for (int i = 0; i < 8; i++) {
				pieceBoard[6][i].setImage(curImage);
			}
		}
		catch(IOException ex) {
			Logger.getLogger(Board.class.getName()).log(Level.SEVERE,null,ex);
		}
		// Rook
		try {
			curImage = ImageIO.read(new FileImageInputStream(new File("Assets/wR.png")));
			pieceBoard[7][0].setImage(curImage);
			pieceBoard[7][7].setImage(curImage);
		}
		catch(IOException ex) {
			Logger.getLogger(Board.class.getName()).log(Level.SEVERE,null,ex);
		}
		// Knight
		try {
			curImage = ImageIO.read(new FileImageInputStream(new File("Assets/wN.png")));
			pieceBoard[7][1].setImage(curImage);
			pieceBoard[7][6].setImage(curImage);
		}
		catch(IOException ex) {
			Logger.getLogger(Board.class.getName()).log(Level.SEVERE,null,ex);
		}
		// Bishop
		try {
			curImage = ImageIO.read(new FileImageInputStream(new File("Assets/wB.png")));
			pieceBoard[7][2].setImage(curImage);
			pieceBoard[7][5].setImage(curImage);
		}
		catch(IOException ex) {
			Logger.getLogger(Board.class.getName()).log(Level.SEVERE,null,ex);
		}
		// Quuen and King
		try {
			curImage = ImageIO.read(new FileImageInputStream(new File("Assets/wQ.png")));
			pieceBoard[7][3].setImage(curImage);
			curImage = ImageIO.read(new FileImageInputStream(new File("Assets/wK.png")));
			pieceBoard[7][4].setImage(curImage);
		}
		catch(IOException ex) {
			Logger.getLogger(Board.class.getName()).log(Level.SEVERE,null,ex);
		}
	}*/
}
