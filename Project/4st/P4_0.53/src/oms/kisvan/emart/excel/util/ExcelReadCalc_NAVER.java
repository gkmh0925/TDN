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

public class ExcelReadCalc_NAVER {
	public static HashMap<String, Object> read(ExcelReadOption excelReadOption, Map<String, Object> requestParamMap) {
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
		Integer negative = 0;//-인금액은 +로 ,+인 금액은 -으로 바꾸기 위한 변수
		Integer seqCount = 0;

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
						Object MaxCount = requestParamMap.get("startIndex");
						//엑셀1개의 로우 끝날때마다시퀀스 번호에 값을 넣어줄 변수 로우값+시퀀스 max = 엑셀업로드시마다 들어갈값
					    seqCount = rowIndex + (Integer)MaxCount;
						
						
						
						//NO. 23.01부터  추가됨 의미없어서 INSERT안함
						if(cellName.equals("A")) {
							ExcelCellRef.getValue(cell).trim();
							continue;
						}
						
						// 주문번호(PK)
						if (cellName.equals("B")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();

							if (CommonFunc.valueCheck("settle", data, true)) {
								map.put("DOUBT", "Y");
							}

							map.put("SETTLE_NO", data);
							map.put("CH_CD", "03");
							continue;
						}
						// 상품주문번호(PK)
						if (cellName.equals("C")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();
							if (CommonFunc.valueCheck("settle", data, true)) {
								map.put("DOUBT", "Y");
							}

							map.put("ITEM_ORD_ID", data);
							continue;
						}
						// 구분
						if (cellName.equals("D")) {
							map.put("GUBUN", ExcelCellRef.getValue(cell));
							continue;
						}
						// 상품명
						if (cellName.equals("E")) {
							map.put("ITEM_NM", ExcelCellRef.getValue(cell));
							continue;
						}
						// 구매자명
						if (cellName.equals("F")) {
							map.put("BUYER_NM", ExcelCellRef.getValue(cell));
							continue;
						}
						// 정산예정일
						if (cellName.equals("G")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();
							
							//데이터가 존재 하고 길이가 맞지 않으면 에러처리
							if (CommonFunc.IsHasString(data) && CommonFunc.valueCheck("date", data, true)) {
								map.put("DOUBT", "Y");
							}

							map.put("SETTLE_EXPECTED_DATE", data);
							continue;
						}
						// 정산완료일
						if (cellName.equals("H")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();
							
							//데이터가 존재 하고 길이가 맞지 않으면 에러처리
							if (CommonFunc.IsHasString(data) && CommonFunc.valueCheck("date", data, true)) {
								map.put("DOUBT", "Y");
							}

							map.put("SETTLE_COMPLET_DATE", data);
							continue;
						}
						// 정산기준일(PK)
						if (cellName.equals("I")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();
							if (CommonFunc.valueCheck("date", data, true)) {
								map.put("DOUBT", "Y");
							}
							//정산월보다 정산기준일이 이전달일 경우 정산월에01일 으로 하기위함
							String ord_date=data;
							String settle_month_str = (String) requestParamMap.get("SETTLE_MONTH");
							int ord_year = Integer.parseInt(data.substring(0, 4));
							int ord_month = Integer.parseInt(data.substring(4, 6));
							int settle_year = Integer.parseInt(settle_month_str.substring(0, 4));
							int settle_month = Integer.parseInt(settle_month_str.substring(4));
							if (ord_year < settle_year || (ord_year == settle_year && ord_month < settle_month)) {
								ord_date = settle_month_str + "01";
							}
							map.put("ORD_DATE", ord_date);
							continue;
						}
						// 세금신고기준일
						if (cellName.equals("J")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();
							
							//데이터가 존재 하고 길이가 맞지 않으면 에러처리
							if (CommonFunc.IsHasString(data) && CommonFunc.valueCheck("date", data, true)) {
								map.put("DOUBT", "Y");
							}

							map.put("TAX_RETURN_DATE", data);
							continue;
						}
						// 정산상태
						if (cellName.equals("K")) {
							map.put("SETTLE_STATE", ExcelCellRef.getValue(cell));
							continue;
						}
					
						// 결제금액(A)
						if (cellName.equals("L")) {
							map.put("ACT_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						// 수수료구분
						if (cellName.equals("M")) {
							map.put("CMS_GUBUN", ExcelCellRef.getValue(cell));
							continue;
						}
						// 결제수단
						if (cellName.equals("N")) {
							map.put("PAY_TYPE", ExcelCellRef.getValue(cell));
							continue;
						}
						// 매출연동 수수료 상세
						if (cellName.equals("O")) {
							map.put("SALE_LINK_CMS_GUBUN", ExcelCellRef.getValue(cell));
							continue;
						}
						// 수수료금액
						if (cellName.equals("P")) {
							String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim();
							negative = Integer.parseInt(data)*-1; //-인금액은 +로 ,+인 금액은 -으로 바꾸기 위해사용
							map.put("SALE_LINK_CMS",negative );
							continue;
						}
					}
					if (map.get("DOUBT") == null) {
						map.put("SET_NAVER_SEQ",seqCount);
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