package view.gameroot.gameview;

import javafx.scene.layout.HBox;

public class GameView extends HBox {

	private Board board;
	private DeadZonePane dz;
	
	public GameView() {
		this.setSpacing(10);
		board = new Board();
		dz = new DeadZonePane();
		
		this.getChildren().addAll(board, dz);
	}
	
	
	public Board getBoard() {
		return board;
	}
	
	public DeadZonePane getDeadZonePane() {
		return dz;
	}
}
