package com.gambalit.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import com.gmb.modelo.GmbReportPatrol;

public class Test {

	// public static void main(String args[]) throws IOException {
	/*public void extractFile() throws IOException {
 
		FileInputStream file = new FileInputStream(new File("D:\\patrulla.xlsx"));
		XSSFWorkbook    workbook = new XSSFWorkbook(file);
		GmbReportPatrol reportPatrol = new GmbReportPatrol();
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		Row row;
		Integer contadorRow = 0;
		Integer contadorCell = 0;
		while (rowIterator.hasNext()) {
			row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			Cell celda;
			while (cellIterator.hasNext()) {
				celda = cellIterator.next();

				if (contadorRow == 1) {
					if (contadorCell == 1) {
						reportPatrol.setRuta(celda.getStringCellValue());
					}
					if(contadorCell==2) {
						reportPatrol.setRuta(celda.getStringCellValue());
					}
					if(contadorCell==3)
					{
						reportPatrol.setRuta(celda.getStringCellValue());
					}
					if(contadorCell==4) {
						reportPatrol.setRuta(celda.getStringCellValue());
						
					}if(contadorCell==5)
					{
						reportPatrol.setRuta(celda.getStringCellValue());
					}

				} else
					
					contadorCell++;
			}
			contadorRow++;
		}
		
		workbook.close();
	}*/
}
/*
 * switch (celda.getCellType()) { case Cell.CELL_TYPE_NUMERIC: if
 * (DateUtil.isCellDateFormatted(celda)) {
 * System.out.println(celda.getDateCellValue()); } else {
 * System.out.println(celda.getNumericCellValue()); } break; case
 * Cell.CELL_TYPE_STRING: System.out.println(celda.getStringCellValue());
 * //if(celda.getStringCellValue) break; case Cell.CELL_TYPE_BOOLEAN:
 * System.out.println(celda.getBooleanCellValue()); break; }
 */
