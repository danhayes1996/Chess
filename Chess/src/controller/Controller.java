package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Model;
import model.boardObjects.BoardObject;
import model.boardObjects.King;
import model.boardObjects.Pawn;
import view.gameroot.GameRootPane;
import view.mainmenu.MainRootPane;

public class Controller {

	private Stage stage;
	private MainRootPane mainRoot;
	private GameRootPane gameRoot;
	
	private Model model;
	
	public Controller(MainRootPane view, Model model) {
		this.stage = (Stage) view.getScene().getWindow();
		this.mainRoot = view;
		this.model = model;
		
		assignMainHandlers();
	}
	
	private void assignMainHandlers() {
		mainRoot.setContinueButtonClickedHandler(new ContinueButtonClickHandler());
		mainRoot.setPVPButtonClickedHandler(new PVPButtonClickHandler());
		//mainRoot.setPVBotButtonClickedHandler(new PVBotButtonClickHandler());
	}
	
	private void assignGameHandlers() {
		stage.setOnCloseRequest(new WindowCloseHandler());
		
		for(BoardObject bo : model.getBoard()) {
			if(bo == null) continue;
			bo.setMousePressedHandler(new BoardObjectMousePressHandler());
			bo.setMouseReleasedHandler(new BoardObjectMouseReleaseHandler());
			bo.setMouseDraggedHandler(new BoardObjectMouseDraggedHandler());
		}
		
		gameRoot.getMenuBar().setSaveHandler(new SaveHandler());
		gameRoot.getMenuBar().setMainMenuHandler(new GoToMainMenuHandler());
	}
	
	private void addAllPiecesToView(boolean assertImage) {
		for(BoardObject bo : model.getBoard()) {
			if(bo == null) continue;
			if(assertImage) bo.assertImage();
			gameRoot.getBoard().addBoardObject(bo);
		}
		
		for(BoardObject bo : model.getDeadPieces()) {
			if(bo == null) continue;
			if(assertImage) bo.assertImage();
			gameRoot.getDeadZonePane().addDeadBoardObject(bo);
		}
	}
	
	private Alert createAlert(String title, AlertType alertType, String content, ButtonType... buttons) {
		Alert a = new Alert(alertType, content, buttons);
		a.setTitle(title);
		return a;
	}
	
	private TextInputDialog createTextInputDialog(String title, String header, String content, String defaultValue, Node graphic) {
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
			Alert saveAlert = createAlert("Save?", AlertType.CONFIRMATION, "Do you wish to save the current game?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			ButtonType result = saveAlert.showAndWait().orElse(ButtonType.CANCEL);
			
			if(result.equals(ButtonType.YES)) {
				System.out.println("Saved");
				new SaveHandler().handle(null);
			} else if(result.equals(ButtonType.NO)) {
				System.out.println("No Save");
			} else {
				System.out.println("Cancel");
				e.consume();
			}
		}
	}
	
	
	//---------------------------- MAIN HANDLERS ----------------------------
	private class ContinueButtonClickHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			gameRoot = new GameRootPane();
			//load game from file
			new OpenHandler().handle(e);
			
			stage.setScene(new Scene(gameRoot));
			stage.centerOnScreen();
		}
	}

	private class PVPButtonClickHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			//popup for player1 name
			TextInputDialog tidPlayer1 = createTextInputDialog("Enter Player One's Name", null, "Enter Player One's Name:", "Player1", null);
			Optional<String> player1Name = tidPlayer1.showAndWait();
			if(player1Name.isPresent()) model.setPlayer1Name(player1Name.get());
			
			//popup for player2 name
			TextInputDialog tidPlayer2 = createTextInputDialog("Enter Player Two's Name", null, "Enter Player Two's Name:", "Player2", null);
			Optional<String> player2Name = tidPlayer2.showAndWait();
			if(player2Name.isPresent()) model.setPlayer2Name(player2Name.get());
	
			//show chess board
			gameRoot = new GameRootPane();
			gameRoot.setPlayerNames(model.getPlayer1Name(), model.getPlayer2Name());
			
			model.createNewBoard();
			assignGameHandlers();
			addAllPiecesToView(false);
			
			stage.setScene(new Scene(gameRoot));
			stage.centerOnScreen();
		}
	}

	private class PVBotButtonClickHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			gameRoot = new GameRootPane();
			model.createNewBoard();
			assignGameHandlers();
			addAllPiecesToView(false);
			
			//TODO: initalize bot & add bot handler
			
			stage.setScene(new Scene(gameRoot));
			stage.centerOnScreen();
		}
	}
	
	//---------------------------- GAME HANDLERS ---------------------------- 
	private class BoardObjectMousePressHandler implements EventHandler<MouseEvent> {
		public void handle(MouseEvent e) {
			BoardObject source = (BoardObject) e.getSource();
			//checks if the player has selected one of their pieces 
			if((source.isWhite() && model.isWhitesTurn()) || (!source.isWhite() && !model.isWhitesTurn())) {
				//finds all valid moves and places a red circle in the appropriate location
				for(int i = 0; i < 8 * 8; i++) {
					if(source.isValidMove(i % 8, i / 8, model.getBoard()))
						gameRoot.getBoard().addCircle((i % 8) * 64 + 32, (i / 8) * 64 + 32, 10);
				}
			}
		}
	}
	
	private class BoardObjectMouseReleaseHandler implements EventHandler<MouseEvent> {
		public void handle(MouseEvent e) {
			BoardObject source = (BoardObject) e.getSource();
			//only trigger if the right pieces are being moved
			if((source.isWhite() && model.isWhitesTurn()) || (!source.isWhite() && !model.isWhitesTurn())) {
				int currentX = (int)e.getX() / 64;
				int currentY = (int)e.getY() / 64;
				
				if(source.isValidMove(currentX, currentY, model.getBoard())) {
					movePiece(source, currentX, currentY);
					model.switchTurns();
					gameRoot.switchTurns(model.isWhitesTurn());
				} else {
					source.setXOnBoard(source.getXOnBoard());
					source.setYOnBoard(source.getYOnBoard());
				}
				//remove any circles on the board placed by BoardObjectMousePressHandler
				gameRoot.getBoard().removeCirlces();
			}
		}
		
		private void movePiece(BoardObject bo, int x, int y) {
			BoardObject[] board = model.getBoard();
			int index = -1;
			for(int i = 0; i < board.length; i++) {
				if(board[i] != null && board[i].equals(bo)) {
					index = i;
					break;
				}
			}
			
			board[index] = null;
			if(board[x + y * 8] != null) {
				gameRoot.getBoard().removePiece(board[x + y * 8]);
				gameRoot.getDeadZonePane().addDeadBoardObject(board[x + y * 8]);
				
				//when an opponents king has been taken
				if(board[x + y * 8] instanceof King) 
					declareWinner(model.isWhitesTurn() ? model.getPlayer1Name() : model.getPlayer2Name());
				
				model.removePiece(board[x + y * 8]);
			}
			board[x + y * 8] = bo;
			bo.setXOnBoard(x);
			bo.setYOnBoard(y);
			if(bo instanceof Pawn && ((Pawn)bo).isFistMove()) ((Pawn)bo).setFirstMove(false);
		}
		
		private void declareWinner(String winnerName) {
			System.out.println(winnerName + " Wins!");
			ButtonType btRematch = new ButtonType("Rematch");
			ButtonType btWatch = new ButtonType("Watch Match");
			ButtonType btSaveMatch = new ButtonType("Save Replay");
			ButtonType btMainMenu = new ButtonType("Main Menu");
			
			Alert winAlert = createAlert(winnerName + " Wins!",
											null, 
											winnerName + " Wins!",
											btRematch, btWatch, btSaveMatch, btMainMenu);
			Optional<ButtonType> result = winAlert.showAndWait();
			
			ButtonType btResult = result.orElse(btMainMenu);
			if(btResult.equals(btRematch)) {
				model.createNewBoard();
				assignGameHandlers();
				addAllPiecesToView(false);
			} else if(btResult.equals(btWatch)) {
				//TODO: watch match
			} else if(btResult.equals(btSaveMatch)) {
				//TODO: save match
			} else {
				//TODO: goto main menu
			}
		}
	}
	
	private class BoardObjectMouseDraggedHandler implements EventHandler<MouseEvent> {
		public void handle(MouseEvent e) {
			BoardObject source = (BoardObject) e.getSource();
			
			//only move if the correct pieces are being moved
			if((source.isWhite() && model.isWhitesTurn()) || (!source.isWhite() && !model.isWhitesTurn())) {
				int mouseX = (int) e.getX();
				int mouseY = (int) e.getY();
				
				//piece follows mouse
				source.setX(mouseX - (source.getWidth() / 2));
				source.setY(mouseY - (source.getHeight() / 2));
			}
		}
	}
	
	private class SaveHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/currentGame.obj"));
				oos.writeObject(model);
				
				oos.flush();
				oos.close();
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private class OpenHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("res/currentGame.obj"));
				model = (Model) ois.readObject();
				
				//send data from model to view
				gameRoot.setPlayerNames(model.getPlayer1Name(), model.getPlayer2Name());
				gameRoot.switchTurns(model.isWhitesTurn());
				
				assignGameHandlers();
				addAllPiecesToView(true);
				
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
	
	private class GoToMainMenuHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Alert saveAlert = createAlert("Save?", AlertType.CONFIRMATION, "Do you wish to save the current game?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			ButtonType result = saveAlert.showAndWait().orElse(ButtonType.CANCEL);
			
			if(result.equals(ButtonType.YES)) {
				System.out.println("Saved");
				new SaveHandler().handle(e);
			} else if(result.equals(ButtonType.NO)) {
				System.out.println("No Save");
				//dont save
			} else {
				System.out.println("Cancel");
				return;
			}
			
			//get top most menuItem
			MenuItem m = (MenuItem) e.getSource();
			while (m.getParentPopup() == null) {
				m = m.getParentMenu();
			}

			stage.setScene(mainRoot.getScene());
			stage.centerOnScreen();
		}
	}
}
