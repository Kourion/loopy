package jpp.infinityloop.solver;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import jpp.infinityloop.gui.GameInterfacePane;
import jpp.infinityloop.gui.Tile;

public class Solver {
	
	private Stack<Tile> nextTiles = new Stack<>();
	private Set<Tile> setTiles = new HashSet<>();
	private GameInterfacePane board;
	
	
	public Solver(GameInterfacePane board) {
		this.board = board;
	}

	public void solve() {
		Tile actTile = null;
		try{
			actTile = board.getTile(0, 0);
		} catch(Exception e) {
			System.err.println("Not able to solve. Board has no tiles: " + e.getMessage());
			return;
		}
		nextTiles.push(actTile);
		while(!nextTiles.empty()) {
			actTile = nextTiles.pop();
			
		}
	}
}
