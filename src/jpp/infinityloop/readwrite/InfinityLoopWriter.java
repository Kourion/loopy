package jpp.infinityloop.readwrite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jpp.infinityloop.board.Board;
import jpp.infinityloop.boardalgorithms.LogicTile;

public class InfinityLoopWriter {

	private int fileNumber = 0;
	
	public void write(Board board, boolean printAsByte, File filetarget) throws IOException{
		String fileName;	int safetyCheck = 0;		
		
		if(printAsByte){
			System.out.println("File: "+filetarget+" exists: "+filetarget.exists());
			LogicTile[][] array = board.getLogicTiles();
			
			int amnt = board.getRows()*board.getColumns();
			boolean isEven;
			if (amnt % 2 == 0) {
				isEven = true;
		    } else{
		    	isEven = false;
		    }
			String[] str = gridToStringArray(array, board.getRows(), board.getColumns(), isEven);
			
			
			int even = isEven ? 1 : 0 ;
			byte[] byteArray = new byte[((board.getRows()*board.getColumns()+even)/2)+11];
			
			byteArray[0] = (byte)226;
			byteArray[1] = (byte)136;
			byteArray[2] = (byte)158;
			
			byte[] width = toByte(board.getColumns());
			byte[] height = toByte(board.getRows());
			
			byteArray[3] = width[0];
			byteArray[4] = width[1];
			byteArray[5] = width[2];
			byteArray[6] = width[3];
			
			byteArray[7] = height[0];
			byteArray[8] = height[1];
			byteArray[9] = height[2];
			byteArray[10] = height[3];
			
			for (int i = 0; i < str.length; i++) {
				byteArray[i+11] = (byte) Integer.parseInt(str[i], 2);
			}
			//128 64 32 16 8 4 2 1		
			Path path = Paths.get(filetarget.getAbsolutePath());
			
			try {
				Files.write(path, byteArray);
			} catch(IOException e) {
			}
			
		}else{
			if(!new File("savegames\\").exists()){
				if(new File("savegames\\").mkdirs()){
				}else{
					throw new IOException("Failed creating file path for savegames.");
				}
			}
			
			switch (fileNumber) {
			case 0:
				fileName = "savegames"+File.separator+"save" + ".txt";
				break;
			default:
				fileName = "savegames"+File.separator+"save" + fileNumber + ".txt";
				break;
			}
			
			while(new File(fileName).isFile() && safetyCheck < 1000){
				fileNumber++;
				safetyCheck++;
				fileName = "savegames"+File.separator+"save" + fileNumber + ".txt";
			}
			
			if(safetyCheck == 1000){
				throw new IOException("Warning you tried to create more than 1000 savegames. "
						+ "Delete old savegames first before trying to create any further ones.");
			}
			FileWriter file = new FileWriter(fileName);
			BufferedWriter writer = new BufferedWriter(file);
			writer.write(board.getGamedata());
			writer.close();
		}
	}
	
	public byte[] getOut(Board board) throws IOException{
		@SuppressWarnings("unused")
		String fileName;	@SuppressWarnings("unused")
		int safetyCheck = 0;		

			LogicTile[][] array = board.getLogicTiles();
			
			int amnt = board.getRows()*board.getColumns();
			boolean isEven;
			if (amnt % 2 == 0) {
				isEven = true;
		    } else{
		    	isEven = false;
		    }
			String[] str = gridToStringArray(array, board.getRows(), board.getColumns(), isEven);
			
			
			int even = isEven ? 1 : 0 ;
			byte[] byteArray = new byte[((board.getRows()*board.getColumns()+even)/2)+11];
			
			byteArray[0] = (byte)226;
			byteArray[1] = (byte)136;
			byteArray[2] = (byte)158;
			
			byte[] width = toByte(board.getColumns());
			byte[] height = toByte(board.getRows());
			
			byteArray[3] = width[0];
			byteArray[4] = width[1];
			byteArray[5] = width[2];
			byteArray[6] = width[3];
			
			byteArray[7] = height[0];
			byteArray[8] = height[1];
			byteArray[9] = height[2];
			byteArray[10] = height[3];
			
			for (int i = 0; i < str.length; i++) {
				byteArray[i+11] = (byte) Integer.parseInt(str[i], 2);
			}
			//128 64 32 16 8 4 2 1		
			
			return byteArray;
	}
	
	private byte[] toByte(int integ){
		byte[] bytarr = new byte[4];
		bytarr[0] = (byte) (integ);
		bytarr[1] = (byte) (integ >> 8);
		bytarr[2] = (byte) (integ >> 16); 
		bytarr[3] = (byte) (integ >> 24);
		return bytarr;
	}
	
	
	private String[] gridToStringArray(LogicTile[][] array, int rows, int cols, boolean isEven) {
		int even = isEven ? 1 : 0 ;
		String[] strs = new String[rows*cols+even];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				strs[(cols*i)+j] = getTileByte(array[i][j]);
			}
		}
		if(!isEven){
			strs[strs.length-1] = "0000";
		}
		String[] bStr = new String[strs.length/2];
		int j = 0;
		for (int i = 0; i < strs.length/2; i++) {
			bStr[i] = strs[j]+strs[j+1];
			j = j + 2;
		}
		
		return bStr;
	}
	
	private String getTileByte(LogicTile tile){
		//String tileString = "";
		String s;
		if(tile.hasLeft()){
			if(tile.hasUp()){
				if(tile.hasRight()){
					if(tile.hasDown()){
						s = "1111"; //b = (byte)15; //8+4+2+1
					}else{
						s = "1110"; //b = (byte)14; //tileString = "\u253B";
					}
				}else{
					if(tile.hasDown()){
						s = "1101"; //b = (byte)13; //tileString = "\u252B";
					}else{
						s = "1100"; //b = (byte)12; //tileString = "\u251B";
					}
				}
			}else{
				if(tile.hasRight()){
					if(tile.hasDown()){
						s = "1011"; //b = (byte)11; //tileString = "\u2533";
					}else{
						s = "1010"; //b = (byte)10; //tileString = "\u2501";
					}
				}else{
					if(tile.hasDown()){
						s = "1001"; //b = (byte)9; //tileString = "\u2513";
					}else{
						s = "1000"; //b = (byte)8; //tileString = "\u2578";
					}
				}
			}
		}else{
			if(tile.hasUp()){
				if(tile.hasRight()){
					if(tile.hasDown()){
						s = "0111"; //b = (byte)7; //tileString = "\u2523";
					}else{
						s = "0110"; //b = (byte)6; //tileString = "\u2517";
					}
				}else{
					if(tile.hasDown()){
						s = "0101"; //b = (byte)5; //tileString = "\u2503";
					}else{
						s = "0100"; //b = (byte)4; //tileString = "\u2579";
					}
				}
			}else{
				if(tile.hasRight()){
					if(tile.hasDown()){
						s = "0011"; //b = (byte)3; //tileString = "\u250F";
					}else{
						s = "0010"; //b = (byte)2; //tileString = "\u257A";
					}
				}else{
					if(tile.hasDown()){
						s = "0001"; //b = (byte)1; //tileString = "\u257B";
					}else{
						//tileString = "\u2007";
						s = "0000"; //b = (byte)0; //tileString = "\u25CB";
					}
				}
			}
		}
		return s;
	}
	
}
