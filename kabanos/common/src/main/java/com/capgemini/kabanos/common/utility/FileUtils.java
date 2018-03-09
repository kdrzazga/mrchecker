package com.capgemini.kabanos.common.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	public static String[] readFile(String path) {
		try {
			return Files.readAllLines(Paths.get(path)).toArray(new String[0]);
		} catch (IOException e) {
			System.out.println("Unable to read file: " + path);
			System.out.println(e);
			return new String[0];
		}
	}

	public static List<String> listFiles(String path) {
		List<String> result = new ArrayList<String>();
		
		File directory = new File(path);
		
		if(directory.isDirectory()) {
			
		    File[] fList = directory.listFiles();
		    for (File file : fList) {
		        if (file.isFile()) {
		        	result.add(file.getAbsolutePath());
		        } else if (file.isDirectory()) {
		            result.addAll(listFiles(file.getAbsolutePath()));
		        }
		    }
		}
		else if(directory.isFile()) {
			result.add(directory.getAbsolutePath());
		}
	    
		return result;
	}
	
	public static boolean saveFile(String path, String content) throws IOException {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8))) {
		    writer.write(content);
		    return true;
		} 
		catch (IOException ex) {
			System.out.println(ex);

			throw ex;
		}
	}
}