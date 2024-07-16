package oms.kisvan.emart.excel.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import oms.kisvan.emart.common.CommonFunc;

public class ExcelReadCalc_BAROGO {
	public static HashMap<String, List<Map<String, Object>>> read(ExcelReadOption excelReadOption,Map<String, Object> requestParamMap) {
		// 엑셀 파일 자체
		// 엑셀파일을 읽어 들인다.
		// FileType.getWorkbook() <-- 파일의 확장자에 따라서 적절하게 가져온다.
		Workbook wb = ExcelFileType.getWorkbook(excelReadOption.getFilePath());

		HashMap<String, List<Map<String, Object>>> paramMap = new HashMap<String, List<Map<String, Object>>>();
		
		int sheetCnt = wb.getNumberOfSheets(); // 시트의 수를 가져오기 위한 변수
		int numOfCells = 0;

		Row row = null;
		Cell cell = null;

		String cellName = "";

		/**
		 * 각 row마다의 값을 저장할 맵 객체 저장되는 형식은 다음과 같다. put("A", "이름"); put("B", "게임명");
		 */
		Map<String, Object> map = new HashMap<String, Object>();

		List<Map<String, Object>> resultS = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> resultF = new ArrayList<Map<String, Object>>();

		/*
		 * 각 Row를 리스트에 담는다. 하나의 Row를 하나의 Map으로 표현되며 List에는 모든 Row가 포함될 것이다.
		 */

		for (int i = 0; i < sheetCnt; i++) {
			Sheet sheet = wb.getSheetAt(i);

			// 유효한 데이터가 있는 행의 개수를 가져온다.
			int numOfRows = sheet.getPhysicalNumberOfRows(); 
			
			//유효한 행 만큼 데이터를 읽어서 처리
			for (int rowIndex = 4; rowIndex <= numOfRows; rowIndex++) {
				row = sheet.getRow(rowIndex);
				
				if (row != null) {
					/*
					 * 가져온 Row의 Cell의 개수를 구한다.
					 */
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
						map.put("USER_CD", requestParamMap.get("USER_CD"));
						map.put("SETTLE_MONTH", requestParamMap.get("SETTLE_MONTH")); //월정산 값을 map으로 받아서 넘겨줌
						// ExcelUpload_YGYD_STARTINDEX 에서 미리 셀렉트로 조회해서 SET_YOGIYO_DV_SEQ max값 
						//where절에 SETTLE_MONTH 포함으로 월별로 시퀀스 max값을 갖고옴
						//startIndex = 월별시퀀스번호 max값
						map.put("startIndex", requestParamMap.get("startIndex")); 
						//엑셀1개의 로우 끝날때마다시퀀스 번호에 값을 넣어줄 변수 로우값+시퀀스 max = 엑셀업로드시마다 들어갈값
						map.put("rowIndex", rowIndex-4);

						// 접수번호
						if (cellName.equals("A")) {
							//map.put("RECEIPT_NO", dataFormatter.formatCellValue(cell).trim());
							cell.setCellType(CellType.STRING); //엑셀데이터를 스트링으로 받기위함
							map.put("RECEIPT_NO", ExcelCellRef.getValue(cell).trim());
							continue;
						}
						
						// 주문번호
						if (cellName.equals("B")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^a-zA-Z0-9]", "").trim();
							String ord_id = StringUtils.leftPad(String.valueOf(data), 20, "0");
							if (CommonFunc.valueCheck("ord_id", ord_id, true)) {
								map.put("DOUBT", "Y");
							}
							map.put("ORD_ID", ord_id);
							continue;
						}
						// 접수일자
						if (cellName.equals("C")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();
							if (CommonFunc.valueCheck("date", data, false)) {
								map.put("DOUBT", "Y");
							}
							//정산월보다 주문일시(자) 이전달일 경우 정산월에01일 으로 하기위함
							String ord_date=data;;
							String settle_month_str = (String) requestParamMap.get("SETTLE_MONTH");
							int ord_year = Integer.parseInt(ord_date.substring(0, 4));
							int ord_month = Integer.parseInt(ord_date.substring(4, 6));
							int settle_year = Integer.parseInt(settle_month_str.substring(0, 4));
							int settle_month = Integer.parseInt(settle_month_str.substring(4));
							if (ord_year < settle_year || (ord_year == settle_year && ord_month < settle_month)) {
							    ord_date = settle_month_str + "01";
							}
							map.put("ORD_DATE", data);
							map.put("RECEIPT_DATE", ord_date);

							continue;
						}
						// 접수시간
						if (cellName.equals("D")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();
							if (CommonFunc.valueCheck("time", data, false)) {
								map.put("DOUBT", "Y");
							}
							map.put("RECEIPT_TIME", data);
							continue;
						}
						// 완료시간
						if (cellName.equals("E")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();
							if (CommonFunc.valueCheck("time", data, false)) {
								map.put("DOUBT", "Y");
							}
							map.put("COMPLET_TIME", data);
							continue;
						}
						// 취소시간
						if (cellName.equals("F")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();
							if (CommonFunc.valueCheck("time", data, false)) {
								map.put("DOUBT", "Y");
							}
							map.put("CANCEL_TIME", data);
							continue;
						}
						// 발주가맹점명
						if (cellName.equals("G")) {
							map.put("ORD_ISSUER_NM", ExcelCellRef.getValue(cell).trim());
							continue;
						}
						// 거리(km)
						if (cellName.equals("H")) {
							map.put("DISTANCE", ExcelCellRef.getValue(cell).trim());
							continue;
						}
						// 기본운임
						if (cellName.equals("I")) {
							map.put("BASIC_FARE_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						// 거리운임
						if (cellName.equals("J")) {
							map.put("DISTANCE_FARE_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						// 픽업할증
						if (cellName.equals("K")) {
							map.put("PICKUP_PREMIUM_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						// 심야할증
						if (cellName.equals("L")) {
							map.put("LATE_NIGTH_PREMIUM_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						// 우천할증
						if (cellName.equals("M")) {
							map.put("RAIN_PREMIUM_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						// 추가할증
						if (cellName.equals("N")) {
							map.put("ADD_PREMIUM_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						// 구역할증
						if (cellName.equals("O")) {
							map.put("AREA_PREMIUM_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						// 상품할증
						if (cellName.equals("P")) {
							map.put("ITEM_PREMIUM_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						// 배송운임
						if (cellName.equals("Q")) {
							map.put("DELIVERY_FARE_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						// VAT
						if (cellName.equals("R")) {
							map.put("VAT_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						// VAT포함
						if (cellName.equals("S")) {
							map.put("VAT_INCLUDE_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						// 접수채널
						if (cellName.equals("T")) {
							map.put("RECEIPT_CH", ExcelCellRef.getValue(cell).trim());
							continue;
						}
						// 비고
						if (cellName.equals("U")) {
							map.put("BIGO", ExcelCellRef.getValue(cell).trim());
							continue;
						}
					}

					boolean isDuplicate = false;
					for(Map<String, Object> result : resultS) {//첫번째 데이터일경우는 pass 하고 두번째데이터부터는 map 담기고 1번째 데이터는 2번째 바퀴부터 result담겨서 비교
						isDuplicate = isDuplicateRow(map, result);
						if(isDuplicate) break;
						//데이터중에 ORD_DATE,RECEIPT_TIME,ORD_ID 4개를 비교해서 중복된값이 있는지 비교
						//map 과 result를 비교해서 같으면 true를 뱉어서 if(isDuplicate)일때 resultF에담김 값이 같지않으면 false를 뱉고 그데이터가 정상일경우
						//else if (map.get("DOUBT") == null)에 일때 resultS에 담김 그외 중복 데이터가 아니지만 형식오류인경우도 resultF에 담김
					}
					if(isDuplicate) {//데이터가 중복이면 담김
						resultF.add(map);
						
					} else if (map.get("DOUBT") == null) {//데이터가 이상이없으면 담김
						resultS.add(map);

					} else {//그외 담김
						resultF.add(map);
					}
					
				}
				paramMap.put("S", resultS);//전체 정상건 들이 담긴 map
				paramMap.put("F", resultF);//데이터에 문제가 있거나 중복건이 담긴 map 
			}
			paramMap = filterDuplicateRows(paramMap);// isDuplicateRow 가서  resultS와 resultF를 비교해서 중복건인 경우 true 중복이 아닐경우 false 뱉음
			
		}
		try {
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramMap;
	}
	
	private static HashMap<String, List<Map<String, Object>>> filterDuplicateRows(HashMap<String, List<Map<String, Object>>> paramMap) {
		//성공한 row 정보들을 담을 새로운 LIST 생성
		List<Map<String, Object>> successMap = new ArrayList<>(paramMap.get("S"));
		//성공한 row 정보들을 반복하며 실패한 row정보에 있는지 확인
		for(Map<String, Object> row : successMap) {//성공한 row만큼 돌기시작
			boolean isDuplicate = false;
			for(Map<String, Object> fail : paramMap.get("F")) {
				//성공한 row의 데이터가 실패한fail를 돌아서 row가 자기자신과 같은 데이터가있는지 확인
				//같은데이터가 있을경우 resultS에 있는 row를 삭제 하고 true 진행
				if (/* fail.containsValue(row.get("STORE_ID").toString()) && */
				   fail.containsValue(row.get("RECEIPT_DATE").toString()) &&
				   fail.containsValue(row.get("RECEIPT_TIME").toString()) &&
				   fail.containsValue(row.get("ORD_ID").toString())) {
					paramMap.get("S").remove(row);
					isDuplicate = true;
				}
			}//true일때 지웠던 row의 데이터를 resultF에 추가함
			if(isDuplicate) paramMap.get("F").add(row);
		}
		
		return paramMap;
	}
	

	private static boolean isDuplicateRow(Map<String, Object> map, Map<String, Object> result) { 
		//result 와 map 의데이터를 비교 하고 중복이면 true,중복이아닐경우 false뱉음
		return (/* result.get("STORE_ID").equals(map.get("STORE_ID")) && */
			    result.get("RECEIPT_DATE").equals(map.get("RECEIPT_DATE")) &&
			    result.get("RECEIPT_TIME").equals(map.get("RECEIPT_TIME")) &&
			    result.get("ORD_ID").equals(map.get("ORD_ID")));
	}
}
