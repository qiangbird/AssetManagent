package com.augmentum.ams.util;

import java.io.BufferedInputStream;  
import java.io.BufferedOutputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.augmentum.ams.web.controller.base.BaseController;
  
/** 
 *  
 * @author John li 
 * @date 2014-01-16
 */  
public class FileOperateUtil {  

    private static Logger logger = Logger.getLogger(BaseController.class);
    
    private static final String UPLOADDIR = "upload";  
  
    /** 
     * Download file 
     *  
     * @author John li 
     * @date 2014-01-16
     * @param request 
     * @param response 
     * @param storeName 
     * @param contentType 
     * @param realName 
     * @throws Exception 
     */  
    public static void download(HttpServletRequest request,  
            HttpServletResponse response, String outPutPath) throws Exception { 
        
        try{
            request.setCharacterEncoding("UTF-8");  
            BufferedInputStream bis = null;  
            BufferedOutputStream bos = null;  
      
            File file = new File(outPutPath);
            String fileName = file.getName();
            long fileLength = new File(outPutPath).length(); 
            
      
            bis = new BufferedInputStream(new FileInputStream(outPutPath));  
            bos = new BufferedOutputStream(response.getOutputStream()); 
            
            byte[] buff = new byte[bis.available()]; 
            
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename="  
                    + new String(fileName.getBytes("utf-8"), "ISO8859-1"));  
            response.setHeader("Content-Length", String.valueOf(fileLength));  
            response.setContentType("application/vnd.ms-excel");  
            
            int bytesRead;  
            
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
                bos.write(buff, 0, bytesRead);  
            }  
            
            bos.flush(); 
            bos.close();  
            bis.close();
            
        } catch (IOException e) {
            logger.error("Download export file" + outPutPath +  " fail!  " + e.getMessage(), e);
        }
    }  
    
    /** 
     * Upload file 
     *  
     * @author John li 
     * @date 2014-01-16
     * @param request 
     * @param params 
     * @param values 
     * @return 
     * @throws Exception 
     */  
    public static File upload(HttpServletRequest request, HttpServletResponse response, 
            MultipartFile file) {  
  
        String path = request.getSession().getServletContext().getRealPath(UPLOADDIR);//upload directory
        String fileName = file.getOriginalFilename();
        File targetFile = new File(path, fileName);
        
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            logger.error("Upload image error!",e);
        }
        return targetFile;  
    }  
}  