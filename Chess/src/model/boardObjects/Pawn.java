package model.boardObjects;

import view.gameroot.images.Images;

public class Pawn extends BoardObject {

	private boolean firstMove;
	
	public Pawn(int x, int y, boolean whiteTeam) {
		super(x, y, whiteTeam, whiteTeam ? Images.PAWN_W : Images.PAWN_B);
		value = 1;
		firstMove = true;
	}

	public boolean isValidMove(int x, int y, final BoardObject[] board) {
		int prevX = this.x;
		int prevY = this.y;
		if(x == prevX && y == prevY) return false;
		
		int translateY = y - prevY;
		//checks if moving towards opponent
		if(whiteTeam && translateY < 0 || !whiteTeam && translateY > 0) { 
			
			//checks if moving diagonally
			if(Math.abs(translateY) == 1 && Math.abs(prevX - x) == 1) {
				//check if opponent is on that space (that makes diagonal moves valid)
				BoardObject bo = board[x + y * 8];
				if(bo != null && bo.whiteTeam != whiteTeam) {
					return !hasPassedOverAnyBoardObject(x, y, board);
				} else return false; //moved diagonally into empty space or a team mate
			}
			
			//checks if the forward move made is valid depending on if its the piece's first move or not 
			if(Math.abs(prevX - x) == 0) {
				if((firstMove && Math.abs(translateY) <= 2) || 
						(!firstMove && Math.abs(translateY) == 1)) {
					//checks if its destination is blocked by another piece (can only take pieces from diagonal moves)
					if(board[x + y * 8] != null) return false;
					return !hasPassedOverAnyBoardObject(x, y, board);
				}
			}
		}
		return false;
	}

	public boolean isFistMove() {
		return firstMove;
	}
	
	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}

}
