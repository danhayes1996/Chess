package view.settingsroot;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class SettingsView extends HBox {

	private SettingsList settingsList;
	private Pane settingsPane;
	
	public SettingsView() {
//		this.setPadding(new Insets(10));
		this.setSpacing(10);
		
		settingsList = new SettingsList();
		settingsPane = new Pane();
		
		this.getChildren().addAll(settingsList, settingsPane);
	}
	
	public SettingsList getSettingsList() {
		return settingsList;
	}
	
	public Pane getSettingsPane() {
		return settingsPane;
	}
}
