package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JPanel;

import Board.Board;
import GameManager.WhitePlayer;
import Pieces.Piece;

public class ChessPanel extends JPanel{
	private Board board;
	private Point[] piecePoints = new Point[32];
	private Piece[] pieces = new Piece[32];
	private boolean hold = false;
	private int currentPieceInd;
	private int currentPieceX;
	private int currentPieceY;
	private String pieceString;
	private ArrayList<int[]> helperPoints;
	
	public ChessPanel( ) {
		board = new Board();
		WhitePlayer wPlayer = new WhitePlayer(board);
		ClickListener clickListener = new ClickListener();
		//DragListener dragListener= new DragListener();
		this.addMouseListener(clickListener);
		//this.addMouseMotionListener(dragListener);
		putPoints();
	}
	
	private class ClickListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			//System.out.println("E x Loc  = " + e.getX() + " y Loc " + e.getY());
			for(int i = 0; i<32; i++) {
				if (piecePoints[i] != null && (e.getY() > (int)piecePoints[i].getX()*100 && e.getY() <= (int)piecePoints[i].getX()*100+95 ) &&
						(e.getX() > (int)piecePoints[i].getY()*100 && e.getX() <= (int)piecePoints[i].getY()*100+95 )) {
					currentPieceInd = i;
					currentPieceX = pieces[i].getxLoc();
					currentPieceY = pieces[i].getyLoc();
					//System.out.println("Tuttum := " + i + " curr X " +  currentPieceX + " curr Y " + currentPieceY);
					
					// Change Board
					board.getPieceBoard()[currentPieceX][currentPieceY].possibleMoves();
					helperPoints = board.getPieceBoard()[currentPieceX][currentPieceY].getPossibleMoves();
					board.getPieceBoard()[currentPieceX][currentPieceY] = null;
					pieceString = board.getBoard()[currentPieceX][currentPieceY];
					board.getBoard()[currentPieceX][currentPieceY] = null;
					
					
					// 
					hold = true;
					repaint();
					//printPieceBoard();
				}
			}
			
		}
		
		public void mouseReleased(MouseEvent e) {
			if (hold) {
				Point currPointFixed = new Point(e.getPoint().x - e.getPoint().x%100+15, e.getPoint().y - e.getPoint().y%100+15);
				
				pieces[currentPieceInd].setyLoc((int)currPointFixed.x/100);
				pieces[currentPieceInd].setxLoc((int)currPointFixed.y/100);
				
				board.getPieceBoard()[pieces[currentPieceInd].getxLoc()][pieces[currentPieceInd].getyLoc()] = pieces[currentPieceInd];
				board.getBoard()[pieces[currentPieceInd].getxLoc()][pieces[currentPieceInd].getyLoc()] = pieceString;
				
				
				repaint();
				putPoints();
				hold = false;
				helperPoints = null;
				//printPieceBoard();
			}
		}
		
	}
	/*
	private class DragListener extends MouseMotionAdapter{
		public void mouseDragged(MouseEvent e) {
			if (prevPt != null) {
				Point currPoint = e.getPoint();
				repaint();
			}
		}
		
	}
	*/
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(board.getImage(), 0, 0, 800, 800, this);
		drawPieces(g);
		drawHelperPoints(g);
		drawBoundries(g);
	}
	
	
	private void putPoints() {
		//System.out.println("Koydum");
		Piece[][] pieceBoard = board.getPieceBoard();
		int k = 0;
		
		
		for (int i = 0; i < 8; i ++) {
			for (int j = 0; j < 8; j++) {
				if (pieceBoard[i][j] != null) {
					piecePoints[k] = new Point(pieceBoard[i][j].getxLoc(), pieceBoard[i][j].getyLoc());
					pieces[k] = pieceBoard[i][j];
					k++;
				}
			}
		}
		
	}
	
	private void drawPieces(Graphics g) {
		Piece[][] pieceBoard = board.getPieceBoard();
		for (int i = 0; i < 8; i ++) {
			for (int j = 0; j < 8; j++) {
				if (pieceBoard[i][j] != null) {
					g.drawImage(pieceBoard[i][j].getImage(), 15 + 100*j , 15 + 100*i, 80, 80, this);
				}
			}
		}
	}
	
	private void drawHelperPoints(Graphics g) {
		if (helperPoints != null) {
			System.out.println(helperPoints);
			for (int[] currPoint: helperPoints) {
				g.setColor(Color.BLUE);
				g.fillOval(currPoint[1]*100+15, currPoint[0]*100+15, 30, 30);
			}
		}
	}
	
	private void printPieceBoard() {
		Piece[][] pieceBoard = board.getPieceBoard();
		for (int i = 0; i < 8; i ++) {
			for (int j = 0; j < 8; j++) {
				if (pieceBoard[i][j] != null) {
					System.out.print(pieceBoard[i][j] + "    ") ;
				}
				else {
					System.out.print("                        ");
				}
			}
			System.out.println();
		}
	}
	
	private void drawBoundries(Graphics g) {
		for(int i = 0; i<32; i++) {
				g.drawOval((int)piecePoints[i].getY()*100+5, (int)piecePoints[i].getX()*100+5 , 20, 20);
				g.drawOval((int)piecePoints[i].getY()*100+5, (int)piecePoints[i].getX()*100+95 , 20, 20);
				g.drawOval((int)piecePoints[i].getY()*100+95, (int)piecePoints[i].getX()*100+5 , 20, 20);
				g.drawOval((int)piecePoints[i].getY()*100+95, (int)piecePoints[i].getX()*100+95, 20, 20);
				
		}
	}
}
