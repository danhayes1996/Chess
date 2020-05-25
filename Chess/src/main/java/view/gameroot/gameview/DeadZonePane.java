package view.gameroot.gameview;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.boardObjects.BoardObject;

public class DeadZonePane extends VBox {

	private DeadPiecePane whitesDeadPane, blacksDeadPane;
	private Label lPlayer1, lPlayer2;
	
	public DeadZonePane() {
		this.setMaxWidth(250);
		this.setPadding(new Insets(10));
		this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
		
		whitesDeadPane = new DeadPiecePane();
		blacksDeadPane = new DeadPiecePane();
		
		lPlayer1 = new Label("Player1");
		lPlayer2 = new Label("Player2");
		
		//makes DeadPiecePanes fill height of the window evenly
		VBox.setVgrow(whitesDeadPane, Priority.ALWAYS);
		VBox.setVgrow(blacksDeadPane, Priority.ALWAYS);
		
		this.getChildren().addAll(lPlayer2, blacksDeadPane, whitesDeadPane, lPlayer1);
	}
	
	public void clearDeadBoardObjects() {
		whitesDeadPane.clearPieces();
		blacksDeadPane.clearPieces();
	}
	
	public void addDeadBoardObject(BoardObject bo) {
		if(bo.isWhite()) whitesDeadPane.addPiece(bo);
		else blacksDeadPane.addPiece(bo);
	}

	public void setPlayerNames(String player1, String player2) {
		lPlayer1.setText(player1);
		lPlayer2.setText(player2);
	}
	
	public Label getPlayer1Label() {
		return lPlayer1;
	}
	
	public Label getPlayer2Label() {
		return lPlayer2;
	}
}
