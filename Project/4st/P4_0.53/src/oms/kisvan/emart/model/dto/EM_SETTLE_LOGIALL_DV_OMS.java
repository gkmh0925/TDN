package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_SETTLE_LOGIALL_DV_OMS {
	private String ORD_DATE; // 주문일자(PK)
	private String ORD_ID; // 주문번호(PK)
	private String STORE_ID; // 점포코드(PK)
	private String DELIVER_DATE; // 배달일자(PK)
	private Integer SET_LOGIALL_DV_SEQ; // 생각대로 배달 시퀀스(PK)
	private String STORE_NM; // 점포명
	private String RECEIPT_TIME; // 접수시간
	private String COMPLET_TIME; // 완료시간
	private String CANCEL_TIME; // 최소시간
	private Integer DELIVERY_AGENCY_FEE; // 배달대행료
	private Integer PREMIUM_FEE; // 할증료
	private String PREMIUM_REASON; // 할증사유
	private Integer DELIVERY_TOT_AMT; // 배달총금액
	private String DISTANCE; // 거리
	private String DELIVERY_STATE; // 배달상태
	private String USER_CD; // 사용자코드
	private Timestamp REG_DATE; // 등록일시

	// 추가 컬럼
	private Integer RN; // 순번
	private String USER_NM; // 등록자명
	
}
