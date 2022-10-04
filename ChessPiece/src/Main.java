import javax.swing.JFrame;

import Board.Board;
import GameManager.BoardNode;
import GameManager.Computer;
import Pieces.Bishop;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;
import Pieces.Şah;
import UI.ChessFrame;


public class Main {
	public static void main(String[] args) {
		
		
		ChessFrame chessFrame = new ChessFrame();
		chessFrame.setResizable(false);
		chessFrame.setFocusable(false);
		chessFrame.setSize(1200,1200);
		chessFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chessFrame.setVisible(true);
		
		
		/*
		Board board = new Board();
		printPieceBoard(board);
		
		Computer computer = new Computer(board);
		BoardNode root = new BoardNode(board);
		
		double result = root.minimax(3, Double.MIN_VALUE, Double.MAX_VALUE, false);
		System.out.println("Result " + result);
		*/
		
		
		/*
		BoardNode root = new BoardNode(board);
		root.generateTree(false);
		root.getChildNodes().get(0).generateTree(true);
		root.getChildNodes().get(0).getChildNodes().get(0).generateTree(false);
		
		
		printStringBoard(root.getChildNodes().get(0).getChildNodes().get(0).getBoard());
		root.getChildNodes().get(0).getChildNodes().get(0).generatePieces();
		System.out.println("Bastır = " + root.getChildNodes().get(0).getChildNodes().get(0).getBlackPieces().size());
		printStringBoard(root.getChildNodes().get(0).getChildNodes().get(0).getBoard());
		printPieceBoard(root.getChildNodes().get(0).getChildNodes().get(0).getBoard());
		System.out.println("Şimdi 5 e 0 ");
		printStringBoard(root.getChildNodes().get(0).getChildNodes().get(0).getBoard().getPieceBoard()[5][0].getBoard());
		root.getChildNodes().get(0).getChildNodes().get(0).getBoard().getPieceBoard()[5][0].possibleMoves();
		System.out.println(root.getChildNodes().get(0).getChildNodes().get(0).getBoard().getPieceBoard()[5][0].getPossibleMoves());
		System.out.println("Moveste = " + root.getChildNodes().get(0).getChildNodes().get(0).getBlackMoveSet().size());
		*/
		/*
		System.out.println("***********************************************\nHamleler\n************************************************");
		
		for (int i = 0; i < root.getChildNodes().get(0).getChildNodes().get(0).getChildNodes().size(); i++) { // root.getChildNodes().get(0).getChildNodes().get(0).getChildNodes().size()
			printStringBoard(root.getChildNodes().get(0).getChildNodes().get(0).getChildNodes().get(i).getBoard());
		System.out.println("******************************************************************************************************************************************************");
		}
		*/
		//root.getChildNodes().get(0).generateTree(true);
		
	}
	
	public static void printStringBoard(Board board) {
		String boardString = board.toString();
		String[] arr = boardString.split("\\n");
		String curr = "  ";
		for (int i = 0; i < 8 ; i++) {
			curr += i + "   ";
		}
		System.out.println(curr);
		for (int i = 0; i < 8; i ++) {
			System.out.println(i + arr[i]);
		}
		System.out.println();
	}
	
	private static void printPieceBoard(Board board) {
		Piece[][] pieceBoard = board.getPieceBoard();
		for (int i = 0; i < 8; i ++) {
			for (int j = 0; j < 8; j++) {
				if (pieceBoard[i][j] != null) {
					System.out.print(pieceBoard[i][j] + "    ") ;
				}
				else {
					System.out.print("---------------------   "); 
					//               "------------------------"   "                        "
				}
			}
			System.out.println();
		}
	}
	
	public static void testKnight(Board board) {
		Knight knight = new Knight(board, 2,2, "wK"); 
		for (int[] curr: knight.getPossibleMoves()) {
			for (int i = 0; i <2 ; i++) {
				System.out.println(curr[i]);
			}
		}
	}
	
	public static void testBishop(Board board) {
		Bishop bishop = new Bishop(board, 3,2, "wB");
		for (int[] curr: bishop.getPossibleMoves()) {
			for (int i = 0; i <2 ; i++) {
				System.out.print(curr[i] + "  ");
			}
			System.out.println();
		}
	}
	
	public static void testRook(Board board) {
		Rook rook = new Rook(board, 3,3, "wR");
		for (int[] curr: rook.getPossibleMoves()) {
			for (int i = 0; i <2 ; i++) {
				System.out.print(curr[i] + "  ");
			}
			System.out.println();
		}
	}
	
	public static void testQueen(Board board) {
		Queen queen = new Queen(board, 3,3, "wQ");
		for (int[] curr: queen.getPossibleMoves()) {
			for (int i = 0; i <2 ; i++) {
				System.out.print(curr[i] + "  ");
			}
			System.out.println();
		}
	}
	
	public static void testPawn(Board board) {
		Pawn pawn = new Pawn(board, 6,5, "wP");
		for (int[] curr: pawn.getPossibleMoves()) {
			for (int i = 0; i <2 ; i++) {
				System.out.print(curr[i] + "  ");
			}
			System.out.println();
		}
	}
	
	public static void testKing(Board board) {
		Şah king = new Şah(board, 2,7, "wK");
		
		for (int[] curr: king.getPossibleMoves()) {
			for (int i = 0; i <2 ; i++) {
				System.out.print(curr[i] + "  ");
			}
			System.out.println();
		}
		
		
	}
}
