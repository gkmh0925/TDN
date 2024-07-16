package oms.kisvan.emart.excel.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import oms.kisvan.emart.common.CommonFunc;

public class ExcelReadCalc_CHAIN {
	public static HashMap<String, Object> read(ExcelReadOption excelReadOption, Object USER_CD) {
		
		System.out.println("체인로지스 엑셀리드셀 시작");
		// 엑셀 파일 자체
		// 엑셀파일을 읽어 들인다.
		// FileType.getWorkbook() <-- 파일의 확장자에 따라서 적절하게 가져온다.
		Workbook wb = ExcelFileType.getWorkbook(excelReadOption.getFilePath());
		HashMap<String, Object> paramMap = new HashMap<String, Object>();

		int sheetCnt = wb.getNumberOfSheets(); // 시트의 수를 가져오기 위한 변수
		int numOfCells = 0;

		Row row = null;
		Cell cell = null;

		String cellName = "";
		
		/**
		 * 각 row마다의 값을 저장할 맵 객체 저장되는 형식은 다음과 같다. put("A", "이름"); put("B", "게임명");
		 */
		Map<String, Object> map = null;

		List<Map<String, Object>> resultS = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> resultF = new ArrayList<Map<String, Object>>();

		/*
		 * 각 Row를 리스트에 담는다. 하나의 Row를 하나의 Map으로 표현되며 List에는 모든 Row가 포함될 것이다.
		 */

		for (int i = 0; i < sheetCnt; i++) {
			System.out.println("Sheet 이름: " + wb.getSheetName(i));

			int rowIndex = excelReadOption.getStartRow();
			// 에러 엑샐일 경우 2행부터 start
			if (wb.getSheetName(i).equals("체인로지스 정산리스트")) {
				rowIndex = excelReadOption.getStartRow() - 1;
			}
			Sheet sheet = wb.getSheetAt(i);

			int numOfRows = sheet.getPhysicalNumberOfRows() - 1; // 유효한 데이터가 있는 행의 개수를 가져온다.
			/* for (; rowIndex <= numOfRows; rowIndex++) { */
               for( rowIndex = 1; rowIndex <= numOfRows; rowIndex++) {
				row = sheet.getRow(rowIndex);

				if (row != null) {
					/*
					 * 가져온 Row의 Cell의 개수를 구한다.
					 */
					// numOfCells = row.getPhysicalNumberOfCells(); // 한개의 행마다 몇개의 cell이 있는지 체크
					numOfCells = excelReadOption.getOutputColumns().size(); // 등록한 컬럼 개수로 가져오기
					/*
					 * 데이터를 담을 맵 객체 초기화
					 */
					map = new HashMap<String, Object>();

					/*
					 * cell의 수 만큼 반복한다.
					 */
					for (int cellIndex = 0; cellIndex < numOfCells; cellIndex++) {

						/*
						 * Row에서 CellIndex에 해당하는 Cell을 가져온다.
						 */
						cell = row.getCell(cellIndex);
						/*
						 * 현재 Cell의 이름을 가져온다 이름의 예 : A,B,C,D,......
						 */
						cellName = ExcelCellRef.getName(cell, cellIndex);
						/*
						 * 추출 대상 컬럼인지 확인한다 추출 대상 컬럼이 아니라면, for로 다시 올라간다
						 */
						if (!excelReadOption.getOutputColumns().contains(cellName)) {
							continue;
						}
						/*
						 * map객체의 Cell의 이름을 키(Key)로 데이터를 담는다.
						 */

						// 필수 값 입력
						map.put("USER_CD", USER_CD);
						// 배달일자(PK)
						if (cellName.equals("A")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();

							if (CommonFunc.valueCheck("datetime", data, true)) {
								map.put("DOUBT", "Y");
							}

							String ord_date = data.substring(0, 8);
							
							map.put("ORD_DATE", ord_date);
							map.put("DELIVER_DATE", data);

							continue;
						}
						// 점포명
						if (cellName.equals("B")) {
							String data = ExcelCellRef.getValue(cell).trim();
							map.put("STORE_NM", data);
							continue;
						}
						// 예약번호
						if (cellName.equals("C")) {
							String data = ExcelCellRef.getValue(cell).trim();
							map.put("RESERVATION_NO", data);
							continue;
						}
						// 주문번호(PK)
						if (cellName.equals("D")) {
							String data = ExcelCellRef.getValue(cell).trim();
							map.put("ORD_ID", data);
							continue;
						}
						// 배달총금액(부가세포함)
						if (cellName.equals("E")) {
							String data = ExcelCellRef.getValue(cell).trim();
							map.put("DELIVERY_TOT_AMT", data);
							continue;
						}
					}

					if (map.get("DOUBT") == null) {
						resultS.add(map);

					} else {
						resultF.add(map);

					}
				}
				paramMap.put("S", resultS);
				paramMap.put("F", resultF);
			}
		}
		try {
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return paramMap;
	}
}