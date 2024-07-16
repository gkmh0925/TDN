package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_SETTLE_NAVER_ORD_OMS {
	private Integer RN; // 순번
	private String ORD_DATE; // 정산기준일(PK)
	private String SETTLE_NO; // 주문번호(PK)
	private String ITEM_ORD_ID; // 상품주문번호(PK)
	private String CH_CD; // 채널사코드(PK)
	private Integer SET_NAVER_SEQ; // 네이버 시퀀스(PK)
	private String GUBUN; // 구분
	private String ITEM_NM; // 상품명
	private String BUYER_NM; // 구매자명
	private String SETTLE_EXPECTED_DATE; // 정산예정일
	private String SETTLE_COMPLET_DATE; // 정산완료일
	private String TAX_RETURN_DATE; // 세금신고기준일
	private Integer ACT_AMT; // 결제금액(A)
	private Integer NAVER_PAY_ORDER_CMS; // 네이버페이 예약주문관리 수수료(B)
	private Integer SALE_LINK_CMS; // 수수료금액
	private String SALE_LINK_CMS_GUBUN; // 매출연동수수료상세
	private Integer FREE_INSTALLMENT_FEE; // 무이자할부 수수료(E)
	private String SETTLE_STATE; // 정산상태
	private Integer USER_CD; // 사용자코드
	private Timestamp REG_DATE; // 등록일시

	// 추가 컬럼
	private String USER_NM; // 등록자명
	
	//23.01엑셀추가
	private String CMS_GUBUN; //수수료구분
	private String PAY_TYPE; //결제수단

}
