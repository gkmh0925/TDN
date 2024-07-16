package oms.kisvan.emart.model.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;

import lombok.Setter;
import oms.kisvan.emart.common.CommonFunc;
import oms.kisvan.emart.common.ExcelStyle;
import oms.kisvan.emart.excel.util.ExcelReadCalc_BAEMIN;
import oms.kisvan.emart.excel.util.ExcelReadCalc_BAROGO;
import oms.kisvan.emart.excel.util.ExcelReadCalc_CHAIN;
import oms.kisvan.emart.excel.util.ExcelReadCalc_DEALVER;
import oms.kisvan.emart.excel.util.ExcelReadCalc_EMART24;
import oms.kisvan.emart.excel.util.ExcelReadCalc_KAKAO;
import oms.kisvan.emart.excel.util.ExcelReadCalc_KIS;
import oms.kisvan.emart.excel.util.ExcelReadCalc_LOGIALL;
import oms.kisvan.emart.excel.util.ExcelReadCalc_NAVER;
import oms.kisvan.emart.excel.util.ExcelReadCalc_SPIDOR;
import oms.kisvan.emart.excel.util.ExcelReadCalc_VROONG;
import oms.kisvan.emart.excel.util.ExcelReadCalc_YGYD;
import oms.kisvan.emart.excel.util.ExcelReadCalc_YGYP;
import oms.kisvan.emart.excel.util.ExcelReadCalc_RELAY;


import oms.kisvan.emart.excel.util.ExcelReadOption;
import oms.kisvan.emart.model.dao.ExcelDAO;
import oms.kisvan.emart.model.dao.PosDAO;
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
import oms.kisvan.emart.model.dto.EM_SETTLE_POS_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_SPIDOR_DV_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_VROONG_DV_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_YOGIYO_DV_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SETTLE_YOGIYO_PU_ORD_OMS;
import oms.kisvan.emart.model.dto.EM_SYNTH_DV_CH_VIEW;
import oms.kisvan.emart.model.dto.EM_SYNTH_ORD_CH_VIEW;
import oms.kisvan.emart.model.dto.TB_ORDER_STATE;
import oms.kisvan.emart.model.dto.EM_RELAY_TOTAL_OMS;


/**
 * @Author : 이준혁
 * @Date : 2022. 8. 30.
 * @Explan : 엑셀 서비스
 */
@Service
public class ExcelService {
	@Setter(onMethod_ = @Autowired)
	private ExcelDAO dao;


	// 주문 리스트 엑셀 다운로드
	public HttpServletResponse getStoreExcel(HashMap<String, Object> paramMap, HttpServletResponse response)
			throws Exception {

		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

		List<EM_COMPANY_MASTER_OMS> storeList = dao.getStoreExcel(paramMap);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 11;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("매장리스트");

		sheet.setColumnWidth(0, 5 * 256);
		sheet.setColumnWidth(1, 15 * 256);
		sheet.setColumnWidth(2, 30 * 256);
		sheet.setColumnWidth(3, 15 * 256);
		sheet.setColumnWidth(4, 60 * 256);
		sheet.setColumnWidth(5, 12 * 256);
		sheet.setColumnWidth(6, 20 * 256);
		sheet.setColumnWidth(7, 15 * 256);
		sheet.setColumnWidth(8, 15 * 256);
		sheet.setColumnWidth(9, 45 * 256);
		sheet.setColumnWidth(10, 20 * 256);
		sheet.setColumnWidth(11, 25 * 256);

		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("순번");
		row.createCell(cellCount++).setCellValue("매장코드");
		row.createCell(cellCount++).setCellValue("매장명");
		row.createCell(cellCount++).setCellValue("MFC구분");
		row.createCell(cellCount++).setCellValue("주소");
		row.createCell(cellCount++).setCellValue("매장상태");
		row.createCell(cellCount++).setCellValue("매장전화");
		row.createCell(cellCount++).setCellValue("주중영업시작");
		row.createCell(cellCount++).setCellValue("주중영업끝");
		row.createCell(cellCount++).setCellValue("채널사명");
		row.createCell(cellCount++).setCellValue("배달대행사명");
		row.createCell(cellCount++).setCellValue("등록일자");

		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (EM_COMPANY_MASTER_OMS cmo : storeList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue(cmo.getRN());
			row.createCell(cellCount++).setCellValue(cmo.getCPN_CD()); // 데이터를 가져와 입력
			row.createCell(cellCount++).setCellValue(cmo.getCPN_NM());
			row.createCell(cellCount++).setCellValue(cmo.getMFC_TP_CD());
			row.createCell(cellCount++).setCellValue(cmo.getCPN_ADDR());
			row.createCell(cellCount++).setCellValue(cmo.getCPN_STATE());
			row.createCell(cellCount++).setCellValue(cmo.getCPN_TEL());
			row.createCell(cellCount++).setCellValue(cmo.getDAY_START_TIME());
			row.createCell(cellCount++).setCellValue(cmo.getDAY_END_TIME());
			row.createCell(cellCount++).setCellValue(cmo.getORD_CH_NM());
			row.createCell(cellCount++).setCellValue(cmo.getDLV_CH_NM());
			row.createCell(cellCount++).setCellValue(CommonFunc.unixTimeFormat(cmo.getREG_DATE()));
			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				row.getCell(e).setCellStyle(bodyStyle);
			}
		}

		// 컨텐츠 타입과 파일명 지정

		Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다

		// 년월일시분초 14자리 포멧
		SimpleDateFormat simple_format = new SimpleDateFormat("yyyyMMdd_HHmmss");

		String getNowDatetime = simple_format.format(date_now);

		String fileName = "EMART24_OMS_매장리스트_" + getNowDatetime + ".xlsx";

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

		return response;
	}

	// 상품 리스트 엑셀 다운로드
	public List<EM_MENU_MASTER_OMS> getProductExcel(HashMap<String, Object> paramMap) {
		return dao.getProductExcel(paramMap);
	}

	// 주문 리스트 엑셀 다운로드
	public HttpServletResponse getOrderExcel(HashMap<String, Object> paramMap, HttpServletResponse response)
			throws Exception {

		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

		// 숫자 포멧
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

		List<TB_ORDER_STATE> orderList = dao.getOrderExcel(paramMap);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 24;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("주문리스트");

		sheet.setColumnWidth(0, 5 * 256);// 순번
		sheet.setColumnWidth(1, 15 * 256);// 주문일자
		sheet.setColumnWidth(2, 25 * 256);// 주문번호
		sheet.setColumnWidth(3, 15 * 256);// 매장코드
		sheet.setColumnWidth(4, 15 * 256);// 매장명
		sheet.setColumnWidth(5, 25 * 256);// 주문채널명
		sheet.setColumnWidth(6, 15 * 256);// 주문상태
		sheet.setColumnWidth(7, 15 * 256);// 정산주문상태
		sheet.setColumnWidth(8, 15 * 256);// 주문타입
		sheet.setColumnWidth(9, 15 * 256);// 배달비(원)
		sheet.setColumnWidth(10, 15 * 256);// 상품금액(원)
		sheet.setColumnWidth(11, 15 * 256);// 합계금액(원)
		sheet.setColumnWidth(12, 15 * 256);// 부가세(원)
		sheet.setColumnWidth(13, 35 * 256);// 기본주소
		sheet.setColumnWidth(14, 25 * 256);// 상세주소
		sheet.setColumnWidth(15, 25 * 256);// 상세주문번호
		sheet.setColumnWidth(16, 25 * 256);// 네이버주문번호
		sheet.setColumnWidth(17, 15 * 256);// 판매매장코드
		sheet.setColumnWidth(18, 20 * 256);// 고객전화번호
		sheet.setColumnWidth(19, 25 * 256);// 배송채널
		sheet.setColumnWidth(20, 20 * 256);// 차량정보
		sheet.setColumnWidth(21, 20 * 256);// 픽업예상시간
		sheet.setColumnWidth(22, 25 * 256);// 처리결과메시지
		sheet.setColumnWidth(23, 25 * 256);// 등록일시
		sheet.setColumnWidth(24, 25 * 256);// 최종수정일시

		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("순번");
		row.createCell(cellCount++).setCellValue("주문일자");
		row.createCell(cellCount++).setCellValue("주문번호");
		row.createCell(cellCount++).setCellValue("매장코드");
		row.createCell(cellCount++).setCellValue("매장명");
		row.createCell(cellCount++).setCellValue("주문채널명");
		row.createCell(cellCount++).setCellValue("주문상태");
		row.createCell(cellCount++).setCellValue("정산주문상태");
		row.createCell(cellCount++).setCellValue("주문타입");
		row.createCell(cellCount++).setCellValue("배송료(원)");
		row.createCell(cellCount++).setCellValue("결제금액(원)");
		row.createCell(cellCount++).setCellValue("합계금액(원)");
		row.createCell(cellCount++).setCellValue("부가세(원)");
		row.createCell(cellCount++).setCellValue("기본주소");
		row.createCell(cellCount++).setCellValue("상세주소");
		row.createCell(cellCount++).setCellValue("상세주문번호");
		row.createCell(cellCount++).setCellValue("네이버주문번호");
		row.createCell(cellCount++).setCellValue("판매매장코드");
		row.createCell(cellCount++).setCellValue("고객전화번호");
		row.createCell(cellCount++).setCellValue("배송채널");
		row.createCell(cellCount++).setCellValue("차량정보");
		row.createCell(cellCount++).setCellValue("픽업예상시간");
		row.createCell(cellCount++).setCellValue("처리결과메시지");
		row.createCell(cellCount++).setCellValue("주문등록일시");
		row.createCell(cellCount++).setCellValue("최종수정일시");

		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (TB_ORDER_STATE tos : orderList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue(tos.getRN()); // 순번
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(tos.getORD_TIME().substring(0, 8))); // 주문일자
			row.createCell(cellCount++).setCellValue(tos.getORD_ID()); // 주문번호
			row.createCell(cellCount++).setCellValue(tos.getSTORE_ID()); // 매장코드
			row.createCell(cellCount++).setCellValue(tos.getSTORE_NM()); // 매장명
			row.createCell(cellCount++).setCellValue(tos.getORDER_CH_NM()); // 채널사명
			row.createCell(cellCount++).setCellValue(tos.getORD_STATE()); // 주문상태
			row.createCell(cellCount++).setCellValue(tos.getSTATE_RESULT()); // 정산주문상태
			row.createCell(cellCount++).setCellValue(tos.getPICKUP_NAME()); // 주문방식
			row.createCell(cellCount++).setCellValue(tos.getDELIVER_FEE()); // 배송료(원)
			row.createCell(cellCount++).setCellValue(tos.getACT_AMT()); // 결제금액
			row.createCell(cellCount++).setCellValue(tos.getTOT_AMT()); // 합계금액
			row.createCell(cellCount++).setCellValue(tos.getTOT_VAT()); // 부가세
			row.createCell(cellCount++).setCellValue(tos.getORD_ADDR()); // 주소
			row.createCell(cellCount++).setCellValue(tos.getORD_ADDR_DETAIL()); // 상세주소
			row.createCell(cellCount++).setCellValue(tos.getDTORD_NO()); // 상세주문번호
			row.createCell(cellCount++).setCellValue(tos.getSETTLE_NO()); // 네이버주문번호
			row.createCell(cellCount++).setCellValue(tos.getSALE_STORE()); // 판매매장코드
			row.createCell(cellCount++).setCellValue(CommonFunc.telFormat(tos.getPHONE())); // 배송연락처
			row.createCell(cellCount++).setCellValue(tos.getDLV_CH_NM()); // 배송채널명
			row.createCell(cellCount++).setCellValue(tos.getCAR_NUMBER()); // 차량정보
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(tos.getEXPECTED_PICKUP_TIME())); // 픽업예상시간
			row.createCell(cellCount++).setCellValue(tos.getRST_MSG()); // 처리결과메시지
			row.createCell(cellCount++).setCellValue(CommonFunc.unixTimeFormat(tos.getREGDATE())); // 등록일시
			row.createCell(cellCount++).setCellValue(CommonFunc.unixTimeFormat(tos.getMODI_DATE())); // 최종수정일시
			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (e == 8 || e == 9 || e == 10 || e == 11) {
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

		String fileName = "EMART24_OMS_주문리스트_" + getNowDatetime + ".xlsx";

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

		return response;
	}

	// 포스 리스트 엑셀 다운로드
	public HttpServletResponse getPosExcel(HashMap<String, Object> paramMap, HttpServletResponse response)
			throws Exception {

		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

		// 숫자 포멧
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

		List<EM_SETTLE_POS_ORD_OMS> posList = dao.getPosExcel(paramMap);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 18;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("POS리스트");

		sheet.setColumnWidth(0, 5 * 256); //// 순번
		sheet.setColumnWidth(1, 15 * 256); // 주문일자
		sheet.setColumnWidth(2, 15 * 256); // 제휴사구분코드
		sheet.setColumnWidth(3, 15 * 256); // 제휴사명
		sheet.setColumnWidth(4, 10 * 256); // 점포코드
		sheet.setColumnWidth(5, 20 * 256); // 점포명
		sheet.setColumnWidth(6, 25 * 256); // 채널사주문번호
		sheet.setColumnWidth(7, 15 * 256); // 매출발생점포코드
		sheet.setColumnWidth(8, 20 * 256); // 매출발생점포명
		sheet.setColumnWidth(9, 25 * 256); // 상세주문번호
		sheet.setColumnWidth(10, 25 * 256); // 이마트주문번호
		sheet.setColumnWidth(11, 15 * 256); // 배송업체
		sheet.setColumnWidth(12, 15 * 256); // 주문금액
		sheet.setColumnWidth(13, 20 * 256); // 주문상태
		sheet.setColumnWidth(14, 20 * 256); // 정산주문상태
		sheet.setColumnWidth(15, 20 * 256); // 네이버NPAY번호
		sheet.setColumnWidth(16, 10 * 256); // 픽업코드
		sheet.setColumnWidth(17, 15 * 256); // MFC구분
		sheet.setColumnWidth(18, 25 * 256); // 등록일시

		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("순번");
		row.createCell(cellCount++).setCellValue("주문일자");
		row.createCell(cellCount++).setCellValue("제휴사구분코드");
		row.createCell(cellCount++).setCellValue("제휴사명");
		row.createCell(cellCount++).setCellValue("점포코드");
		row.createCell(cellCount++).setCellValue("점포명");
		row.createCell(cellCount++).setCellValue("채널사주문번호");
		row.createCell(cellCount++).setCellValue("매출발생점포코드");
		row.createCell(cellCount++).setCellValue("매출발생점포명");
		row.createCell(cellCount++).setCellValue("상세주문번호");
		row.createCell(cellCount++).setCellValue("이마트주문번호");
		row.createCell(cellCount++).setCellValue("배송업체");
		row.createCell(cellCount++).setCellValue("주문금액");
		row.createCell(cellCount++).setCellValue("주문상태");
		row.createCell(cellCount++).setCellValue("정산주문상태");
		row.createCell(cellCount++).setCellValue("네이버NPAY번호");
		row.createCell(cellCount++).setCellValue("픽업코드");
		row.createCell(cellCount++).setCellValue("MFC구분");
		row.createCell(cellCount++).setCellValue("등록일시");

		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (EM_SETTLE_POS_ORD_OMS poo : posList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue(poo.getRN()); // 순번
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(poo.getORD_DT())); // 주문일자
			row.createCell(cellCount++).setCellValue(poo.getCHNEL_CD()); // 제휴사구분코드
			row.createCell(cellCount++).setCellValue(poo.getCHNEL_NM()); // 제휴사명
			row.createCell(cellCount++).setCellValue(poo.getSTR_CD()); // 점포코드
			row.createCell(cellCount++).setCellValue(poo.getSTR_NM()); // 점포명
			row.createCell(cellCount++).setCellValue(poo.getCH_ORD_NO()); // 채널사주문번호
			row.createCell(cellCount++).setCellValue(poo.getSALE_STORE()); // 매출발생점포코드
			row.createCell(cellCount++).setCellValue(poo.getSALE_STORE_NM()); // 매출발생점포명
			row.createCell(cellCount++).setCellValue(poo.getDTORD_NO()); // 상세주문번호
			row.createCell(cellCount++).setCellValue(poo.getEM_ORD_NO()); // 이마트주문번호
			row.createCell(cellCount++).setCellValue(poo.getTRANL_CD()); // 배송업체
			row.createCell(cellCount++).setCellValue(poo.getORD_AMT()); // 주문금액
			row.createCell(cellCount++).setCellValue(poo.getORD_STAT()); // 주문상태
			row.createCell(cellCount++).setCellValue(poo.getSTATE_RESULT()); // 정산주문상태
			row.createCell(cellCount++).setCellValue(poo.getNPAY_ORD_NO()); // 네이버
			row.createCell(cellCount++).setCellValue(poo.getPICKUP_CD()); // 픽업코드
			row.createCell(cellCount++).setCellValue(poo.getMFC_TP_CD()); // MFC구분
			row.createCell(cellCount++).setCellValue(CommonFunc.unixTimeFormat(poo.getREG_DATE())); // 등록일시

			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (e == 12) {
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

		String fileName = "EMART24_OMS_POS리스트_" + getNowDatetime + ".xlsx";

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

		return response;
	}
	
	
	
	// 채널 리스트 엑셀 다운로드
			public HttpServletResponse getchannelExcel(HashMap<String, Object> paramMap, HttpServletResponse response)
					throws Exception {

				// 워크북 생성
				SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

				// header CellStyle 작성
				XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

				// body CellStyle 작성
				XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

				List<EM_COMPANY_MASTER_OMS> channelList = dao.getchannelExcel(paramMap);

				// 엑셀 파일 작성
				SXSSFRow row = null; // 행
				int rowCount = 0;
				int cellCount = 0;
				int columnCnt = 12;

				// SXSSFSheet 생성
				SXSSFSheet sheet = null;
				if(paramMap.get("cpntype").equals("ORDER")) {
					sheet = wb.createSheet("채널리스트");
					
				}else {
					sheet = wb.createSheet("배달리스트");
				}
				

				sheet.setColumnWidth(0, 5 * 256);  //순번
				sheet.setColumnWidth(1, 10 * 256); //채널사코드
				sheet.setColumnWidth(2, 30 * 256); //채널사명
				sheet.setColumnWidth(3, 10 * 256); //업체상태
				sheet.setColumnWidth(4, 20 * 256); //사업자번호
				sheet.setColumnWidth(5, 20 * 256); //대표자명
				sheet.setColumnWidth(6, 80 * 256); //주소
				sheet.setColumnWidth(7, 20 * 256); //콜센터전화
				sheet.setColumnWidth(8, 20 * 256); //담장자명
				sheet.setColumnWidth(9, 20 * 256); //담당자휴대번호
				sheet.setColumnWidth(10, 40 * 256);//이메일
				sheet.setColumnWidth(11, 25 * 256);//인앱여부
				sheet.setColumnWidth(11, 25 * 256);//등록일자

				// 엑셀 내용 작성
				// 제목 Cell 생성
				row = sheet.createRow(rowCount++);
				row.setHeight((short) 420);

				// 스타일 적용
				row.createCell(cellCount++).setCellValue("순번");
				row.createCell(cellCount++).setCellValue("업체코드");
				row.createCell(cellCount++).setCellValue("업체명");
				row.createCell(cellCount++).setCellValue("업체상태");
				row.createCell(cellCount++).setCellValue("사업자번호");
				row.createCell(cellCount++).setCellValue("대표자명");
				row.createCell(cellCount++).setCellValue("주소");
				row.createCell(cellCount++).setCellValue("콜센터전화");
				row.createCell(cellCount++).setCellValue("담당자명");
				row.createCell(cellCount++).setCellValue("담당자휴대번호");
				row.createCell(cellCount++).setCellValue("이메일");
				row.createCell(cellCount++).setCellValue("인앱여부");
				row.createCell(cellCount++).setCellValue("등록일자");

				// 스타일 적용
				for (int e = 0; e <= columnCnt; e++) {
					row.getCell(e).setCellStyle(headStyle);
				}

				// 데이터 Cell 생성
				for (EM_COMPANY_MASTER_OMS cmo : channelList) {
												 
					cellCount = 0;

					row = sheet.createRow(rowCount++);

					row.setHeight((short) 350);

					row.createCell(cellCount++).setCellValue(cmo.getRN());
					row.createCell(cellCount++).setCellValue(cmo.getCPN_CD()); // 데이터를 가져와 입력
					row.createCell(cellCount++).setCellValue(cmo.getCPN_NM());
					row.createCell(cellCount++).setCellValue(cmo.getCPN_STATE());
					row.createCell(cellCount++).setCellValue(cmo.getCPN_BIZ_NO());
					row.createCell(cellCount++).setCellValue(cmo.getCEO_NM());
					row.createCell(cellCount++).setCellValue(cmo.getCPN_ADDR_DT());
					row.createCell(cellCount++).setCellValue(cmo.getCPN_TEL());
					row.createCell(cellCount++).setCellValue(cmo.getMNG_NM());
					row.createCell(cellCount++).setCellValue(cmo.getMNG_HPNO());
					row.createCell(cellCount++).setCellValue(cmo.getEMAIL());
					row.createCell(cellCount++).setCellValue(cmo.getIN_APP());
					row.createCell(cellCount++).setCellValue(CommonFunc.unixTimeFormat(cmo.getREG_DATE()));
					// 스타일 적용
					for (int e = 0; e <= columnCnt; e++) {
						row.getCell(e).setCellStyle(bodyStyle);
					}
				}

				// 컨텐츠 타입과 파일명 지정

				Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다

				// 년월일시분초 14자리 포멧
				SimpleDateFormat simple_format = new SimpleDateFormat("yyyyMMdd_HHmmss");

				String getNowDatetime = simple_format.format(date_now);

				String fileName;
				if(paramMap.get("cpntype").equals("ORDER")) {
					fileName = "EMART24_OMS_채널리스트_" + getNowDatetime + ".xlsx";
				}else {
					fileName = "EMART24_OMS_배달대행사리스트_" + getNowDatetime + ".xlsx";
				}
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

				return response;
			}
			
	//중계 서비스  월별 시퀀스 SELECT
	public int ExcelUpload_RELAY_STARTINDEX(String month) {
		return dao.ExcelUpload_RELAY_STARTINDEX(month);
	}
	
	//요기요 배달  월별 시퀀스 SELECT
	public int ExcelUpload_YGYD_STARTINDEX(String month) {
		return dao.ExcelUpload_YGYD_STARTINDEX(month);
	}
	
	//요기요 픽업 월별 시퀀스 SELECT
	public int ExcelUpload_YGYP_STARTINDEX(String month) {
		return dao.ExcelUpload_YGYP_STARTINDEX(month);
	}
	//카카오  월별 시퀀스 SELECT
	public int ExcelUpload_KAKAO_STARTINDEX(String month) {
		return dao.ExcelUpload_KAKAO_STARTINDEX(month);
	}
	//네이버  월별 시퀀스 SELECT
	public int ExcelUpload_NAVER_STARTINDEX(String month) {
		return dao.ExcelUpload_NAVER_STARTINDEX(month);
	}
	//KIS 월별 시퀀스 SELECT
	public int ExcelUpload_KIS_STARTINDEX(String month) {
		return dao.ExcelUpload_KIS_STARTINDEX(month);
	}
	//배민 월별 시퀀스 SELECT
	public int ExcelUpload_BAEMIN_STARTINDEX(String month) {
		return dao.ExcelUpload_BAEMIN_STARTINDEX(month);
	}
	//이마트24 월별 시퀀스 SELECT
	public int ExcelUpload_EMART24_STARTINDEX(String month) {
		return dao.ExcelUpload_EMART24_STARTINDEX(month);
	}
	// 부릉  월별 시퀀스 SELECT
	public int ExcelUpload_VROONG_STARTINDEX(String month) {
		return dao.ExcelUpload_VROONG_STARTINDEX(month);
	}
	// 바로고 월별 시퀀스 SELECT
	public int ExcelUpload_BAROGO_STARTINDEX(String month) {
		return dao.ExcelUpload_BAROGO_STARTINDEX(month);
	}
	// Logiall(생각대로) 월별 시퀀스 SELECT
	public int ExcelUpload_LOGIALL_STARTINDEX(String month) {
		return dao.ExcelUpload_LOGIALL_STARTINDEX(month);
	}
	// Dealver(딜버) 월별 시퀀스 SELECT
	public int ExcelUpload_DEALVER_STARTINDEX(String month) {
		return dao.ExcelUpload_DEALVER_STARTINDEX(month);
	}
	// SPIDOR 스파이더 월별 시퀀스 SELECT
	public int ExcelUpload_SPIDOR_STARTINDEX(String month) {
		return dao.ExcelUpload_SPIDOR_STARTINDEX(month);
	}
	// 체인로지스 월별 시퀀스 SELECT
	public int ExcelUpload_CHAIN_STARTINDEX(String month) {
		return dao.ExcelUpload_CHAIN_STARTINDEX(month);
	}
	
	//
	@SuppressWarnings("unchecked")
	public HttpServletResponse ExcelUpload_RELAY(HashMap<String, Object> paramMap, MultipartHttpServletRequest request,
				HttpServletResponse response) {	
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		MultipartFile excelFile = request.getFile("excelFile");
		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택 해 주세요.");
		}
		Date createDate = new Date();
		String year = (new SimpleDateFormat("yyyy").format(createDate)); // 년도
		String month = (new SimpleDateFormat("MM").format(createDate)); // 월
		String day = (new SimpleDateFormat("dd").format(createDate)); // 일

		String path = request.getSession().getServletContext().getRealPath("resources/ExcelUpload/") + "/" + year + "/"
				+ month + "/" + day + "/ExcelUpload/RELAY/";

		File folder = new File(path);

		if (!folder.exists()) {
			try {
				folder.mkdirs(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		}

		// 엑셀 업로드 파일 지정
		File destFile = new File(path + excelFile.getOriginalFilename());

		try {
			excelFile.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		// Service 단에서 가져온 코드
		ExcelReadOption excelReadOption = new ExcelReadOption();
		// 파일 경로 추가
		excelReadOption.setFilePath(destFile.getAbsolutePath());

		// 추출 할 컬럼 명 추가
		excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
				"Q", "R", "S", "T", "U", "V", "W");

				 
		// 추출 시작 행
		excelReadOption.setStartRow(6);

		HashMap<String, Object> excelRead = ExcelReadCalc_RELAY.read(excelReadOption, paramMap);

		// Insert 보내기
		try {

			if (!ObjectUtils.isEmpty(excelRead.get("S"))) {
				
				System.out.print(" 업로드데이타" + excelRead.get("S"));
				
				dao.ExcelUpload_RELAY((List<Map<String, Object>>) excelRead.get("S"));

			}
			System.out.println("======= 업로드 완료 =======");

			HashMap<String, Object> resMap = new HashMap<String, Object>();

			resMap.put("fileName", excelFile.getOriginalFilename());

			if (!ObjectUtils.isEmpty(excelRead.get("F"))) {
				resMap.put("excel", excelRead.get("F"));
			}
			resMap.put("success", ((List<Map<String, String>>) excelRead.get("S")).size());
			resMap.put("fail", ((List<Map<String, String>>) excelRead.get("F")).size());

			String json = new Gson().toJson(resMap);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

			response.getWriter().flush();

			return response;

		} catch (Exception e) {
			if (destFile.exists()) {
				if (destFile.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
				}
			}

			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));

			System.out.println("SETTLE RELAY FEE UPLOAD ERROR ===>" + e.getMessage());
			try {
				String errCd = matcher.group(1).trim();

				if (matcher.find()) {
					response.setStatus(400);
					response.getWriter().write(errCd);
					System.err.println("ERROR ===>" + errCd);
				} else {
					response.setStatus(400);
					response.getWriter().write(2);
					System.err.println("ERROR ===>" + errCd);
				}
			} catch (IOException e1) {
				System.out.println("response IOException ===> " + e1.getStackTrace());
			}
		}

		return response;
		 
	}
	

	// 요기요 배달
	@SuppressWarnings("unchecked")
	public HttpServletResponse ExcelUpload_YGYD(HashMap<String, Object> paramMap, MultipartHttpServletRequest request,
			HttpServletResponse response) {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		MultipartFile excelFile = request.getFile("excelFile");

		System.out.println("엑셀 파일 업로드 컨트롤러");

		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택 해 주세요.");
		}

		// 현재 날짜인 경로 폴더 생성
		Date createDate = new Date();
		String year = (new SimpleDateFormat("yyyy").format(createDate)); // 년도
		String month = (new SimpleDateFormat("MM").format(createDate)); // 월
		String day = (new SimpleDateFormat("dd").format(createDate)); // 일

		String path = request.getSession().getServletContext().getRealPath("resources/ExcelUpload/") + "/" + year + "/"
				+ month + "/" + day + "/ExcelUpload/YOGIYO_DV/";

		File folder = new File(path);

		if (!folder.exists()) {
			try {
				folder.mkdirs(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		}

		// 엑셀 업로드 파일 지정
		File destFile = new File(path + excelFile.getOriginalFilename());

		try {
			excelFile.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		// Service 단에서 가져온 코드
		ExcelReadOption excelReadOption = new ExcelReadOption();
		// 파일 경로 추가
		excelReadOption.setFilePath(destFile.getAbsolutePath());

		// 추출 할 컬럼 명 추가
		excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
				"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI",
				"AJ","AK","AL","AM","AN","AO");

		// 추출 시작 행
		excelReadOption.setStartRow(2);

		HashMap<String, Object> excelRead = ExcelReadCalc_YGYD.read(excelReadOption, paramMap);

		// Insert 보내기
		try {

			if (!ObjectUtils.isEmpty(excelRead.get("S"))) {

				dao.ExcelUpload_YGYD((List<Map<String, Object>>) excelRead.get("S"));

			}
			System.out.println("======= 업로드 완료 =======");

			HashMap<String, Object> resMap = new HashMap<String, Object>();

			resMap.put("fileName", excelFile.getOriginalFilename());

			if (!ObjectUtils.isEmpty(excelRead.get("F"))) {
				resMap.put("excel", excelRead.get("F"));
			}
			resMap.put("success", ((List<Map<String, String>>) excelRead.get("S")).size());
			resMap.put("fail", ((List<Map<String, String>>) excelRead.get("F")).size());

			String json = new Gson().toJson(resMap);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

			response.getWriter().flush();

			return response;

		} catch (Exception e) {
			if (destFile.exists()) {
				if (destFile.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
				}
			}

			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));

			System.out.println("YOGIYO_Delivery UPLOAD ERROR ===>" + e.getMessage());
			try {
				String errCd = matcher.group(1).trim();

				if (matcher.find()) {
					response.setStatus(400);
					response.getWriter().write(errCd);
					System.err.println("ERROR ===>" + errCd);
				} else {
					response.setStatus(400);
					response.getWriter().write(2);
					System.err.println("ERROR ===>" + errCd);
				}
			} catch (IOException e1) {
				System.out.println("response IOException ===> " + e1.getStackTrace());
			}
		}

		return response;
	}

	// 요기요 픽업
	@SuppressWarnings("unchecked")
	public HttpServletResponse ExcelUpload_YGYP(HashMap<String, Object> paramMap, MultipartHttpServletRequest request,
			HttpServletResponse response) {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		MultipartFile excelFile = request.getFile("excelFile");

		System.out.println("엑셀 파일 업로드 컨트롤러");

		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택 해 주세요.");
		}

		// 현재 날짜인 경로 폴더 생성
		Date createDate = new Date();
		String year = (new SimpleDateFormat("yyyy").format(createDate)); // 년도
		String month = (new SimpleDateFormat("MM").format(createDate)); // 월
		String day = (new SimpleDateFormat("dd").format(createDate)); // 일

		String path = request.getSession().getServletContext().getRealPath("resources/ExcelUpload/") + "/" + year + "/"
				+ month + "/" + day + "/ExcelUpload/YOGIYO_DV/";

		File folder = new File(path);

		if (!folder.exists()) {
			try {
				folder.mkdirs(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		}

		// 엑셀 업로드 파일 지정
		File destFile = new File(path + excelFile.getOriginalFilename());

		try {
			excelFile.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		// Service 단에서 가져온 코드
		ExcelReadOption excelReadOption = new ExcelReadOption();
		// 파일 경로 추가
		excelReadOption.setFilePath(destFile.getAbsolutePath());

		// 추출 할 컬럼 명 추가
		excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
				"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI",
				"AJ","AK","AL","AM","AN","AO");

		// 추출 시작 행
		excelReadOption.setStartRow(2);

		HashMap<String, Object> excelRead = ExcelReadCalc_YGYP.read(excelReadOption, paramMap);

		// Insert 보내기
		try {

			if (!ObjectUtils.isEmpty(excelRead.get("S"))) {

				dao.ExcelUpload_YGYP((List<Map<String, Object>>) excelRead.get("S"));

			}
			System.out.println("======= 업로드 완료 =======");

			HashMap<String, Object> resMap = new HashMap<String, Object>();

			resMap.put("fileName", excelFile.getOriginalFilename());

			if (!ObjectUtils.isEmpty(excelRead.get("F"))) {
				resMap.put("excel", excelRead.get("F"));
			}
			resMap.put("success", ((List<Map<String, String>>) excelRead.get("S")).size());
			resMap.put("fail", ((List<Map<String, String>>) excelRead.get("F")).size());

			String json = new Gson().toJson(resMap);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

			response.getWriter().flush();

			return response;

		} catch (Exception e) {
			if (destFile.exists()) {
				if (destFile.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
				}
			}

			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));

			System.out.println("YOGIYO_PICKUP UPLOAD ERROR ===>" + e.getMessage());
			try {
				String errCd = matcher.group(1).trim();

				if (matcher.find()) {
					response.setStatus(400);
					response.getWriter().write(errCd);
					System.err.println("ERROR ===>" + errCd);
				} else {
					response.setStatus(400);
					response.getWriter().write(2);
					System.err.println("ERROR ===>" + errCd);
				}
			} catch (IOException e1) {
				System.out.println("response IOException ===> " + e1.getStackTrace());
			}
		}

		return response;
	}

	// 카카오
	@SuppressWarnings("unchecked")
	public HttpServletResponse ExcelUpload_KAKAO(HashMap<String, Object> paramMap, MultipartHttpServletRequest request,
			HttpServletResponse response) {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		MultipartFile excelFile = request.getFile("excelFile");

		System.out.println("엑셀 파일 업로드 컨트롤러");

		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택 해 주세요.");
		}

		// 현재 날짜인 경로 폴더 생성
		Date createDate = new Date();
		String year = (new SimpleDateFormat("yyyy").format(createDate)); // 년도
		String month = (new SimpleDateFormat("MM").format(createDate)); // 월
		String day = (new SimpleDateFormat("dd").format(createDate)); // 일

		String path = request.getSession().getServletContext().getRealPath("resources/ExcelUpload/") + "/" + year + "/"
				+ month + "/" + day + "/ExcelUpload/KAKAO/";

		File folder = new File(path);

		if (!folder.exists()) {
			try {
				folder.mkdirs(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		}

		// 엑셀 업로드 파일 지정
		File destFile = new File(path + excelFile.getOriginalFilename());

		try {
			excelFile.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		// Service 단에서 가져온 코드
		ExcelReadOption excelReadOption = new ExcelReadOption();
		// 파일 경로 추가
		excelReadOption.setFilePath(destFile.getAbsolutePath());

		// 추출 할 컬럼 명 추가
		excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
				"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI");

		// 추출 시작 행
		excelReadOption.setStartRow(2);

		HashMap<String, Object> excelRead = ExcelReadCalc_KAKAO.read(excelReadOption, paramMap);

		// Insert 보내기
		try {

			if (!ObjectUtils.isEmpty(excelRead.get("S"))) {

				dao.ExcelUpload_KAKAO((List<Map<String, Object>>) excelRead.get("S"));

			}
			System.out.println("======= 업로드 완료 =======");

			HashMap<String, Object> resMap = new HashMap<String, Object>();

			resMap.put("fileName", excelFile.getOriginalFilename());

			if (!ObjectUtils.isEmpty(excelRead.get("F"))) {
				resMap.put("excel", excelRead.get("F"));
			}
			resMap.put("success", ((List<Map<String, String>>) excelRead.get("S")).size());
			resMap.put("fail", ((List<Map<String, String>>) excelRead.get("F")).size());

			String json = new Gson().toJson(resMap);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

			response.getWriter().flush();

			return response;

		} catch (Exception e) {
			if (destFile.exists()) {
				if (destFile.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
				}
			}

			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));

			System.out.println("KAKAO UPLOAD ERROR ===>" + e.getMessage());
			try {
				String errCd = matcher.group(1).trim();

				if (matcher.find()) {
					response.setStatus(400);
					response.getWriter().write(errCd);
					System.err.println("ERROR ===>" + errCd);
				} else {
					response.setStatus(400);
					response.getWriter().write(2);
					System.err.println("ERROR ===>" + errCd);
				}
			} catch (IOException e1) {
				System.out.println("response IOException ===> " + e1.getStackTrace());
			}
		}
		
		

		return response;
	}

	// 네이버
	@SuppressWarnings("unchecked")
	public HttpServletResponse ExcelUpload_NAVER(HashMap<String, Object> paramMap, MultipartHttpServletRequest request,
			HttpServletResponse response) {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		MultipartFile excelFile = request.getFile("excelFile");

		System.out.println("엑셀 파일 업로드 컨트롤러");

		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택 해 주세요.");
		}

		// 현재 날짜인 경로 폴더 생성
		Date createDate = new Date();
		String year = (new SimpleDateFormat("yyyy").format(createDate)); // 년도
		String month = (new SimpleDateFormat("MM").format(createDate)); // 월
		String day = (new SimpleDateFormat("dd").format(createDate)); // 일

		String path = request.getSession().getServletContext().getRealPath("resources/ExcelUpload/") + "/" + year + "/"
				+ month + "/" + day + "/ExcelUpload/NAVER/";

		File folder = new File(path);

		if (!folder.exists()) {
			try {
				folder.mkdirs(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		}

		// 엑셀 업로드 파일 지정
		File destFile = new File(path + excelFile.getOriginalFilename());

		try {
			excelFile.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		// Service 단에서 가져온 코드
		ExcelReadOption excelReadOption = new ExcelReadOption();
		// 파일 경로 추가
		excelReadOption.setFilePath(destFile.getAbsolutePath());

		// 추출 할 컬럼 명 추가
		excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O","P");

		// 추출 시작 행
		excelReadOption.setStartRow(2);

		HashMap<String, Object> excelRead = ExcelReadCalc_NAVER.read(excelReadOption, paramMap);

		// Insert 보내기
		try {

			if (!ObjectUtils.isEmpty(excelRead.get("S"))) {

				dao.ExcelUpload_NAVER((List<Map<String, Object>>) excelRead.get("S"));

			}
			System.out.println("======= 업로드 완료 =======");

			HashMap<String, Object> resMap = new HashMap<String, Object>();

			resMap.put("fileName", excelFile.getOriginalFilename());

			if (!ObjectUtils.isEmpty(excelRead.get("F"))) {
				resMap.put("excel", excelRead.get("F"));
			}
			resMap.put("success", ((List<Map<String, String>>) excelRead.get("S")).size());
			resMap.put("fail", ((List<Map<String, String>>) excelRead.get("F")).size());

			String json = new Gson().toJson(resMap);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

			response.getWriter().flush();

			return response;

		} catch (Exception e) {
			if (destFile.exists()) {
				if (destFile.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
				}
			}

			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));

			System.out.println("NAVER UPLOAD ERROR ===>" + e.getMessage());
			try {
				String errCd = matcher.group(1).trim();

				if (matcher.find()) {
					response.setStatus(400);
					response.getWriter().write(errCd);
					System.err.println("ERROR ===>" + errCd);
				} else {
					response.setStatus(400);
					response.getWriter().write(2);
					System.err.println("ERROR ===>" + errCd);
				}
			} catch (IOException e1) {
				System.out.println("response IOException ===> " + e1.getStackTrace());
			}
		}

		return response;
	}

	// KIS
	@SuppressWarnings("unchecked")
	public HttpServletResponse ExcelUpload_KIS(HashMap<String, Object> paramMap, MultipartHttpServletRequest request,
			HttpServletResponse response) {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		MultipartFile excelFile = request.getFile("excelFile");

		System.out.println("엑셀 파일 업로드 컨트롤러");

		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택 해 주세요.");
		}

		// 현재 날짜인 경로 폴더 생성
		Date createDate = new Date();
		String year = (new SimpleDateFormat("yyyy").format(createDate)); // 년도
		String month = (new SimpleDateFormat("MM").format(createDate)); // 월
		String day = (new SimpleDateFormat("dd").format(createDate)); // 일

		String path = request.getSession().getServletContext().getRealPath("resources/ExcelUpload/") + "/" + year + "/"
				+ month + "/" + day + "/ExcelUpload/KIS/";

		File folder = new File(path);

		if (!folder.exists()) {
			try {
				folder.mkdirs(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		}

		// 엑셀 업로드 파일 지정
		File destFile = new File(path + excelFile.getOriginalFilename());

		try {
			excelFile.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		// Service 단에서 가져온 코드
		ExcelReadOption excelReadOption = new ExcelReadOption();
		// 파일 경로 추가
		excelReadOption.setFilePath(destFile.getAbsolutePath());

		// 추출 할 컬럼 명 추가
		excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O");

		// 추출 시작 행
		excelReadOption.setStartRow(2);

		HashMap<String, Object> excelRead = ExcelReadCalc_KIS.read(excelReadOption, paramMap);

		// Insert 보내기
		try {

			if (!ObjectUtils.isEmpty(excelRead.get("S"))) {

				dao.ExcelUpload_KIS((List<Map<String, Object>>) excelRead.get("S"));

			}
			System.out.println("======= 업로드 완료 =======");

			HashMap<String, Object> resMap = new HashMap<String, Object>();

			resMap.put("fileName", excelFile.getOriginalFilename());

			if (!ObjectUtils.isEmpty(excelRead.get("F"))) {
				resMap.put("excel", excelRead.get("F"));
			}
			resMap.put("success", ((List<Map<String, String>>) excelRead.get("S")).size());
			resMap.put("fail", ((List<Map<String, String>>) excelRead.get("F")).size());

			String json = new Gson().toJson(resMap);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

			response.getWriter().flush();

			return response;

		} catch (Exception e) {
			if (destFile.exists()) {
				if (destFile.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
				}
			}

			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));

			System.out.println("KIS UPLOAD ERROR ===>" + e.getMessage());
			try {
				String errCd = matcher.group(1).trim();

				if (matcher.find()) {
					response.setStatus(400);
					response.getWriter().write(errCd);
					System.err.println("ERROR ===>" + errCd);
				} else {
					response.setStatus(400);
					response.getWriter().write(2);
					System.err.println("ERROR ===>" + errCd);
				}
			} catch (IOException e1) {
				System.out.println("response IOException ===> " + e1.getStackTrace());
			}
		}

		return response;
	}
	
	// 배달의민족
	@SuppressWarnings("unchecked")
	public HttpServletResponse ExcelUpload_BAEMIN(HashMap<String, Object> paramMap, MultipartHttpServletRequest request,
			HttpServletResponse response) {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		MultipartFile excelFile = request.getFile("excelFile");
		
		System.out.println("엑셀 파일 업로드 컨트롤러");
		
		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택 해 주세요.");
		}
		
		// 현재 날짜인 경로 폴더 생성
		Date createDate = new Date();
		String year = (new SimpleDateFormat("yyyy").format(createDate)); // 년도
		String month = (new SimpleDateFormat("MM").format(createDate)); // 월
		String day = (new SimpleDateFormat("dd").format(createDate)); // 일
		
		String path = request.getSession().getServletContext().getRealPath("resources/ExcelUpload/") + "/" + year + "/"
				+ month + "/" + day + "/ExcelUpload/BAEMIN/";
		
		File folder = new File(path);
		
		if (!folder.exists()) {
			try {
				folder.mkdirs(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		
		// 엑셀 업로드 파일 지정
		File destFile = new File(path + excelFile.getOriginalFilename());
		
		try {
			excelFile.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		// Service 단에서 가져온 코드
		ExcelReadOption excelReadOption = new ExcelReadOption();
		// 파일 경로 추가
		excelReadOption.setFilePath(destFile.getAbsolutePath());
		
		// 추출 할 컬럼 명 추가
		excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
				"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF");
		
		// 추출 시작 행
		excelReadOption.setStartRow(4);
		
		HashMap<String, Object> excelRead = ExcelReadCalc_BAEMIN.read(excelReadOption, paramMap);
		
		// Insert 보내기
		try {
			
			if (!ObjectUtils.isEmpty(excelRead.get("S"))) {
				
				dao.ExcelUpload_BAEMIN((List<Map<String, Object>>) excelRead.get("S"));
				
			}
			System.out.println("======= 업로드 완료 =======");
			
			HashMap<String, Object> resMap = new HashMap<String, Object>();
			
			resMap.put("fileName", excelFile.getOriginalFilename());
			
			if (!ObjectUtils.isEmpty(excelRead.get("F"))) {
				resMap.put("excel", excelRead.get("F"));
			}
			resMap.put("success", ((List<Map<String, String>>) excelRead.get("S")).size());
			resMap.put("fail", ((List<Map<String, String>>) excelRead.get("F")).size());
			
			String json = new Gson().toJson(resMap);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			
			response.getWriter().flush();
			
			return response;
			
		} catch (Exception e) {
			if (destFile.exists()) {
				if (destFile.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
				}
			}
			
			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));
			
			System.out.println("BaeMin UPLOAD ERROR ===>" + e.getMessage());
			try {
				String errCd = matcher.group(1).trim();
				
				if (matcher.find()) {
					response.setStatus(400);
					response.getWriter().write(errCd);
					System.err.println("ERROR ===>" + errCd);
				} else {
					response.setStatus(400);
					response.getWriter().write(2);
					System.err.println("ERROR ===>" + errCd);
				}
			} catch (IOException e1) {
				System.out.println("response IOException ===> " + e1.getStackTrace());
			}
		}
		
		return response;
	}
	
	// 이마트24앱
	@SuppressWarnings("unchecked")
	public HttpServletResponse ExcelUpload_EMART24(HashMap<String, Object> paramMap, MultipartHttpServletRequest request,
			HttpServletResponse response) {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		MultipartFile excelFile = request.getFile("excelFile");

		

		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택 해 주세요.");
		}
	
		// 현재 날짜인 경로 폴더 생성
		Date createDate = new Date();
		String year = (new SimpleDateFormat("yyyy").format(createDate)); // 년도
		String month = (new SimpleDateFormat("MM").format(createDate)); // 월
		String day = (new SimpleDateFormat("dd").format(createDate)); // 일

		String path = request.getSession().getServletContext().getRealPath("resources/ExcelUpload/") + "/" + year + "/"
				+ month + "/" + day + "/ExcelUpload/EMART24/";

		File folder = new File(path);

		if (!folder.exists()) {
			try {
				folder.mkdirs(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		}

		// 엑셀 업로드 파일 지정
		File destFile = new File(path + excelFile.getOriginalFilename());

		try {
			excelFile.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		// Service 단에서 가져온 코드
		ExcelReadOption excelReadOption = new ExcelReadOption();
		// 파일 경로 추가
		excelReadOption.setFilePath(destFile.getAbsolutePath());

		// 추출 할 컬럼 명 추가
		excelReadOption.setOutputColumns( "B", "C", "D", "E", "F", "G", "H", "I", "J", "K","L");

		// 추출 시작 행
		excelReadOption.setStartRow(4);

		HashMap<String, Object> excelRead = ExcelReadCalc_EMART24.read(excelReadOption, paramMap);
		
		// Insert 보내기
		try {

			if (!ObjectUtils.isEmpty(excelRead.get("S"))) {

				dao.ExcelUpload_EMART24((List<Map<String, Object>>) excelRead.get("S"));

			}
			System.out.println("======= 업로드 완료 =======");

			HashMap<String, Object> resMap = new HashMap<String, Object>();

			resMap.put("fileName", excelFile.getOriginalFilename());

			if (!ObjectUtils.isEmpty(excelRead.get("F"))) {
				resMap.put("excel", excelRead.get("F"));
			}
			resMap.put("success", ((List<Map<String, String>>) excelRead.get("S")).size());
			resMap.put("fail", ((List<Map<String, String>>) excelRead.get("F")).size());

			String json = new Gson().toJson(resMap);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

			response.getWriter().flush();

			return response;

		} catch (Exception e) {
			if (destFile.exists()) {
				if (destFile.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
				}
			}

			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));

			System.out.println("EMART24 UPLOAD ERROR ===>" + e.getMessage());
			try {
				String errCd = matcher.group(1).trim();

				if (matcher.find()) {
					response.setStatus(400);
					response.getWriter().write(errCd);
					System.err.println("ERROR ===>" + errCd);
				} else {
					response.setStatus(400);
					response.getWriter().write(2);
					System.err.println("ERROR ===>" + errCd);
				}
			} catch (IOException e1) {
				System.out.println("response IOException ===> " + e1.getStackTrace());
			}
		}

		return response;
	}
	

	// 부릉
	@SuppressWarnings("unchecked")
	public HttpServletResponse ExcelUpload_VROONG(HashMap<String, Object> paramMap, MultipartHttpServletRequest request,
			HttpServletResponse response) {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		MultipartFile excelFile = request.getFile("excelFile");

		System.out.println("엑셀 파일 업로드 컨트롤러");

		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택 해 주세요.");
		}

		// 현재 날짜인 경로 폴더 생성
		Date createDate = new Date();
		String year = (new SimpleDateFormat("yyyy").format(createDate)); // 년도
		String month = (new SimpleDateFormat("MM").format(createDate)); // 월
		String day = (new SimpleDateFormat("dd").format(createDate)); // 일

		String path = request.getSession().getServletContext().getRealPath("resources/ExcelUpload/") + "/" + year + "/"
				+ month + "/" + day + "/ExcelUpload/VROONG/";

		File folder = new File(path);

		if (!folder.exists()) {
			try {
				folder.mkdirs(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		}

		// 엑셀 업로드 파일 지정
		File destFile = new File(path + excelFile.getOriginalFilename());

		try {
			excelFile.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		// Service 단에서 가져온 코드
		ExcelReadOption excelReadOption = new ExcelReadOption();
		// 파일 경로 추가
		excelReadOption.setFilePath(destFile.getAbsolutePath());

		// 추출 할 컬럼 명 추가
		excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
				"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI",
				"AJ", "AK", "AL", "AM", "AN", "AO", "AP","AQ");

		// 추출 시작 행
		excelReadOption.setStartRow(2);

		HashMap<String, List<Map<String, Object>>> excelRead = ExcelReadCalc_VROONG.read(excelReadOption, paramMap);

		// Insert 보내기
		try {

			if (!ObjectUtils.isEmpty(excelRead.get("S"))) {

				dao.ExcelUpload_VROONG((List<Map<String, Object>>) excelRead.get("S"));

			}
			System.out.println("======= 업로드 완료 =======");

			HashMap<String, Object> resMap = new HashMap<String, Object>();

			resMap.put("fileName", excelFile.getOriginalFilename());

			if (!ObjectUtils.isEmpty(excelRead.get("F"))) {
				resMap.put("excel", excelRead.get("F"));
			}
			resMap.put("success", ( excelRead.get("S")).size());
			resMap.put("fail", ( excelRead.get("F")).size());

			String json = new Gson().toJson(resMap);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

			response.getWriter().flush();

			return response;

		} catch (Exception e) {
			if (destFile.exists()) {
				if (destFile.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
				}
			}

			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));

			System.out.println("VROONG UPLOAD ERROR ===>" + e.getMessage());
			try {
				String errCd = matcher.group(1).trim();

				if (matcher.find()) {
					response.setStatus(400);
					response.getWriter().write(errCd);
					System.err.println("ERROR ===>" + errCd);
				} else {
					response.setStatus(400);
					response.getWriter().write(2);
					System.err.println("ERROR ===>" + errCd);
				}
			} catch (IOException e1) {
				System.out.println("response IOException ===> " + e1.getStackTrace());
			}
		}

		return response;

	}

	// 바로고
	@SuppressWarnings("unchecked")
	public HttpServletResponse ExcelUpload_BAROGO(HashMap<String, Object> paramMap, MultipartHttpServletRequest request,
			HttpServletResponse response) {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		MultipartFile excelFile = request.getFile("excelFile");

		System.out.println("엑셀 파일 업로드 컨트롤러");

		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택 해 주세요.");
		}

		// 현재 날짜인 경로 폴더 생성
		Date createDate = new Date();
		String year = (new SimpleDateFormat("yyyy").format(createDate)); // 년도
		String month = (new SimpleDateFormat("MM").format(createDate)); // 월
		String day = (new SimpleDateFormat("dd").format(createDate)); // 일

		String path = request.getSession().getServletContext().getRealPath("resources/ExcelUpload/") + "/" + year + "/"
				+ month + "/" + day + "/ExcelUpload/BAROGO/";

		File folder = new File(path);

		if (!folder.exists()) {
			try {
				folder.mkdirs(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		}

		// 엑셀 업로드 파일 지정
		File destFile = new File(path + excelFile.getOriginalFilename());

		try {
			excelFile.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		// Service 단에서 가져온 코드
		ExcelReadOption excelReadOption = new ExcelReadOption();
		// 파일 경로 추가
		excelReadOption.setFilePath(destFile.getAbsolutePath());

		// 추출 할 컬럼 명 추가
		excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
				"Q", "R", "S", "T","U");

		// 추출 시작 행
		excelReadOption.setStartRow(6);

		HashMap<String, List<Map<String, Object>>> excelRead = ExcelReadCalc_BAROGO.read(excelReadOption, paramMap);

		// Insert 보내기
		try {

			if (!ObjectUtils.isEmpty(excelRead.get("S"))) {

				dao.ExcelUpload_BAROGO((List<Map<String, Object>>) excelRead.get("S"));

			}
			System.out.println("======= 업로드 완료 =======");

			HashMap<String, Object> resMap = new HashMap<String, Object>();

			resMap.put("fileName", excelFile.getOriginalFilename());

			if (!ObjectUtils.isEmpty(excelRead.get("F"))) {
				resMap.put("excel", excelRead.get("F"));
			}
			resMap.put("success", ( excelRead.get("S")).size());
			resMap.put("fail", ( excelRead.get("F")).size());

			String json = new Gson().toJson(resMap);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

			response.getWriter().flush();

			return response;

		} catch (Exception e) {
			if (destFile.exists()) {
				if (destFile.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
				}
			}

			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));

			System.out.println("BAROGO UPLOAD ERROR ===>" + e.getMessage());
			try {
				String errCd = matcher.group(1).trim();

				if (matcher.find()) {
					response.setStatus(400);
					response.getWriter().write(errCd);
					System.err.println("ERROR ===>" + errCd);
				} else {
					response.setStatus(400);
					response.getWriter().write(2);
					System.err.println("ERROR ===>" + errCd);
				}
			} catch (Exception e1) {
				System.out.println("response Exception ===> " + e1.getMessage());
			}
		}

		return response;

	}

	// 생각대로(로지올)
	@SuppressWarnings("unchecked")
	public HttpServletResponse ExcelUpload_LOGIALL(HashMap<String, Object> paramMap,
			MultipartHttpServletRequest request, HttpServletResponse response) {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		MultipartFile excelFile = request.getFile("excelFile");

		System.out.println("엑셀 파일 업로드 컨트롤러");
		

		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택 해 주세요.");
		}

		// 현재 날짜인 경로 폴더 생성
		Date createDate = new Date();
		String year = (new SimpleDateFormat("yyyy").format(createDate)); // 년도
		String month = (new SimpleDateFormat("MM").format(createDate)); // 월
		String day = (new SimpleDateFormat("dd").format(createDate)); // 일

		String path = request.getSession().getServletContext().getRealPath("resources/ExcelUpload/") + "/" + year + "/"
				+ month + "/" + day + "/ExcelUpload/LOGIALL/";

		File folder = new File(path);

		if (!folder.exists()) {
			try {
				folder.mkdirs(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		}

		// 엑셀 업로드 파일 지정
		File destFile = new File(path + excelFile.getOriginalFilename());

		try {
			excelFile.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		// Service 단에서 가져온 코드
		ExcelReadOption excelReadOption = new ExcelReadOption();
		// 파일 경로 추가
		excelReadOption.setFilePath(destFile.getAbsolutePath());

		// 추출 할 컬럼 명 추가
		excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M","N");

		// 추출 시작 행
		excelReadOption.setStartRow(3);

		HashMap<String, List<Map<String, Object>>> excelRead = ExcelReadCalc_LOGIALL.read(excelReadOption, paramMap);
		
		// Insert 보내기
		try {

			if (!ObjectUtils.isEmpty(excelRead.get("S"))) {

				dao.ExcelUpload_LOGIALL((List<Map<String, Object>>) excelRead.get("S"));

			}
			System.out.println("======= 업로드 완료 =======");

			HashMap<String, Object> resMap = new HashMap<String, Object>();

			resMap.put("fileName", excelFile.getOriginalFilename());

			if (!ObjectUtils.isEmpty(excelRead.get("F"))) {
				resMap.put("excel", excelRead.get("F"));
				System.out.println("A --------------------- " + excelRead.get("A"));
				System.out.println("B---------------------- " + excelRead.get("B"));
				System.out.println("C---------------------- " + excelRead.get("C"));
				System.out.println("D---------------------- " + excelRead.get("D"));
				System.out.println("E---------------------- " + excelRead.get("E"));
				System.out.println("F---------------------- " + excelRead.get("F"));
				System.out.println("G---------------------- " + excelRead.get("G"));
				System.out.println("H---------------------- " + excelRead.get("H"));
				System.out.println("S---------------------- " + excelRead.get("S"));
				
			}
			resMap.put("success", ( excelRead.get("S")).size());
			resMap.put("fail", ( excelRead.get("F")).size());

			String json = new Gson().toJson(resMap);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

			response.getWriter().flush();
			return response;

		} catch (Exception e) {
			if (destFile.exists()) {
				if (destFile.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
				}
			}

			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));

			System.out.println("LOGIALL UPLOAD ERROR ===>" + e.getMessage());
			try {
				String errCd = matcher.group(1).trim();

				if (matcher.find()) {
					response.setStatus(400);
					response.getWriter().write(errCd);
					System.err.println("ERROR ===>" + errCd);
				} else {
					response.setStatus(400);
					response.getWriter().write(2);
					System.err.println("ERROR ===>" + errCd);
				}
			} catch (IOException e1) {
				System.out.println("response IOException ===> " + e1.getStackTrace());
			}
		}

		return response;

	}

	// 딜버
	@SuppressWarnings("unchecked")
	public HttpServletResponse ExcelUpload_DEALVER(HashMap<String, Object> paramMap,
			MultipartHttpServletRequest request, HttpServletResponse response) {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		MultipartFile excelFile = request.getFile("excelFile");

		System.out.println("엑셀 파일 업로드 컨트롤러");

		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택 해 주세요.");
		}

		// 현재 날짜인 경로 폴더 생성
		Date createDate = new Date();
		String year = (new SimpleDateFormat("yyyy").format(createDate)); // 년도
		String month = (new SimpleDateFormat("MM").format(createDate)); // 월
		String day = (new SimpleDateFormat("dd").format(createDate)); // 일

		String path = request.getSession().getServletContext().getRealPath("resources/ExcelUpload/") + "/" + year + "/"
				+ month + "/" + day + "/ExcelUpload/DEALVER/";

		File folder = new File(path);

		if (!folder.exists()) {
			try {
				folder.mkdirs(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		}

		// 엑셀 업로드 파일 지정
		File destFile = new File(path + excelFile.getOriginalFilename());

		try {
			excelFile.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		// Service 단에서 가져온 코드
		ExcelReadOption excelReadOption = new ExcelReadOption();
		// 파일 경로 추가
		excelReadOption.setFilePath(destFile.getAbsolutePath());

		// 추출 할 컬럼 명 추가
		excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M");

		// 추출 시작 행
		excelReadOption.setStartRow(2);

		HashMap<String, List<Map<String, Object>>> excelRead = ExcelReadCalc_DEALVER.read(excelReadOption, paramMap);

		// Insert 보내기
		try {

			if (!ObjectUtils.isEmpty(excelRead.get("S"))) {

				dao.ExcelUpload_DEALVER((List<Map<String, Object>>) excelRead.get("S"));

			}
			System.out.println("======= 업로드 완료 =======");

			HashMap<String, Object> resMap = new HashMap<String, Object>();

			resMap.put("fileName", excelFile.getOriginalFilename());

			if (!ObjectUtils.isEmpty(excelRead.get("F"))) {
				resMap.put("excel", excelRead.get("F"));
			}
			resMap.put("success", excelRead.get("S").size());
			resMap.put("fail", excelRead.get("F").size());

			String json = new Gson().toJson(resMap);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

			response.getWriter().flush();

			return response;

		} catch (Exception e) {
			if (destFile.exists()) {
				if (destFile.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
				}
			}

			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));

			System.out.println("DEALVER UPLOAD ERROR ===>" + e.getMessage());
			try {
				String errCd = matcher.group(1).trim();

				if (matcher.find()) {
					response.setStatus(400);
					response.getWriter().write(errCd);
					System.err.println("ERROR ===>" + errCd);
				} else {
					response.setStatus(400);
					response.getWriter().write(2);
					System.err.println("ERROR ===>" + errCd);
				}
			} catch (IOException e1) {
				System.out.println("response IOException ===> " + e1.getStackTrace());
			}
		}

		return response;

	}

	// 스파이더
	@SuppressWarnings("unchecked")
	public HttpServletResponse ExcelUpload_SPIDOR(HashMap<String, Object> paramMap, MultipartHttpServletRequest request,
			HttpServletResponse response) {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		MultipartFile excelFile = request.getFile("excelFile");

		System.out.println("엑셀 파일 업로드 컨트롤러");

		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택 해 주세요.");
		}

		// 현재 날짜인 경로 폴더 생성
		Date createDate = new Date();
		String year = (new SimpleDateFormat("yyyy").format(createDate)); // 년도
		String month = (new SimpleDateFormat("MM").format(createDate)); // 월
		String day = (new SimpleDateFormat("dd").format(createDate)); // 일

		String path = request.getSession().getServletContext().getRealPath("resources/ExcelUpload/") + "/" + year + "/"
				+ month + "/" + day + "/ExcelUpload/SPIDOR/";

		File folder = new File(path);

		if (!folder.exists()) {
			try {
				folder.mkdirs(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		}

		// 엑셀 업로드 파일 지정
		File destFile = new File(path + excelFile.getOriginalFilename());

		try {
			excelFile.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		// Service 단에서 가져온 코드
		ExcelReadOption excelReadOption = new ExcelReadOption();
		// 파일 경로 추가
		excelReadOption.setFilePath(destFile.getAbsolutePath());

		// 추출 할 컬럼 명 추가
		excelReadOption.setOutputColumns("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M");

		// 추출 시작 행
		excelReadOption.setStartRow(2);

		HashMap<String, List<Map<String, Object>>> excelRead = ExcelReadCalc_SPIDOR.read(excelReadOption, paramMap);

		// Insert 보내기
		try {

			if (!ObjectUtils.isEmpty(excelRead.get("S"))) {

				dao.ExcelUpload_SPIDOR((List<Map<String, Object>>) excelRead.get("S"));

			}
			System.out.println("======= 업로드 완료 =======");

			HashMap<String, Object> resMap = new HashMap<String, Object>();

			resMap.put("fileName", excelFile.getOriginalFilename());

			if (!ObjectUtils.isEmpty(excelRead.get("F"))) {
				resMap.put("excel", excelRead.get("F"));
			}
			resMap.put("success", excelRead.get("S").size());
			resMap.put("fail", excelRead.get("F").size());

			String json = new Gson().toJson(resMap);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

			response.getWriter().flush();

			return response;

		} catch (Exception e) {
			if (destFile.exists()) {
				if (destFile.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
				}
			}

			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));

			System.out.println("SPIDOR UPLOAD ERROR ===>" + e.getMessage());
			try {
				String errCd = matcher.group(1).trim();

				if (matcher.find()) {
					response.setStatus(400);
					response.getWriter().write(errCd);
					System.err.println("ERROR ===>" + errCd);
				} else {
					response.setStatus(400);
					response.getWriter().write(2);
					System.err.println("ERROR ===>" + errCd);
				}
			} catch (IOException e1) {
				System.out.println("response IOException ===> " + e1.getStackTrace());
			}
		}

		return response;

	}
	
	// 체인로지스
	@SuppressWarnings("unchecked")
	public HttpServletResponse ExcelUpload_CHAIN(HashMap<String, Object> paramMap, MultipartHttpServletRequest request,
			HttpServletResponse response) {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		MultipartFile excelFile = request.getFile("excelFile");
		System.out.println("엑셀 파일 업로드 컨트롤러");		
		if (excelFile == null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택 해 주세요.");
		}
		
		// 현재 날짜인 경로 폴더 생성
		Date createDate = new Date();
		String year = (new SimpleDateFormat("yyyy").format(createDate)); // 년도
		String month = (new SimpleDateFormat("MM").format(createDate)); // 월
		String day = (new SimpleDateFormat("dd").format(createDate)); // 일
		
		String path = request.getSession().getServletContext().getRealPath("resources/ExcelUpload/") + "/" + year + "/"
				+ month + "/" + day + "/ExcelUpload/CHAIN/";
		
		File folder = new File(path);
		if (!folder.exists()) {
			try {
				folder.mkdirs(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
		
		// 엑셀 업로드 파일 지정
		File destFile = new File(path + excelFile.getOriginalFilename());
		try {
			excelFile.transferTo(destFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);		
		}
		


		// Service 단에서 가져온 코드
		ExcelReadOption excelReadOption = new ExcelReadOption();
		// 파일 경로 추가
		excelReadOption.setFilePath(destFile.getAbsolutePath());
		
		// 추출 할 컬럼 명 추가
		excelReadOption.setOutputColumns("A", "B", "C", "D", "E");
		
		// 추출 시작 행
		excelReadOption.setStartRow(2);
		
		HashMap<String, Object> excelRead = ExcelReadCalc_CHAIN.read(excelReadOption, paramMap);
		System.out.println("excelRead : " + excelRead);
		// Insert 보내기
		try {
			
			if (!ObjectUtils.isEmpty(excelRead.get("S"))) {
				
				dao.ExcelUpload_CHAIN((List<Map<String, Object>>) excelRead.get("S"));
				
			}
			System.out.println("======= 업로드 완료 =======");
			
			HashMap<String, Object> resMap = new HashMap<String, Object>();
			
			resMap.put("fileName", excelFile.getOriginalFilename());
			
			if (!ObjectUtils.isEmpty(excelRead.get("F"))) {
				resMap.put("excel", excelRead.get("F"));
			}
			resMap.put("success", ((List<Map<String, String>>) excelRead.get("S")).size());
			resMap.put("fail", ((List<Map<String, String>>) excelRead.get("F")).size());
			
			String json = new Gson().toJson(resMap);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			
			response.getWriter().flush();
			
			return response;
			
		} catch (Exception e) {
			e.printStackTrace();
			if (destFile.exists()) {
				if (destFile.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
				}
			}
			
			final Pattern pattern = Pattern.compile("ORA-(.+?):", Pattern.DOTALL);
			final Matcher matcher = pattern.matcher(ExceptionUtils.getMessage(e));
			
			System.out.println("CHAIN UPLOAD ERROR ===>" + e.getMessage());
			try {
				String errCd = matcher.group(1).trim();
				
				if (matcher.find()) {
					response.setStatus(400);
					response.getWriter().write(errCd);
					System.err.println("ERROR ===>" + errCd);
				} else {
					response.setStatus(400);
					response.getWriter().write(2);
					System.err.println("ERROR ===>" + errCd);
				}
			} catch (IOException e1) {
				System.out.println("response IOException ===> " + e1.getStackTrace());
			}
		}
		
		return response;
		
	}
	
	public HttpServletResponse getExcelDownload_RELAY(List<EM_RELAY_TOTAL_OMS> excelList, boolean type,
			HttpServletResponse response) {

		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

		// 숫자 포멧
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 9;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("중계수수료 정산조회");

		sheet.setColumnWidth(0, 25 * 256);// 정산월
		sheet.setColumnWidth(1, 12 * 256);// 사업장 ID
		sheet.setColumnWidth(2, 12 * 256);// 사업장 명
		sheet.setColumnWidth(3, 25 * 256);// 주문번호
		sheet.setColumnWidth(4, 10 * 256);// 주문일자
		sheet.setColumnWidth(5, 15 * 256);// 배송일자
		sheet.setColumnWidth(6, 15 * 256);// 수수료
		sheet.setColumnWidth(7, 15 * 256);// 비고
		sheet.setColumnWidth(8, 20 * 256);// 주문USER
		sheet.setColumnWidth(9, 20 * 256);// 등록일자
		
	
		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("정산월");
		//row.createCell(cellCount++).setCellValue("정산월SEQ");
		row.createCell(cellCount++).setCellValue("사업장코드");
		row.createCell(cellCount++).setCellValue("사업장명");
		row.createCell(cellCount++).setCellValue("주문번호");
		row.createCell(cellCount++).setCellValue("주문일자");
		row.createCell(cellCount++).setCellValue("배송일자");
		row.createCell(cellCount++).setCellValue("KIS_FEE");
		row.createCell(cellCount++).setCellValue("비고");
		row.createCell(cellCount++).setCellValue("사용자");
		row.createCell(cellCount++).setCellValue("등록일자");
		
	 
		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (EM_RELAY_TOTAL_OMS sydo : excelList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);
			 
			row.createCell(cellCount++).setCellValue(sydo.getSETTLE_MONTH()); // 정산월		
			//row.createCell(cellCount++).setCellValue(sydo.getSET_RELAY_SEQ()); // 정산seq
			row.createCell(cellCount++).setCellValue(sydo.getCPN_CD()); // 사업장코드
			row.createCell(cellCount++).setCellValue(sydo.getCPN_NM()); // 사업장명
			row.createCell(cellCount++).setCellValue(sydo.getORD_ID()); // 주문ID
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sydo.getORD_DATE())); // 오더일자
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sydo.getDELIVERY_DATE())); // 배송일자
			row.createCell(cellCount++).setCellValue(sydo.getKIS_FEE()); //fee /
			row.createCell(cellCount++).setCellValue(sydo.getAPPLY_GUBUN()); //비고
			row.createCell(cellCount++).setCellValue(sydo.getUSER_NM()); // 등록자
			row.createCell(cellCount++).setCellValue((CommonFunc.unixTimeFormat(sydo.getREG_DATE() ) )  ); // 등록일자
			
			// 스타일 적용
			
			for (int e = 0; e <= columnCnt; e++) {
				if (e == 1 || e == 7) {
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

		String fileName;
		if (type) {
			fileName = "중계수수료 정산조회_" + getNowDatetime + ".xlsx";
		} else {
			fileName = "중계수수료 정산조회오류_" + getNowDatetime + ".xlsx";
		}

		OutputStream os = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 클라이언트 측 다운로드
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

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
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
 
	}

	public HttpServletResponse getExcelDownload_YGYD(List<EM_SETTLE_YOGIYO_DV_ORD_OMS> excelList, boolean type,
			HttpServletResponse response) {

		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

		// 숫자 포멧
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 36;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("요기요 정산리스트");

		sheet.setColumnWidth(0, 25 * 256);// 주문일시
		sheet.setColumnWidth(1, 25 * 256);// 환불일시
		sheet.setColumnWidth(2, 12 * 256);// 지급예정 일자
		sheet.setColumnWidth(3, 12 * 256);// 사업자번호
		sheet.setColumnWidth(4, 25 * 256);// 음식점명
		sheet.setColumnWidth(5, 10 * 256);// 매장코드
		sheet.setColumnWidth(6, 15 * 256);// 주문ID
		sheet.setColumnWidth(7, 20 * 256);// 주문번호
		sheet.setColumnWidth(8, 10 * 256);// 상품가 합계
		sheet.setColumnWidth(9, 10 * 256);// 최소주문 추가결제액
		sheet.setColumnWidth(10, 10 * 256);// 배달료
		sheet.setColumnWidth(11, 10 * 256);// 주문 총액
		sheet.setColumnWidth(12, 10 * 256);// 주문 금액 구분 : 온라인 주문
		sheet.setColumnWidth(13, 10 * 256);// 주문 금액 구분 : 현장 주문
		sheet.setColumnWidth(14, 10 * 256);// 레스토랑 자체할인
		sheet.setColumnWidth(15, 10 * 256);// 프로모션 할인금액 : 요기요 부담
		sheet.setColumnWidth(16, 10 * 256);// 프로모션 할인금액 : 프랜차이즈 부담
		sheet.setColumnWidth(17, 10 * 256);// 프로모션 할인금액 : 레스토랑 부담
		sheet.setColumnWidth(18, 10 * 256);// 주문중개이용료 : 주문중개이용료율(+VAT)
		sheet.setColumnWidth(19, 10 * 256);// 주문중개이용료 : 주문중개이용료 총액
		sheet.setColumnWidth(20, 10 * 256);// 결제이용료 : 결제이용료율(+VAT)
		sheet.setColumnWidth(21, 10 * 256);// 결제이용료 : 결제이용료 총액
		sheet.setColumnWidth(22, 10 * 256);// 배달대행이용료 : 배달대행이용료 총액
		sheet.setColumnWidth(23, 10 * 256);// 이용료 전체 합계
		sheet.setColumnWidth(24, 10 * 256);// OD배달료 매출 : 레스토랑 자체펀딩 총액
		sheet.setColumnWidth(25, 10 * 256);// 요타임딜이용료 : 요타임딜이용료 총액
		sheet.setColumnWidth(26, 10 * 256);// 추천광고이용료:추천광고이용료 총액
		sheet.setColumnWidth(27, 10 * 256);// 정산 금액
		sheet.setColumnWidth(28, 10 * 256);// 결제유형
		sheet.setColumnWidth(29, 10 * 256);// 결제유형 상세
		sheet.setColumnWidth(30, 10 * 256);// 결제형태
		sheet.setColumnWidth(31, 10 * 256);// 결제형태 상세
		sheet.setColumnWidth(32, 15 * 256);// 배달 유형(VD/OD)
		sheet.setColumnWidth(33, 10 * 256);// 프랜차이즈 ID
		sheet.setColumnWidth(34, 25 * 256);// 회사명
		sheet.setColumnWidth(35, 15 * 256);// 사업자명
		sheet.setColumnWidth(36, 35 * 256);// 배달주소1

		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("주문일시");
		row.createCell(cellCount++).setCellValue("환불일시");
		row.createCell(cellCount++).setCellValue("지급예정 일자");
		row.createCell(cellCount++).setCellValue("사업자번호");
		row.createCell(cellCount++).setCellValue("음식점명");
		row.createCell(cellCount++).setCellValue("매장코드");
		row.createCell(cellCount++).setCellValue("주문ID");
		row.createCell(cellCount++).setCellValue("주문번호");
		row.createCell(cellCount++).setCellValue("상품가 합계");
		row.createCell(cellCount++).setCellValue("최소주문 추가결제액");
		row.createCell(cellCount++).setCellValue("배달료");
		row.createCell(cellCount++).setCellValue("주문 총액");
		row.createCell(cellCount++).setCellValue("주문 금액 구분 : 온라인 주문");
		row.createCell(cellCount++).setCellValue("주문 금액 구분 : 현장 주문");
		row.createCell(cellCount++).setCellValue("레스토랑 자체할인");
		row.createCell(cellCount++).setCellValue("프로모션 할인금액 : 요기요 부담");
		row.createCell(cellCount++).setCellValue("프로모션 할인금액 : 프랜차이즈 부담");
		row.createCell(cellCount++).setCellValue("프로모션 할인금액 : 레스토랑 부담");
		row.createCell(cellCount++).setCellValue("주문중개이용료 : 주문중개이용료율(+VAT)");
		row.createCell(cellCount++).setCellValue("주문중개이용료 : 주문중개이용료 총액");
		row.createCell(cellCount++).setCellValue("결제이용료 : 결제이용료율(+VAT)");
		row.createCell(cellCount++).setCellValue("결제이용료 : 결제이용료 총액");
		row.createCell(cellCount++).setCellValue("배달대행이용료 : 배달대행이용료 총액");
		row.createCell(cellCount++).setCellValue("이용료 전체 합계");
		row.createCell(cellCount++).setCellValue("OD배달료 매출 : 레스토랑 자체펀딩 총액");
		row.createCell(cellCount++).setCellValue("요타임딜이용료 : 요타임딜이용료 총액");
		row.createCell(cellCount++).setCellValue("추천광고이용료:추천광고이용료 총액");
		row.createCell(cellCount++).setCellValue("정산 금액");
		row.createCell(cellCount++).setCellValue("결제유형");
		row.createCell(cellCount++).setCellValue("결제유형 상세");
		row.createCell(cellCount++).setCellValue("결제형태");
		row.createCell(cellCount++).setCellValue("결제형태 상세");
		row.createCell(cellCount++).setCellValue("배달 유형(VD/OD)");
		row.createCell(cellCount++).setCellValue("프랜차이즈 ID");
		row.createCell(cellCount++).setCellValue("회사명");
		row.createCell(cellCount++).setCellValue("사업자명");
		row.createCell(cellCount++).setCellValue("배달주소1");

		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (EM_SETTLE_YOGIYO_DV_ORD_OMS sydo : excelList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sydo.getORD_TIME())); // 주문일시
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sydo.getREFUND_TIME())); // 환불일시
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sydo.getPAY_EXPECTED_DATE())); // 지급예정 일자
			row.createCell(cellCount++).setCellValue(CommonFunc.biznoFormat(sydo.getBIZ_NO())); // 사업자번호
			row.createCell(cellCount++).setCellValue(sydo.getSTORE_NM()); // 음식점명 /
			row.createCell(cellCount++).setCellValue(sydo.getSTORE_ID()); // 매장코드
			row.createCell(cellCount++).setCellValue(sydo.getORD_ID()); // 주문ID
			row.createCell(cellCount++).setCellValue(sydo.getCH_ORD_NO()); // 주문번호
			row.createCell(cellCount++).setCellValue(sydo.getITEM_SUM_AMT()); // 상품가 합계
			row.createCell(cellCount++).setCellValue(sydo.getMIN_ORD_ADD_PAY()); // 최소주문 추가결제액 /
			row.createCell(cellCount++).setCellValue(sydo.getDELIVER_FEE()); // 배달료
			row.createCell(cellCount++).setCellValue(sydo.getORD_TOT_AMT()); // 주문 총액
			row.createCell(cellCount++).setCellValue(sydo.getORD_ONLINE_AMT()); // 주문 금액 구분 : 온라인 주문
			row.createCell(cellCount++).setCellValue(sydo.getORD_OFFLINE_AMT()); // 주문 금액 구분 : 현장 주문
			row.createCell(cellCount++).setCellValue(sydo.getRESTAURANT_SELF_DIS()); // 레스토랑 자체할인 /
			row.createCell(cellCount++).setCellValue(sydo.getPMT_DIS_YOGIYO_LEVY_AMT()); // 프로모션 할인금액 : 요기요 부담
			row.createCell(cellCount++).setCellValue(sydo.getPMT_DIS_FRANCHISEE_LEVY_AMT()); // 프로모션 할인금액 : 프랜차이즈 부담
			row.createCell(cellCount++).setCellValue(sydo.getPMT_DIS_RESTAURANT_LEVY_AMT()); // 프로모션 할인금액 : 레스토랑 부담
			row.createCell(cellCount++).setCellValue(sydo.getORD_RELAY_FEE_RATE()); // 주문중개이용료 : 주문중개이용료율(+VAT)
			row.createCell(cellCount++).setCellValue(sydo.getORD_RELAY_FEE_TOT_AMT()); // 주문중개이용료 : 주문중개이용료 총액 /
			row.createCell(cellCount++).setCellValue(sydo.getPAY_RELAY_FEE_RATE()); // 결제이용료 : 결제이용료율(+VAT)
			row.createCell(cellCount++).setCellValue(sydo.getPAY_RELAY_FEE_TOT_AMT()); // 결제이용료 : 결제이용료 총액
			row.createCell(cellCount++).setCellValue(sydo.getDELIVERY_AGENT_FEE()); // 배달대행이용료 : 배달대행이용료 총액
			row.createCell(cellCount++).setCellValue(sydo.getTOT_SUM_FEE()); // 이용료 전체 합계
			row.createCell(cellCount++).setCellValue(sydo.getOD_DELI_FEE_REST_SELF_FUND()); // OD배달료 매출 : 레스토랑 자체펀딩 총액 /
			row.createCell(cellCount++).setCellValue(sydo.getYO_TIME_FEE_TOT_YO_TIME_FEE()); // 요타임딜이용료 : 요타임딜이용료 총액
			row.createCell(cellCount++).setCellValue(sydo.getREFERRAL_ADVERTISEMENT_FEE());  //추천광고이용료:추천광고이용료 총액
			row.createCell(cellCount++).setCellValue(sydo.getSETTLE_AMT()); // 정산 금액
			row.createCell(cellCount++).setCellValue(sydo.getPAY_TYPE()); // 결제유형
			row.createCell(cellCount++).setCellValue(sydo.getPAY_TYPE_DETAIL()); // 결제유형 상세 /
			row.createCell(cellCount++).setCellValue(sydo.getPAY_FORM()); // 결제형태
			row.createCell(cellCount++).setCellValue(sydo.getPAY_FORM_DETAIL()); // 결제형태 상세
			row.createCell(cellCount++).setCellValue(sydo.getDELIVERY_TYPE()); // 배달 유형(VD/OD)
			row.createCell(cellCount++).setCellValue(sydo.getFRANCHISEE_ID()); // 프랜차이즈 ID
			row.createCell(cellCount++).setCellValue(sydo.getCPN_NM()); // 회사명 /
			row.createCell(cellCount++).setCellValue(sydo.getBIZ_NM()); // 사업자명
			row.createCell(cellCount++).setCellValue(sydo.getDELIVERY_ADDRESS1()); // 배달주소1

			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (e == 8 || e == 9 || e == 10 || e == 11 || e == 12 || e == 13 || e == 14 || e == 15 || e == 16 || e == 17
						|| e == 18 ||e == 19 || e == 20 || e == 21 || e == 22 || e == 23 || e == 24 || e == 25 || e == 26 || e == 27 || e == 28 || e == 29) {
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

		String fileName;
		if (type) {
			fileName = "EMART24_OMS_요기요배달_정산_" + getNowDatetime + ".xlsx";
		} else {
			fileName = "EMART24_OMS_요기요배달_정산오류_" + getNowDatetime + ".xlsx";
		}

		OutputStream os = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 클라이언트 측 다운로드
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

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
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	} 
	public HttpServletResponse getExcelDownload_YGYP(List<EM_SETTLE_YOGIYO_PU_ORD_OMS> excelList, boolean type,
			HttpServletResponse response) {

		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

		// 숫자 포멧
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 36;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("요기요 정산리스트");

		sheet.setColumnWidth(0, 25 * 256);// 주문일시
		sheet.setColumnWidth(1, 25 * 256);// 환불일시
		sheet.setColumnWidth(2, 12 * 256);// 지급예정 일자
		sheet.setColumnWidth(3, 12 * 256);// 사업자번호
		sheet.setColumnWidth(4, 25 * 256);// 음식점명
		sheet.setColumnWidth(5, 10 * 256);// 매장코드
		sheet.setColumnWidth(6, 15 * 256);// 주문ID
		sheet.setColumnWidth(7, 20 * 256);// 주문번호
		sheet.setColumnWidth(8, 10 * 256);// 상품가 합계
		sheet.setColumnWidth(9, 10 * 256);// 최소주문 추가결제액
		sheet.setColumnWidth(10, 10 * 256);// 배달료
		sheet.setColumnWidth(11, 10 * 256);// 주문 총액
		sheet.setColumnWidth(12, 10 * 256);// 주문 금액 구분 : 온라인 주문
		sheet.setColumnWidth(13, 10 * 256);// 주문 금액 구분 : 현장 주문
		sheet.setColumnWidth(14, 10 * 256);// 레스토랑 자체할인
		sheet.setColumnWidth(15, 10 * 256);// 프로모션 할인금액 : 요기요 부담
		sheet.setColumnWidth(16, 10 * 256);// 프로모션 할인금액 : 프랜차이즈 부담
		sheet.setColumnWidth(17, 10 * 256);// 프로모션 할인금액 : 레스토랑 부담
		sheet.setColumnWidth(18, 10 * 256);// 주문중개이용료 : 주문중개이용료율(+VAT)
		sheet.setColumnWidth(19, 10 * 256);// 주문중개이용료 : 주문중개이용료 총액
		sheet.setColumnWidth(20, 10 * 256);// 결제이용료 : 결제이용료율(+VAT)
		sheet.setColumnWidth(21, 10 * 256);// 결제이용료 : 결제이용료 총액
		sheet.setColumnWidth(22, 10 * 256);// 배달대행이용료 : 배달대행이용료 총액
		sheet.setColumnWidth(23, 10 * 256);// 이용료 전체 합계
		sheet.setColumnWidth(24, 10 * 256);// OD배달료 매출 : 레스토랑 자체펀딩 총액
		sheet.setColumnWidth(25, 10 * 256);// 요타임딜이용료 : 요타임딜이용료 총액
		sheet.setColumnWidth(26, 10 * 256);// 추천광고이용료:추천광고이용료 총액
		sheet.setColumnWidth(27, 10 * 256);// 정산 금액
		sheet.setColumnWidth(28, 10 * 256);// 결제유형
		sheet.setColumnWidth(29, 10 * 256);// 결제유형 상세
		sheet.setColumnWidth(30, 10 * 256);// 결제형태
		sheet.setColumnWidth(31, 10 * 256);// 결제형태 상세
		sheet.setColumnWidth(32, 15 * 256);// 배달 유형(VD/OD)
		sheet.setColumnWidth(33, 10 * 256);// 프랜차이즈 ID
		sheet.setColumnWidth(34, 25 * 256);// 회사명
		sheet.setColumnWidth(35, 15 * 256);// 사업자명
		sheet.setColumnWidth(36, 35 * 256);// 배달주소1

		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("주문일시");
		row.createCell(cellCount++).setCellValue("환불일시");
		row.createCell(cellCount++).setCellValue("지급예정 일자");
		row.createCell(cellCount++).setCellValue("사업자번호");
		row.createCell(cellCount++).setCellValue("음식점명");
		row.createCell(cellCount++).setCellValue("매장코드");
		row.createCell(cellCount++).setCellValue("주문ID");
		row.createCell(cellCount++).setCellValue("주문번호");
		row.createCell(cellCount++).setCellValue("상품가 합계");
		row.createCell(cellCount++).setCellValue("최소주문 추가결제액");
		row.createCell(cellCount++).setCellValue("배달료");
		row.createCell(cellCount++).setCellValue("주문 총액");
		row.createCell(cellCount++).setCellValue("주문 금액 구분 : 온라인 주문");
		row.createCell(cellCount++).setCellValue("주문 금액 구분 : 현장 주문");
		row.createCell(cellCount++).setCellValue("레스토랑 자체할인");
		row.createCell(cellCount++).setCellValue("프로모션 할인금액 : 요기요 부담");
		row.createCell(cellCount++).setCellValue("프로모션 할인금액 : 프랜차이즈 부담");
		row.createCell(cellCount++).setCellValue("프로모션 할인금액 : 레스토랑 부담");
		row.createCell(cellCount++).setCellValue("주문중개이용료 : 주문중개이용료율(+VAT)");
		row.createCell(cellCount++).setCellValue("주문중개이용료 : 주문중개이용료 총액");
		row.createCell(cellCount++).setCellValue("결제이용료 : 결제이용료율(+VAT)");
		row.createCell(cellCount++).setCellValue("결제이용료 : 결제이용료 총액");
		row.createCell(cellCount++).setCellValue("배달대행이용료 : 배달대행이용료 총액");
		row.createCell(cellCount++).setCellValue("이용료 전체 합계");
		row.createCell(cellCount++).setCellValue("OD배달료 매출 : 레스토랑 자체펀딩 총액");
		row.createCell(cellCount++).setCellValue("요타임딜이용료 : 요타임딜이용료 총액");
		row.createCell(cellCount++).setCellValue("추천광고이용료:추천광고이용료 총액");
		row.createCell(cellCount++).setCellValue("정산 금액");
		row.createCell(cellCount++).setCellValue("결제유형");
		row.createCell(cellCount++).setCellValue("결제유형 상세");
		row.createCell(cellCount++).setCellValue("결제형태");
		row.createCell(cellCount++).setCellValue("결제형태 상세");
		row.createCell(cellCount++).setCellValue("배달 유형(VD/OD)");
		row.createCell(cellCount++).setCellValue("프랜차이즈 ID");
		row.createCell(cellCount++).setCellValue("회사명");
		row.createCell(cellCount++).setCellValue("사업자명");
		row.createCell(cellCount++).setCellValue("배달주소1");

		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (EM_SETTLE_YOGIYO_PU_ORD_OMS sydo : excelList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sydo.getORD_TIME())); // 주문일시
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sydo.getREFUND_TIME())); // 환불일시
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sydo.getPAY_EXPECTED_DATE())); // 지급예정 일자
			row.createCell(cellCount++).setCellValue(CommonFunc.biznoFormat(sydo.getBIZ_NO())); // 사업자번호
			row.createCell(cellCount++).setCellValue(sydo.getSTORE_NM()); // 음식점명
			row.createCell(cellCount++).setCellValue(sydo.getSTORE_ID()); // 매장코드
			row.createCell(cellCount++).setCellValue(sydo.getORD_ID()); // 주문ID
			row.createCell(cellCount++).setCellValue(sydo.getCH_ORD_NO()); // 주문번호
			row.createCell(cellCount++).setCellValue(sydo.getITEM_SUM_AMT()); // 상품가 합계
			row.createCell(cellCount++).setCellValue(sydo.getMIN_ORD_ADD_PAY()); // 최소주문 추가결제액
			row.createCell(cellCount++).setCellValue(sydo.getDELIVER_FEE()); // 배달료
			row.createCell(cellCount++).setCellValue(sydo.getORD_TOT_AMT()); // 주문 총액
			row.createCell(cellCount++).setCellValue(sydo.getORD_ONLINE_AMT()); // 주문 금액 구분 : 온라인 주문
			row.createCell(cellCount++).setCellValue(sydo.getORD_OFFLINE_AMT()); // 주문 금액 구분 : 현장 주문
			row.createCell(cellCount++).setCellValue(sydo.getRESTAURANT_SELF_DIS()); // 레스토랑 자체할인
			row.createCell(cellCount++).setCellValue(sydo.getPMT_DIS_YOGIYO_LEVY_AMT()); // 프로모션 할인금액 : 요기요 부담
			row.createCell(cellCount++).setCellValue(sydo.getPMT_DIS_FRANCHISEE_LEVY_AMT()); // 프로모션 할인금액 : 프랜차이즈 부담
			row.createCell(cellCount++).setCellValue(sydo.getPMT_DIS_RESTAURANT_LEVY_AMT()); // 프로모션 할인금액 : 레스토랑 부담
			row.createCell(cellCount++).setCellValue(sydo.getORD_RELAY_FEE_RATE()); // 주문중개이용료 : 주문중개이용료율(+VAT)
			row.createCell(cellCount++).setCellValue(sydo.getORD_RELAY_FEE_TOT_AMT()); // 주문중개이용료 : 주문중개이용료 총액
			row.createCell(cellCount++).setCellValue(sydo.getPAY_RELAY_FEE_RATE()); // 결제이용료 : 결제이용료율(+VAT)
			row.createCell(cellCount++).setCellValue(sydo.getPAY_RELAY_FEE_TOT_AMT()); // 결제이용료 : 결제이용료 총액
			row.createCell(cellCount++).setCellValue(sydo.getDELIVERY_AGENT_FEE()); // 배달대행이용료 : 배달대행이용료 총액
			row.createCell(cellCount++).setCellValue(sydo.getTOT_SUM_FEE()); // 이용료 전체 합계
			row.createCell(cellCount++).setCellValue(sydo.getOD_DELI_FEE_REST_SELF_FUND()); // OD배달료 매출 : 레스토랑 자체펀딩 총액
			row.createCell(cellCount++).setCellValue(sydo.getYO_TIME_FEE_TOT_YO_TIME_FEE()); // 요타임딜이용료 : 요타임딜이용료 총액
			row.createCell(cellCount++).setCellValue(sydo.getREFERRAL_ADVERTISEMENT_FEE());  //추천광고이용료:추천광고이용료 총액
			row.createCell(cellCount++).setCellValue(sydo.getSETTLE_AMT()); // 정산 금액
			row.createCell(cellCount++).setCellValue(sydo.getPAY_TYPE()); // 결제유형
			row.createCell(cellCount++).setCellValue(sydo.getPAY_TYPE_DETAIL()); // 결제유형 상세
			row.createCell(cellCount++).setCellValue(sydo.getPAY_FORM()); // 결제형태
			row.createCell(cellCount++).setCellValue(sydo.getPAY_FORM_DETAIL()); // 결제형태 상세
			row.createCell(cellCount++).setCellValue(sydo.getDELIVERY_TYPE()); // 배달 유형(VD/OD)
			row.createCell(cellCount++).setCellValue(sydo.getFRANCHISEE_ID()); // 프랜차이즈 ID
			row.createCell(cellCount++).setCellValue(sydo.getCPN_NM()); // 회사명
			row.createCell(cellCount++).setCellValue(sydo.getBIZ_NM()); // 사업자명
			row.createCell(cellCount++).setCellValue(sydo.getDELIVERY_ADDRESS1()); // 배달주소1

			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (e == 8 || e == 9 || e == 10 || e == 11 || e == 12 || e == 13 || e == 14 || e == 15 || e == 16 || e == 17
						|| e == 18 || e == 19 || e == 20 || e == 21 || e == 22 || e == 23 || e == 24 || e == 25 || e == 26 || e == 27 || e == 28 || e == 29) {
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

		String fileName;
		if (type) {
			fileName = "EMART24_OMS_요기요픽업_정산_" + getNowDatetime + ".xlsx";
		} else {
			fileName = "EMART24_OMS_요기요픽업_정산오류_" + getNowDatetime + ".xlsx";
		}

		OutputStream os = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 클라이언트 측 다운로드
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

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
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}

	public HttpServletResponse getExcelDownload_KAKAO(List<EM_SETTLE_KAKAO_DV_ORD_OMS> excelList, boolean type,
			HttpServletResponse response) {
		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

		// 숫자 포멧
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 33;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("카카오 정산리스트");

		sheet.setColumnWidth(0, 20 * 256);// 브랜드명
		sheet.setColumnWidth(1, 20 * 256);// 매장코드
		sheet.setColumnWidth(2, 20 * 256);// 매장명
		sheet.setColumnWidth(3, 20 * 256);// 주문일시
		sheet.setColumnWidth(4, 20 * 256);// 조정일자
		sheet.setColumnWidth(5, 20 * 256);// CMS주문번호
		sheet.setColumnWidth(6, 20 * 256);// 카카오주문번호
		sheet.setColumnWidth(7, 20 * 256);// 주문금액
		sheet.setColumnWidth(8, 20 * 256);// 배달료
		sheet.setColumnWidth(9, 20 * 256);// 할인금액
		sheet.setColumnWidth(10, 20 * 256);// 결제금액
		sheet.setColumnWidth(11, 20 * 256);// 최소금액
		sheet.setColumnWidth(12, 20 * 256);// 결제수단
		sheet.setColumnWidth(13, 20 * 256);// 선결제카드
		sheet.setColumnWidth(14, 20 * 256);// 선결제카카오페이
		sheet.setColumnWidth(15, 20 * 256);// 선결제카카오머니
		sheet.setColumnWidth(16, 20 * 256);// 후결제
		sheet.setColumnWidth(17, 20 * 256);// 브랜드 부담금
		sheet.setColumnWidth(18, 20 * 256);// 카카오 부담금
		sheet.setColumnWidth(19, 20 * 256);// 가맹점 부담금
		sheet.setColumnWidth(20, 20 * 256);// 기타 부담금
		sheet.setColumnWidth(21, 20 * 256);// CNT 주문중개 수수료
		sheet.setColumnWidth(22, 20 * 256);// 카카오 주문중개수수료
		sheet.setColumnWidth(23, 20 * 256);// 주문중개 수수료 합
		sheet.setColumnWidth(24, 20 * 256);// CNT 할인중개 수수료
		sheet.setColumnWidth(25, 20 * 256);// 카카오 할인중개 수수료
		sheet.setColumnWidth(26, 20 * 256);// 할인중개 수수료 합
		sheet.setColumnWidth(27, 20 * 256);// 아니시스 수수료
		sheet.setColumnWidth(28, 20 * 256);// 카카오페이 수수료
		sheet.setColumnWidth(29, 20 * 256);// PG 수수료
		sheet.setColumnWidth(30, 20 * 256);// 이니시스백마진
		sheet.setColumnWidth(31, 20 * 256);// E쿠폰수수료
		sheet.setColumnWidth(32, 20 * 256);// 수수료총액
		sheet.setColumnWidth(33, 20 * 256);// 입금액

		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("브랜드명");
		row.createCell(cellCount++).setCellValue("매장코드");
		row.createCell(cellCount++).setCellValue("매장명");
		row.createCell(cellCount++).setCellValue("주문일시");
		row.createCell(cellCount++).setCellValue("조정일자");
		row.createCell(cellCount++).setCellValue("CMS주문번호");
		row.createCell(cellCount++).setCellValue("카카오주문번호");
		row.createCell(cellCount++).setCellValue("주문금액");
		row.createCell(cellCount++).setCellValue("배달료");
		row.createCell(cellCount++).setCellValue("할인금액");
		row.createCell(cellCount++).setCellValue("결제금액");
		row.createCell(cellCount++).setCellValue("최소금액");
		row.createCell(cellCount++).setCellValue("결제수단");
		row.createCell(cellCount++).setCellValue("선결제카드");
		row.createCell(cellCount++).setCellValue("선결제카카오페이");
		row.createCell(cellCount++).setCellValue("선결제카카오머니");
		row.createCell(cellCount++).setCellValue("후결제");
		row.createCell(cellCount++).setCellValue("브랜드 부담금");
		row.createCell(cellCount++).setCellValue("카카오 부담금");
		row.createCell(cellCount++).setCellValue("가맹점 부담금");
		row.createCell(cellCount++).setCellValue("기타 부담금");
		row.createCell(cellCount++).setCellValue("CNT 주문중개 수수료");
		row.createCell(cellCount++).setCellValue("카카오 주문중개 수수료");
		row.createCell(cellCount++).setCellValue("주문중개 수수료 합");
		row.createCell(cellCount++).setCellValue("CNT 할인중개 수수료 ");
		row.createCell(cellCount++).setCellValue("카카오 할인중개 수수료");
		row.createCell(cellCount++).setCellValue("할인중개 수수료 합");
		row.createCell(cellCount++).setCellValue("아니시스 수수료");
		row.createCell(cellCount++).setCellValue("카카오페이 수수료");
		row.createCell(cellCount++).setCellValue("PG 수수료");
		row.createCell(cellCount++).setCellValue("이니시스 백마진");
		row.createCell(cellCount++).setCellValue("E쿠폰수수료");
		row.createCell(cellCount++).setCellValue("수수료총액");
		row.createCell(cellCount++).setCellValue("입금액");

		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (EM_SETTLE_KAKAO_DV_ORD_OMS skdo : excelList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue(skdo.getBRAND_NM()); // 브랜드명
			row.createCell(cellCount++).setCellValue(skdo.getSTORE_ID()); // 매장코드
			row.createCell(cellCount++).setCellValue(skdo.getSTORE_NM()); // 매장명
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(skdo.getORD_TIME())); // 주문일시
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(skdo.getADJUSTMENT_DATE())); // 조정일자
			row.createCell(cellCount++).setCellValue(skdo.getCMD_ORD_NO()); // CMS주문번호
			row.createCell(cellCount++).setCellValue(skdo.getCH_ORD_NO()); // 카카오주문번호
			row.createCell(cellCount++).setCellValue(skdo.getORD_AMT()); // 주문금액
			row.createCell(cellCount++).setCellValue(skdo.getDELIVER_FEE()); // 배달료
			row.createCell(cellCount++).setCellValue(skdo.getDIS_AMT()); // 할인금액
			row.createCell(cellCount++).setCellValue(skdo.getACT_AMT()); // 결제금액
			row.createCell(cellCount++).setCellValue(skdo.getCANCEL_AMT()); // 최소금액
			row.createCell(cellCount++).setCellValue(skdo.getPAY_TYPE()); // 결제수단
			row.createCell(cellCount++).setCellValue(skdo.getPREPAID_CARD()); // 선결제카드
			row.createCell(cellCount++).setCellValue(skdo.getPREPAID_KAKAO_PAY()); // 선결제카카오페이
			row.createCell(cellCount++).setCellValue(skdo.getPREPAID_KAKAO_MONEY()); // 선결제카카오머니
			row.createCell(cellCount++).setCellValue(skdo.getAFTER_PAY()); // 후결제
			row.createCell(cellCount++).setCellValue(skdo.getBRAND_LEVY_AMT()); // 브랜드 부담금
			row.createCell(cellCount++).setCellValue(skdo.getKAKAO_LEVY_AMT()); // 카카오 부담금
			row.createCell(cellCount++).setCellValue(skdo.getFRANCHISEE_LEVY_AMT()); // 가맹점 부담금
			row.createCell(cellCount++).setCellValue(skdo.getETC_LEVY_AMT()); // 기타 부담금
			row.createCell(cellCount++).setCellValue(skdo.getCNT_ORD_RELAY_CMS()); // CNT 주문중개 수수료
			row.createCell(cellCount++).setCellValue(skdo.getKAKAO_ORD_RELAY_CMS()); // 카카오 주문중개 수수료 
			row.createCell(cellCount++).setCellValue(skdo.getORD_RELAY_CMS_SUM()); // 주문중개 수수료 합
			row.createCell(cellCount++).setCellValue(skdo.getCNT_DIS_RELAY_CMS()); // CNT 할인중개 수수료 
			row.createCell(cellCount++).setCellValue(skdo.getKAKAO_DIS_RELAY_CMS()); // 카카오 할인중개 수수료
			row.createCell(cellCount++).setCellValue(skdo.getDIS_RELAY_CMS_SUM()); // 할인중개 수수료 합
			row.createCell(cellCount++).setCellValue(skdo.getINICIS_CMS()); // 아니시스 수수료
			row.createCell(cellCount++).setCellValue(skdo.getKAKAO_PAY_CMS()); // 카카오페이 수수료
			row.createCell(cellCount++).setCellValue(skdo.getPG_CMS()); // PG 수수료
			row.createCell(cellCount++).setCellValue(skdo.getINICIS_BACK_CMS()); // 이니시스 백마진
			row.createCell(cellCount++).setCellValue(skdo.getE_CUPON_CMS()); // E쿠폰수수료
			row.createCell(cellCount++).setCellValue(skdo.getTOT_CMS_AMT()); // 수수료총액
			row.createCell(cellCount++).setCellValue(skdo.getDEPOSIT_AMT()); // 입급액

			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (e == 7 || e == 8 || e == 9 || e == 10 || e == 11 || e >= 13) {
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

		String fileName;
		if (type) {
			fileName = "EMART24_OMS_카카오_정산_" + getNowDatetime + ".xlsx";
		} else {
			fileName = "EMART24_OMS_카카오_정산오류_" + getNowDatetime + ".xlsx";
		}

		OutputStream os = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 클라이언트 측 다운로드
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

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
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}

	public HttpServletResponse getExcelDownload_NAVER(List<EM_SETTLE_NAVER_ORD_OMS> excelList, boolean type,
			HttpServletResponse response) {
		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

		// 숫자 포멧
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 14;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("네이버 정산리스트");

		sheet.setColumnWidth(0, 20 * 256);// 주문번호(PK)
		sheet.setColumnWidth(1, 20 * 256);// 상품주문번호(PK)
		sheet.setColumnWidth(2, 15 * 256);// 구분
		sheet.setColumnWidth(3, 20 * 256);// 상품명
		sheet.setColumnWidth(4, 10 * 256);// 구매자명
		sheet.setColumnWidth(5, 15 * 256);// 정산예정일
		sheet.setColumnWidth(6, 15 * 256);// 정산완료일
		sheet.setColumnWidth(7, 15 * 256);// 정산기준일(PK)
		sheet.setColumnWidth(8, 15 * 256);// 세금신고기준일
		sheet.setColumnWidth(9, 15 * 256);// 정산상태
		sheet.setColumnWidth(10, 15 * 256);// 결제금액(A)
		sheet.setColumnWidth(11, 30 * 256);// 수수료구분
		sheet.setColumnWidth(12, 20 * 256);// 결제수단
		sheet.setColumnWidth(13, 20 * 256);// 매출연동수수료구분
		sheet.setColumnWidth(14, 20 * 256);// 수수료금액
		
		
		
		

		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("주문번호");
		row.createCell(cellCount++).setCellValue("상품주문번호");
		row.createCell(cellCount++).setCellValue("구분");
		row.createCell(cellCount++).setCellValue("상품명");
		row.createCell(cellCount++).setCellValue("구매자명");
		row.createCell(cellCount++).setCellValue("정산예정일");
		row.createCell(cellCount++).setCellValue("정산완료일");
		row.createCell(cellCount++).setCellValue("정산기준일");
		row.createCell(cellCount++).setCellValue("세금신고기준일");
		row.createCell(cellCount++).setCellValue("정산상태");
		row.createCell(cellCount++).setCellValue("결제금액");
		row.createCell(cellCount++).setCellValue("수수료구분");
		row.createCell(cellCount++).setCellValue("결제수단");
		row.createCell(cellCount++).setCellValue("매출연동수수료구분");
		row.createCell(cellCount++).setCellValue("수수료금액");

		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (EM_SETTLE_NAVER_ORD_OMS snoo : excelList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue(snoo.getSETTLE_NO()); // 주문번호(PK)
			row.createCell(cellCount++).setCellValue(snoo.getITEM_ORD_ID()); // 상품주문번호(PK)
			row.createCell(cellCount++).setCellValue(snoo.getGUBUN()); // 구분
			row.createCell(cellCount++).setCellValue(snoo.getITEM_NM()); // 상품명
			row.createCell(cellCount++).setCellValue(snoo.getBUYER_NM()); // 구매자명
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(snoo.getSETTLE_EXPECTED_DATE())); // 정산예정일
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(snoo.getSETTLE_COMPLET_DATE())); // 정산완료일
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(snoo.getORD_DATE())); // 정산기준일(PK)
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(snoo.getTAX_RETURN_DATE())); // 세금신고기준일
			row.createCell(cellCount++).setCellValue(snoo.getSETTLE_STATE()); // 정산상태
			row.createCell(cellCount++).setCellValue(snoo.getACT_AMT()); // 결제금액(A)
			row.createCell(cellCount++).setCellValue(snoo.getCMS_GUBUN()); // 수수료구분
			row.createCell(cellCount++).setCellValue(snoo.getPAY_TYPE()); // 결제수단
			row.createCell(cellCount++).setCellValue(snoo.getSALE_LINK_CMS_GUBUN()); // 매출연동 수수료 상세
			row.createCell(cellCount++).setCellValue(snoo.getSALE_LINK_CMS()); // 수수료금액

			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (e == 10 || e == 14) {
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

		String fileName;
		if (type) {
			fileName = "EMART24_OMS_네이버_정산_" + getNowDatetime + ".xlsx";
		} else {
			fileName = "EMART24_OMS_네이버_정산오류_" + getNowDatetime + ".xlsx";
		}

		OutputStream os = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 클라이언트 측 다운로드
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

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
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}

	public HttpServletResponse getExcelDownload_KIS(List<EM_SETTLE_KISAPP_ORD_OMS> excelList, boolean type,
			HttpServletResponse response) {

		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

		// 숫자 포멧
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 14;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("키스 정산리스트");

		sheet.setColumnWidth(0, 10 * 256);// 구분
		sheet.setColumnWidth(1, 20 * 256);// 채널명
		sheet.setColumnWidth(2, 25 * 256);// 주문일시
		sheet.setColumnWidth(3, 15 * 256);// 지급예정일
		sheet.setColumnWidth(4, 15 * 256);// 사업자번호
		sheet.setColumnWidth(5, 35 * 256);// 점포명
		sheet.setColumnWidth(6, 25 * 256);// 점포코드
		sheet.setColumnWidth(7, 35 * 256);// 주문번호
		sheet.setColumnWidth(8, 10 * 256);// 상품가합계
		sheet.setColumnWidth(9, 10 * 256);// 배달료
		sheet.setColumnWidth(10, 10 * 256);// 결제금액
		sheet.setColumnWidth(11, 10 * 256);// 수수료
		sheet.setColumnWidth(12, 10 * 256);// VAT
		sheet.setColumnWidth(13, 10 * 256);// 지급액
		sheet.setColumnWidth(14, 15 * 256);// 비고

		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("구분");
		row.createCell(cellCount++).setCellValue("채널명");
		row.createCell(cellCount++).setCellValue("주문일시");
		row.createCell(cellCount++).setCellValue("지급예정일");
		row.createCell(cellCount++).setCellValue("사업자번호");
		row.createCell(cellCount++).setCellValue("점포명");
		row.createCell(cellCount++).setCellValue("점포코드");
		row.createCell(cellCount++).setCellValue("주문번호");
		row.createCell(cellCount++).setCellValue("상품가합계");
		row.createCell(cellCount++).setCellValue("배달료");
		row.createCell(cellCount++).setCellValue("결제금액");
		row.createCell(cellCount++).setCellValue("수수료");
		row.createCell(cellCount++).setCellValue("VAT");
		row.createCell(cellCount++).setCellValue("지급액");
		row.createCell(cellCount++).setCellValue("비고");

		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (EM_SETTLE_KISAPP_ORD_OMS esko : excelList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue(esko.getGUBUN()); // 구분
			row.createCell(cellCount++).setCellValue(esko.getCH_NM()); // 채널명
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(esko.getORD_TIME()));// 주문일시
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(esko.getPAY_EXPECTED_DATE())); // 지급예정일
			row.createCell(cellCount++).setCellValue(CommonFunc.biznoFormat(esko.getBIZ_NO())); // 사업자번호
			row.createCell(cellCount++).setCellValue(esko.getSTORE_NM()); // 점포명
			row.createCell(cellCount++).setCellValue(esko.getSTORE_ID()); // 점포코드
			row.createCell(cellCount++).setCellValue(esko.getCH_ORD_NO()); // 주문번호
			row.createCell(cellCount++).setCellValue(esko.getMENU_TOT_AMT()); // 상품가합계
			row.createCell(cellCount++).setCellValue(esko.getDELIVER_FEE()); // 배달료
			row.createCell(cellCount++).setCellValue(esko.getACT_AMT()); // 결제금액
			row.createCell(cellCount++).setCellValue(esko.getCMS_AMT()); // 수수료
			row.createCell(cellCount++).setCellValue(esko.getVAT_AMT()); // VAT
			row.createCell(cellCount++).setCellValue(esko.getPAY_AMT()); // 지급액
			row.createCell(cellCount++).setCellValue(esko.getBIGO()); // 비고

			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (e >= 8 && e <= 13) {
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

		String fileName;
		if (type) {
			fileName = "EMART24_OMS_KIS_정산_" + getNowDatetime + ".xlsx";
		} else {
			fileName = "EMART24_OMS_KIS_정산오류_" + getNowDatetime + ".xlsx";
		}

		OutputStream os = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 클라이언트 측 다운로드
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

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
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}
	
	public HttpServletResponse getExcelDownload_BAEMIN(List<EM_SETTLE_BAEMIN_DV_ORD_OMS> getData, boolean type,
			HttpServletResponse response) {
		
		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush
		
		// header CellStyle 작성
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);
		
		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);
		
		// 숫자 포멧
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);
		
		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 22;
		
		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("배달의민족 정산리스트");
		

		sheet.setColumnWidth(0, 15 * 256);// 상호명
		sheet.setColumnWidth(1, 15 * 256);// 지급예정일자
		sheet.setColumnWidth(2, 15 * 256);// 결제일자
		sheet.setColumnWidth(3, 15 * 256);// 결제시각
		sheet.setColumnWidth(4, 15 * 256);// 정산기준일자
		sheet.setColumnWidth(5, 20 * 256);// 주문서번호
		sheet.setColumnWidth(6, 15 * 256);// 주문서상태	
		sheet.setColumnWidth(7, 15 * 256);// (a)상품금액 - sum
		sheet.setColumnWidth(8, 15 * 256);// (b)배달팁 - sum
		sheet.setColumnWidth(9, 15 * 256);// (c)봉투값 - sum
		sheet.setColumnWidth(10, 15 * 256);// (d)부분환불금액 - sum
		sheet.setColumnWidth(11, 15 * 256);// (e)셀러부담쿠폰금액 - sum
		sheet.setColumnWidth(12, 15 * 256);// (f)합계(정산기준금액) - sum
		sheet.setColumnWidth(13, 15 * 256);// 결제정산수수료 부과대상 금액
		sheet.setColumnWidth(14, 15 * 256);// 중개이용료 부과대상 금액
		sheet.setColumnWidth(15, 15 * 256);// 기준수수료 - sum
		sheet.setColumnWidth(16, 15 * 256);// 우대수수료 - sum
		sheet.setColumnWidth(17, 15 * 256);// 결제정산수수료(부가세) - sum
		sheet.setColumnWidth(18, 15 * 256);// 중개이용료(공급가액) - sum
		sheet.setColumnWidth(19, 15 * 256);// 중개이용료(부가세) - sum
		sheet.setColumnWidth(20, 15 * 256);// 배민라이더스 이용료(공급가액) - sum
		sheet.setColumnWidth(21, 15 * 256);// 배민라이더스 이용료(부가세) - sum
		sheet.setColumnWidth(22, 15 * 256);// 정산금액
		
		
		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);
		
		// 스타일 적용
		row.createCell(cellCount++).setCellValue("상호명");
		row.createCell(cellCount++).setCellValue("지급예정일자");
		row.createCell(cellCount++).setCellValue("결제일자");
		row.createCell(cellCount++).setCellValue("결제시각");
		row.createCell(cellCount++).setCellValue("정산기준일자");
		row.createCell(cellCount++).setCellValue("주문서번호");
		row.createCell(cellCount++).setCellValue("주문서상태");
		row.createCell(cellCount++).setCellValue("(a)상품금액");
		row.createCell(cellCount++).setCellValue("(b)배달팁");
		row.createCell(cellCount++).setCellValue("(c)봉투값");
		row.createCell(cellCount++).setCellValue("(d)부분환불금액");
		row.createCell(cellCount++).setCellValue("(e)셀러부담쿠폰금액");
		row.createCell(cellCount++).setCellValue("(f)합계(정산기준금액)(f=a+b+c+d+e)");
		row.createCell(cellCount++).setCellValue("(B)결제정산수수료 부과 대상 금액 (B=f)");
		row.createCell(cellCount++).setCellValue("중개이용료 부과대상금액  (C=a+d+e)");
		row.createCell(cellCount++).setCellValue("기준수수료");
		row.createCell(cellCount++).setCellValue("우대수수료");
		row.createCell(cellCount++).setCellValue("결제정산수수료(부가세)");
		row.createCell(cellCount++).setCellValue("중개이용료(공급가액)");
		row.createCell(cellCount++).setCellValue("중개이용료(부가세)");
		row.createCell(cellCount++).setCellValue("배민라이더스 이용료(공급가액)");
		row.createCell(cellCount++).setCellValue("배민라이더스 이용료(부가세)");
		row.createCell(cellCount++).setCellValue("(E)정산금액 (E=f+D)");

		
		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}
		
		// 데이터 Cell 생성
		for (EM_SETTLE_BAEMIN_DV_ORD_OMS sbdo : getData) {
			
			cellCount = 0;
			
			row = sheet.createRow(rowCount++);
			
			row.setHeight((short) 350);
			
			row.createCell(cellCount++).setCellValue(sbdo.getBRANCH_NM()); // 상호명
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sbdo.getEXP_PAY_DATE())); //지급예정일자
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sbdo.getPAY_DATE())); //결제일자
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sbdo.getPAY_TIME())); //결제시각
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sbdo.getSETTLE_STD_DATE())); //정산기준일자
			row.createCell(cellCount++).setCellValue(sbdo.getORD_ID()); // 주문서번호
			row.createCell(cellCount++).setCellValue(sbdo.getORD_STATE()); // 주문서상태
			row.createCell(cellCount++).setCellValue(sbdo.getSALE_ITEM_AMT()); // (a)상품금액
			row.createCell(cellCount++).setCellValue(sbdo.getSALE_DV_AMT()); // (b)배달팁
			row.createCell(cellCount++).setCellValue(sbdo.getSALE_ENVELOPE_AMT()); // (c)봉투값
			row.createCell(cellCount++).setCellValue(sbdo.getSALE_PARTIAL_REFUND()); // (d)부분환불금액
			row.createCell(cellCount++).setCellValue(sbdo.getSALE_SELLER_CPN_AMT()); // (e)셀러부담쿠폰금액
			row.createCell(cellCount++).setCellValue(sbdo.getSALE_SUM()); // (f)합계(정산기준금액)
			row.createCell(cellCount++).setCellValue(sbdo.getSETTLE_PAY_CHARGE_TARGET_AMT()); // 결제정산수수료 부과대상 금액(B=f)
			row.createCell(cellCount++).setCellValue(sbdo.getRELAY_FEE_CHARGE_TARGET_AMT()); //중개이용료 부과대상 금액(C=a+d+e)
			row.createCell(cellCount++).setCellValue(sbdo.getCOMM_SD_SUPPLY_AMT()); //기준수수료
			row.createCell(cellCount++).setCellValue(sbdo.getCOMM_SD_PREFERENTIAL_AMT()); //우대수수료
			row.createCell(cellCount++).setCellValue(sbdo.getCOMM_PAY_SETTLE_VAT()); //결제정산수수료(부가세)
			row.createCell(cellCount++).setCellValue(sbdo.getCOMM_RELAY_FEE_SUPPLY_AMT()); //중개이용료(공급가액)
			row.createCell(cellCount++).setCellValue(sbdo.getCOMM_RELAY_FEE_VAT()); //중개이용료(부가세)
			row.createCell(cellCount++).setCellValue(sbdo.getCOMM_BM_RD_FEE_SUPPLY_AMT()); //배민라이더스 이용료(공급가액)
			row.createCell(cellCount++).setCellValue(sbdo.getCOMM_BM_RD_FEE_VAT()); //배민라이더스 이용료(부가세)
			row.createCell(cellCount++).setCellValue(sbdo.getSETTLE_AMT()); //(E)정산금액(E=f*d)
			
			
			
			
			
			
			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				row.getCell(e).setCellStyle(bodyStyle);
			}
		}
		
		// 컨텐츠 타입과 파일명 지정
		
		Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
		
		// 년월일시분초 14자리 포멧
		SimpleDateFormat simple_format = new SimpleDateFormat("yyyyMMdd_HHmmss");
		
		String getNowDatetime = simple_format.format(date_now);
		
		String fileName;
		if (type) {
			fileName = "EMART24_OMS_배달의민족_정산_" + getNowDatetime + ".xlsx";
		} else {
			fileName = "EMART24_OMS_배달의민족_정산오류_" + getNowDatetime + ".xlsx";
		}
		
		OutputStream os = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// 클라이언트 측 다운로드
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
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
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}
	//이마트24(인앱)
	public HttpServletResponse getExcelDownload_EMART24(List<EM_SETTLE_EMART24_DV_PU_OMS> getData, boolean type,
			HttpServletResponse response) {
		
		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush
		
		// header CellStyle 작성
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
		SXSSFSheet sheet = wb.createSheet("이마트24앱 정산리스트");
		
		sheet.setColumnWidth(0, 10 * 256);// 구분
		sheet.setColumnWidth(1, 35 * 256);// 점포명
		sheet.setColumnWidth(2, 25 * 256);// 점포코드
		sheet.setColumnWidth(3, 15 * 256);// 이마트앱주문번호
		sheet.setColumnWidth(4, 35 * 256);// 상세주문번호
		sheet.setColumnWidth(5, 25 * 256);// 주문일시
		sheet.setColumnWidth(6, 10 * 256);// 상품가합계
		sheet.setColumnWidth(7, 10 * 256);// 배달료
		sheet.setColumnWidth(8, 10 * 256);// 결제금액
		sheet.setColumnWidth(9, 10 * 256);// 수수료(VAT포함)
		sheet.setColumnWidth(10, 10 * 256);// 지급액
		sheet.setColumnWidth(11, 15 * 256);// 비고
		
		
		
		
		
		
		
		
		
		
		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);
		
		// 스타일 적용
		row.createCell(cellCount++).setCellValue("구분");
		row.createCell(cellCount++).setCellValue("점포명");
		row.createCell(cellCount++).setCellValue("점포코드");
		row.createCell(cellCount++).setCellValue("주문번호");//주문ID
		row.createCell(cellCount++).setCellValue("상세주문번호");
		row.createCell(cellCount++).setCellValue("주문일시");
		row.createCell(cellCount++).setCellValue("상품가합계");
		row.createCell(cellCount++).setCellValue("배달료");
		row.createCell(cellCount++).setCellValue("결제금액");
		row.createCell(cellCount++).setCellValue("수수료(VAT포함)");
		row.createCell(cellCount++).setCellValue("지급액");
		row.createCell(cellCount++).setCellValue("비고");
		
		
		
		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}
		
		// 데이터 Cell 생성
		for (EM_SETTLE_EMART24_DV_PU_OMS esem24 : getData) {
			
			cellCount = 0;
			
			row = sheet.createRow(rowCount++);
			
			row.setHeight((short) 350);
			
			
			row.createCell(cellCount++).setCellValue(esem24.getDIVISION());                             // 구분
			row.createCell(cellCount++).setCellValue(esem24.getSTORE_NM());                             // 점포명
			row.createCell(cellCount++).setCellValue(esem24.getSTORE_ID());                             // 점포코드
			row.createCell(cellCount++).setCellValue(esem24.getCH_ORD_NO());                            // 이마트24앱 주문번호
			row.createCell(cellCount++).setCellValue(esem24.getCH_ORD_DT_NO());                         // 상세주문번호
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat (esem24.getORD_TIME())); // 주문일시
			row.createCell(cellCount++).setCellValue(esem24.getSALE_ITEM_AMT());                        // 상품가합계
			row.createCell(cellCount++).setCellValue(esem24.getSALE_DELV_FEE());                        // 배달료
			row.createCell(cellCount++).setCellValue(esem24.getSALE_PAY_AMT());                         // 결제금액
			row.createCell(cellCount++).setCellValue(esem24.getCMS_VAT_INCLUDED());                     // 수수료(VAT포함)
			row.createCell(cellCount++).setCellValue(esem24.getSETTLE_PAY_AMT());                       // 지급액
			row.createCell(cellCount++).setCellValue(esem24.getNOTE());                                 // 비고
			

			
			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (e >= 6 && e <= 10) {
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
		
		String fileName;
		if (type) {
			fileName = "EMART24_OMS_EMART24앱_정산_" + getNowDatetime + ".xlsx";
		} else {
			fileName = "EMART24_OMS_EMART24앱_정산오류_" + getNowDatetime + ".xlsx";
		}
		
		OutputStream os = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// 클라이언트 측 다운로드
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
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
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}
	
	
	

	public HttpServletResponse getExcelDownload_VROONG(List<EM_SETTLE_VROONG_DV_OMS> excelList, boolean type,
			HttpServletResponse response) {
		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

		// 숫자 포멧
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 42;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("부릉 정산리스트");

		sheet.setColumnWidth(0, 15 * 256);// 일자
		sheet.setColumnWidth(1, 15 * 256);// 상점코드
		sheet.setColumnWidth(2, 25 * 256);// 상점명
		sheet.setColumnWidth(3, 25 * 256);// 프렌차이즈명
		sheet.setColumnWidth(4, 15 * 256);// 프렌차이즈유형
		sheet.setColumnWidth(5, 25 * 256);// 부릉오더넘버
		sheet.setColumnWidth(6, 25 * 256);// 재배송부릉오더넘버
		sheet.setColumnWidth(7, 25 * 256);// 프라임딜리버리넘버
		sheet.setColumnWidth(8, 25 * 256);// 자체주문번호
		sheet.setColumnWidth(9, 25 * 256);// 자체배송번호
		sheet.setColumnWidth(10, 15 * 256);// 상태
		sheet.setColumnWidth(11, 30 * 256);// 출발지주소
		sheet.setColumnWidth(12, 30 * 256);// 도착지주소
		sheet.setColumnWidth(13, 15 * 256);// 결제수단
		sheet.setColumnWidth(14, 15 * 256);// 상품가액
		sheet.setColumnWidth(15, 15 * 256);// 취소수수료
		sheet.setColumnWidth(16, 25 * 256);// 정산상품가액(송급포함)
		sheet.setColumnWidth(17, 15 * 256);// 기본배송료
		sheet.setColumnWidth(18, 15 * 256);// 거리추가
		sheet.setColumnWidth(19, 15 * 256);// 기상할증
		sheet.setColumnWidth(20, 15 * 256);// 지역할증
		sheet.setColumnWidth(21, 15 * 256);// 픽업할증
		sheet.setColumnWidth(22, 15 * 256);// 명절할증
		sheet.setColumnWidth(23, 15 * 256);// 과적할증
		sheet.setColumnWidth(24, 15 * 256);// 재이동배송비
		sheet.setColumnWidth(25, 15 * 256);// 반납배송비
		sheet.setColumnWidth(26, 15 * 256);// 정산취소수수료
		sheet.setColumnWidth(27, 15 * 256);// 카드수수료
		sheet.setColumnWidth(28, 15 * 256);// 상점입금액
		sheet.setColumnWidth(29, 15 * 256);// 배달대행수수료
		sheet.setColumnWidth(30, 15 * 256);// 카드수수료율(%)
		sheet.setColumnWidth(31, 40 * 256);// 비고
		sheet.setColumnWidth(32, 10 * 256);// 거리
		sheet.setColumnWidth(33, 15 * 256);// 접수시각
		sheet.setColumnWidth(34, 15 * 256);// 배차시각
		sheet.setColumnWidth(35, 15 * 256);// 픽업시각
		sheet.setColumnWidth(36, 15 * 256);// 완료시각
		sheet.setColumnWidth(37, 15 * 256);// 취소시각
		sheet.setColumnWidth(38, 15 * 256);// 최종결제수단
		sheet.setColumnWidth(39, 10 * 256);// 완료건수
		sheet.setColumnWidth(40, 10 * 256);// 취소건수
		sheet.setColumnWidth(41, 10 * 256);// 할증금액
		sheet.setColumnWidth(42, 25 * 256);// 할증내용

		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("일자");
		row.createCell(cellCount++).setCellValue("상점코드");
		row.createCell(cellCount++).setCellValue("상점명");
		row.createCell(cellCount++).setCellValue("프렌차이즈명");
		row.createCell(cellCount++).setCellValue("프렌차이즈유형");
		row.createCell(cellCount++).setCellValue("부릉오더넘버");
		row.createCell(cellCount++).setCellValue("재배송부릉오더넘버");
		row.createCell(cellCount++).setCellValue("프라임딜리버리넘버");
		row.createCell(cellCount++).setCellValue("자체주문번호");
		row.createCell(cellCount++).setCellValue("자체배송번호"); //10
		row.createCell(cellCount++).setCellValue("상태");
		row.createCell(cellCount++).setCellValue("출발지주소");
		row.createCell(cellCount++).setCellValue("도착지주소");
		row.createCell(cellCount++).setCellValue("결제수단");
		row.createCell(cellCount++).setCellValue("상품가액");
		row.createCell(cellCount++).setCellValue("취소수수료");
		row.createCell(cellCount++).setCellValue("정산상품가액(송급포함)");
		row.createCell(cellCount++).setCellValue("기본배송료");
		row.createCell(cellCount++).setCellValue("거리추가");
		row.createCell(cellCount++).setCellValue("기상할증"); //20
		row.createCell(cellCount++).setCellValue("지역할증");
		row.createCell(cellCount++).setCellValue("픽업할증");
		row.createCell(cellCount++).setCellValue("명절할증");
		row.createCell(cellCount++).setCellValue("과적할증");
		row.createCell(cellCount++).setCellValue("재이동배송비");
		row.createCell(cellCount++).setCellValue("반납배송비");
		row.createCell(cellCount++).setCellValue("정산취소수수료");
		row.createCell(cellCount++).setCellValue("카드수수료");
		row.createCell(cellCount++).setCellValue("상점입금액");
		row.createCell(cellCount++).setCellValue("배달대행수수료");
		row.createCell(cellCount++).setCellValue("카드수수료율(%)");
		row.createCell(cellCount++).setCellValue("비고");
		row.createCell(cellCount++).setCellValue("거리");
		row.createCell(cellCount++).setCellValue("접수시각");
		row.createCell(cellCount++).setCellValue("배차시각");
		row.createCell(cellCount++).setCellValue("픽업시각");
		row.createCell(cellCount++).setCellValue("완료시각");
		row.createCell(cellCount++).setCellValue("취소시각");
		row.createCell(cellCount++).setCellValue("최종결제수단");
		row.createCell(cellCount++).setCellValue("완료건수");
		row.createCell(cellCount++).setCellValue("취소건수");
		row.createCell(cellCount++).setCellValue("할증금액");
		row.createCell(cellCount++).setCellValue("할증내용");

		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (EM_SETTLE_VROONG_DV_OMS sbdo : excelList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sbdo.getRECEIPT_DATE())); // 일자
			row.createCell(cellCount++).setCellValue(sbdo.getSTORE_ID()); // 상점코드
			row.createCell(cellCount++).setCellValue(sbdo.getSTORE_NM()); // 상점명
			row.createCell(cellCount++).setCellValue(sbdo.getFRANCHISEE_NM()); // 프렌차이즈명
			row.createCell(cellCount++).setCellValue(sbdo.getFRANCHISEE_TYPE()); // 프렌차이즈유형
			row.createCell(cellCount++).setCellValue(sbdo.getVROONG_NO()); // 부릉오더넘버
			row.createCell(cellCount++).setCellValue(sbdo.getRE_VROONG_NO()); // 재배송부릉오더넘버
			row.createCell(cellCount++).setCellValue(sbdo.getPRIME_DELIVERY_NO()); // 프라임딜리버리넘버
			row.createCell(cellCount++).setCellValue(sbdo.getORD_ID()); // 자체주문번호
			row.createCell(cellCount++).setCellValue(sbdo.getSELF_DELIVERY_NO()); // 자체배송번호 10
			row.createCell(cellCount++).setCellValue(sbdo.getSTATE()); // 상태
			row.createCell(cellCount++).setCellValue(sbdo.getSTART_ADDRESS()); // 출발지주소
			row.createCell(cellCount++).setCellValue(sbdo.getFINISH_ADDRESS()); // 도착지주소
			row.createCell(cellCount++).setCellValue(sbdo.getPAY_TYPE()); // 결제수단
			row.createCell(cellCount++).setCellValue(sbdo.getITEM_AMT()); // 상품가액
			row.createCell(cellCount++).setCellValue(sbdo.getCANCEL_CMS()); // 취소수수료
			row.createCell(cellCount++).setCellValue(sbdo.getSETTLE_ITEM_AMT()); // 정산상품가액(송급포함)
			row.createCell(cellCount++).setCellValue(sbdo.getBASIC_DELIVER_FEE()); // 기본배송료
			row.createCell(cellCount++).setCellValue(sbdo.getADD_DISTANCE_AMT()); // 거리추가
			row.createCell(cellCount++).setCellValue(sbdo.getWEATHER_PREMIUM_AMT()); // 기상할증 20
			row.createCell(cellCount++).setCellValue(sbdo.getAREA_PREMIUM_AMT()); // 지역할증
			row.createCell(cellCount++).setCellValue(sbdo.getPICKUP_PREMIUM_AMT()); // 픽업할증
			row.createCell(cellCount++).setCellValue(sbdo.getHOLIDAY_PREMIUM_AMT()); // 명절할증
			row.createCell(cellCount++).setCellValue(sbdo.getOVERLOAD_PREMIUM_AMT()); // 과적할증
			row.createCell(cellCount++).setCellValue(sbdo.getRE_MOVE_DELIVER_FEE()); // 재이동배송비
			row.createCell(cellCount++).setCellValue(sbdo.getRETURN_DELIVER_FEE()); // 반납배송비
			row.createCell(cellCount++).setCellValue(sbdo.getSETTLE_CANCEL_CMS()); // 정산취소수수료
			row.createCell(cellCount++).setCellValue(sbdo.getCARD_CMS()); // 카드수수료
			row.createCell(cellCount++).setCellValue(sbdo.getSTORE_DEPOSIT_AMT()); // 상점입금액
			row.createCell(cellCount++).setCellValue(sbdo.getDELIVERY_AGENCY_CMS()); // 배달대행수수료
			row.createCell(cellCount++).setCellValue(sbdo.getCARD_CMS_RATE()); // 카드수수료율(%)
			row.createCell(cellCount++).setCellValue(sbdo.getBIGO()); // 비고
			row.createCell(cellCount++).setCellValue(sbdo.getDISTANCE()); // 거리
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sbdo.getRECEIPT_TIME())); // 접수시각
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sbdo.getASSIGNED_TIME())); // 배차시각
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sbdo.getPICKUP_TIME())); // 픽업시각
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sbdo.getCOMPLET_TIME())); // 완료시각
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sbdo.getCANCEL_TIME())); // 취소시각
			row.createCell(cellCount++).setCellValue(sbdo.getFINAL_PAY_TYPE()); // 최종결제수단
			row.createCell(cellCount++).setCellValue(sbdo.getCOMPLET_COUNT()); // 완료건수
			row.createCell(cellCount++).setCellValue(sbdo.getCANCEL_COUNT()); // 취소건수
			row.createCell(cellCount++).setCellValue(sbdo.getPREMIUM_AMT()); // 할증금액
			row.createCell(cellCount++).setCellValue(sbdo.getPREMIUM_MEMO()); // 할증내용

			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (e >= 14 && e <= 29 || e == 42) {
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

		String fileName;
		if (type) {
			fileName = "EMART24_OMS_부릉_정산_" + getNowDatetime + ".xlsx";
		} else {
			fileName = "EMART24_OMS_부릉_정산오류_" + getNowDatetime + ".xlsx";
		}

		OutputStream os = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 클라이언트 측 다운로드
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

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
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}

	public HttpServletResponse getExcelDownload_BAROGO(List<EM_SETTLE_BAROGO_DV_OMS> excelList, boolean type,
			HttpServletResponse response) {
		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

		// 숫자 포멧
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 20;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("바로고 정산리스트");

		sheet.setColumnWidth(0, 15 * 256);// 접수번호
		sheet.setColumnWidth(1, 25 * 256);// 주문번호
		sheet.setColumnWidth(2, 15 * 256);// 접수일자
		sheet.setColumnWidth(3, 10 * 256);// 접수시간
		sheet.setColumnWidth(4, 10 * 256);// 완료시간
		sheet.setColumnWidth(5, 10 * 256);// 최소시간
		sheet.setColumnWidth(6, 25 * 256);// 발주가맹점명
		sheet.setColumnWidth(7, 10 * 256);// 거리(km)
		sheet.setColumnWidth(8, 10 * 256);// 기본운임
		sheet.setColumnWidth(9, 10 * 256);// 거리운임
		sheet.setColumnWidth(10, 10 * 256);// 픽업할증
		sheet.setColumnWidth(11, 10 * 256);// 심야할증
		sheet.setColumnWidth(12, 10 * 256);// 우천할증
		sheet.setColumnWidth(13, 10 * 256);// 추가할증
		sheet.setColumnWidth(14, 10 * 256);// 구역할증
		sheet.setColumnWidth(15, 10 * 256);// 상품할증
		sheet.setColumnWidth(16, 10 * 256);// 배송운임
		sheet.setColumnWidth(17, 10 * 256);// VAT
		sheet.setColumnWidth(18, 10 * 256);// VAT포함
		sheet.setColumnWidth(19, 15 * 256);// 접수채널
		sheet.setColumnWidth(20, 25 * 256);// 비고

		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("접수번호");
		row.createCell(cellCount++).setCellValue("주문번호");
		row.createCell(cellCount++).setCellValue("접수일자");
		row.createCell(cellCount++).setCellValue("접수시간");
		row.createCell(cellCount++).setCellValue("완료시간");
		row.createCell(cellCount++).setCellValue("최소시간");
		row.createCell(cellCount++).setCellValue("발주가맹점명");
		row.createCell(cellCount++).setCellValue("거리(km)");
		row.createCell(cellCount++).setCellValue("기본운임");
		row.createCell(cellCount++).setCellValue("거리운임");
		row.createCell(cellCount++).setCellValue("픽업할증");
		row.createCell(cellCount++).setCellValue("심야할증");
		row.createCell(cellCount++).setCellValue("우천할증");
		row.createCell(cellCount++).setCellValue("추가할증");
		row.createCell(cellCount++).setCellValue("구역할증");
		row.createCell(cellCount++).setCellValue("상품할증");
		row.createCell(cellCount++).setCellValue("배송운임");
		row.createCell(cellCount++).setCellValue("VAT");
		row.createCell(cellCount++).setCellValue("VAT포함");
		row.createCell(cellCount++).setCellValue("접수채널");
		row.createCell(cellCount++).setCellValue("비고");

		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (EM_SETTLE_BAROGO_DV_OMS sbdo : excelList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue(sbdo.getRECEIPT_NO());// 접수번호
			row.createCell(cellCount++).setCellValue(sbdo.getORD_ID());// 주문번호
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sbdo.getRECEIPT_DATE()));// 접수일자
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sbdo.getRECEIPT_TIME()));// 접수시간
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sbdo.getCOMPLET_TIME()));// 완료시간
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sbdo.getCANCEL_TIME()));// 최소시간
			row.createCell(cellCount++).setCellValue(sbdo.getORD_ISSUER_NM());// 발주가맹점명
			row.createCell(cellCount++).setCellValue(sbdo.getDISTANCE());// 거리(km)
			row.createCell(cellCount++).setCellValue(sbdo.getBASIC_FARE_AMT());// 기본운임
			row.createCell(cellCount++).setCellValue(sbdo.getDISTANCE_FARE_AMT());// 거리운임
			row.createCell(cellCount++).setCellValue(sbdo.getPICKUP_PREMIUM_AMT());// 픽업할증
			row.createCell(cellCount++).setCellValue(sbdo.getLATE_NIGTH_PREMIUM_AMT());// 심야할증
			row.createCell(cellCount++).setCellValue(sbdo.getRAIN_PREMIUM_AMT());// 우천할증
			row.createCell(cellCount++).setCellValue(sbdo.getADD_PREMIUM_AMT());// 추가할증
			row.createCell(cellCount++).setCellValue(sbdo.getAREA_PREMIUM_AMT());// 구역할증
			row.createCell(cellCount++).setCellValue(sbdo.getITEM_PREMIUM_AMT());// 상품할증
			row.createCell(cellCount++).setCellValue(sbdo.getDELIVERY_FARE_AMT());// 배송운임
			row.createCell(cellCount++).setCellValue(sbdo.getVAT_AMT());// VAT
			row.createCell(cellCount++).setCellValue(sbdo.getVAT_INCLUDE_AMT());// VAT포함
			row.createCell(cellCount++).setCellValue(sbdo.getRECEIPT_CH());// 접수채널
			row.createCell(cellCount++).setCellValue(sbdo.getBIGO());// 비고

			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (e >= 8 && e <= 18) {
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

		String fileName;
		if (type) {
			fileName = "EMART24_OMS_바로고_정산_" + getNowDatetime + ".xlsx";
		} else {
			fileName = "EMART24_OMS_바로고_정산오류_" + getNowDatetime + ".xlsx";
		}

		OutputStream os = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 클라이언트 측 다운로드
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

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
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}

	public HttpServletResponse getExcelDownload_LOGIALL(List<EM_SETTLE_LOGIALL_DV_OMS> excelList, boolean type,
			HttpServletResponse response) {
		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

		// 숫자 포멧
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 12;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("생각대로 정산리스트");

		sheet.setColumnWidth(0, 10 * 256);// 점포코드
		sheet.setColumnWidth(1, 25 * 256);// 점포명
		sheet.setColumnWidth(2, 15 * 256);// 배달일자
		sheet.setColumnWidth(3, 10 * 256);// 접수시간
		sheet.setColumnWidth(4, 10 * 256);// 완료시간
		sheet.setColumnWidth(5, 10 * 256);// 취소시간
		sheet.setColumnWidth(6, 25 * 256);// 주문번호
		sheet.setColumnWidth(7, 15 * 256);// 배달대행료
		sheet.setColumnWidth(8, 10 * 256);// 할증료
		sheet.setColumnWidth(9, 15 * 256);// 할증사유
		sheet.setColumnWidth(10, 15 * 256);// 배달총금액
		sheet.setColumnWidth(11, 10 * 256);// 거리
		sheet.setColumnWidth(12, 15 * 256);// 배달상태

		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("점포코드");
		row.createCell(cellCount++).setCellValue("점포명");
		row.createCell(cellCount++).setCellValue("배달일자");
		row.createCell(cellCount++).setCellValue("접수시간");
		row.createCell(cellCount++).setCellValue("완료시간");
		row.createCell(cellCount++).setCellValue("취소시간");
		row.createCell(cellCount++).setCellValue("주문번호");
		row.createCell(cellCount++).setCellValue("배달대행료");
		row.createCell(cellCount++).setCellValue("할증료");
		row.createCell(cellCount++).setCellValue("할증사유");
		row.createCell(cellCount++).setCellValue("배달총금액");
		row.createCell(cellCount++).setCellValue("거리");
		row.createCell(cellCount++).setCellValue("배달상태");

		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (EM_SETTLE_LOGIALL_DV_OMS sldo : excelList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue(sldo.getSTORE_ID());// 점포코드
			row.createCell(cellCount++).setCellValue(sldo.getSTORE_NM());// 점포명
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sldo.getDELIVER_DATE()));// 배달일자
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sldo.getRECEIPT_TIME()));// 접수시간
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sldo.getCOMPLET_TIME()));// 완료시간
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sldo.getCANCEL_TIME()));// 취소시간
			row.createCell(cellCount++).setCellValue(sldo.getORD_ID());// 주문번호
			row.createCell(cellCount++).setCellValue(sldo.getDELIVERY_AGENCY_FEE());// 배달대행료
			row.createCell(cellCount++).setCellValue(sldo.getPREMIUM_FEE());// 할증료
			row.createCell(cellCount++).setCellValue(sldo.getPREMIUM_REASON());// 할증사유
			row.createCell(cellCount++).setCellValue(sldo.getDELIVERY_TOT_AMT());// 배달총금액
			row.createCell(cellCount++).setCellValue(sldo.getDISTANCE());// 거리
			row.createCell(cellCount++).setCellValue(sldo.getDELIVERY_STATE());// 배달상태

			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (e == 7 || e == 8 || e == 10) {
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

		String fileName;
		if (type) {
			fileName = "EMART24_OMS_생각대로_정산_" + getNowDatetime + ".xlsx";
		} else {
			fileName = "EMART24_OMS_생각대로_정산오류_" + getNowDatetime + ".xlsx";
		}

		OutputStream os = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 클라이언트 측 다운로드
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

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
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}

	public HttpServletResponse getExcelDownload_DEALVER(List<EM_SETTLE_DEALVER_DV_OMS> excelList, boolean type,
			HttpServletResponse response) {
		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

		// 숫자 포멧
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 12;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("딜버 정산리스트");

		sheet.setColumnWidth(0, 10 * 256);// 점포코드
		sheet.setColumnWidth(1, 25 * 256);// 점포명
		sheet.setColumnWidth(2, 15 * 256);// 배달일자
		sheet.setColumnWidth(3, 10 * 256);// 접수시간
		sheet.setColumnWidth(4, 10 * 256);// 완료시간
		sheet.setColumnWidth(5, 10 * 256);// 취소시간
		sheet.setColumnWidth(6, 25 * 256);// 주문번호
		sheet.setColumnWidth(7, 15 * 256);// 배달대행료
		sheet.setColumnWidth(8, 10 * 256);// 할증료
		sheet.setColumnWidth(9, 15 * 256);// 할증사유
		sheet.setColumnWidth(10, 15 * 256);// 배달총금액
		sheet.setColumnWidth(11, 10 * 256);// 거리
		sheet.setColumnWidth(12, 15 * 256);// 배달상태

		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("점포코드");
		row.createCell(cellCount++).setCellValue("점포명");
		row.createCell(cellCount++).setCellValue("배달일자");
		row.createCell(cellCount++).setCellValue("접수시간");
		row.createCell(cellCount++).setCellValue("완료시간");
		row.createCell(cellCount++).setCellValue("취소시간");
		row.createCell(cellCount++).setCellValue("주문번호");
		row.createCell(cellCount++).setCellValue("배달대행료");
		row.createCell(cellCount++).setCellValue("할증료");
		row.createCell(cellCount++).setCellValue("할증사유");
		row.createCell(cellCount++).setCellValue("배달총금액");
		row.createCell(cellCount++).setCellValue("거리");
		row.createCell(cellCount++).setCellValue("배달상태");

		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (EM_SETTLE_DEALVER_DV_OMS sldo : excelList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue(sldo.getSTORE_ID());// 점포코드
			row.createCell(cellCount++).setCellValue(sldo.getSTORE_NM());// 점포명
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sldo.getDELIVER_DATE()));// 배달일자
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sldo.getRECEIPT_TIME()));// 접수시간
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sldo.getCOMPLET_TIME()));// 완료시간
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sldo.getCANCEL_TIME()));// 취소시간
			row.createCell(cellCount++).setCellValue(sldo.getORD_ID());// 주문번호
			row.createCell(cellCount++).setCellValue(sldo.getDELIVERY_AGENCY_FEE());// 배달대행료
			row.createCell(cellCount++).setCellValue(sldo.getPREMIUM_FEE());// 할증료
			row.createCell(cellCount++).setCellValue(sldo.getPREMIUM_REASON());// 할증사유
			row.createCell(cellCount++).setCellValue(sldo.getDELIVERY_TOT_AMT());// 배달총금액
			row.createCell(cellCount++).setCellValue(sldo.getDISTANCE());// 거리
			row.createCell(cellCount++).setCellValue(sldo.getDELIVERY_STATE());// 배달상태

			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (e == 7 || e == 8 || e == 10) {
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

		String fileName;
		if (type) {
			fileName = "EMART24_OMS_딜버_정산_" + getNowDatetime + ".xlsx";
		} else {
			fileName = "EMART24_OMS_딜버_정산오류_" + getNowDatetime + ".xlsx";
		}

		OutputStream os = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 클라이언트 측 다운로드
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

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
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}

	public HttpServletResponse getExcelDownload_SPIDOR(List<EM_SETTLE_SPIDOR_DV_OMS> excelList, boolean type,
			HttpServletResponse response) {
		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

		// 숫자 포멧
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 12;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("스파이더 정산리스트");

		sheet.setColumnWidth(0, 10 * 256);// 점포코드
		sheet.setColumnWidth(1, 25 * 256);// 점포명
		sheet.setColumnWidth(2, 15 * 256);// 배달일자
		sheet.setColumnWidth(3, 10 * 256);// 접수시간
		sheet.setColumnWidth(4, 10 * 256);// 완료시간
		sheet.setColumnWidth(5, 10 * 256);// 취소시간
		sheet.setColumnWidth(6, 25 * 256);// 주문번호
		sheet.setColumnWidth(7, 15 * 256);// 배달대행료
		sheet.setColumnWidth(8, 10 * 256);// 할증료
		sheet.setColumnWidth(9, 15 * 256);// 할증사유
		sheet.setColumnWidth(10, 15 * 256);// 배달총금액
		sheet.setColumnWidth(11, 10 * 256);// 거리
		sheet.setColumnWidth(12, 15 * 256);// 배달상태

		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("점포코드");
		row.createCell(cellCount++).setCellValue("점포명");
		row.createCell(cellCount++).setCellValue("배달일자");
		row.createCell(cellCount++).setCellValue("접수시간");
		row.createCell(cellCount++).setCellValue("완료시간");
		row.createCell(cellCount++).setCellValue("취소시간");
		row.createCell(cellCount++).setCellValue("주문번호");
		row.createCell(cellCount++).setCellValue("배달대행료");
		row.createCell(cellCount++).setCellValue("할증료");
		row.createCell(cellCount++).setCellValue("할증사유");
		row.createCell(cellCount++).setCellValue("배달총금액");
		row.createCell(cellCount++).setCellValue("거리");
		row.createCell(cellCount++).setCellValue("배달상태");

		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (EM_SETTLE_SPIDOR_DV_OMS sldo : excelList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue(sldo.getSTORE_ID());// 점포코드
			row.createCell(cellCount++).setCellValue(sldo.getSTORE_NM());// 점포명
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sldo.getDELIVER_DATE()));// 배달일자
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sldo.getRECEIPT_TIME()));// 접수시간
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sldo.getCOMPLET_TIME()));// 완료시간
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(sldo.getCANCEL_TIME()));// 취소시간
			row.createCell(cellCount++).setCellValue(sldo.getORD_ID());// 주문번호
			row.createCell(cellCount++).setCellValue(sldo.getDELIVERY_AGENCY_FEE());// 배달대행료
			row.createCell(cellCount++).setCellValue(sldo.getPREMIUM_FEE());// 할증료
			row.createCell(cellCount++).setCellValue(sldo.getPREMIUM_REASON());// 할증사유
			row.createCell(cellCount++).setCellValue(sldo.getDELIVERY_TOT_AMT());// 배달총금액
			row.createCell(cellCount++).setCellValue(sldo.getDISTANCE());// 거리
			row.createCell(cellCount++).setCellValue(sldo.getDELIVERY_STATE());// 배달상태

			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (e == 7 || e == 8 || e == 10) {
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

		String fileName;
		if (type) {
			fileName = "EMART24_OMS_스파이더_정산_" + getNowDatetime + ".xlsx";
		} else {
			fileName = "EMART24_OMS_스파이더_정산오류_" + getNowDatetime + ".xlsx";
		}

		OutputStream os = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 클라이언트 측 다운로드
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

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
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}
	
	public HttpServletResponse getExcelDownload_CHAIN(List<EM_SETTLE_CHAINLOGIS_DV_OMS> getData, boolean type,
			HttpServletResponse response) {
		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush
		
		// header CellStyle 작성
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);
		
		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);
		
		// 숫자 포멧
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);
		
		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 5;
		
		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("체인로지스 정산리스트");
		
		sheet.setColumnWidth(0, 15 * 256);// 주문일자
		sheet.setColumnWidth(1, 20 * 256);// 주문번호
		sheet.setColumnWidth(2, 15 * 256);// 배달일자
		sheet.setColumnWidth(3, 25 * 256);// 예약번호
		sheet.setColumnWidth(4, 15 * 256);// 점포명
		sheet.setColumnWidth(5, 15 * 256);// 배달총금액(부가세포함)
		
		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);
		
		// 스타일 적용
		row.createCell(cellCount++).setCellValue("주문일자");
		row.createCell(cellCount++).setCellValue("주문번호");
		row.createCell(cellCount++).setCellValue("배달일자");
		row.createCell(cellCount++).setCellValue("예약번호");
		row.createCell(cellCount++).setCellValue("점포명");
		row.createCell(cellCount++).setCellValue("배달총금액(부가세포함)");
		
		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}
		
		// 데이터 Cell 생성
		for (EM_SETTLE_CHAINLOGIS_DV_OMS scdo : getData) {
			
			cellCount = 0;
			
			row = sheet.createRow(rowCount++);
			
			row.setHeight((short) 350);
			
	        row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(scdo.getORD_DATE()));// 주문일자
	        row.createCell(cellCount++).setCellValue(scdo.getORD_ID());// 주문번호
	        row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(scdo.getDELIVER_DATE()));// 배달일자
	        row.createCell(cellCount++).setCellValue(scdo.getRESERVATION_NO());// 예약번호
	        row.createCell(cellCount++).setCellValue(scdo.getSTORE_NM());// 점포명
	        row.createCell(cellCount++).setCellValue(scdo.getDELIVERY_TOT_AMT());// 배달총금액(부가세포함)
			
			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (e == 5) {
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
		
		String fileName;
		if (type) {
			fileName = "EMART24_OMS_체인로지스_정산_" + getNowDatetime + ".xlsx";
		} else {
			fileName = "EMART24_OMS_체인로지스_정산오류_" + getNowDatetime + ".xlsx";
		}
		
		OutputStream os = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// 클라이언트 측 다운로드
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
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
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}

	public HttpServletResponse getExcelDownload_TOTAL(List<EM_DELIVERY_TOTAL_OMS> excelList,
			HttpServletResponse response) {
		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = (XSSFCellStyle) wb.createCellStyle();

		// 배경 색 설정
		headStyle.setFillForegroundColor(new XSSFColor(new byte[] { (byte) 231, (byte) 240, (byte) 248 }, null));
		headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// border값 지정
		headStyle.setBorderBottom(BorderStyle.THIN);
		headStyle.setBottomBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		headStyle.setBorderRight(BorderStyle.THIN);
		headStyle.setRightBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		headStyle.setBorderTop(BorderStyle.THIN);
		headStyle.setTopBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		headStyle.setBorderLeft(BorderStyle.THIN);
		headStyle.setLeftBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));

		// 글자 중앙 정렬
		headStyle.setAlignment(HorizontalAlignment.CENTER); // 가로 중앙
		headStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 세로 중앙

		XSSFFont headerFont = (XSSFFont) wb.createFont();
		headerFont.setFontName("Microsoft JhengHei");
		headerFont.setFontHeight((short) 200);
		headerFont.setColor(new XSSFColor(new byte[] { (byte) 85, (byte) 85, (byte) 85 }, null));
		headerFont.setBold(true);

		headStyle.setFont(headerFont);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

		// TOTAL CellStyle 작성
		XSSFCellStyle totStyle = (XSSFCellStyle) wb.createCellStyle();

		// 배경 색 설정
		totStyle.setFillForegroundColor(new XSSFColor(new byte[] { (byte) 155, (byte) 209, (byte) 230 }, null));
		totStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// border값 지정
		totStyle.setBorderBottom(BorderStyle.THIN);
		totStyle.setBottomBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		totStyle.setBorderRight(BorderStyle.THIN);
		totStyle.setRightBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		totStyle.setBorderTop(BorderStyle.THIN);
		totStyle.setTopBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		totStyle.setBorderLeft(BorderStyle.THIN);
		totStyle.setLeftBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		totStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

		XSSFFont totFont = (XSSFFont) wb.createFont();
		totFont.setFontName("Microsoft JhengHei");
		totFont.setFontHeight((short) 200);
		totFont.setColor(new XSSFColor(new byte[] { (byte) 51, (byte) 51, (byte) 51 }, null));
		totFont.setBold(true);

		totStyle.setFont(totFont);

		// TOTAL CellStyle 작성
		XSSFCellStyle totStyle2 = (XSSFCellStyle) wb.createCellStyle();

		// 배경 색 설정
		totStyle2.setFillForegroundColor(new XSSFColor(new byte[] { (byte) 155, (byte) 209, (byte) 230 }, null));
		totStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// border값 지정
		totStyle2.setBorderBottom(BorderStyle.THIN);
		totStyle2.setBottomBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		totStyle2.setBorderRight(BorderStyle.THIN);
		totStyle2.setRightBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		totStyle2.setBorderTop(BorderStyle.THIN);
		totStyle2.setTopBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		totStyle2.setBorderLeft(BorderStyle.THIN);
		totStyle2.setLeftBorderColor(new XSSFColor(new byte[] { (byte) 0, (byte) 0, (byte) 0 }, null));
		totStyle2.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

		// 글자 중앙 정렬
		totStyle2.setAlignment(HorizontalAlignment.CENTER); // 가로 중앙
		totStyle2.setVerticalAlignment(VerticalAlignment.CENTER); // 세로 중앙

		XSSFFont totFont2 = (XSSFFont) wb.createFont();
		totFont2.setFontName("Microsoft JhengHei");
		totFont2.setFontHeight((short) 200);
		totFont2.setColor(new XSSFColor(new byte[] { (byte) 51, (byte) 51, (byte) 51 }, null));
		totFont2.setBold(true);

		totStyle2.setFont(totFont2);

		// 숫자 포멧
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 9;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("배달 정산 집계조회");

		sheet.setColumnWidth(0, 20 * 256);// 구분
		sheet.setColumnWidth(1, 20 * 256);// 플랫폼 매출액
		sheet.setColumnWidth(2, 20 * 256);// 추가입금액
		sheet.setColumnWidth(3, 20 * 256);// 플랫폼수수료2
		sheet.setColumnWidth(4, 20 * 256);// 고객부담금
		sheet.setColumnWidth(5, 20 * 256);// 배달료 행사지원금
		sheet.setColumnWidth(6, 20 * 256);// 추가부담금
		sheet.setColumnWidth(7, 20 * 256);// 배달대행료
		sheet.setColumnWidth(8, 20 * 256);// 배달지원금
		sheet.setColumnWidth(9, 20 * 256);// KIS중계수수료

		sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 4, 4));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 5, 5));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 6, 6));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 7, 7));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 8, 8));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 9, 9));

		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 300);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("구분");
		row.createCell(cellCount++).setCellValue("플랫폼 매출액");
		row.createCell(cellCount++).setCellValue("추가입금액");
		row.createCell(cellCount++).setCellValue("플랫폼수수료2");
		row.createCell(cellCount++).setCellValue("고객부담금");
		row.createCell(cellCount++).setCellValue("배달료 행사지원금");
		row.createCell(cellCount++).setCellValue("추가부담금");
		row.createCell(cellCount++).setCellValue("배달대행료");
		row.createCell(cellCount++).setCellValue("배달지원금");
		row.createCell(cellCount++).setCellValue("KIS중계수수료");

		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 300);
		cellCount = 0;

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("상세");
		row.createCell(cellCount++).setCellValue("");
		row.createCell(cellCount++).setCellValue("");
		row.createCell(cellCount++).setCellValue("");
		row.createCell(cellCount++).setCellValue("");
		row.createCell(cellCount++).setCellValue("");
		row.createCell(cellCount++).setCellValue("");
		row.createCell(cellCount++).setCellValue("");
		row.createCell(cellCount++).setCellValue("");
		row.createCell(cellCount++).setCellValue("");

		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (EM_DELIVERY_TOTAL_OMS edto : excelList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue(edto.getCPN_NM());// 구분
			row.createCell(cellCount++).setCellValue(CommonFunc.integerNullCheck(edto.getPLATFORM_AMT()));// 플랫폼 매출액
			row.createCell(cellCount++).setCellValue(CommonFunc.integerNullCheck(edto.getPLATFORM_FEE()));// 플랫폼수수료1
			row.createCell(cellCount++).setCellValue(CommonFunc.integerNullCheck(edto.getPLATFORM_FEE2()));// 플랫폼수수료2
			row.createCell(cellCount++).setCellValue(CommonFunc.integerNullCheck(edto.getCUSTORMER_CHARGE()));// 고객부담금
			row.createCell(cellCount++).setCellValue(CommonFunc.integerNullCheck(edto.getDELIVER_EVENT_FUND()));// 배달료
			row.createCell(cellCount++).setCellValue(CommonFunc.integerNullCheck(edto.getADDITIONAL_CHARGE()));// 추가부담금
			row.createCell(cellCount++).setCellValue(CommonFunc.integerNullCheck(edto.getDELIVERY_AGENCY_FEE()));// 배달대행료
			row.createCell(cellCount++).setCellValue(CommonFunc.integerNullCheck(edto.getDELIVER_SUPPORT_FUND()));// 배달지원금
			row.createCell(cellCount++).setCellValue(CommonFunc.integerNullCheck(edto.getKIS_FEE()));// KIS중계수수료

			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (edto.getCPN_NM().equals("합계")) {
					if (e == 0) {
						row.getCell(e).setCellStyle(totStyle2);
						continue;
					} else {
						row.getCell(e).setCellStyle(totStyle);
						continue;

					}
				} else {
					if (e >= 1 && e <= 9) {
						row.getCell(e).setCellStyle(numStyle);
						continue;
					}
				}
				row.getCell(e).setCellStyle(bodyStyle);
			}
		}

		// 컨텐츠 타입과 파일명 지정

		Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다

		// 년월일시분초 14자리 포멧
		SimpleDateFormat simple_format = new SimpleDateFormat("yyyyMMdd_HHmmss");

		String getNowDatetime = simple_format.format(date_now);
		
		String fileName = "EMART24_OMS_배달정산집계조회_" + getNowDatetime + ".xlsx";

		OutputStream os = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 클라이언트 측 다운로드
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

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
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}

	public HttpServletResponse getExcelSynthesis_Order(HashMap<String, String> paramMap, HttpServletResponse response) {

		List<EM_SYNTH_ORD_CH_VIEW> excelList = dao.getExcelSynthesis_Order(paramMap);

		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

		// 숫자 포멧
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 1;
		int cellCount = 0;
		int columnCnt = 9;
		Cell cell=null;
		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("주문채널사 정산리스트");

		sheet.setColumnWidth(0, 10 * 256); // 업체코드
		sheet.setColumnWidth(1, 25 * 256); // 주문번호
		sheet.setColumnWidth(2, 15 * 256); // 주문일자
		sheet.setColumnWidth(3, 15 * 256); // 상품가합계
		sheet.setColumnWidth(4, 15 * 256); // 배달료
		sheet.setColumnWidth(5, 20 * 256); // 배달료행사지원금
		sheet.setColumnWidth(6, 15 * 256); // 플랫폼건수
		sheet.setColumnWidth(7, 15 * 256); // 추가부담금
		sheet.setColumnWidth(8, 20 * 256); // 플랫폼수수료1
		sheet.setColumnWidth(9, 20 * 256); // 플랫폼수수료2
		
		
		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("업체코드");
		row.createCell(cellCount++).setCellValue("주문번호");
		row.createCell(cellCount++).setCellValue("주문일자");
		row.createCell(cellCount++).setCellValue("상품가합계");
		row.createCell(cellCount++).setCellValue("배달료");
		row.createCell(cellCount++).setCellValue("배달료행사지원금");
		row.createCell(cellCount++).setCellValue("플랫폼건수");
		row.createCell(cellCount++).setCellValue("추가부담금");
		row.createCell(cellCount++).setCellValue("플랫폼수수료1");
		row.createCell(cellCount++).setCellValue("플랫폼수수료2");
		
		
		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}
//		row = sheet.createRow(2);
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);
		row.createCell(0).setCellValue("01");
		row.createCell(1).setCellValue("1077256239");
		row.createCell(2).setCellValue("2023-02-01");
		row.createCell(3).setCellValue("21,980");
		row.createCell(4).setCellValue("0");
		row.createCell(5).setCellValue("0");
		row.createCell(6).setCellValue("1");
		row.createCell(7).setCellValue("0");
		row.createCell(8).setCellValue("1,123");
		row.createCell(9).setCellValue("0");
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}
		// 데이터 Cell 생성
		for (EM_SYNTH_ORD_CH_VIEW ordView : excelList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue(ordView.getCHNEL_CD()); // 업체코드
			row.createCell(cellCount++).setCellValue(ordView.getORD_ID()); // 주문번호
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(ordView.getORD_DATE())); // 주문일자
			row.createCell(cellCount++).setCellValue(ordView.getORD_AMT()); // 상품가합계
			row.createCell(cellCount++).setCellValue(ordView.getDELIVER_FEE()); // 배달료
			row.createCell(cellCount++).setCellValue(ordView.getDELIVER_EVENT_FUND()); // 배달료행사지원금
			row.createCell(cellCount++).setCellValue(ordView.getPLATFORM_CNT()); // 플랫폼건수
			row.createCell(cellCount++).setCellValue(ordView.getLEVY()); // 추가부담금
			row.createCell(cellCount++).setCellValue(ordView.getPLATFORM_FEE()); // 플랫폼수수료1
			row.createCell(cellCount++).setCellValue(ordView.getPLATFORM_FEE2()); // 플랫폼수수료2

			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (e == 3 || e == 4 || e == 5 || e == 7 || e == 8 || e == 9) {
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

		String fileName;
		if(paramMap.get("stype").equals("all")) {
			fileName = "EMART24_OMS_주문정산_" + getNowDatetime + ".xlsx";
		}else if(paramMap.get("stype").equals("nomal")){
			fileName = "EMART24_OMS_주문정산_" + getNowDatetime +"_일반매장" + ".xlsx";
		}else {
			fileName = "EMART24_OMS_주문정산_" + getNowDatetime +"_BSD" + ".xlsx";
		}
		

		OutputStream os = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 클라이언트 측 다운로드
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

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
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}

	public HttpServletResponse getExcelSynthesis_Delivery(HashMap<String, String> paramMap,
			HttpServletResponse response) {
		List<EM_SYNTH_DV_CH_VIEW> excelList = dao.getExcelSynthesis_Delivery(paramMap);

		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

		// 숫자 포멧
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 1;
		int cellCount = 0;
		int columnCnt = 6;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("배달대행사 정산리스트");

		sheet.setColumnWidth(0, 15 * 256); // 업체코드
		sheet.setColumnWidth(1, 15 * 256); // 배달일자
		sheet.setColumnWidth(2, 15 * 256); // 배달시간
		sheet.setColumnWidth(3, 25 * 256); // 주문번호
		sheet.setColumnWidth(4, 15 * 256); // 배달대행료
		sheet.setColumnWidth(5, 15 * 256); // 추가대행료
		sheet.setColumnWidth(6, 10 * 256); // 배달건수

		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("업체코드");
		row.createCell(cellCount++).setCellValue("배달일자");
		row.createCell(cellCount++).setCellValue("배달시간");
		row.createCell(cellCount++).setCellValue("주문번호");
		row.createCell(cellCount++).setCellValue("배달대행료");
		row.createCell(cellCount++).setCellValue("추가대행료");
		row.createCell(cellCount++).setCellValue("배달건수");

		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}
		
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);
		row.createCell(0).setCellValue("01");
		row.createCell(1).setCellValue("2023-01-01");
		row.createCell(2).setCellValue("11:11");
		row.createCell(3).setCellValue("00000000001080739428");
		row.createCell(4).setCellValue("4950");
		row.createCell(5).setCellValue("0");
		row.createCell(6).setCellValue("1");

		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (EM_SYNTH_DV_CH_VIEW dvView : excelList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue(dvView.getCHNEL_CD()); // 업체코드
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(dvView.getORD_DATE())); // 배달일자
			row.createCell(cellCount++).setCellValue(CommonFunc.datetimeFormat(dvView.getRECEIPT_TIME())); // 배달시간
			row.createCell(cellCount++).setCellValue(dvView.getORD_ID()); // 주문번호
			row.createCell(cellCount++).setCellValue(dvView.getDELIVER_FEE()); // 배달대행료
			row.createCell(cellCount++).setCellValue(dvView.getADD_DELIVER_FEE()); // 추가대행료
			row.createCell(cellCount++).setCellValue(dvView.getDELIVERY_CNT()); // 배달건수

			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (e == 4 || e == 5) {
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

		String fileName;

		fileName = "EMART24_OMS_배달정산_" + getNowDatetime + ".xlsx";

		OutputStream os = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 클라이언트 측 다운로드
		/* response.reset(); *///초기화 왜 하는지 모름 이거주석안달면 에러남
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

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
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}// 요기요 배달 엑셀셈플 다운로드
	public List<EM_SETTLE_YOGIYO_DV_ORD_OMS> getExcel_Sample_Data_YGYD(HashMap<String, Object> paramMap) {
		return dao.getExcel_Sample_Data_YGYD(paramMap);
	}
	
	
	public HttpServletResponse getExcel_Sample_Download_YGYD(List<EM_SETTLE_YOGIYO_DV_ORD_OMS> excelList, boolean type,
			HttpServletResponse response) {

		// 워크북 생성
		SXSSFWorkbook wb = new SXSSFWorkbook(100);// 메모리 행 100개로 제한, 초과 시 Disk로 flush

		// header CellStyle 작성
		XSSFCellStyle headStyle = ExcelStyle.headerStyle(wb);

		// body CellStyle 작성
		XSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(wb);

		// 숫자 포멧
		XSSFCellStyle numStyle = ExcelStyle.numStyle(wb);

		// 엑셀 파일 작성
		SXSSFRow row = null; // 행
		int rowCount = 0;
		int cellCount = 0;
		int columnCnt = 36;

		// SXSSFSheet 생성
		SXSSFSheet sheet = wb.createSheet("요기요 정산리스트");

		sheet.setColumnWidth(0, 25 * 256);// 주문일시
		sheet.setColumnWidth(1, 25 * 256);// 환불일시
		sheet.setColumnWidth(2, 12 * 256);// 지급예정 일자
		sheet.setColumnWidth(3, 12 * 256);// 사업자번호
		sheet.setColumnWidth(4, 25 * 256);// 음식점명
		sheet.setColumnWidth(5, 10 * 256);// 매장코드
		sheet.setColumnWidth(6, 15 * 256);// 주문ID
		sheet.setColumnWidth(7, 20 * 256);// 주문번호
		sheet.setColumnWidth(8, 10 * 256);// 상품가 합계
		sheet.setColumnWidth(9, 10 * 256);// 최소주문 추가결제액
		sheet.setColumnWidth(10, 10 * 256);// 배달료
		sheet.setColumnWidth(11, 10 * 256);// 주문 총액
		sheet.setColumnWidth(12, 10 * 256);// 주문 금액 구분 : 온라인 주문
		sheet.setColumnWidth(13, 10 * 256);// 주문 금액 구분 : 현장 주문
		sheet.setColumnWidth(14, 10 * 256);// 레스토랑 자체할인
		sheet.setColumnWidth(15, 10 * 256);// 프로모션 할인금액 : 요기요 부담
		sheet.setColumnWidth(16, 10 * 256);// 프로모션 할인금액 : 프랜차이즈 부담
		sheet.setColumnWidth(17, 10 * 256);// 프로모션 할인금액 : 레스토랑 부담
		sheet.setColumnWidth(18, 10 * 256);// 주문중개이용료 : 주문중개이용료율(+VAT)
		sheet.setColumnWidth(19, 10 * 256);// 주문중개이용료 : 주문중개이용료 총액
		sheet.setColumnWidth(20, 10 * 256);// 결제이용료 : 결제이용료율(+VAT)
		sheet.setColumnWidth(21, 10 * 256);// 결제이용료 : 결제이용료 총액
		sheet.setColumnWidth(22, 10 * 256);// 배달대행이용료 : 배달대행이용료 총액
		sheet.setColumnWidth(23, 10 * 256);// 이용료 전체 합계
		sheet.setColumnWidth(24, 10 * 256);// OD배달료 매출 : 레스토랑 자체펀딩 총액
		sheet.setColumnWidth(25, 10 * 256);// 요타임딜이용료 : 요타임딜이용료 총액
		sheet.setColumnWidth(26, 10 * 256);// 추천광고이용료:추천광고이용료 총액
		sheet.setColumnWidth(27, 10 * 256);// 정산 금액
		sheet.setColumnWidth(28, 10 * 256);// 결제유형
		sheet.setColumnWidth(29, 10 * 256);// 결제유형 상세
		sheet.setColumnWidth(30, 10 * 256);// 결제형태
		sheet.setColumnWidth(31, 10 * 256);// 결제형태 상세
		sheet.setColumnWidth(32, 15 * 256);// 배달 유형(VD/OD)
		sheet.setColumnWidth(33, 10 * 256);// 프랜차이즈 ID
		sheet.setColumnWidth(34, 25 * 256);// 회사명
		sheet.setColumnWidth(35, 15 * 256);// 사업자명
		sheet.setColumnWidth(36, 35 * 256);// 배달주소1

		// 엑셀 내용 작성
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);
		row.setHeight((short) 420);

		// 스타일 적용
		row.createCell(cellCount++).setCellValue("주문일시");
		row.createCell(cellCount++).setCellValue("환불일시");
		row.createCell(cellCount++).setCellValue("지급예정 일자");
		row.createCell(cellCount++).setCellValue("사업자번호");
		row.createCell(cellCount++).setCellValue("음식점명");
		row.createCell(cellCount++).setCellValue("매장코드");
		row.createCell(cellCount++).setCellValue("주문ID");
		row.createCell(cellCount++).setCellValue("주문번호");
		row.createCell(cellCount++).setCellValue("상품가 합계");
		row.createCell(cellCount++).setCellValue("최소주문 추가결제액");
		row.createCell(cellCount++).setCellValue("배달료");
		row.createCell(cellCount++).setCellValue("주문 총액");
		row.createCell(cellCount++).setCellValue("주문 금액 구분 : 온라인 주문");
		row.createCell(cellCount++).setCellValue("주문 금액 구분 : 현장 주문");
		row.createCell(cellCount++).setCellValue("레스토랑 자체할인");
		row.createCell(cellCount++).setCellValue("프로모션 할인금액 : 요기요 부담");
		row.createCell(cellCount++).setCellValue("프로모션 할인금액 : 프랜차이즈 부담");
		row.createCell(cellCount++).setCellValue("프로모션 할인금액 : 레스토랑 부담");
		row.createCell(cellCount++).setCellValue("주문중개이용료 : 주문중개이용료율(+VAT)");
		row.createCell(cellCount++).setCellValue("주문중개이용료 : 주문중개이용료 총액");
		row.createCell(cellCount++).setCellValue("결제이용료 : 결제이용료율(+VAT)");
		row.createCell(cellCount++).setCellValue("결제이용료 : 결제이용료 총액");
		row.createCell(cellCount++).setCellValue("배달대행이용료 : 배달대행이용료 총액");
		row.createCell(cellCount++).setCellValue("이용료 전체 합계");
		row.createCell(cellCount++).setCellValue("OD배달료 매출 : 레스토랑 자체펀딩 총액");
		row.createCell(cellCount++).setCellValue("요타임딜이용료 : 요타임딜이용료 총액");
		row.createCell(cellCount++).setCellValue("추천광고이용료:추천광고이용료 총액");
		row.createCell(cellCount++).setCellValue("정산 금액");
		row.createCell(cellCount++).setCellValue("결제유형");
		row.createCell(cellCount++).setCellValue("결제유형 상세");
		row.createCell(cellCount++).setCellValue("결제형태");
		row.createCell(cellCount++).setCellValue("결제형태 상세");
		row.createCell(cellCount++).setCellValue("배달 유형(VD/OD)");
		row.createCell(cellCount++).setCellValue("프랜차이즈 ID");
		row.createCell(cellCount++).setCellValue("회사명");
		row.createCell(cellCount++).setCellValue("사업자명");
		row.createCell(cellCount++).setCellValue("배달주소1");

		// 스타일 적용
		for (int e = 0; e <= columnCnt; e++) {
			row.getCell(e).setCellStyle(headStyle);
		}

		// 데이터 Cell 생성
		for (EM_SETTLE_YOGIYO_DV_ORD_OMS sydo : excelList) {

			cellCount = 0;

			row = sheet.createRow(rowCount++);

			row.setHeight((short) 350);

			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 주문일시
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 환불일시
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 지급예정 일자
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 사업자번호
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 음식점명 /
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 매장코드
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 주문ID
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 주문번호
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 상품가 합계
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 최소주문 추가결제액 /
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 배달료
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 주문 총액
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 주문 금액 구분 : 온라인 주문
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 주문 금액 구분 : 현장 주문
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 레스토랑 자체할인 /
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 프로모션 할인금액 : 요기요 부담
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 프로모션 할인금액 : 프랜차이즈 부담
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 프로모션 할인금액 : 레스토랑 부담
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 주문중개이용료 : 주문중개이용료율(+VAT)
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 주문중개이용료 : 주문중개이용료 총액 /
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 결제이용료 : 결제이용료율(+VAT)
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 결제이용료 : 결제이용료 총액
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 배달대행이용료 : 배달대행이용료 총액
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 이용료 전체 합계
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // OD배달료 매출 : 레스토랑 자체펀딩 총액 /
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 요타임딜이용료 : 요타임딜이용료 총액
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23");  //추천광고이용료:추천광고이용료 총액
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 정산 금액
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 결제유형
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 결제유형 상세 /
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 결제형태
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 결제형태 상세
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 배달 유형(VD/OD)
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 프랜차이즈 ID
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 회사명 /
			row.createCell(cellCount++).setCellValue("2023-01-01 10:11:23"); // 사업자명
			row.createCell(cellCount++).setCellValue(sydo.getDELIVERY_ADDRESS1()); // 배달주소1

			// 스타일 적용
			for (int e = 0; e <= columnCnt; e++) {
				if (e == 8 || e == 9 || e == 10 || e == 11 || e == 12 || e == 13 || e == 14 || e == 15 || e == 16 || e == 17
						|| e == 18 ||e == 19 || e == 20 || e == 21 || e == 22 || e == 23 || e == 24 || e == 25 || e == 26 || e == 27 || e == 28 || e == 29) {
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

		String fileName;
		if (type) {
			fileName = "EMART24_OMS_요기요배달_정산_" + getNowDatetime + ".xlsx";
		} else {
			fileName = "EMART24_OMS_요기요배달_정산오류_" + getNowDatetime + ".xlsx";
		}

		OutputStream os = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 클라이언트 측 다운로드
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

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
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}	

	// 엑셀 데이터 다운로드
	// 요기요 배달
	
	public List<EM_RELAY_TOTAL_OMS> getExcelData_RELAY(HashMap<String, Object> paramMap) {
		return dao.getExcelData_RELAY(paramMap);
	}

	// 요기요 배달
	public List<EM_SETTLE_YOGIYO_DV_ORD_OMS> getExcelData_YGYD(HashMap<String, Object> paramMap) {
		return dao.getExcelData_YGYD(paramMap);
	}

	// 요기요 픽업
	public List<EM_SETTLE_YOGIYO_PU_ORD_OMS> getExcelData_YGYP(HashMap<String, Object> paramMap) {
		return dao.getExcelData_YGYP(paramMap);
	}

	// 네이버
	public List<EM_SETTLE_NAVER_ORD_OMS> getExcelData_NAVER(HashMap<String, Object> paramMap) {
		return dao.getExcelData_NAVER(paramMap);
	}

	// 카카오
	public List<EM_SETTLE_KAKAO_DV_ORD_OMS> getExcelData_KAKAO(HashMap<String, Object> paramMap) {
		return dao.getExcelData_KAKAO(paramMap);
	}

	// KIS
	public List<EM_SETTLE_KISAPP_ORD_OMS> getExcelData_KIS(HashMap<String, Object> paramMap) {
		return dao.getExcelData_KIS(paramMap);
	}
	
	// 배달의민족
	public List<EM_SETTLE_BAEMIN_DV_ORD_OMS> getExcelData_BAEMIN(HashMap<String, Object> paramMap) {
		return dao.getExcelData_BAEMIN(paramMap);
	}
	// 이마트24(인앱)
	public List<EM_SETTLE_EMART24_DV_PU_OMS> getExcelData_EMART24(HashMap<String, Object> paramMap) {
		return dao.getExcelData_EMART24(paramMap);
	}

	// 부릉
	public List<EM_SETTLE_VROONG_DV_OMS> getExcelData_VROONG(HashMap<String, Object> paramMap) {
		return dao.getExcelData_VROONG(paramMap);
	}

	// 바로고
	public List<EM_SETTLE_BAROGO_DV_OMS> getExcelData_BAROGO(HashMap<String, Object> paramMap) {
		return dao.getExcelData_BAROGO(paramMap);
	}

	// 생각대로
	public List<EM_SETTLE_LOGIALL_DV_OMS> getExcelData_LOGIALL(HashMap<String, Object> paramMap) {
		return dao.getExcelData_LOGIALL(paramMap);
	}

	// 딜버
	public List<EM_SETTLE_DEALVER_DV_OMS> getExcelData_DEALVER(HashMap<String, Object> paramMap) {
		return dao.getExcelData_DEALVER(paramMap);
	}

	// 스파이더크래프트
	public List<EM_SETTLE_SPIDOR_DV_OMS> getExcelData_SPIDOR(HashMap<String, Object> paramMap) {
		return dao.getExcelData_SPIDOR(paramMap);
	}
	
	// 체인로지스
	public List<EM_SETTLE_CHAINLOGIS_DV_OMS> getExcelData_CHAIN(HashMap<String, Object> paramMap) {
		return dao.getExcelData_CHAIN(paramMap);
	}

	// 배달정산합계
	public List<EM_DELIVERY_TOTAL_OMS> getExcelData_TOTAL(HashMap<String, Object> paramMap) {
		return dao.getExcelData_TOTAL(paramMap);
	}
	// 주문채널사 합계
	public List<EM_SYNTH_ORD_CH_VIEW> getExcelSynthesis_Order(HashMap<String, String> paramMap) {
		return dao.getExcelSynthesis_Order(paramMap);
	}

	// 배달대행사 합계
	public List<EM_SYNTH_DV_CH_VIEW> getExcelSynthesis_Delivery(HashMap<String, String> paramMap) {
		return getExcelSynthesis_Delivery(paramMap);
	}


 

	
	
}