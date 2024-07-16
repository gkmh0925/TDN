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

public class ExcelReadCalc_KIS {
	public static HashMap<String, Object> read(ExcelReadOption excelReadOption, Map<String, Object> requestParamMap) {
		// 엑셀 파일 자체
		// 엑셀파일을 읽어 들인다.
		// FileType.getWorkbook() <-- 파일의 확장자에 따라서 적절하게 가져온다.
		Workbook wb = ExcelFileType.getWorkbook(excelReadOption.getFilePath());

		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		DataFormatter formatter = new DataFormatter();

		int sheetCnt = wb.getNumberOfSheets(); // 시트의 수를 가져오기 위한 변수
		int numOfCells = 0;

		Row row = null;
		Cell cell = null;

		String cellName = "";
		Integer seqCount =0;

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

						// 구분
						if (cellName.equals("A")) {
							String data = ExcelCellRef.getValue(cell).trim();

							if (StringUtils.isBlank(data)) {
								map.put("DOUBT", "Y");
							} else {
								switch (data) {
								case "310":
									map.put("CH_CD", "04");
									break;
								case "410":
									map.put("CH_CD", "04");
									break;
								case "320":
									map.put("CH_CD", "06");
									break;
								default:
									map.put("DOUBT", "Y");
								}
							}

							map.put("GUBUN", data);

							continue;
						}
						// 채널사명
						if (cellName.equals("B")) {
							map.put("CH_NM", ExcelCellRef.getValue(cell).trim());

							continue;
						}

						// 주문일자, 주문일시
						if (cellName.equals("C")) {
							String data = formatter.formatCellValue(cell).replaceAll("[^0-9]", "").trim();

							if (CommonFunc.valueCheck("datetime", data, true)) {
								map.put("DOUBT", "Y");
							}

							if (data.length() < 14) {
								data = StringUtils.rightPad(data, 14, "0");
							}

							String ord_date = data.substring(0, 8);
							map.put("ORD_DATE", ord_date);
							map.put("ORD_TIME", data);

							continue;

						}

						// 지급예정일
						if (cellName.equals("D")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();

							if (CommonFunc.valueCheck("date", data, false)) {
								map.put("DOUBT", "Y");
							}

							map.put("PAY_EXPECTED_DATE", data);

							continue;

						}

						// 사업자번호
						if (cellName.equals("E")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();

							if (CommonFunc.valueCheck("biz_no", data, true)) {
								map.put("DOUBT", "Y");
							}

							map.put("BIZ_NO", data);

							continue;

						}

						// 점포명
						if (cellName.equals("F")) {
							map.put("STORE_NM", ExcelCellRef.getValue(cell).trim());

						}

						// 점포코드
						if (cellName.equals("G")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();

							if (CommonFunc.valueCheck("sto_id", data, true)) {
								map.put("DOUBT", "Y");
							}

							map.put("STORE_ID", data);

							continue;

						}

						// 주문번호
						if (cellName.equals("H")) {
							String data = ExcelCellRef.getValue(cell).replaceAll("[^0-9]", "").trim();
							String dtord_no = StringUtils.leftPad(String.valueOf(Long.parseLong(data)), 20, " ");

							if (CommonFunc.valueCheck("ord_id", dtord_no, true)) {
								map.put("DOUBT", "Y");
							}

							map.put("DTORD_NO", dtord_no);
							map.put("CH_ORD_NO", data);

							continue;

						}

						// 상품가합계
						if (cellName.equals("I")) {
							String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim();

							map.put("MENU_TOT_AMT", data);

							continue;

						}

						// 배달료
						if (cellName.equals("J")) {
							String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim();

							map.put("DELIVER_FEE", data);

							continue;

						}

						// 결제금액
						if (cellName.equals("K")) {
							String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim();

							map.put("ACT_AMT", data);

							continue;
						}

						// 수수료
						if (cellName.equals("L")) {
							String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim();

							map.put("CMS_AMT", data);

							continue;

						}

						// VAT
						if (cellName.equals("M")) {
							String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim();

							map.put("VAT_AMT", data);

							continue;

						}

						// 지급액
						if (cellName.equals("N")) {
							String data = CommonFunc.numberNullCheck(ExcelCellRef.getValue(cell))
									.replaceAll("[^0-9\\-]", "").trim();

							map.put("PAY_AMT", data);

							continue;

						}

						// 비고
						if (cellName.equals("O")) {
							map.put("BIGO", ExcelCellRef.getValue(cell).trim());

							continue;

						}

						// map.put(cellName, ExcelCellRef.getValue(cell));
						/*
						 * 만들어진 Map객체를 List로 넣는다.
						 */
					}

					if (map.get("DOUBT") == null) {
						map.put("SET_KISAPP_SEQ",seqCount);
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