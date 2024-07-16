package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)

public class EM_SETTLE_EMART24_DV_PU_OMS {
	private Integer RN; // 순번
	private String ORD_DATE;// 주문일자(PK)
	private String ORD_ID;// 주문ID(PK)
	private String CH_ORD_NO;// 이마트앱주문번호(PK)
	private String CH_CD;// 채널사코드(PK)
	private String STORE_ID;// 점포코드(PK)
	private Integer SET_EMART24_DV_SEQ;// 이마트앱 배달 시퀀스(PK)
	private String DIVISION;// 구분
	private String STORE_NM;// 점포명
	private String CH_ORD_DT_NO;// 상세주문번호
	private String ORD_TIME;// 주문일시
	private Integer SALE_ITEM_AMT;// 상품가합계
	private Integer SALE_DELV_FEE;// 배달료
	private Integer SALE_PAY_AMT;// 결제금액
	private Integer CMS_VAT_INCLUDED;// 수수료(VAT포함)
	private Integer SETTLE_PAY_AMT;// 지급액
	private String NOTE;// 비고
	private Integer USER_CD;// 사용자코드
	private Timestamp REG_DATE;// 등록일시
	
	
	
	
	
	

	// 추가 컬럼
	private String USER_NM; // 등록자명







}
