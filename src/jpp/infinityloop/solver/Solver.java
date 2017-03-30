package jpp.infinityloop.solver;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import jpp.infinityloop.boardalgorithms.CompletionChecker;
import jpp.infinityloop.gui.GameInterfacePane;
import jpp.infinityloop.gui.Tile;
import jpp.infinityloop.gui.TileType;

public class Solver {

	//private Stack<Step> stepStack = new Stack<>();
	private GameInterfacePane board;
	
	public Solver(GameInterfacePane board) {
		this.board = board;
	}
	
	public void solve() {
		Step step = new Step(board);
		//stepStack.push(startStep);
		int count = 0;
//		while(!CompletionChecker.isComplete(board.getPane(),board.getColumnCount(), board.getRowCount())){
//		while(count < 1000) {
		while(step.hasNext()) {
			
			count++;
			if(count % 10000 == 0) {
				System.out.println("Count step: " + count);
			}
			//Step step = stepStack.pop();
			step = step.getNextStep();
			
			//System.out.println("Step count: " + count + " Step: " + step);
			while(!step.couldFit() && !step.triedAll()) {
				step = step.getNextConfigStep();
			}
			//System.out.println("Step count: " + count + " Step: " + step + " could fit: " + step.couldFit() + " nextSize: " + step.nextTiles.size());
			
			//if(!step.couldFit()) { // next step couldn't fit unto actual step

			if(step.couldFit()) {
				step.fixRot();
			}
			while(!step.couldFit()) { // next step couldn't fit unto actual step
				if(count > 400) {
				List<Tile> neighs = step.getNeighbourTiles(step.actualTile);
				for(Tile t : neighs) {
					//System.out.println("Neighbour: " + t);
				}
				}
				//System.out.println("Could not fit Step: " + step);

				//Step changeActStep = changeStep(step); // get next config for actual step
				//step = step.getNextConfigStep();
				//if(step.triedAll()) { // tried every configuration for this step, but couldn't fit any other
					//Step prevStep = stepStack.pop();
					step = step.revertStep();
					step = step.getNextConfigStep();
					//System.out.println("reverting to Step: " + step);
					while(!step.couldFit() && !step.triedAll()) {
						step = step.getNextConfigStep();
					}
					//System.out.println("revert to with config: " + step);
					//stepStack.push(changeStep(prevStep)); // change previous step
				//} else {
					//stepStack.push(changeActStep); // try again with changed actual step in next iteration
				//}
			}
		}
		System.out.println("Last Step: " + step + " could fit: " + step.couldFit());
	}

	
	private class Step {
		
		private int actualTileRot;
		private Tile actualTile;
		//private Tile startTile;
		private Step prevStep;
		private Set<Tile> setTiles;
		private List<Tile> nextTiles;

		public Step(GameInterfacePane board) {
			actualTile = null;
			actualTileRot = 0;
			prevStep = null;
			setTiles = new HashSet<>();
			nextTiles = new LinkedList<>();
			Tile startTile = null;
			try {
				startTile = board.getTile(0, 0);
			} catch (Exception e) {
				System.err.println("Not able to solve. Board has no tiles: " + e.getMessage());
			}
			nextTiles.add(startTile);
		}
		
		public void fixRot() {
			actualTileRot = 1;
		}

		public Step revertStep() {
			//System.out.println("Revert: " + this + " actRot=" + actualTileRot);
			for(int i = actualTileRot; i < 4; i++) {
				//System.out.println("rotating");
				actualTile.rotateTile();
			}
			if (prevStep.triedAll()) {
				return prevStep.revertStep();
			} else {
				return prevStep;
			}
		}

		private Step(Tile actTile, Step prevStep, Set<Tile> sTiles, Collection<Tile> nTiles) {
			this.actualTile = actTile;
			this.setTiles = new HashSet<>();
			setTiles.addAll(sTiles);
			setTiles.add(actTile);
			this.nextTiles = new LinkedList<>();
			nextTiles.addAll(nTiles);
			actualTileRot = 1;
			this.prevStep = prevStep;
		}
		
		public Step getNextStep() {
			//Random ran = new Random();
			//Tile nTile = nextTiles.get(ran.nextInt(nextTiles.size()));
			Tile nTile = nextTiles.get(0);
			List<Tile> newNextTiles = new LinkedList<>();
			List<Tile> neighbours = getNeighbourTiles(nTile);
			//System.out.println("neighbour size " + neighbours.size());
			for(Tile next : nextTiles) {
				if(!newNextTiles.contains(next))
					newNextTiles.add(next);
			}
			//newNextTiles.addAll(nextTiles);
			for(Tile neighbour : neighbours) {
				if(!newNextTiles.contains(neighbour)) {
					newNextTiles.add(neighbour);
				}
			}
			//newNextTiles.addAll(neighbours);
			newNextTiles.removeAll(setTiles);
			newNextTiles.remove(nTile);
			//System.out.println("new next size " + newNextTiles.size());
			
			return new Step(nTile, this, setTiles, newNextTiles);
		}

		public Step getNextConfigStep() {
			if(actualTile == null) {
				System.out.println(this);
			}
			if(actualTile != null) {
				actualTile.rotateTile();
				actualTileRot += 1;
			}
			//return new Step(actualTile, prevStep, setTiles, actualTileRot+1, nextTiles);
			return this;
		}

		public boolean triedAll() {
			return actualTileRot >= 4;
		}
		
		public boolean hasNext() {
			return nextTiles.size() > 0;
		}


		public boolean couldFit() {
			List<Tile> setNeighbours = new LinkedList<>();
			setNeighbours.addAll(setTiles);
			setNeighbours.remove(actualTile);
			setNeighbours.removeIf(t -> !isNeighbourTo(actualTile, t));
			//System.out.println(setNeighbours.size());
			return testFit(actualTile, setNeighbours);
		}
		
		private boolean isNeighbourTo(Tile actTile, Tile other) {
			//System.out.println(actTile + " <-> " + other + " = " + getNeighbourTiles(actTile).contains(other));
			return getNeighbourTiles(actTile).contains(other);
		}

		public List<Tile> getNeighbourTiles(Tile actTile) {
			List<Tile> res = new LinkedList<>();
			int actCol = actTile.getColumn();
			int actRow = actTile.getRow();
			if(actCol > 0) {
				res.add(board.getTile(actRow, actCol - 1));
			}
			if(actCol + 1 < board.getColumnCount()) {
				res.add(board.getTile(actRow, actCol + 1));
			}
			if(actRow > 0) {
				res.add(board.getTile(actRow - 1, actCol));
			}
			if(actRow + 1 < board.getRowCount()) {
				res.add(board.getTile(actRow + 1, actCol));
			}
			//res.remove(actTile);
			return res;
		}
		
		private boolean testFit(Tile actTile, List<Tile> setNeighbours) {
			boolean res = true;
			for (Tile n : setNeighbours) {
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
			if(actTile.getRow() == 4 && actTile.getColumn() == 0) {
				//for(Tile n : setNeighbours)
					//System.out.println(n);
			}
			return res;
		}

		private boolean fitToNeighbour(Tile actTile, Tile neighbour) {
			if(actTile.getType() == TileType.EMPTY) {
				//System.out.println("FitToNeighbour empty to " + neighbour);
			}
			boolean res = true;
			if (actTile.getColumn() == neighbour.getColumn()) {
				if (actTile.getRow() < neighbour.getRow()) {
					res = (actTile.hasDown() == neighbour.hasUp());
				} else if (actTile.getRow() > neighbour.getRow()){
					res = (actTile.hasUp() == neighbour.hasDown());
				} else {
					System.err.println("fitToNeighbour: Tiles are the same");
				}
			} else if (actTile.getRow() == neighbour.getRow()) {
				if (actTile.getColumn() < neighbour.getColumn()) {
					res = (actTile.hasRight() == neighbour.hasLeft());
				} else if (actTile.getColumn() > neighbour.getColumn()){
					res = (actTile.hasLeft() == neighbour.hasRight());
				} else {
					System.err.println("fitToNeighbour: Tiles are the same");
				}
			}else {
				System.err.println("fitToNeighbour: Tiles are not neighbours");
			}
			return res;
		}
		
		@Override
		public String toString() {
			String res = "Start";
			if(actualTile != null) {
				res = actualTile.toString();
			}
			return res;
		}
	}
}
