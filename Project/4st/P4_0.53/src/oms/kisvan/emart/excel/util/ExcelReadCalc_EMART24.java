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

public class ExcelReadCalc_EMART24 {
	public static HashMap<String, Object> read(ExcelReadOption excelReadOption, Map<String, Object> requestParamMap) {
		//반환객체 생성
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		try {
			// 엑셀 파일 자체
			// 엑셀파일을 읽어 들인다.
			// FileType.getWorkbook() <-- 파일의 확장자에 따라서 적절하게 가져온다./엑셀파일을 읽어서 Workbook 객체에 리턴한다. XLS와 XLSX 확장자를 비교한다.
			Workbook wb = ExcelFileType.getWorkbook(excelReadOption.getFilePath());
			DataFormatter formatter = new DataFormatter(); //formatter = 작성한 코드에 대해 설정된 규칙에 의해 정렬을 해주는 기능
	
			//변수 초기화
			int numOfCells = 0;
			Row row = null;
			Cell cell = null;
			String cellName = ""; 
			
			//엑셀 파일에 있는 시트 수를 변수에 담는다
			int sheetCnt = wb.getNumberOfSheets();
			
			/**
			 * 각 row마다의 값을 저장할 맵 객체 저장되는 형식은 다음과 같다. ("A", "이름") ("B", "게임명")
			 */
			Map<String, Object> map = null;
	
			List<Map<String, Object>> resultS = new ArrayList<Map<String, Object>>();//값을저장할list
			List<Map<String, Object>> resultF = new ArrayList<Map<String, Object>>();//에러난값을 저장할list
			//시트의 마지막 row값을 다음시트에 넘기기위한 변수
			//시퀀스 번호를 넘겨줄 변수
			Integer lastSeq = 0;
			/*
			 * 각 Row를 리스트에 담는다. 하나의 Row를 하나의 Map으로 표현되며 List에는 모든 Row가 포함될 것이다.
			 */
			for (int i = 0; i < sheetCnt; i++) {
				//일반 배달 0번 시트와 3번 픽업 시트를 제외 하고  업로드 처리 하지 않음
				if(i ==1 || i==2 || i > 3) continue;
				
				System.out.println("Sheet 이름: " + wb.getSheetName(i));
	
				Sheet sheet = wb.getSheetAt(i);

				// 필수 값 입력
				int numOfRows = sheet.getPhysicalNumberOfRows() - 1; // 유효한 데이터가 있는 행의 개수를 가져오고 그수의 -1
				
				//getStartRow()가  1부터 시작해서 - 1 해줘야 0부터 시작 함                                                                                1+유효데이터 -1
				//ExcelService에서 지정한 excelReadOption.getStartRow(시작row) / 이마트24시작row = 4임  
				//rowindex = 4-1; rowindex<=4+셀의 시작(4)부터 유효row-1 
				for (int rowIndex = excelReadOption.getStartRow() - 1; rowIndex <= (excelReadOption.getStartRow()+numOfRows-1); rowIndex++) {
					row = sheet.getRow(rowIndex);
					
					if (row != null) {
						//로우를 돌때마다 ++해서 lastSeq값에 저장 (시트가변경이되도 값이유지됨)
						lastSeq++;
	
						/*
						 * 가져온 Row의 Cell의 개수를 구한다.
						 */
						// numOfCells = row.getPhysicalNumberOfCells(); // 한개의 행마다 몇개의 cell이 있는지 체크
						numOfCells = excelReadOption.getOutputColumns().size()+1; // 등록한 컬럼 개수로 가져오기
						/*
						 * 데이터를 담을 맵 객체 초기화
						 */
						map = new HashMap<String, Object>();
						
						/*
						 * cell의 수 만큼 반복한다.
						 */
						map.put("USER_CD", requestParamMap.get("USER_CD"));
						map.put("SETTLE_MONTH", requestParamMap.get("SETTLE_MONTH")); //월정산 값을 map으로 받아서 넘겨줌
						// ExcelUpload_YGYD_STARTINDEX 에서 미리 셀렉트로 조회해서 SET_YOGIYO_DV_SEQ max값 
						//where절에 SETTLE_MONTH 포함으로 월별로 시퀀스 max값을 갖고옴
						//startIndex = 월별시퀀스번호 max값
						map.put("startIndex", requestParamMap.get("startIndex")); 
 						map.put("rowIndex",lastSeq);
							
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
							if (!excelReadOption.getOutputColumns().contains(cellName)) continue;
							
							// 구분
							if (cellName.equals("B")) {
								//읽어온 엑셀시트A에 시작row 값을 map저장
								map.put("DIVISION", ExcelCellRef.getValue(cell)); 
								continue;
							}
							// 점포명
							if (cellName.equals("C")) {
								map.put("STORE_NM", ExcelCellRef.getValue(cell).trim());
								continue;
							}
							// 점포코드
							if (cellName.equals("D")) {
								//셀에 0~9를제외한 값은 없는값으로 만듬 그값을date담음(숫자만찍히게되는거임)그리고 공백제거
								String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();
								//5자리가 아니면 에러를 찍어라
								if (CommonFunc.valueCheck("sto_id", data, true)) map.put("DOUBT", "Y");
								map.put("STORE_ID", data);//숫자만들어온값을 map에저장
								continue;
							}
							
							// 주문번호
							if (cellName.equals("E")) {
								String data = ExcelCellRef.getValue(cell).replaceAll("[^a-zA-Z0-9]", "").trim();
								//대소문자 a~z,A~Z,0~9 를 제외한 value값을 ""으로 변환하고 trim으로 공백을지움
								String ord_id = StringUtils.leftPad(String.valueOf(data), 20, " ");
								//셀에서 받아온값이 20자리가 안되면 왼쪽부터 0으로채움
								if (CommonFunc.valueCheck("ord_id", ord_id, true)) {
									//ord_id(!=20)이면 true ch_ord_dt_no 체크
									map.put("DOUBT", "Y");
								}
	
								//데이터 입력
								//에러가 나도  에러 엑셀에 입력 데이터를 표기
								map.put("CH_ORD_DT_NO", data); 		   	//상세주문번호
								map.put("CH_ORD_NO", data);    			//주문번호
								map.put("ORD_ID", ord_id);              //주문ID
								map.put("CH_CD", "08");               	//채널코드 하드코딩...

								continue;
							}

							// 주문일자, 주문일시
							if (cellName.equals("F") ) {
								//0부터 9까지 숫자만남기고 제거 그리고 trim으로 공백을 제거함
								String data = formatter.formatCellValue(cell).replaceAll("[^0-9]", "").trim();
								//셀에있는값을 formatter 으로 불러오고 그것을data에 넣어줌
								if (data.length() < 14) {
									data = StringUtils.rightPad(data, 14, "0");//14자리숫가안될경우 오른쪽끝부터 0으로 채움
								}
								if (CommonFunc.valueCheck("datetime", data, true)) {
								//formatter 으로 담아온 data 를 datetime 으로 yyyyMMddHHmm또는yyyyMMddHHmmss 형식으로 검증
									map.put("DOUBT", "Y");
								}
								//정산월보다 주문일자가 이전달일 경우 정산월에01일 으로 하기위함
								String ord_date = data.substring(0, 8);//0~7까지받고 8번째부터 값을안가져옴
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
	
								continue;
	
							}

							// 상품가합계
							if (cellName.equals("G")) {
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))//numberNullCheck= 공백있을경우 0으로표현
										.replaceAll("[^0-9\\-]", "").trim(); //0~9 하고 - 를 제외한 모든것을 ""으로 변형 하고 trim으로 공백을 제거
	
								map.put("SALE_ITEM_AMT", data);
	
								continue;
	
							}
							
							
	
							// 배달료
							if (cellName.equals("H")) {
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
										.replaceAll("[^0-9\\-]", "").trim();
	
								map.put("SALE_DELV_FEE", data);
	
								continue;
							}

							// 결제금액
							if (cellName.equals("I")) {
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
										.replaceAll("[^0-9\\-]", "").trim();
	
								map.put("SALE_PAY_AMT", data);
	
								continue;
							}
	
							// 수수료
							if (cellName.equals("J")) {
								
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
										.replaceAll("[^0-9\\-]", "").trim();
	
								map.put("CMS_VAT_INCLUDED", data);
	
								continue;
	
							}
							
							// 지급액
							if (cellName.equals("K")) {
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
										.replaceAll("[^0-9\\-]", "").trim();
	
								map.put("SETTLE_PAY_AMT", data);
	
								continue;
	
							}
	
							// 비고
							if (cellName.equals("L")) {
								map.put("NOTE", ExcelCellRef.getValue(cell).trim());
	
								continue;
	
							}
						}
						
						if (map.get("DOUBT") == null) { //map에 DOUBT(에러값)이 null일경우 resultS에 저장
							//셀렉트해서 불러온 max번호와 row값을 더한값을 시퀀스번호에 부여
							//map.put("SET_EMART24_DV_SEQ",seqCount);
							resultS.add(map);
						} else {//map에 DOUBT(에러값)이 있을경우 resultF에저장
							resultF.add(map);
						}
					}
				
					//이마트24 정산자료를 정상자료와 실패자료를 객체에 담는다
					//엑셀 정산 자료를 정상자료와 실패자료를 객체에 담는다
					paramMap.put("S", resultS);
					paramMap.put("F", resultF);
				}
			}
			
			wb.close();
			wb = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return paramMap;
	}
}