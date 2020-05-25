package model.boardobjects;

import view.gameroot.images.Images;

public class Queen extends BoardObject {

	public Queen(int x, int y, boolean whiteTeam) {
		super(x, y, whiteTeam, whiteTeam ? Images.QUEEN_W : Images.QUEEN_B);
		value = 5;
	}

	public boolean isValidMove(int x, int y, final BoardObject[] board) {
		int prevX = this.x;
		int prevY = this.y;
		if(x == prevX && y == prevY) return false;

		//checks if moving diagonally or in a single direction (e.g. isn't move 2 left and 1 down)
		if((Math.abs(x - prevX) == Math.abs(y - prevY)) || 
				(x - prevX) == 0 || (y - prevY) == 0) {
			return !hasPassedOverAnyBoardObject(x, y, board);
		}
		return false;
	}
}
