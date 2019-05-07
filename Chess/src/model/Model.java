package model;

import java.io.Serializable;
import java.util.ArrayList;

import model.boardObjects.Bishop;
import model.boardObjects.BoardObject;
import model.boardObjects.King;
import model.boardObjects.Knight;
import model.boardObjects.Pawn;
import model.boardObjects.Queen;
import model.boardObjects.Rook;

public class Model implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private BoardObject[] board;
	private ArrayList<BoardObject> deadPieces;
	private boolean whitesTurn;
	
	private String p1Name, p2Name;
	
	public Model() {
		deadPieces = new ArrayList<>();
		whitesTurn = true;
		board = new BoardObject[8 * 8];
		
		p1Name = "Player1";
		p2Name = "Player2";
	}
	
	public void createNewBoard() {
		//remove all peices from the board
		for(int i = 0; i < 8 * 8; i++) board[i] = null;
		
		//pawns
		for(int i = 0; i < 8; i++) {
			board[i + 1 * 8] = new Pawn(i, 1, false);
			board[i + 6 * 8] = new Pawn(i, 6, true);
		}
		
		//rooks
		for(int i = 0; i < 2; i++) {
			board[i * 7] = new Rook(i * 7, 0, false);
			board[(i * 7) + 7 * 8] = new Rook(i * 7, 7, true);
		}
		
		//knights
		for(int i = 0; i < 2; i++) {
			board[1 + i * 5] = new Knight(1 + i * 5, 0, false);
			board[(1 + i * 5) + 7 * 8] = new Knight(1 + i * 5, 7, true);
		}
		
		//knights
		for(int i = 0; i < 2; i++) {
			board[2 + i * 3] = new Bishop(2 + i * 3, 0, false);
			board[(2 + i * 3) + 7 * 8] = new Bishop(2 + i * 3, 7, true);
		}
		
		//queens and kings
		board[3] = new Queen(3, 0, false);
		board[3 + 7 * 8] = new Queen(3, 7, true);
		board[4] = new King(4, 0, false);
		board[4 + 7 * 8] = new King(4, 7, true);
		
		//clear dead piece array
		deadPieces.clear();
	}
	
	public void removePiece(BoardObject bo) {
		board[bo.getXOnBoard() + bo.getYOnBoard() * 8] = null;
		deadPieces.add(bo);
	}
	
	public BoardObject[] getBoard() {
		return board;
	}
	
	public ArrayList<BoardObject> getDeadPieces() {
		return deadPieces;
	}
	
	public boolean isWhitesTurn() {
		return whitesTurn;
	}
	
	public void switchTurns() {
		whitesTurn = !whitesTurn;
	}

	public String getPlayer1Name() {
		return p1Name;
	}

	public void setPlayer1Name(String p1Name) {
		p1Name = p1Name.trim();
		if(!p1Name.isEmpty()) this.p1Name = p1Name;
	}

	public String getPlayer2Name() {
		return p2Name;
	}

	public void setPlayer2Name(String p2Name) {
		p2Name = p2Name.trim();
		if(!p2Name.isEmpty()) this.p2Name = p2Name;
	}
}
