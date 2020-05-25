package model.boardobjects;

import view.gameroot.images.Images;

public class Rook extends BoardObject {

	public Rook(int x, int y, boolean whiteTeam) {
		super(x, y, whiteTeam, whiteTeam ? Images.ROOK_W : Images.ROOK_B);
		value = 2;
	}

	public boolean isValidMove(int x, int y, final BoardObject[] board) {
		int prevX = this.x;
		int prevY = this.y;
		if(x == prevX && y == prevY) return false;
		
		//if moving in the x and y axis
		if(x - prevX != 0 && y - prevY != 0) return false;
		else return !hasPassedOverAnyBoardObject(x, y, board);
	}
}
