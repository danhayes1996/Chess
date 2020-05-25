package model.boardobjects;

import view.gameroot.images.Images;

public class Knight extends BoardObject {

	public Knight(int x, int y, boolean whiteTeam) {
		super(x, y, whiteTeam, whiteTeam ? Images.KNIGHT_W : Images.KNIGHT_B);
		value = 3;
	}

	public boolean isValidMove(int x, int y, final BoardObject[] board) {
		int prevX = this.x;
		int prevY = this.y;
		if(x == prevX && y == prevY) return false;
		
		//get the x and y distance the piece wants to move
		int translateX = Math.abs(prevX - x);
		int translateY = Math.abs(prevY - y);
		
		//check if the piece is moving in an 'L' shape
		if((translateX == 2 && translateY == 1) || (translateX == 1 && translateY == 2)) {
			BoardObject bo = board[x + y * 8];
			//check if bo is empty or an opponent (making the move valid)
			if(bo == null || bo.whiteTeam != whiteTeam) return true;
		}
		return false;
	}
}
