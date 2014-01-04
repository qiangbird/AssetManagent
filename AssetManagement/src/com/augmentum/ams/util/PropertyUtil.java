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
 * The class used to read properties and write properties.
 */
public class PropertyUtil {

    static Logger logger = Logger.getLogger(PropertyUtil.class);

    public static String readProperties(String key) {
        String content = null;
        Properties p = new Properties();
        FileInputStream fis = null;
        try {
            URL url = Thread.currentThread().getContextClassLoader()
                    .getResource("database.properties");
            // FileInputStream(Thread.currentThread().getContextClassLoader().getResource("database.properties").getPath());
            if (null != url) {
                fis = new FileInputStream(url.getPath());
            }
            p.load(fis);
        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
        content = p.getProperty(key);
        return content;
    }

    public static void writeProperties(String key, String value) {
        InputStream ins = null;
        OutputStream os = null;
        Properties p = new Properties();
        String filePath;
        
        URL url = Thread.currentThread().getContextClassLoader()
                .getResource("database.properties");
        if(null!=url){
            filePath= url.getPath();
        File file = new File(filePath);
        try {
            ins = new FileInputStream(file);
            p.load(ins);
            os = new FileOutputStream(file);
            p.setProperty(key, value);
            p.store(os, "Save or Update key: " + key + ", value: " + value);
        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        } finally {
            try {
                ins.close();
                os.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }
    }
}
