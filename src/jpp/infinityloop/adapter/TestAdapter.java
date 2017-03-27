package jpp.infinityloop.adapter;

import java.io.IOException;

import jpp.infinityloop.board.Board;
import jpp.infinityloop.readwrite.InfinityLoopReader;

public class TestAdapter implements ITestAdapter<Board>{

	private InfinityLoopReader reader = new InfinityLoopReader();
	
	@Override
	public Board decode(byte[] data) {
		
		Board board = null;
		try {
			board = reader.read(data, data.length, data.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return board;
	}

	@Override
	public byte[] encode(Board board) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean solve(Board board) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void initGenerator(int minWidth, int maxWidth, int minHeight, int maxHeight) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Board generate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rotate(Board board, int column, int row) {
		// TODO Auto-generated method stub
		
	}

}
