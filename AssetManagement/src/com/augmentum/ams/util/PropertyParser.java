package com.augmentum.ams.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * <p>Description: the class about read property</p>
 * <p>Date: 12/03/23</p>
 * <p>Company: Augmentum</p>
 * @author Gordon.Gu
 *
 */
public class PropertyParser {

    static Logger logger = Logger.getLogger(PropertyParser.class);
    public static String readProperties(String key) {
        
        String content = null;
        Properties p = new Properties();
        FileInputStream fis =null;
        URL url = Thread.currentThread().getContextClassLoader().getResource("ams.properties");
        try {
            if(null!=url){
            fis = new FileInputStream(url.getPath()); 
            p.load(fis);
            }
        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }finally{
            try {
                fis.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
        content = p.getProperty(key);
        return content;
    }
    public static void writeProperties(String key, String value){
    	InputStream fis = null;
        OutputStream fos = null;
    	Properties p=new Properties();
    	URL url = Thread.currentThread().getContextClassLoader().getResource("ams.properties");
    	String 	file=url.getPath();
    	if(null!=file){
    	File file3 = new File(file);
    	try {
            fis = new FileInputStream(file3);
            p.load(fis);
            fos = new FileOutputStream(file3);
 
            p.setProperty(key, value);
            p.store(fos, "Update '" + key + "' value");
        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }finally{
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
    	
    	}
    }
}
