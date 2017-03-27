package jpp.infinityloop.board;

public class Board {

	private String gamedata = "";
	private String tiledata = "";
	private int columns = 0, rows = 0;
	
	public Board() {
		// TODO Auto-generated constructor stub
	}

	public Board(String gamedata, String tiledata, int columns, int rows) {
		this.gamedata = gamedata;
		this.tiledata = tiledata;
		this.columns = columns;
		this.rows = rows;
	}

	/**
	 * @return the gamedata
	 */
	public String getGamedata() {
		return gamedata;
	}

	/**
	 * @param gamedata the gamedata to set the board to
	 */
	public void setGamedata(String gamedata) {
		this.gamedata = gamedata;
	}

	/**
	 * @return the tiledata
	 */
	public String getTiledata() {
		return tiledata;
	}

	/**
	 * @param tiledata the tiledata to set the board to
	 */
	public void setTiledata(String tiledata) {
		this.tiledata = tiledata;
	}

	/**
	 * @return the columns
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * @param columns the columns to set
	 */
	public void setColumns(int columns) {
		this.columns = columns;
	}

	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}
	
}
