package jpp.infinityloop.gui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jpp.infinityloop.board.Board;
import jpp.infinityloop.boardalgorithms.RandomBoard;
import jpp.infinityloop.readwrite.InfinityLoopReader;
import jpp.infinityloop.readwrite.InfinityLoopWriter;
import jpp.infinityloop.solver.Solver;

public class Launcher extends Application {

	private GuiControl control = new GuiControl();
	private GameInterfacePane gameinterface = new GameInterfacePane();
	private InfinityLoopReader reader = new InfinityLoopReader();
	@SuppressWarnings("unused")
	private InfinityLoopWriter writer = new InfinityLoopWriter();
	private RandomBoard rdm = new RandomBoard();
	
	private Scene scene;
	
	final int windowWidth = 1600, windowHeight = 900;
	final int minWindowFactorTo16x9 = 4;
	
	@Override
	public void start(Stage arg0) throws Exception {
		BorderPane root = new BorderPane(); 
		ButtonPane menuPane = new ButtonPane(gameinterface);
		
		//String savegame = "";
		//String gameData = reader.read("testfiles\\03_sample_valid_solved.bin");
		//savegame = gameData;
		//writer.write(savegame); //TODO WRITER
		File selectedFile = new File("testfiles"+ File.separator + "03_sample_valid_solved.bin");
		byte[] dataBytes = new byte[(int)selectedFile.length()];
		@SuppressWarnings("resource")
		InputStream inputData = new BufferedInputStream(new FileInputStream(selectedFile));
	    int amountBytes = inputData.read(dataBytes, 0, (int) selectedFile.length());
		
	    Board board = reader.read(dataBytes, amountBytes, selectedFile.length());
	    
		gameinterface = new GameInterfacePane(windowWidth-3, windowHeight-3, board);

		menuPane.setStyle(gameinterface.getStyle());
		root.setCenter(gameinterface);
		
		/** Replace the initial board with a label. **/
		Label welcomeLabel = new Label("Welcome to the infinity-loop game. To play, either load a gamefile or select the random game mode.");
		root.setCenter(welcomeLabel);
		
		control.menuPaneAdjustments(menuPane, windowHeight, gameinterface, arg0);
		menuPane.setCenterShape(true);
		root.setRight(menuPane);
		root.setCenterShape(true);
		scene = new Scene(root, windowWidth, windowHeight); //scene.setFill(Color.GREEN);
		control.sceneListeners(scene, gameinterface, menuPane);
		
		arg0.setScene(scene);
	    arg0.initStyle(StageStyle.DECORATED);
	    arg0.setMinWidth(160*minWindowFactorTo16x9);
	    arg0.setMinHeight(90*minWindowFactorTo16x9);
	    //arg0.setFullScreen(true);
	    arg0.sizeToScene();
	    arg0.show();
	    
	    newGame(menuPane, arg0);
	    //System.exit(-1); //TODO REMOVE
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void newGame(ButtonPane menuPane, Stage arg0){
		//menuPane = new ButtonPane();
		((ButtonBase) menuPane.getChildren().get(0)).setOnAction(new EventHandler<ActionEvent>(){ 
	    	@SuppressWarnings("resource")
			@Override
	    	public void handle(ActionEvent event) { 
	    		FileChooser fileChooser = new FileChooser();
	    		fileChooser.setTitle("Open Movie File");
	    		File selectedFile = fileChooser.showOpenDialog(arg0);
	    		BorderPane border = new BorderPane();
	    		
	    		if (selectedFile != null) {
	    			//openFile(selectedFile);
	    			//FileInputStream InputStream;
	    			
					try {
						//InputStream = new FileInputStream(selectedFile);
						InputStream inputData = new BufferedInputStream(new FileInputStream(selectedFile));
						InfinityLoopReader reader = new InfinityLoopReader();
						try {

						    //File file = new File(fileName);
						    byte[] dataBytes = new byte[(int)selectedFile.length()];
						    int amountBytes = inputData.read(dataBytes, 0, (int) selectedFile.length());
							
						    Board board = reader.read(dataBytes, amountBytes, selectedFile.length());
						    
							gameinterface = new GameInterfacePane(windowWidth-3, windowHeight-3, board); //windowWidth-3, windowHeight-3, reader.rowCount, reader.columnCount, reader.t
							border.setCenter(gameinterface);
							border.setRight(menuPane);
							//scene = new Scene(border, windowWidth, windowHeight);
							//arg0.setScene(scene);
							
							menuPane.setStyle(gameinterface.getStyle());
							border.setCenter(gameinterface);
							control.menuPaneAdjustments(menuPane, windowHeight, gameinterface, arg0);
							menuPane.setCenterShape(true);
							border.setCenterShape(true);
							
							double sW = scene.getWidth();
							double sH = scene.getHeight();
							
							scene = new Scene(border, sW, sH); //scene.setFill(Color.GREEN);
							//double gIW = gameinterface.getWidth();
							//double giMW = gameinterface.getMinWidth();
							//gameinterface.setMinWidth(gIW+1000);
							//gameinterface.setMinWidth(giMW);
							
							control.sceneListeners(scene, gameinterface, menuPane);
							
							arg0.setScene(scene);
						    arg0.setMinWidth(160*minWindowFactorTo16x9);
						    arg0.setMinHeight(90*minWindowFactorTo16x9);
						    
						    if(arg0.isFullScreen()){
						    	arg0.setFullScreen(false);
						    	arg0.setFullScreen(true);
						    }else{
						    	arg0.setFullScreen(true);
						    	arg0.setFullScreen(false);
						    }
						    
						    //arg0.setFullScreen(true);
						    //arg0.sizeToScene();
						    //arg0.show();
							
						} catch(IOException e0) {
							//System.out.println("Error 1: FILE READ ERROR");
								Alert error1 = new Alert(AlertType.CONFIRMATION); 
									error1.setTitle("Error 1"); 
									error1.setHeaderText("FILE READ ERROR"); 
									error1.setContentText("Reading the file failed, please try a different file."); 
									
									ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
									error1.getButtonTypes().setAll(okButton); 
									error1.showAndWait();
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
	    			
	    		 }
	    	}
	    } );
		
		((ButtonBase) menuPane.getChildren().get(1)).setOnAction(new EventHandler<ActionEvent>(){ 
	    	@Override
	    	public void handle(ActionEvent event) {
	    		
	    		BorderPane border = new BorderPane();
				
			    Board board = rdm.createRandomBoard();
			    
				gameinterface = new GameInterfacePane(windowWidth-3, windowHeight-3, board); //windowWidth-3, windowHeight-3, reader.rowCount, reader.columnCount, reader.t
				border.setCenter(gameinterface);
				border.setRight(menuPane);
				//scene = new Scene(border, windowWidth, windowHeight);
				//arg0.setScene(scene);
				
				menuPane.setStyle(gameinterface.getStyle());
				border.setCenter(gameinterface);
				control.menuPaneAdjustments(menuPane, windowHeight, gameinterface, arg0);
				menuPane.setCenterShape(true);
				border.setCenterShape(true);
				
				double sW = scene.getWidth();
				double sH = scene.getHeight();
				
				scene = new Scene(border, sW, sH); //scene.setFill(Color.GREEN);
				//double gIW = gameinterface.getWidth();
				//double giMW = gameinterface.getMinWidth();
				//gameinterface.setMinWidth(gIW+1000);
				//gameinterface.setMinWidth(giMW);
				
				control.sceneListeners(scene, gameinterface, menuPane);
				
				arg0.setScene(scene);
			    arg0.setMinWidth(160*minWindowFactorTo16x9);
			    arg0.setMinHeight(90*minWindowFactorTo16x9);
			    
			    if(arg0.isFullScreen()){
			    	arg0.setFullScreen(false);
			    	arg0.setFullScreen(true);
			    }else{
			    	arg0.setFullScreen(true);
			    	arg0.setFullScreen(false);
			    }
	    			
	    		 }
	    } );
		
		((ButtonBase) menuPane.getChildren().get(2)).setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Solver solver = new Solver(gameinterface);
				solver.solve();
			}
			
		});
		
		
	}
	
}
		//Color col = Color.ALICEBLUE;
		//String colorVal = col.toString().substring(2, 8);
		//String newColor = "#" + colorVal;
		//menuPane.setStyle("-fx-background-color: " + newColor + "; -fx-border-color: " + newColor + "; -fx-focus-color: " + newColor + "; -fx-faint-focus-color: " + newColor + ";");

		//windowWidth-3, windowHeight-3, reader.rowCount, reader.columnCount, reader.tileData);
		//gameinterface.setBackground(new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY))); //(Color.color(Math.random(), Math.random(), Math.random());