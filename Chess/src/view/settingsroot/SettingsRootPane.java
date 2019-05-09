package view.settingsroot;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class SettingsRootPane extends VBox {

	private SettingsView settingsView;
	private SettingsButtonPane buttonPane;
	
	public SettingsRootPane(String defaultSetting) {
//		this.setMaxSize(100, 100);
		this.setPadding(new Insets(10));
		this.setSpacing(10);
		
		settingsView = new SettingsView();
		buttonPane = new SettingsButtonPane();
		
		settingsView.getSettingsList().setSelectedItem(defaultSetting);
		setSettingsSubPane(defaultSetting);
		
		this.getChildren().addAll(settingsView, buttonPane);
	}
	
	//removes old sub pane then adds the new sub pane
	public void setSettingsSubPane(String subSetting) {
		settingsView.getSettingsPane().getChildren().clear();
		settingsView.getSettingsPane().getChildren().add(settingsView.getSettingsList().getSubSettingPane(subSetting));
	}

	public SettingsList getSettingsList() {
		return settingsView.getSettingsList();
	}
	
}
