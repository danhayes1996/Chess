package model.boardObjects;

import java.io.Serializable;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import view.gameroot.images.Images;

public abstract class BoardObject extends ImageView implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int x, y;
	protected int value;
	protected boolean whiteTeam;
	
	public BoardObject(int x, int y, boolean whiteTeam, Image image) {
		this.x = x;
		this.y = y;
		this.whiteTeam = whiteTeam;
		
		this.setImage(image);
		assertImage();
	}
	
	public void assertImage() {
		//determine which image to use
		if(getImage() == null) {
			switch(value) {
				case 1 : this.setImage(whiteTeam ? Images.PAWN_W : Images.PAWN_B); break;
				case 2 : this.setImage(whiteTeam ? Images.ROOK_W : Images.ROOK_B); break;
				case 3 : this.setImage(whiteTeam ? Images.KNIGHT_W : Images.KNIGHT_B); break;
				case 4 : this.setImage(whiteTeam ? Images.BISHOP_W : Images.BISHOP_B); break;
				case 5 : this.setImage(whiteTeam ? Images.QUEEN_W : Images.QUEEN_B); break;
				case 6 : this.setImage(whiteTeam ? Images.KING_W : Images.KING_B); break;
			}
		}
		this.setFitHeight(50);
		this.setPreserveRatio(true);
		this.setPickOnBounds(true); //allows mouse event on transparent parts of image
		
		setXOnBoard(x);
		setYOnBoard(y);
	}
	
	//dictates the rules on the directions and distance each BoardObject can move in
	public abstract boolean isValidMove(int x, int y, final BoardObject[] board);
	
	protected boolean hasPassedOverAnyBoardObject(int x, int y, final BoardObject[] board) {
		int translationX = (int)Math.signum(x - this.x);
		int translationY = (int)Math.signum(y - this.y);
		
		//check path besides end point
		for(int i = 1; i <= Math.max(Math.abs(this.x - x), Math.abs(this.y - y)) - 1; i++) {
			//check each space between the start pos to end pos to see if any pieces block the path
			if(board[this.x + (translationX * i) + (this.y + (translationY * i)) * 8] != null) 
				return true;
		}
		
		//check endpoint to see if the piece lands on a teammate (making the move invalid)
		if(board[x + y * 8] != null && board[x + y * 8].whiteTeam == whiteTeam) return true;
		return false;
	}
	
	//x corresponds to the tile to move to
	public void setXOnBoard(int x) {
		this.x = x;
		x *= 64;
		this.setX(x + (64 / 2) - (getWidth() / 2)); //centre image
	}
	
	public int getXOnBoard() {
		return x;
	}
	
	public void setYOnBoard(int y) {
		this.y = y;
		y *= 64;
		this.setY(y + (64 / 2) - (getHeight() / 2)); //centre image
	}

	public int getYOnBoard() {
		return y;
	}

	public boolean isWhite() {
		return whiteTeam;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getWidth() {
		return (int)this.getBoundsInParent().getWidth();
	}
	
	public int getHeight() {
		return (int) this.getFitHeight();
	}

	@Override
	public String toString() {
		return "BoardObject:[x=" + x + ", y=" + y + ", value=" + value + ", whiteTeam=" + whiteTeam + ", image=" + this.getImage() + "]";
	}
}
