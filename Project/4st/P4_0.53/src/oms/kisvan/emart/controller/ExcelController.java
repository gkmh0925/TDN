package oms.kisvan.emart.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import lombok.Setter;
import oms.kisvan.emart.common.CommonFunc;
import oms.kisvan.emart.common.ExcelStyle;
import oms.kisvan.emart.model.dto.COMPARE_RELAY;
import oms.kisvan.emart.model.dto.EM_COMPANY_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_DELIVERY_TOTAL_OMS;
import oms.kisvan.emart.model.dto.EM_MENU_MASTER_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_BAEMIN_DV_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_BAROGO_DV_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_CHAINLOGIS_DV_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_DEALVER_DV_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_EMART24_DV_PU_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_KAKAO_DV_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_KISAPP_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_LOGIALL_DV_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_NAVER_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_SPIDOR_DV_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_VROONG_DV_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_YOGIYO_DV_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_YOGIYO_PU_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_USER_ACCOUNT_OMS;
import oms.kisvan.emart.model.dto.EM_RELAY_TOTAL_OMS;

import oms.kisvan.emart.model.service.ExcelService;
import oms.kisvan.emart.model.service.PosService;

@Controller
//엑셀 컨트롤러
public class ExcelController {
	
	@Setter(onMethod_ = {@Autowired})
	private ExcelService service;
	
	@Setter(onMethod_ = {@Autowired})
	private PosService posService;
	
	
	@PostMapping("/Excel_PosCompare_Download")
	//정산내역관리 > POS 데이터 대사 조회 > 엑셀 다운로드 
	public void Excel_PosCompare_Download(@RequestParam HashMap<String, Object> paramMap, ModelAndView mav, HttpServletRequest request, HttpServletResponse response)
				throws IOException{	
		
		String ch_view = (String) paramMap.get("comp_data");

		paramMap.get("comp_data"); // 대사 대상
		paramMap.get("sdate"); // 시작일
		paramMap.get("edate"); // 종료일

		if(ch_view.equals("relay")) {	//중계
			
			// 워크북 생성
			@SuppressWarnings("resource")
			SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

			XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

			// body CellStyle 작성
			XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

			// 숫자 포멧
			XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

			// 엑셀 파일 작성
			SXSSFRow row = null; // 행
			int rowCount = 0;
			int cellCount = 0;
			int columnCnt = 17;

			// SXSSFSheet 생성
			SXSSFSheet sheet = wb.createSheet("대사조회");

			//POS 데이터
			sheet.setColumnWidth(0, 15 * 256);// 주문일자 
			sheet.setColumnWidth(1, 15 * 256);// 채널사코드
			sheet.setColumnWidth(2, 40 * 256);// 이마트주문번호
			sheet.setColumnWidth(3, 15 * 256);// 상세주문번호
			sheet.setColumnWidth(4, 15 * 256);// 점포코드
			sheet.setColumnWidth(5, 15 * 256);// 매출발생점포코드
			sheet.setColumnWidth(6, 15 * 256);// 주문금액(배달비제외)
			sheet.setColumnWidth(8, 15 * 256);// 주문상태
			sheet.setColumnWidth(9, 15 * 256);// 주문상태구분
			//중계데이터
			sheet.setColumnWidth(0, 15 * 256);// 주문일자 
			sheet.setColumnWidth(1, 15 * 256);// 채널사코드
			sheet.setColumnWidth(2, 40 * 256);// 이마트주문번호
			sheet.setColumnWidth(3, 15 * 256);// 상세주문번호
			sheet.setColumnWidth(4, 15 * 256);// 점포코드
			sheet.setColumnWidth(5, 15 * 256);// 매출발생점포코드
			sheet.setColumnWidth(6, 15 * 256);// 주문금액(배달비제외)
			sheet.setColumnWidth(8, 15 * 256);// 주문상태
			sheet.setColumnWidth(9, 15 * 256);// 주문상태구분
			
			// 첫 행 병합 및 타이틀 설정
		    row = sheet.createRow(rowCount++);
		    
		    for (int i = 0; i <= columnCnt; i++) {
		        row.createCell(i);
		    }
		    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
		    row.getCell(0).setCellValue("POS데이터");
		    row.getCell(0).setCellStyle(headStyle);

		    sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 17)); 
		    row.getCell(9).setCellValue("중계데이터");
		    row.getCell(9).setCellStyle(headStyle);		

			// 엑셀 내용 작성
			// 제목 Cell 생성
			row = sheet.createRow(rowCount++);
			row.setHeight((short) 420);

			// 스타일 적용
			row.createCell(cellCount++).setCellValue("주문일자");
			row.createCell(cellCount++).setCellValue("채널사코드");
			row.createCell(cellCount++).setCellValue("이마트주문번호");
			row.createCell(cellCount++).setCellValue("상세주문번호");
			row.createCell(cellCount++).setCellValue("점포코드");
			row.createCell(cellCount++).setCellValue("매출발생점포코드");
			row.createCell(cellCount++).setCellValue("주문금액(배달비제외)");
			row.createCell(cellCount++).setCellValue("주문상태");
			row.createCell(cellCount++).setCellValue("주문상태구분");
			//중계데이터
			row.createCell(cellCount++).setCellValue("주문일자");
			row.createCell(cellCount++).setCellValue("채널사코드");
			row.createCell(cellCount++).setCellValue("이마트주문번호");
			row.createCell(cellCount++).setCellValue("상세주문번호");
			row.createCell(cellCount++).setCellValue("점포코드");
			row.createCell(cellCount++).setCellValue("매출발생점포코드");
			row.createCell(cellCount++).setCellValue("주문금액(배달비제외)");
			row.createCell(cellCount++).setCellValue("주문상태");
			row.createCell(cellCount++).setCellValue("주문상태구분");
			
			// 스타일 적용
			row.getCell(0).setCellStyle(numStyle);
			for (int e = 0; e <= columnCnt; e++) {
				row.getCell(e).setCellStyle(headStyle);
			}
			
			// 주문 내역 검색 메소드
			List<COMPARE_RELAY> calcList = (List<COMPARE_RELAY>) posService.getCompareData(paramMap);			

			for (COMPARE_RELAY temp : calcList) {
		
				cellCount = 0;

				row = sheet.createRow(rowCount++);

				row.setHeight((short) 350);
				
				row.createCell(cellCount++).setCellValue(temp.getORD_DT_P());// 주문일자
				row.createCell(cellCount++).setCellValue(temp.getCHNEL_CD_P()); // 채널사코드
				row.createCell(cellCount++).setCellValue(temp.getEM_ORD_NO_P()); //이마트주문번호
				row.createCell(cellCount++).setCellValue(temp.getDTORD_NO_P());//상세주문번호
				row.createCell(cellCount++).setCellValue(temp.getSTR_CD_P());//점포코드
				row.createCell(cellCount++).setCellValue(temp.getSALE_STORE_P());//매출발생점포코드
				if(temp.getORD_AMT_P() == null) {
					temp.setORD_AMT_P(0);
				}
				row.createCell(cellCount++).setCellValue(temp.getORD_AMT_P()); //주문금액(배달비제외)
				row.createCell(cellCount++).setCellValue(temp.getORD_STAT_P());//주문상태
				row.createCell(cellCount++).setCellValue(temp.getSTATE_RESULT_P());//주문상태구분
				
				row.createCell(cellCount++).setCellValue(temp.getORD_TIME_R());
				row.createCell(cellCount++).setCellValue(temp.getFRANCHISE_CODE_R());
				row.createCell(cellCount++).setCellValue(temp.getORD_ID_R());
				row.createCell(cellCount++).setCellValue(temp.getDTORD_NO_R());
				row.createCell(cellCount++).setCellValue(temp.getSTORE_ID_R());
				row.createCell(cellCount++).setCellValue(temp.getSALE_STORE_R());
				if(temp.getACT_AMT_R() == null) {
					temp.setACT_AMT_R(0);
				}
				row.createCell(cellCount++).setCellValue(temp.getACT_AMT_R());
				row.createCell(cellCount++).setCellValue(temp.getORD_STATE_R());			
				row.createCell(cellCount++).setCellValue(temp.getSTATE_RESULT_R());

			}
			
			// 컨텐츠 타입과 파일명 지정

			Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다

			// 년월일시분초 14자리 포멧
			SimpleDateFormat simple_format = new SimpleDateFormat("yyyyMMdd_HHmmss");

			String getNowDatetime = simple_format.format(date_now);

			String fileName = "EMART24_OMS_POS데이터대사조회_" + getNowDatetime + ".xlsx";

			OutputStream os = new BufferedOutputStream(response.getOutputStream());

			// 클라이언트 측 다운로드
			response.reset();

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

			try {
				wb.write(os);
				os.flush();

				// 종료
				// fileOut.close();
				wb.dispose();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (os != null) {
					os.close();
				}
			}
		}else if(ch_view.equals("order")) {	//주문채널
			
			// 워크북 생성
			@SuppressWarnings("resource")
			SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

			XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

			// body CellStyle 작성
			XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

			// 숫자 포멧
			XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

			// 엑셀 파일 작성
			SXSSFRow row = null; // 행
			int rowCount = 0;
			int cellCount = 0;
			int columnCnt = 17;

			// SXSSFSheet 생성
			SXSSFSheet sheet = wb.createSheet("대사조회");

			
			//주문채널 데이터
			sheet.setColumnWidth(0, 15 * 256);// 주문일자 
			sheet.setColumnWidth(1, 15 * 256);// 채널사코드
			sheet.setColumnWidth(2, 40 * 256);// 이마트주문번호
			sheet.setColumnWidth(3, 15 * 256);// 상세주문번호
			sheet.setColumnWidth(4, 15 * 256);// 점포코드
			sheet.setColumnWidth(5, 15 * 256);// 매출발생점포코드
			sheet.setColumnWidth(6, 15 * 256);// 주문금액(배달비제외)
			sheet.setColumnWidth(8, 15 * 256);// 주문상태
			sheet.setColumnWidth(9, 15 * 256);// 주문상태구분
			//중계데이터
			sheet.setColumnWidth(0, 15 * 256);// 주문일자 
			sheet.setColumnWidth(1, 15 * 256);// 채널사코드
			sheet.setColumnWidth(2, 40 * 256);// 이마트주문번호
			sheet.setColumnWidth(3, 15 * 256);// 상세주문번호
			sheet.setColumnWidth(4, 15 * 256);// 점포코드
			sheet.setColumnWidth(5, 15 * 256);// 매출발생점포코드
			sheet.setColumnWidth(6, 15 * 256);// 주문금액(배달비제외)
			sheet.setColumnWidth(8, 15 * 256);// 주문상태
			sheet.setColumnWidth(9, 15 * 256);// 주문상태구분
			
			// 첫 행 병합 및 타이틀 설정
		    row = sheet.createRow(rowCount++);
		    
		    for (int i = 0; i <= columnCnt; i++) {
		        row.createCell(i);
		    }
		    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
		    row.getCell(0).setCellValue("중계데이터");
		    row.getCell(0).setCellStyle(headStyle);

		    sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 17)); 
		    row.getCell(9).setCellValue("채널사데이터");
		    row.getCell(9).setCellStyle(headStyle);		

			// 엑셀 내용 작성
			// 제목 Cell 생성
			row = sheet.createRow(rowCount++);
			row.setHeight((short) 420);

			// 스타일 적용
			row.createCell(cellCount++).setCellValue("주문일자");
			row.createCell(cellCount++).setCellValue("채널사코드");
			row.createCell(cellCount++).setCellValue("이마트주문번호");
			row.createCell(cellCount++).setCellValue("상세주문번호");
			row.createCell(cellCount++).setCellValue("점포코드");
			row.createCell(cellCount++).setCellValue("매출발생점포코드");
			row.createCell(cellCount++).setCellValue("주문금액(배달비제외)");
			row.createCell(cellCount++).setCellValue("주문상태");
			row.createCell(cellCount++).setCellValue("주문상태구분");
			//중계데이터
			row.createCell(cellCount++).setCellValue("주문일자");
			row.createCell(cellCount++).setCellValue("채널사코드");
			row.createCell(cellCount++).setCellValue("이마트주문번호");
			row.createCell(cellCount++).setCellValue("상세주문번호");
			row.createCell(cellCount++).setCellValue("점포코드");
			row.createCell(cellCount++).setCellValue("매출발생점포코드");
			row.createCell(cellCount++).setCellValue("주문금액(배달비제외)");
			row.createCell(cellCount++).setCellValue("주문상태");
			row.createCell(cellCount++).setCellValue("주문상태구분");
			
			// 스타일 적용
			row.getCell(0).setCellStyle(numStyle);
			for (int e = 0; e <= columnCnt; e++) {
				row.getCell(e).setCellStyle(headStyle);
			}
			
			List<COMPARE_RELAY> calcList = (List<COMPARE_RELAY>) posService.getCompareData(paramMap);	
			
			// 데이터 Cell 생성
			for (COMPARE_RELAY temp : calcList) {
		
				cellCount = 0;

				row = sheet.createRow(rowCount++);

				row.setHeight((short) 350);
				
				row.createCell(cellCount++).setCellValue(temp.getORD_TIME_R());
				row.createCell(cellCount++).setCellValue(temp.getFRANCHISE_CODE_R());
				row.createCell(cellCount++).setCellValue(temp.getORD_ID_R());
				row.createCell(cellCount++).setCellValue(temp.getDTORD_NO_R());
				row.createCell(cellCount++).setCellValue(temp.getSTORE_ID_R());
				row.createCell(cellCount++).setCellValue(temp.getSALE_STORE_R());
				if(temp.getACT_AMT_R() == null) {
					temp.setACT_AMT_R(0);
				}
				row.createCell(cellCount++).setCellValue(temp.getACT_AMT_R());
				row.createCell(cellCount++).setCellValue(temp.getORD_STATE_R());			
				row.createCell(cellCount++).setCellValue(temp.getSTATE_RESULT_R());
				
				//채널사 데이터
				row.createCell(cellCount++).setCellValue(temp.getORD_DT_C()); //ORD_DT_C
				row.createCell(cellCount++).setCellValue(temp.getCHNEL_CD_C()); //CHNEL_CD_C
				row.createCell(cellCount++).setCellValue(temp.getEM_ORD_NO_C()); //EM_ORD_NO_C
				row.createCell(cellCount++).setCellValue(temp.getDTORD_NO_C()); //DTORD_NO_C
				row.createCell(cellCount++).setCellValue(temp.getSTR_CD_C()); //STR_CD_C
				row.createCell(cellCount++).setCellValue(temp.getSALE_STORE_C()); //SALE_STORE_C
				if(temp.getORD_AMT_C() == null) {
					temp.setORD_AMT_C(0);
				}
				row.createCell(cellCount++).setCellValue(temp.getORD_AMT_C()); //ORD_AMT_C
				row.createCell(cellCount++).setCellValue(temp.getORD_STATE_C()); //ORD_STATE_C				
				row.createCell(cellCount++).setCellValue(temp.getSTATE_RESULT_C()); //STATE_RESULT_C

			}
			
			// 컨텐츠 타입과 파일명 지정

			Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다

			// 년월일시분초 14자리 포멧
			SimpleDateFormat simple_format = new SimpleDateFormat("yyyyMMdd_HHmmss");

			String getNowDatetime = simple_format.format(date_now);

			String fileName = "EMART24_OMS_POS데이터대사조회_" + getNowDatetime + ".xlsx";

			OutputStream os = new BufferedOutputStream(response.getOutputStream());

			// 클라이언트 측 다운로드
			response.reset();

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

			try {
				wb.write(os);
				os.flush();

				// 종료
				// fileOut.close();
				wb.dispose();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (os != null) {
					os.close();
				}
			}
			
		}else if(ch_view.equals("delivery")) {	//배달
			
			// 워크북 생성
			@SuppressWarnings("resource")
			SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

			XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

			// body CellStyle 작성
			XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

			// 숫자 포멧
			XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

			// 엑셀 파일 작성
			SXSSFRow row = null; // 행
			int rowCount = 0;
			int cellCount = 0;
			int columnCnt = 11;

			// SXSSFSheet 생성
			SXSSFSheet sheet = wb.createSheet("대사조회");
			
			//POS 데이터
			sheet.setColumnWidth(0, 15 * 256);// 주문일자
			sheet.setColumnWidth(1, 15 * 256);// 채널사코드
			sheet.setColumnWidth(2, 40 * 256);// 이마트주문번호
			sheet.setColumnWidth(3, 15 * 256);// 상세주문번호
			sheet.setColumnWidth(4, 15 * 256);// 점포코드
			sheet.setColumnWidth(5, 15 * 256);// 매출발생점포코드

			//중계데이터
			sheet.setColumnWidth(0, 15 * 256);// 주문일자
			sheet.setColumnWidth(1, 15 * 256);// 채널사코드
			sheet.setColumnWidth(2, 40 * 256);// 이마트주문번호
			sheet.setColumnWidth(3, 15 * 256);// 상세주문번호
			sheet.setColumnWidth(4, 15 * 256);// 점포코드
			sheet.setColumnWidth(5, 15 * 256);// 매출발생점포코드
			
			// 첫 행 병합 및 타이틀 설정
		    row = sheet.createRow(rowCount++);
		    
		    for (int i = 0; i <= columnCnt; i++) {
		        row.createCell(i);
		    }
		    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
		    row.getCell(0).setCellValue("중계데이터");
		    row.getCell(0).setCellStyle(headStyle);

		    sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 11)); 
		    row.getCell(6).setCellValue("배달대행사 데이터");
		    row.getCell(6).setCellStyle(headStyle);		

			// 엑셀 내용 작성
			// 제목 Cell 생성
			row = sheet.createRow(rowCount++);
			row.setHeight((short) 420);

			// 스타일 적용
			row.createCell(cellCount++).setCellValue("주문일자");
			row.createCell(cellCount++).setCellValue("채널사코드");
			row.createCell(cellCount++).setCellValue("이마트주문번호");
			row.createCell(cellCount++).setCellValue("주문금액(배달비제외)");
			row.createCell(cellCount++).setCellValue("주문총금액(배달비포함)");
			row.createCell(cellCount++).setCellValue("주문상태구분");
			//배달대행사
			row.createCell(cellCount++).setCellValue("주문일자");
			row.createCell(cellCount++).setCellValue("채널사코드");
			row.createCell(cellCount++).setCellValue("이마트주문번호");
			row.createCell(cellCount++).setCellValue("주문금액(배달비제외)");
			row.createCell(cellCount++).setCellValue("주문총금액(배달비포함)");
			row.createCell(cellCount++).setCellValue("주문상태구분");
			
			// 스타일 적용
			row.getCell(0).setCellStyle(numStyle);
			for (int e = 0; e <= columnCnt; e++) {
				row.getCell(e).setCellStyle(headStyle);
			}
			
			List<COMPARE_RELAY> calcList = (List<COMPARE_RELAY>) posService.getCompareData(paramMap);	
			
			// 데이터 Cell 생성
			for (COMPARE_RELAY temp : calcList) {
		
				cellCount = 0;

				row = sheet.createRow(rowCount++);

				row.setHeight((short) 350);
				
				row.createCell(cellCount++).setCellValue(temp.getORD_TIME_R());	//주문일자
				row.createCell(cellCount++).setCellValue(temp.getFRANCHISE_CODE_R());	//채널사코드
				row.createCell(cellCount++).setCellValue(temp.getORD_ID_R());	//이마트주문번호
				if(temp.getACT_AMT_R() == null) {
					temp.setACT_AMT_R(0);
				}
				row.createCell(cellCount++).setCellValue(temp.getACT_AMT_R());	////주문금액(배달비제외)
				if(temp.getTOT_AMT_R() == null) {
					temp.setTOT_AMT_R(0);
				}
				row.createCell(cellCount++).setCellValue(temp.getTOT_AMT_R());	//"주문총금액(배달비포함)
				row.createCell(cellCount++).setCellValue(temp.getSTATE_RESULT_R());	//주문상태구분
		
				
				row.createCell(cellCount++).setCellValue(temp.getORD_DT_D());	//주문일자
				row.createCell(cellCount++).setCellValue(temp.getCHNEL_CD_D());	//채널사코드
				row.createCell(cellCount++).setCellValue(temp.getEM_ORD_NO_D());	//이마트주문번호
				if(temp.getACT_AMT_D() == null) {
					temp.setACT_AMT_D(0);
				}
				row.createCell(cellCount++).setCellValue(temp.getACT_AMT_D());	////주문금액(배달비제외)
				if(temp.getTOT_AMT_D() == null) {
					temp.setTOT_AMT_D(0);
				}
				row.createCell(cellCount++).setCellValue(temp.getTOT_AMT_D());	//"주문총금액(배달비포함)
				row.createCell(cellCount++).setCellValue(temp.getSTATE_RESULT_D());	//주문상태구분

			}
			
			
			
			// 컨텐츠 타입과 파일명 지정

			Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다

			// 년월일시분초 14자리 포멧
			SimpleDateFormat simple_format = new SimpleDateFormat("yyyyMMdd_HHmmss");

			String getNowDatetime = simple_format.format(date_now);

			String fileName = "EMART24_OMS_POS데이터대사조회_" + getNowDatetime + ".xlsx";

			OutputStream os = new BufferedOutputStream(response.getOutputStream());

			// 클라이언트 측 다운로드
			response.reset();

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

			try {
				wb.write(os);
				os.flush();

				// 종료
				// fileOut.close();
				wb.dispose();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (os != null) {
					os.close();
				}
			}
		}

	}
	
	// 01_01 엑셀 페이지
	@PostMapping("/storeList_excel")
	public void storeList_excel(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response)
			throws Exception {

		response = service.getStoreExcel(paramMap, response);

	}

	// 02_01 엑셀 페이지
	@PostMapping("/productList_excel")
	public String productList_excel(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response)
			throws IOException, ParseException {
		paramMap.get("scd");
		paramMap.get("pcd");
		paramMap.get("etyp");
		paramMap.get("sinp");

		HashMap<String, Object> tempMap = paramMap;

		// 워크북 생성
		@SuppressWarnings("resource")
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

		// 숫자 포멧
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

		List<EM_MENU_MASTER_OMS> productList = service.getProductExcel(tempMap);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 6;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("메뉴리스트");

		// 총 240정도가 적당
		sheet.setColumnWidth(0, 5 * 256);// 순번
		sheet.setColumnWidth(1, 20 * 256);// 메뉴코드
		sheet.setColumnWidth(2, 40 * 256);// 메뉴명
		sheet.setColumnWidth(3, 15 * 256);// 단가(원)
		sheet.setColumnWidth(4, 15 * 256);// 메뉴상태
		// sheet.setColumnWidth(4, 30 * 256);// 상태수정일시
		sheet.setColumnWidth(5, 35 * 256);// 매장 내 메뉴 분류
		// sheet.setColumnWidth(6, 15 * 256);// 등록자명
		sheet.setColumnWidth(6, 25 * 256);// 등록일시
		// sheet.setColumnWidth(8, 15 * 256);// 수정자명
		// sheet.setColumnWidth(9, 25 * 256);// 수정일시

		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("순번");
		row.createCell(cellCount++).setCellValue("메뉴코드");
		row.createCell(cellCount++).setCellValue("메뉴명");
		row.createCell(cellCount++).setCellValue("단가(원)");
		row.createCell(cellCount++).setCellValue("메뉴상태");
		// row.createCell(cellCount++).setCellValue("상태수정일시");
		row.createCell(cellCount++).setCellValue("매장 내 메뉴 분류");
		// row.createCell(cellCount++).setCellValue("등록자명");
		row.createCell(cellCount++).setCellValue("등록일시");
		// row.createCell(cellCount++).setCellValue("수정자명");
		// row.createCell(cellCount++).setCellValue("수정일시");

		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (EM_MENU_MASTER_OMS tpm : productList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue(tpm.getRN()); // 데이터를 가져와 입력
			row.createCell(cellCount++).setCellValue(tpm.getMENU_CD()); // 데이터를 가져와 입력
			row.createCell(cellCount++).setCellValue(tpm.getMENU_NM());
			row.createCell(cellCount++).setCellValue(tpm.getPRICE());
			row.createCell(cellCount++).setCellValue(tpm.getMENU_STATE());
//			row.createCell(cellCount++).setCellValue(tpm.getMENU_STATE_MDF_DATE() == null ? ""
//					: String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", tpm.getMENU_STATE_MDF_DATE()));
			row.createCell(cellCount++).setCellValue(tpm.getCTGR_NM());
//			row.createCell(cellCount++).setCellValue(tpm.getREG_USER_NM());
			row.createCell(cellCount++)
					.setCellValue(String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", tpm.getREG_DATE()));
//			row.createCell(cellCount++).setCellValue(tpm.getMDF_USER_NM());
//			row.createCell(cellCount++).setCellValue(tpm.getMDF_DATE() == null ? ""
//					: String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", tpm.getMDF_DATE()));
			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (e == 3) {
					row.getCell(e).setCellStyle(numStyle);
					continue;
				}
				row.getCell(e).setCellStyle(bodyStyle);
			}
		}

		// 컨텐츠 타입과 파일명 지정

		Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다

		// 년월일시분초 14자리 포멧
		SimpleDateFormat simple_format = new SimpleDateFormat("yyyyMMdd_HHmmss");

		String getNowDatetime = simple_format.format(date_now);

		String fileName = "EMART24_OMS_메뉴리스트_" + getNowDatetime + ".xlsx";

		OutputStream os = new BufferedOutputStream(response.getOutputStream());

		// 클라이언트 측 다운로드
		response.reset();

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

		try {
			wb.write(os);
			os.flush();

			// 종료
			// fileOut.close();
			wb.dispose();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
		}

		return "oms_sub02_01";
	}

	// 03_01 엑셀 페이지
	@PostMapping("/orderList_excel")
	public void orderList_excel(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response)
			throws Exception {

		response = service.getOrderExcel(paramMap, response);
	}

	// 04_01 엑셀 페이지
	@PostMapping("/posList_excel")
	public void posList_excel(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response)
			throws Exception {

		response = service.getPosExcel(paramMap, response);
	}
	//05_02,05_03엑셀 페이지(채널사관리,배달대행사관리 페이지)
	@PostMapping("/channelList_excel")
	public void channelOrderList_excel(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response)
			throws Exception {

		response = service.getchannelExcel(paramMap, response);

	}
	
	@PostMapping(value = "/ExcelUpload_Cal_10")
	@ResponseBody
	public void ExcelUpload_RELAY(MultipartHttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> paramMap, HttpSession session) {
		try {
			EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
			paramMap.put("USER_CD", uSession.getUSER_CD());
			//화면에서 정산월 클릭한 데이터를 가져옴
			String month = request.getParameter("month").replaceAll("[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]", "");
			paramMap.put("SETTLE_MONTH", month.replaceAll("\\s", ""));
		
			//월별 시퀀스번호 max값
			paramMap.put("startIndex", service.ExcelUpload_RELAY_STARTINDEX(month.replaceAll("\\s", "")));
		} catch (Exception e) {
			response.setStatus(400);
			try {
				response.getWriter().write(3);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		//파일을 업로드한다.
		System.out.println("파일업로드 사용자 :" + paramMap.get("USER_CD"));
		response = service.ExcelUpload_RELAY(paramMap, request, response);

	}

	
	

	@PostMapping(value = "/ExcelUpload_ORD_0101")
	@ResponseBody
	public void ExcelUpload_YGYD(MultipartHttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> paramMap, HttpSession session) {
		try {
			EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
			paramMap.put("USER_CD", uSession.getUSER_CD());
			//화면에서 정산월 클릭한 데이터를 가져옴
			String month = request.getParameter("month").replaceAll("[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]", "");
			paramMap.put("SETTLE_MONTH", month.replaceAll("\\s", ""));
			//월별 시퀀스번호 max값
			paramMap.put("startIndex", service.ExcelUpload_YGYD_STARTINDEX(month.replaceAll("\\s", "")));
		} catch (Exception e) {
			response.setStatus(400);
			try {
				response.getWriter().write(3);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		response = service.ExcelUpload_YGYD(paramMap, request, response);

	}

	@PostMapping(value = "/ExcelUpload_ORD_0102")
	@ResponseBody
	public void ExcelUpload_YGYP(MultipartHttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> paramMap, HttpSession session) {
		try {
			EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
			paramMap.put("USER_CD", uSession.getUSER_CD());
			//화면에서 정산월 클릭한 데이터를 가져옴
			String month = request.getParameter("month").replaceAll("[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]", "");
			paramMap.put("SETTLE_MONTH", month.replaceAll("\\s", ""));
			//월별 시퀀스번호 max값
			paramMap.put("startIndex", service.ExcelUpload_YGYP_STARTINDEX(month.replaceAll("\\s", "")));
		} catch (Exception e) {
			response.setStatus(400);
			try {
				response.getWriter().write(3);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		response = service.ExcelUpload_YGYP(paramMap, request, response);

	}

	@PostMapping(value = "/ExcelUpload_ORD_02")
	@ResponseBody
	public void ExcelUpload_KAKAO(MultipartHttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> paramMap, HttpSession session) {
		try {
			EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
			paramMap.put("USER_CD", uSession.getUSER_CD());
			//화면에서 정산월 클릭한 데이터를 가져옴
			String month = request.getParameter("month").replaceAll("[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]", "");
			paramMap.put("SETTLE_MONTH", month.replaceAll("\\s", ""));
			//월별 시퀀스번호 max값
			paramMap.put("startIndex", service.ExcelUpload_KAKAO_STARTINDEX(month.replaceAll("\\s", "")));
		} catch (Exception e) {
			response.setStatus(400);
			try {
				response.getWriter().write(3);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		response = service.ExcelUpload_KAKAO(paramMap, request, response);
		
	}

	@PostMapping(value = "/ExcelUpload_ORD_03")
	@ResponseBody
	public void ExcelUpload_NAVER(MultipartHttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> paramMap, HttpSession session) {
		try {
			EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
			paramMap.put("USER_CD", uSession.getUSER_CD());
			//화면에서 정산월 클릭한 데이터를 가져옴
			String month = request.getParameter("month").replaceAll("[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]", "");
			paramMap.put("SETTLE_MONTH", month.replaceAll("\\s", ""));
			//월별 시퀀스번호 max값
			paramMap.put("startIndex", service.ExcelUpload_NAVER_STARTINDEX(month.replaceAll("\\s", "")));
		} catch (Exception e) {
			response.setStatus(400);
			try {
				response.getWriter().write(3);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		response = service.ExcelUpload_NAVER(paramMap, request, response);

	}

	@PostMapping(value = "/ExcelUpload_ORD_04")
	@ResponseBody
	public void ExcelUpload_KIS(MultipartHttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> paramMap, HttpSession session) {
		try {
			EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
			paramMap.put("USER_CD", uSession.getUSER_CD());
			//화면에서 정산월 클릭한 데이터를 가져옴
			String month = request.getParameter("month").replaceAll("[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]", "");
			paramMap.put("SETTLE_MONTH", month.replaceAll("\\s", ""));
			//월별 시퀀스번호 max값
			paramMap.put("startIndex", service.ExcelUpload_KIS_STARTINDEX(month.replaceAll("\\s", "")));
		} catch (Exception e) {
			response.setStatus(400);
			try {
				response.getWriter().write(3);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		response = service.ExcelUpload_KIS(paramMap, request, response);

	}
	
	@PostMapping(value = "/ExcelUpload_ORD_07")
	@ResponseBody
	public void ExcelUpload_BAEMIN(MultipartHttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> paramMap, HttpSession session) {
		try {
			EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
			paramMap.put("USER_CD", uSession.getUSER_CD());
			//화면에서 정산월 클릭한 데이터를 가져옴
			String month = request.getParameter("month").replaceAll("[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]", "");
			paramMap.put("SETTLE_MONTH", month.replaceAll("\\s", ""));
			//월별 시퀀스번호 max값
			paramMap.put("startIndex", service.ExcelUpload_BAEMIN_STARTINDEX(month.replaceAll("\\s", "")));
		} catch (Exception e) {
			response.setStatus(400);
			try {
				response.getWriter().write(3);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		response = service.ExcelUpload_BAEMIN(paramMap, request, response);
		
	}
	
	@PostMapping(value = "/ExcelUpload_ORD_08")
	@ResponseBody
	public void ExcelUpload_EMART24(MultipartHttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> paramMap, HttpSession session) {
		try {
			EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
			paramMap.put("USER_CD", uSession.getUSER_CD());
			//화면에서 정산월 클릭한 데이터를 가져옴
			String month = request.getParameter("month").replaceAll("[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]", "");
			paramMap.put("SETTLE_MONTH", month.replaceAll("\\s", ""));
			//월별 시퀀스번호 max값
			paramMap.put("startIndex", service.ExcelUpload_EMART24_STARTINDEX(month.replaceAll("\\s", "")));
		} catch (Exception e) {
			response.setStatus(400);
			try {
				response.getWriter().write(3);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		response = service.ExcelUpload_EMART24(paramMap, request, response);
		
	}

	@PostMapping(value = "/ExcelUpload_DV_01")
	@ResponseBody
	public void ExcelUpload_VROONG(MultipartHttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> paramMap, HttpSession session) {
		try {
			EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
			paramMap.put("USER_CD", uSession.getUSER_CD());
			//화면에서 정산월 클릭한 데이터를 가져옴
			String month = request.getParameter("month").replaceAll("[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]", "");
			paramMap.put("SETTLE_MONTH", month.replaceAll("\\s", ""));
			//월별 시퀀스번호 max값
			paramMap.put("startIndex", service.ExcelUpload_VROONG_STARTINDEX(month.replaceAll("\\s", "")));
		} catch (Exception e) {
			response.setStatus(400);
			try {
				response.getWriter().write(3);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		response = service.ExcelUpload_VROONG(paramMap, request, response);

	}

	@PostMapping(value = "/ExcelUpload_DV_02")
	@ResponseBody
	public void ExcelUpload_BAROGO(MultipartHttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> paramMap, HttpSession session) {
		try {
			EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
			paramMap.put("USER_CD", uSession.getUSER_CD());
			//화면에서 정산월 클릭한 데이터를 가져옴
			String month = request.getParameter("month").replaceAll("[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]", "");
			paramMap.put("SETTLE_MONTH", month.replaceAll("\\s", ""));
			//월별 시퀀스번호 max값
			paramMap.put("startIndex", service.ExcelUpload_BAROGO_STARTINDEX(month.replaceAll("\\s", "")));
		} catch (Exception e) {
			response.setStatus(400);
			try {
				response.getWriter().write(3);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		response = service.ExcelUpload_BAROGO(paramMap, request, response);

	}

	@PostMapping(value = "/ExcelUpload_DV_03")
	@ResponseBody
	public void ExcelUpload_LOGIALL(MultipartHttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> paramMap, HttpSession session) {
		try {
			EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
			paramMap.put("USER_CD", uSession.getUSER_CD());
			//화면에서 정산월 클릭한 데이터를 가져옴
			String month = request.getParameter("month").replaceAll("[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]", "");
			paramMap.put("SETTLE_MONTH", month.replaceAll("\\s", ""));
			//월별 시퀀스번호 max값
			paramMap.put("startIndex", service.ExcelUpload_LOGIALL_STARTINDEX(month.replaceAll("\\s", "")));
		} catch (Exception e) {
			response.setStatus(400);
			try {
				response.getWriter().write(3);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
         
		response = service.ExcelUpload_LOGIALL(paramMap, request, response);
		
	}

	@PostMapping(value = "/ExcelUpload_DV_04")
	@ResponseBody
	public void ExcelUpload_DEALVER(MultipartHttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> paramMap, HttpSession session) {
		try {
			EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
			paramMap.put("USER_CD", uSession.getUSER_CD());
			//화면에서 정산월 클릭한 데이터를 가져옴
			String month = request.getParameter("month").replaceAll("[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]", "");
			paramMap.put("SETTLE_MONTH", month.replaceAll("\\s", ""));
			//월별 시퀀스번호 max값
			paramMap.put("startIndex", service.ExcelUpload_DEALVER_STARTINDEX(month.replaceAll("\\s", "")));
		} catch (Exception e) {
			response.setStatus(400);
			try {
				response.getWriter().write(3);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		response = service.ExcelUpload_DEALVER(paramMap, request, response);

	}

	@PostMapping(value = "/ExcelUpload_DV_05")
	@ResponseBody
	public void ExcelUpload_SPIDOR(MultipartHttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> paramMap, HttpSession session) {
		try {
			EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
			paramMap.put("USER_CD", uSession.getUSER_CD());
			//화면에서 정산월 클릭한 데이터를 가져옴
			String month = request.getParameter("month").replaceAll("[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]", "");
			paramMap.put("SETTLE_MONTH", month.replaceAll("\\s", ""));
			//월별 시퀀스번호 max값
			paramMap.put("startIndex", service.ExcelUpload_SPIDOR_STARTINDEX(month.replaceAll("\\s", "")));
		} catch (Exception e) {
			response.setStatus(400);
			try {
				response.getWriter().write(3);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		response = service.ExcelUpload_SPIDOR(paramMap, request, response);

	}
	
	@PostMapping(value = "/ExcelUpload_DV_08")
	@ResponseBody
	public void ExcelUpload_CHAIN(MultipartHttpServletRequest request, HttpServletResponse response,
			HashMap<String, Object> paramMap, HttpSession session) {
		try {
			EM_USER_ACCOUNT_OMS uSession = (EM_USER_ACCOUNT_OMS) session.getAttribute("UserInfo");
			paramMap.put("USER_CD", uSession.getUSER_CD());
			//화면에서 정산월 클릭한 데이터를 가져옴
			String month = request.getParameter("month").replaceAll("[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]", "");
			paramMap.put("SETTLE_MONTH", month.replaceAll("\\s", ""));
			//월별 시퀀스번호 max값
			paramMap.put("startIndex", service.ExcelUpload_CHAIN_STARTINDEX(month.replaceAll("\\s", "")));
		} catch (Exception e) {
			response.setStatus(400);
			try {
				response.getWriter().write(3);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		response = service.ExcelUpload_CHAIN(paramMap, request, response);
		
	}
	//중계숫수료 정산 엑셀다운로드
	@PostMapping("ExcelDownload_ORD_10")
	public void ExcelDownload_ORD_RELAY(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response) {
		
		List<EM_RELAY_TOTAL_OMS> getData = service.getExcelData_RELAY(paramMap);

		response = service.getExcelDownload_RELAY(getData, true, response);
	}

	// 요기요 배달
	@PostMapping("/ExcelDownload_ORD_0101")
	public void ExcelDownload_ORD_YGYD(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response) {

		List<EM_SETTLE_YOGIYO_DV_ORD_OMS> getData = service.getExcelData_YGYD(paramMap);

		response = service.getExcelDownload_YGYD(getData, true, response);

	}

	// 요기요 픽업
	@PostMapping("/ExcelDownload_ORD_0102")
	public void ExcelDownload_ORD_YGYP(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response) {

		List<EM_SETTLE_YOGIYO_PU_ORD_OMS> getData = service.getExcelData_YGYP(paramMap);

		response = service.getExcelDownload_YGYP(getData, true, response);

	}

	// 카카오
	@PostMapping("/ExcelDownload_ORD_02")
	public void ExcelDownload_ORD_KAKAO(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response) {

		List<EM_SETTLE_KAKAO_DV_ORD_OMS> getData = service.getExcelData_KAKAO(paramMap);

		response = service.getExcelDownload_KAKAO(getData, true, response);

	}

	// 네이버
	@PostMapping("/ExcelDownload_ORD_03")
	public void ExcelDownload_ORD_NAVER(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response) {

		List<EM_SETTLE_NAVER_ORD_OMS> getData = service.getExcelData_NAVER(paramMap);

		response = service.getExcelDownload_NAVER(getData, true, response);

	}

	// KIS
	@PostMapping("/ExcelDownload_ORD_04")
	public void ExcelDownload_ORD_KIS(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response) {

		List<EM_SETTLE_KISAPP_ORD_OMS> getData = service.getExcelData_KIS(paramMap);

		response = service.getExcelDownload_KIS(getData, true, response);

	}
	
	// 배달의민족
	@PostMapping("/ExcelDownload_ORD_07")
	public void ExcelDownload_ORD_BAEMIN(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response) {
		
		List<EM_SETTLE_BAEMIN_DV_ORD_OMS> getData = service.getExcelData_BAEMIN(paramMap);
		
		response = service.getExcelDownload_BAEMIN(getData, true, response);
		
	}
	// 이마트24(인앱)
	@PostMapping("/ExcelDownload_ORD_08")
	public void ExcelDownload_ORD_EMART24(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response) {
		
		List<EM_SETTLE_EMART24_DV_PU_OMS> getData = service.getExcelData_EMART24(paramMap);
		
		response = service.getExcelDownload_EMART24(getData, true, response);
		
	}

	// 부릉
	@PostMapping("/ExcelDownload_DV_01")
	public void ExcelDownload_DV_VROONG(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response) {

		List<EM_SETTLE_VROONG_DV_OMS> getData = service.getExcelData_VROONG(paramMap);

		response = service.getExcelDownload_VROONG(getData, true, response);

	}

	// 바로고
	@PostMapping("/ExcelDownload_DV_02")
	public void ExcelDownload_DV_BAROGO(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response) {

		List<EM_SETTLE_BAROGO_DV_OMS> getData = service.getExcelData_BAROGO(paramMap);

		response = service.getExcelDownload_BAROGO(getData, true, response);

	}

	// 생각대로
	@PostMapping("/ExcelDownload_DV_03")
	public void ExcelDownload_DV_LOGIALL(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response) {

		List<EM_SETTLE_LOGIALL_DV_OMS> getData = service.getExcelData_LOGIALL(paramMap);

		response = service.getExcelDownload_LOGIALL(getData, true, response);

	}

	// 딜버
	@PostMapping("/ExcelDownload_DV_04")
	public void ExcelDownload_DV_DEALVER(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response) {

		List<EM_SETTLE_DEALVER_DV_OMS> getData = service.getExcelData_DEALVER(paramMap);

		response = service.getExcelDownload_DEALVER(getData, true, response);

	}

	// 스파이더
	@PostMapping("/ExcelDownload_DV_05")
	public void ExcelDownload_DV_SPIDOR(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response) {

		List<EM_SETTLE_SPIDOR_DV_OMS> getData = service.getExcelData_SPIDOR(paramMap);

		response = service.getExcelDownload_SPIDOR(getData, true, response);

	}
	
	// 체인로지스
	@PostMapping("/ExcelDownload_DV_08")
	public void ExcelDownload_DV_CHAIN(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response) {
		
		List<EM_SETTLE_CHAINLOGIS_DV_OMS> getData = service.getExcelData_CHAIN(paramMap);
		
		response = service.getExcelDownload_CHAIN(getData, true, response);
		
	}
	
	// 중계수수료정산조회
	@PostMapping(value = "/ExcelDownload_ORD_ERR10")
	@ResponseBody
	public void ExcelDownload_ORD_ERR_RELAY(@RequestParam String data, HttpServletResponse response) {
		// HTML 인코딩 -> 디코딩
		@SuppressWarnings("deprecation")
		String decodeData = CommonFunc.HTMLtoReplace(URLDecoder.decode(data));

		// String -> JSON(GSON)
		@SuppressWarnings("deprecation")
		JsonParser jsonParser = new JsonParser();
		@SuppressWarnings("deprecation")
		JsonArray jsonArray = (JsonArray) jsonParser.parse(decodeData);

		// JSONArray -> LIST<MAP>
		Gson gson = new GsonBuilder().serializeNulls().create();
		Type listType = new TypeToken<List<EM_RELAY_TOTAL_OMS>>() {
		}.getType();
		List<EM_RELAY_TOTAL_OMS> excel = gson.fromJson(jsonArray, listType);

		response = service.getExcelDownload_RELAY(excel, false, response);

	}
	// 요기요 배달
	@PostMapping(value = "/ExcelDownload_ORD_ERR0101")
	@ResponseBody
	public void ExcelDownload_ORD_ERR_YGYD(@RequestParam String data, HttpServletResponse response) {
		// HTML 인코딩 -> 디코딩
		@SuppressWarnings("deprecation")
		String decodeData = CommonFunc.HTMLtoReplace(URLDecoder.decode(data));

		// String -> JSON(GSON)
		@SuppressWarnings("deprecation")
		JsonParser jsonParser = new JsonParser();
		@SuppressWarnings("deprecation")
		JsonArray jsonArray = (JsonArray) jsonParser.parse(decodeData);

		// JSONArray -> LIST<MAP>
		Gson gson = new GsonBuilder().serializeNulls().create();
		Type listType = new TypeToken<List<EM_SETTLE_YOGIYO_DV_ORD_OMS>>() {
		}.getType();
		List<EM_SETTLE_YOGIYO_DV_ORD_OMS> excel = gson.fromJson(jsonArray, listType);

		response = service.getExcelDownload_YGYD(excel, false, response);

	}
	
	// 요기요 픽업
	@PostMapping(value = "/ExcelDownload_ORD_ERR0102")
	@ResponseBody
	public void ExcelDownload_ORD_ERR_YGYP(@RequestParam String data, HttpServletResponse response) {
		// HTML 인코딩 -> 디코딩
		@SuppressWarnings("deprecation")
		String decodeData = CommonFunc.HTMLtoReplace(URLDecoder.decode(data));

		// String -> JSON(GSON)
		@SuppressWarnings("deprecation")
		JsonParser jsonParser = new JsonParser();
		@SuppressWarnings("deprecation")
		JsonArray jsonArray = (JsonArray) jsonParser.parse(decodeData);

		// JSONArray -> LIST<MAP>
		Gson gson = new GsonBuilder().serializeNulls().create();
		Type listType = new TypeToken<List<EM_SETTLE_YOGIYO_PU_ORD_OMS>>() {
		}.getType();
		List<EM_SETTLE_YOGIYO_PU_ORD_OMS> excel = gson.fromJson(jsonArray, listType);

		response = service.getExcelDownload_YGYP(excel, false, response);

	}

	// 카카오
	@PostMapping(value = "/ExcelDownload_ORD_ERR02")
	@ResponseBody
	public void ExcelDownload_ORD_ERR_KAKAO(@RequestParam String data, HttpServletResponse response) {
		// HTML 인코딩 -> 디코딩
		@SuppressWarnings("deprecation")
		String decodeData = CommonFunc.HTMLtoReplace(URLDecoder.decode(data));

		// String -> JSON(GSON)
		@SuppressWarnings("deprecation")
		JsonParser jsonParser = new JsonParser();
		@SuppressWarnings("deprecation")
		JsonArray jsonArray = (JsonArray) jsonParser.parse(decodeData);

		// JSONArray -> LIST<MAP>
		Gson gson = new GsonBuilder().serializeNulls().create();
		Type listType = new TypeToken<List<EM_SETTLE_KAKAO_DV_ORD_OMS>>() {
		}.getType();
		List<EM_SETTLE_KAKAO_DV_ORD_OMS> excel = gson.fromJson(jsonArray, listType);

		response = service.getExcelDownload_KAKAO(excel, false, response);

	}

	// 네이버
	@PostMapping(value = "/ExcelDownload_ORD_ERR03")
	@ResponseBody
	public void ExcelDownload_ORD_ERR_NAVER(@RequestParam String data, HttpServletResponse response) {
		// HTML 인코딩 -> 디코딩
		@SuppressWarnings("deprecation")
		String decodeData = CommonFunc.HTMLtoReplace(URLDecoder.decode(data));

		// String -> JSON(GSON)
		@SuppressWarnings("deprecation")
		JsonParser jsonParser = new JsonParser();
		@SuppressWarnings("deprecation")
		JsonArray jsonArray = (JsonArray) jsonParser.parse(decodeData);

		// JSONArray -> LIST<MAP>
		Gson gson = new GsonBuilder().serializeNulls().create();
		Type listType = new TypeToken<List<EM_SETTLE_NAVER_ORD_OMS>>() {
		}.getType();
		List<EM_SETTLE_NAVER_ORD_OMS> excel = gson.fromJson(jsonArray, listType);

		response = service.getExcelDownload_NAVER(excel, false, response);

	}

	// KIS
	@PostMapping(value = "/ExcelDownload_ORD_ERR04")
	@ResponseBody
	public void ExcelDownload_ORD_ERR_KIS(@RequestParam String data, HttpServletResponse response) {
		// HTML 인코딩 -> 디코딩
		@SuppressWarnings("deprecation")
		String decodeData = CommonFunc.HTMLtoReplace(URLDecoder.decode(data));

		// String -> JSON(GSON)
		@SuppressWarnings("deprecation")
		JsonParser jsonParser = new JsonParser();
		@SuppressWarnings("deprecation")
		JsonArray jsonArray = (JsonArray) jsonParser.parse(decodeData);

		// JSONArray -> LIST<MAP>
		Gson gson = new GsonBuilder().serializeNulls().create();
		Type listType = new TypeToken<List<EM_SETTLE_KISAPP_ORD_OMS>>() {
		}.getType();
		List<EM_SETTLE_KISAPP_ORD_OMS> excel = gson.fromJson(jsonArray, listType);

		response = service.getExcelDownload_KIS(excel, false, response);

	}
	
	// 배달의민족
	@PostMapping(value = "/ExcelDownload_ORD_ERR07")
	@ResponseBody
	public void ExcelDownload_ORD_ERR_BAEMIN(@RequestParam String data, HttpServletResponse response) {
		// HTML 인코딩 -> 디코딩
		@SuppressWarnings("deprecation")
		String decodeData = CommonFunc.HTMLtoReplace(URLDecoder.decode(data));
		
		// String -> JSON(GSON)
		@SuppressWarnings("deprecation")
		JsonParser jsonParser = new JsonParser();
		@SuppressWarnings("deprecation")
		JsonArray jsonArray = (JsonArray) jsonParser.parse(decodeData);
		
		// JSONArray -> LIST<MAP>
		Gson gson = new GsonBuilder().serializeNulls().create();
		Type listType = new TypeToken<List<EM_SETTLE_BAEMIN_DV_ORD_OMS>>() {
		}.getType();
		List<EM_SETTLE_BAEMIN_DV_ORD_OMS> excel = gson.fromJson(jsonArray, listType);
		
		response = service.getExcelDownload_BAEMIN(excel, false, response);
		
	}
	// 이마트24(인앱)
	@PostMapping(value = "/ExcelDownload_ORD_ERR08")
	@ResponseBody
	public void ExcelDownload_ORD_ERR_EMART24(@RequestParam String data, HttpServletResponse response) {
		// HTML 인코딩 -> 디코딩
		@SuppressWarnings("deprecation")
		String decodeData = CommonFunc.HTMLtoReplace(URLDecoder.decode(data));
		
		// String -> JSON(GSON)
		@SuppressWarnings("deprecation")
		JsonParser jsonParser = new JsonParser();
		@SuppressWarnings("deprecation")
		JsonArray jsonArray = (JsonArray) jsonParser.parse(decodeData);
		
		// JSONArray -> LIST<MAP>
		Gson gson = new GsonBuilder().serializeNulls().create();
		Type listType = new TypeToken<List<EM_SETTLE_EMART24_DV_PU_OMS>>() {
		}.getType();
		List<EM_SETTLE_EMART24_DV_PU_OMS> excel = gson.fromJson(jsonArray, listType);
		
		response = service.getExcelDownload_EMART24(excel, false, response);
		
	}

	// 부릉
	@PostMapping(value = "/ExcelDownload_DV_ERR01")
	@ResponseBody
	public void ExcelDownload_DV_ERR_VROONG(@RequestParam String data, HttpServletResponse response) {
		// HTML 인코딩 -> 디코딩
		@SuppressWarnings("deprecation")
		String decodeData = CommonFunc.HTMLtoReplace(URLDecoder.decode(data));

		// String -> JSON(GSON)
		@SuppressWarnings("deprecation")
		JsonParser jsonParser = new JsonParser();
		@SuppressWarnings("deprecation")
		JsonArray jsonArray = (JsonArray) jsonParser.parse(decodeData);

		// JSONArray -> LIST<MAP>
		Gson gson = new GsonBuilder().serializeNulls().create();
		Type listType = new TypeToken<List<EM_SETTLE_VROONG_DV_OMS>>() {
		}.getType();
		List<EM_SETTLE_VROONG_DV_OMS> excel = gson.fromJson(jsonArray, listType);

		response = service.getExcelDownload_VROONG(excel, false, response);

	}

	// 바로고
	@PostMapping(value = "/ExcelDownload_DV_ERR02")
	@ResponseBody
	public void ExcelDownload_DV_ERR_BAROGO(@RequestParam String data, HttpServletResponse response) {
		// HTML 인코딩 -> 디코딩
		String decodeData = CommonFunc.HTMLtoReplace(data);//애는 URLDecoder주면 %를 못읽음 그래서 URLDecoder안함

		// String -> JSON(GSON)
		@SuppressWarnings("deprecation")
		JsonParser jsonParser = new JsonParser();
		@SuppressWarnings("deprecation")
		JsonArray jsonArray = (JsonArray) jsonParser.parse(decodeData);

		// JSONArray -> LIST<MAP>
		Gson gson = new GsonBuilder().serializeNulls().create();
		Type listType = new TypeToken<List<EM_SETTLE_BAROGO_DV_OMS>>() {
		}.getType();
		List<EM_SETTLE_BAROGO_DV_OMS> excel = gson.fromJson(jsonArray, listType);

		response = service.getExcelDownload_BAROGO(excel, false, response);

	}

	// 생각대로
	@PostMapping(value = "/ExcelDownload_DV_ERR03")
	@ResponseBody
	public void ExcelDownload_DV_ERR_LOGIALL(@RequestParam String data, HttpServletResponse response) {
		// HTML 인코딩 -> 디코딩
		@SuppressWarnings("deprecation")
		String decodeData = CommonFunc.HTMLtoReplace(URLDecoder.decode(data));

		// String -> JSON(GSON)
		@SuppressWarnings("deprecation")
		JsonParser jsonParser = new JsonParser();
		@SuppressWarnings("deprecation")
		JsonArray jsonArray = (JsonArray) jsonParser.parse(decodeData);

		// JSONArray -> LIST<MAP>
		Gson gson = new GsonBuilder().serializeNulls().create();
		Type listType = new TypeToken<List<EM_SETTLE_LOGIALL_DV_OMS>>() {
		}.getType();
		List<EM_SETTLE_LOGIALL_DV_OMS> excel = gson.fromJson(jsonArray, listType);

		response = service.getExcelDownload_LOGIALL(excel, false, response);

	}

	// 딜버
	@PostMapping(value = "/ExcelDownload_DV_ERR04")
	@ResponseBody
	public void ExcelDownload_DV_ERR_DEALVER(@RequestParam String data, HttpServletResponse response) {
		// HTML 인코딩 -> 디코딩
		@SuppressWarnings("deprecation")
		String decodeData = CommonFunc.HTMLtoReplace(URLDecoder.decode(data));

		// String -> JSON(GSON)
		@SuppressWarnings("deprecation")
		JsonParser jsonParser = new JsonParser();
		@SuppressWarnings("deprecation")
		JsonArray jsonArray = (JsonArray) jsonParser.parse(decodeData);

		// JSONArray -> LIST<MAP>
		Gson gson = new GsonBuilder().serializeNulls().create();
		Type listType = new TypeToken<List<EM_SETTLE_DEALVER_DV_OMS>>() {
		}.getType();
		List<EM_SETTLE_DEALVER_DV_OMS> excel = gson.fromJson(jsonArray, listType);

		response = service.getExcelDownload_DEALVER(excel, false, response);

	}

	// 스파이더
	@PostMapping(value = "/ExcelDownload_DV_ERR05")
	@ResponseBody
	public void ExcelDownload_DV_ERR_SPIDOR(@RequestParam String data, HttpServletResponse response) {
		// HTML 인코딩 -> 디코딩
		@SuppressWarnings("deprecation")
		String decodeData = CommonFunc.HTMLtoReplace(URLDecoder.decode(data));

		// String -> JSON(GSON)
		@SuppressWarnings("deprecation")
		JsonParser jsonParser = new JsonParser();
		@SuppressWarnings("deprecation")
		JsonArray jsonArray = (JsonArray) jsonParser.parse(decodeData);

		// JSONArray -> LIST<MAP>
		Gson gson = new GsonBuilder().serializeNulls().create();
		Type listType = new TypeToken<List<EM_SETTLE_SPIDOR_DV_OMS>>() {
		}.getType();
		List<EM_SETTLE_SPIDOR_DV_OMS> excel = gson.fromJson(jsonArray, listType);

		response = service.getExcelDownload_SPIDOR(excel, false, response);

	}
	
	// 체인로지스
	@PostMapping(value = "/ExcelDownload_DV_ERR08")
	@ResponseBody
	public void ExcelDownload_DV_ERR_CHAIN(@RequestParam String data, HttpServletResponse response) {
		// HTML 인코딩 -> 디코딩
		@SuppressWarnings("deprecation")
		String decodeData = CommonFunc.HTMLtoReplace(URLDecoder.decode(data));
		
		// String -> JSON(GSON)
		@SuppressWarnings("deprecation")
		JsonParser jsonParser = new JsonParser();
		@SuppressWarnings("deprecation")
		JsonArray jsonArray = (JsonArray) jsonParser.parse(decodeData);
		
		// JSONArray -> LIST<MAP>
		Gson gson = new GsonBuilder().serializeNulls().create();
		Type listType = new TypeToken<List<EM_SETTLE_CHAINLOGIS_DV_OMS>>() {
		}.getType();
		List<EM_SETTLE_CHAINLOGIS_DV_OMS> excel = gson.fromJson(jsonArray, listType);
		
		response = service.getExcelDownload_CHAIN(excel, false, response);
		
	}

	// 배달정산집계
	@PostMapping("/ExcelDownload_TOTAL")
	public void ExcelDownload_TOTAL(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response) {
		
		
		List<EM_DELIVERY_TOTAL_OMS> getData = service.getExcelData_TOTAL(paramMap);

		response = service.getExcelDownload_TOTAL(getData, response);

	}

	// 채널사 엑셀 취함
	@PostMapping("/ExcelDownload_Synthesis")
	public void ExcelDownload_Synthesis(@RequestParam HashMap<String, String> paramMap, HttpServletResponse response) {
		paramMap.get("stype"); // mfc구분
		switch (paramMap.get("channel")) {
		case "Order":
			response = service.getExcelSynthesis_Order(paramMap, response);
		case "Delivery":
			response = service.getExcelSynthesis_Delivery(paramMap, response);
		}
	}

	// 요기요 배달 엑셀샘플 다운로드
		@PostMapping("/Excel_Sample_Download_Relay")
		public void Excel_Sample_Download_ORD_RELAY(@RequestParam HashMap<String, Object> paramMap,	HttpServletRequest request, HttpServletResponse response)
				 throws IOException{

			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			try {
				String path = request.getSession().getServletContext().getRealPath("resources/excel_sample/");

				path += "이마트24_배달_중계수수료_엑셀업로드_양식.xlsx"; // 경로에 접근할 때 역슬래시('\') 사용

				
				File file = new File(path);
				String fileName = new String(file.getName().getBytes("UTF-8"), "ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment;filename=" + fileName); // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를
																								// 알려주는 헤더

				@SuppressWarnings("resource")
				FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기
				OutputStream out = response.getOutputStream();

				int read = 0;
				byte[] buffer = new byte[1024];
				while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을
																		// 파일이 없음
					out.write(buffer, 0, read);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}	
			
			

		}

	
	// 요기요 배달 엑셀샘플 다운로드
		@PostMapping("/Excel_Sample_Download_ORD_0101")
		public void Excel_Sample_Download_ORD_YGYD(@RequestParam HashMap<String, Object> paramMap,HttpServletRequest request, HttpServletResponse response)
				 throws IOException{

			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			try {
				String path = request.getSession().getServletContext().getRealPath("resources/excel_sample/");

				path += "요기요배달_엑셀업로드_양식.xlsx"; // 경로에 접근할 때 역슬래시('\') 사용

				
				File file = new File(path);
				String fileName = new String(file.getName().getBytes("UTF-8"), "ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment;filename=" + fileName); // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를
																								// 알려주는 헤더

				@SuppressWarnings("resource")
				FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기
				OutputStream out = response.getOutputStream();

				int read = 0;
				byte[] buffer = new byte[1024];
				while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을
																		// 파일이 없음
					out.write(buffer, 0, read);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}	
			
			

		}
		// 요기요 픽업 엑셀샘플 다운로드
		@PostMapping("/Excel_Sample_Download_ORD_0102")
		public void Excel_Sample_Download_ORD_YGYP(@RequestParam HashMap<String, Object> paramMap,HttpServletRequest request, HttpServletResponse response)
				throws IOException{
			
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			try {
				String path = request.getSession().getServletContext().getRealPath("resources/excel_sample/");

				path += "요기요픽업_엑셀업로드_양식.xlsx"; // 경로에 접근할 때 역슬래시('\') 사용

				
				File file = new File(path);
				String fileName = new String(file.getName().getBytes("UTF-8"), "ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment;filename=" + fileName); // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를
																								// 알려주는 헤더

				@SuppressWarnings("resource")
				FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기
				OutputStream out = response.getOutputStream();

				int read = 0;
				byte[] buffer = new byte[1024];
				while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을
																		// 파일이 없음
					out.write(buffer, 0, read);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}	
			
			
			
		}
		// 카카오 엑셀샘플 다운로드
		@PostMapping("/Excel_Sample_Download_ORD_02")
		public void Excel_Sample_Download_ORD_KAKAO(@RequestParam HashMap<String, Object> paramMap,HttpServletRequest request, HttpServletResponse response)
				throws IOException{
			
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			try {
				String path = request.getSession().getServletContext().getRealPath("resources/excel_sample/");

				path += "카카오_엑셀업로드_양식.xlsx"; // 경로에 접근할 때 역슬래시('\') 사용

				
				File file = new File(path);
				String fileName = new String(file.getName().getBytes("UTF-8"), "ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment;filename=" + fileName); // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를
																								// 알려주는 헤더

				@SuppressWarnings("resource")
				FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기
				OutputStream out = response.getOutputStream();

				int read = 0;
				byte[] buffer = new byte[1024];
				while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을
																		// 파일이 없음
					out.write(buffer, 0, read);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		// 네이버 엑셀샘플 다운로드
				@PostMapping("/Excel_Sample_Download_ORD_03")
				public void Excel_Sample_Download_ORD_NAVER(@RequestParam HashMap<String, Object> paramMap,HttpServletRequest request, HttpServletResponse response)
						throws IOException{
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html; charset=UTF-8");
					try {
						String path = request.getSession().getServletContext().getRealPath("resources/excel_sample/");

						path += "네이버_엑셀업로드_양식.xlsx"; // 경로에 접근할 때 역슬래시('\') 사용

						
						File file = new File(path);
						String fileName = new String(file.getName().getBytes("UTF-8"), "ISO-8859-1");
						response.setHeader("Content-Disposition", "attachment;filename=" + fileName); // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를
																										// 알려주는 헤더

						@SuppressWarnings("resource")
						FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기
						OutputStream out = response.getOutputStream();

						int read = 0;
						byte[] buffer = new byte[1024];
						while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을
																				// 파일이 없음
							out.write(buffer, 0, read);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}	
					

				}
				// 배달의민족 엑셀샘플 다운로드
				@PostMapping("/Excel_Sample_Download_ORD_07")
				public void Excel_Sample_Download_ORD_BAEMIN(@RequestParam HashMap<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response)
						throws IOException{
					
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html; charset=UTF-8");
					try {
						String path = request.getSession().getServletContext().getRealPath("resources/excel_sample/");

						path += "배민_엑셀업로드_양식.xlsx"; // 경로에 접근할 때 역슬래시('\') 사용

						
						File file = new File(path);
						String fileName = new String(file.getName().getBytes("UTF-8"), "ISO-8859-1");
						response.setHeader("Content-Disposition", "attachment;filename=" + fileName); // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를
																										// 알려주는 헤더

						@SuppressWarnings("resource")
						FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기
						OutputStream out = response.getOutputStream();

						int read = 0;
						byte[] buffer = new byte[1024];
						while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을
																				// 파일이 없음
							out.write(buffer, 0, read);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}					
				}
				// 이마트24 엑셀샘플 다운로드
				@PostMapping("/Excel_Sample_Download_ORD_08")
				public void Excel_Sample_Download_ORD_EMART24(@RequestParam HashMap<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response)
						throws IOException{
					
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html; charset=UTF-8");
					try {
						String path = request.getSession().getServletContext().getRealPath("resources/excel_sample/");
						
						path += "이마트24_엑셀업로드_양식.xlsx"; // 경로에 접근할 때 역슬래시('\') 사용
						
						
						File file = new File(path);
						String fileName = new String(file.getName().getBytes("UTF-8"), "ISO-8859-1");
						response.setHeader("Content-Disposition", "attachment;filename=" + fileName); // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를
						// 알려주는 헤더
						
						@SuppressWarnings("resource")
						FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기
						OutputStream out = response.getOutputStream();
						
						int read = 0;
						byte[] buffer = new byte[1024];
						while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을
							// 파일이 없음
							out.write(buffer, 0, read);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}
				// 메쉬코리아(부릉) 엑셀샘플 다운로드
				@PostMapping("/Excel_Sample_Download_DV_01")
				public void Excel_Sample_Download_DV_VROONG(@RequestParam HashMap<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response)
						throws IOException{
					
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html; charset=UTF-8");
					try {
						String path = request.getSession().getServletContext().getRealPath("resources/excel_sample/");
						
						path += "메쉬코리아(부릉)_엑셀업로드_양식.xlsx"; // 경로에 접근할 때 역슬래시('\') 사용
						
						
						File file = new File(path);
						String fileName = new String(file.getName().getBytes("UTF-8"), "ISO-8859-1");
						response.setHeader("Content-Disposition", "attachment;filename=" + fileName); // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를
						// 알려주는 헤더
						
						@SuppressWarnings("resource")
						FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기
						OutputStream out = response.getOutputStream();
						
						int read = 0;
						byte[] buffer = new byte[1024];
						while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을
							// 파일이 없음
							out.write(buffer, 0, read);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}
				// 바로고 엑셀샘플 다운로드
				@PostMapping("/Excel_Sample_Download_DV_02")
				public void Excel_Sample_Download_DV_BAROGO(@RequestParam HashMap<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response)
						throws IOException{
					
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html; charset=UTF-8");
					try {
						String path = request.getSession().getServletContext().getRealPath("resources/excel_sample/");
						
						path += "바로고_엑셀업로드_양식.xlsx"; // 경로에 접근할 때 역슬래시('\') 사용
						
						
						File file = new File(path);
						String fileName = new String(file.getName().getBytes("UTF-8"), "ISO-8859-1");
						response.setHeader("Content-Disposition", "attachment;filename=" + fileName); // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를
						// 알려주는 헤더
						
						@SuppressWarnings("resource")
						FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기
						OutputStream out = response.getOutputStream();
						
						int read = 0;
						byte[] buffer = new byte[1024];
						while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을
							// 파일이 없음
							out.write(buffer, 0, read);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}
				// 생각대로 엑셀샘플 다운로드
				@PostMapping("/Excel_Sample_Download_DV_03")
				public void Excel_Sample_Download_DV_LOGIALL(@RequestParam HashMap<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response)
						throws IOException{
					
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html; charset=UTF-8");
					try {
						String path = request.getSession().getServletContext().getRealPath("resources/excel_sample/");
						
						path += "생각대로(로지올)_엑셀업로드_양식.xlsx"; // 경로에 접근할 때 역슬래시('\') 사용
						
						
						File file = new File(path);
						String fileName = new String(file.getName().getBytes("UTF-8"), "ISO-8859-1");
						response.setHeader("Content-Disposition", "attachment;filename=" + fileName); // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를
						// 알려주는 헤더
						
						@SuppressWarnings("resource")
						FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기
						OutputStream out = response.getOutputStream();
						
						int read = 0;
						byte[] buffer = new byte[1024];
						while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을
							// 파일이 없음
							out.write(buffer, 0, read);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}
				// 딜버 엑셀샘플 다운로드
				@PostMapping("/Excel_Sample_Download_DV_04")
				public void Excel_Sample_Download_DV_DEALVER(@RequestParam HashMap<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response)
						throws IOException{
					
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html; charset=UTF-8");
					try {
						String path = request.getSession().getServletContext().getRealPath("resources/excel_sample/");
						
						path += "딜버_엑셀업로드_양식.xlsx"; // 경로에 접근할 때 역슬래시('\') 사용
						
						
						File file = new File(path);
						String fileName = new String(file.getName().getBytes("UTF-8"), "ISO-8859-1");
						response.setHeader("Content-Disposition", "attachment;filename=" + fileName); // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를
						// 알려주는 헤더
						
						@SuppressWarnings("resource")
						FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기
						OutputStream out = response.getOutputStream();
						
						int read = 0;
						byte[] buffer = new byte[1024];
						while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을
							// 파일이 없음
							out.write(buffer, 0, read);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}
				// 스파이더 엑셀샘플 다운로드
				@PostMapping("/Excel_Sample_Download_DV_05")
				public void Excel_Sample_Download_DV_SPIDOR(@RequestParam HashMap<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response)
						throws IOException{
					
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html; charset=UTF-8");
					try {
						String path = request.getSession().getServletContext().getRealPath("resources/excel_sample/");
						
						path += "스파이더크래프트_엑셀업로드_양식.xlsx"; // 경로에 접근할 때 역슬래시('\') 사용
						
						
						File file = new File(path);
						String fileName = new String(file.getName().getBytes("UTF-8"), "ISO-8859-1");
						response.setHeader("Content-Disposition", "attachment;filename=" + fileName); // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를
						// 알려주는 헤더
						
						@SuppressWarnings("resource")
						FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기
						OutputStream out = response.getOutputStream();
						
						int read = 0;
						byte[] buffer = new byte[1024];
						while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을
							// 파일이 없음
							out.write(buffer, 0, read);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}
				
}