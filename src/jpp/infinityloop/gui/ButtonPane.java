package jpp.infinityloop.gui;

import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class ButtonPane extends FlowPane{

	private StyleType style = StyleType.AliceBlue;
	
	public ButtonPane(GameInterfacePane board){
		
		//this.style = style;
		
		ImageView loadFileIcon = new ImageView( new Image( getClass().getResource("sekkyumuicons"+ File.separator + "_getdoc.png").toExternalForm() ) );
		ImageView createRandomBoardIcon = new ImageView( new Image(getClass().getResource("sekkyumuicons"+ File.separator + "_play.png").toExternalForm() ) );
		ImageView solveBoardIcon = new ImageView( new Image(getClass().getResource("sekkyumuicons"+ File.separator + "_PluginGreenButton.png").toExternalForm() ) );
		ImageView saveBoardIcon = new ImageView( new Image(getClass().getResource("sekkyumuicons"+ File.separator + "_WriteDocument.png").toExternalForm() ) );
		ImageView newFinishedColorIcon = new ImageView( new Image(getClass().getResource("sekkyumuicons"+ File.separator + "_picture_document.png").toExternalForm() ) );
		ImageView newRandomGameModeIcon = new ImageView( new Image(getClass().getResource("sekkyumuicons"+ File.separator + "_play_all.png").toExternalForm() ) );
		
		//double iconSize = 1/(32/20);
		//loadFileIcon.setScaleX(iconSize);	loadFileIcon.setScaleY(iconSize);
		
		Button loadFileButton = new Button();
		Tooltip tooltipLoadFileButton = new Tooltip();
		tooltipLoadFileButton.setText("Load a new game-board file.");
		loadFileButton.setTooltip(tooltipLoadFileButton);
		loadFileButton.setMinSize(32, 32);
		loadFileButton.setPrefSize(32, 32);
		loadFileButton.setMaxSize(32, 32);
		//loadFileButton.setStyle(style);
		loadFileButton.setGraphic(loadFileIcon);
		loadFileButton.setStyle("-fx-background-color: " + style + "; -fx-border-color: " + style + "; -fx-focus-color:  " + style + ";"
		+ " -fx-faint-focus-color:  " + style + " ;");
		
		Button randomBoardButton = new Button();
		Tooltip tooltipRandomBoardButton = new Tooltip();
		tooltipRandomBoardButton.setText("Generate a new random game-board.");
		randomBoardButton.setTooltip(tooltipRandomBoardButton);
		randomBoardButton.setMinSize(32, 32);
		randomBoardButton.setPrefSize(32, 32);
		randomBoardButton.setMaxSize(32, 32);
		randomBoardButton.setGraphic(createRandomBoardIcon);
		randomBoardButton.setStyle("-fx-background-color: " + style + "; -fx-border-color: " + style + "; -fx-focus-color:  " + style + ";"
				+ " -fx-faint-focus-color:  " + style + " ;");
		
		Button solveBoardButton = new Button();
		Tooltip tooltipSolveBoardButton = new Tooltip();
		tooltipSolveBoardButton.setText("Ask the computer to solve the puzzle for you.");
		solveBoardButton.setTooltip(tooltipSolveBoardButton);
		solveBoardButton.setMinSize(32, 32);
		solveBoardButton.setPrefSize(32, 32);
		solveBoardButton.setMaxSize(32, 32);
		solveBoardButton.setGraphic(solveBoardIcon);
		solveBoardButton.setStyle("-fx-background-color: " + style + "; -fx-border-color: " + style + "; -fx-focus-color:  " + style + ";"
				+ " -fx-faint-focus-color:  " + style + " ;");
		
		
		Button saveBoardButton = new Button();
		Tooltip tooltipSaveBoardButton = new Tooltip();
		tooltipSaveBoardButton.setText("Save the current game-board as a binary file.");
		saveBoardButton.setTooltip(tooltipSaveBoardButton);
		saveBoardButton.setMinSize(32, 32);
		saveBoardButton.setPrefSize(32, 32);
		saveBoardButton.setMaxSize(32, 32);
		saveBoardButton.setGraphic(saveBoardIcon);
		saveBoardButton.setStyle("-fx-background-color: " + style + "; -fx-border-color: " + style + "; -fx-focus-color:  " + style + ";"
				+ " -fx-faint-focus-color:  " + style + " ;");
		
		Button toggleColorButton = new Button();
		Tooltip tooltipToggleColorButton = new Tooltip();
		tooltipToggleColorButton.setText("If you completed a game-board you can use this button to take a look at it in different colors.");
		toggleColorButton.setTooltip(tooltipToggleColorButton);
		toggleColorButton.setMinSize(32, 32);
		toggleColorButton.setPrefSize(32, 32);
		toggleColorButton.setMaxSize(32, 32);
		toggleColorButton.setGraphic(newFinishedColorIcon);
		toggleColorButton.setStyle("-fx-background-color: " + style + "; -fx-border-color: " + style + "; -fx-focus-color:  " + style + ";"
				+ " -fx-faint-focus-color:  " + style + " ;");
		
		Button randomGameMode = new Button();
		randomGameMode.setMinSize(32, 32);
		randomGameMode.setPrefSize(32, 32);
		randomGameMode.setMaxSize(32, 32);
		randomGameMode.setGraphic(newRandomGameModeIcon);
		randomGameMode.setStyle("-fx-background-color: " + style + "; -fx-border-color: " + style + "; -fx-focus-color:  " + style + ";"
				+ " -fx-faint-focus-color:  " + style + " ;");
		
		//((ButtonBase) menuPane.getChildren().get(4)).setOnAction(new EventHandler<ActionEvent>(){ 
		
		
		this.setCenterShape(true);
		this.getChildren().setAll(loadFileButton, randomBoardButton, solveBoardButton, saveBoardButton, toggleColorButton); //randomGameMode
	}
	
	
	public void applyStyle(StyleType newStyle){
		if(newStyle != StyleType.AliceBlue){
			for (int i = 0; i < 5; i++) {
				this.getChildren().get(i).setStyle("-fx-background-color: " + newStyle + "; -fx-border-color: " + newStyle + "; -fx-focus-color:  " + newStyle + ";"
						+ " -fx-faint-focus-color:  " + newStyle + " ;");
			}
		}
	}
	
}
