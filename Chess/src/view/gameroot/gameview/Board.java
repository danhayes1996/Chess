package view.gameroot.gameview;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.boardObjects.BoardObject;

public class Board extends Pane {

	public Board() {
		this.setPrefSize(512, 512);
		BackgroundImage bi = new BackgroundImage(new Image("board.png", 512, 512, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		this.setBackground(new Background(bi));
	}
	
	public void addBoardObject(BoardObject bo) {
		this.getChildren().add(bo);
	}
	
	public void removePiece(BoardObject bo) {
		this.getChildren().remove(bo);
	}

	public void addCircle(int x, int y, int radius) {
		this.getChildren().add(new Circle(x , y, radius, Color.RED));
	}
	
	public void removeCirlces() {
		this.getChildren().removeIf(n -> n instanceof Circle);
	}
}
