package jpp.infinityloop.boardalgorithms;

import jpp.infinityloop.gui.TileType;

public class LogicTile {

	private TileType type = TileType.EMPTY;
	private boolean left = false, up = false, right = false, down = false, switchedLR = false, switchedUD = false;
	
	public LogicTile(TileType type, boolean left, boolean up, boolean right, boolean down) {
		this.setType(type);
		this.left = left;
		this.up = up;
		this.right = right;
		this.down = down;
	}

	/**
	 * @return the left
	 */
	public boolean hasLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(boolean left) {
		this.left = left;
	}

	/**
	 * @return the up
	 */
	public boolean hasUp() {
		return up;
	}

	/**
	 * @param up the up to set
	 */
	public void setUp(boolean up) {
		this.up = up;
	}

	/**
	 * @return the right
	 */
	public boolean hasRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(boolean right) {
		this.right = right;
	}

	/**
	 * @return the down
	 */
	public boolean hasDown() {
		return down;
	}

	/**
	 * @param down the down to set
	 */
	public void setDown(boolean down) {
		this.down = down;
	}

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}

	/**
	 * @return the switchedLR
	 */
	public boolean isSwitchedLR() {
		return switchedLR;
	}

	/**
	 * @param switchedLR the switchedLR to set
	 */
	public void setSwitchedLR(boolean switchedLR) {
		this.switchedLR = switchedLR;
	}

	/**
	 * @return the switchedUD
	 */
	public boolean isSwitchedUD() {
		return switchedUD;
	}

	/**
	 * @param switchedUD the switchedUD to set
	 */
	public void setSwitchedUD(boolean switchedUD) {
		this.switchedUD = switchedUD;
	}

}
