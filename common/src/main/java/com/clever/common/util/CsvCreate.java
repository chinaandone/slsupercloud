package com.clever.common.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class CsvCreate {
	
	/**
	 * 生成csv文件
	 * @param listmap：头文件
	 * @param listmap2：文件内容
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void csvCreate(OutputStream out,ArrayList<Map<String, String>> listmap,ArrayList<Map<String,String>> listmap2) throws Exception{
		//设置生成临时文件的路径
		BufferedWriter csvFileOutputStream = null;
	    try {
	            // GB2312使正确读取分隔符","
	            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(out, "GB2312"), 1024);
	            // 写入文件头部
	            for (Iterator iterator = listmap.iterator(); iterator.hasNext();) {
	            	String[] list = null;
					Map<String, String> map = (Map<String, String>) iterator.next();
					for(Map.Entry<String, String> map1 : map.entrySet()){
						//分割头部
						 list =  map1.getValue().split(",");
						for (String str : list) {
							 csvFileOutputStream.write(str);
					         csvFileOutputStream.write(",");
						}
						csvFileOutputStream.newLine();
					}
				}
	            
				//写入内容
	            for (Iterator iterator = listmap2.iterator(); iterator.hasNext();) {
	            	String[] list = null;
					Map<String, String> map = (Map<String, String>) iterator.next();
					for (Map.Entry<String, String> map1 : map.entrySet()) {
						list = map1.getValue().split(",");
						for (String str : list) {
							 csvFileOutputStream.write(str);
					         csvFileOutputStream.write(",");
						}
						list = null;
					}
					csvFileOutputStream.newLine();
				}
	            csvFileOutputStream.flush();  
	        } catch (Exception e) {  
	           e.printStackTrace();  
	        } finally {  
	           try {  
	                csvFileOutputStream.close();
				    out.close();
	            } catch (IOException e) {
	               e.printStackTrace();
	           }  
	       } 
	}

}
