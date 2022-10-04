package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Board.Board;
import GameManager.BlackPlayer;
import GameManager.BoardNode;
import GameManager.Computer;
import GameManager.WhitePlayer;
import Pieces.*;

public class ChessPanel extends JPanel{
	private Board board;
	
	private WhitePlayer wPlayer;
	private BlackPlayer bPlayer;
	
	
	private ArrayList<Point> piecePoints = new ArrayList<Point>();
	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	private boolean hold = false;
	private int currentPieceInd;
	private int currentPieceX;
	private int currentPieceY;
	private ArrayList<int[]> helperPoints;
	
	
	private ArrayList<int[]> moves;
	
	//Castle Buttons
	private JButton wShortCastle;
	private JButton wLongCastle;
	private JButton bShortCastle;
	private JButton bLongCastle;
	private JButton rookButton;
	private JButton knightButton;
	private JButton bishopButton;
	private JButton queenButton;
	
	// PieceImages
	private BufferedImage boardImage;
	private BufferedImage wP;
	private BufferedImage wR;
	private BufferedImage wN;	
	private BufferedImage wB;
	private BufferedImage wQ;
	private BufferedImage wK;
	private BufferedImage bP;
	private BufferedImage bR;	
	private BufferedImage bN;
	private BufferedImage bB;
	private BufferedImage bK;
	private BufferedImage bQ;
	
	public ChessPanel( ) {
		board = new Board();
	
		try {
			boardImage = ImageIO.read(new FileImageInputStream(new File("Assets/Board.png")));
		}
		catch(IOException ex) {
		}
		putPieceImages();
		wPlayer = new WhitePlayer(board);
		bPlayer = new BlackPlayer(board);
		ClickListener clickListener = new ClickListener();
		//DragListener dragListener= new DragListener();
		this.addMouseListener(clickListener);
		//this.addMouseMotionListener(dragListener);
		putPoints();
		initButtons();
	}
	
	private class ClickListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			//System.out.println("E x Loc  = " + e.getX() + " y Loc " + e.getY());
			for(int i = 0; i<piecePoints.size(); i++) {
				if (piecePoints.get(i) != null && (e.getY() > (int)piecePoints.get(i).getX()*100 && e.getY() <= (int)piecePoints.get(i).getX()*100+95 ) &&
						(e.getX() > (int)piecePoints.get(i).getY()*100 && e.getX() <= (int)piecePoints.get(i).getY()*100+95 )) {
					currentPieceInd = i;
					currentPieceX = pieces.get(i).getxLoc();
					currentPieceY = pieces.get(i).getyLoc();
					//System.out.println("Tuttum := " + i + " curr X " +  currentPieceX + " curr Y " + currentPieceY);
					
					// Change Board
					//board.getPieceBoard()[currentPieceX][currentPieceY].possibleMoves();
					helperPoints = board.getPieceBoard()[currentPieceX][currentPieceY].getPossibleMoves();
					
					
					if (board.getPieceBoard()[currentPieceX][currentPieceY].getPieceString().charAt(0) == 'w') {
						wPlayer.checkMove(board.getPieceBoard()[currentPieceX][currentPieceY], helperPoints, currentPieceX, currentPieceY);
					}
					
					else {
						bPlayer.checkMove(board.getPieceBoard()[currentPieceX][currentPieceY], helperPoints, currentPieceX, currentPieceY);
					}
					
					board.getPieceBoard()[currentPieceX][currentPieceY] = null;
					
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
				
				pieces.get(currentPieceInd).setyLoc((int)currPointFixed.x/100);
				pieces.get(currentPieceInd).setxLoc((int)currPointFixed.y/100);
				
				board.getPieceBoard()[pieces.get(currentPieceInd).getxLoc()][pieces.get(currentPieceInd).getyLoc()] = pieces.get(currentPieceInd);
				
				if (pieces.get(currentPieceInd).getxLoc() != currentPieceX 
						|| pieces.get(currentPieceInd).getyLoc() != currentPieceY) {
					if (pieces.get(currentPieceInd).getPieceString().charAt(0) == 'w') {
						board.makeBlackPawnDoubleMoveFalse();
					}
					else {
						board.makeWhitePawnDoubleMoveFalse();
					}
				}
				if (pieces.get(currentPieceInd).getPieceString().equals("wP") &&
						pieces.get(currentPieceInd).getxLoc() == 4 && currentPieceX == 6) {
					((Pawn) pieces.get(currentPieceInd)).setDoubleMove(true);
				}
				else if (pieces.get(currentPieceInd).getPieceString().equals("bP") &&
						pieces.get(currentPieceInd).getxLoc() == 3 && currentPieceX == 1) {
					((Pawn) pieces.get(currentPieceInd)).setDoubleMove(true);
				}
				else if (pieces.get(currentPieceInd).getPieceString().charAt(1) == 'R' &&
						(pieces.get(currentPieceInd).getxLoc() != currentPieceX || pieces.get(currentPieceInd).getyLoc() != currentPieceY) ) {
					((Rook) pieces.get(currentPieceInd)).setHasMoved(true);
				}
				else if (pieces.get(currentPieceInd).getPieceString().charAt(1) == 'K' &&
						(pieces.get(currentPieceInd).getxLoc() != currentPieceX || pieces.get(currentPieceInd).getyLoc() != currentPieceY) ) {
					((Şah) pieces.get(currentPieceInd)).setHasMoved(true);
				}
					
				
				
				repaint();
				putPoints();
				hold = false;
				helperPoints = null;
				System.out.println(board.calculateBoardPoint());
				System.out.println("White calstle " + board.getWhiteCastleMoves().size());
				//printPieceBoard();
				
				/*
				 * Delete...
				 */
				
				
				System.out.println("Bıraktımm");
				repaint();
				System.out.println("Çizdim");
				
				makeMove();
				/*
				BoardNode boardNode = new BoardNode(board);
				double returned= boardNode.minimax(4, -10000, 10000, false);
				System.out.println("MİNİMAX " + returned);
				
				
				for (BoardNode currNodes : boardNode.getChildNodes()) {
					if (currNodes.getChosen()) {
						board = currNodes.copyBoardObjectReturn();
					}
				}
				*/
				
				
				/*
				 * till here
				 */
				
			}
		}
		
	}
	
	public void makeMove() {
		BoardNode boardNode = new BoardNode(board);
		double returned= boardNode.minimax(4, -10000, 10000, false);
		System.out.println("MİNİMAX " + returned);
		
		
		for (BoardNode currNodes : boardNode.getChildNodes()) {
			if (currNodes.getChosen()) {
				board = currNodes.copyBoardObjectReturn();
			}
		}
		
		if (board.gameOver()) {
			
			if (board.whiteWon()) {
				JOptionPane.showMessageDialog(this, "You won.",
                        "INFORMATION",
                        JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(this, "You lost.",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			
			System.exit(0);
			
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
	public void repaint() {
		super.repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		printPieceBoard();
		g.drawImage(boardImage, 0, 0, 800, 800, this);
		drawPieces(g);
		drawHelperPoints(g);
		drawBoundries(g);
		drawHelperPointsPosMov(g);
	}
	
	
	private void putPoints() {
		//System.out.println("Koydum");
		Piece[][] pieceBoard = board.getPieceBoard();
		
		piecePoints.clear();
		pieces.clear();
		
		for (int i = 0; i < 8; i ++) {
			for (int j = 0; j < 8; j++) {
				if (pieceBoard[i][j] != null) {
					piecePoints.add(new Point(pieceBoard[i][j].getxLoc(), pieceBoard[i][j].getyLoc()));
					pieces.add( pieceBoard[i][j]);
				}
			}
		}
		
	}
	
	private void drawPieces(Graphics g) {
		Piece[][] pieceBoard = board.getPieceBoard();
		for (int i = 0; i < 8; i ++) {
			for (int j = 0; j < 8; j++) {
				if (pieceBoard[i][j] != null) {
					//g.drawImage(pieceBoard[i][j].getImage(), 15 + 100*j , 15 + 100*i, 80, 80, this);
					if (pieceBoard[i][j] instanceof Pawn) {
						if (pieceBoard[i][j].getPieceString().charAt(0) == 'w') {
							g.drawImage(wP, 15 + 100*j , 15 + 100*i, 80, 80, this);
						}
						else {
							g.drawImage(bP, 15 + 100*j , 15 + 100*i, 80, 80, this);
						}
					}
					else if (pieceBoard[i][j] instanceof Rook) {
						if (pieceBoard[i][j].getPieceString().charAt(0) == 'w') {
							g.drawImage(wR, 15 + 100*j , 15 + 100*i, 80, 80, this);
						}
						else {
							g.drawImage(bR, 15 + 100*j , 15 + 100*i, 80, 80, this);
						}
					}
					else if (pieceBoard[i][j] instanceof Knight) {
						if (pieceBoard[i][j].getPieceString().charAt(0) == 'w') {
							g.drawImage(wN, 15 + 100*j , 15 + 100*i, 80, 80, this);
						}
						else {
							g.drawImage(bN, 15 + 100*j , 15 + 100*i, 80, 80, this);
						}
					}
					else if (pieceBoard[i][j] instanceof Bishop) {
						if (pieceBoard[i][j].getPieceString().charAt(0) == 'w') {
							g.drawImage(wB, 15 + 100*j , 15 + 100*i, 80, 80, this);
						}
						else {
							g.drawImage(bB, 15 + 100*j , 15 + 100*i, 80, 80, this);
						}
					}
					else if (pieceBoard[i][j] instanceof Queen) {
						if (pieceBoard[i][j].getPieceString().charAt(0) == 'w') {
							g.drawImage(wQ, 15 + 100*j , 15 + 100*i, 80, 80, this);
						}
						else {
							g.drawImage(bQ, 15 + 100*j , 15 + 100*i, 80, 80, this);
						}
					}
					else if (pieceBoard[i][j] instanceof Şah) {
						if (pieceBoard[i][j].getPieceString().charAt(0) == 'w') {
							g.drawImage(wK, 15 + 100*j , 15 + 100*i, 80, 80, this);
						}
						else {
							g.drawImage(bK, 15 + 100*j , 15 + 100*i, 80, 80, this);
						}
					}
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
	
	private void drawHelperPointsPosMov(Graphics g) {
		if (moves != null) {
			//System.out.println(helperPoints);
			for (int[] currPoint: moves) {
				g.setColor(Color.red);
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
		for(int i = 0; i< piecePoints.size(); i++) {
				g.drawOval((int)piecePoints.get(i).getY()*100+5, (int)piecePoints.get(i).getX()*100+5 , 20, 20);
				g.drawOval((int)piecePoints.get(i).getY()*100+5, (int)piecePoints.get(i).getX()*100+95 , 20, 20);
				g.drawOval((int)piecePoints.get(i).getY()*100+95, (int)piecePoints.get(i).getX()*100+5 , 20, 20);
				g.drawOval((int)piecePoints.get(i).getY()*100+95, (int)piecePoints.get(i).getX()*100+95, 20, 20);
				
		}
	}
	
	private void putPieceImages() {
		try {
			wP = ImageIO.read(new FileImageInputStream(new File("Assets/wP.png")));
		}
		catch(IOException ex) {
		}
		// Rook
		try {
			wR= ImageIO.read(new FileImageInputStream(new File("Assets/wR.png")));
		}
		catch(IOException ex) {
		}
		// Knight
		try {
			wN = ImageIO.read(new FileImageInputStream(new File("Assets/wN.png")));
		}
		catch(IOException ex) {
		}
		// Bishop
		try {
			wB = ImageIO.read(new FileImageInputStream(new File("Assets/wB.png")));
		}
		catch(IOException ex) {
		}
		// Quuen and King
		try {
			wQ = ImageIO.read(new FileImageInputStream(new File("Assets/wQ.png")));
			wK = ImageIO.read(new FileImageInputStream(new File("Assets/wK.png")));
		}
		catch(IOException ex) {
		}
		// Pawn
		try {
			bP = ImageIO.read(new FileImageInputStream(new File("Assets/bP.png")));
		}
		catch(IOException ex) {
		}
		// Rook
		try {
			bR = ImageIO.read(new FileImageInputStream(new File("Assets/bR.png")));
		}
		catch(IOException ex) {
		}
		// Knight
		try {
			bN = ImageIO.read(new FileImageInputStream(new File("Assets/bN.png")));
		}
		catch(IOException ex) {
		}
		// Bishop
		try {
			bB = ImageIO.read(new FileImageInputStream(new File("Assets/bB.png")));
		}
		catch(IOException ex) {
		}
		// Quuen and King
		try {
			bQ = ImageIO.read(new FileImageInputStream(new File("Assets/bQ.png")));
			bK = ImageIO.read(new FileImageInputStream(new File("Assets/bK.png")));
		}
		catch(IOException ex) {
		}
	}
	
	private void initButtons() {
		/*
		this.saveButton = new JButton("Save");
		saveButton.setFocusable(false);
		saveButton.addActionListener(this);
		saveButton.setSize(100,35);
		saveButton.setLocation(860,90);
		saveButton.setFont(font);
		add(saveButton);
		 */
		
		this.setLayout(null);
		
		ButtonListener bListener = new ButtonListener();
		
		wShortCastle = new JButton("White Short Castle");
		wShortCastle.setFocusable(false);
		wShortCastle.setSize(200, 30);
		add(wShortCastle);
		wShortCastle.setLocation(850, 700);
		
		
		
		wLongCastle = new JButton("White Long Castle");
		wLongCastle.setFocusable(false);
		wLongCastle.setSize(200, 30);
		add(wLongCastle);
		wLongCastle.setLocation(850, 750);
		
		bShortCastle = new JButton("Black Short Castle");
		bShortCastle.setFocusable(false);
		bShortCastle.setSize(200, 30);
		add(bShortCastle);
		bShortCastle.setLocation(850, 0);
		
		
		bLongCastle = new JButton("Black Long Castle");
		bLongCastle.setFocusable(false);
		bLongCastle.setSize(200, 30);
		add(bLongCastle);
		bLongCastle.setLocation(850, 50);
		
		rookButton = new JButton("Rook");
		rookButton.setFocusable(false);
		rookButton.setSize(100, 30);
		add(rookButton);
		rookButton.setLocation(850, 300);
		
		knightButton = new JButton("Knight");
		knightButton.setFocusable(false);
		knightButton.setSize(100, 30);
		add(knightButton);
		knightButton.setLocation(850, 350);
		
		bishopButton = new JButton("Bishop");
		bishopButton.setFocusable(false);
		bishopButton.setSize(100, 30);
		add(bishopButton);
		bishopButton.setLocation(850, 400);
		
		queenButton = new JButton("Queen");
		queenButton.setFocusable(false);
		queenButton.setSize(100, 30);
		add(queenButton);
		queenButton.setLocation(850, 450);
		
		wShortCastle.addActionListener(bListener);
		wLongCastle.addActionListener(bListener);
		bShortCastle.addActionListener(bListener);
		bLongCastle.addActionListener(bListener);
		rookButton.addActionListener(bListener);
		knightButton.addActionListener(bListener);
		bishopButton.addActionListener(bListener);
		queenButton.addActionListener(bListener);
	}
	
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == wShortCastle) {
				System.out.println("WHite Short Castle");
				ArrayList<int[]> wCastleMoves = board.getWhiteCastleMoves();
				if ((wCastleMoves.size() == 1  && wCastleMoves.get(0)[1] == 6 ) ||
						(wCastleMoves.size() == 2  && wCastleMoves.get(0)[1] == 6 )) {
					
					board.getPieceBoard()[7][6] = board.getPieceBoard()[7][4];
					board.getPieceBoard()[7][4] = null;
					board.getPieceBoard()[7][5] = board.getPieceBoard()[7][7];
					board.getPieceBoard()[7][7] = null;
					((Şah) board.getPieceBoard()[7][6]).setHasMoved(true);
					((Rook) board.getPieceBoard()[7][5]).setHasMoved(true);

					board.getPieceBoard()[7][6].setxLoc(7); board.getPieceBoard()[7][6].setyLoc(6);
					board.getPieceBoard()[7][5].setxLoc(7); board.getPieceBoard()[7][5].setyLoc(5);
				}
			}
			if (e.getSource() == wLongCastle) {
				System.out.println("White Long Castle");
				ArrayList<int[]> wCastleMoves = board.getWhiteCastleMoves();
				if ((wCastleMoves.size() == 1  && wCastleMoves.get(0)[1] == 2 ) ||
						(wCastleMoves.size() == 2  && wCastleMoves.get(0)[1] == 2 )) {
					
					board.getPieceBoard()[7][2] = board.getPieceBoard()[7][4];
					board.getPieceBoard()[7][4] = null;
					board.getPieceBoard()[7][3] = board.getPieceBoard()[7][0];
					board.getPieceBoard()[7][0] = null;
					((Şah) board.getPieceBoard()[7][2]).setHasMoved(true);
					((Rook) board.getPieceBoard()[7][3]).setHasMoved(true);
					
					board.getPieceBoard()[7][2].setxLoc(7); board.getPieceBoard()[7][2].setyLoc(2);
					board.getPieceBoard()[7][3].setxLoc(7); board.getPieceBoard()[7][3].setyLoc(3);
				}
			}
			if (e.getSource() == bShortCastle) {
				System.out.println("Black Short Castle");
				ArrayList<int[]> wCastleMoves = board.getBlackCastleMoves();
				if ((wCastleMoves.size() == 1  && wCastleMoves.get(0)[1] == 6 ) ||
						(wCastleMoves.size() == 2  && wCastleMoves.get(0)[1] == 6 )) {
					
					board.getPieceBoard()[0][6] = board.getPieceBoard()[0][4];
					board.getPieceBoard()[0][4] = null;
					board.getPieceBoard()[0][5] = board.getPieceBoard()[0][7];
					board.getPieceBoard()[0][7] = null;
					((Şah) board.getPieceBoard()[0][6]).setHasMoved(true);
					((Rook) board.getPieceBoard()[0][5]).setHasMoved(true);
					
					board.getPieceBoard()[0][6].setxLoc(0); board.getPieceBoard()[0][6].setyLoc(6);
					board.getPieceBoard()[0][5].setxLoc(0); board.getPieceBoard()[0][5].setyLoc(5);
				}
			}
			if (e.getSource() == bLongCastle) {
				System.out.println("Black Long Castle");
				ArrayList<int[]> wCastleMoves = board.getBlackCastleMoves();
				if ((wCastleMoves.size() == 1  && wCastleMoves.get(0)[1] == 2 ) ||
						(wCastleMoves.size() == 2  && wCastleMoves.get(0)[1] == 2 )) {
					
					board.getPieceBoard()[0][2] = board.getPieceBoard()[0][4];
					board.getPieceBoard()[0][4] = null;
					board.getPieceBoard()[0][3] = board.getPieceBoard()[0][0];
					board.getPieceBoard()[0][0] = null;
					((Şah) board.getPieceBoard()[0][2]).setHasMoved(true);
					((Rook) board.getPieceBoard()[0][3]).setHasMoved(true);
					
					board.getPieceBoard()[0][2].setxLoc(0); board.getPieceBoard()[0][2].setyLoc(2);
					board.getPieceBoard()[0][3].setxLoc(0); board.getPieceBoard()[0][3].setyLoc(3);
				}
			}
			if (e.getSource() == rookButton ) {
				for (int i = 0; i < 8; i++) {
					if (board.getPieceBoard()[0][i] != null && board.getPieceBoard()[0][i].getPieceString().equals("wP")) {
						board.getPieceBoard()[0][i] =  new Rook(board, 0, i, "wR");
						((Rook) board.getPieceBoard()[0][i]).setHasMoved(true);
						break;
					}
					if (board.getPieceBoard()[7][i] != null && board.getPieceBoard()[7][i].getPieceString().equals("bP")) {
						board.getPieceBoard()[7][i] =  new Rook(board, 7, i, "bR");
						((Rook) board.getPieceBoard()[7][i]).setHasMoved(true);
						break;
					}
				}
			}
			if (e.getSource() == knightButton ) {
				for (int i = 0; i < 8; i++) {
					if (board.getPieceBoard()[0][i] != null && board.getPieceBoard()[0][i].getPieceString().equals("wP")) {
						board.getPieceBoard()[0][i] =  new Knight(board, 0, i, "wN");
						break;
					}
					if (board.getPieceBoard()[7][i] != null && board.getPieceBoard()[7][i].getPieceString().equals("bP")) {
						board.getPieceBoard()[7][i] =  new Knight(board, 7, i, "bN");
						break;
					}
				}
			}
			if (e.getSource() == bishopButton ) {
				for (int i = 0; i < 8; i++) {
					if (board.getPieceBoard()[0][i] != null && board.getPieceBoard()[0][i].getPieceString().equals("wP")) {
						board.getPieceBoard()[0][i] =  new Bishop(board, 0, i, "wB");
						break;
					}
					if (board.getPieceBoard()[7][i] != null && board.getPieceBoard()[7][i].getPieceString().equals("bP")) {
						board.getPieceBoard()[7][i] =  new Bishop(board, 7, i, "bB");
						break;
					}
				}
			}
			if (e.getSource() == queenButton ) {
				for (int i = 0; i < 8; i++) {
					if (board.getPieceBoard()[0][i] != null && board.getPieceBoard()[0][i].getPieceString().equals("wP")) {
						board.getPieceBoard()[0][i] =  new Queen(board, 0, i, "wQ");
						break;
					}
					if (board.getPieceBoard()[7][i] != null && board.getPieceBoard()[7][i].getPieceString().equals("bP")) {
						board.getPieceBoard()[7][i] =  new Queen(board, 7, i, "bQ");
						break;
					}
				}
			}
			repaint();
			BoardNode boardNode = new BoardNode(board);
			double returned= boardNode.minimax(4, -10000, 10000, false);
			System.out.println("MİNİMAX " + returned);
			
			
			for (BoardNode currNodes : boardNode.getChildNodes()) {
				if (currNodes.getChosen()) {
					board = currNodes.copyBoardObjectReturn();
				}
			}
			repaint();
			putPoints();
		}
	}
	
	private void printPieceBoard(Board board) {
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
	
}
