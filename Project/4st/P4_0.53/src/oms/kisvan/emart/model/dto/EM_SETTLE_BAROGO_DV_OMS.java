package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_SETTLE_BAROGO_DV_OMS {
	private String ORD_DATE; // 주문일자(PK)
	private String ORD_ID; // 주문번호(PK)
	private String RECEIPT_DATE; // 접수일자(PK)
	private String RECEIPT_NO; // 접수번호(PK)
	private Integer SET_BAROGO_DV_SEQ; // 바로고 배달 시퀀스(PK)
	private String RECEIPT_TIME; // 접수시간
	private String COMPLET_TIME; // 완료시간
	private String CANCEL_TIME; // 최소시간
	private String ORD_ISSUER_NM; // 발주가맹점명
	private String DISTANCE; // 거리(km)
	private Integer BASIC_FARE_AMT; // 기본운임
	private Integer DISTANCE_FARE_AMT; // 거리운임
	private Integer PICKUP_PREMIUM_AMT; //픽업할증
	private Integer LATE_NIGTH_PREMIUM_AMT; // 심야할증
	private Integer RAIN_PREMIUM_AMT; // 우천할증
	private Integer ADD_PREMIUM_AMT; // 추가할증
	private Integer AREA_PREMIUM_AMT; // 구역할증
	private Integer ITEM_PREMIUM_AMT; // 상품할증
	private Integer DELIVERY_FARE_AMT; // 배송운임
	private Integer VAT_AMT; // VAT
	private Integer VAT_INCLUDE_AMT; // VAT포함
	private String RECEIPT_CH; // 접수채널
	private String BIGO; // 비고
	private Integer USER_CD; // 사용자코드
	private Timestamp REG_DATE; // 등록일시

	// 추가 컬럼
	private Integer RN; // 순번
	private String USER_NM; // 등록자명

}
