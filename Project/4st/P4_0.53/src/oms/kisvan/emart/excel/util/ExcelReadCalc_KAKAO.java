package oms.kisvan.emart.excel.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import oms.kisvan.emart.common.CommonFunc;

public class ExcelReadCalc_KAKAO {
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
			// 에러 엑샐일 경우 2행부터 start
			if (i != 0 && !wb.getSheetName(i).equals("카카오 정산리스트")) {//0번째시트만 데이터를 담음
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
						//Object MaxCount = requestParamMap.get("startIndex");
						//엑셀1개의 로우 끝날때마다시퀀스 번호에 값을 넣어줄 변수 로우값+시퀀스 max = 엑셀업로드시마다 들어갈값
						//seqCount = rowIndex + (Integer)MaxCount;
						map.put("rowIndex", rowIndex);
						//브랜드명
						if (cellName.equals("A")) {
							map.put("BRAND_NM", ExcelCellRef.getValue(cell).trim());
							continue;
						}
						//매장코드
						if (cellName.equals("B")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();

							if (CommonFunc.valueCheck("sto_id", data, true)) {
								map.put("DOUBT", "Y");
							}
							map.put("STORE_ID", data);
							continue;

						}
						//매장명
						if (cellName.equals("C")) {
							map.put("STORE_NM", ExcelCellRef.getValue(cell).trim());
							continue;

						}
						//주문일시
						if (cellName.equals("D")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();
							
							if (CommonFunc.valueCheck("date", data, true)) {
								map.put("DOUBT", "Y");
							}
							//정산월보다 주문일시가 이전달일 경우 정산월에01일 으로 하기위함
							String ord_date=data;
							String settle_month_str = (String) requestParamMap.get("SETTLE_MONTH");
							int ord_year = Integer.parseInt(data.substring(0, 4));
							int ord_month = Integer.parseInt(data.substring(4, 6));
							int settle_year = Integer.parseInt(settle_month_str.substring(0, 4));
							int settle_month = Integer.parseInt(settle_month_str.substring(4));
							if (ord_year < settle_year || (ord_year == settle_year && ord_month < settle_month)) {
								ord_date = settle_month_str + "01";
							}
							map.put("ORD_TIME", data);
							map.put("ORD_DATE", ord_date);
							map.put("CH_CD", "02");
							continue;
						}
						//조정일자
						if (cellName.equals("E")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();
							if (CommonFunc.valueCheck("date", data, false)) {
								map.put("DOUBT", "Y");
							}
							map.put("ADJUSTMENT_DATE", data);
							continue;
						}
						//CMS주문번호
						if (cellName.equals("F")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();
							String ord_id = StringUtils.leftPad(String.valueOf(Long.parseLong(data)), 20, " ");
							if (CommonFunc.valueCheck("ord_id", ord_id, true)) {
								map.put("DOUBT", "Y");
							}
							map.put("CMD_ORD_NO", data);
							map.put("ORD_ID", ord_id);//pos데이터와 중개 데이터 모두 cms 번호를 사용하고 연관이있어서 ord_id값에 cms 주문번호를 넣고 사용

						}
						//카카오주문번호
						if (cellName.equals("G")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();
							map.put("CH_ORD_NO", data);

						}
						//주문금액
						if (cellName.equals("H")) {
							map.put("ORD_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//배달료
						if (cellName.equals("I")) {
							map.put("DELIVER_FEE", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//할인금액
						if (cellName.equals("J")) {
							map.put("DIS_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//결제금액
						if (cellName.equals("K")) {
							map.put("ACT_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//취소금액
						if (cellName.equals("L")) {
							map.put("CANCEL_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//결제수단
						if (cellName.equals("M")) {
							map.put("PAY_TYPE", ExcelCellRef.getValue(cell).trim());
							continue;
						}
						//선결제카드
						if (cellName.equals("N")) {
							map.put("PREPAID_CARD", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//선결제카카오페이
						if (cellName.equals("O")) {
							map.put("PREPAID_KAKAO_PAY", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//선결제카카오머니
						if (cellName.equals("P")) {
							map.put("PREPAID_KAKAO_MONEY", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//후결제
						if (cellName.equals("Q")) {
							map.put("AFTER_PAY", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//브랜드부담금
						if (cellName.equals("R")) {
							map.put("BRAND_LEVY_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//카카오부담금
						if (cellName.equals("S")) {
							map.put("KAKAO_LEVY_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//가맹점부담금
						if (cellName.equals("T")) {
							map.put("FRANCHISEE_LEVY_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//기타 부담금
						if (cellName.equals("U")) {
							map.put("ETC_LEVY_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//CNT 주문중개 수수료
						if (cellName.equals("V")) {
							map.put("CNT_ORD_RELAY_CMS", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//카카오 주문중개 수수료
						if (cellName.equals("W")) {
							map.put("KAKAO_ORD_RELAY_CMS", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//주문중개 수수료 합
						if (cellName.equals("X")) {
							map.put("ORD_RELAY_CMS_SUM", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//CNT 할인중개 수수료
						if (cellName.equals("Y")) {
							map.put("CNT_DIS_RELAY_CMS", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//카카오 할인중개 수수료
						if (cellName.equals("Z")) {
							map.put("KAKAO_DIS_RELAY_CMS", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//할인중개 수수료 합
						if (cellName.equals("AA")) {
							map.put("DIS_RELAY_CMS_SUM", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//이니시스 수수료
						if (cellName.equals("AB")) {
							map.put("INICIS_CMS", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//카카오페이 수수료
						if (cellName.equals("AC")) {
							map.put("KAKAO_PAY_CMS", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//PG 수수료
						if (cellName.equals("AD")) {
							map.put("PG_CMS", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//이니시스백마진
						if (cellName.equals("AE")) {
							map.put("INICIS_BACK_CMS", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//E쿠폰수수료
						if (cellName.equals("AF")) {
							map.put("E_CUPON_CMS", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//수수료총액
						if (cellName.equals("AG")) {
							map.put("TOT_CMS_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
						//일회용컵 보증금 금액
						  if (cellName.equals("AH")) {ExcelCellRef.getValue(cell).trim(); }//일회용컵수수료 
						 //입금액
						if (cellName.equals("AI")) {
							map.put("DEPOSIT_AMT", CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim());
							continue;
						}
					}

					if (map.get("DOUBT") == null) {
						//map.put("SET_KAKAKO_DV_SEQ",seqCount);
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