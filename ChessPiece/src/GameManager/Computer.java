package GameManager;

import java.util.ArrayList;

import Board.Board;
import Pieces.Bishop;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;
import Pieces.Şah;

public class Computer {
	
	private BlackPlayer bPlayer;
	private WhitePlayer wPlayer;
	
	private ArrayList<Piece> whitePieces = new ArrayList<Piece>();
	private ArrayList<int[]> whiteMoveSet = new ArrayList<int[]>();
	private ArrayList<Piece> blackPieces = new ArrayList<Piece>();
	private ArrayList<int[]> blackMoveSet = new ArrayList<int[]>();
	
	private Board initialBoard;
	
	public Computer(Board board) {
		this.initialBoard = board;
		copyBoardObject();
	}
	
	public BoardNode generateMoveTree3Depth() { // It can just calculate until depth 4 ; 
		//I do not know if anything can be done, it is heapOverFlow Error so either we need more space (new desktop) or more space-efficent implementation
		System.out.println("Başladı");
		BoardNode root = new BoardNode(this.initialBoard);
		int i = 0;
		root.generateTree(false);
		for (BoardNode childNode : root.getChildNodes()) {
			childNode.generateTree(true);
			for (BoardNode grand1Child: childNode.getChildNodes()) {
				grand1Child.generateTree(false);
				for (BoardNode grand2Child: grand1Child.getChildNodes()) {
					grand2Child.generateTree(false);
					for (BoardNode grand3Child: grand2Child.getChildNodes()) {
						grand3Child.generateTree(false);
						
						i+=20;
						System.out.println(i);
					}
				}
			}
		}
		
		System.out.println("Bitti");
		
		return root;
	}
	
	public void generateMoveTree(int depth, int maxDepth, boolean blackMove) { // DÜZELLLTTTTTT 
		if (depth == 1) {
			
			BoardNode root = new BoardNode(this.initialBoard);
			root.generateTree(blackMove);
			
		}
		else if (depth > 1 && depth <maxDepth) {
			
		}
		else { // final
			
		}
		
	}
	
	public void generateAllWhiteMoves() {
		wPlayer = new WhitePlayer(initialBoard);
		for (Piece currWPiece: whitePieces) {
			ArrayList<int[]> currWPieceMoves = currWPiece.getPossibleMoves();
			wPlayer.checkMove(currWPiece, currWPiece.getPossibleMoves(), currWPiece.getxLoc(), currWPiece.getyLoc());
			this.whiteMoveSet.addAll(currWPieceMoves);
		}
		System.out.println(this.whiteMoveSet.size());
	}
	
	public void generateAllBlackMoves() {
		bPlayer = new BlackPlayer(initialBoard);
		for (Piece currBPiece: blackPieces) {
			ArrayList<int[]> currBPieceMoves = currBPiece.getPossibleMoves();
			bPlayer.checkMove(currBPiece, currBPiece.getPossibleMoves(), currBPiece.getxLoc(), currBPiece.getyLoc());
			this.blackMoveSet.addAll(currBPieceMoves);
		}
		System.out.println(this.blackMoveSet.size());
	}
	
	
	
	public void generatePieces() {
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++ ) {
				if (this.initialBoard.getPieceBoard()[i][j] != null && this.initialBoard.getPieceBoard()[i][j].getPieceString().charAt(0) == 'b') {
					this.blackPieces.add(this.initialBoard.getPieceBoard()[i][j]);
				}
				if (this.initialBoard.getPieceBoard()[i][j] != null && this.initialBoard.getPieceBoard()[i][j].getPieceString().charAt(0) == 'w') {
					this.whitePieces.add(this.initialBoard.getPieceBoard()[i][j]);
				}
			}
		}
	}
	
	
	private void copyBoardObject() {
		Board newBoard = new Board();
		newBoard.setPieceBoard(copyPieceBoard());
		adjustPieces(newBoard);
		this.initialBoard = newBoard;
	}
	
	private Board copyBoardObjectReturn() {
		Board newBoard = new Board();
		newBoard.setPieceBoard(copyPieceBoard());
		adjustPieces(newBoard);
		return newBoard;
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
				 if (initialBoard.getPieceBoard()[i][j] != null) {
					 if (initialBoard.getPieceBoard()[i][j] instanceof Pawn) {
						 copyBoard[i][j] = ((Pawn) initialBoard.getPieceBoard()[i][j]).clone();
					 }
					 else if (initialBoard.getPieceBoard()[i][j] instanceof Rook) {
						 copyBoard[i][j] = ((Rook) initialBoard.getPieceBoard()[i][j]).clone();
					 }
					 else if (initialBoard.getPieceBoard()[i][j] instanceof Knight) {
						 copyBoard[i][j] = ((Knight) initialBoard.getPieceBoard()[i][j]).clone();
					 }
					 else if (initialBoard.getPieceBoard()[i][j] instanceof Bishop)  {
						 copyBoard[i][j] = ((Bishop) initialBoard.getPieceBoard()[i][j]).clone();
					 }
					 else if (initialBoard.getPieceBoard()[i][j] instanceof Queen) {
						 copyBoard[i][j] = ((Queen) initialBoard.getPieceBoard()[i][j]).clone();
					 }
					 else if (initialBoard.getPieceBoard()[i][j] instanceof Şah) {
						 Şah currKing = ((Şah) initialBoard.getPieceBoard()[i][j]).clone();
						 copyBoard[i][j] = currKing;
					 }
				 }
			}
		}
		return copyBoard;
	}
	
	
}
