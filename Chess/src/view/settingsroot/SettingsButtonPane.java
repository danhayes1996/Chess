package view.settingsroot;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class SettingsButtonPane extends HBox {

	private Button bApplyClose, bCancel;
	
	public SettingsButtonPane() {
		this.setSpacing(10);
		this.setAlignment(Pos.CENTER_RIGHT);
		
		bApplyClose = new Button("Apply & Close");
		bCancel = new Button("Cancel");
		
		this.getChildren().addAll(bApplyClose, bCancel);
	}
	
	public void setApplyCloseButtonClickHandler(EventHandler<ActionEvent> e) {
		bApplyClose.setOnAction(e);
	}
	
	public void setCancelButtonClickHandler(EventHandler<ActionEvent> e) {
		bCancel.setOnAction(e);
	}
}
