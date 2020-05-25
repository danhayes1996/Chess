package view.settingsroot.settingspanes;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

//TODO: implement game settings e.g. viable moves indicator (for player 1 & player 2)
public class GameSettingsPane extends Pane {

	private Label lWhite, lBlack;
	private CheckBox cbViableMovesW, cbViableMovesB;
	
	public GameSettingsPane() {
//		this.setMinSize(200, 400);
		
		//TODO: put into separate class
		HBox hbox = new HBox();
		hbox.setSpacing(5);
		
		VBox vb1 = new VBox();
		vb1.setSpacing(5);
		lWhite = new Label("White Team:"); //TODO: get player1 & player2 names from model
		lBlack = new Label("Black Team:");
		vb1.getChildren().addAll(lWhite, lBlack);
		
		VBox vb2 = new VBox();
		vb2.setSpacing(5);
		cbViableMovesB = new CheckBox();
		cbViableMovesW = new CheckBox();
		vb2.getChildren().addAll(cbViableMovesW, cbViableMovesB);
		
		hbox.getChildren().addAll(vb1, vb2);
		
		TitledPane tp = new TitledPane("Viable Moves Indicator", hbox);
		tp.setCollapsible(false);
		
		this.getChildren().add(tp);
	}

}
