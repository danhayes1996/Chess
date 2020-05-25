package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.Model;
import view.settingsroot.SettingsList;
import view.settingsroot.SettingsRootPane;

public class SettingsController {

	private SettingsRootPane view;
	private Model model;
	
	private Controller c;
	
	public SettingsController(SettingsRootPane view, Model model) {
		this.view = view;
		this.model = model;
		
		assignHandlers();
	}
	
	public void initialize(Controller c) {
		this.c = c;
	}
	
	private void assignHandlers() {
		view.getSettingsList().setMouseClickHandler(new SettingsListMouseClickHandler());
	}
	
	
	private class SettingsListMouseClickHandler implements EventHandler<MouseEvent> {
		public void handle(MouseEvent e) {
			SettingsList source = (SettingsList) e.getSource();
			System.out.println("CLICKED ON: " + source.getSelectionModel().getSelectedItem());
			//change to the correct sub pane
			view.setSettingsSubPane(source.getSelectionModel().getSelectedItem());
		}
	}
}
