package com.anjuke.android.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;



public class LogFile {
	final public static void writelog(String sFilePath, String sContent) {
		saveFile(sFilePath, "result.html", sContent);
	}

	final public static void saveFile(String filePath, String fileName, String content) {
		FileOutputStream outFile = null;
		OutputStreamWriter osw = null;

		filePathExists(filePath);

		try {
			outFile = new FileOutputStream(filePath + fileName);
			osw = new OutputStreamWriter(outFile, "UTF-8");
			osw.write(content);
			osw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				osw.close();
				outFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	final public static void filePathExists(String path) {
		File f = null;

		f = new File(path);
		if (f.exists() == false) {
			f.mkdirs();// 没有存在就在建立一个
		}
	}

	/**
	 * 从项目根目录读取config.ini配置文件存入map中,等号前面的内容是key,等号后面的内容是value. 注释以#开头的行.
	 * 
	 * @return Map<String, String>
	 */
	final public static Map<String, String> readConfig() {
		BufferedReader input = null;// 读文件用
		Map<String, String> map = new HashMap<String, String>();// 存文件内容的map,等号前面是key,等号后面的是value
		String text = null;// 存放读出每行的变量
		FileInputStream file = null;

		try {
			file = new FileInputStream("config.ini");
			input = new BufferedReader(new InputStreamReader(file, "UTF-8"));
			while ((text = input.readLine()) != null) {
				if (text.length() >= 1 && text.substring(0, 1).equals("#")) {
					continue;
				}
				int number = text.indexOf("=");
				if (number != -1) {
					map.put(text.substring(0, number), text.substring(number + 1, text.length()));
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException ioException) {
			System.err.println("File Error!");
			ioException.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	
	
//	final public static Map<String, String> getConfigInfo(String configKey) {
//
//		String dataInfo = "";
//		String[] data;
//		String[] singleData;
//		Map<String, String> map = new HashMap<String, String>();
//		dataInfo = Init.G_config.get(configKey);
//		data = dataInfo.split(",");
//		for (String dataStr : data) {
//			singleData = dataStr.split("-");
//			map.put(singleData[0], singleData[1]);
//		}
//		return map;
//	}
}

