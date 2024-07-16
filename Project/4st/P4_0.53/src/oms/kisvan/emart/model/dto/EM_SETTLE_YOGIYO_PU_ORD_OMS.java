package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_SETTLE_YOGIYO_PU_ORD_OMS {
	private String ORD_DATE;// 주문일자(PK)
	private String ORD_ID;// 주문ID(PK)
	private String CH_ORD_NO;// 채널사주문ID(PK)
	private String STORE_ID;// 매장코드(PK)
	private String CH_CD;// 채널사코드(PK)
	private Integer SET_YOGIYO_PU_SEQ;// 요기요 픽업 시퀀스(PK)
	private String ORD_TIME;// 주문일시
	private String REFUND_TIME;// 환불일시
	private String PAY_EXPECTED_DATE;// 지급예정 일자
	private String BIZ_NO;// 사업자번호
	private String STORE_NM;// 음식점명
	private String ORD_NO;// 주문번호
	private Integer ITEM_SUM_AMT;// 상품가 합계
	private Integer MIN_ORD_ADD_PAY;// 최소주문 추가결제액
	private Integer DELIVER_FEE;// 배달료
	private Integer ORD_TOT_AMT;// 주문 총액
	private Integer ORD_ONLINE_AMT;// 주문 금액 구분 : 온라인 주문
	private Integer ORD_OFFLINE_AMT;// 주문 금액 구분 : 현장 주문
	private Integer RESTAURANT_SELF_DIS;// 레스토랑 자체할인
	private Integer PMT_DIS_YOGIYO_LEVY_AMT;// 프로모션 할인금액 : 요기요 부담
	private Integer PMT_DIS_FRANCHISEE_LEVY_AMT;// 프로모션 할인금액 : 프랜차이즈 부담
	private Integer PMT_DIS_RESTAURANT_LEVY_AMT;// 프로모션 할인금액 : 레스토랑 부담
	private String ORD_RELAY_FEE_RATE;// 주문중개이용료 : 주문중개이용료율(+VAT)
	private Integer ORD_RELAY_FEE_TOT_AMT;// 주문중개이용료 : 주문중개이용료 총액
	private String PAY_RELAY_FEE_RATE;// 결제이용료 : 결제이용료율(+VAT)
	private Integer PAY_RELAY_FEE_TOT_AMT;// 결제이용료 : 결제이용료 총액
	private Integer DELIVERY_AGENT_FEE;// 배달대행이용료 : 배달대행이용료 총액
	private Integer TOT_SUM_FEE;// 이용료 전체 합계
	private Integer OD_DELI_FEE_REST_SELF_FUND;// OD배달료 매출 : 레스토랑 자체펀딩 총액
	private Integer YO_TIME_FEE_TOT_YO_TIME_FEE;// 요타임딜이용료 : 요타임딜이용료 총액
	private Integer SETTLE_AMT;// 정산 금액
	private String PAY_TYPE;// 결제유형
	private String PAY_TYPE_DETAIL;// 결제유형 상세
	private String PAY_FORM;// 결제형태
	private String PAY_FORM_DETAIL;// 결제형태 상세
	private String DELIVERY_TYPE;// 배달 유형(VD/OD)
	private String FRANCHISEE_ID;// 프랜차이즈 ID
	private String CPN_NM;// 회사명
	private String BIZ_NM;// 사업자명
	private String DELIVERY_ADDRESS1;// 배달주소1
	private Integer USER_CD;// 사용자코드
	private Timestamp REG_DATE;// 등록일시

	// 추가 컬럼
	private Integer RN; // 순번
	private String USER_NM; // 등록자명
	//23.02 엑셀추가
	private Integer REFERRAL_ADVERTISEMENT_FEE; //추천광고이용료:추천광고이용료 총액
	
}
