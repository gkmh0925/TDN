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
import org.apache.poi.util.IOUtils;

import oms.kisvan.emart.common.CommonFunc;

public class ExcelReadCalc_BAEMIN {
	public static HashMap<String, Object> read(ExcelReadOption excelReadOption, Map<String, Object> requestParamMap) {
		//반환객체 생성
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		try {
			//공간 확보를 위해 선언
			IOUtils.setByteArrayMaxOverride(500000000);
			
			// 엑셀 파일 자체
			// 엑셀파일을 읽어 들인다.
			// FileType.getWorkbook() <-- 파일의 확장자에 따라서 적절하게 가져온다.
			Workbook wb = ExcelFileType.getWorkbook(excelReadOption.getFilePath());
			DataFormatter formatter = new DataFormatter();

			//변수초기화
			int numOfCells = 0;
			Row row = null;
			Cell cell = null;
			String cellName = "";
			
			//엑셀의 -차감금액 부분을 -처리해서 -면 +가되게, +면 -로 처리되게 하기위한 변수 
			// cellName.equals("Y")~cellName.equals("E") 까지처리
			Integer negative = 0;
			
			//엑셀 파일에 있는 시트 수를 변수에 담는다
			int sheetCnt = wb.getNumberOfSheets();
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

				for (int rowIndex = excelReadOption.getStartRow() - 1; rowIndex <= (excelReadOption.getStartRow()+numOfRows-1); rowIndex++) {
					
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
							map.put("USER_CD", requestParamMap.get("USER_CD"));
							map.put("SETTLE_MONTH", requestParamMap.get("SETTLE_MONTH")); //월정산 값을 map으로 받아서 넘겨줌
							
							// ExcelUpload_YGYD_STARTINDEX 에서 미리 셀렉트로 조회해서 SET_YOGIYO_DV_SEQ max값 
							//where절에 SETTLE_MONTH 포함으로 월별로 시퀀스 max값을 갖고옴
							//startIndex = 월별시퀀스번호 max값
							map.put("startIndex", requestParamMap.get("startIndex")); 
							Object MaxCount = requestParamMap.get("startIndex");
							//엑셀1개의 로우 끝날때마다시퀀스 번호에 값을 넣어줄 변수 로우값+시퀀스 max = 엑셀업로드시마다 들어갈값
							Integer seqCount = (rowIndex-2) + (Integer)MaxCount;
							
							//엑셀 insert 마다 시퀀스 번호 증가하기위해 로우 rowIndex값 만큼 증가(rowIndex는 for문안에서 로우를 돌때마다 ++증감이됨)
							//rowIndex-2를 하는이유는  시퀀스번호가 1부터시작해야하기때문에 (배민의경우는 row가 3부터시작해서 -2함)
							//map.put("rowIndex", rowIndex-2);
							
							
							//Integer seqCount = 0;
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
							
							/*
							 * map객체의 Cell의 이름을 키(Key)로 데이터를 담는다.
							 */
							// 지점번호
							if (cellName.equals("A")) {
								ExcelCellRef.getValue(cell).trim();
								//String data = ExcelCellRef.getValue(cell).trim();
								//map.put("BRANCH_NO", data);
								continue;
							}
							// 상호명
							if (cellName.equals("B")) {
								map.put("BRANCH_NM", ExcelCellRef.getValue(cell).trim());
								continue;
							}
							// 지급예정일자
							if (cellName.equals("C")) {
								//셀에있는값을 formatter 으로 불러오고 그것을data에 넣어줌
								String data = formatter.formatCellValue(cell).replaceAll("[^0-9]", "").trim();
								if (CommonFunc.valueCheck("date", data, true)) {
									map.put("DOUBT", "Y");
								}
								map.put("EXP_PAY_DATE", data);
								map.put("CH_CD", "07");
								continue;
							}
							// 결제일자
							if (cellName.equals("D")) {
								//셀에있는값을 formatter 으로 불러오고 그것을data에 넣어줌
								String data = formatter.formatCellValue(cell).replaceAll("[^0-9]", "").trim();

								if (CommonFunc.valueCheck("date", data, true)) {
									map.put("DOUBT", "Y");
								}
								//정산월보다 주문일자가 이전달일 경우 정산월에01일 으로 하기위함
								String ord_date=data;
								String settle_month_str = (String) requestParamMap.get("SETTLE_MONTH");
								int ord_year = Integer.parseInt(data.substring(0, 4));
								int ord_month = Integer.parseInt(data.substring(4, 6));
								int settle_year = Integer.parseInt(settle_month_str.substring(0, 4));
								int settle_month = Integer.parseInt(settle_month_str.substring(4));
								if (ord_year < settle_year || (ord_year == settle_year && ord_month < settle_month)) {
									ord_date = settle_month_str + "01";
								}
								map.put("PAY_DATE", data);
								map.put("ORD_DATE", ord_date);
								continue;
							}
							// 결제시각
							if (cellName.equals("E")) {
								//셀에있는값을 formatter 으로 불러오고 그것을data에 넣어줌
								String time = formatter.formatCellValue(cell).replaceAll("[^0-9]", "").trim();

								if (CommonFunc.valueCheck("time", time, true)) {
									map.put("DOUBT", "Y");
								}
								map.put("PAY_TIME", time);
								continue;
							}
							// 정산기준일자
							if (cellName.equals("F")) {
								//셀에있는값을 formatter 으로 불러오고 그것을data에 넣어줌
								String data = formatter.formatCellValue(cell).replaceAll("[^0-9]", "").trim();
								
								if (CommonFunc.valueCheck("date", data, true)) {
									map.put("DOUBT", "Y");
								}
								map.put("SETTLE_STD_DATE", data);
								continue;
							}
							// 정산유형
							if (cellName.equals("G")) {
								//map.put("BAEMIN_SETTLE_TYPE", ExcelCellRef.getValue(cell).trim());
								ExcelCellRef.getValue(cell).trim();
								continue;
							}
							// 정산주기
							if (cellName.equals("H")) {
								//map.put("BAEMIN_SETTLE_CYCLE", ExcelCellRef.getValue(cell).trim());
								ExcelCellRef.getValue(cell).trim();
								continue;
							}
							// 매출규모
							if (cellName.equals("I")) {
								//map.put("SALE_SCALE", ExcelCellRef.getValue(cell).trim());
								ExcelCellRef.getValue(cell).trim();
								continue;
							}
							// 주문서번호
							if (cellName.equals("J")) {
								String data = ExcelCellRef.getValue(cell).replaceAll("[^a-zA-Z0-9]", "").trim();
								//대소문자 a~z,A~Z,0~9 를 제외한 value값을 ""으로 변환하고 trim으로 공백을지움
								String ord_id = StringUtils.leftPad(String.valueOf(data), 20, " ");
								//셀에서 받아온값이 20자리가 안되면 왼쪽부터 공백으로채움
								if (CommonFunc.valueCheck("ord_id", ord_id, true)) {
									//ord_id(!=20)이면 true ch_ord_dt_no 체크
									map.put("DOUBT", "Y");
								}
								//데이터 입력
								//에러가 나도  에러 엑셀에 입력 데이터를 표기
								map.put("CH_ORD_NO", data);       //배민주문서번호
								map.put("ORD_ID", ord_id);        //주문번호
								continue;
							}
							// 주문서 상태
							if (cellName.equals("K")) {
								map.put("ORD_STATE", ExcelCellRef.getValue(cell).trim());
								continue;
							}
							// 상품명
							if (cellName.equals("L")) {
								//map.put("ITEM_NM", ExcelCellRef.getValue(cell).trim());
								ExcelCellRef.getValue(cell).trim();
								continue;
							}
							// 옵션명
							if (cellName.equals("M")) {
								//map.put("ITEM_OPTION_NM", ExcelCellRef.getValue(cell).trim());
								ExcelCellRef.getValue(cell).trim();
								continue;
							}
							// 상품옵션주문번호
							if (cellName.equals("N")) {
								//map.put("ITEM_ORD_NO", ExcelCellRef.getValue(cell).trim());
								ExcelCellRef.getValue(cell).trim();
								continue;
							}
							// 배송방식
							if (cellName.equals("O")) {
								//map.put("DELIVERY_TYPE", ExcelCellRef.getValue(cell).trim());
								ExcelCellRef.getValue(cell).trim();
								continue;
							}
							// 배달주체
							if (cellName.equals("P")) {
							//	map.put("DELIVERY_SUBJECT", ExcelCellRef.getValue(cell).trim());
								ExcelCellRef.getValue(cell).trim();
								continue;
							}
							// (a)상품금액
							if (cellName.equals("Q")) {
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
										.replaceAll("[^0-9\\-]", "").trim();
								map.put("SALE_ITEM_AMT", data);
								continue;
							}
							// (b)배달팁
							if (cellName.equals("R")) {
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
										.replaceAll("[^0-9\\-]", "").trim();
								map.put("SALE_DV_AMT", data);
								continue;
							}
							// (c)봉투값
							if (cellName.equals("S")) {
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
										.replaceAll("[^0-9\\-]", "").trim();
								map.put("SALE_ENVELOPE_AMT", data);
								continue;
							}
							// (d)부분환불금액
							if (cellName.equals("T")) {
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
										.replaceAll("[^0-9\\-]", "").trim();
								map.put("SALE_PARTIAL_REFUND", data);
								continue;
							}
							// (e)셀러부담쿠폰금액
							if (cellName.equals("U")) {
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
										.replaceAll("[^0-9\\-]", "").trim();
								map.put("SALE_SELLER_CPN_AMT", data);
								continue;
							}
							// (f)합계(정산기준금액)
							if (cellName.equals("V")) {
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
										.replaceAll("[^0-9\\-]", "").trim();
								map.put("SALE_SUM", data);
								continue;
							}
							// (B)결제정산수수료 부과대상 금액(B=f)
							if (cellName.equals("W")) {
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
										.replaceAll("[^0-9\\-]", "").trim();
								map.put("SETTLE_PAY_CHARGE_TARGET_AMT", data);
								continue;
							}
							// (C)중개이용료 부과대상금액(C=a+d+e)
							if (cellName.equals("X")) {
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
										.replaceAll("[^0-9\\-]", "").trim();
								map.put("RELAY_FEE_CHARGE_TARGET_AMT", data);
								continue;
							}
							// 기준수수료
							if (cellName.equals("Y")) {
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
										.replaceAll("[^0-9\\-]", "").trim();
								//셀에서 받아온값이 100이면 -100 처리 or -100이면 100되게끔 처리하기위한  *-1 처리
								negative = Integer.parseInt(data)*-1;
								map.put("COMM_SD_SUPPLY_AMT", negative );
								continue;
							}
							// 우대수수료
							if (cellName.equals("Z")) {
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
										.replaceAll("[^0-9\\-]", "").trim();
								negative = Integer.parseInt(data)*-1;
								map.put("COMM_SD_PREFERENTIAL_AMT", negative);
								continue;
							}
							// 결제정산수수료(부가세)
							if (cellName.equals("AA")) {
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
										.replaceAll("[^0-9\\-]", "").trim();
								negative = Integer.parseInt(data)*-1;
								map.put("COMM_PAY_SETTLE_VAT", negative);
								continue;
							}
							// 중개이용수수료(공급가액)
							if (cellName.equals("AB")) {
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
										.replaceAll("[^0-9\\-]", "").trim();
								negative = Integer.parseInt(data)*-1;
								map.put("COMM_RELAY_FEE_SUPPLY_AMT", negative);
								continue;
							}
							// 중개이용수수료(부가세)
							if (cellName.equals("AC")) {
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
										.replaceAll("[^0-9\\-]", "").trim();
								negative = Integer.parseInt(data)*-1;
								map.put("COMM_RELAY_FEE_VAT", negative);
								continue;
							}
							// 배민라이더스 이용료(공급가액)
							if (cellName.equals("AD")) {
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
										.replaceAll("[^0-9\\-]", "").trim();
								negative = Integer.parseInt(data)*-1;
								map.put("COMM_BM_RD_FEE_SUPPLY_AMT", negative);
								continue;
							}
							// 배민라이더스 이용료(부가세)
							if (cellName.equals("AE")) {
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
										.replaceAll("[^0-9\\-]", "").trim();
								negative = Integer.parseInt(data)*-1;
								map.put("COMM_BM_RD_FEE_VAT", negative);
								continue;
							}
							// (E)정산금액 (E=f+D)
							if (cellName.equals("AF")) {
								String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
										.replaceAll("[^0-9\\-]", "").trim();
								map.put("SETTLE_AMT", data);
								
								continue;
							}							
						}
						
						if (map.get("DOUBT") == null) {
							map.put("SET_BAEMIN_DV_SEQ",seqCount);// 배민시퀀스 번호 추가  위에 카운트 count++ 변수 하나 생성해서 사용하면됨
							resultS.add(map);
						} else {
							resultF.add(map);
	
						}
					}
					paramMap.put("S", resultS);
					paramMap.put("F", resultF);
				}
			}
		
			//초기화
			wb.close();
			wb = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return paramMap;
	}
}