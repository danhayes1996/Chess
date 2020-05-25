package view.gameroot.gameview;

import javafx.scene.layout.HBox;

public class GameView extends HBox {

	private BoardPane boardPane;
	private DeadZonePane dz;
	
	public GameView() {
		this.setSpacing(10);
		boardPane = new BoardPane();
		dz = new DeadZonePane();
		
		this.getChildren().addAll(boardPane, dz);
	}
	
	
	public BoardPane getBoardPane() {
		return boardPane;
	}
	
	public DeadZonePane getDeadZonePane() {
		return dz;
	}
}
