package com.capgemini.kabanos.common.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	public static String[] readFile(String path) throws IOException {
		return Files.readAllLines(Paths.get(path)).toArray(new String[0]);
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
}
