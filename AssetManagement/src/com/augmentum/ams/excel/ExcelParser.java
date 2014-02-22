package com.augmentum.ams.excel;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;

import com.augmentum.ams.constants.SystemConstants;
import com.augmentum.ams.exception.ExcelException;
import com.augmentum.ams.util.UTCTimeUtil;

public abstract class ExcelParser {

    private Logger logger = Logger.getLogger(ExcelParser.class);
    public static final String EXCEL_FILE_TYPE = ".xls";

    protected Workbook workbook;
    protected WritableWorkbook writableWorkbook;
    protected String templatePath;
    protected String outputFile;

    public abstract String parse(Collection<?> collection, HttpServletRequest request)
            throws ExcelException;

    /**
     * Override this method if needed.
     * 
     * @param list
     * @return
     * @throws ExcelException
     */
    public String parseList(List<?> list) throws ExcelException {
        return null;
    }

    /**
     * Override this method if needed.
     * 
     * @param map
     * @return
     * @throws ExcelException
     */
    public String parseMap(Map<?, ?> map) throws ExcelException {
        return null;
    }

    public void check(Collection<?> collection) throws ExcelException {
        if ((collection == null) || collection.isEmpty()) {
            // TODO throw ExcelException
            // throw new ExcelException(ExcelException.ERROR_TYPE_NO_DATA,
            // "No data found when fill excel.");
        }
    }

    public Workbook getWorkbook(String templateName) throws ExcelException {
        this.workbook = ExcelBuilder.getWorkbookByTemplate(getTemplatePath(templateName));
        return workbook;
    }

    public String getTemplatePath(String templateName) {
        templatePath = getBasePath() + SystemConstants.CONFIG_TEMPLATES_PATH + templateName;
        return templatePath;
    }

    public String getOutputPath(String... outputName) {
        StringBuilder builder = new StringBuilder(getBasePath()
                + SystemConstants.CONFIG_TEMPLATES_PATH);

        for (int i = 0, n = outputName.length; i < n; i++) {
            builder.append(outputName[i]);
            if ((i + 1) != n)
                builder.append("_");
        }

        outputFile = builder.append(EXCEL_FILE_TYPE).toString();

        return outputFile;
    }

    public void close() throws ExcelException {
        ExcelBuilder.closeWritableWorkbook(writableWorkbook);
        workbook.close();
    }

    public void fillOneCell(int c, int r, Object value, WritableSheet ws) throws ExcelException {
        if (value == null)
            value = "";
        CellFormat format = ws.getCell(c, r).getCellFormat();

        try {
            fillOneCell(c, r, value, ws, format);
        } catch (Exception e) {
            throw new ExcelException(e, "Cannot write: " + value + " to comlum: " + c + " row: "
                    + r);
        }
    }

    public static void fillOneCell(int c, int r, Object value, WritableSheet ws, CellFormat format)
            throws ExcelException {
        if (value == null)
            value = "";

        try {
            if (format == null) {
                if (value instanceof String) {
                    ws.addCell(new Label(c, r, (String) value));
                } else if (value instanceof Integer) {
                    ws.addCell(new Number(c, r, (Integer) value));
                } else if (value instanceof Float) {
                    ws.addCell(new Number(c, r, Math.round((Float) value * 100) / 100f));
                } else if (value instanceof Date) {
                    ws.addCell(new Label(c, r, UTCTimeUtil.formatDateToString((Date) value)));
                } else if (value instanceof Boolean) {
                    ws.addCell(new Label(c, r, (Boolean) value ? "Y" : "N"));
                } else {
                    ws.addCell(new Label(c, r, value.toString()));
                }
            } else {
                if (value instanceof String) {
                    ws.addCell(new Label(c, r, (String) value, format));
                } else if (value instanceof Integer) {
                    ws.addCell(new Number(c, r, (Integer) value, format));
                } else if (value instanceof Float) {
                    ws.addCell(new Number(c, r, Math.round((Float) value * 100) / 100f, format));
                } else if (value instanceof Date) {
                    ws.addCell(new Label(c, r, UTCTimeUtil.formatDateToString((Date) value), format));
                } else if (value instanceof Boolean) {
                    ws.addCell(new Label(c, r, (Boolean) value ? "Y" : "N", format));
                } else {
                    ws.addCell(new Label(c, r, value.toString(), format));
                }
            }
        } catch (Exception e) {
            throw new ExcelException(e, "Cannot write: " + value + " to comlum: " + c + " row: "
                    + r);
        }
    }

    private String getBasePath() {

        String url = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        String basePath = url.replaceFirst("/", "");
        String decodedPath = null;
        try {
            decodedPath = java.net.URLDecoder.decode(basePath, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Decode path error, path = " + basePath, e);
        }

        return decodedPath;
    }
}
