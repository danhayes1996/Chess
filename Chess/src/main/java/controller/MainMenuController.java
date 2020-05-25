package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

  public void initialize(Controller c) {
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

      c.getGameController().initializeBoard(true);
      c.changeScene(c.getGameController().getRoot());
    }
  }

  private class PVPButtonClickHandler implements EventHandler<ActionEvent> {
    public void handle(ActionEvent e) {
      //popup for player1 name
      Controller.createTextInputDialog(
          "Enter Player One's Name", null,
          "Enter Player One's Name:", "Player1", null)
          .showAndWait()
          .ifPresent(s -> model.getPlayer1().setName(s));

      //popup for player2 name
      Controller.createTextInputDialog(
          "Enter Player Two's Name", null,
          "Enter Player Two's Name:", "Player2", null)
          .showAndWait()
          .ifPresent(s -> model.getPlayer2().setName(s));

      model.createNewBoard();
      c.getGameController().initializeBoard(false);

      c.changeScene(c.getGameController().getRoot());
    }
  }

  private class PVBotButtonClickHandler implements EventHandler<ActionEvent> {
    public void handle(ActionEvent e) {
      //gameRoot = new GameRootPane();
      model.createNewBoard();
      //assignGameHandlers();
      //addAllPiecesToView(false);

      //TODO: initialize bot & add bot handler

      c.changeScene(c.getGameController().getRoot());
    }
  }

  private class OpenHandler implements EventHandler<ActionEvent> {
    public void handle(ActionEvent e) {
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("dummyExternalDir/currentGame.obj"))) {
        c.setModel((Model) ois.readObject());
	  } catch (IOException | ClassNotFoundException ex) {
        ex.printStackTrace();
      }
	}
  }

}
