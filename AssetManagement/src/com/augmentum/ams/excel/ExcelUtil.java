package com.augmentum.ams.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelUtil {

	private static ExcelUtil excelUtil = new ExcelUtil();
	
	private ExcelUtil() {
		
	}
	
	public static ExcelUtil getInstance() {

		return excelUtil;
	}
	/**
	 * Gets work book according to file by jxl.
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws BiffException 
	 */
	public Workbook getWorkbook(File file) throws BiffException, IOException {
		
		Workbook workbook = null;
		if (file != null) { 
			workbook =  Workbook.getWorkbook(file);
		}
		
		return workbook;
	}


    /**
     * Gets HSSFWorkbook according to file by apache poi.
     * @param file
     * @return
     * @throws IOException
     */
    public HSSFWorkbook getHSSFWorkbook(File file) throws IOException {
		
        POIFSFileSystem fs = null; 
        HSSFWorkbook wb = null;
        	
		if (file != null) { 
			fs = new POIFSFileSystem(new FileInputStream(file));
			wb = new HSSFWorkbook(fs);
		}
		
		return wb;
	}
	
    /**
     * Gets numbers of sheet in workbook
     * @param book
     * @return
     */
	public int getNumberOfSheets(Workbook book) {
		
		return book == null ? 0 : book.getNumberOfSheets();
	}

    /**
     * Gets total rows of sheet
     * @param sheet
     * @return
     */
	public int getRows(Sheet sheet) {
		int righRow = 0;
		
		if (sheet != null) { 
			int rsCols = sheet.getColumns();
			int rsRows = sheet.getRows();
			int nullCellNum;
			int afterRows = rsRows;
			
			for (int i = 1; i < rsRows; i++) {
				nullCellNum = 0;
				for (int j = 0; j < rsCols; j++) {
					String val = sheet.getCell(j, i).getContents();
					val = StringUtils.trimToEmpty(val);
					if (StringUtils.isBlank(val))
						nullCellNum++;
				}
				if (nullCellNum >= rsCols) {
					afterRows--;
				}
			}
			righRow = afterRows;
		}
		
		return righRow;
	}

	/**
	 * Gets total columns of sheet
	 * 
	 * @param sheet
	 * @return
	 */
	public int getColumns(Sheet sheet) {
		
		return sheet == null ? 0 : sheet.getColumns();
	}

	/**
	 * Gets all cells about row.
	 * @param sheet
	 * @param row
	 * @return
	 */
	public Cell[] getRows(Sheet sheet, int row) {
		
		return sheet == null || sheet.getRows() < row ? null : sheet
				.getRow(row);
	}

	@SuppressWarnings("unchecked")
	public List getRowCellValues(HSSFRow hrrsRow) {
		
		List RowvalueList = new ArrayList<String>();
		
		for(Iterator it = hrrsRow.cellIterator() ; it.hasNext();) {
			HSSFCell hrrsCell = (HSSFCell)it.next();
			String value = hrrsCell.getRichStringCellValue().getString().trim();
			if (value != null) {
				RowvalueList.add(value);
			}
		}
		
		return RowvalueList;
	}
	/**
	 * Gets column Cells according to certain column and startRow and end endRow.
	 * @param sheet
	 * @param row
	 * @param startcol
	 * @param endcol
	 * @return
	 */
	public Cell[] getColCells(Sheet sheet, int col, int startrow, int endrow) {
		Cell[] cellArray = new Cell[endrow - startrow];
		int maxRow = this.getRows(sheet);
		int maxCos = this.getColumns(sheet);
		if (col <= 0 || col > maxCos || startrow > maxRow || endrow < startrow) {
			return null;
		}
		if (startrow < 0) {
			startrow = 0;
		}
		for (int i = startrow; i < endrow && i < maxRow; i++) {
			cellArray[i - startrow] = sheet.getCell(col, i);
		}
		
		return cellArray;
	}

	/**
	 * Gets rowCells according to certain row and startColumn and endColumn.
	 * @param sheet
	 * @param row
	 * @param startcol
	 * @param endcol
	 * @return
	 */
	public Cell[] getRowCells(Sheet sheet, int row, int startcol, int endcol) {
		Cell[] cellArray = new Cell[endcol - startcol];
		int maxRow = this.getRows(sheet);
		int maxCos = this.getColumns(sheet);
		if (row <= 0 || row > maxRow || startcol > maxCos || endcol < startcol) {
			return null;
		}
		
		if (startcol < 0) {
			startcol = 0;
		}
		
		for (int i = startcol; i < endcol && i < maxCos; i++) {
			cellArray[i - startcol] = sheet.getCell(i, row);
		}
		
		return cellArray;
	}

}
