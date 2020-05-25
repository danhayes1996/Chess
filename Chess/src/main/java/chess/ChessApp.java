package chess;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.mainmenuroot.MainRootPane;

public class ChessApp extends Application {
	
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Chess Application");
		primaryStage.setResizable(false);
		
		MainRootPane view = new MainRootPane();
		Scene scene = new Scene(view);
		primaryStage.setScene(scene);
		
		primaryStage.sizeToScene();
		primaryStage.show();

		new Controller(view);
	}

	public void init() {
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
