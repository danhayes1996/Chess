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
	
	private Player player1, player2;
	
	public Model() {
		deadPieces = new ArrayList<>();
		whitesTurn = true;
		board = new BoardObject[8 * 8];
		
		player1 = new Player("Player1");
		player2 = new Player("Player2");
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
	
	//returns the player based on whose turn it is
	public Player getPlayerByTurn() {
		return whitesTurn ? player1 : player2;
	}
	
	public Player getPlayer1() {
		return player1;
	}
	
	public Player getPlayer2() {
		return player2;
	}
	
}
