package jpp.infinityloop.boardalgorithms;

import java.util.concurrent.ThreadLocalRandom;

import jpp.infinityloop.board.Board;
import jpp.infinityloop.gui.TileType;

public class RandomBoard {

	public RandomBoard() {
		

	}

	public Board createRandomBoard(){
	
		int rngW = ThreadLocalRandom.current().nextInt(2, 51);
		int rngH = ThreadLocalRandom.current().nextInt(2, 51);
		
	    byte[] dataBytes = new byte[rngW*rngH];
	    
	    LogicTile[][] array = new LogicTile[rngH][rngW];
	    								//left	 up 	right  down
	    array[0][0] = new LogicTile(null, false, false, false, false);
	    array[0][0].setLeft(true);
	    
	    /**
	     * Distributions
	     * 			cross		tee			bend		straight	dead end	empty
	     * corners	0.0000		0.0000		0.2500		0.0000		0.5000		0.2500
	     * edges	0.0000		0.1250		0.2500		0.1250		0.3750		0.1250
	     * center	0.0625		0.2500		0.2500		0.1250		0.2500		0.0625
	     */
	    
	    /** Create corners **/
	    //Corner 1 upperleft
	    TileType corner = getRandomCornerType();
	    if(corner == TileType.EMPTY){
	    	array[0][0] = new LogicTile(corner, false, false, false, false);
	    }else if(corner == TileType.DEADEND){
	    	if(Math.random() < 0.5){
	    		array[0][0] = new LogicTile(corner, false, false, true, false);
	    	}else{
	    		array[0][0] = new LogicTile(corner, false, false, false, true);
	    	}
	    }
	    //Corner 2

	    
		return null;
	}
	
	public TileType getRandomCornerType(){
		TileType type = TileType.EMPTY;
		
		double rn = Math.random();
		if(rn<0.5){
			type = TileType.DEADEND;
		}else if(rn<0.75){
			type = TileType.BEND;
		}else{
			type = TileType.EMPTY;
		}
		
		return type;
	}
}
