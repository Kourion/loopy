package jpp.infinityloop.adapter;

import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.FlowPane;
import jpp.infinityloop.board.Board;
import jpp.infinityloop.boardalgorithms.RandomBoard;
import jpp.infinityloop.gui.GameInterfacePane;
import jpp.infinityloop.readwrite.InfinityLoopReader;
import jpp.infinityloop.readwrite.InfinityLoopWriter;
import jpp.infinityloop.solver.LogicSolver;
import jpp.infinityloop.gui.Tile;

public class TestAdapter implements ITestAdapter<Board>{

	private InfinityLoopReader reader = new InfinityLoopReader();
	private InfinityLoopWriter writer = new InfinityLoopWriter();
	private int minWidth = 0, maxWidth = 0, minHeight = 0, maxHeight = 0;
	private boolean generatorInitialised;
	private RandomBoard rdm = new RandomBoard();
	
	@Override
	public Board decode(byte[] data) {
		
		Board board = null;
		try {
			board = reader.read(data, data.length, data.length, false, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return board;
	}

	@Override
	public byte[] encode(Board board) {
		try {
			return writer.getOut(board);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean solve(Board board) {
		@SuppressWarnings({ "unused", "rawtypes" })
		Dialog dialog = new Dialog();
		Alert info = new Alert(AlertType.NONE);
		GameInterfacePane gameinterface = new GameInterfacePane(1600, 900, board, false);
		LogicSolver solver = new LogicSolver(gameinterface, info);
		boolean possible = solver.solve();
		return possible;
	}

	@Override
	public void initGenerator(int minWidth, int maxWidth, int minHeight, int maxHeight) {
		this.minWidth = minWidth;
		this.maxWidth = maxWidth+1;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight+1;
		if(minWidth != 0 && maxWidth != 0 && minHeight != 0 && maxHeight != 0){
			this.generatorInitialised = true;
		}else{
			throw new IllegalArgumentException("ERROR A: You tried to create a board with a zero as argument for the random generator, which could create an empty board.");
		}
	}

	@Override
	public Board generate() {
		Board board = null;
		if(generatorInitialised){
			board = rdm.createRandomBoard(minWidth, maxWidth, minHeight, maxHeight);
		}
		if(board == null || !generatorInitialised){
			throw new IllegalStateException("ERROR B: The generator was not properly initialised for this operation. Please initialise the generator first with positive integers.");
		}
		return board;
	}

	@Override
	public void rotate(Board board, int column, int row) {
		GameInterfacePane gameinterface = new GameInterfacePane(1600, 900, board, false);
		FlowPane flowgrid = gameinterface.getFlowgrid();
		((Tile) flowgrid.getChildren().get( (board.getColumns()*row)+column )).fire();
		gameinterface.setFlowgrid(flowgrid); //This line ought to be unnecessary.
		gameinterface.getCurrentBoardData();// WRITE BACK TO BOARD //TODO check does this work?
		
	}

}
