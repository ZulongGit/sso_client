package com.krm.web.codegen.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public static List<String> getContent(File f) throws IOException{
		FileReader reader = new FileReader(f);
		BufferedReader br = new BufferedReader(reader);
		 List<String> lists = new ArrayList<String>();
		 String lineTxt = null;
         while((lineTxt = br.readLine()) != null){
        	 lists.add(lineTxt);
         }
         br.close();
        return  lists;
	}
	
	public static String getAllContent(File f) throws IOException{
		FileReader reader = new FileReader(f);
		BufferedReader br = new BufferedReader(reader);
		 StringBuffer sb =new StringBuffer();
		 String lineTxt = null;
         while((lineTxt = br.readLine()) != null){
        	 sb.append(lineTxt).append("\n");
         }
         br.close();
        return  sb.toString();
	}
	
	/**
	 * 获取无后缀名的文件名
	 */
	public static String getFileNameNoSuffix(String fileName) {
		if (fileName != null && fileName.indexOf(".") != -1) {
			return fileName.substring(0, fileName.lastIndexOf("."));
		}
		return fileName;
	}
	
	public static File createPath(File root,String packageName){
		packageName = packageName.replaceAll("\\.","/");
		File file = new File(root,packageName);
		if(!file.exists()){
			file.mkdirs();
		}
		return file;
	}
	
	
	public static void outputLine(File f){
		
		
	}
}
