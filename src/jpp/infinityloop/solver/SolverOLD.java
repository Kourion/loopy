package jpp.infinityloop.solver;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import jpp.infinityloop.boardalgorithms.CompletionChecker;
import jpp.infinityloop.gui.GameInterfacePane;
import jpp.infinityloop.gui.Tile;
import jpp.infinityloop.gui.TileType;

public class SolverOLD {

	private Stack<Tile> nextTiles = new Stack<>();
	private List<Tile> setTiles = new LinkedList<>();
	private Map<Tile, Integer> testedRot = new HashMap<>();
	private GameInterfacePane board;

	public SolverOLD(GameInterfacePane board) {
		this.board = board;
	}

	public void solve() {
		Tile actTile = null;
		try {
			actTile = board.getTile(0, 0);
		} catch (Exception e) {
			System.err.println("Not able to solve. Board has no tiles: " + e.getMessage());
			return;
		}

		nextTiles.push(actTile);
		int count = 0;

		//while (!nextTiles.empty()) {
		//while(count < 1000 || !CompletionChecker.isComplete(board.getPane(),board.getColumnCount(), board.getRowCount())){
		while(count < 30) {
			count++;
			actTile = nextTiles.pop();
			List<Tile> setNeighbours = getNeighbourTiles(actTile);
			setNeighbours.removeIf(t -> !setTiles.contains(t));
			boolean tileFitted = false;

			tileFitted = testFit(actTile, setNeighbours);
			int triedRots = 1;
			if (testedRot.containsKey(actTile)) {
				triedRots = testedRot.get(actTile) + 1;
				actTile.rotateTile();
				System.out.println("again rot Tile: " + actTile + " = fitted: " + tileFitted + " = rotcount: " + triedRots);

			}

			if (setTiles.size() <= 1) {
				System.out.println("Solve step: " + count);
				System.out.println("SetTiles: " + setTiles.size());
				System.out.println("Actual Tile: " + actTile);
			}

			
			while (triedRots < 5 && !tileFitted) { // try until all 4 rotations were tested, or tile fits
				actTile.rotateTile();
				tileFitted = testFit(actTile, setNeighbours);
				triedRots++;
				//System.out.println("Rotating Tile: " + actTile + " = fitted: " + tileFitted + " = rotcount: " + triedRots);
			}
			
			
			// backtracking is not right, maybe too few steps back, or false
			// last tile gets removed
			// next tile couldn't be set with all 4 rotations of this one, so
			// the tile before this one must be set new
			
			 if(triedRots > 4) { nextTiles.add(actTile);
			  testedRot.remove(actTile); 
			  Tile lastTile = setTiles.get(setTiles.size()-1); 
			  setTiles.remove(lastTile);
			  nextTiles.add(lastTile);
			  System.out.println("==Back== " + actTile);
			  } else if(tileFitted) {// add new unsolved tiles to stack, if actual tile could be fit 
				  List<Tile> newNeighbours = getNeighbourTiles(actTile); 
				 newNeighbours.removeIf(t ->setTiles.contains(t) || nextTiles.contains(t)); 
				 setTiles.add(actTile);
				 nextTiles.addAll(newNeighbours); 
				 if(actTile.getType() == TileType.CROSS ||actTile.getType() == TileType.EMPTY) {
					 testedRot.put(actTile, 5);
				 } else {
					 testedRot.put(actTile, triedRots);
				 }
				 System.out.println("For tile: " + actTile + " -> " + newNeighbours.size());
			  } else { // actual tile couldn't be fit, so last tile has to be set to new 
				  nextTiles.add(actTile); 
				  testedRot.remove(actTile);
				  Tile lastTile = setTiles.get(setTiles.size()-1);
				  setTiles.remove(lastTile); nextTiles.add(lastTile); 
				  System.out.println("==no Fit== " + actTile);
			  }
			 
			 /*
			setTiles.add(actTile);
			List<Tile> newNeighbours = getNeighbourTiles(actTile);
			newNeighbours.removeIf(t -> setTiles.contains(t));
			nextTiles.addAll(newNeighbours);
			testedRot.put(actTile, nextRot);
			*/
		}
		System.out.println("After Solve Loop");
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
		return res;
	}

	private boolean fitToNeighbour(Tile actTile, Tile neighbour) {
		boolean res = true;
		if (actTile.getColumn() == neighbour.getColumn()) {
			if (actTile.getRow() < neighbour.getRow()) {
				res = actTile.hasDown() == neighbour.hasUp();
			} else {
				res = actTile.hasUp() == neighbour.hasDown();
			}
		} else {
			if (actTile.getColumn() < neighbour.getColumn()) {
				res = actTile.hasRight() == neighbour.hasLeft();
			} else {
				res = actTile.hasLeft() == neighbour.hasRight();
			}
		}
		return res;
	}

	private List<Tile> getNeighbourTiles(Tile actTile) {
		List<Tile> res = new LinkedList<>();
		int actCol = actTile.getColumn();
		int actRow = actTile.getRow();
		if(actCol > 0) {
			res.add(board.getTile(actCol - 1, actRow));
		}
		if(actCol < board.getColumnCount()) {
			res.add(board.getTile(actCol + 1, actRow));
		}
		if(actRow > 0) {
			res.add(board.getTile(actCol, actRow - 1));
		}
		if(actRow < board.getRowCount()) {
			res.add(board.getTile(actCol, actRow + 1));
		}
		res.remove(actTile);
		return res;
	}
}
