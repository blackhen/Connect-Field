package Utility;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
		char word;
		try {
			file = new File("save.dat");
			fis = new FileInputStream(file);
			dis = new DataInputStream(fis);
			while((word = (char) dis.readInt()) != -1) {
				if(word == 'E') easy++;
				else if(word == 'N') normal++;
				else if(word == 'H') hard++;
			}
		} catch (EOFException ignore) {
			dis.close();
		} catch (FileNotFoundException notFound) {
			fos = new FileOutputStream("save.dat");
			dos = new DataOutputStream(fos);
			dos.writeInt('E');
			dos.writeInt('N');
			dos.writeInt('H');
			dos.close();
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
		
		if(level.equals("Easy")  && stage >= easy) 
			easy = stage + 1;
		else if(level.equals("Normal") && stage >= normal)
			normal = stage + 1;
		else if(level.equals("Hard") && stage >= hard)
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
