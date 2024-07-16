package oms.kisvan.emart.excel.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelCellRef {
	/**
	 * Cell에 해당하는 Column Name을 가젼온다(A,B,C..) 만약 Cell이 Null이라면 int cellIndex의 값으로
	 * Column Name을 가져온다.
	 * 
	 * @param cell
	 * @param cellIndex
	 * @return
	 */

	public static String getName(Cell cell, int cellIndex) {
		int cellNum = 0;
		if (cell != null) {
			cellNum = cell.getColumnIndex();
		} else {
			cellNum = cellIndex;
		}

		return CellReference.convertNumToColString(cellNum);
	}

	public static String getValue(Cell cell) {
		String value = "";
		DecimalFormat df = new DecimalFormat("0.############");
		DataFormatter formatter = new DataFormatter();

		if (cell == null) {
			return value = "";
		}

		switch (cell.getCellType()) {
		case FORMULA:
			value = cell.getNumericCellValue() + "";
			value = value.substring(value.indexOf("."), value.length()).equals(".0") ? value.replace(".0", "") : value;
			break;

		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				double val = cell.getNumericCellValue();
				if (DateUtil.isValidExcelDate(val)) {
					int length = formatter.formatCellValue(cell).replaceAll("[^0-9]", "").length();
					SimpleDateFormat sdf = null;

					if (length == 4) {
						sdf = new SimpleDateFormat("HHmm");
					} else if (length == 6) {
						sdf = new SimpleDateFormat("HHmmss");
					} else if (length == 8) {
						sdf = new SimpleDateFormat("yyyyMMdd");
					} else if (length == 12) {
						sdf = new SimpleDateFormat("yyyyMMddHHmm");
					} else if (length == 14) {
						sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					}

					try {
						value = sdf.format(cell.getDateCellValue()) + "";
					} catch (Exception e) {
						value = "";
					}
				} else {
					value = df.format(cell.getNumericCellValue()) + "";
				}
			} else {
				value = cell.getNumericCellValue() + "";
				value = value.substring(value.indexOf("."), value.length()).equals(".0") ? value.replace(".0", "")
						: value;
			}
			break;

		case STRING:
			value = (cell.getRichStringCellValue().getString() != null ? cell.getRichStringCellValue().getString()
					: "");
			break;

		case BOOLEAN:
			value = cell.getBooleanCellValue() + "";
			break;

		case BLANK:
			value = "";
			break;

		case ERROR:
			value = cell.getErrorCellValue() + "";
			break;

		default:
			value = cell.getStringCellValue() + "";
			break;
		}

		return value;
	}

	public static void readCell(String excelFilePath, int rowIndex, int columnIndex)
			throws FileNotFoundException, IOException {
		try (InputStream inp = new FileInputStream(excelFilePath)) {
			XSSFWorkbook wb = new XSSFWorkbook(inp);
			XSSFCell cell = wb.getSheetAt(0).getRow(rowIndex).getCell(columnIndex);

			switch (cell.getCellType()) {

			case STRING:
				System.out.println(cell.getRichStringCellValue().getString());
				break;

			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					System.out.println(cell.getDateCellValue());
				} else {
					System.out.println(cell.getNumericCellValue());
				}
				break;

			case BOOLEAN:
				System.out.println(cell.getBooleanCellValue());
				break;

			case FORMULA:
				System.out.println(cell.getCellFormula());
				break;

			case BLANK:
				System.out.println();
				break;

			default:
				System.out.println();
			}

			wb.close();
		}
	}

	public static void isInternalDateFormat(short dataFormat) {
		// TODO Auto-generated method stub
		
	}

}