package jpp.infinityloop.gui;

import java.io.File;

import javafx.animation.RotateTransition;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Tile extends Button{

	Image icon = new Image(getClass().getResource("tileicons"+ File.separator + "bend.png").toExternalForm() );
	TileType type = TileType.EMPTY;
	private boolean up = false, right = false, down = false, left = false, first = true;
	private int column = 0, row = 0, crossRot, blendMode = 0, pastRotations = 0, deadendRot = 0, straightRot = 0, teeRot = 0;
	private ImageView tileImageView;
	private StyleType coloring = StyleType.AliceBlue;
	private String hexColor = "#000000";
	private boolean hasHexColor = false;
	RotateTransition rt = null;
	
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
		this.rotateTile(false);
		this.rotateTile(false);
		this.rotateTile(false);
		this.rotateTile(false);
	}
	
	public void rotateTile(boolean onStage){
		switch (type){
			case BEND:
				if(up&&right){
					up = false;
					down = true;
					if(onStage){	
						rt = new RotateTransition(Duration.millis(200), this);
						rt.setToAngle(180+pastRotations);
						rt.setCycleCount(1);
						rt.play();
					}
					this.setRotate(180+pastRotations);
				}else if(right&&down){
					right = false;
					left = true;
					if(onStage){
						rt = new RotateTransition(Duration.millis(200), this);
						rt.setToAngle(270+pastRotations);
						rt.setCycleCount(1);
						rt.play();
					}
					this.setRotate(270+pastRotations);
				}else if(down&&left){
					down = false;
					up = true;
					if(onStage){
						rt = new RotateTransition(Duration.millis(200), this);
						rt.setToAngle(360+pastRotations);
						rt.setCycleCount(1);
						rt.play();
					}
					this.setRotate(360+pastRotations);
					rotCtr();
				}
				else if(left&&up){
					left = false;
					right = true;
					if(onStage){
						rt = new RotateTransition(Duration.millis(200), this);
						rt.setToAngle(90+pastRotations);
						rt.setCycleCount(1);
						rt.play();
					}
					this.setRotate(90+pastRotations);
					//this.setRotate(this.getRotate()+90);
				}
				break;
				
			case CROSS:
				
				if(onStage){
					rt = new RotateTransition(Duration.millis(200), this);
					rt.setToAngle(90+crossRot);
					rt.setCycleCount(1);
					rt.play();
				}
				this.setRotate(90+crossRot);
				
				crossRot = crossRot+90;
				
				//this.setRotate(this.getRotate()+pastRotations); //TODO animate?
				
				break;
				
			case DEADEND:
				
				//System.out.println(pastRotations+" "+left+" "+up+" "+" "+right+" "+down);
				
				if(left){
					if(first){ pastRotations = 360; first = false; deadendRot = 0; }
					if(onStage){
						rt = new RotateTransition(Duration.millis(200), this);
						rt.setToAngle(90+pastRotations);
						rt.setCycleCount(1);
						rt.play();
						//deadendRot++;
					}
					deadendRot++;
					left = false;
					up = true;
					this.setRotate(90+pastRotations);
				}else if(up){
					if(onStage){
						rt = new RotateTransition(Duration.millis(200), this);
						rt.setToAngle(180+pastRotations);
						rt.setCycleCount(1);
						rt.play();
						//deadendRot++;
					}
					deadendRot++;
					up = false;
					right = true;
					this.setRotate(180+pastRotations);
				}else if(right){
					if(onStage){
						rt = new RotateTransition(Duration.millis(200), this);
						rt.setToAngle(270+pastRotations);
						rt.setCycleCount(1);
						rt.play();
						//deadendRot++;
					}
					deadendRot++;
					right = false;
					down = true;
					this.setRotate(270+pastRotations);
				}else if(down){
					if(onStage){
						rt = new RotateTransition(Duration.millis(200), this);
						rt.setToAngle(360+pastRotations);
						rt.setCycleCount(1);
						rt.play();
						//deadendRot++;
					}
					deadendRot++;
					down = false;
					left = true;
					this.setRotate(360+pastRotations);
				}
				if(deadendRot == 4){
					pastRotations = pastRotations+360;
					deadendRot = 0;
				}
				
				//this.setRotate(this.getRotate()+90);
				break;
				
			case STRAIGHT:
				if(left && right){
					
					left = false;
					right = false;
					up = true;
					down = true;
					if(this.getRotate() == 0 + pastRotations ){
						if(first){ pastRotations = 360; first = false; deadendRot = 0; }
						if(onStage){
							rt = new RotateTransition(Duration.millis(200), this);
							rt.setToAngle(90+pastRotations);
							rt.setCycleCount(1);
							rt.play();
							//deadendRot++;
						}
						this.setRotate(90+pastRotations);
						straightRot++;
					}else if(this.getRotate() == 180 + pastRotations){
						if(onStage){
							rt = new RotateTransition(Duration.millis(200), this);
							rt.setToAngle(270+pastRotations);
							rt.setCycleCount(1);
							rt.play();
							//deadendRot++;
						}
						this.setRotate(270+pastRotations);
						straightRot++;
					}
				}else if(up && down){
					up = false;
					down = false;
					left = true;
					right = true;
					if(this.getRotate() == 90 + pastRotations){
						if(onStage){
							rt = new RotateTransition(Duration.millis(200), this);
							rt.setToAngle(180+pastRotations);
							rt.setCycleCount(1);
							rt.play();
							//deadendRot++;
						}
						this.setRotate(180+pastRotations);
						straightRot++;
					}else if(this.getRotate() == 270+pastRotations){
						if(onStage){
							rt = new RotateTransition(Duration.millis(200), this);
							rt.setToAngle(360+pastRotations);
							rt.setCycleCount(1);
							rt.play();
						}
						this.setRotate(360+pastRotations);
						straightRot++;
					}
				}
				
				if(straightRot == 4){
					pastRotations = pastRotations+360;
					straightRot = 0;
				}
				
				break;
				
			case TEE:
				if(onStage){
					//System.out.println("curr:"+this.getRotate()+" rnd:"+pastRotations+" "+left+" "+up+" "+" "+right+" "+down+" rot:"+teeRot);
				}
				if(left && up && right){
					
					left = false;
					down = true;
					if(onStage){
						rt = new RotateTransition(Duration.millis(200), this);
						rt.setToAngle(90+pastRotations);
						rt.setCycleCount(1);
						rt.play();
					}
					this.setRotate(90+pastRotations);
					teeRot++;
				}else if(up && right && down){
					up = false;
					left = true;
					if(onStage){
						rt = new RotateTransition(Duration.millis(200), this);
						rt.setToAngle(180+pastRotations);
						rt.setCycleCount(1);
						rt.play();
					}
					this.setRotate(180+pastRotations);
					teeRot++;
				}else if(right && down && left){
					right = false;
					up = true;
					if(onStage){
						rt = new RotateTransition(Duration.millis(200), this);
						rt.setToAngle(270+pastRotations);
						rt.setCycleCount(1);
						rt.play();
					}
					this.setRotate(270+pastRotations);
					teeRot++;
				}else if(down && left && up){
					
					down = false;
					right = true;
					if(onStage){
						rt = new RotateTransition(Duration.millis(200), this);
						rt.setToAngle(360+pastRotations);
						rt.setCycleCount(1);
						rt.play();
					}
					this.setRotate(360+pastRotations);
					teeRot++;
					if(first){ pastRotations = 360; first = false; teeRot = 0; }
				}
				
				if(teeRot == 4){
					pastRotations = pastRotations+360;
					teeRot = 0;
				}
				
				if(onStage){
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


	@Override
	public String toString() {
		String res = "Tile: " + this.type + "; Pos: " + this.row + ", " + this.column;
		res +=  "; Conns: " + hasLeft()+hasUp()+hasRight()+hasDown();
		return res;
	}

	public int getPastRotations() {
		return pastRotations;
	}

	/**
	 * @param pastRotations the pastRotations to set
	 */
	public void rotCtr() {
		this.pastRotations = this.pastRotations + 360;
	}
}
