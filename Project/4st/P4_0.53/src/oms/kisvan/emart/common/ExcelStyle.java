package oms.kisvan.emart.common;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;

/**
 * @Author : 이준혁
 * @Date : 2022. 8. 29.
 * @Explan : 엑셀 스타일 지정
 */
public class ExcelStyle {

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 29.
	 * @Method : headerStyle
	 * @return : XSSFCellStyle
	 * @Explan : 엑셀 헤더(컬럼) 스타일
	 */
	public static XSSFCellStyle headerStyle(SXSSFWorkbook wb) {

		XSSFCellStyle headStyle = (XSSFCellStyle) wb.createCellStyle();

		// 배경 색 설정
		headStyle.setFillForegroundColor(new XSSFColor(new byte[] { (byte) 244, (byte) 244, (byte) 244 }, null));
		headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// border값 지정
		headStyle.setBorderBottom(BorderStyle.THIN);
		headStyle.setBottomBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		headStyle.setBorderRight(BorderStyle.THIN);
		headStyle.setRightBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		headStyle.setBorderTop(BorderStyle.THIN);
		headStyle.setTopBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		headStyle.setBorderLeft(BorderStyle.THIN);
		headStyle.setLeftBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));

		// 글자 중앙 정렬
		headStyle.setAlignment(HorizontalAlignment.CENTER); // 가로 중앙
		headStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 세로 중앙

		XSSFFont headerFont = (XSSFFont) wb.createFont();
		headerFont.setFontName("Microsoft JhengHei");
		headerFont.setFontHeight((short) 200);
		headerFont.setColor(new XSSFColor(new byte[] { (byte) 113, (byte) 113, (byte) 113 }, null));
		headerFont.setBold(true);

		headStyle.setFont(headerFont);

		return headStyle;
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 29.
	 * @Method : bodyStyle
	 * @return : XSSFCellStyle
	 * @Explan : 엑셀 바디(필드) 스타일
	 */
	public static XSSFCellStyle bodyStyle(SXSSFWorkbook wb) {

		XSSFCellStyle bodyStyle = (XSSFCellStyle) wb.createCellStyle();

		bodyStyle.setBorderBottom(BorderStyle.THIN);
		bodyStyle.setBottomBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		bodyStyle.setBorderRight(BorderStyle.THIN);
		bodyStyle.setRightBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		bodyStyle.setBorderTop(BorderStyle.THIN);
		bodyStyle.setTopBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		bodyStyle.setBorderLeft(BorderStyle.THIN);
		bodyStyle.setLeftBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));

		// 글자 중앙 정렬
		bodyStyle.setAlignment(HorizontalAlignment.CENTER); // 가로 중앙
		bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 세로 중앙

		XSSFFont bodyFont = (XSSFFont) wb.createFont();
		bodyFont.setFontName("Microsoft JhengHei");
		bodyFont.setFontHeight((short) 180);
		bodyFont.setColor(new XSSFColor(new byte[] { (byte) 51, (byte) 51, (byte) 51 }, null));

		bodyStyle.setFont(bodyFont);

		return bodyStyle;
	}

	/**
	 * @Author : 이준혁
	 * @Date : 2022. 8. 29.
	 * @Method : numStyle
	 * @return : XSSFCellStyle
	 * @Explan : 엑셀 숫자 스타일 (금액)
	 */
	public static XSSFCellStyle numStyle(SXSSFWorkbook wb) {
		XSSFCellStyle numStyle = (XSSFCellStyle) wb.createCellStyle();

		numStyle.setBorderBottom(BorderStyle.THIN);
		numStyle.setBottomBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		numStyle.setBorderRight(BorderStyle.THIN);
		numStyle.setRightBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		numStyle.setBorderTop(BorderStyle.THIN);
		numStyle.setTopBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		numStyle.setBorderLeft(BorderStyle.THIN);
		numStyle.setLeftBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		numStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

		XSSFFont numFont = (XSSFFont) wb.createFont();
		numFont.setFontName("Microsoft JhengHei");
		numFont.setFontHeight((short) 180);
		numFont.setColor(new XSSFColor(new byte[] { (byte) 51, (byte) 51, (byte) 51 }, null));

		numStyle.setFont(numFont);

		return numStyle;

	}
}
