package jpp.infinityloop.readwrite;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import jpp.infinityloop.board.Board;

public class InfinityLoopReader {
	
	int byteCount = 0;
	
	final boolean addComma = false, savegameTileFormat = true; //Adjust style of txt-file output.
	final boolean gamedataTileFormat = true; //Should always be true. (Does not have to be depending on grid, but supports more graphics options if it is.)
	public int rowCount = 0, columnCount = 0;
	public double num = 0;
	int columnCounterTile = 0, columnCounterOutput = 0;
	public String tileData;
	
	public Board read (byte[] dataBytes, int amountBytes, long filelength) throws IOException {
	    StringBuffer strBfIso = new StringBuffer();
	    StringBuffer strBfUtf8 = new StringBuffer();
	    StringBuffer strBfOutput = new StringBuffer();
	    StringBuffer strBfTile = new StringBuffer();
	    boolean infinitySymbol = true, firstLoop = true;
	    
	    //System.out.println("File size: "+file.length()+" Bytes returned: "+amountBytes+"  Array Size: "+dataBytes.length); //OLD DEBUG
	    System.out.println("Bytes returned: "+amountBytes+"  Array Size: "+dataBytes.length); //OLD DEBUG
	    
	    /* Check for infinity symbol */
	    if(dataBytes[0] == (byte)226){}else{ System.out.println("Infinity symbol not found! (1)"); infinitySymbol = false; } //11100010
	    if(dataBytes[1] == (byte)136){}else{ System.out.println("Infinity symbol not found! (2)"); infinitySymbol = false; } //10001000
	    if(dataBytes[2] == (byte)158){}else{ System.out.println("Infinity symbol not found! (3)"); infinitySymbol = false; } //10011110
	    
	    if(infinitySymbol){
	    	strBfOutput.append('\u221E');
	    }else {
			throw new IOException("Invalid file, infinity symbol not found!");
		}
	    
	    strBfOutput.append(decodeWidthHeight(dataBytes));
	    
    	for(int i = 11; i < dataBytes.length; i++) {
    		strBfIso.append(asISO(dataBytes[i]));
    		strBfUtf8.append(asUTF8(dataBytes[i]));
    		
    		if(savegameTileFormat && firstLoop){
    			strBfOutput.append('\n');
    			firstLoop = false;
    		}
    		num = i-10;
    		strBfOutput.append(asInfinityLoopEnum(dataBytes[i], ReaderType.SAVEGAME, savegameTileFormat));
    		strBfTile.append(asInfinityLoopEnum(dataBytes[i], ReaderType.GAMEDATA, gamedataTileFormat));
    			

    		
    		if(addComma){
    			if(i < dataBytes.length-1){
    				strBfOutput.append(",");
    			}
    		}
    		
    		//System.out.println(Double.valueOf(asUTF8(dataBytes[i]).toString()));
    	}
    	tileData = strBfTile.toString();
    	
    	//System.out.println("Bytes translated: "+byteCount/2+" Bytes expected to be translated: "+((file.length()-11)*2)); //OLD DEBUG
	    
    	//System.out.println("\n\n\n\n\n");
    	
    	System.out.println(strBfOutput.toString());
    	//System.out.println(strBfTile.toString());
    	
    	Board board = new Board(strBfOutput.toString(), tileData, columnCount, rowCount);
	    return board;
	}
	
	/**
	 * Converts content to ISO-8859-1 String
	 * @return
	 */
	private String asISO(byte readByte) {
		byte[] b = new byte[1];
		b[0] = readByte;
		String str = "";
		try {
			str = new String(b, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			System.err.println("error encode");
		}
		return str;
	}
	
	/**
	 * Converts content to UTF8 String
	 * @return
	 */
	private String asUTF8(byte readByte) {
		byte[] b = new byte[1];
		b[0] = readByte;
		String str = "";
		try {
			str = new String(b, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.err.println("error utf");
		}
		return str;
	}
	
	/**
	 * Converts content to Infinity Loop Enum
	 * @param type 
	 * @return
	 */
	private String asInfinityLoopEnum(byte readByte, ReaderType type, boolean isTileFormat) {
		byte[] b = new byte[1];
		b[0] = readByte;
		String str = "";
		String strFirst = "";
		String strSecond = "";
		
		byte firstByte = readByte;
		byte secondByte = readByte;
		int first = 0;
		int second = 0;
		
    	if((firstByte & 0x10) != 0){ first = first +1; } //System.out.println("is1");}
    	if((firstByte & 0x20) != 0){ first = first +2; } //System.out.println("is2");}
    	if((firstByte & 0x40) != 0){ first = first +4; } //System.out.println("is4");}
    	if((firstByte & 0x80) != 0){ first = first +8; } //System.out.println("is8");}
    	
    	if(first < 0){
    		first = firstByte>>4; 
    		System.err.println("Possible error while decoding bytes.");
    	}
    	
    	if((secondByte & 0x1) != 0){ second = second +1; } //System.out.println("is1");}
    	if((secondByte & 0x2) != 0){ second = second +2; } //System.out.println("is2");}
    	if((secondByte & 0x4) != 0){ second = second +4; } //System.out.println("is4");}
    	if((secondByte & 0x8) != 0){ second = second +8; } //System.out.println("is8");}
    	
		//second = (secondByte<<4);//<<4;
		//System.out.println(first+"--"+second);
		
		strFirst = decode(first);
		strSecond = decode(second);
		
		//1'2 , 3'4   , 5'6   , 7'8   , 9'10  , 11'12 , 13
		//14  , 15'16 , 17'18 , 19'20 , 21'22 , 23'24 , 25'26 
		
		if(type == ReaderType.SAVEGAME){
			
			if(columnCounterOutput == columnCount-1 && isTileFormat == true){
				//System.out.println("CASE1: "+(num) +" -" + " CCO" + columnCounterOutput + " CC: "+columnCount + (((num/columnCount) == (1)) ? "returnExpected" : " " ) );
				if(addComma){
					str = str + strFirst + "," + "\n" + strSecond;
				}else{
					str = str + strFirst + "\n" + strSecond;
				}
				//System.out.println("SAVEGAME return");
				columnCounterOutput = 1;
			}else if(columnCounterOutput < columnCount-1 && isTileFormat == true){
				//System.out.println("CASE2: "+(num) +" -"+ ((num/columnCount)==1.0000 ? "returnExpected" : " " + " CCO" + columnCounterOutput + " CC: "+columnCount) );
				if(addComma){
					str = str + strFirst + "," + strSecond;
				}else{
					str = str + strFirst + strSecond;
				}
				columnCounterOutput = columnCounterOutput + 2;
			}else if(columnCounterOutput == columnCount && isTileFormat == true){
				
				if(addComma){
					str = str + "\n" + strFirst + ","  + strSecond;
				}else{
					str = str + "\n" + strFirst + strSecond;
				}
				columnCounterOutput = 0;
				//System.out.println("SAVEGAME return");
				//System.out.println("CASE3: "+(num) +" -"+ ((num/columnCount)==1.0000 ? "returnExpected" : " ") + " CCO" + columnCounterOutput + " CC: "+columnCount );
				columnCounterOutput = columnCounterOutput + 2;
			}else if(isTileFormat == false){
				//System.out.println("CASE4: "+(num) +" -"+ ((num/columnCount)==1.0000 ? "returnExpected" : " ") + " CCO" + columnCounterOutput + " CC: "+columnCount );
				if(addComma){
					str = str + strFirst + "," + strSecond;
				}else{
					str = str + strFirst + strSecond;
				}
			}else{
				
				System.err.println("ERROR 4: Unsupported byte splitting decoding case." + " Current CCO: "+columnCounterOutput);
			}
			
		}		
		
		// Either print as multiple lines(isTileFormat) or as single line(!isTileFormat). 
		// booleanCondition ? valueIfTrue : valueIfFalse
		if(type == ReaderType.GAMEDATA){
			
			if(columnCounterTile == columnCount-1 && isTileFormat == true){
				if(addComma){
					str = str + strFirst + "," + "\n" + strSecond;
				}else{
					str = str + strFirst + "\n" + strSecond;
				}
				columnCounterTile = 1;
			}else if(columnCounterTile < columnCount-1 && isTileFormat == true){
				if(addComma){
					str = str + strFirst + "," + strSecond;
				}else{
					str = str + strFirst + strSecond;
				}
			}else if(columnCounterTile == columnCount && isTileFormat == true){
				if(addComma){
					str = str + "\n" + strFirst + ","  + strSecond;
				}else{
					str = str + "\n" + strFirst + strSecond;
				}
				columnCounterTile = 0;
			}else if(isTileFormat == false){
				if(addComma){
					str = str + strFirst + "," + strSecond;
				}else{
					str = str + strFirst + strSecond;
				}
				System.err.println("ERROR 6: FILE FORMAT SHOULD BE SET TO TRUE!");
			}else{
				System.err.println("ERROR 4: Unsupported byte splitting decoding case." + " Current CCT: "+columnCounterTile);
			}
			columnCounterTile = columnCounterTile + 2;
		}

		// Either print as multiple lines(isTileFormat) or as single line(!isTileFormat).

		


		//columnCounter = columnCounter + 2;
		
		// System.out.println("ByteString: "+str.toString());
		
		return str;
	}
	
	private String decode(int bit){
		String str = "";
		
		switch (bit) {
			case 0:
				//str = "\u2007"; 
				str = "\u25CB";
				byteCount++;
				break;
			case 1:
				str = "\u257B"; byteCount++;
				break;
			case 2:
				str = "\u257A"; byteCount++;
				break;
			case 3:
				str = "\u250F"; byteCount++;
				break;
			case 4:
				str = "\u2579"; byteCount++;
				break;
			case 5:
				str = "\u2503"; byteCount++;
				break;
			case 6:
				str = "\u2517"; byteCount++;
				break;
			case 7:
				str = "\u2523"; byteCount++;
				break;
			case 8:
				str = "\u2578"; byteCount++;
				break;
			case 9:
				str = "\u2513"; byteCount++;
				break;
			case 10:
				str = "\u2501"; byteCount++;
				break;
			case 11:
				str = "\u2533"; byteCount++;
				break;
			case 12:
				str = "\u251B"; byteCount++;
				break;
			case 13:
				str = "\u252B"; byteCount++;
				break;
			case 14:
				str = "\u253B"; byteCount++;
				break;
			case 15:
				str = "\u254B"; byteCount++;
				break;
			default:
				System.err.println("Error @jpp.infinityloop.readWrite.InfinityLoop.Reader.asInfinityLoopEnum !");
				break;
		}
		return str;
	}
	
	private String decodeWidthHeight(byte[] byteData){
		String str = "";
	
		//int width2 = ((byteData[6]<<24)&0xff000000 | (byteData[5]<<16)&0x00ff0000 | (byteData[4]<< 8)&0x0000ff00 | (byteData[3]<< 0)&0x000000ff);
		//int height = ( (byteData[7]<<24)&0xff000000 | (byteData[9]<<16)&0x00ff0000 | (byteData[10]<< 8)&0x0000ff00 | (byteData[11]<< 0)&0x000000ff );
		
		int width = ( (byteData[3]<< 0)&0x000000ff | (byteData[4]<< 8)&0x0000ff00 | (byteData[5]<<16)&0x00ff0000 | (byteData[6]<<24)&0xff000000 );
		int height = ( (byteData[7]<< 0)&0x000000ff | (byteData[8]<< 8)&0x0000ff00 | (byteData[9]<<16)&0x00ff0000 | (byteData[10]<<24)&0xff000000 );
		
		this.columnCount = width;
		this.rowCount = height;
		
		str = str + "(W:" + width +" H:" + height+")";
		return str;
	}
}
