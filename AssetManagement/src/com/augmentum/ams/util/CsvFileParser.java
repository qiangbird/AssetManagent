package com.augmentum.ams.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Description: the class about read csv file</p>
 * <p>Date: 12/03/23</p>
 * <p>Company: Augmentum</p>
 * @author Gordon.Gu
 *
 */
public class CsvFileParser {
    
    private static Log log = LogFactory.getLog(CsvFileParser.class);
    private static final String SPECIAL_CHAR_A = "[^\",\\n \ufffd\ufffd]";
    private static final String SPECIAL_CHAR_B = "[^\",\\n]";

    public List<String[]> readCsvFile(String argPath) throws FileNotFoundException, IOException {
        CsvFileParser util = new CsvFileParser();
        File cvsFile = new File(argPath);
        List<String[]> list = new ArrayList<String[]>();
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        
        try {
            fileReader = new FileReader(cvsFile);
            bufferedReader = new BufferedReader(fileReader);
            String regExp = util.getRegExp();

            String strLine = "";
            String str = "";
            while ((strLine = bufferedReader.readLine()) != null) {
                
                Pattern pattern = Pattern.compile(regExp);
                Matcher matcher = pattern.matcher(strLine);
                List<String> listTemp = new ArrayList<String>();
                while (matcher.find()) {
                    str = matcher.group();
                    str = str.trim();
                    if (str.endsWith(",")) {
                        str = str.substring(0, str.length() - 1);
                        str = str.trim();
                    }
                    if (str.startsWith("\"") && str.endsWith("\"")) {
                        str = str.substring(1, str.length() - 1);
                        if (util.isExisted("\"\"", str)) {
                            str = str.replaceAll("\"\"", "\"");
                        }
                    }
                    if (!"".equals(str)) {
                        listTemp.add(str);
                    }
                }
                list.add((String[]) listTemp.toArray(new String[listTemp.size()]));
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            //throw e;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            //throw e;
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                //throw e;
            }
        }
        return list;
    }

    private boolean isExisted(String argChar, String argStr) {

        boolean blnReturnValue = false;
        if ((argStr.indexOf(argChar) >= 0) && (argStr.indexOf(argChar) <= argStr.length())) {
            blnReturnValue = true;
        }
        return blnReturnValue;
    }

    /**
     * Regular expressions 
     * @return Matching CSV file minimum unit of regular expressions
     */
    private String getRegExp() {
        StringBuffer strRegExps = new StringBuffer();
        
        strRegExps.append("\"((");
        strRegExps.append(SPECIAL_CHAR_A);
        strRegExps.append("*[,\\n \ufffd\ufffd])*(");
        strRegExps.append(SPECIAL_CHAR_A);
        strRegExps.append("*\"{2})*)*");
        strRegExps.append(SPECIAL_CHAR_A);
        strRegExps.append("*\"[ \ufffd\ufffd]*,[ \ufffd\ufffd]*");
        strRegExps.append("|");
        strRegExps.append(SPECIAL_CHAR_B);
        strRegExps.append("*[ \ufffd\ufffd]*,[ \ufffd\ufffd]*");
        strRegExps.append("|\"((");
        strRegExps.append(SPECIAL_CHAR_A);
        strRegExps.append("*[,\\n \ufffd\ufffd])*(");
        strRegExps.append(SPECIAL_CHAR_A);
        strRegExps.append("*\"{2})*)*");
        strRegExps.append(SPECIAL_CHAR_A);
        strRegExps.append("*\"[ \ufffd\ufffd]*");
        strRegExps.append("|");
        strRegExps.append(SPECIAL_CHAR_B);
        strRegExps.append("*[ \ufffd\ufffd]*");
        
        return strRegExps.toString();
    }

}
