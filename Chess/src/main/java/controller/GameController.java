package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Model;
import model.boardObjects.BoardObject;
import model.boardObjects.King;
import model.boardObjects.Pawn;
import view.gameroot.GameRootPane;
import view.stages.SettingsStage;

public class GameController {

  private GameRootPane view;
  private Model model;

  private Controller c;

  public GameController(GameRootPane view, Model model) {
    this.view = view;
    this.model = model;

    assignHandlers();
  }

  public void initialize(Controller c) {
    this.c = c;
  }

  private void assignHandlers() {
    view.getMenuBar().setSaveHandler(new SaveHandler());
    view.getMenuBar().setMainMenuHandler(new GoToMainMenuHandler());

    view.getMenuBar().setMoveIndicatorHandler(new MoveIndicatorSettingsHandler());
  }

  private void applyMouseHandlers(BoardObject bo) {
    bo.setOnMousePressed(new BoardObjectMousePressHandler());
    bo.setOnMouseReleased(new BoardObjectMouseReleaseHandler());
    bo.setOnMouseDragged(new BoardObjectMouseDraggedHandler());
    bo.setOnMouseEntered(new BoardObjectMouseHoverHandler());
  }

  private void removeMouseHandlers(BoardObject bo) {
    bo.setOnMousePressed(null);
    bo.setOnMouseReleased(null);
    bo.setOnMouseDragged(null);
    bo.setOnMouseEntered(null);
  }

  private void removeAllPiecesFromView() {
    view.getBoard().getChildren().clear();
    view.getDeadZonePane().clearDeadBoardObjects();
  }

  private void addAllPiecesToView(boolean assertImage) {
    for (BoardObject bo : model.getBoard()) {
		if (bo == null) {
			continue;
		}
		if (assertImage) {
			bo.assertImage();
		}
      view.getBoard().addBoardObject(bo);
    }

    for (BoardObject bo : model.getDeadPieces()) {
		if (bo == null) {
			continue;
		}
		if (assertImage) {
			bo.assertImage();
		}
      view.getDeadZonePane().addDeadBoardObject(bo);
    }
  }

  public void initializeBoard(boolean assertImages) {
    view.setPlayerNames(model.getPlayer1().getName(), model.getPlayer2().getName());
    view.displayCurrentPlayersTurn(model.isWhitesTurn()); //set whose turn it is

    //apply mouse handlers for all each piece
	  for (BoardObject bo : model.getBoard()) {
		  if (bo != null) {
			  applyMouseHandlers(bo);
		  }
	  }

    //remove any pieces from the board
    removeAllPiecesFromView();

    addAllPiecesToView(assertImages);
  }

  private boolean isCurrentPlayersPiece(final BoardObject bo) {
    return (bo.isWhite() && model.isWhitesTurn()) || (!bo.isWhite() && !model.isWhitesTurn());
  }

  public void setModel(Model model) {
    this.model = model;
  }

  public GameRootPane getRoot() {
    return view;
  }

  //---------------------------- GAME HANDLERS ----------------------------
  private class BoardObjectMousePressHandler implements EventHandler<MouseEvent> {
    public void handle(MouseEvent e) {
      BoardObject source = (BoardObject) e.getSource();

      //only continue if the player has move helper on
		if (!model.getPlayerByTurn().isMoveHelperActive()) {
			return;
		}

      //checks if the player has selected one of their pieces
      if (isCurrentPlayersPiece(source)) {
        //finds all valid moves and places a red circle in the appropriate location
        for (int i = 0; i < 8 * 8; i++) {
			if (source.isValidMove(i % 8, i / 8, model.getBoard())) {
				view.getBoard().addCircle((i % 8) * 64 + 32, (i / 8) * 64 + 32, 10);
			}
        }
      }
    }
  }

  private class BoardObjectMouseReleaseHandler implements EventHandler<MouseEvent> {
    public void handle(MouseEvent e) {
      BoardObject source = (BoardObject) e.getSource();
      //only trigger if the right pieces are being moved
      if (isCurrentPlayersPiece(source)) {
        int currentX = (int) e.getX() / 64;
        int currentY = (int) e.getY() / 64;

        if (source.isValidMove(currentX, currentY, model.getBoard())) {
          movePiece(source, currentX, currentY);
          model.switchTurns();
          view.displayCurrentPlayersTurn(model.isWhitesTurn());
        } else {
          source.setXOnBoard(source.getXOnBoard());
          source.setYOnBoard(source.getYOnBoard());
        }
        //remove any circles on the board placed by BoardObjectMousePressHandler
        view.getBoard().removeCircles();
      }
    }

    private void movePiece(BoardObject bo, int x, int y) {
      BoardObject[] board = model.getBoard();
      int index = -1;
      for (int i = 0; i < board.length; i++) {
        if (board[i] != null && board[i].equals(bo)) {
          index = i;
          break;
        }
      }

      board[index] = null;
      if (board[x + y * 8] != null) {
        view.getBoard().removePiece(board[x + y * 8]);
        view.getDeadZonePane().addDeadBoardObject(board[x + y * 8]);
        removeMouseHandlers(board[x + y * 8]);

        //when an opponents king has been taken
		  if (board[x + y * 8] instanceof King) {
			  declareWinner(model.isWhitesTurn() ? model.getPlayer1().getName() : model.getPlayer2().getName());
		  }

        model.removePiece(board[x + y * 8]);
      }
      board[x + y * 8] = bo;
      bo.setXOnBoard(x);
      bo.setYOnBoard(y);
		if (bo instanceof Pawn && ((Pawn) bo).isFistMove()) {
			((Pawn) bo).setFirstMove(false);
		}
    }

    private void declareWinner(String winnerName) {
      System.out.println(winnerName + " Wins!");
      ButtonType btRematch = new ButtonType("Rematch");
      ButtonType btWatch = new ButtonType("Watch Match");
      ButtonType btSaveMatch = new ButtonType("Save Replay");
      ButtonType btMainMenu = new ButtonType("Main Menu");

      Alert winAlert = c.createAlert(winnerName + " Wins!",
          null,
          winnerName + " Wins!",
          btRematch, btWatch, btSaveMatch, btMainMenu);
      Optional<ButtonType> result = winAlert.showAndWait();

      ButtonType btResult = result.orElse(btMainMenu);
      if (btResult.equals(btRematch)) {
        //model.createNewBoard();
        //assignGameHandlers();
        //addAllPiecesToView(false);
      } else if (btResult.equals(btWatch)) {
        //TODO: watch match
      } else if (btResult.equals(btSaveMatch)) {
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
      if (isCurrentPlayersPiece(source)) {
        int mouseX = (int) e.getX();
        int mouseY = (int) e.getY();

        //piece follows mouse
        source.setX(mouseX - (source.getWidth() / 2));
        source.setY(mouseY - (source.getHeight() / 2));
      }
    }
  }

  private class BoardObjectMouseHoverHandler implements EventHandler<MouseEvent> {
    public void handle(MouseEvent e) {
      BoardObject source = (BoardObject) e.getSource();
      if (isCurrentPlayersPiece(source)) {
        source.setStyle("-fx-cursor: hand;");
      }
    }
  }

  public class SaveHandler implements EventHandler<ActionEvent> {
    public void handle(ActionEvent e) {
      try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("currentGame.obj"))) {
        oos.writeObject(model);
        oos.flush();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
	}
  }

  private class GoToMainMenuHandler implements EventHandler<ActionEvent> {
    public void handle(ActionEvent e) {
      Alert saveAlert = c.createAlert(
          "Save?",
          AlertType.CONFIRMATION,
          "Do you wish to save the current game?",
          ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
      ButtonType result = saveAlert.showAndWait().orElse(ButtonType.CANCEL);

      if (result.equals(ButtonType.YES)) {
        System.out.println("Saved");
        new SaveHandler().handle(e);
      } else if (result.equals(ButtonType.NO)) {
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

      c.changeScene(c.getMainMenuController().getRoot());
    }
  }

  private class MoveIndicatorSettingsHandler implements EventHandler<ActionEvent> {
    public void handle(ActionEvent e) {
      Stage subStage = new SettingsStage(model, c.getPrimaryStage(), "Game Settings");
      subStage.show();
    }
  }
}

