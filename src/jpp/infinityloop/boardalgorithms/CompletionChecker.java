package jpp.infinityloop.boardalgorithms;

import javafx.scene.layout.Pane;
import jpp.infinityloop.gui.Tile;

public class CompletionChecker {

	public static boolean isComplete(Pane pane, int columns, int rows){ //
		
		boolean isCompleted = true;
		
		//System.out.println("R: "+ rows+ " C:"+ columns);
		for (int currRow = 0; currRow < rows; currRow++) {
			for (int currColumn = 0; currColumn < columns; currColumn++) {
				//System.out.println("CR: " + currRow + " CC: " +currColumn + " Pos: " + ((columns*currRow)+currColumn) );
				
				/** CHECK UP **/
				if( ((Tile) pane.getChildren().get( (columns*currRow)+currColumn )).hasUp() ){
					try { //5 6
						
						if(((Tile) pane.getChildren().get(((columns*(currRow-1))+currColumn))).hasDown()){
							}else{
								isCompleted = false;
								//((Tile) pane.getChildren().get(((columns*(currRow-1))+currColumn))).setStyle("-fx-background-color: lightblue;");
						}
						
					} catch (Exception e) {
						isCompleted = false;
						//System.out.println("Cant find location: " + ((columns*(currRow-1))+currColumn) );
					}
				}
				
				/** CHECK RIGHT **/
				if( ((Tile) pane.getChildren().get( (columns*currRow)+currColumn )).hasRight() ){
					try { //5 6
						
						if(((Tile) pane.getChildren().get(((columns*currRow)+(currColumn+1)))).hasLeft() && 
								((Tile) pane.getChildren().get(((columns*currRow)+(currColumn+1)))).getRow() == ((Tile) pane.getChildren().get( (columns*currRow)+currColumn )).getRow()	){	
							}else{
								if(((Tile) pane.getChildren().get(((columns*currRow)+(currColumn+1)))).getRow() == ((Tile) pane.getChildren().get( (columns*currRow)+currColumn )).getRow()){
									//System.out.println("SR");
								}else{
									//System.out.println("NSR");
								}
								isCompleted = false;
								//((Tile) pane.getChildren().get(((columns*currRow)+(currColumn+1)))).setStyle("-fx-background-color: lightblue;");
						}
						
					} catch (Exception e) {
						isCompleted = false;
						//System.out.println("Cant find location: " + ((columns*(currRow))+currColumn+1) );
					}
				}
				
				/** CHECK DOWN **/
				if( ((Tile) pane.getChildren().get( (columns*currRow)+currColumn )).hasDown() ){
					try { //5 6
						
						if(((Tile) pane.getChildren().get(((columns*(currRow+1))+currColumn))).hasUp()){
							}else{
								isCompleted = false;
								//((Tile) pane.getChildren().get(((columns*(currRow+1))+currColumn))).setStyle("-fx-background-color: lightblue;");
						}
						
					} catch (Exception e) {
						isCompleted = false;
						//System.out.println("Cant find location: " + ((columns*(currRow+1))+currColumn) );
					}
				}
				
				/** CHECK LEFT **/
				if( ((Tile) pane.getChildren().get( (columns*currRow)+currColumn )).hasLeft() ){
					try { //5 6
						
						if(((Tile) pane.getChildren().get( ((columns*currRow)+(currColumn-1)) /*-1*/ ) ).hasRight() && 
								((Tile) pane.getChildren().get( ((columns*currRow)+(currColumn-1)) /*-1*/ ) ).getRow() == ((Tile) pane.getChildren().get( (columns*currRow)+currColumn )).getRow()
								){
							}else{
								isCompleted = false;
								//((Tile) pane.getChildren().get(((columns*currRow)+(currColumn-1)))).setStyle("-fx-background-color: lightblue;");
						}
						
					} catch (Exception e) {
						isCompleted = false; 
						//System.out.println("Cant find location: " + ((columns*currRow)+(currColumn-1)) );
					}
				}
				
				/** CHECK FINISHED **/
			}
		}
		
		//pane.setStyle("-fx-background-color: orangered; -fx-border-color: orangered; -fx-focus-color: orangered; -fx-faint-focus-color: orangered;");
		
		//System.out.println(isCompleted);
		return isCompleted;
	}
}
