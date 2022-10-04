package UI;

import javax.swing.JFrame;

public class ChessFrame extends JFrame{
	
	private ChessPanel chessPanel;
	
	public ChessFrame() {
		chessPanel = new ChessPanel();
		chessPanel.setSize(830,830);
		chessPanel.requestFocus();
		chessPanel.setFocusable(true);
		chessPanel.setFocusTraversalKeysEnabled(false);
		add(chessPanel);
	}
	
	
}
