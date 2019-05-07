package view.gameroot.gameview;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import model.boardObjects.BoardObject;
import model.boardObjects.Pawn;

public class DeadPiecePane extends VBox {
	
	private FlowPane pawnRow, otherRow;
	
	public DeadPiecePane() {
		pawnRow = new FlowPane();
		pawnRow.setHgap(-20);
		
		otherRow = new FlowPane();
		otherRow.setHgap(-20);
		
		this.getChildren().addAll(otherRow, pawnRow);
	}
	
	public void addPiece(BoardObject bo) {
		if(bo instanceof Pawn) pawnRow.getChildren().add(bo);
		else {
			if(otherRow.getChildren().isEmpty()) otherRow.getChildren().add(bo);
			else {
				for(int i = 0; i < otherRow.getChildren().size(); i++) {
					if(((BoardObject)otherRow.getChildren().get(i)).getValue() < bo.getValue()) {
						otherRow.getChildren().add(i, bo);
						return;
					}
				}
				otherRow.getChildren().add(bo);
			}
		}
	}
	
	public boolean removePiece(BoardObject bo) {
		 return bo instanceof Pawn ? pawnRow.getChildren().remove(bo) : otherRow.getChildren().remove(bo);
	}
	
	public void clearPieces() {
		pawnRow.getChildren().clear();
		otherRow.getChildren().clear();
		
	}
}
