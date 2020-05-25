package view.mainmenu;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

//TODO: add background colour & text at main menu title
public class MainRootPane extends VBox {

	private Button bContinue, bPVP, bPVBot;
	
	public MainRootPane() {
		this.setPrefSize(512, 512);
		this.setAlignment(Pos.BOTTOM_CENTER);
		this.setSpacing(50);
		this.setPadding(new Insets(0, 0, 50, 0));

		bContinue = new Button("Continue"); //only show if a previous match exists
		bPVP = new Button("Player VS Player");
		bPVBot = new Button("Player VS Bot");
		
		this.getChildren().addAll(bContinue, bPVP, bPVBot);
	}
	
	public void setContinueButtonClickedHandler(EventHandler<ActionEvent> e) {
		bContinue.setOnAction(e);
	}
	
	public void setPVPButtonClickedHandler(EventHandler<ActionEvent> e) {
		bPVP.setOnAction(e);
	}
	
	public void setPVBotButtonClickedHandler(EventHandler<ActionEvent> e) {
		bPVBot.setOnAction(e);
	}
}
