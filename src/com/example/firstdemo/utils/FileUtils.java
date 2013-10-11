package com.example.firstdemo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.os.Environment;

public class FileUtils {
	private String SDCardRoot;

	public FileUtils() {
		
		SDCardRoot = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ File.separator;
	}
	
	public File createFileInSDCard(String fileName, String path)
			throws IOException {
		
		boolean b = isDirExist(path);
		
		if(!b){
			creatSDDir(path);
		}
		File file = new File(SDCardRoot + path + File.separator + fileName);
		boolean result = false;
		try{
			result = file.createNewFile();
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(result){
			return file;
		}
		return null;
	}


	public File creatSDDir(String path) {
		File dirFile = new File(SDCardRoot + path);
		boolean b = dirFile.mkdirs();
		if(b){
			return dirFile;
		}
		return null;
	}
	

	public boolean isDirExist(String path){
		return isFileExist("", path);
	}


	public boolean isFileExist(String fileName, String path) {
		File file = new File(SDCardRoot + path + File.separator + fileName);
		return file.exists();
	}


	public File write2SDFromInput(String path, String fileName,
			InputStream input) {

		File file = null;
		OutputStream output = null;
		try {
			creatSDDir(path);
			file = createFileInSDCard(fileName, path);
			output = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			int temp;
			while ((temp = input.read(buffer)) != -1) {
				output.write(buffer, 0, temp);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	public InputStream getInputStreamFromRaw(int rawId , Context context){
		InputStream input= context.getResources().openRawResource(rawId);
		return input;
		
	}
}