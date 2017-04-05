package jpp.infinityloop.solver;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.util.Duration;
import jpp.infinityloop.boardalgorithms.CompletionChecker;
import jpp.infinityloop.boardalgorithms.LogicTile;
import jpp.infinityloop.gui.GameInterfacePane;
import jpp.infinityloop.gui.Tile;
import jpp.infinityloop.gui.TileType;

public class LogicSolver {

	private GameInterfacePane board;
	private LogicTile[][] logic;
	boolean busy = false;
	private Alert info;
	@SuppressWarnings({ "rawtypes", "unused" })
	private Dialog dialog;

	public LogicSolver(GameInterfacePane board, Alert info) {
		this.board = board;
		this.logic = board.getLogicTiles();
		this.info = info;
		//this.dialog = dialog;
	}

	public boolean solve() {
		Step step = new Step(board);
		int count = 0;
		// while(!CompletionChecker.isComplete(board.getPane(),board.getColumnCount(),
		// board.getRowCount())){
		
		for (int i = 0; i < board.getRowCount(); i++) {
			for (int j = 0; j < board.getColumnCount(); j++) {
					logic[i][j].setType(board.getTile(i,j).getType());
					logic[i][j].setLeft(( board.getTile(i,j)).hasLeft());
					logic[i][j].setUp((board.getTile(i,j)).hasUp());
					logic[i][j].setRight(board.getTile(i,j).hasRight());
					logic[i][j].setDown(board.getTile(i,j).hasDown());
					logic[i][j].setColumn(board.getTile(i,j).getColumn());
					logic[i][j].setRow(board.getTile(i,j).getRow());
			}
		}
		
		while (step.hasNext()) {

			count++;
			//if (count % 10000 == 0) {
				//System.out.println("Count step: " + count);
				
			//}
			if(count == 500000){
				//dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
				//dialog.close();
				ButtonType thisOkButton = new ButtonType(" ", ButtonData.OK_DONE);
				info.getButtonTypes().setAll(thisOkButton);
				info.close();
				
				//dialog = new Dialog();
				//dialog.setHeaderText("Calculating...");
				///dialog.setContentText("This seems to be a difficult game-board. The internal monkey is working hard on turning the tiles.");
				//dialog.show();
				
				info = new Alert(AlertType.NONE);
				info.setHeaderText("Calculating...");
				info.setContentText("This seems to be a difficult game-board. The internal monkey is working hard on turning the tiles.");
				info.show();
				
				
			}if(count == 2000000){
				//dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
				//dialog.close();
				
				ButtonType thisOkButton = new ButtonType(" ", ButtonData.OK_DONE);
				info.getButtonTypes().setAll(thisOkButton);
				info.close();
				
				info = new Alert(AlertType.NONE);
				info.setHeaderText("Calculating...");
				info.setContentText("This game-board is tricky indead. Maybe it is unusually large, or the tiles are turned exceptionally well to confuse.");
				info.show();
				
				//dialog = new Dialog();
				//dialog.setHeaderText("Calculating...");
				//dialog.setContentText("This game-board is tricky indead. Maybe it is unusually large, or the tiles are turned exceptionally well to confuse.");
				//dialog.show();
			}
				
			step = step.getNextStep();

			while (!step.couldFit() && !step.triedAll()) {
				step = step.getNextConfigStep();
			}

			if (step.couldFit()) {
				step.fixRot();
			}
			while (!step.couldFit()) { // next step couldn't fit unto actual
										// step

				step = step.revertStep();
				if (step.isStart()) {
					//System.err.println("Reverted in step to Startstep -> Puzzle is unsolvable");
					Alert error1 = new Alert(AlertType.CONFIRMATION); 
					error1.setTitle("The field could not be solved!"); 
					error1.setHeaderText("UNSOLVABLE"); 
					error1.setContentText("Recursion reverted back to start -> Puzzle is unsolvable"); 
					
					ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
					error1.getButtonTypes().setAll(okButton); 
					error1.showAndWait();
					return false;
				}
				step = step.getNextConfigStep();
				// tried every configuration for this step, but couldn't fit any
				// other
				while (!step.couldFit() && !step.triedAll()) {
					step = step.getNextConfigStep();
				}

			}
		}
		//CompletionChecker.isComplete(board.getPane(),board.getColumnCount(),board.getRowCount());
		
		//LogicTile aTile = logic[0][0];
		boolean possible = CompletionChecker.isProper(logic, board.getRowCount(), board.getColumnCount());
		if(possible){
			//System.out.println("COMPLETE");
			//alert.close();
			//dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
			//dialog.close();
			
			ButtonType thisOkButton = new ButtonType(" ", ButtonData.OK_DONE);
			info.getButtonTypes().setAll(thisOkButton);
			info.close();
			
			turnTile(step);
				/*
				while(!step.isStart){
					int row = step.actualTile.getRow();
					int col = step.actualTile.getColumn();
					boolean left = step.actualTile.hasLeft();
					boolean up = step.actualTile.hasUp();
					boolean right = step.actualTile.hasRight();
					boolean down = step.actualTile.hasDown();
					//boolean busy = true;
					Tile actualTile = board.getTile(row, col);
					//System.out.println("r:"+row+" c:"+col+" rot:"+rot);
					//board.getTile(row, col).getR
					if(actualTile.hasLeft() != left || actualTile.hasUp() != up 
							   || actualTile.hasRight() != right || actualTile.hasDown() != down){
						RotateTransition rt = new RotateTransition(Duration.millis(200), actualTile);
						rt.setByAngle(90);
						rt.setCycleCount(1);
						rt.setOnFinished(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							board.getTile(row, col).rotateTile(false);
							
						}
						});
						rt.play();
					}
					//step = step.prevStep;
					
					/*
					while(actualTile.hasLeft() != left || actualTile.hasUp() != up 
					   || actualTile.hasRight() != right || actualTile.hasDown() != down ){
						
						if(!busy){
							busy = true;
							RotateTransition rt = new RotateTransition(Duration.millis(200), actualTile);
							rt.setByAngle(90);
							rt.setCycleCount(1);
							rt.setOnFinished(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								board.getTile(row, col).rotateTile(false);
								busy = false;
							}
							});
							rt.play();
						}
				        
						//while(busy){}
					}
					step = step.prevStep;
					*//*
				}
				*/
		}else{
			//System.out.println("NOT COMPLETE");
		}
		
		//for(int i = 0; i < 4; i++) { // fire a button four times, to get the board to check for completion
		//	aTile.fire();
		//}
		return possible;
	}

	private void turnTile(Step step){
		if(step.isStart){
			Tile aTile = board.getTile(0, 0);
			for(int i = 0; i < 4; i++) { // fire a button four times, to get the board to check for completion
				aTile.fire();
			}
		}else{
			int row = step.actualTile.getRow();
			int col = step.actualTile.getColumn();
			boolean left = step.actualTile.hasLeft();
			boolean up = step.actualTile.hasUp();
			boolean right = step.actualTile.hasRight();
			boolean down = step.actualTile.hasDown();
			Tile actualTile = board.getTile(row, col);
			if(actualTile.hasLeft() != left || actualTile.hasUp() != up 
					   || actualTile.hasRight() != right || actualTile.hasDown() != down){
				RotateTransition rt = new RotateTransition(Duration.millis(200), actualTile);
				rt.setByAngle(90);
				rt.setCycleCount(1);
				rt.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					board.getTile(row, col).rotateTile(false);
					if(actualTile.hasLeft() != left || actualTile.hasUp() != up 
							   || actualTile.hasRight() != right || actualTile.hasDown() != down){
						turnTile(step);
					}else{
						if(!step.isStart){
							turnTile(step.prevStep);
						}else{
							//System.out.println("AT START");
						}
						
					}
				}
				});
				rt.play();
			}else{
				if(!step.isStart){
					turnTile(step.prevStep);
				}else{
					//System.out.println("AT START");
				}
				
			}
		}
		//step = step.prevStep;
	}
	
	private class Step {

		private int actualTileRot;
		private LogicTile actualTile;
		// private Tile startTile;
		private Step prevStep;
		private Set<LogicTile> setTiles;
		private List<LogicTile> nextTiles;
		private boolean isStart = true;

		public Step(GameInterfacePane board) {
			actualTile = null;
			actualTileRot = 0;
			prevStep = null;
			setTiles = new HashSet<>();
			nextTiles = new LinkedList<>();
			LogicTile startTile = null;
			try {
				startTile = logic[0][0];
			} catch (Exception e) {
				//System.err.println("Not able to solve. Board has no tiles: " + e.getMessage());
				Alert error1 = new Alert(AlertType.CONFIRMATION); 
				error1.setTitle("Board Error"); 
				error1.setHeaderText("UNSOLVABLE"); 
				error1.setContentText("Not able to solve. Board has no tiles."); 
				
				ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
				error1.getButtonTypes().setAll(okButton); 
				error1.showAndWait();
			}
			nextTiles.add(startTile);
		}

		public boolean isStart() {
			return isStart;
		}

		public void fixRot() {
			if(actualTile.getType() == TileType.EMPTY || actualTile.getType() == TileType.CROSS) {
				actualTileRot = 4;
			} else {
				actualTileRot = 1;
			}
		}

		public Step revertStep() {
			// System.out.println("Revert: " + this + " actRot=" +
			// actualTileRot);
			for (int i = actualTileRot; i < 4; i++) {
				// System.out.println("rotating");
				actualTile.rotateTile(false);
			}
			if (prevStep.triedAll()) {
				return prevStep.revertStep();
			} else {
				return prevStep;
			}
		}

		private Step(LogicTile actTile, Step prevStep, Set<LogicTile> sTiles, Collection<LogicTile> nTiles) {
			isStart = false;
			this.actualTile = actTile;
			this.setTiles = new HashSet<>();
			setTiles.addAll(sTiles);
			setTiles.add(actTile);
			this.nextTiles = new LinkedList<>();
			nextTiles.addAll(nTiles);
			if(actualTile.getType() == TileType.EMPTY || actualTile.getType() == TileType.CROSS) {
				actualTileRot = 4;
			} else {
				actualTileRot = 1;
			}
			this.prevStep = prevStep;
		}

		public Step getNextStep() {
			LogicTile nTile = nextTiles.get(0);
			List<LogicTile> newNextTiles = new LinkedList<>();
			List<LogicTile> neighbours = getNeighbourTiles(nTile);
			for (LogicTile next : nextTiles) {
				if (!newNextTiles.contains(next))
					newNextTiles.add(next);
			}
			for (LogicTile neighbour : neighbours) {
				if (!newNextTiles.contains(neighbour)) {
					newNextTiles.add(neighbour);
				}
			}
			newNextTiles.removeAll(setTiles);
			newNextTiles.remove(nTile);

			return new Step(nTile, this, setTiles, newNextTiles);
		}

		public Step getNextConfigStep() {
			if (actualTile == null) {
				//System.err.println("getNextConfigStep: tried to get next config for: " + this);
			}
			//RotateTransition rt = new RotateTransition(Duration.millis(500), actualTile);
			//rt.setByAngle(90);
			//rt.setCycleCount(1);
			//rt.play();
			// if(actualTile != null) {
			//actualTile.rotateTile(false);
			actualTile.rotate();
			//actualTile.fire();
			actualTileRot += 1;
			if(isTilePointingOutward()) {
				return getNextConfigStep();
			}
			// }
			/// return new Step(actualTile, prevStep, setTiles, actualTileRot+1,
			// nextTiles);
			return this;
		}

		private boolean isTilePointingOutward() {
			if(actualTile.getRow() == 0) {
				if(actualTile.hasUp()) {
					return true;
				}
			}
			if(actualTile.getRow() == board.getRowCount()-1) {
				if(actualTile.hasDown()) {
					return true;
				}
			}
			if(actualTile.getColumn() == 0) {
				if(actualTile.hasLeft()) {
					return true;
				}
			}
			if(actualTile.getColumn() == board.getColumnCount()-1) {
				if(actualTile.hasRight()) {
					return true;
				}
			}
			return false;
		}

		public boolean triedAll() {
			return actualTileRot >= 4;
		}

		public boolean hasNext() {
			return nextTiles.size() > 0;
		}

		public boolean couldFit() {
			List<LogicTile> setNeighbours = new LinkedList<>();
			setNeighbours.addAll(setTiles);
			setNeighbours.remove(actualTile);
			setNeighbours.removeIf(t -> !isNeighbourTo(actualTile, t));
			return testFit(actualTile, setNeighbours);
		}

		private boolean isNeighbourTo(LogicTile actTile, LogicTile other) {
			return getNeighbourTiles(actTile).contains(other);
		}

		public List<LogicTile> getNeighbourTiles(LogicTile actTile) {
			List<LogicTile> res = new LinkedList<>();
			int actCol = actTile.getColumn();
			int actRow = actTile.getRow();
			if (actCol > 0) {
				res.add(logic[actRow][actCol-1]);//board.getTile(actRow, actCol - 1));
			}
			if (actCol + 1 < board.getColumnCount()) {
				res.add(logic[actRow][actCol+1]);//board.getTile(actRow, actCol + 1));
			}
			if (actRow > 0) {
				res.add(logic[actRow-1][actCol]);//board.getTile(actRow - 1, actCol));
			}
			if (actRow + 1 < board.getRowCount()) {
				res.add(logic[actRow+1][actCol]);//board.getTile(actRow + 1, actCol));
			}
			// res.remove(actTile);
			return res;
		}

		private boolean testFit(LogicTile actTile, List<LogicTile> setNeighbours) {
			boolean res = true;
			for (LogicTile n : setNeighbours) {
				res = res & fitToNeighbour(actTile, n);
			}
			if (actTile.getColumn() == 0) {
				res = res & !actTile.hasLeft();
			}
			if (actTile.getColumn() == board.getColumnCount() - 1) {
				res = res & !actTile.hasRight();
			}
			if (actTile.getRow() == 0) {
				res = res & !actTile.hasUp();
			}
			if (actTile.getRow() == board.getRowCount() - 1) {
				res = res & !actTile.hasDown();
			}
			if (actTile.getRow() == 4 && actTile.getColumn() == 0) {
				// for(Tile n : setNeighbours)
				// System.out.println(n);
			}
			return res;
		}

		private boolean fitToNeighbour(LogicTile actTile, LogicTile neighbour) {
			boolean res = true;
			if (actTile.getColumn() == neighbour.getColumn()) {
				if (actTile.getRow() < neighbour.getRow()) {
					res = (actTile.hasDown() == neighbour.hasUp());
				} else if (actTile.getRow() > neighbour.getRow()) {
					res = (actTile.hasUp() == neighbour.hasDown());
				} else {
					//System.err.println("fitToNeighbour: Tiles are the same");
				}
			} else if (actTile.getRow() == neighbour.getRow()) {
				if (actTile.getColumn() < neighbour.getColumn()) {
					res = (actTile.hasRight() == neighbour.hasLeft());
				} else if (actTile.getColumn() > neighbour.getColumn()) {
					res = (actTile.hasLeft() == neighbour.hasRight());
				} else {
					//System.err.println("fitToNeighbour: Tiles are the same");
				}
			} else {
				//System.err.println("fitToNeighbour: Tiles are not neighbours");
			}
			return res;
		}

		@Override
		public String toString() {
			String res = "Start";
			if (actualTile != null) {
				res = actualTile.toString();
			}
			return res;
		}
	}
}
