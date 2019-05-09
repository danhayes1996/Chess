package view.settingsroot;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import view.settingsroot.settingspanes.GameSettingsPane;

//TODO: change to TreeView implementation?
public class SettingsList extends ListView<String> {

	private static final ObservableList<String> SETTINGS_LIST = FXCollections.<String>observableArrayList("Theme Settings", "Game Settings", "TEST SETTINGS");
	
	private static final Map<String, Pane> SETTINGS_PANES;
	static {
		SETTINGS_PANES = new HashMap<>();
		SETTINGS_PANES.put("Theme Settings", new Pane());
		SETTINGS_PANES.put("Game Settings", new GameSettingsPane());
		SETTINGS_PANES.put("TEST SETTINGS", new Pane());
	}
	
	public SettingsList() {
		this.setOrientation(Orientation.VERTICAL);
		this.getItems().addAll(SETTINGS_LIST);
	}

	public void setSelectedItem(String defaultSetting) {
		if(defaultSetting == null) defaultSetting = this.getItems().get(0); 
		this.getSelectionModel().select(defaultSetting);
	}
	
	public Node getSubSettingPane(String subSetting) {
		return SETTINGS_PANES.get(subSetting);
	}
	
	public void setMouseClickHandler(EventHandler<MouseEvent> e) {
		this.setOnMouseClicked(e);
	}
}
