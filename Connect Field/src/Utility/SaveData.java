package Utility;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveData {
	
	private FileInputStream fis;
	private DataInputStream dis;
	private FileOutputStream fos;
	private DataOutputStream dos;
	private File file;
	
	private int easy;
	private int normal;
	private int hard;
	
	public SaveData() throws IOException {
		file = new File("save.dat");
		fis = new FileInputStream(file);
		dis = new DataInputStream(fis);
	
		char word;
		try {
			while((word = (char) dis.readInt()) != -1) {
				if(word == 'E') easy++;
				else if(word == 'N') normal++;
				else if(word == 'H') hard++;
			}
		} catch (EOFException ignore) {
			dis.close();
		}
	}
	
	public int getData(String level) {
		if(level.equals("Easy")) {
			if(easy == 0)
				easy++;
			return easy;
		}
		else if(level.equals("Normal")) {
			if(normal == 0)
				normal++;
			return normal;
		}
		else {
			if(hard == 0)
				hard++;
			return hard;
		}
	}

	
	public void saveData(String level, int stage) throws IOException {
		
		fos = new FileOutputStream("save.dat");
		dos = new DataOutputStream(fos);
		
		if(level.equals("Easy")) 
			easy = stage + 1;
		else if(level.equals("Normal"))
			normal = stage + 1;
		else if(level.equals("Hard"))
			hard = stage + 1;
		
		for(int i = 0; i < easy; i++)
			dos.writeInt('E');
		for(int i = 0; i < normal; i++)
			dos.writeInt('N');
		for(int i = 0; i < hard; i++)
			dos.writeInt('H');
		dos.close();
	}
}
