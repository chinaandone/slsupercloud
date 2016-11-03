package com.clever.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HttpClientUtil {
	private final static Logger logger = Logger.getLogger(HttpClientUtil.class);


	public static String doPost(String url) {
		return doPost(url, null);
	}

	public static String doPost(String url, Map<String, String> params) {
		return doPost("UTF-8", url, params);
	}

	public static String doPost(String charsetName, String url,
			Map<String, String> params) {
		return doPost(null, charsetName, url, params);
	}

	public static String doPost(Map<String, File> files, String url,
			Map<String, String> params) {
		return doPost(files, "UTF-8", url, params);
	}

	public static String doPost(Map<String, File> files, String charsetName,
			String url, Map<String, String> params) {
		String responseContent = "";
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			builder.setCharset(Charset.forName(charsetName));

			if (params != null) {
				Iterator itor = params.entrySet().iterator();
				while (itor.hasNext()) {
					Entry entry = (Entry) itor.next();
					builder.addTextBody(TypeConverter.toString(entry.getKey()), TypeConverter.toString(entry.getValue()), ContentType.create("text/plain", "UTF-8"));
				}
			}

			if (files != null) {
				Iterator itor = files.entrySet().iterator();
				while (itor.hasNext()) {
					Entry entry = (Entry) itor.next();
					Object valueObj = entry.getValue();
					if(valueObj instanceof File[]){
						File[] fileArray = (File[])valueObj ;
						for(int i=0; i<fileArray.length; i++){
				            builder.addBinaryBody(TypeConverter.toString(entry.getKey()), fileArray[i]);
						}
					}
					if(valueObj instanceof File){
			            builder.addBinaryBody(TypeConverter.toString(entry.getKey()), (File)valueObj);
					}
				}
			}
	        
	        HttpEntity entity = builder.build();
			//setEntity只有第一次调用起作用，第二次调用会冲掉第一次设置的数据
			post.setEntity(entity);

			HttpResponse response;
			response = client.execute(post);
			HttpEntity responseEntity = response.getEntity();
			InputStream is = responseEntity.getContent();
			if (is != null) {
				InputStreamReader isr = null;
				try {
					isr = new InputStreamReader(is, charsetName);
					BufferedReader rd = new BufferedReader(isr);

					String line = "";
					while ((line = rd.readLine()) != null) {
						responseContent += line;
					}
					return responseContent;
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					isr.close();
				}
			}
		} catch (ParseException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
		return responseContent;
	}

}
