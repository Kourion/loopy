package jpp.infinityloop.boardalgorithms;

import java.util.concurrent.ThreadLocalRandom;

import jpp.infinityloop.board.Board;

public class RandomBoard {

	public RandomBoard() {
		

	}

	public Board createRandomBoard(){
		int rngW = ThreadLocalRandom.current().nextInt(2, 51);
		int rngH = ThreadLocalRandom.current().nextInt(2, 51);
		
	    byte[] dataBytes = new byte[rngW*rngH];
		return null;
	}
	
}
