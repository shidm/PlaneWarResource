package com.sdm.planewar2;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {
	private File folder;
	private File fileName;

	public void initFile() {
		String path = Environment.getExternalStorageDirectory().toString()
				+ File.separator + "plane";
		folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		String path1 = path + File.separator + "score.txt";
		fileName = new File(path1);
		if (!fileName.exists()) {
			try {
				fileName.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void write(String sumScore) {
		try {
			FileOutputStream out = new FileOutputStream(fileName);
			byte[] b = sumScore.getBytes();
			out.write(b);
			out.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String read() {
		String score = null;
		
		try {
			FileInputStream input = new FileInputStream(fileName);
			int length=input.available();
			byte[]b=new byte[length];
			input.read(b);
			score=new String(b, "UTF-8");
			input.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return score;
	}
	
	public boolean isUse(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
	

}
