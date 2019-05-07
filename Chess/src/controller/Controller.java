package controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Model;
import view.gameroot.GameRootPane;
import view.mainmenu.MainRootPane;

public class Controller {

	private Stage stage;

	private Model model;
	
	private MainRootPane mainRoot;
	private GameRootPane gameRoot;
	
	private GameController gc;
	private MainMenuController mmc;
	
	public Controller(MainRootPane view, Model model) { 
		this.stage = (Stage) view.getScene().getWindow();
		this.mainRoot = view;
		this.model = model;
		
		this.gameRoot = new GameRootPane();
		this.gc = new GameController(gameRoot, model);
		this.mmc = new MainMenuController(mainRoot, model);
		initalize();
		
		assignHandlers();
	}
	
	private void initalize() {
		gc.initalize(this);
		mmc.initalize(this);
	}
	
	private void assignHandlers() {
		stage.setOnCloseRequest(new WindowCloseHandler());
	}
	
	public void changeScene(Parent root) {
		Scene scene = root.getScene();
		if(scene == null) scene = new Scene(root);
		stage.setScene(scene);
		stage.centerOnScreen();
	}
	
	public void setModel(Model model) {
		this.model = model;
		gc.setModel(model);
		mmc.setModel(model);
	}
	
	public MainMenuController getMainMenuController() {
		return mmc;
	}
	
	public GameController getGameController() {
		return gc;
	}
	
	public Alert createAlert(String title, AlertType alertType, String content, ButtonType... buttons) {
		Alert a = new Alert(alertType, content, buttons);
		a.setTitle(title);
		return a;
	}
	
	public TextInputDialog createTextInputDialog(String title, String header, String content, String defaultValue, Node graphic) {
		TextInputDialog tid = new TextInputDialog(defaultValue);
		tid.setTitle(title);
		tid.setHeaderText(header);
		tid.setContentText(content);
		tid.setGraphic(graphic);
		return tid;
	}
	
	//---------------------------- WINDOW HANDLERS ----------------------------
	private class WindowCloseHandler implements EventHandler<WindowEvent> {
		public void handle(WindowEvent e) {
			System.out.println("CLOSE REQUEST");
			//show save alert if we are on the game root pane
			if(stage.getScene().getRoot().equals(gc.getRoot())) {
				Alert saveAlert = createAlert("Save?", AlertType.CONFIRMATION, "Do you wish to save the current game?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
				ButtonType result = saveAlert.showAndWait().orElse(ButtonType.CANCEL);
				if(result.equals(ButtonType.YES)) {
					System.out.println("Saved");
					gc.new SaveHandler().handle(null);
				} else if(result.equals(ButtonType.NO)) {
					System.out.println("No Save");
				} else {
					System.out.println("Cancel");
					e.consume();
				}
			}
		}
	}
}