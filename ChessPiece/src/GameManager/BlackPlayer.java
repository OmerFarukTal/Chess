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

public class BlackPlayer {

	private Board board;
	private Şah blackKing;
	
	public BlackPlayer(Board board) {
		this.board = board;
		//putImages(this.board);
	}
	
	public void checkMove(Piece piece, ArrayList<int[]> moveSet, int currentX, int currentY) {
		
		if (!piece.getPieceString().equals("bK")) {
			for (int i = moveSet.size() -1; i>= 0; i--) {
				Board newBoard = new Board();
				newBoard.setPieceBoard(copyPieceBoard());
				adjustPieces(newBoard);
				
				
				Piece pieceN = newBoard.getPieceBoard()[currentX][currentY];
				newBoard.getPieceBoard()[currentX][currentY] = null;
				
				int xLoc = moveSet.get(i)[0];
				int yLoc = moveSet.get(i)[1];
				
				newBoard.getPieceBoard()[xLoc][yLoc] = pieceN;
				
				
				if (blackKing.checkCheck()) {
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
						 if (currKing.getPieceString().equals("bK")) {
							 blackKing = currKing;
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
			curImage = ImageIO.read(new FileImageInputStream(new File("Assets/bP.png")));
			for (int i = 0; i < 8; i++) {
				pieceBoard[1][i].setImage(curImage);
			}
		}
		catch(IOException ex) {
			Logger.getLogger(Board.class.getName()).log(Level.SEVERE,null,ex);
		}
		// Rook
		try {
			curImage = ImageIO.read(new FileImageInputStream(new File("Assets/bR.png")));
			pieceBoard[0][0].setImage(curImage);
			pieceBoard[0][7].setImage(curImage);
		}
		catch(IOException ex) {
			Logger.getLogger(Board.class.getName()).log(Level.SEVERE,null,ex);
		}
		// Knight
		try {
			curImage = ImageIO.read(new FileImageInputStream(new File("Assets/bN.png")));
			pieceBoard[0][1].setImage(curImage);
			pieceBoard[0][6].setImage(curImage);
		}
		catch(IOException ex) {
			Logger.getLogger(Board.class.getName()).log(Level.SEVERE,null,ex);
		}
		// Bishop
		try {
			curImage = ImageIO.read(new FileImageInputStream(new File("Assets/bB.png")));
			pieceBoard[0][2].setImage(curImage);
			pieceBoard[0][5].setImage(curImage);
		}
		catch(IOException ex) {
			Logger.getLogger(Board.class.getName()).log(Level.SEVERE,null,ex);
		}
		// Quuen and King
		try {
			curImage = ImageIO.read(new FileImageInputStream(new File("Assets/bQ.png")));
			pieceBoard[0][3].setImage(curImage);
			curImage = ImageIO.read(new FileImageInputStream(new File("Assets/bK.png")));
			pieceBoard[0][4].setImage(curImage);
		}
		catch(IOException ex) {
			Logger.getLogger(Board.class.getName()).log(Level.SEVERE,null,ex);
		}
	}
	*/
}
