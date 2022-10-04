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

public class BoardNode {
	
	private BoardNode parentNode;
	private Board board;
	private ArrayList<BoardNode> childNodes = new ArrayList<BoardNode>();
	
	private ArrayList<Piece> blackPieces = new ArrayList<Piece>();
	
	private ArrayList<Piece> whitePieces = new ArrayList<Piece>();
	
	private boolean chosen = false;
	
	public BoardNode(Board board) {
		this.board = board;
		this.board = copyBoardObjectReturn();
	}
	
	public BoardNode() {
		
	}
	
	public void generateTree(boolean blackMove) {
		generatePieces();
		if (blackMove) {
			BlackPlayer bPlayer = new BlackPlayer(board);
			for (Piece currBPiece : blackPieces) {
				
				ArrayList<int[]> currBPieceMoves = currBPiece.getPossibleMoves();
				bPlayer.checkMove(currBPiece, currBPiece.getPossibleMoves(), currBPiece.getxLoc(), currBPiece.getyLoc());
				
				int currentX = currBPiece.getxLoc();
				int currentY = currBPiece.getyLoc();
				
				for (int[] currMove: currBPieceMoves) {
					BoardNode childNode = new BoardNode();
					childNode.setParentNode(this);
					Board newBoard = copyBoardObjectReturn();
					
					newBoard.getPieceBoard()[currentX][currentY].setxLoc(currMove[0]); newBoard.getPieceBoard()[currentX][currentY].setyLoc(currMove[1]);
					newBoard.getPieceBoard()[currMove[0]][currMove[1]] = newBoard.getPieceBoard()[currentX][currentY];
					newBoard.getPieceBoard()[currentX][currentY] = null;
					
					if (currBPiece instanceof Pawn && currentX == 1 && currMove[0] == 3) {
						((Pawn) newBoard.getPieceBoard()[currMove[0]][currMove[1]]).setDoubleMove(true);
					}
					else if (currBPiece instanceof Pawn && currMove[0] == 7) {
						// Rook Promotion
						BoardNode childNodeRook = new BoardNode();
						childNodeRook.setParentNode(this);
						Board newBoardRook = copyBoardObjectReturn();
						
						Rook rook = new Rook(newBoardRook, currMove[0], currMove[1], "bR");
						rook.setHasMoved(true);
						newBoardRook.getPieceBoard()[currentX][currentY].setxLoc(currMove[0]); newBoardRook.getPieceBoard()[currentX][currentY].setyLoc(currMove[1]);
						newBoardRook.getPieceBoard()[currMove[0]][currMove[1]] = newBoardRook.getPieceBoard()[currentX][currentY];
						newBoardRook.getPieceBoard()[currentX][currentY] = null;
						newBoardRook.getPieceBoard()[currMove[0]][currMove[1]] = rook;
						
						newBoardRook.makeWhitePawnDoubleMoveFalse();
						//if (newBoardRook.calculateBoardPoint() <= board.calculateBoardPoint()) {
							childNodeRook.setBoard(newBoardRook);
							childNodes.add(childNodeRook);
						//}
						
						// Knight Promotion
						
						BoardNode childNodeKnight = new BoardNode();
						childNodeKnight.setParentNode(this);
						Board newBoardKnight = copyBoardObjectReturn();
						
						Knight knight = new Knight(newBoardKnight, currMove[0], currMove[1], "bN");
						newBoardKnight.getPieceBoard()[currentX][currentY].setxLoc(currMove[0]); newBoardKnight.getPieceBoard()[currentX][currentY].setyLoc(currMove[1]);
						newBoardKnight.getPieceBoard()[currMove[0]][currMove[1]] = newBoardKnight.getPieceBoard()[currentX][currentY];
						newBoardKnight.getPieceBoard()[currentX][currentY] = null;
						newBoardKnight.getPieceBoard()[currMove[0]][currMove[1]] = knight;
						
						newBoardKnight.makeWhitePawnDoubleMoveFalse();
						//if (newBoardKnight.calculateBoardPoint() <= board.calculateBoardPoint()) {
							childNodeKnight.setBoard(newBoardKnight);
							childNodes.add(childNodeKnight);
						//}
						
						// Bishop Promotion
						
						BoardNode childNodeBishop = new BoardNode();
						childNodeBishop.setParentNode(this);
						Board newBoardBishop = copyBoardObjectReturn();
						
						Bishop bishop = new Bishop(newBoardBishop, currMove[0], currMove[1], "bB");
						newBoardBishop.getPieceBoard()[currentX][currentY].setxLoc(currMove[0]); newBoardBishop.getPieceBoard()[currentX][currentY].setyLoc(currMove[1]);
						newBoardBishop.getPieceBoard()[currMove[0]][currMove[1]] = newBoardBishop.getPieceBoard()[currentX][currentY];
						newBoardBishop.getPieceBoard()[currentX][currentY] = null;
						newBoardBishop.getPieceBoard()[currMove[0]][currMove[1]] = bishop;
						
						newBoardBishop.makeWhitePawnDoubleMoveFalse();
						//if (newBoardBishop.calculateBoardPoint() <= board.calculateBoardPoint()) {
							childNodeBishop.setBoard(newBoardBishop);
							childNodes.add(childNodeBishop);
						//}

						// Queen Promotion
						
						BoardNode childNodeQueen = new BoardNode();
						childNodeQueen.setParentNode(this);
						Board newBoardQueen = copyBoardObjectReturn();
						
						Queen queen = new Queen(newBoardQueen, currMove[0], currMove[1], "bQ");
						newBoardQueen.getPieceBoard()[currentX][currentY].setxLoc(currMove[0]); newBoardQueen.getPieceBoard()[currentX][currentY].setyLoc(currMove[1]);
						newBoardQueen.getPieceBoard()[currMove[0]][currMove[1]] = newBoardQueen.getPieceBoard()[currentX][currentY];
						newBoardQueen.getPieceBoard()[currentX][currentY] = null;
						newBoardQueen.getPieceBoard()[currMove[0]][currMove[1]] = queen;
						
						newBoardQueen.makeWhitePawnDoubleMoveFalse();
						//if (newBoardQueen.calculateBoardPoint() <= board.calculateBoardPoint()) {
							childNodeQueen.setBoard(newBoardQueen);
							childNodes.add(childNodeQueen);
						//}
						
						continue;
					}
					else if (currBPiece instanceof Rook) {
						((Rook) newBoard.getPieceBoard()[currMove[0]][currMove[1]]).setHasMoved(true);
					}
					else if (currBPiece instanceof Şah) {
						((Şah) newBoard.getPieceBoard()[currMove[0]][currMove[1]]).setHasMoved(true);
					}
					
					newBoard.makeWhitePawnDoubleMoveFalse();
					//if (newBoard.calculateBoardPoint() <= board.calculateBoardPoint()) {
						childNode.setBoard(newBoard);
						childNodes.add(childNode);
					//}
					//childNode.setBoard();
				}
			}
			// Castle Moves
			
			
			ArrayList<int[]> bCastleMoves = board.getBlackCastleMoves();
			
			for (int[] currMove: bCastleMoves) {
				BoardNode childNode = new BoardNode();
				childNode.setParentNode(this);
				Board newBoard = copyBoardObjectReturn();
				// Kısa Rook
				if (currMove[1] == 6) {
					// Şah
					newBoard.getPieceBoard()[0][6] = newBoard.getPieceBoard()[0][4];
					newBoard.getPieceBoard()[0][6].setxLoc(0); newBoard.getPieceBoard()[0][6].setyLoc(6);
					((Şah) newBoard.getPieceBoard()[0][6]).setHasMoved(true);
					newBoard.getPieceBoard()[0][4] = null;
					
					// Rook
					newBoard.getPieceBoard()[0][5] = newBoard.getPieceBoard()[0][7];
					newBoard.getPieceBoard()[0][5].setxLoc(0); newBoard.getPieceBoard()[0][5].setyLoc(5);
					((Rook) newBoard.getPieceBoard()[0][5]).setHasMoved(true);
					newBoard.getPieceBoard()[0][7] = null;
				}
				if (currMove[1] == 2) {
					// Şah
					newBoard.getPieceBoard()[0][2] = newBoard.getPieceBoard()[0][4];
					newBoard.getPieceBoard()[0][2].setxLoc(0); newBoard.getPieceBoard()[0][2].setyLoc(2);
					((Şah) newBoard.getPieceBoard()[0][2]).setHasMoved(true);
					newBoard.getPieceBoard()[0][4] = null;
					
					// Rook
					newBoard.getPieceBoard()[0][3] = newBoard.getPieceBoard()[0][0];
					newBoard.getPieceBoard()[0][3].setxLoc(0); newBoard.getPieceBoard()[0][3].setyLoc(3);
					((Rook) newBoard.getPieceBoard()[0][3]).setHasMoved(true);
					newBoard.getPieceBoard()[0][0] = null;
				}
				
				newBoard.makeWhitePawnDoubleMoveFalse();
				//if (newBoard.calculateBoardPoint() <= board.calculateBoardPoint()) {
					childNode.setBoard(newBoard);
					childNodes.add(childNode);
				//}
				
			}
			
			
		}
		else {
			WhitePlayer wPlayer = new WhitePlayer(board);
			for (Piece currWPiece : whitePieces) {
				
				ArrayList<int[]> currWPieceMoves = currWPiece.getPossibleMoves();
				wPlayer.checkMove(currWPiece, currWPiece.getPossibleMoves(), currWPiece.getxLoc(), currWPiece.getyLoc());
				
				int currentX = currWPiece.getxLoc();
				int currentY = currWPiece.getyLoc();
				
				for (int[] currMove: currWPieceMoves) {
					BoardNode childNode = new BoardNode();
					childNode.setParentNode(this);
					Board newBoard = copyBoardObjectReturn();
					
					newBoard.getPieceBoard()[currentX][currentY].setxLoc(currMove[0]); newBoard.getPieceBoard()[currentX][currentY].setyLoc(currMove[1]);
					newBoard.getPieceBoard()[currMove[0]][currMove[1]] = newBoard.getPieceBoard()[currentX][currentY];
					newBoard.getPieceBoard()[currentX][currentY] = null;
					
					if (currWPiece instanceof Pawn && currentX == 6 && currMove[0] == 4) {
						((Pawn) newBoard.getPieceBoard()[currMove[0]][currMove[1]]).setDoubleMove(true);
					}
					else if (currWPiece instanceof Pawn && currMove[0] == 0) {
						// Rook Promotion
						BoardNode childNodeRook = new BoardNode();
						childNodeRook.setParentNode(this);
						Board newBoardRook = copyBoardObjectReturn();
						
						Rook rook = new Rook(newBoardRook, currMove[0], currMove[1], "wR");
						rook.setHasMoved(true);
						newBoardRook.getPieceBoard()[currentX][currentY].setxLoc(currMove[0]); newBoardRook.getPieceBoard()[currentX][currentY].setyLoc(currMove[1]);
						newBoardRook.getPieceBoard()[currMove[0]][currMove[1]] = newBoardRook.getPieceBoard()[currentX][currentY];
						newBoardRook.getPieceBoard()[currentX][currentY] = null;
						newBoardRook.getPieceBoard()[currMove[0]][currMove[1]] = rook;
						
						newBoardRook.makeBlackPawnDoubleMoveFalse();
						//if (newBoardRook.calculateBoardPoint() >= board.calculateBoardPoint()) {
							childNodeRook.setBoard(newBoardRook);
							childNodes.add(childNodeRook);
						//}
						
						// Knight Promotion
						
						BoardNode childNodeKnight = new BoardNode();
						childNodeKnight.setParentNode(this);
						Board newBoardKnight = copyBoardObjectReturn();
						
						Knight knight = new Knight(newBoardKnight, currMove[0], currMove[1], "wN");
						newBoardKnight.getPieceBoard()[currentX][currentY].setxLoc(currMove[0]); newBoardKnight.getPieceBoard()[currentX][currentY].setyLoc(currMove[1]);
						newBoardKnight.getPieceBoard()[currMove[0]][currMove[1]] = newBoardKnight.getPieceBoard()[currentX][currentY];
						newBoardKnight.getPieceBoard()[currentX][currentY] = null;
						newBoardKnight.getPieceBoard()[currMove[0]][currMove[1]] = knight;
						
						newBoardKnight.makeBlackPawnDoubleMoveFalse();
						//if (newBoardKnight.calculateBoardPoint() >= board.calculateBoardPoint()) {
							childNodeKnight.setBoard(newBoardKnight);
							childNodes.add(childNodeKnight);
						//}
						
						// Bishop Promotion
						
						BoardNode childNodeBishop = new BoardNode();
						childNodeBishop.setParentNode(this);
						Board newBoardBishop = copyBoardObjectReturn();
						
						Bishop bishop = new Bishop(newBoardBishop, currMove[0], currMove[1], "wB");
						newBoardBishop.getPieceBoard()[currentX][currentY].setxLoc(currMove[0]); newBoardBishop.getPieceBoard()[currentX][currentY].setyLoc(currMove[1]);
						newBoardBishop.getPieceBoard()[currMove[0]][currMove[1]] = newBoardBishop.getPieceBoard()[currentX][currentY];
						newBoardBishop.getPieceBoard()[currentX][currentY] = null;
						newBoardBishop.getPieceBoard()[currMove[0]][currMove[1]] = bishop;
						
						newBoardBishop.makeBlackPawnDoubleMoveFalse();
						//if (newBoardBishop.calculateBoardPoint() >= board.calculateBoardPoint()) {
							childNodeBishop.setBoard(newBoardBishop);
							childNodes.add(childNodeBishop);
						//}

						// Queen Promotion
						
						BoardNode childNodeQueen = new BoardNode();
						childNodeQueen.setParentNode(this);
						Board newBoardQueen = copyBoardObjectReturn();
						
						Queen queen = new Queen(newBoardQueen, currMove[0], currMove[1], "wQ");
						newBoardQueen.getPieceBoard()[currentX][currentY].setxLoc(currMove[0]); newBoardQueen.getPieceBoard()[currentX][currentY].setyLoc(currMove[1]);
						newBoardQueen.getPieceBoard()[currMove[0]][currMove[1]] = newBoardQueen.getPieceBoard()[currentX][currentY];
						newBoardQueen.getPieceBoard()[currentX][currentY] = null;
						newBoardQueen.getPieceBoard()[currMove[0]][currMove[1]] = queen;
						
						newBoardQueen.makeBlackPawnDoubleMoveFalse();
						//if (newBoardQueen.calculateBoardPoint() >= board.calculateBoardPoint()) {
							childNodeQueen.setBoard(newBoardQueen);
							childNodes.add(childNodeQueen);
						//}
						
						continue;
					}
					else if (currWPiece instanceof Rook) {
						((Rook) newBoard.getPieceBoard()[currMove[0]][currMove[1]]).setHasMoved(true);
					}
					else if (currWPiece instanceof Şah) {
						((Şah) newBoard.getPieceBoard()[currMove[0]][currMove[1]]).setHasMoved(true);
					}
					
					
					newBoard.makeBlackPawnDoubleMoveFalse();
					//if (newBoard.calculateBoardPoint() >= board.calculateBoardPoint()) {
						childNode.setBoard(newBoard);
						childNodes.add(childNode);
					//}
					//childNode.setBoard();
				}
			}
			
			// Castle Moves
			ArrayList<int[]> wCastleMoves = board.getWhiteCastleMoves();
			
			for (int[] currMove: wCastleMoves) {
				BoardNode childNode = new BoardNode();
				childNode.setParentNode(this);
				Board newBoard = copyBoardObjectReturn();
				// Kısa Rook
				if (currMove[1] == 6) {
					// Şah
					newBoard.getPieceBoard()[7][6] = newBoard.getPieceBoard()[7][4];
					newBoard.getPieceBoard()[7][6].setxLoc(7); newBoard.getPieceBoard()[7][6].setyLoc(6);
					((Şah) newBoard.getPieceBoard()[7][6]).setHasMoved(true);
					newBoard.getPieceBoard()[7][4] = null;
					
					// Rook
					newBoard.getPieceBoard()[7][5] = newBoard.getPieceBoard()[7][7];
					newBoard.getPieceBoard()[7][5].setxLoc(7); newBoard.getPieceBoard()[7][5].setyLoc(5);
					((Rook) newBoard.getPieceBoard()[7][5]).setHasMoved(true);
					newBoard.getPieceBoard()[7][7] = null;
				}
				if (currMove[1] == 2) {
					// Şah
					newBoard.getPieceBoard()[7][2] = newBoard.getPieceBoard()[7][4];
					newBoard.getPieceBoard()[7][2].setxLoc(7); newBoard.getPieceBoard()[7][2].setyLoc(2);
					((Şah) newBoard.getPieceBoard()[7][2]).setHasMoved(true);
					newBoard.getPieceBoard()[7][4] = null;
					
					// Rook
					newBoard.getPieceBoard()[7][3] = newBoard.getPieceBoard()[7][0];
					newBoard.getPieceBoard()[7][3].setxLoc(7); newBoard.getPieceBoard()[7][3].setyLoc(3);
					((Rook) newBoard.getPieceBoard()[7][3]).setHasMoved(true);
					newBoard.getPieceBoard()[7][0] = null;
				}
				
				newBoard.makeBlackPawnDoubleMoveFalse();
				//if (newBoard.calculateBoardPoint() >= board.calculateBoardPoint()) {
					childNode.setBoard(newBoard);
					childNodes.add(childNode);
				//}
				
			}
		}
		//System.out.println("childNode Size = " +  childNodes.size());
	}
	
	public void generatePieces() {
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++ ) {
				if (this.board.getPieceBoard()[i][j] != null && this.board.getPieceBoard()[i][j].getPieceString().charAt(0) == 'b') {
					this.blackPieces.add(this.board.getPieceBoard()[i][j]);
				}
				if (this.board.getPieceBoard()[i][j] != null && this.board.getPieceBoard()[i][j].getPieceString().charAt(0) == 'w') {
					this.whitePieces.add(this.board.getPieceBoard()[i][j]);
				}
			}
		}
	}
	
	
	/*
	 * Copy Board
	 */
	
	private void copyBoardObject() {
		Board newBoard = new Board();
		newBoard.setPieceBoard(copyPieceBoard());
		adjustPieces(newBoard);
		this.board = newBoard;
	}
	
	public Board copyBoardObjectReturn() {
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
						
					 }
				 }
			}
		}
		return copyBoard;
	}
	
	
	/*
	 * Getter And Setters
	 */
	
	public BoardNode getParentNode() {
		return parentNode;
	}

	public void setParentNode(BoardNode parentNode) {
		this.parentNode = parentNode;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public ArrayList<BoardNode> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(ArrayList<BoardNode> childNodes) {
		this.childNodes = childNodes;
	}

	public ArrayList<Piece> getBlackPieces() {
		return blackPieces;
	}

	public void setBlackPieces(ArrayList<Piece> blackPieces) {
		this.blackPieces = blackPieces;
	}

	

	public ArrayList<Piece> getWhitePieces() {
		return whitePieces;
	}

	public void setWhitePieces(ArrayList<Piece> whitePieces) {
		this.whitePieces = whitePieces;
	}

	
	public ArrayList<int[]> tester(boolean blackMove) {
		ArrayList<int[]> moves = new ArrayList<int[]>();
		generatePieces();
		if (blackMove) {
			BlackPlayer bPlayer = new BlackPlayer(board);
			for (Piece currBPiece : blackPieces) {
				
				ArrayList<int[]> currBPieceMoves = currBPiece.getPossibleMoves();
				bPlayer.checkMove(currBPiece, currBPiece.getPossibleMoves(), currBPiece.getxLoc(), currBPiece.getyLoc());
				
				int currentX = currBPiece.getxLoc();
				int currentY = currBPiece.getyLoc();
				
				for (int[] currMove: currBPieceMoves) {
					BoardNode childNode = new BoardNode();
					childNode.setParentNode(this);
					Board newBoard = copyBoardObjectReturn();
					
					newBoard.getPieceBoard()[currentX][currentY].setxLoc(currMove[0]); newBoard.getPieceBoard()[currentX][currentY].setyLoc(currMove[1]);
					newBoard.getPieceBoard()[currMove[0]][currMove[1]] = newBoard.getPieceBoard()[currentX][currentY];
					newBoard.getPieceBoard()[currentX][currentY] = null;
					
					if (currBPiece instanceof Pawn && currentX == 1 && currMove[0] == 3) {
						((Pawn) newBoard.getPieceBoard()[currMove[0]][currMove[1]]).setDoubleMove(true);
					}
					else if (currBPiece instanceof Pawn && currMove[0] == 7) {
						// Rook Promotion
						BoardNode childNodeRook = new BoardNode();
						childNodeRook.setParentNode(this);
						Board newBoardRook = copyBoardObjectReturn();
						
						Rook rook = new Rook(newBoardRook, currMove[0], currMove[1], "bR");
						rook.setHasMoved(true);
						newBoardRook.getPieceBoard()[currentX][currentY].setxLoc(currMove[0]); newBoardRook.getPieceBoard()[currentX][currentY].setyLoc(currMove[1]);
						newBoardRook.getPieceBoard()[currMove[0]][currMove[1]] = newBoardRook.getPieceBoard()[currentX][currentY];
						newBoardRook.getPieceBoard()[currentX][currentY] = null;
						newBoardRook.getPieceBoard()[currMove[0]][currMove[1]] = rook;
						
						newBoardRook.makeWhitePawnDoubleMoveFalse();
						//if (newBoardRook.calculateBoardPoint() <= board.calculateBoardPoint()) {
							childNodeRook.setBoard(newBoardRook);
							childNodes.add(childNodeRook);
							int[] move = new int[2];
							move[0] = currMove[0]; move[1] = currMove[1];
							moves.add(move);
						//}
						
						// Knight Promotion
						
						BoardNode childNodeKnight = new BoardNode();
						childNodeKnight.setParentNode(this);
						Board newBoardKnight = copyBoardObjectReturn();
						
						Knight knight = new Knight(newBoardKnight, currMove[0], currMove[1], "bN");
						newBoardKnight.getPieceBoard()[currentX][currentY].setxLoc(currMove[0]); newBoardKnight.getPieceBoard()[currentX][currentY].setyLoc(currMove[1]);
						newBoardKnight.getPieceBoard()[currMove[0]][currMove[1]] = newBoardKnight.getPieceBoard()[currentX][currentY];
						newBoardKnight.getPieceBoard()[currentX][currentY] = null;
						newBoardKnight.getPieceBoard()[currMove[0]][currMove[1]] = knight;
						
						newBoardKnight.makeWhitePawnDoubleMoveFalse();
						//if (newBoardKnight.calculateBoardPoint() <= board.calculateBoardPoint()) {
							childNodeKnight.setBoard(newBoardKnight);
							childNodes.add(childNodeKnight);
						//}
						
						// Bishop Promotion
						
						BoardNode childNodeBishop = new BoardNode();
						childNodeBishop.setParentNode(this);
						Board newBoardBishop = copyBoardObjectReturn();
						
						Bishop bishop = new Bishop(newBoardBishop, currMove[0], currMove[1], "bB");
						newBoardBishop.getPieceBoard()[currentX][currentY].setxLoc(currMove[0]); newBoardBishop.getPieceBoard()[currentX][currentY].setyLoc(currMove[1]);
						newBoardBishop.getPieceBoard()[currMove[0]][currMove[1]] = newBoardBishop.getPieceBoard()[currentX][currentY];
						newBoardBishop.getPieceBoard()[currentX][currentY] = null;
						newBoardBishop.getPieceBoard()[currMove[0]][currMove[1]] = bishop;
						
						newBoardBishop.makeWhitePawnDoubleMoveFalse();
						//if (newBoardBishop.calculateBoardPoint() <= board.calculateBoardPoint()) {
							childNodeBishop.setBoard(newBoardBishop);
							childNodes.add(childNodeBishop);
						//}

						// Queen Promotion
						
						BoardNode childNodeQueen = new BoardNode();
						childNodeQueen.setParentNode(this);
						Board newBoardQueen = copyBoardObjectReturn();
						
						Queen queen = new Queen(newBoardQueen, currMove[0], currMove[1], "bQ");
						newBoardQueen.getPieceBoard()[currentX][currentY].setxLoc(currMove[0]); newBoardQueen.getPieceBoard()[currentX][currentY].setyLoc(currMove[1]);
						newBoardQueen.getPieceBoard()[currMove[0]][currMove[1]] = newBoardQueen.getPieceBoard()[currentX][currentY];
						newBoardQueen.getPieceBoard()[currentX][currentY] = null;
						newBoardQueen.getPieceBoard()[currMove[0]][currMove[1]] = queen;
						
						newBoardQueen.makeWhitePawnDoubleMoveFalse();
						//if (newBoardQueen.calculateBoardPoint() <= board.calculateBoardPoint()) {
							childNodeQueen.setBoard(newBoardQueen);
							childNodes.add(childNodeQueen);
						//}
						
						continue;
					}
					else if (currBPiece instanceof Rook) {
						((Rook) newBoard.getPieceBoard()[currMove[0]][currMove[1]]).setHasMoved(true);
					}
					else if (currBPiece instanceof Şah) {
						((Şah) newBoard.getPieceBoard()[currMove[0]][currMove[1]]).setHasMoved(true);
					}
					
					newBoard.makeWhitePawnDoubleMoveFalse();
					//if (newBoard.calculateBoardPoint() <= board.calculateBoardPoint()) {
						childNode.setBoard(newBoard);
						childNodes.add(childNode);
						int[] move = new int[2];
						move[0] = currMove[0]; move[1] = currMove[1];
						moves.add(move);
					//}
					//childNode.setBoard();
				}
			}
			// Castle Moves
			
			
			ArrayList<int[]> bCastleMoves = board.getBlackCastleMoves();
			
			for (int[] currMove: bCastleMoves) {
				BoardNode childNode = new BoardNode();
				childNode.setParentNode(this);
				Board newBoard = copyBoardObjectReturn();
				// Kısa Rook
				if (currMove[1] == 6) {
					// Şah
					newBoard.getPieceBoard()[0][6] = newBoard.getPieceBoard()[0][4];
					newBoard.getPieceBoard()[0][6].setxLoc(0); newBoard.getPieceBoard()[0][6].setyLoc(6);
					((Şah) newBoard.getPieceBoard()[0][6]).setHasMoved(true);
					newBoard.getPieceBoard()[0][4] = null;
					
					// Rook
					newBoard.getPieceBoard()[0][5] = newBoard.getPieceBoard()[0][7];
					newBoard.getPieceBoard()[0][5].setxLoc(0); newBoard.getPieceBoard()[0][5].setyLoc(5);
					((Rook) newBoard.getPieceBoard()[0][5]).setHasMoved(true);
					newBoard.getPieceBoard()[0][7] = null;
				}
				if (currMove[1] == 2) {
					// Şah
					newBoard.getPieceBoard()[0][2] = newBoard.getPieceBoard()[0][4];
					newBoard.getPieceBoard()[0][2].setxLoc(0); newBoard.getPieceBoard()[0][2].setyLoc(2);
					((Şah) newBoard.getPieceBoard()[0][2]).setHasMoved(true);
					newBoard.getPieceBoard()[0][4] = null;
					
					// Rook
					newBoard.getPieceBoard()[0][3] = newBoard.getPieceBoard()[0][0];
					newBoard.getPieceBoard()[0][3].setxLoc(0); newBoard.getPieceBoard()[0][3].setyLoc(3);
					((Rook) newBoard.getPieceBoard()[0][3]).setHasMoved(true);
					newBoard.getPieceBoard()[0][0] = null;
				}
				
				newBoard.makeWhitePawnDoubleMoveFalse();
				//if (newBoard.calculateBoardPoint() <= board.calculateBoardPoint()) {
					childNode.setBoard(newBoard);
					childNodes.add(childNode);
					int[] move = new int[2];
					move[0] = currMove[0]; move[1] = currMove[1];
					moves.add(move);
				//}
				
			}
			
			
		}
		System.out.println("Size "  + moves.size());
		return moves;
	}
	
	
	public double minimax(int depth, double alpha, double beta, boolean maximazingPlayer) {
		if (depth == 0 || board.gameOver()) {
			return board.calculateBoardPoint();
			
		}
		if (maximazingPlayer) {
			double maxEval = -10000;
			this.generateTree(false);
			for (BoardNode currNode: this.childNodes) {
				//maxEval = Double.max(maxEval, currNode.minimax(depth -1, alpha, beta, false));
				
				double newEval = currNode.minimax(depth -1, alpha, beta, false);
				
				if (maxEval < newEval) {
					maxEval = newEval;
					this.setAllChildrenChosenFalse();
					currNode.setChosen(true);
				}
				
				if (maxEval >= beta) {
					break;
				}
				alpha = Double.max(alpha, maxEval);
			}			
			
			return maxEval;
		}
		else {
			double minEval = 10000;
			this.generateTree(true);
			for (BoardNode currNode: this.childNodes) {
				//minEval = Double.min(minEval, currNode.minimax(depth -1, alpha, beta, true));
				
				double newEval = currNode.minimax(depth -1, alpha, beta, true);
				
				if (newEval < minEval) {
					minEval = newEval;
					this.setAllChildrenChosenFalse();
					currNode.setChosen(true);
				}
				
				if (minEval <= alpha) {
					break;
				}
				beta = Double.min(beta, minEval);
			}	
			
			return minEval;
		}
		
	}
	
	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}
	
	public boolean getChosen() {
		return chosen;
	}
	
	public void setAllChildrenChosenFalse() {
		for (BoardNode currNode: this.childNodes) {
			currNode.setChosen(false);
		}
	}
	
	

}
