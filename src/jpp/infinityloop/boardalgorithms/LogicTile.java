package jpp.infinityloop.boardalgorithms;

import jpp.infinityloop.gui.TileType;

public class LogicTile {

	private TileType type = TileType.EMPTY;
	private boolean left = false, up = false, right = false, down = false;
	
	public LogicTile(TileType type, boolean left, boolean up, boolean right, boolean down) {
		this.type = type;
		this.left = left;
		this.up = up;
		this.right = right;
		this.down = down;
	}

}
