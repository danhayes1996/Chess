package model.boardobjects;

import view.gameroot.images.Images;

public class Bishop extends BoardObject {

	public Bishop(int x, int y, boolean whiteTeam) {
		super(x, y, whiteTeam, whiteTeam ? Images.BISHOP_W : Images.BISHOP_B);
		value = 4;
	}

	public boolean isValidMove(int x, int y, final BoardObject[] board) {
		int prevX = this.x;
		int prevY = this.y;
		if(x == prevX && y == prevY) return false;
		
		//checks if moving diagonally
		if(Math.abs(x - prevX) == Math.abs(y - prevY)) {
			return !hasPassedOverAnyBoardObject(x, y, board); 
		}
		return false;
	}
}
