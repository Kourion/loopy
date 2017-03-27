package jpp.infinityloop.gui;

import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile extends Button{

	Image icon = new Image(getClass().getResource("tileicons\\bend.png").toExternalForm() );
	TileType type = TileType.EMPTY;
	boolean up = false, right = false, down = false, left = false;
	int column = 0, row = 0;
	private ImageView tileImageView;
	private StyleType coloring = StyleType.AliceBlue;
	private String hexColor = "#000000";
	private boolean hasHexColor = false;
	private int blendMode = 0;
	
	public Tile(TileType type, Image icon, StyleType newStyle, boolean up, boolean right, boolean down, boolean left, int column, int row, int blendMode){
		super();
		this.type = type;
		this.icon = icon;
		this.coloring = newStyle;
		this.up = up;
		this.right = right;
		this.down = down;
		this.left = left;
		this.column = column;
		this.row = row;
		this.setTileImageView(new ImageView(icon));
		this.blendMode = blendMode;
		
		getTileImageView().setScaleX(0.05);
		getTileImageView().setScaleY(0.05);
		this.setGraphic(getTileImageView());
		
		//Rotation of the tile 4 times ensures its proper direction on game start.
		this.rotateTile();
		this.rotateTile();
		this.rotateTile();
		this.rotateTile();
	}
	
	public void rotateTile(){
		switch (type){
			case BEND:
				if(up&&right){
					up = false;
					down = true;
					this.setRotate(180);
				}else if(right&&down){
					right = false;
					left = true;
					this.setRotate(270);
				}else if(down&&left){
					down = false;
					up = true;
					this.setRotate(360);
				}
				else if(left&&up){
					left = false;
					right = true;
					this.setRotate(90);
					//this.setRotate(this.getRotate()+90);
				}
				break;
				
			case CROSS:
				this.setRotate(this.getRotate()+90);
				break;
				
			case DEADEND:
				if(left){
					left = false;
					up = true;
					this.setRotate(90);
				}else if(up){
					up = false;
					right = true;
					this.setRotate(180);
				}else if(right){
					right = false;
					down = true;
					this.setRotate(270);
				}else if(down){
					down = false;
					left = true;
					this.setRotate(360);
				}
				//this.setRotate(this.getRotate()+90);
				break;
				
			case STRAIGHT:
				if(left && right){
					left = false;
					right = false;
					up = true;
					down = true;
					if(this.getRotate() == 0 || this.getRotate() == 360){
						this.setRotate(90);
					}else if(this.getRotate() == 180){
						this.setRotate(270);
					}
				}else if(up && down){
					up = false;
					down = false;
					left = true;
					right = true;
					if(this.getRotate() == 90){
						this.setRotate(180);
					}else if(this.getRotate() == 270){
						this.setRotate(360);
					}
				}
				
				break;
				
			case TEE:
				if(left && up && right){
					left = false;
					down = true;
					this.setRotate(90);
				}else if(up && right && down){
					up = false;
					left = true;
					this.setRotate(180);
				}else if(right && down && left){
					right = false;
					up = true;
					this.setRotate(270);
				}else if(down && left && up){
					down = false;
					right = true;
					this.setRotate(360);
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
	 * @return the icon of the tile
	 */
	public Image getIcon() {
		return icon;
	}


	/**
	 * @param icon the icon to set the tile to
	 */
	public void setIcon(Image icon) {
		this.icon = icon;
		this.setTileImageView(new ImageView(icon));
		this.setGraphic(getTileImageView());
	}
	
	/**
	 * @param icon the icon to set the tile to
	 */
	public void setIconSize(double tileSize) {
		this.setTileImageView(new ImageView(icon));
		
		double iconSize = 500/tileSize;
		iconSize = (1/iconSize);
		//System.out.println(iconSize);
		iconSize = iconSize + 0.001;
		iconSize = iconSize*1000;
		iconSize = Math.ceil(iconSize);
		iconSize = iconSize/1000;
		getTileImageView().setScaleX(iconSize);
		getTileImageView().setScaleY(iconSize);
		this.setGraphic(getTileImageView());
		this.setBlendModeSetting();
		//tileImageView.setBlendMode(BlendMode.DIFFERENCE);
	}

	/** Setting the blend-mode to an appropriate value depending on color. **/
	public void setBlendModeSetting(){
		//blendMode = 0;
		//if(!hasHexColor||true){
			switch (this.getColoring()) {
				case AliceBlue:
						getTileImageView().setBlendMode(BlendMode.EXCLUSION);
					break;
				case Beige:
						getTileImageView().setBlendMode(BlendMode.EXCLUSION);
					break;
				case BlueViolet:
					if(blendMode == 0){
						getTileImageView().setBlendMode(BlendMode.OVERLAY);
					}else if(blendMode == 1){
						getTileImageView().setBlendMode(BlendMode.EXCLUSION);
					}else if(blendMode == 2 || blendMode == 3){
						getTileImageView().setBlendMode(BlendMode.DIFFERENCE);
					}
					break;	
				case CadetBlue:
					//blendMode = 3;
					if(blendMode == 0){
						getTileImageView().setBlendMode(BlendMode.OVERLAY);
					}else if(blendMode == 1){
						getTileImageView().setBlendMode(BlendMode.EXCLUSION);
					}else if(blendMode == 2){
						getTileImageView().setBlendMode(BlendMode.DIFFERENCE);
					}else if(blendMode == 3){
						getTileImageView().setBlendMode(BlendMode.COLOR_DODGE);
					}
					break;	
				case Chartreuse:
					//if(blendMode == 0 ){
						//getTileImageView().setBlendMode(BlendMode.OVERLAY);
					//}else 
					if(blendMode == 1 || blendMode == 0){
						getTileImageView().setBlendMode(BlendMode.EXCLUSION);
					}else if(blendMode == 2){
						getTileImageView().setBlendMode(BlendMode.DIFFERENCE);
					}else if(blendMode == 3){
						getTileImageView().setBlendMode(BlendMode.COLOR_DODGE);
					}
					break;	
				case DarkMagenta:
					if(blendMode == 0){
						getTileImageView().setBlendMode(BlendMode.OVERLAY);
					}else if(blendMode == 1){
						getTileImageView().setBlendMode(BlendMode.EXCLUSION);
					}else if(blendMode == 2){
						getTileImageView().setBlendMode(BlendMode.DIFFERENCE);
					}else if(blendMode == 3){
						getTileImageView().setBlendMode(BlendMode.COLOR_DODGE);
					}
					break;	
				case DarkOrange:
					if(blendMode == 0){
						getTileImageView().setBlendMode(BlendMode.OVERLAY);
					}else if(blendMode == 1){
						getTileImageView().setBlendMode(BlendMode.EXCLUSION);
					}else if(blendMode == 2){
						getTileImageView().setBlendMode(BlendMode.DIFFERENCE);
					}else if(blendMode == 3){
						getTileImageView().setBlendMode(BlendMode.COLOR_DODGE);
					}
					break;	
				case DeepSkyBlue:
					if(blendMode == 0){
						getTileImageView().setBlendMode(BlendMode.OVERLAY);
					}else if(blendMode == 1){
						getTileImageView().setBlendMode(BlendMode.EXCLUSION);
					}else if(blendMode == 2){
						getTileImageView().setBlendMode(BlendMode.DIFFERENCE);
					}else if(blendMode == 3){
						getTileImageView().setBlendMode(BlendMode.COLOR_DODGE);
					}
					break;	
				case Gold:
					if(blendMode == 0){
						getTileImageView().setBlendMode(BlendMode.OVERLAY);
					}else if(blendMode == 1){
						getTileImageView().setBlendMode(BlendMode.EXCLUSION);
					}else if(blendMode == 2){
						getTileImageView().setBlendMode(BlendMode.DIFFERENCE);
					}else if(blendMode == 3){
						getTileImageView().setBlendMode(BlendMode.COLOR_DODGE);
					}
					break;	
				case LightCoral:
					if(blendMode == 0){
						getTileImageView().setBlendMode(BlendMode.OVERLAY);
					}else if(blendMode == 1){
						getTileImageView().setBlendMode(BlendMode.EXCLUSION);
					}else if(blendMode == 2){
						getTileImageView().setBlendMode(BlendMode.DIFFERENCE);
					}else if(blendMode == 3){
						getTileImageView().setBlendMode(BlendMode.COLOR_DODGE);
					}
					break;	
				case MediumAquaMarine:
					if(blendMode == 0){
						getTileImageView().setBlendMode(BlendMode.OVERLAY);
					}else if(blendMode == 1){
						getTileImageView().setBlendMode(BlendMode.EXCLUSION);
					}else if(blendMode == 2){
						getTileImageView().setBlendMode(BlendMode.DIFFERENCE);
					}else if(blendMode == 3){
						getTileImageView().setBlendMode(BlendMode.COLOR_DODGE);
					}
					break;	
				case Thistle:
					if(blendMode == 0){
						getTileImageView().setBlendMode(BlendMode.OVERLAY);
					}else if(blendMode == 1){
						getTileImageView().setBlendMode(BlendMode.EXCLUSION);
					}else if(blendMode == 2){
						getTileImageView().setBlendMode(BlendMode.DIFFERENCE);
					}else if(blendMode == 3){
						getTileImageView().setBlendMode(BlendMode.COLOR_DODGE);
					}
					break;	
					
				default:
					getTileImageView().setBlendMode(BlendMode.COLOR_DODGE);
					break;
			}
		//}else{
		//	getTileImageView().setBlendMode(BlendMode.DIFFERENCE);
		//}
	}
	
	
	/**
	 * @return the column of the tile
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @return the row of the tile
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * @return the type of the tile
	 */
	public TileType getType() {
		return type;
	}


	/**
	 * @param type the type to set the tile to
	 */
	public void setType(TileType type) {
		this.type = type;
	}


	/**
	 * @return existence of an up connection
	 */
	public boolean hasUp() {
		return up;
	}


	/**
	 * @param up defining the status of an up connection
	 */
	public void setUp(boolean up) {
		this.up = up;
	}


	/**
	 * @return existence of an right connection
	 */
	public boolean hasRight() {
		return right;
	}


	/**
	 * @param right defining the status of an right connection
	 */
	public void setRight(boolean right) {
		this.right = right;
	}


	/**
	 * @return existence of an down connection
	 */
	public boolean hasDown() {
		return down;
	}


	/**
	 * @param down defining the status of an down connection
	 */
	public void setDown(boolean down) {
		this.down = down;
	}


	/**
	 * @return existence of an left connection
	 */
	public boolean hasLeft() {
		return left;
	}


	/**
	 * @param left defining the status of an left connection
	 */
	public void setLeft(boolean left) {
		this.left = left;
	}

	/**
	 * @return the coloring of the tile
	 */
	public StyleType getColoring() {
		return coloring;
	}

	/**
	 * @param coloring the coloring to set the tile to
	 */
	public void setColoring(StyleType coloring) {
		this.coloring = coloring;
	}

	/**
	 * @param hasHexColor the hasHexColor to set
	 */
	public void setHasHexColor(boolean hasHexColor) {
		this.hasHexColor = hasHexColor;
	}

	/**
	 * @return whether there is an additional hex-color in use
	 */
	public boolean getHasHexColor() {
		return hasHexColor;
	}
	
	/**
	 * @return the hexColor
	 */
	public String getHexColor() {
		return hexColor;
	}

	/**
	 * @param hexColor the hexColor to set
	 */
	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}

	public ImageView getTileImageView() {
		return tileImageView;
	}

	public void setTileImageView(ImageView tileImageView) {
		this.tileImageView = tileImageView;
	}


	
}
