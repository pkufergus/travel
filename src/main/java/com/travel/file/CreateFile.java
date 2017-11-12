package com.travel.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travel.util.ConstantUtil;


public class CreateFile {
	private static Logger logger = LoggerFactory
			.getLogger(CreateFile.class);
	public static String createFile(String destPath, String content) {
		String writeFlag = ConstantUtil.WRITEFAIL;
		logger.info("进入createFile方法，destPath=" + destPath + ",content="
				);
		File destFile = new File(destPath);
		FileWriter writer = null;
		BufferedWriter bw = null;
		boolean destFileExists = destFile.exists();
		logger.info("目标文件是否存在：" + destFileExists + ",writeFlag：" + writeFlag);
		if (!destFileExists) {
			try {
				destFile.createNewFile();
				logger.info("创建目标文件结束");
			} catch (IOException e) {
				e.printStackTrace();
				return writeFlag;
			}
		}
		try {
			logger.info("write开启，开始写内容");
			writer = new FileWriter(destPath);
			bw = new BufferedWriter(writer);
			bw.write(content);
			bw.close();
			writer.close();
			writeFlag = ConstantUtil.WRITESUCCESS;
			logger.info("写内容结束，writeFlag:" + writeFlag);
		} catch (IOException e) {
			logger.info(e.getMessage());
			return writeFlag;
		} finally {
			try {
				writer.close();
				bw.close();
			} catch (IOException e) {
				logger.info(e.getMessage());
				return writeFlag;
			}

		}
		return writeFlag;
	}
	
	public static void isDirExist(String dir) {
		File fileDir = new File(dir);
		boolean isDir = fileDir.isDirectory();
		logger.info("目录是否存在==="+isDir);
		if (!isDir) {
			boolean mkDirFlag = fileDir.mkdir();
			logger.info("创建文件夹是否成功==="+mkDirFlag);
		}
	}
	
	public static String getFileName(String fileName){
		try{
			String fArry[] = fileName.split("\\\\");
			int i = fArry.length == 0 ? -1 : fArry.length - 1;
			if(i !=-1 ){
				fileName = fArry[i];
			}
		}catch(Exception e){
			logger.error(e.getLocalizedMessage());
			return fileName;
		}
			return fileName;
		}
}
