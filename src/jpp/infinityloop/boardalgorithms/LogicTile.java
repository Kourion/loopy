package jpp.infinityloop.boardalgorithms;

import jpp.infinityloop.gui.TileType;

public class LogicTile {

	private TileType type = TileType.EMPTY;
	private boolean left = false, up = false, right = false, down = false, switchedLR = false, switchedUD = false;
	private int row = 0, col = 0;
	
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
	
	public void LEFT_UP_RIGHT_DOWN(boolean left, boolean up, boolean right, boolean down){
		this.left = left;
		this.up = up;
		this.right = right;
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

	public boolean checkTypeMatchBoolean() {
		int boolctr = 0;
		boolean matching = true;
		if(left){ boolctr++; }
		if(up){ boolctr++; }
		if(right){ boolctr++; }
		if(down){ boolctr++; }
		if(type == TileType.BEND){		if(boolctr!= 2){	matching = false;	System.err.println("BEND does not match boolctr! Ctr is: "+boolctr); }	}
		if(type == TileType.CROSS){		if(boolctr!= 4){	matching = false;	System.err.println("CROSS does not match boolctr! Ctr is: "+boolctr); }	}
		if(type == TileType.DEADEND){	if(boolctr!= 1){	matching = false;	System.err.println("DEADEND does not match boolctr! Ctr is: "+boolctr); }	}
		if(type == TileType.EMPTY){		if(boolctr!= 0){	matching = false;	System.err.println("EMPTY does not match boolctr! Ctr is: "+boolctr); }	}
		if(type == TileType.STRAIGHT){	if(boolctr!= 2){	matching = false;	System.err.println("STRAIGHT does not match boolctr! Ctr is: "+boolctr); }	}
		if(type == TileType.TEE){		if(boolctr!= 3){	matching = false;	System.err.println("TEE does not match boolctr! Ctr is: "+boolctr); }	}
		
		if(type == TileType.BEND && up && down){	matching = false;	System.err.println("BEND does not match bool-directions!"); }
		if(type == TileType.BEND && right && left){	matching = false;	System.err.println("BEND does not match bool-directions!"); }
		
		if(type == TileType.STRAIGHT && up && left){	matching = false;	System.err.println("STRAIGHT does not match bool-directions!"); }
		if(type == TileType.STRAIGHT && up && right){	matching = false;	System.err.println("STRAIGHT does not match bool-directions!"); }
		if(type == TileType.STRAIGHT && down && left){	matching = false;	System.err.println("STRAIGHT does not match bool-directions!"); }
		if(type == TileType.STRAIGHT && down && right){	matching = false;	System.err.println("STRAIGHT does not match bool-directions!"); }
		
		return matching;
	}

	public void rotate() {
		//DEADENDS
		if(left && !up && !right && !down){
			left = false;
			up = true;
		}else if(!left && up && !right && !down){
			up = false;
			right = true;
		}else if(!left && !up && right && !down){
			right = false;
			down = true;
		}else if(!left && !up && !right && down){
			down = false;
			left = true;
		}
		
		//BENDS
		else if(left && up && !right && !down){
			left = false;
			right = true;
		}else if(!left && up && right && !down){
			up = false;
			down = true;
		}else if(!left && !up && right && down){
			right = false;
			left = true;
		}else if(left && !up && !right && down){
			down = false;
			up = true;
		}
		
		//STRAIGHTS
		else if(left && !up && right && !down){
			left = false;
			right = false;
			up = true;
			down = true;
		}else if(!left && up && !right && down){
			up = false;
			down = false;
			left = true;
			right = true;
		}
		
		//NOTHING TO DO FOR EMPTY
		//NOTHING TO DO FOR CROSS
		
		//TEES
		else if(left && up && right && !down){
			left = false;
			down = true;
		}else if(!left && up && right && down){
			up = false;
			left = true;
		}else if(left && !up && right && down){
			right = false;
			up = true;
		}else if(left && up && !right && down){
			down = false;
			right = true;
		}
		
	}
	
	public void rotateTile(boolean animate){
		switch (type){
			case BEND:
				if(up&&right){
					up = false;
					down = true;
				}else if(right&&down){
					right = false;
					left = true;
				}else if(down&&left){
					down = false;
					up = true;
				}
				else if(left&&up){
					left = false;
					right = true;
				}
				break;
				
			case CROSS:
				break;
				
			case DEADEND:
				
				//System.out.println(pastRotations+" "+left+" "+up+" "+" "+right+" "+down);
				
				if(left){
					left = false;
					up = true;
					//this.setRotate(90);
				}else if(up){
					up = false;
					right = true;
					//this.setRotate(180);
				}else if(right){
					right = false;
					down = true;
					//this.setRotate(270);
				}else if(down){
					down = false;
					left = true;
					//this.setRotate(360);
				}
				break;
				
			case STRAIGHT:
				if(left && right){
					
					left = false;
					right = false;
					up = true;
					down = true;
					
				}else if(up && down){
					up = false;
					down = false;
					left = true;
					right = true;
					
				}
				
				break;
				
			case TEE:
				if(animate){
					//System.out.println("curr:"+this.getRotate()+" rnd:"+pastRotations+" "+left+" "+up+" "+" "+right+" "+down+" rot:"+teeRot);
				}
				if(left && up && right){
					
					left = false;
					down = true;
					
				}else if(up && right && down){
					up = false;
					left = true;
					
				}else if(right && down && left){
					right = false;
					up = true;
					
				}else if(down && left && up){
					
					down = false;
					right = true;
					
				}
				
				
				if(animate){
					//System.out.println("curr:"+this.getRotate()+" rnd:"+pastRotations+" "+left+" "+up+" "+" "+right+" "+down+" rot:"+teeRot);
				}
				
				//this.setRotate(this.getRotate()+90);
				break;
				
			case EMPTY:
				break;
				
			default:
				break;
		}
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @return the col
	 */
	public int getColumn() {
		return col;
	}

	/**
	 * @param col the col to set
	 */
	public void setColumn(int col) {
		this.col = col;
	}

}
