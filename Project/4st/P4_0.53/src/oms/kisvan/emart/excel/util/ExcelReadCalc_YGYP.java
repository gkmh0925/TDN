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

public class ExcelReadCalc_YGYP {
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
			if (i != 0 && !wb.getSheetName(i).equals("요기요 정산리스트")) {//0번째시트가아니면 에러를 표출
				continue;
			}

			Sheet sheet = wb.getSheetAt(i);

			int numOfRows = sheet.getPhysicalNumberOfRows() - 1; // 유효한 데이터가 있는 행의 개수를 가져온다.

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
					
					//엑셀 insert 마다 시퀀스 번호 증가하기위해 로우 rowIndex값 만큼 증가 (값을 map으로 넘기기 때문에 통으로 insert되서 시퀀스 번호증감이 안되서 이렇게함 )
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

						// 주문일시
						if (cellName.equals("A")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();

							if (CommonFunc.valueCheck("datetime", data, true)) {
								map.put("DOUBT", "Y");
							}

							if (data.length() < 14) {
								data = StringUtils.rightPad(data, 14, "0");
							}
							//정산월보다 주문일시 이전달일 경우 정산월에01일 으로 하기위함
							String ord_date = data.substring(0, 8);
							String settle_month_str = (String) requestParamMap.get("SETTLE_MONTH");
							int ord_year = Integer.parseInt(ord_date.substring(0, 4));
							int ord_month = Integer.parseInt(ord_date.substring(4, 6));
							int settle_year = Integer.parseInt(settle_month_str.substring(0, 4));
							int settle_month = Integer.parseInt(settle_month_str.substring(4));
							if (ord_year < settle_year || (ord_year == settle_year && ord_month < settle_month)) {
							    ord_date = settle_month_str + "01";
							}
							map.put("ORD_DATE", ord_date);
							map.put("ORD_TIME", data);
							map.put("CH_CD", "01");
							continue;
						}
						// 환불일시
						if (cellName.equals("B")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();

							if (CommonFunc.valueCheck("datetime", data, false)) {
								map.put("DOUBT", "Y");
							}

							map.put("REFUND_TIME", data);

							continue;
						}
						// 지급예정 일자
						if (cellName.equals("C")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();

							if (CommonFunc.valueCheck("date", data, true)) {
								map.put("DOUBT", "Y");
							}

							map.put("PAY_EXPECTED_DATE", data);

							continue;
						}
						// 사업자번호
						if (cellName.equals("D")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();

							if (CommonFunc.valueCheck("biz_no", data, true)) {
								map.put("DOUBT", "Y");
							}

							map.put("BIZ_NO", data);

							continue;
						}
						// 음식점명
						if (cellName.equals("E")) {
							map.put("STORE_NM", ExcelCellRef.getValue(cell).trim());

							continue;
						}
						// 매장 코드
						if (cellName.equals("F")) {//요기요 픽업은 매장코드를 텍스트로 안해서 앞에 0빠지는녀석들이 많아서 5자리가아닐경우 0으로채우게 함
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();
							//5자리가 아니면 왼쪽에 0을 추가한다.
							if (data.length() != 5) data = StringUtils.leftPad(data, 5, "0");
							
							//값이 맞는지 체크
							if (CommonFunc.valueCheck("sto_id", data, true)) map.put("DOUBT", "Y");
						    
							//데이터를 담는다
						    map.put("STORE_ID", data);
						    
						    continue;
						}
						// 주문ID
						if (cellName.equals("G")) {
							String data = formatter.formatCellValue(cell).replaceAll("[^0-9]", "").trim();
							String ord_id = StringUtils.leftPad(data, 20, " ");

							if (CommonFunc.valueCheck("ord_id", ord_id, true)) {
								map.put("DOUBT", "Y");
							}
							map.put("CH_ORD_NO", data);
							map.put("ORD_ID", ord_id);

							continue;
						}
						// 주문번호
						if (cellName.equals("H")) {
							map.put("ord_id", ExcelCellRef.getValue(cell).trim());

							continue;
						}
						// 상품가 합계
						if (cellName.equals("I")) {
							map.put("ITEM_SUM_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());

							continue;
						}
						// 최소주문 추가결제액
						if (cellName.equals("J")) {
							map.put("MIN_ORD_ADD_PAY", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());

							continue;
						}
						// 배달료
						if (cellName.equals("K")) {
							map.put("DELIVER_FEE", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());

							continue;
						}
						// 일회용컵 보증금
						if (cellName.equals("L")) {//엑셀에새로생겨서 컬럼은 추가 하지않고 건너뛰위해 작업
							ExcelCellRef.getValue(cell).trim();
							continue;
						}
						// 주문 총액
						if (cellName.equals("M")) {
							map.put("ORD_TOT_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());

							continue;
						}
						// 주문 금액 구분 : 온라인 주문
						if (cellName.equals("N")) {
							map.put("ORD_ONLINE_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());

							continue;
						}
						// 주문 금액 구분 : 현장 주문
						if (cellName.equals("O")) {
							map.put("ORD_OFFLINE_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());

							continue;
						}
						// 레스토랑 자체할인
						if (cellName.equals("P")) {
							map.put("RESTAURANT_SELF_DIS", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());

							continue;
						}
						// 요타임딜 할인(20231004)추가
						if (cellName.equals("Q")) {//엑셀에새로생겨서 컬럼은 추가 하지않고 건너뛰위해 작업
							ExcelCellRef.getValue(cell).trim();	
							continue;
						}
						// 쿠폰할인금액:프랜차이즈 부담
						if (cellName.equals("R")) {//엑셀에새로생겨서 컬럼은 추가 하지않고 건너뛰위해 작업
							ExcelCellRef.getValue(cell).trim();	
							continue;
						}
						// 쿠폰할인금액:레스토랑 부담
						if (cellName.equals("S")) {//엑셀에새로생겨서 컬럼은 추가 하지않고 건너뛰위해 작업
							ExcelCellRef.getValue(cell).trim();	
							continue;
						}
						// 프로모션 할인금액 : 요기요 부담
						if (cellName.equals("T")) {
							map.put("PMT_DIS_YOGIYO_LEVY_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());

							continue;
						}
						// 프로모션 할인금액 : 프랜차이즈 부담
						if (cellName.equals("U")) {
							map.put("PMT_DIS_FRANCHISEE_LEVY_AMT", CommonFunc
									.numberNullCheck(ExcelCellRef.getValue(cell)).replaceAll("[^0-9\\-]", "").trim());

							continue;
						}
						// 프로모션 할인금액 : 레스토랑 부담
						if (cellName.equals("V")) {
							map.put("PMT_DIS_RESTAURANT_LEVY_AMT", CommonFunc
									.numberNullCheck(ExcelCellRef.getValue(cell)).replaceAll("[^0-9\\-]", "").trim());

							continue;
						}
						// 주문중개이용료 : 주문중개이용료율(+VAT)
						if (cellName.equals("W")) {
							map.put("ORD_RELAY_FEE_RATE", ExcelCellRef.getValue(cell).trim());

							continue;
						}
						// 주문중개이용료 : 주문중개이용료 총액
						if (cellName.equals("X")) {
							map.put("ORD_RELAY_FEE_TOT_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());

							continue;
						}
						// 결제이용료 : 결제이용료율(+VAT)
						if (cellName.equals("Y")) {
							map.put("PAY_RELAY_FEE_RATE", ExcelCellRef.getValue(cell).trim());

							continue;
						}
						// 결제이용료 : 결제이용료 총액
						if (cellName.equals("Z")) {
							map.put("PAY_RELAY_FEE_TOT_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());

							continue;
						}
						// 배달대행이용료 : 배달대행이용료 총액
						if (cellName.equals("AA")) {
							map.put("DELIVERY_AGENT_FEE", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());

							continue;
						}
						// 이용료 전체 합계
						if (cellName.equals("AB")) {
							map.put("TOT_SUM_FEE", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());

							continue;
						}
						// OD배달료 매출 : 레스토랑 자체펀딩 총액
						if (cellName.equals("AC")) {
							map.put("OD_DELI_FEE_REST_SELF_FUND", CommonFunc
									.numberNullCheck(ExcelCellRef.getValue(cell)).replaceAll("[^0-9\\-]", "").trim());

							continue;
						}
						// 요타임딜이용료 : 요타임딜이용료 총액
						if (cellName.equals("AD")) {
							map.put("YO_TIME_FEE_TOT_YO_TIME_FEE", CommonFunc
									.numberNullCheck(ExcelCellRef.getValue(cell)).replaceAll("[^0-9\\-]", "").trim());

							continue;
						}
						
						// 추천광고이용료:추천광고이용료 총액
						if (cellName.equals("AE")) {
							map.put("REFERRAL_ADVERTISEMENT_FEE", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						// 정산 금액
						if (cellName.equals("AF")) {
							map.put("SETTLE_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());

							continue;
						}
						// 요기요부담할인총액(20231004)
						if (cellName.equals("AG")) {
							//map.put("PAY_TYPE", ExcelCellRef.getValue(cell).trim());
							//ExcelCellRef.getValue(cell).trim();
							continue;
						}
						// 결제유형
						if (cellName.equals("AH")) {
							map.put("PAY_TYPE", ExcelCellRef.getValue(cell).trim());
							//ExcelCellRef.getValue(cell).trim();
							continue;
						}
						// 결제유형 상세
						if (cellName.equals("AI")) {
							map.put("PAY_TYPE_DETAIL", ExcelCellRef.getValue(cell).trim());

							continue;
						}
						// 결제형태
						if (cellName.equals("AJ")) {
							map.put("PAY_FORM", ExcelCellRef.getValue(cell).trim());

							continue;
						}
						// 결제형태 상세
						if (cellName.equals("AK")) {
							map.put("PAY_FORM_DETAIL", ExcelCellRef.getValue(cell).trim());

							continue;
						}
						// 계약유형(20231004)
						if (cellName.equals("AL")) {				
											
							continue;
						}
						// 배달 유형(VD/OD)(20231004) 없어짐
//						if (cellName.equals("AJ")) {
//							map.put("DELIVERY_TYPE", ExcelCellRef.getValue(cell).trim());
//						//	ExcelCellRef.getValue(cell).trim();
//							continue;
//						}
						// 포장여부
						if (cellName.equals("AM")) {//엑셀에새로생겨서 컬럼은 추가 하지않고 건너뛰위해 작업
							ExcelCellRef.getValue(cell).trim();	
							continue;
						}
						// 프랜차이즈 ID
						if (cellName.equals("AN")) {
						  map.put("FRANCHISEE_ID", ExcelCellRef.getValue(cell).trim());
						//	ExcelCellRef.getValue(cell).trim();

							continue;
						}
						// 회사명
						if (cellName.equals("AO")) {
							map.put("CPN_NM", ExcelCellRef.getValue(cell).trim());
						//	ExcelCellRef.getValue(cell).trim();
							continue;
						}
						// 사업자명
						if (cellName.equals("AP")) {
							map.put("BIZ_NM", ExcelCellRef.getValue(cell).trim());
						//	ExcelCellRef.getValue(cell).trim();
							continue;
						}
						// 배달주소1
						if (cellName.equals("AQ")) {
							map.put("DELIVERY_ADDRESS1", ExcelCellRef.getValue(cell).trim());
						//	ExcelCellRef.getValue(cell).trim();
							continue;
						}
					}

					if (map.get("DOUBT") == null) {
						//셀렉트해서 불러온 max번호와 row값을 더한값을 시퀀스번호에 부여
						map.put("SET_YOGIYO_PU_SEQ",seqCount);
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