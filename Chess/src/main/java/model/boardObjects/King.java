package model.boardObjects;

import view.gameroot.images.Images;

public class King extends BoardObject {

	public King(int x, int y, boolean whiteTeam) {
		super(x, y, whiteTeam, whiteTeam ? Images.KING_W : Images.KING_B);
		value = 6;
	}

	public boolean isValidMove(int x, int y, final BoardObject[] board) {
		int prevX = this.x;
		int prevY = this.y;
		if(x == prevX && y == prevY) return false;

		//checks if moving only moving 1 space in any direction
		if(Math.abs(x - prevX) <= 1 && Math.abs(y - prevY) <= 1) {
			return !hasPassedOverAnyBoardObject(x, y, board);
		}
		return false;
	}
}
