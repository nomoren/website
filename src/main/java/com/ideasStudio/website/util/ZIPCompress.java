package com.ideasStudio.website.util;
import java.io.File;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;  

/**
 * 压缩文件  
 * @author Administrator
 *
 */
public class ZIPCompress {
        static Logger logger = Logger.getLogger(ZIPCompress.class);
	
        /**
    	 * @Title: zipFilesAndEncrypt 
         * @Description: 将指定路径下的文件压缩至指定zip文件，并以指定密码加密,若密码为空，则不进行加密保护 
    	 * @param srcFileName 待压缩文件路径 或 文件夹路径
    	 * @param zipFileName zip路径+文件名 
    	 * @param password 加密密码  
    	 * @return 
    	 */
        public static void compressedFiles(String srcFileName,String zipFileName,String password){  
        	
    	    if(StringUtils.isEmpty(srcFileName) || StringUtils.isEmpty(zipFileName)){  
    	    	System.out.println("请求的压缩路径或者文件名有误");
    	    	return;
    	    }  
    	    try {  
    		    ZipParameters parameters = new ZipParameters();  
    		    parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);//压缩方式
    		    parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);//压缩级别
    		    if(!StringUtils.isEmpty(password)){  
    		    	parameters.setEncryptFiles(true);     
    			    parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);//加密方式
    			    parameters.setPassword(password);  
    		    }  
    		    ArrayList<File> filesToAdd=getFiles(srcFileName);
    		    ZipFile zipFile = new ZipFile(zipFileName); 
    		    zipFile.setFileNameCharset("gbk");
    		    zipFile.addFiles(filesToAdd, parameters); 
    	    } catch (ZipException e) {  
    	    	logger.error("文件压缩出错");
    	    	e.printStackTrace();
    	    } 
    	}
        
        //通过路径获取压缩文件集合
        private static ArrayList<File> getFiles(String srcFileName){
        	ArrayList<File> filesToAdd = new ArrayList<File>();
    	    File file=new File(srcFileName);
    	    File[] files = new File[0]; 
    	    if(file.isDirectory()){
    	    	files = file.listFiles();
    	    	for(int i=0;i<files.length;i++){
	    			filesToAdd.add(new File(files[i].getPath()));
			    	logger.info("文件："+files[i].getPath());
    	    	}
    	    } else {
    			filesToAdd.add(new File(file.getPath()));
    		}
    	    
    	    return filesToAdd;
        }
}
