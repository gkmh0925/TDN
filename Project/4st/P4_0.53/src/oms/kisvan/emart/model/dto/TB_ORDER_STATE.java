package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class TB_ORDER_STATE {
	private String ORD_ID; // *주문ID
	private String STORE_ID; // *가맹점 상점ID*
	private String ORD_STATE; // *주문상태*
	private String FRANCHISE_CODE; // *가맹점코드*
	private String VANDOR_CODE; // *요기요-Vendor*
	private Integer DELIVER_FEE; // *배송료*
	private String ORD_ADDR; // *배송지 주소[지번]*
	private String ORD_ADDR_DETAIL; // *배성지 주소[지번]상세*
	private String ORD_LAND_ADDR; // *배송지 주소[행정]*
	private String LAND_ADDR_DETAIL; // *배송지 주소[상세]*
	private String PHONE; // *배송 연락처
	private String LOC_LAT; // *위도정보*
	private String LOC_LNG; // *경도정보*
	private String ORD_MSG; // *고객요청사항*
	private Integer ACT_AMT; // *결제금액*
	private Integer TOT_AMT; // *합계금액*
	private Integer TOT_VAT; // *부가세*
	private String PAY_TYPE; // *결제방식*
	private String ORD_TYPE; // *주문방식*
	private String ORD_TIME; // *결제시간*
	private String PICKUP_CODE; // *픽업코드*
	private String EXPECTED_PICKUP_TIME; // *픽업예상시간*
	private String ORD_NOTICE; // *주문공지*
	private String DELV_YN; // *배송가능여부*
	private String DELV_TIME; // *배송 픽업예상시간*
	private String ORD_MEMO; // *주문메모*
	private Timestamp REGDATE; // *등록일자*
	private String RST_CD; // *처리결과코드*
	private String RST_MSG; // *처리결과메시지*
	private Timestamp MODI_DATE; // *최종변경일시*
	private String DELIVERY_ID; // *배송번호*
	private String DELIVERY_VENDOR_CODE; // *배송업체코드*
	private String AUTO_CANCEL_ALARM; // *자동취소알람상태*
	private String CAR_NUMBER; // *차량번호*
	private String BIZITEMID; // *BIZITEM*
	private String SALE_STORE; // *매출발생점포코드*
	private String DTORD_NO; // *상세주문번호*
	private String SETTLE_NO; // *네이버주문번호*
	private String STATE_RESULT; // 정산상태구분

	private String STORE_NM; // *매장명*
	private String ORDER_CH_NM; // *주문채널사명*
	private String DLV_CH_NM; // *배달대행사명*
	private String DLV_STATE; // *배달 대행 상태*
	private String PICKUP_NAME; // *픽업코드명*

	private String REGDATE_ST; // *등록일자포맷*
	private String MODI_DATE_ST; // *최종변경일시포맷*

	// 주문통계
	private Integer TOTAL_COUNT; // 전체건수
	private long TOTAL_AMT; // 주문 합계
	private Integer COMMIT_COUNT; // 성공 건수
	private long COMMIT_AMT;// 승인금액
	private Integer CANCEL_COUNT;// 취소 건수
	private long CANCEL_AMT;// 취소금액

	private Integer RN; // 행 넘버
	
	// 230119 컬럼추가 
	private String MFC_TP_CD; // *MFC구분* (보류)

	
}