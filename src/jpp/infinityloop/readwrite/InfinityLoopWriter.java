package jpp.infinityloop.readwrite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InfinityLoopWriter {

	private int fileNumber = 0;
	
	public void write(String savegame) throws IOException{
		String fileName;	int safetyCheck = 0;		
		
		if(!new File("savegames\\").exists()){
			if(new File("savegames\\").mkdirs()){
			}else{
				throw new IOException("Failed creating file path for savegames.");
			}
		}
		
		switch (fileNumber) {
		case 0:
			fileName = "savegames\\save" + ".txt";
			break;
		default:
			fileName = "savegames\\save" + fileNumber + ".txt";
			break;
		}
		
		while(new File(fileName).isFile() && safetyCheck < 1000){
			fileNumber++;
			safetyCheck++;
			fileName = "savegames\\save" + fileNumber + ".txt";
		}
		
		if(safetyCheck == 1000){
			throw new IOException("Warning you tried to create more than 1000 savegames. "
					+ "Delete old savegames first before trying to create any further ones.");
		}
		FileWriter file = new FileWriter(fileName);
		BufferedWriter writer = new BufferedWriter(file);
		writer.write(savegame);
		writer.close();
	}
	
}
