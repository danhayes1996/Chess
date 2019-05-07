package view.gameroot;

import javafx.scene.layout.VBox;
import view.gameroot.gameview.Board;
import view.gameroot.gameview.DeadZonePane;
import view.gameroot.gameview.GameView;
import view.gameroot.meubar.MyMenuBar;

public class GameRootPane extends VBox {
	
	private MyMenuBar mb;
	private GameView gv;
	
	public GameRootPane() {
		mb = new MyMenuBar();
		gv = new GameView();
		
		this.getChildren().addAll(mb, gv);
	}
	
	public void switchTurns(boolean whitesTurn) {
		//TODO: sort out CSS Error parsing warning
		gv.getDeadZonePane().getPlayer1Label().setStyle("-fx-font-weight: " + (whitesTurn ? "bold;" : "regular;"));
		gv.getDeadZonePane().getPlayer2Label().setStyle("-fx-font-weight: " + (whitesTurn ? "regular;" : "bold;"));
	}
	
	public void setPlayerNames(String player1, String player2) {
		gv.getDeadZonePane().setPlayerNames(player1, player2);
	}
	
	public GameView getGameView() {
		return gv;
	}
	
	public Board getBoard() {
		return gv.getBoard();
	}
	
	public DeadZonePane getDeadZonePane() {
		return gv.getDeadZonePane();
	}
	
	public MyMenuBar getMenuBar() {
		return mb;
	}
}
