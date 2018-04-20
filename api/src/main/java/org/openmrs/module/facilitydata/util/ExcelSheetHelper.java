package org.openmrs.module.facilitydata.util;

import org.apache.poi.hssf.usermodel.*;
import org.openmrs.Cohort;

import java.util.Date;

/**
 * A wrapper around a POI HSSFSheet that lets you interact via
 *  * skipCell
 *  * addCell
 *  * nextRow
 *  
 *  This class was adapted from org.pih.SheetHelper in PIH-EMR.
 *  @see ExcelStyleHelper
 */
public class ExcelSheetHelper {

    short currentRowNum;
    short currentColNum;
    HSSFSheet sheet;
    HSSFRow currentRow;
    
    public ExcelSheetHelper(HSSFSheet sheet) {
        this.sheet = sheet;
        currentRowNum = 0;
        currentColNum = 0;
    }

    /**
     * @return the currentRowNum
     */
    public short getCurrentRowNum() {
        return currentRowNum;
    }

    /**
     * @param currentRowNum the currentRowNum to set
     */
    public void setCurrentRowNum(short currentRowNum) {
        this.currentRowNum = currentRowNum;
    }

    /**
     * @return the currentColNum
     */
    public short getCurrentColNum() {
        return currentColNum;
    }

    /**
     * @param currentColNum the currentColNum to set
     */
    public void setCurrentColNum(short currentColNum) {
        this.currentColNum = currentColNum;
    }
    
    /**
     * Adds the next cell with the given value, and no style.
     * 
     * @param cellValue
     */
    public void addCell(Object cellValue) {
        addCell(cellValue, null);
    }
    
    /**
     * Adds the next cell with the given value and style.
     * 
     * @param cellValue
     * @param style
     */
    public void addCell(Object cellValue, HSSFCellStyle style) {
        if (currentRow == null) {
            currentRow = sheet.createRow(currentRowNum);
        }
        HSSFCell cell;
        if (cellValue == null) {
            cell = currentRow.createCell(currentColNum, HSSFCell.CELL_TYPE_BLANK);
        } 
        else if (cellValue instanceof Number) {
            cell = currentRow.createCell(currentColNum, HSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue(((Number) cellValue).doubleValue());
        } 
        else if (cellValue instanceof Date) {
            cell = currentRow.createCell(currentColNum, HSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue((Date) cellValue);
        }
        else if (cellValue instanceof Boolean) {
            cell = currentRow.createCell(currentColNum, HSSFCell.CELL_TYPE_BOOLEAN);
            cell.setCellValue((Boolean) cellValue);
        } 
        else if (cellValue instanceof Cohort) {
        	cell = currentRow.createCell(currentColNum, HSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue(((Cohort) cellValue).getSize());
        }
        else {
            cell = currentRow.createCell(currentColNum, HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(new HSSFRichTextString(cellValue.toString()));
        } 
        if (style != null) {
            cell.setCellStyle(style);
        }
        ++currentColNum;
    }

    /**
     * Moves to the next row.
     */
    public void nextRow() {
        ++currentRowNum;
        currentRow = null;
        currentColNum = 0;
    }

    /**
     * Removes illegal characters from a sheet name and shortens it so it isn't too long.
     * 
     * @param name
     * @return
     */
    public static String fixSheetName(String name) {
        name = name.replace(" ", "");
        if (name.length() > 15)
            name = name.substring(0, 15);
        return name;
    }
    
}
