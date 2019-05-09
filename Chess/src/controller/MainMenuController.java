package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputDialog;
import model.Model;
import view.mainmenuroot.MainRootPane;

public class MainMenuController {

	private MainRootPane view;
	private Model model;
	
	private Controller c;
	
	public MainMenuController(MainRootPane view, Model model) {
		this.view = view;
		this.model = model;
		
		assignHandlers();
	}
	
	public void initalize(Controller c) {
		this.c = c;
	}
	
	
	private void assignHandlers() {
		view.setContinueButtonClickedHandler(new ContinueButtonClickHandler());
		view.setPVPButtonClickedHandler(new PVPButtonClickHandler());
		//view.setPVBotButtonClickedHandler(new PVBotButtonClickHandler());
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
	public MainRootPane getRoot() {
		return view;
	}
	
//---------------------------- MAIN HANDLERS ----------------------------
	private class ContinueButtonClickHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			//load game from file
			new OpenHandler().handle(e);
			
			c.getGameController().initalizeBoard(true);
			c.changeScene(c.getGameController().getRoot());
		}
	}

	private class PVPButtonClickHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			//popup for player1 name
			TextInputDialog tidPlayer1 = c.createTextInputDialog("Enter Player One's Name", null, "Enter Player One's Name:", "Player1", null);
			Optional<String> player1Name = tidPlayer1.showAndWait();
			if(player1Name.isPresent()) model.getPlayer1().setName(player1Name.get());//setPlayer1Name(player1Name.get());
			
			//popup for player2 name
			TextInputDialog tidPlayer2 = c.createTextInputDialog("Enter Player Two's Name", null, "Enter Player Two's Name:", "Player2", null);
			Optional<String> player2Name = tidPlayer2.showAndWait();
			if(player2Name.isPresent()) model.getPlayer2().setName(player2Name.get());//.setPlayer2Name(player2Name.get());
	
			model.createNewBoard();
			c.getGameController().initalizeBoard(false);
			
			c.changeScene(c.getGameController().getRoot());
		}
	}

	private class PVBotButtonClickHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			//gameRoot = new GameRootPane();
			model.createNewBoard();
			//assignGameHandlers();
			//addAllPiecesToView(false);
			
			//TODO: initalize bot & add bot handler
			
			c.changeScene(c.getGameController().getRoot());
		}
	}
	
	private class OpenHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("res/currentGame.obj"));
				c.setModel((Model) ois.readObject());
				
				ois.close();
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		}
	}
	
}
