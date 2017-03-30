package jpp.infinityloop.gui;


import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import jpp.infinityloop.board.Board;

/**
 * Displays the main game-interface, with which the game can be played.
 */
public class GameInterfacePane extends Pane{

	GuiControl ctrl = new GuiControl();
	private int rowCount = 0, columnCount = 0;
	private String tileData = "";
	private boolean isPredefinedBord = false, isRandomBord = false;
	private StyleType currentStyle = StyleType.AliceBlue;
	private GridPane flowgrid;
	String color = "blue";
	public GameInterfacePane(){
	}
	
	public GameInterfacePane(double width, double height, Board board){
		//int rowCount, int columnCount, String tileData
		this.setPrefSize(width, height);
		this.rowCount = board.getRows();
		this.columnCount = board.getColumns();
		if(board.getTiledata() != null && board.getTiledata() != ""){
			this.tileData = board.getTiledata();
			isPredefinedBord = true;
		}else{
			isRandomBord = true;
		}
		
		BorderPane displayPane = new BorderPane();
		flowgrid = new GridPane();
		
		/** Pick a random coloring style for the board.Create icons and choose randomly between filled and non-filled versions, as well as a blend-mode. **/
		currentStyle = randomStyle();
		
		@SuppressWarnings("unused")
		ImageView icon = null;
		Image iconImg = null;
		Image bendIcon = null;
		Image crossIcon = null;
		Image deadendIcon = null;
		Image straightIcon = null;
		Image teeIcon = null;
		Image emptyIcon = new Image(getClass().getResource("tileicons"+ File.separator + "empty.png").toExternalForm() );
		
		if(Math.random()<0.5){
			
			bendIcon = new Image( getClass().getResource("tileicons"+ File.separator + "bend.png").toExternalForm() );
			crossIcon = new Image(getClass().getResource("tileicons"+ File.separator + "cross.png").toExternalForm() );
			deadendIcon = new Image(getClass().getResource("tileicons"+ File.separator + "deadend.png").toExternalForm() );
			straightIcon = new Image(getClass().getResource("tileicons"+ File.separator + "straight.png").toExternalForm() );
			teeIcon = new Image(getClass().getResource("tileicons"+ File.separator + "tee.png").toExternalForm() );
		}else{
			bendIcon = new Image( getClass().getResource("tileicons"+ File.separator + "bend_o.png").toExternalForm() );
			crossIcon = new Image(getClass().getResource("tileicons"+ File.separator + "cross_o.png").toExternalForm() );
			deadendIcon = new Image(getClass().getResource("tileicons"+ File.separator + "deadend_o.png").toExternalForm() );
			straightIcon = new Image(getClass().getResource("tileicons"+ File.separator + "straight_o.png").toExternalForm() );
			teeIcon = new Image(getClass().getResource("tileicons"+ File.separator + "tee_o.png").toExternalForm() );
		}
		double rng = Math.random(); int blendMode;
		if(rng<0.249){
			blendMode = 0;
		}else if(rng<0.498){
			blendMode = 1;
		}else if(rng<0.747){
			blendMode = 2;
		}else{
			blendMode = 3;
		}
		System.out.println("DEBUG: Style: "+currentStyle+" BlendMode: "+blendMode);
		
		
		/** Clear the tile data string from eventual line breaks. **/
		String tempTileData = tileData;
		tempTileData.replaceAll("\n","");
		tempTileData.replaceAll("\r","");
		tempTileData.replaceAll("\r\n","");
		tempTileData.replaceAll("\n\r","");
		char[] tiles = tempTileData.toCharArray();
		
		/** Make a first attempt at setting the grid where the tiles are set on to a decent size.**/
		flowgrid.setPrefSize(width, height);
		flowgrid.setMinWidth(width);
		
		int tilesCount = 0;
		for (int currRow = 0; currRow < rowCount; currRow++) { //columnCount
			for (int currColumn = 0; currColumn < columnCount; currColumn++) { //rowCount
				if(isPredefinedBord){
					TileType type = TileType.EMPTY; 
					boolean up = false, right = false, down = false, left = false;
					
					if(tempTileData != null && tempTileData != "" && tiles != null){
						if(tiles[tilesCount] == '\n'){
							tilesCount++;
						}
						
						if(tiles[tilesCount] == '\u25CB'){
							type = TileType.EMPTY;
						}else if(tiles[tilesCount] == '\u257B'){
							type = TileType.DEADEND;
							down = true;
						}else if(tiles[tilesCount] == '\u257A'){
							type = TileType.DEADEND;
							right = true;
						}else if(tiles[tilesCount] == '\u250F'){
							type = TileType.BEND;
							right = true;
							down = true;
						}else if(tiles[tilesCount] == '\u2579'){
							type = TileType.DEADEND;
							up = true;
						}else if(tiles[tilesCount] == '\u2503'){
							type = TileType.STRAIGHT;
							up = true;
							down = true;
						}else if(tiles[tilesCount] == '\u2517'){
							type = TileType.BEND;
							up = true;
							right = true;
						}else if(tiles[tilesCount] == '\u2523'){
							type = TileType.TEE;
							up = true;
							right = true;
							down = true;
						}else if(tiles[tilesCount] == '\u2578'){
							type = TileType.DEADEND;
							left = true;
						}else if(tiles[tilesCount] == '\u2513'){
							type = TileType.BEND;
							down = true;
							left = true;
						}else if(tiles[tilesCount] == '\u2501'){
							type = TileType.STRAIGHT;
							right = true;
							left = true;
						}else if(tiles[tilesCount] == '\u2533'){
							type = TileType.TEE;
							right = true;
							down = true;
							left = true;
						}else if(tiles[tilesCount] == '\u251B'){
							type = TileType.BEND;
							up = true;
							left = true;
						}else if(tiles[tilesCount] == '\u252B'){
							type = TileType.TEE;
							up = true;
							down = true;
							left = true;
						}else if(tiles[tilesCount] == '\u253B'){
							type = TileType.TEE;
							up = true;
							right = true;
							left = true;
						}else if(tiles[tilesCount] == '\u254B'){
							type = TileType.CROSS;
							up = true;
							right = true;
							down = true;
							left = true;
						}else{
							if(tiles[tilesCount] == '\n'){
								
							}else{
								System.out.println("ERROR 3: Could not determine tile type."+" Tried parcing: "+tiles[tilesCount]);
								type = TileType.EMPTY;
							}
							
						}
						
						switch (type) { //BEND, CROSS, DEADEND, STRAIGHT, TEE, EMPTY;
						case BEND:
							iconImg = bendIcon;
							break;
						case CROSS:
							iconImg = crossIcon;
							break;
						case DEADEND:
							iconImg = deadendIcon;
							break;
						case STRAIGHT:
							iconImg = straightIcon;
							break;
						case TEE:
							iconImg = teeIcon;
							break;
						case EMPTY:
							iconImg = emptyIcon;
							break;
						default:
							iconImg = emptyIcon;
							System.out.println("Error 2: Default image set, no proper image could be indentified!");
							break;
						}
						
					}else{
						System.out.println("ERROR 1: There seems to be an empty board loaded!");
					}
					
					
					Tile gameTile = new Tile(type, iconImg, currentStyle, up, right, down, left, currColumn, currRow, blendMode);
					
					ctrl.setTileButtonStyle(gameTile, flowgrid, columnCount, rowCount);
					
					//icon.setBlendMode(BlendMode.DIFFERENCE);
					//flowgrid.getChildren().add(gameTile);
					flowgrid.add(gameTile, currColumn, currRow);
					
					ctrl.setTileButtonLogic(gameTile, flowgrid, columnCount, rowCount);
					
					tilesCount++;
				}else if(isRandomBord){	//TODO INSERT RANDOM PICK MECHANISM
					//NOW IN LAUNCH
				}
				//grid.add(rolandsButton, i, k);
			}
		}
		
		
		
	 	//displayPane.setPrefSize(200, 400);
		//displayPane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		
		displayPane.setStyle("-fx-background-color: "+ currentStyle +"; -fx-border-color: " + currentStyle + "; -fx-focus-color: " + currentStyle + "; -fx-faint-focus-color: " + currentStyle + ";");
		flowgrid.setStyle("-fx-background-color: "+ currentStyle +"; -fx-border-color: " + currentStyle + "; -fx-focus-color: " + currentStyle + "; -fx-faint-focus-color: " + currentStyle + ";");
		
		displayPane.setCenter(flowgrid);
		displayPane.setCenterShape(true);	
		
		displayPane.setPrefSize(width, height);
		
		
		
		this.getChildren().add(displayPane);
		this.setCenterShape(true);
		
		ctrl.fitToWindow(this, displayPane, flowgrid);
		//border.getChildren().add(flowgrid);
		
		//displayPane.setMinWidth(flowgrid.getMinWidth());
		//displayPane.setPrefSize(width, height);
		
		
		
		//displayPane.getChildren().add(flowgrid);	//flowgrid
		//displayPane.setAlignment(Pos.CENTER);
		//return displayPane;
		
		/** Fires the first tile 4 times, to check whether the grid is already completed. **/ //TODO RENABLE
		//((Tile) flowgrid.getChildren().get(0)).fire(); ((Tile) flowgrid.getChildren().get(0)).fire(); ((Tile) flowgrid.getChildren().get(0)).fire(); ((Tile) flowgrid.getChildren().get(0)).fire();
	}

	/**
	 * @return the current style from the StyleType enum
	 */
	public StyleType getStyleType() {
		return currentStyle;
	}

	/**
	 * @return set current style from the StyleType enum
	 */
	public void setStyleType(StyleType newStyle) {
		this.currentStyle = newStyle;
	}

	/**
	 * creates a random style which can not be the previous style
	 * @return random style which was generated
	 */
	public StyleType randomStyle(){
		StyleType newStyle = null; boolean searchingForColor = true;
		
		while(searchingForColor){
			int rng = ThreadLocalRandom.current().nextInt(0, 12);
			try {
				switch (rng) {
					case 0:
						newStyle = StyleType.AliceBlue;
						break;
					case 1:
						newStyle = StyleType.Beige;
						break;
					case 2:
						newStyle = StyleType.BlueViolet;
						break;
					case 3:
						newStyle = StyleType.CadetBlue;
						break;
					case 4:
						newStyle = StyleType.Chartreuse;
						break;
					case 5:
						newStyle = StyleType.DarkMagenta;
						break;
					case 6:
						newStyle = StyleType.DarkOrange;
						break;
					case 7:
						newStyle = StyleType.DeepSkyBlue;
						break;
					case 8:
						newStyle = StyleType.Gold;
						break;
					case 9:
						newStyle = StyleType.LightCoral;
						break;
					case 10:
						newStyle = StyleType.MediumAquaMarine;
						break;
					case 11:
						newStyle = StyleType.Thistle;
						break;
						
					default:
						newStyle = StyleType.AliceBlue;
						break;
				}
			} catch (Exception e) {
				System.out.println("ERROR X: Color chooser switch case called with unsupported rng number.");
			}	
			
			if(currentStyle == null){
				searchingForColor = false;
			}else if(currentStyle != newStyle){
				searchingForColor = false;
			}
			//System.out.println("SFC");
		}
		return newStyle;
	}
	
	/**
	 * @return the rowCount
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * @return the columnCount
	 */
	public int getColumnCount() {
		return columnCount;
	}
	
	public Tile getTile(int row, int col) {
		return (Tile) flowgrid.getChildren().get( (getColumnCount()*row)+col );
	}
	
	public Pane getPane() {
		return flowgrid;
	}
	
}
