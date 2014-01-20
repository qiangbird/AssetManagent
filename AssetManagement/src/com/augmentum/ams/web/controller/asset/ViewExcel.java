package com.augmentum.ams.web.controller.asset;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ViewExcel extends AbstractExcelView {
    
    public String outPutPath = null;

    @Override
    protected void buildExcelDocument(Map<String, Object> arg0, HSSFWorkbook workbook,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        
        String filename = outPutPath;//设置下载时客户端Excel的名称       
//        filename = StringUtils.encodeFilename(filename, request);//处理中文文件名    
        response.setContentType("application/vnd.ms-excel");       
        response.setHeader("Content-disposition", "attachment;filename=" + filename);       
        OutputStream ouputStream = response.getOutputStream();       
        workbook.write(ouputStream);       
        ouputStream.flush();       
        ouputStream.close();   
    }

    public void setFilePath(String outPutPath) {
        this.outPutPath = outPutPath;
        
    }

}
