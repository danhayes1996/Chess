package view.gameroot.menubar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MyMenuBar extends MenuBar {

	private Menu mFile, mOptions, mHelp;
	private MenuItem miSave, miMainMenu; //mFile MenuItems
	private MenuItem miSettings;

	public MyMenuBar() {
		mFile = new Menu("_File");
		miSave = new MenuItem("_Save");
		miMainMenu = new MenuItem("Exit to _Main Menu");
		mFile.getItems().addAll(miSave, miMainMenu);
		
		mOptions = new Menu("_Options");
		miSettings = new MenuItem("_Settings");
		mOptions.getItems().add(miSettings);
		
		
		mHelp = new Menu("_Help");
		
		this.getMenus().addAll(mFile, mOptions, mHelp);
	}
	
	public void setSaveHandler(EventHandler<ActionEvent> e) {
		miSave.setOnAction(e);
	}
	
	public void setMainMenuHandler(EventHandler<ActionEvent> e) {
		miMainMenu.setOnAction(e);
	}
	
	public void setSettingsClickHandler(EventHandler<ActionEvent> e) {
		miSettings.setOnAction(e);
	}
}
