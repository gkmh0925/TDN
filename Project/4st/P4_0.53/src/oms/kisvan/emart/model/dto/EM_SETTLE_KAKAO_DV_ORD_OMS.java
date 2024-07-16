package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
@Data
public class EM_SETTLE_KAKAO_DV_ORD_OMS {
	private String ORD_DATE;// 주문일자(PK)
	private String ORD_ID;// 주문ID(PK)
	private String CH_ORD_NO;// 카카오주문번호(PK)
	private String STORE_ID;// 매장코드(PK)
	private String CH_CD;// 채널사코드(PK)
	private Integer SET_KAKAO_DV_SEQ;// 카카오 배달 시퀀스(PK)
	private String BRAND_NM;// 브랜드명
	private String STORE_NM;// 매장명
	private String ORD_TIME;// 주문일시
	private String ADJUSTMENT_DATE;// 조정일자
	private String CMD_ORD_NO;// CMS주문번호
	private Integer ORD_AMT;// 주문금액
	private Integer DELIVER_FEE;// 배달료
	private Integer DIS_AMT;// 할인금액
	private Integer ACT_AMT;// 결제금액
	private Integer CANCEL_AMT;// 최소금액
	private String PAY_TYPE;// 결제수단
	private Integer PREPAID_CARD;// 선결제카드
	private Integer PREPAID_KAKAO_PAY;// 선결제카카오페이
	private Integer PREPAID_KAKAO_MONEY;// 선결제카카오머니
	private Integer AFTER_PAY;// 후결제
	private Integer BRAND_LEVY_AMT;// 브랜드 부담금
	private Integer KAKAO_LEVY_AMT;// 카카오 부담금
	private Integer FRANCHISEE_LEVY_AMT;// 가맹점 부담금
	private Integer ETC_LEVY_AMT;// 기타 부담금
	private Integer ORD_RELAY_CMS_SUM;// 주문중개 수수료 합
	private Integer DIS_RELAY_CMS_SUM;// 할인중개 수수료 합
	private Integer INICIS_CMS;// 아니시스 수수료
	private Integer KAKAO_PAY_CMS;// 카카오페이 수수료
	private Integer PG_CMS;// PG 수수료
	private Integer E_CUPON_CMS;// E쿠폰수수료
	private Integer TOT_CMS_AMT;// 수수료총액
	private Integer DEPOSIT_AMT;// 입급액
	private Integer USER_CD;// 사용자코드
	private Timestamp REG_DATE;// 등록일시

	// 추가 컬럼
	private Integer RN; // 순번
	private String USER_NM; // 등록자명
	//23.02 정산엑셀에추가
	private Integer CNT_ORD_RELAY_CMS;// CNT 주문중개 수수료
	private Integer KAKAO_ORD_RELAY_CMS;// 카카오 주문중개 수수료
	private Integer CNT_DIS_RELAY_CMS;// CNT 할인중개 수수료
	private Integer KAKAO_DIS_RELAY_CMS;// 카카오 할인중개 수수료
	private Integer INICIS_BACK_CMS;// 이니시스 백마진
	
	
}
