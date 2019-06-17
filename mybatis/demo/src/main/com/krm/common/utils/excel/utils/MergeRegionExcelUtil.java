package com.krm.common.utils.excel.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  

public class MergeRegionExcelUtil {

	/**  
	* 读取excel文件  
	* @param wb   
	* @param sheetIndex sheet页下标：从0开始  
	* @param startReadLine 开始读取的行:从0开始  
	* @param tailLine 去除最后读取的行  
	*/  
	private static void readExcel(Workbook wb, int sheetIndex, int startReadLine, int tailLine) {
		Sheet sheet = wb.getSheetAt(sheetIndex);
		Row row = null;
		for (int i = startReadLine; i < sheet.getLastRowNum() - tailLine + 1; i++) {
			row = sheet.getRow(i);
			for (Cell c : row) {
				boolean isMerge = isMergedRegion(sheet, i, c.getColumnIndex());
				// 判断是否具有合并单元格
				if (isMerge) {
					String rs = getMergedRegionValue(sheet, row.getRowNum(), c.getColumnIndex());
					System.out.print(rs + "----");
				} else {
					System.out.print(c.getRichStringCellValue() + "");
				}
			}
		}
	}
	
	/**
	 * 获取合并单元格的值
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static String getMergedRegionValue(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();

		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return getCellValue(fCell);
				}
			}
		}
		return null;
	}

	/**
	 * 判断合并了行
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static boolean isMergedRow(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row == firstRow && row == lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row
	 *            行下标
	 * @param column
	 *            列下标
	 * @return
	 */
	public static boolean isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断sheet页中是否含有合并单元格
	 * 
	 * @param sheet
	 * @return
	 */
	public static boolean hasMerged(Sheet sheet) {
		return sheet.getNumMergedRegions() > 0 ? true : false;
	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 * @param firstRow 开始行
	 * @param lastRow 结束行
	 * @param firstCol 开始列
	 * @param lastCol 结束列
	 */
	public static void mergeRegion(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {

		if (cell == null)
			return "";

		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {

			return cell.getStringCellValue();

		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {

			return String.valueOf(cell.getBooleanCellValue());

		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

			return cell.getCellFormula();

		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

			return String.valueOf(cell.getNumericCellValue());

		}
		return "";
	}
	
	public static Workbook resetMergeRegionValue(Workbook wb){
		Sheet sheet = wb.getSheetAt(0);
		for (int i = sheet.getNumMergedRegions()-1; i >= 0; i--) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			String value = getMergedRegionValue(sheet, firstRow, firstColumn);
			sheet.removeMergedRegion(i);
			setValue(sheet, firstRow, lastRow, firstColumn, lastColumn, value);
		}
		return wb;
	}
	
	public static void setValue(Sheet sheet, int firstRow, int lastRow, int firstColumn, int lastColumn, String value){
		for (int i = firstRow; i < lastRow+1; i++) {
			Row row = sheet.getRow(i);
			for (int j = firstColumn; j < lastColumn+1; j++) {
				Cell cell = row.getCell(j);
				cell.setCellValue(value);
			}
		}
	}
	
	public static void main(String[] args) {
		String path = "C:/Users/Parker/Desktop/测试20180110140322.xlsx";
		try {
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File(path)));
//			readExcel(wb, 0, 0, wb.getSheetAt(0).getLastRowNum());
			Sheet sheet = wb.getSheetAt(0);
			int sheetMergeCount = sheet.getNumMergedRegions();
			for (int i = sheet.getNumMergedRegions()-1; i >= 0; i--) {
				CellRangeAddress range = sheet.getMergedRegion(i);
				int firstRow = range.getFirstRow();
				int lastRow = range.getLastRow();
				int firstColumn = range.getFirstColumn();
				int lastColumn = range.getLastColumn();
				String value = getMergedRegionValue(sheet, firstRow, firstColumn);
				System.out.println("起始行："+firstRow+"  结束行："+lastRow + "   开始列："+ firstColumn + "   结束列："+ lastColumn + "  值："+getMergedRegionValue(sheet, firstRow, firstColumn));
				sheet.removeMergedRegion(i);
				setValue(sheet, firstRow, lastRow, firstColumn, lastColumn, value);
			}
			OutputStream os = new FileOutputStream(new File("d:/test.xlsx"));
			wb.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
