package jpp.infinityloop.boardalgorithms;

import java.util.concurrent.ThreadLocalRandom;

import jpp.infinityloop.board.Board;
import jpp.infinityloop.gui.TileType;

public class RandomBoard {

	private int width = 0, height = 0, actualDeadEndEdges = 0, actualDeadEndCenter = 0;
	private int balanceBEND = 0, balanceCROSS = 0, balanceDEADEND = 0, balanceSTRAIGHT = 0, balanceTEE = 0, balanceEMPTY = 0;
	
	public RandomBoard() {
		

	}

	public Board createRandomBoard(){
		
		int rngW = ThreadLocalRandom.current().nextInt(4, 6); //51
		int rngH = ThreadLocalRandom.current().nextInt(4, 6); //51
		
		actualDeadEndEdges = 0;
		actualDeadEndCenter = 0;
		rngW = 10; rngH = 10; //TODO REMOVE
		
		this.width = rngW;
		this.height = rngH;
		
	    byte[] dataBytes = new byte[rngW*rngH];
	    
	    System.out.println("W: "+rngW+" H: "+rngH);
	    
	    LogicTile[][] array = new LogicTile[rngH][rngW];
	    
	    /** Initialise Array **/
	   	for (int i = 0; i < rngH; i++) {
			for (int j = 0; j < rngW; j++) {
												//left	 up 	right  down
				array[i][j] = new LogicTile(TileType.EMPTY, false, false, false, false);
			}
		}
	   	int amountEdgeTiles = 2*(rngW-2)+2*(rngH-2);
	   	int amountCenterTiles = (rngW-2)*(rngH-2);
	   	double emptyTilesPercent = ((0.25*4+0.125*amountEdgeTiles+0.0625*amountCenterTiles)/(rngW*rngH));
	   	int amountNonEmptyTiles = (int) (rngW*rngH*(1-emptyTilesPercent));
	   	int filledTiles = 0;
	    System.out.println("Amount of non empty tiles: " + amountNonEmptyTiles);
	   	
	    int supposedDeadEndEdges = (int) (0.375*amountEdgeTiles);
	    int supposedDeadEndCenter = (int) (0.25*amountCenterTiles);
	    
	    /**
	     * Distributions
	     * 			cross		tee			bend		straight	dead end	empty
	     * corners	0.0000		0.0000		0.2500		0.0000		0.5000		0.2500
	     * edges	0.0000		0.1250		0.2500		0.1250		0.3750		0.1250
	     * center	0.0625		0.2500		0.2500		0.1250		0.2500		0.0625
	     */
	    
	    /** Create corner tiles. **/
	    
	    //Corner 1 upper-left
	    array[0][0] = getUpperLeftCorner(getRandomCornerType());
	    
	    //Corner 2	upper-right
	    array[0][rngW-1] = getUpperRightCorner(getRandomCornerType());
	    
	    //Corner 3	lower-left
	    array[rngH-1][0] = getLowerLeftCorner(getRandomCornerType());
	    
	    //Corner 4	upper-right
	    array[rngH-1][rngW-1] = getLowerRightCorner(getRandomCornerType());
	    
	    filledTiles = 4;
	    
	    /** Create edge tiles. **/
	    //Generate upper edge
	    for (int i = 1; i < rngW-1; i++) {
			array[0][i] = getUpperEdgeTile(getRandomEdgeType());
			filledTiles++;
		}
	    
	    //Generate left edge
	    for (int i = 1; i < rngH-1; i++) {
			array[i][0] = getLeftEdgeTile(getRandomEdgeType());
			filledTiles++;
		}
	    
	    //Generate right edge
	    for (int i = 1; i < rngH-1; i++) {
			array[i][rngW-1] = getRightEdgeTile(getRandomEdgeType());
			filledTiles++;
	    }
	    
	    //Generate lower edge
	    for (int i = 1; i < rngW-1; i++) {
			array[rngH-1][i] = getLowerEdgeTile(getRandomEdgeType());
			filledTiles++;
	    }
	    
	    //Generate center tiles
	    for (int i = 1; i < rngH-1; i++) {
			for (int j = 1; j < rngW-1; j++) {
				array[i][j] = getCenterTile(getRandomCenterType());
				filledTiles++;
			}
		}
	    
	    System.out.println("Expect "+amountNonEmptyTiles+" tiles to be filled. Of these "+filledTiles+" are already filled.");
	    System.out.println("Expect "+supposedDeadEndEdges+" edge tiles to be dead ends."+" There are currently "+actualDeadEndEdges+" such tiles.");
	    System.out.println("Expect "+supposedDeadEndCenter+" center tiles to be dead ends."+" There are currently "+actualDeadEndCenter +" such tiles.");
	    
	    balanceEMPTY = 0;
	    balanceDEADEND = 0; //(actualDeadEndEdges - supposedDeadEndEdges) + (actualDeadEndCenter - supposedDeadEndCenter);
	    
	    array = rotateArrayToSolve(array);
	    array = replaceCurves(array);
	    System.out.println("BT2: "+ balanceTEE + " BC2: "+ balanceBEND);
	    
	    //array
	    
	    //Try to fix it with purposeful turns
	    //Try to fix it with switching non-empty tiles with empty tiles
	    
	    System.out.println(print(array));
	    //System.exit(-1);
	    Board board = new Board(print(array), print(array), rngW, rngH);
	    board.setTiledata(print(array));
	    board.setColumns(rngW);
	    board.setRows(rngH);
		return board;
	}
	
	private LogicTile[][] replaceCurves(LogicTile[][] array){
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				if(array[row][column].getType() == TileType.BEND){
					if(column == 0 && row != 0 && row != height-1){
						if(array[row+1][column].hasUp() && array[row-1][column].hasDown() && array[row][column+1].hasLeft() ){
							array[row][column].setUp(true);
							array[row][column].setDown(true);
							array[row][column].setType(TileType.TEE);
							balanceTEE++;
							balanceBEND--;
						}
						//TODO continue replace curves, with straights, afterwards implement replace straights and tees with curves
					}else if(column == width-1 && row != 0 && row != height-1){
						if(array[row+1][column].hasUp() && array[row-1][column].hasDown() && array[row][column-1].hasRight() ){
							array[row][column].setUp(true);
							array[row][column].setDown(true);
							array[row][column].setType(TileType.TEE);
							balanceTEE++;
							balanceBEND--;
						}
					}else if(row == 0 && column != 0 && column != width-1){
						if(array[row][column+1].hasLeft() && array[row+1][column].hasUp() && array[row][column-1].hasRight() ){
							array[row][column].setLeft(true);
							array[row][column].setRight(true);
							array[row][column].setType(TileType.TEE);
							balanceTEE++;
							balanceBEND--;
						}
					}else if(row == height-1 && column != 0 && column != width-1){
						if(array[row][column+1].hasLeft() && array[row][column-1].hasRight() && array[row-1][column].hasDown() ){
							array[row][column].setLeft(true);
							array[row][column].setRight(true);
							array[row][column].setType(TileType.TEE);
							balanceTEE++;
							balanceBEND--;
						}
					}else if(column != 0 && column != width-1 && row != 0 && row != height-1){
						if(!array[row][column+1].hasLeft() && array[row+1][column].hasUp() && array[row][column-1].hasRight() && array[row-1][column].hasDown() ){
							array[row][column].setLeft(true);
							array[row][column].setUp(true);
							array[row][column].setRight(false);
							array[row][column].setDown(true);
							array[row][column].setType(TileType.TEE);
							balanceTEE++;
							balanceBEND--;
						}else if(array[row][column+1].hasLeft() && !array[row+1][column].hasUp() && array[row][column-1].hasRight() && array[row-1][column].hasDown() ){
							array[row][column].setLeft(true);
							array[row][column].setUp(true);
							array[row][column].setRight(true);
							array[row][column].setDown(false);
							array[row][column].setType(TileType.TEE);
							balanceTEE++;
							balanceBEND--;
						}else if(array[row][column+1].hasLeft() && array[row+1][column].hasUp() && !array[row][column-1].hasRight() && array[row-1][column].hasDown() ){
							array[row][column].setLeft(false);
							array[row][column].setUp(true);
							array[row][column].setRight(true);
							array[row][column].setDown(true);
							array[row][column].setType(TileType.TEE);
							balanceTEE++;
							balanceBEND--;
						}else if(array[row][column+1].hasLeft() && array[row+1][column].hasUp() && array[row][column-1].hasRight() && !array[row-1][column].hasDown() ){
							array[row][column].setLeft(true);
							array[row][column].setUp(false);
							array[row][column].setRight(true);
							array[row][column].setDown(true);
							array[row][column].setType(TileType.TEE);
							balanceTEE++;
							balanceBEND--;
						}
					}
				}
			}
		}
		return array;
	}
	
	private LogicTile[][] rotateArrayToSolve(LogicTile[][] array){
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				
				/** Check right.**/
				if(array[row][column].hasRight() &&column+1 != width-1 ){
					//Turn to right if possible.
					if(!array[row][column+1].hasLeft()){ 
						if(row == 0 || row == height-1){
							if(array[row][column+1].getType() == TileType.BEND && !array[row][column+1].isSwitchedLR()){
								array[row][column+1].setRight(false);
								array[row][column+1].setLeft(true);
								array[row][column+1].setSwitchedLR(true);
							}else if(array[row][column+1].getType() == TileType.DEADEND && !array[row][column+1].isSwitchedLR() && array[row][column+1].hasRight() ){
								array[row][column+1].setRight(false);
								array[row][column+1].setLeft(true);
								array[row][column+1].setSwitchedLR(true);
							}
						}else{
							if(array[row][column+1].getType() == TileType.BEND && !array[row][column+1].isSwitchedLR()){
								array[row][column+1].setRight(false);
								array[row][column+1].setLeft(true);
								array[row][column+1].setSwitchedLR(true);
							}else if(array[row][column+1].getType() == TileType.DEADEND && !array[row][column+1].isSwitchedLR() && array[row][column+1].hasRight() ){
								array[row][column+1].setRight(false);
								array[row][column+1].setLeft(true);
								array[row][column+1].setSwitchedLR(true);
							}else if(array[row][column+1].getType() == TileType.STRAIGHT && !array[row][column+1].isSwitchedLR()){
								array[row][column+1].setRight(true);
								array[row][column+1].setLeft(true);
								array[row][column+1].setUp(false);
								array[row][column+1].setDown(false);
								array[row][column+1].setSwitchedLR(true);
							}else if(array[row][column+1].getType() == TileType.TEE && !array[row][column+1].isSwitchedLR()){
								if(array[row][column+1].hasUp()&&array[row][column+1].hasRight()&&array[row][column+1].hasDown()){
									array[row][column+1].setRight(false);
									array[row][column+1].setLeft(true);
									array[row][column+1].setSwitchedLR(true);
								}
								
							}
						}
					}
				}
				
				/** Check left. **/
				if(array[row][column].hasLeft() && column-1 != 0 ){
					//Turn to left if possible.
					if(!array[row][column-1].hasRight()){ 
						if(row == 0 || row == height-1){
							if(array[row][column-1].getType() == TileType.BEND && !array[row][column-1].isSwitchedLR()){
								array[row][column-1].setRight(true);
								array[row][column-1].setLeft(false);
								array[row][column-1].setSwitchedLR(true);
							}else if(array[row][column-1].getType() == TileType.DEADEND && !array[row][column-1].isSwitchedLR() && array[row][column-1].hasLeft() ){
								array[row][column-1].setRight(true);
								array[row][column-1].setLeft(false);
								array[row][column-1].setSwitchedLR(true);
							}
						}else{
							if(array[row][column-1].getType() == TileType.BEND && !array[row][column-1].isSwitchedLR()){
								array[row][column-1].setRight(true);
								array[row][column-1].setLeft(false);
								array[row][column-1].setSwitchedLR(true);
							}else if(array[row][column-1].getType() == TileType.DEADEND && !array[row][column-1].isSwitchedLR() && array[row][column-1].hasLeft() ){
								array[row][column-1].setRight(true);
								array[row][column-1].setLeft(false);
								array[row][column-1].setSwitchedLR(true);
							}else if(array[row][column-1].getType() == TileType.STRAIGHT && !array[row][column-1].isSwitchedLR() ){
								array[row][column-1].setRight(true);
								array[row][column-1].setLeft(true);
								array[row][column-1].setUp(false);
								array[row][column-1].setDown(false);
								array[row][column-1].setSwitchedLR(true);
							}else if(array[row][column-1].getType() == TileType.TEE && !array[row][column-1].isSwitchedLR()){
								if(array[row][column-1].hasUp()&&array[row][column-1].hasLeft()&&array[row][column-1].hasDown()){
									array[row][column-1].setRight(true);
									array[row][column-1].setLeft(false);
									array[row][column-1].setSwitchedLR(true);
								}
								
							}
						}
					}
				}
				
				/** Check up. **/
				if(array[row][column].hasUp() ){ //&& row != 0  //check should not be needed as row0 should never have an up
					//Turn to down if possible.
					if(!array[row-1][column].hasDown() && !array[row-1][column].isSwitchedUD()){ 
						if(column == 0 || column == width-1){
							if(array[row-1][column].getType() == TileType.BEND ){
								array[row-1][column].setUp(false);
								array[row-1][column].setDown(true);
								array[row-1][column].setSwitchedUD(true);
							}else if(array[row-1][column].getType() == TileType.DEADEND && array[row-1][column].hasUp() ){
								array[row-1][column].setUp(false);
								array[row-1][column].setDown(true);
								array[row-1][column].setSwitchedUD(true);
							}
						}else{
							if(array[row-1][column].getType() == TileType.BEND ){
								array[row-1][column].setUp(false);
								array[row-1][column].setDown(true);
								array[row-1][column].setSwitchedUD(true);
							}else if(array[row-1][column].getType() == TileType.DEADEND && array[row-1][column].hasUp() ){
								array[row-1][column].setUp(false);
								array[row-1][column].setDown(true);
								array[row-1][column].setSwitchedUD(true);
							}else if(array[row-1][column].getType() == TileType.STRAIGHT ){
								array[row-1][column].setRight(false);
								array[row-1][column].setLeft(false);
								array[row-1][column].setUp(false);
								array[row-1][column].setDown(true);
								array[row-1][column].setSwitchedUD(true);
							}else if(array[row-1][column].getType() == TileType.TEE ){
								if(array[row-1][column].hasUp()&&array[row-1][column].hasRight()&&array[row-1][column].hasLeft()){
									array[row-1][column].setUp(false);
									array[row-1][column].setDown(true);
									array[row-1][column].setSwitchedUD(true);
								}
								
							}
						}
					}
				}
				
				/** Check down. **/	
				if(array[row][column].hasDown() ){ //&& row != 0  //check should not be needed as row0 should never have an up
					//Turn to down if possible.
					if(row == 9){
						System.out.println("R: "+row+" C: "+column);
					}try {
						

					System.out.println(row+1);
					if(!array[row+1][column].hasUp() && !array[row+1][column].isSwitchedUD()){ 
						if(column == 0 || column == width-1){
							if(array[row+1][column].getType() == TileType.BEND ){
								array[row+1][column].setUp(true);
								array[row+1][column].setDown(false);
								array[row+1][column].setSwitchedUD(true);
							}else if(array[row+1][column].getType() == TileType.DEADEND && array[row+1][column].hasDown() ){
								array[row+1][column].setUp(true);
								array[row+1][column].setDown(false);
								array[row+1][column].setSwitchedUD(true);
							}
						}else{
							if(array[row+1][column].getType() == TileType.BEND ){
								array[row+1][column].setUp(true);
								array[row+1][column].setDown(false);
								array[row+1][column].setSwitchedUD(true);
							}else if(array[row+1][column].getType() == TileType.DEADEND && array[row+1][column].hasDown() ){
								array[row+1][column].setUp(true);
								array[row+1][column].setDown(false);
								array[row+1][column].setSwitchedUD(true);
							}else if(array[row+1][column].getType() == TileType.STRAIGHT ){
								array[row+1][column].setRight(false);
								array[row+1][column].setLeft(false);
								array[row+1][column].setUp(true);
								array[row+1][column].setDown(true);
								array[row+1][column].setSwitchedUD(true);
							}else if(array[row+1][column].getType() == TileType.TEE ){
								if(array[row+1][column].hasLeft()&&array[row+1][column].hasRight()&&array[row+1][column].hasDown()){
									array[row+1][column].setUp(true);
									array[row+1][column].setDown(false);
									array[row+1][column].setSwitchedUD(true);
								}
								
							}
						}
					}
					
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				
				
			}
		}
		return array;
	}
	
	private String print(LogicTile[][] logicTileRaw) {
		String printString = "";
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				printString = printString + getTileString(logicTileRaw[i][j]);
			}
			printString = printString + "\n"; // System.lineSeparator(); //TODO put back in
		}
		return printString;
	}
	
	private String getTileString(LogicTile tile){
		String tileString = "";
		if(tile.hasLeft()){
			if(tile.hasUp()){
				if(tile.hasRight()){
					if(tile.hasDown()){
						tileString = "\u254B";
					}else{
						tileString = "\u253B";
					}
				}else{
					if(tile.hasDown()){
						tileString = "\u252B";
					}else{
						tileString = "\u251B";
					}
				}
			}else{
				if(tile.hasRight()){
					if(tile.hasDown()){
						tileString = "\u2533";
					}else{
						tileString = "\u2501";
					}
				}else{
					if(tile.hasDown()){
						tileString = "\u2513";
					}else{
						tileString = "\u2578";
					}
				}
			}
		}else{
			if(tile.hasUp()){
				if(tile.hasRight()){
					if(tile.hasDown()){
						tileString = "\u2523";
					}else{
						tileString = "\u2517";
					}
				}else{
					if(tile.hasDown()){
						tileString = "\u2503";
					}else{
						tileString = "\u2579";
					}
				}
			}else{
				if(tile.hasRight()){
					if(tile.hasDown()){
						tileString = "\u250F";
					}else{
						tileString = "\u257A";
					}
				}else{
					if(tile.hasDown()){
						tileString = "\u257B";
					}else{
						//tileString = "\u2007";
						tileString = "\u25CB";
					}
				}
			}
		}
		return tileString;
	}

	private TileType getRandomCornerType(){
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
	
	private LogicTile getUpperLeftCorner(TileType cornerType){
		LogicTile tile = null;
	    if(cornerType == TileType.EMPTY){
	    	tile = new LogicTile(cornerType, false, false, false, false);
	    }else if(cornerType == TileType.DEADEND){
	    	if(Math.random() < 0.5){
	    		tile = new LogicTile(cornerType, false, false, true, false);
	    	}else{
	    		tile = new LogicTile(cornerType, false, false, false, true);
	    	}
	    }else if(cornerType == TileType.BEND){
	    	tile = new LogicTile(cornerType, false, false, true, true);
	    }
	    return tile;
	}
	
	private LogicTile getUpperRightCorner(TileType cornerType){
		LogicTile tile = null;
	    if(cornerType == TileType.EMPTY){
	    	tile = new LogicTile(cornerType, false, false, false, false);
	    }else if(cornerType == TileType.DEADEND){
	    	if(Math.random() < 0.5){
	    		tile = new LogicTile(cornerType, true, false, false, false);
	    	}else{
	    		tile = new LogicTile(cornerType, false, false, false, true);
	    	}
	    }else if(cornerType == TileType.BEND){
	    	tile = new LogicTile(cornerType, true, false, false, true);
	    }
	    return tile;
	}
	
	private LogicTile getLowerLeftCorner(TileType cornerType){
		LogicTile tile = null;
	    if(cornerType == TileType.EMPTY){	//left	up 	   right  down
	    	tile = new LogicTile(cornerType, false, false, false, false);
	    }else if(cornerType == TileType.DEADEND){
	    	if(Math.random() < 0.5){
	    		tile = new LogicTile(cornerType, false, true, false, false);
	    	}else{
	    		tile = new LogicTile(cornerType, false, false, true, false);
	    	}
	    }else if(cornerType == TileType.BEND){
	    	tile = new LogicTile(cornerType, false, true, true, false);
	    }
	    return tile;
	}	
	
	private LogicTile getLowerRightCorner(TileType cornerType){
		LogicTile tile = null;
	    if(cornerType == TileType.EMPTY){
	    	tile = new LogicTile(cornerType, false, false, false, false);
	    }else if(cornerType == TileType.DEADEND){
	    	if(Math.random() < 0.5){
	    		tile = new LogicTile(cornerType, true, false, false, false);
	    	}else{
	    		tile = new LogicTile(cornerType, false, true, false, false);
	    	}
	    }else if(cornerType == TileType.BEND){
	    	tile = new LogicTile(cornerType, true, true, false, false);
	    }
	    return tile;
	}	
	
	private TileType getRandomEdgeType(){
		TileType type = TileType.EMPTY;
		
		double rn = Math.random();
		if(rn<0.125){
			type = TileType.TEE;
		}else if(rn<0.375){
			type = TileType.BEND;
		}else if(rn<0.5){
			type = TileType.STRAIGHT;
		}else if(rn<0.5+(0.375/2)){ //
			type = TileType.DEADEND;
		    actualDeadEndEdges++;
		}else{
			type = TileType.EMPTY;
		}
		
		return type;
	}
	
	private LogicTile getUpperEdgeTile(TileType edgeType){
		LogicTile tile = null;
		if(edgeType == TileType.TEE){
			tile = new LogicTile(edgeType, true, false, true, true);
		}else if(edgeType == TileType.BEND){
			if(Math.random() < 0.5){
				tile = new LogicTile(edgeType, true, false, false, true);
			}else{
				tile = new LogicTile(edgeType, false, false, true, true);
			}
		}else if(edgeType == TileType.STRAIGHT){
			tile = new LogicTile(edgeType, true, false, true, false);
		}else if(edgeType == TileType.DEADEND){
			double rn = Math.random();
			if(rn < 0.33){
				tile = new LogicTile(edgeType, true, false, false, false);
			}else if(rn < 0.66){
				tile = new LogicTile(edgeType, false, false, true, false);
			}else{
				tile = new LogicTile(edgeType, false, false, false, true);
			}
		}else{
			tile = new LogicTile(edgeType, false, false, false, false);
		}
		return tile;
	}
	
	private LogicTile getLeftEdgeTile(TileType edgeType){
		LogicTile tile = null;
		if(edgeType == TileType.TEE){
			tile = new LogicTile(edgeType, false, true, true, true);
		}else if(edgeType == TileType.BEND){
			if(Math.random() < 0.5){
				tile = new LogicTile(edgeType, false, true, true, false);
			}else{
				tile = new LogicTile(edgeType, false, false, true, true);
			}
		}else if(edgeType == TileType.STRAIGHT){
			tile = new LogicTile(edgeType, false, true, false, true);
		}else if(edgeType == TileType.DEADEND){
			double rn = Math.random();
			if(rn < 0.33){
				tile = new LogicTile(edgeType, false, true, false, false);
			}else if(rn < 0.66){
				tile = new LogicTile(edgeType, false, false, false, true);
			}else{
				tile = new LogicTile(edgeType, false, false, true, false);
			}
		}else{
			tile = new LogicTile(edgeType, false, false, false, false);
		}
		return tile;
	}
	
	private LogicTile getRightEdgeTile(TileType edgeType){
		LogicTile tile = null;
		if(edgeType == TileType.TEE){
			tile = new LogicTile(edgeType, true, true, false, true);
		}else if(edgeType == TileType.BEND){
			if(Math.random() < 0.5){
				tile = new LogicTile(edgeType, true, true, false, false);
			}else{
				tile = new LogicTile(edgeType, true, false, false, true);
			}
		}else if(edgeType == TileType.STRAIGHT){
			tile = new LogicTile(edgeType, false, true, false, true);
		}else if(edgeType == TileType.DEADEND){
			double rn = Math.random();
			if(rn < 0.33){
				tile = new LogicTile(edgeType, false, true, false, false);
			}else if(rn < 0.66){
				tile = new LogicTile(edgeType, false, false, false, true);
			}else{
				tile = new LogicTile(edgeType, true, false, false, false);
			}
		}else{
			tile = new LogicTile(edgeType, false, false, false, false);
		}
		return tile;
	}	
	
	private LogicTile getLowerEdgeTile(TileType edgeType){
		LogicTile tile = null;
		if(edgeType == TileType.TEE){
			tile = new LogicTile(edgeType, true, true, true, false);
		}else if(edgeType == TileType.BEND){
			if(Math.random() < 0.5){
				tile = new LogicTile(edgeType, true, true, false, false);
			}else{
				tile = new LogicTile(edgeType, false, true, true, false);
			}
		}else if(edgeType == TileType.STRAIGHT){
			tile = new LogicTile(edgeType, true, false, true, false);
		}else if(edgeType == TileType.DEADEND){
			double rn = Math.random();
			if(rn < 0.33){
				tile = new LogicTile(edgeType, true, false, false, false);
			}else if(rn < 0.66){
				tile = new LogicTile(edgeType, false, false, true, false);
			}else{
				tile = new LogicTile(edgeType, false, true, false, false);
			}
		}else{
			tile = new LogicTile(edgeType, false, false, false, false);
		}
		return tile;
	}
	
	private TileType getRandomCenterType(){
		TileType type = TileType.EMPTY;
		
		double rn = Math.random();
		if(rn < 0.0625){
			type = TileType.CROSS;
		}else if(rn<0.3125){ 
			type = TileType.TEE;
		}else if(rn<0.5625){
			type = TileType.BEND;
		}else if(rn<0.6875){
			type = TileType.STRAIGHT;
		}else if(rn<0.8125){ //
			type = TileType.DEADEND;
		    actualDeadEndCenter++;
		}else{
			type = TileType.EMPTY;
		}
		
		return type;
	}
	
	private LogicTile getCenterTile(TileType centerType){
		LogicTile tile = null;
		if(centerType == TileType.CROSS){
			tile = new LogicTile(centerType, true, true, true, true);
		}else if(centerType == TileType.TEE){
			double rn = Math.random();
			if(rn < 0.25){
				tile = new LogicTile(centerType, false, true, true, true);
			}else if(rn < 0.5){
				tile = new LogicTile(centerType, true, false, true, true);
			}else if(rn < 0.75){
				tile = new LogicTile(centerType, true, true, false, true);
			}else{
				tile = new LogicTile(centerType, true, true, true, false);
			}
			
		}else if(centerType == TileType.BEND){
			double rn = Math.random();
			if(rn < 0.25){
				tile = new LogicTile(centerType, true, true, false, false);
			}else if(rn < 0.5){
				tile = new LogicTile(centerType, false, true, true, false);
			}else if(rn < 0.75){
				tile = new LogicTile(centerType, true, false, false, true);
			}else{
				tile = new LogicTile(centerType, false, false, true, true);
			}
		}else if(centerType == TileType.STRAIGHT){
			if(Math.random() < 0.5){
				tile = new LogicTile(centerType, true, false, true, false);
			}else{
				tile = new LogicTile(centerType, false, true, true, false);
			}
		}else if(centerType == TileType.DEADEND){
			double rn = Math.random();
			if(rn < 0.25){
				tile = new LogicTile(centerType, true, false, false, false);
			}else if(rn < 0.5){
				tile = new LogicTile(centerType, false, true, false, false);
			}else if(rn < 0.75){
				tile = new LogicTile(centerType, false, false, true, false);
			}else{
				tile = new LogicTile(centerType, false, false, false, true);
			}
		}else{
			tile = new LogicTile(centerType, false, false, false, false);
		}
		return tile;
	}
	
}
