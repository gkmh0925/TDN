package oms.kisvan.emart.model.service;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import oms.kisvan.emart.common.ExcelStyle;
import oms.kisvan.emart.model.dao.StoreCalculateDAO;
import oms.kisvan.emart.model.dto.Criteria;
import oms.kisvan.emart.model.dto.PagingDTO;
import oms.kisvan.emart.model.dto.StoreMonthDetailsInfoDTO;
import oms.kisvan.emart.model.dto.StoreOrdNoDetailsInfoDTO;

@Service
public class StoreCalculateService {

	private final StoreCalculateDAO storeCalculateDAO;
	
	public StoreCalculateService(StoreCalculateDAO storeCalculateDAO) {
		this.storeCalculateDAO =storeCalculateDAO;
	}
	
	
	//점별/주문번호별 상세 검색
	public ResponseEntity<Object> getStoreOrdNoDetailsInfo(HashMap<String, Object> paramMap) {
		
	    HashMap<String, Object> resMap = new HashMap<>();
	    resMap.put("storeOrdNoDetailsInfo", storeCalculateDAO.getStoreOrdNoDetailsInfo(paramMap));
	    resMap.put("pageMaker", new PagingDTO((Criteria)paramMap.get("cri"), storeCalculateDAO.getPosList_CNT(paramMap)));
	    
		return ResponseEntity.status(HttpStatus.OK).body(resMap);
	}

	// 정산내역관리 > 점별/주문번호별 상세 화면 > 엑셀 다운로드
	public ResponseEntity<Object> storeOrdNoDetailsExcel(HashMap<String, Object> paramMap, HttpServletResponse response) throws IOException {
		
		SXSSFWorkbook wb = new SXSSFWorkbook();
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);
		
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 27;
		
		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("점별_주문번호별 상세조회");
		
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);
		
		row.createCell(cellCount++).setCellValue("순번");
		row.createCell(cellCount++).setCellValue("점포코드");
		row.createCell(cellCount++).setCellValue("점포명");
		row.createCell(cellCount++).setCellValue("직가맹점유형");
		row.createCell(cellCount++).setCellValue("서비스구분");
		row.createCell(cellCount++).setCellValue("주문일자");
		row.createCell(cellCount++).setCellValue("배달일자");
		row.createCell(cellCount++).setCellValue("배달시간");
		row.createCell(cellCount++).setCellValue("주문번호");
		row.createCell(cellCount++).setCellValue("네이버 주문번호");
		row.createCell(cellCount++).setCellValue("주문플랫폼");
		row.createCell(cellCount++).setCellValue("배달업체");
		row.createCell(cellCount++).setCellValue("POS등록액");
		row.createCell(cellCount++).setCellValue("플랫폼매출액");
		row.createCell(cellCount++).setCellValue("추가입금액");
		row.createCell(cellCount++).setCellValue("고객부담금");
		row.createCell(cellCount++).setCellValue("배달료행사지원금");
		row.createCell(cellCount++).setCellValue("추가부담금");
		row.createCell(cellCount++).setCellValue("배달대행료1");
		row.createCell(cellCount++).setCellValue("배달지원금(수정전)");
		row.createCell(cellCount++).setCellValue("배달지원금(수정후)");
		row.createCell(cellCount++).setCellValue("플랫폼수수료1");
		row.createCell(cellCount++).setCellValue("플랫폼매출건수");
		row.createCell(cellCount++).setCellValue("배달실행건수");
		row.createCell(cellCount++).setCellValue("kis중계수수료(수정전)");
		row.createCell(cellCount++).setCellValue("kis중계수수료(수정후)");
		row.createCell(cellCount++).setCellValue("비고");
		row.createCell(cellCount++).setCellValue("반영여부");

		// 스타일 적용
		row.getCell(0).setCellStyle(numStyle);
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}
		
		List<StoreOrdNoDetailsInfoDTO> getStoreOrdNoDetailsInfoList = storeCalculateDAO.getStoreOrdNoDetailsInfo(paramMap);
		
		for (StoreOrdNoDetailsInfoDTO temp : getStoreOrdNoDetailsInfoList) {
			
			cellCount = 0;
			
			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);
			
			row.createCell(cellCount++).setCellValue(temp.getRN()); // 순번
			row.createCell(cellCount++).setCellValue(temp.getSTR_CD()); // 점포코드
			row.createCell(cellCount++).setCellValue(temp.getSTR_NM()); // 점포명
			row.createCell(cellCount++).setCellValue(temp.getSTO_TYPE_NM()); // 직가맹점유형
			row.createCell(cellCount++).setCellValue(temp.getPICKUP_NM()); // 서비스구분
			row.createCell(cellCount++).setCellValue(temp.getORD_DATE()); // 주문일자
			row.createCell(cellCount++).setCellValue(temp.getORD_DT()); // 배달일자
			row.createCell(cellCount++).setCellValue(temp.getDELIVERY_TIME()); // 배달시간
			row.createCell(cellCount++).setCellValue(temp.getCH_ORD_NO()); // 주문번호
			row.createCell(cellCount++).setCellValue(temp.getNPAY_ORD_NO()); // 네이버 주문번호
			row.createCell(cellCount++).setCellValue(temp.getORDER_NM()); // 주문플랫폼
			row.createCell(cellCount++).setCellValue(temp.getDELIVERY_NM()); // 배달업체
			row.createCell(cellCount++).setCellValue(temp.getORD_AMT_A()); // POS등록액
			row.createCell(cellCount++).setCellValue(temp.getORD_AMT_B()); // 플랫폼매출액
			row.createCell(cellCount++).setCellValue(temp.getADD_DEPOSIT()); // 추가입금액
			row.createCell(cellCount++).setCellValue(temp.getDELIVER_FEE()); // 고객부담금
			row.createCell(cellCount++).setCellValue(temp.getDELIVER_EVENT_FUND()); // 배달료행사지원금
			row.createCell(cellCount++).setCellValue(temp.getLEVY()); // 추가부담금
			row.createCell(cellCount++).setCellValue(temp.getDELIVERY_AMT()); // 배달대행료1
			row.createCell(cellCount++).setCellValue(temp.getDELIVERY_SUBSIDY_BEFOR()); // 배달지원금(수정전)
			row.createCell(cellCount++).setCellValue(temp.getDELIVERY_SUBSIDY_AFTER()); // 배달지원금(수정후)
			row.createCell(cellCount++).setCellValue(temp.getPLATFORM_FEE()); // 플랫폼수수료1
			row.createCell(cellCount++).setCellValue(temp.getPLATFORM_CNT()); // 플랫폼매출건수
			row.createCell(cellCount++).setCellValue(temp.getDELIVERY_CNT()); // 배달실행건수
			row.createCell(cellCount++).setCellValue(temp.getKIS_FEE_BEFOR()); // kis중계수수료(수정전)
			row.createCell(cellCount++).setCellValue(temp.getKIS_FEE_AFTER()); // kis중계수수료(수정후)
			row.createCell(cellCount++).setCellValue(temp.getBIGO()); // 비고
			row.createCell(cellCount++).setCellValue(temp.getIS_REFLECTION()); // 반영여부

			
		}
		
		Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다

		// 년월일시분초 14자리 포멧
		SimpleDateFormat simple_format = new SimpleDateFormat("yyyyMMdd_HHmmss");

		String getNowDatetime = simple_format.format(date_now);

		String fileName = "EMART24_OMS_점별/주문번호별 상세조회_" + getNowDatetime + ".xlsx";
		
		
		
		try (OutputStream os = new BufferedOutputStream(response.getOutputStream());){
			
			// 클라이언트 측 다운로드
			response.reset();

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
			
			wb.write(os);
			os.flush();

			wb.dispose();
			
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400");
		}
		
		
		return ResponseEntity.status(HttpStatus.OK).body("200");
	}


	// 점별 월 정산 조회 화면
	public ResponseEntity<Object> getStoreMonthDetailsInfo(HashMap<String, Object> paramMap) {
		HashMap<String, Object> resMap = new HashMap<>();
	    resMap.put("storeOrdNoDetailsInfo", storeCalculateDAO.getStoreMonthDetailsInfo(paramMap));
	    resMap.put("pageMaker", new PagingDTO((Criteria)paramMap.get("cri"), storeCalculateDAO.getStoreMonthDetailsCnt(paramMap)));
	    
		return ResponseEntity.status(HttpStatus.OK).body(resMap);
	}

	// 점별 월 정산 조회 화면 엑셀 다운로드
	public ResponseEntity<Object> storeMonthDetailsExcel(HashMap<String, Object> paramMap,
			HttpServletResponse response) {
		
		SXSSFWorkbook wb = new SXSSFWorkbook();
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);
		
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 20;
		
		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("점별 월 정산 조회 화면");
		
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);
		
		row.createCell(cellCount++).setCellValue("순번");
		row.createCell(cellCount++).setCellValue("점포코드");
		row.createCell(cellCount++).setCellValue("점포명");
		row.createCell(cellCount++).setCellValue("직가맹유형");
		row.createCell(cellCount++).setCellValue("플랫폼매출액");
		row.createCell(cellCount++).setCellValue("추가입금액");
		row.createCell(cellCount++).setCellValue("고객부담금");
		row.createCell(cellCount++).setCellValue("배달료행사지원금");
		row.createCell(cellCount++).setCellValue("추가부담금");
		row.createCell(cellCount++).setCellValue("배달대행료");
		row.createCell(cellCount++).setCellValue("배달지원금");
		row.createCell(cellCount++).setCellValue("플랫폼수수료1");
		row.createCell(cellCount++).setCellValue("플랫폼매출건수");
		row.createCell(cellCount++).setCellValue("배달실행건수");
		row.createCell(cellCount++).setCellValue("미매칭건수(주문/배달)");
		row.createCell(cellCount++).setCellValue("POS주문건수");
		row.createCell(cellCount++).setCellValue("미매칭건수(주문/배달/POS)");
		row.createCell(cellCount++).setCellValue("KIS중계수수료");
		row.createCell(cellCount++).setCellValue("수정여부");
		row.createCell(cellCount++).setCellValue("확정여부");
		row.createCell(cellCount++).setCellValue("정산반영여부");
		
		// 스타일 적용
		row.getCell(0).setCellStyle(numStyle);
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}
		
		List<StoreMonthDetailsInfoDTO> getStoreMonthDetailsInfoList = storeCalculateDAO.getStoreMonthDetailsInfo(paramMap);
		
		for (StoreMonthDetailsInfoDTO temp : getStoreMonthDetailsInfoList) {
			
			cellCount = 0;
			
			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);
			
			row.createCell(cellCount++).setCellValue(temp.getRN()); // 점포코드
			row.createCell(cellCount++).setCellValue(temp.getSTR_CD()); // 점포코드
			row.createCell(cellCount++).setCellValue(temp.getSTR_NM()); // 점포명
			row.createCell(cellCount++).setCellValue(temp.getSTO_TYPE_NM()); // 직가맹유형
			row.createCell(cellCount++).setCellValue(temp.getORDER_ORD_AMT()); // 플랫폼매출액
			row.createCell(cellCount++).setCellValue(temp.getADD_DEPOSIT()); // 추가입금액
			row.createCell(cellCount++).setCellValue(temp.getDELIVER_FEE()); // 고객부담금
			row.createCell(cellCount++).setCellValue(temp.getDELIVER_EVENT_FUND()); // 배달료행사지원금
			row.createCell(cellCount++).setCellValue(temp.getLEVY()); // 추가부담금
			row.createCell(cellCount++).setCellValue(temp.getDELIVERY_AMT()); // 배달대행료
			row.createCell(cellCount++).setCellValue(temp.getDELIVERY_SUBSIDY()); // 배달지원금
			row.createCell(cellCount++).setCellValue(temp.getPLATFORM_FEE()); // 플랫폼수수료1
			row.createCell(cellCount++).setCellValue(temp.getPLATFORM_CNT()); // 플랫폼매출건수
			row.createCell(cellCount++).setCellValue(temp.getDELIVERY_CNT()); // 배달실행건수
			row.createCell(cellCount++).setCellValue(temp.getNON_MATCH_ORD_DV()); // 미매칭건수(주문/배달)
			row.createCell(cellCount++).setCellValue(temp.getPOS_CNT()); // POS주문건수
			row.createCell(cellCount++).setCellValue(temp.getNON_MATCH_ORD_DV_POS()); // 미매칭건수(주문/배달/POS)
			row.createCell(cellCount++).setCellValue(temp.getKIS_FEE()); // KIS중계수수료
			row.createCell(cellCount++).setCellValue(temp.getIS_MODIFY()); // 수정여부
			row.createCell(cellCount++).setCellValue(temp.getIS_COMPLET()); // "확정여부
			row.createCell(cellCount++).setCellValue(temp.getIS_SETTLE()); // 정산반영여부
			
		}
		
		Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다

		// 년월일시분초 14자리 포멧
		SimpleDateFormat simple_format = new SimpleDateFormat("yyyyMMdd_HHmmss");

		String getNowDatetime = simple_format.format(date_now);

		String fileName = "EMART24_OMS_점별 월 정산 조회화면_" + getNowDatetime + ".xlsx";
		
		
		
		try (OutputStream os = new BufferedOutputStream(response.getOutputStream());){
			
			// 클라이언트 측 다운로드
			response.reset();

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
			
			wb.write(os);
			os.flush();

			wb.dispose();
			
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body("200");
	}

	
}
