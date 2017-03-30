package jpp.infinityloop.gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jpp.infinityloop.boardalgorithms.CompletionChecker;

/**
 * Implements listeners and other basic functions of generic GUI elements.
 */
public class GuiControl {


	private BooleanProperty statusProperty = new SimpleBooleanProperty();
	@SuppressWarnings("unused")
	private double sceneWidth = 0, sceneHeight = 0;
	
	private FlowPane colorFlowgrid = new FlowPane();
	private boolean complete = false, colorEnabledAdjustment = false, forceColor = false, colorForceAllowed = false;
	private int columns = 0, rows = 0;
	
	/**
	 * Logic for all tile Buttons
	 * @param tileButton
	 * @param flowgrid 
	 */
	public void setTileButtonLogic(Tile tileButton, FlowPane flowgrid, int columnCount, int rowCount){
		tileButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) { 
				tileButton.rotateTile();
				statusProperty.set(CompletionChecker.isComplete(flowgrid, columnCount, rowCount));
				statusProperty.setValue(CompletionChecker.isComplete(flowgrid, columnCount, rowCount));
				colorFlowgrid = flowgrid;
				complete = CompletionChecker.isComplete(flowgrid, columnCount, rowCount);
				columns = columnCount;
				rows = rowCount;
				colorForceAllowed = true;
				//if(CompletionChecker.isComplete(flowgrid, columnCount, rowCount)){
					colorAdjustment(flowgrid, CompletionChecker.isComplete(flowgrid, columnCount, rowCount), columnCount, rowCount);
				//}
				
			}
		} );
		//TODO ADD NEW RANDOM COLOR BUTTON  ON ACTION
	}
	
	public void setTileButtonStyle(Tile tileButton, Pane pane, int columnCount, int rowCount){
		final int maxTileSize = 200;
		boolean wasDisabled = false;
		
		if(tileButton.isDisabled()){
			wasDisabled = true;
			tileButton.setDisable(false);
		}
		
	 	//btn.setGraphic(bendIcon);
		//tileButton.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.NONE, CornerRadii.EMPTY, BorderWidths.EMPTY)));
		
		//System.out.println("Coloring: " + tileButton.getColoring() );//currentStyle
		if(tileButton.getHasHexColor()){
			tileButton.setStyle("-fx-background-color: " + tileButton.getHexColor() + "; -fx-border-color: " + tileButton.getHexColor() + "; -fx-focus-color:  " + tileButton.getHexColor() + ";"
		 			+ " -fx-faint-focus-color:  " + tileButton.getHexColor() + " ;");
		}else{
			tileButton.setStyle("-fx-background-color: " + tileButton.getColoring() + "; -fx-border-color: " + tileButton.getColoring() + "; -fx-focus-color:  " + tileButton.getColoring() + ";"
	 			+ " -fx-faint-focus-color:  " + tileButton.getColoring() + " ;");
		}
				
		//tileButton.setBackground(pane.getBackground()); // Color.color(Math.random(), Math.random(), Math.random());
		
		//tileButton.setMinSize(pane.getPrefWidth()/columnCount, pane.getPrefHeight()/rowCount);
		//tileButton.setMaxSize(pane.getPrefWidth()/columnCount, pane.getPrefHeight()/rowCount);
		//System.out.println(Math.floor(pane.getMinWidth()/columnCount));
		
		double tileSize = 0;
		if(Math.floor(pane.getMinWidth()/columnCount) < Math.floor(pane.getPrefHeight()/rowCount)){
			//System.out.println("WiM: "+Math.floor(pane.getMinWidth()/columnCount));
			tileSize = Math.floor(pane.getMinWidth()/columnCount);
		}else{
			tileSize = Math.floor(pane.getPrefHeight()/rowCount);
			//System.out.println("HiM: "+Math.floor(pane.getPrefHeight()/rowCount));
		}if(tileSize == 0){
			System.out.println("ERROR 10: Tiles have not been allocated any space. (Possibly to many tiles)");
		}if(tileSize >maxTileSize-1){
			tileSize = maxTileSize-1;
			pane.setMinWidth(maxTileSize*columnCount);
			pane.setPrefWidth(maxTileSize*columnCount);
			pane.setMaxWidth(maxTileSize*columnCount);
		}
		//System.out.println(pane.getMinWidth());
		if((tileSize*columnCount)+tileSize < pane.getMaxWidth() || (tileSize*columnCount)+tileSize < pane.getPrefWidth() ){
			pane.setMinWidth((tileSize*columnCount)+5);
			pane.setPrefWidth((tileSize*columnCount)+5);
			pane.setMaxWidth((tileSize*columnCount)+5);
		}
		
		tileButton.setPrefSize(tileSize, tileSize);
		tileButton.setMinSize(tileSize, tileSize);
		tileButton.setMaxSize(tileSize, tileSize);
		
		tileButton.setIconSize(tileSize);
		
		pane.setPrefHeight((tileSize+0.001)*rowCount);
		pane.setMaxHeight((tileSize+0.001)*rowCount);
		
		if(wasDisabled){
			tileButton.setDisable(true);
		}
		
		//tileButton.setRotate(90);
	 	//tileButton.setBlendMode(BlendMode.EXCLUSION);
	}
	
	public void sceneListeners (Scene scene, Pane pane, Pane menuPane) {
		
		scene.widthProperty().addListener(new ChangeListener<Number>() {
		    @Override 
		    public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
		        //System.out.println(scene.getWidth() + "--**--" + newSceneWidth);
		        //pane.propagateSizes();
		    	//try {
			    	//if(scene.getWidth() != 0 && scene.getHeight() != 0){
		    	try {
					((Region) pane.getChildren().get(0)).setPrefSize(scene.getWidth(), scene.getHeight());
		    		((Region) ((Region) pane.getChildren().get(0)).getChildrenUnmodifiable().get(0)).setPrefSize(scene.getWidth(), scene.getHeight());
				} catch (Exception e) {
					// TODO: handle exception
				}
		    		
			    	//}
			    	sceneWidth = (double) newSceneWidth;
				//} catch (Exception e) {
					// TODO: handle exception
				//}

		    }
	    });
		scene.heightProperty().addListener(new ChangeListener<Number>() {
	        @Override 
	        public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
	        	//System.out.println(scene.getHeight() + "--**--" + newSceneHeight);
	        	//try {
		        	//if(scene.getWidth() != 0 && scene.getHeight() != 0){
	        			try {
	        				((Region) pane.getChildren().get(0)).setPrefSize(scene.getWidth(), scene.getHeight());
			        		((Region) ((Region) pane.getChildren().get(0)).getChildrenUnmodifiable().get(0)).setPrefSize(scene.getWidth(), scene.getHeight());
						} catch (Exception e) {
							// TODO: handle exception
						}
		        		
		        		sceneHeight = (double) newSceneHeight;
		        		menuPane.setTranslateY(scene.getHeight()/2-(32*5)/2);
		        	//}
				//} catch (Exception e) {
					// TODO: handle exception
				//}
	        	

	        }
	    });
		
	}
	
	/*
    public void statusListener (GameInterfacePane gameinterface){
    	
    	statusProperty.addListener(new ChangeListener<Boolean>() {

	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            // Only if completed
	            if (newValue) {
	            	StyleType style = gameinterface.getStyleType();
	                switch (style) {
					case AliceBlue:
							gameinterface.setStyle("-fx-background-color: " + gameinterface.getColoring() + "; -fx-border-color: " + tileButton.getColoring() + ";"
									+ " -fx-focus-color: " + tileButton.getColoring() + "; -fx-faint-focus-color: " + tileButton.getColoring() + ";"); //orangered
							System.out.println("COLOR SWITCH");
						break;

					default:
						break;
					}
	                System.out.println("COLOR SWITCH");
	                gameinterface.getChildren().get(0).setStyle(gameinterface.getStyle());
	            }
	            System.out.println("LISTENER");
	        }
    	});
    	
    	//gameinterface.setStyle("-fx-background-color: orangered; -fx-border-color: orangered; -fx-focus-color: orangered; -fx-faint-focus-color: orangered;");
    } */
    
    public void fitToWindow(GameInterfacePane grandparent, BorderPane parent, FlowPane child){ //GameInterfacePane, displayPane, flowgrid
    	
    	grandparent.widthProperty().addListener(new ChangeListener<Number>() {
		    @Override 
		    public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
		    	//System.out.println("SW: " + sceneWidth + " GPW : " + grandparent.getWidth() + " PW: " + parent.getWidth());
		    	child.setMinHeight(sceneHeight > grandparent.getHeight() ? sceneHeight-10 : grandparent.getHeight()-10 );
		    	child.setPrefHeight(sceneHeight > grandparent.getHeight() ? sceneHeight : grandparent.getHeight() );
		    	child.setMaxHeight(sceneHeight > grandparent.getHeight() ? sceneHeight : grandparent.getHeight() );
		    	
		    	child.setMinWidth(parent.getWidth() < grandparent.getWidth() ? parent.getWidth()-10 : grandparent.getWidth()-10 );
		    	child.setPrefWidth(parent.getWidth() < grandparent.getWidth() ? parent.getWidth() : grandparent.getWidth() );
		    	child.setMaxWidth(parent.getWidth() < grandparent.getWidth() ? parent.getWidth() : grandparent.getWidth() );
		    	//child.setMaxWidth(parent.getWidth());
		    	
		    	//System.out.println("CMW: "+child.getMinWidth());
		    	//System.out.println(child.get);
		    	//setTileButtonStyle(child.getChildren(),Pane pane, int columnCount, int rowCount);
		    	//System.out.println(child.toString());
		    	for (int currRow = 0; currRow < grandparent.getRowCount() ; currRow++) {
					for (int currColumn = 0; currColumn < grandparent.getColumnCount() ; currColumn++) {
		setTileButtonStyle((Tile) child.getChildren().get((grandparent.getColumnCount()*currRow)+currColumn ), child, grandparent.getColumnCount(), grandparent.getRowCount());
					}
		    	}
		        //((Region) pane.getChildren().get(0)).setPrefSize(scene.getWidth(), scene.getHeight());
		    }
	    });
    	parent.heightProperty().addListener(new ChangeListener<Number>() {
	        @Override 
	        public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
	        	//System.out.println("SH: " + sceneHeight + " GPH : " + grandparent.getHeight() + " PH: " + parent.getHeight());
	        	child.setMinHeight(sceneHeight > grandparent.getHeight() ? sceneHeight-10 : grandparent.getHeight()-10 );
		    	child.setPrefHeight(sceneHeight > grandparent.getHeight() ? sceneHeight : grandparent.getHeight() );
		    	child.setMaxHeight(sceneHeight > grandparent.getHeight() ? sceneHeight : grandparent.getHeight() );

		    	child.setMinWidth(parent.getWidth() < grandparent.getWidth() ? parent.getWidth()-10 : grandparent.getWidth()-10 );
		    	child.setPrefWidth(parent.getWidth() < grandparent.getWidth() ? parent.getWidth() : grandparent.getWidth() );
		    	child.setMaxWidth(parent.getWidth() < grandparent.getWidth() ? parent.getWidth() : grandparent.getWidth() );
		    	
		    	//System.out.println("CMW: "+child.getMinWidth());
		    	//System.out.println(child.get);
		    	//setTileButtonStyle(child.getChildren(),Pane pane, int columnCount, int rowCount);
		    	//System.out.println(child.toString());
		    	if(((Tile) child.getChildren().get(0)).getHasHexColor()){
		    		grandparent.setDisable(true);
		    	}
		    	for (int currRow = 0; currRow < grandparent.getRowCount() ; currRow++) {
					for (int currColumn = 0; currColumn < grandparent.getColumnCount() ; currColumn++) {	
		setTileButtonStyle((Tile) child.getChildren().get((grandparent.getColumnCount()*currRow)+currColumn ), child, grandparent.getColumnCount(), grandparent.getRowCount());
		
					}
		    	}
	        }
	    });
    	
    }
    
    public void colorAdjustment(Pane flowgrid, boolean complete, int x, int y){
    	if(complete){
    		Color col = Color.valueOf(((Tile) flowgrid.getChildren().get(0)).getColoring().toString());
    		col = col.deriveColor(10*Math.random()*Math.random()*Math.random(), 10*Math.random()*Math.random(), 0.9, 1);
    		if(Math.random()<0.5){
    			col = col.invert();
    		}
    		String colorVal = col.toString().substring(2, 8);
    		String newColor = "#" + colorVal;
    		flowgrid.setStyle("-fx-background-color: " + newColor + "; -fx-border-color: " + newColor + "; -fx-focus-color: " + newColor + "; -fx-faint-focus-color: " + newColor + ";");
    		flowgrid.getParent().setStyle(flowgrid.getStyle());
    		flowgrid.getParent().getParent().setStyle(flowgrid.getStyle());
    		for (int j = 0; j < x*y; j++) {
    			((Tile) flowgrid.getChildren().get(j)).setStyle("-fx-background-color: " + newColor + "; -fx-border-color: " + newColor + "; -fx-focus-color: " + newColor + "; -fx-faint-focus-color: " + newColor + ";");
    			((Tile) flowgrid.getChildren().get(j)).setHexColor(newColor);
    			((Tile) flowgrid.getChildren().get(j)).setHasHexColor(true);
				((Tile) flowgrid.getChildren().get(j)).setDisable(true);
				((Tile) flowgrid.getChildren().get(j)).setBlendModeSetting(); //((Tile) flowgrid.getChildren().get(j)).getTileImageView().setBlendMode(BlendMode.DIFFERENCE);
    		}
    		//statusProperty.setValue(false);
    		flowgrid.getParent().getParent().setDisable(true);
    		
    		colorEnabledAdjustment = true;
    	}else if(forceColor){
    		Color col = Color.valueOf(((Tile) flowgrid.getChildren().get(0)).getColoring().toString());
    		col = col.deriveColor(10*Math.random()*Math.random()*Math.random(), 10*Math.random()*Math.random(), 1, 1);
    		if(Math.random()<0.5){
    			col = col.invert();
    		}
    		String colorVal = col.toString().substring(2, 8);
    		String newColor = "#" + colorVal;
    		flowgrid.setStyle("-fx-background-color: " + newColor + "; -fx-border-color: " + newColor + "; -fx-focus-color: " + newColor + "; -fx-faint-focus-color: " + newColor + ";");
    		flowgrid.getParent().setStyle(flowgrid.getStyle());
    		flowgrid.getParent().getParent().setStyle(flowgrid.getStyle());
    		for (int j = 0; j < x*y; j++) {
    			((Tile) flowgrid.getChildren().get(j)).setStyle("-fx-background-color: " + newColor + "; -fx-border-color: " + newColor + "; -fx-focus-color: " + newColor + "; -fx-faint-focus-color: " + newColor + ";");
    			((Tile) flowgrid.getChildren().get(j)).setHexColor(newColor);
    			((Tile) flowgrid.getChildren().get(j)).setHasHexColor(true);
				((Tile) flowgrid.getChildren().get(j)).setBlendModeSetting(); //((Tile) flowgrid.getChildren().get(j)).getTileImageView().setBlendMode(BlendMode.DIFFERENCE);
    		}
    		forceColor = false;
    	}else{
    		Color col = Color.valueOf(((Tile) flowgrid.getChildren().get(0)).getColoring().toString());
    		String colorVal = col.toString().substring(2, 8);
    		String newColor = "#" + colorVal;
    		flowgrid.setStyle("-fx-background-color: " + newColor + "; -fx-border-color: " + newColor + "; -fx-focus-color: " + newColor + "; -fx-faint-focus-color: " + newColor + ";");
    		flowgrid.getParent().setStyle(flowgrid.getStyle());
    		flowgrid.getParent().getParent().setStyle(flowgrid.getStyle());
    		for (int j = 0; j < x*y; j++) {
    			((Tile) flowgrid.getChildren().get(j)).setStyle("-fx-background-color: " + newColor + "; -fx-border-color: " + newColor + "; -fx-focus-color: " + newColor + "; -fx-faint-focus-color: " + newColor + ";");
    			((Tile) flowgrid.getChildren().get(j)).setHexColor(newColor);
    			((Tile) flowgrid.getChildren().get(j)).setHasHexColor(false);
				((Tile) flowgrid.getChildren().get(j)).setDisable(false);
				((Tile) flowgrid.getChildren().get(j)).setBlendModeSetting(); //((Tile) flowgrid.getChildren().get(j)).getTileImageView().setBlendMode(BlendMode.DIFFERENCE);
    		}
    		//statusProperty.setValue(false);
    		flowgrid.getParent().getParent().setDisable(false);
    	}
    }
    
    public void toggleColor(boolean force){
    	//System.out.println("HE1 "+complete+" "+colorEnabledAdjustment);
    	if(force){
    		forceColor = true;
    		if(colorForceAllowed){
    			colorAdjustment(colorFlowgrid, complete, columns, rows);
    		}
    		
    		
    	}else{
    		if(colorEnabledAdjustment){
				colorAdjustment(colorFlowgrid, complete, columns, rows);
    		}
    	}
		
    }
    
    public void menuPaneAdjustments(Pane pane, double windowHeight, Pane gameinterface, Stage arg0){
		pane.setPrefWidth(32);
		pane.setTranslateY(windowHeight/2-(32*5)/2);
    }

}