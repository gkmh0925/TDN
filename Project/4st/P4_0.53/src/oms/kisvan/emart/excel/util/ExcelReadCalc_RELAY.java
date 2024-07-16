package oms.kisvan.emart.excel.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import oms.kisvan.emart.common.CommonFunc;

public class ExcelReadCalc_RELAY {
	public static HashMap<String, Object> read(ExcelReadOption excelReadOption, Map<String, Object> requestParamMap) {
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		// 엑셀 파일 자체
		// 엑셀파일을 읽어 들인다.
		// FileType.getWorkbook() <-- 파일의 확장자에 따라서 적절하게 가져온다.
		Workbook wb = ExcelFileType.getWorkbook(excelReadOption.getFilePath());
		DataFormatter formatter = new DataFormatter();
		
		int numOfCells = 0;
		Row row = null;
		Cell cell = null;
		String cellName = "";
		
		int sheetCnt = wb.getNumberOfSheets(); // 시트의 수를 가져오기 위한 변수
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
			// 에러 엑샐일 경우 2행부터 start
			if (i != 0 && !wb.getSheetName(i).equals("중계 수수료 정산")) {//0번째시트만 데이터를 담음
				continue;
			}

			Sheet sheet = wb.getSheetAt(i);

			int numOfRows = sheet.getPhysicalNumberOfRows() - 1; // 유효한 데이터가 있는 행의 개수를 가져온다.
			
			System.out.println("시작로우 : "+ excelReadOption.getStartRow());
			System.out.println("맥스로우 : "+ numOfRows);
			
			for (int rowIndex = excelReadOption.getStartRow() - 1; rowIndex <= numOfRows; rowIndex++) {
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
					
					// 필수 값 입력
					map.put("USER_CD", requestParamMap.get("USER_CD"));
					map.put("SETTLE_MONTH", requestParamMap.get("SETTLE_MONTH")); //월정산 값을 map으로 받아서 넘겨줌
					// ExcelUpload_YGYD_STARTINDEX 에서 미리 셀렉트로 조회해서 SET_YOGIYO_DV_SEQ max값 
					//where절에 SETTLE_MONTH 포함으로 월별로 시퀀스 max값을 갖고옴
					//startIndex = 월별시퀀스번호 max값
					map.put("startIndex", requestParamMap.get("startIndex")); 
					Object MaxCount = requestParamMap.get("startIndex");
					//엑셀1개의 로우 끝날때마다시퀀스 번호에 값을 넣어줄 변수 로우값+시퀀스 max = 엑셀업로드시마다 들어갈값
					Integer seqCount = rowIndex + (Integer)MaxCount;
					
					
					//엑셀 insert 마다 시퀀스 번호 증가하기위해  rowIndex값 만큼 증가
					//(값을 map으로 넘기기 때문에 통으로 insert되서 시퀀스 번호증감이 안되서 이렇게함 (10개를 insert를 하든 100개를 하든 1만증가되기때문에))
					//map.put("rowIndex", rowIndex);
					
					
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
						//A,B,E,F,H, U(금액),W	
						//매장코드
						if (cellName.equals("A")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();
							if (data.length() != 5) data = StringUtils.leftPad(data, 5, "0");
							if (CommonFunc.valueCheck("sto_id", data, true)) map.put("DOUBT", "Y");
							map.put("CPN_CD", data);
							continue;
						}
						//매장명
						if (cellName.equals("B")) {
							map.put("CPN_NM",  ExcelCellRef.getValue(cell).trim());
							continue;
						}
						String orderDate ="";
						
						if (cellName.equals("E")) {
							orderDate = ExcelCellRef.getValue(cell).trim();
							orderDate = orderDate.replaceAll("-", ""); 
							//if (CommonFunc.valueCheck("date", orderDate, true)) {
							//	map.put("DOUBT", "Y");
							//}
							map.put("ORD_DATE", orderDate);
							//System.out.println(" E번째의 값 : " + orderDate);
							continue;
						}
						String deliveryDate ="";
						
						if (cellName.equals("F")) {
							deliveryDate = ExcelCellRef.getValue(cell).trim();
							deliveryDate = deliveryDate.replaceAll("-", "");
							//if (CommonFunc.valueCheck("date", deliveryDate, true)) {
							//	map.put("DOUBT", "Y");
							//}
							map.put("DELIVERY_DATE", deliveryDate);
							
							//System.out.println(" F번째의 값 : " + deliveryDate);
							continue;
						}
						// 주문ID
						if (cellName.equals("H")) {
							String data = formatter.formatCellValue(cell).replaceAll("[^0-9]", "").trim();
							String ord_id = StringUtils.leftPad(data, 20, " ");
							if (CommonFunc.valueCheck("ord_id", ord_id, true)) {
								map.put("DOUBT", "Y");
							}
							map.put("ORD_ID", ord_id);
							//System.out.println(" H번째의 값 : " +  ord_id);
							continue;
						}
					
												
						String feeData = "";
						if (cellName.equals("U")) {
							map.put("KIS_FEE", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());continue;
						}
						
						if (cellName.equals("W")) {
							map.put("APPLY_GUBUN",  ExcelCellRef.getValue(cell).trim());
							//System.out.println(" W번째의 값 : " + ExcelCellRef.getValue(cell).trim());
							continue;
						}
						
					}
					if (map.get("DOUBT") == null) {
						//셀렉트해서 불러온 max번호와 row값을 더한값을 시퀀스번호에 부여
						map.put("SET_RELAY_SEQ",seqCount);
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