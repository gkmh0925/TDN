package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_SETTLE_VROONG_DV_OMS {
	private String ORD_DATE; // 주문일자(PK)
	private String ORD_ID; // 자체주문번호(PK)
	private String STORE_ID; // 상점코드(PK)
	private String RECEIPT_DATE; // 일자(PK)
	private Integer SET_VROONG_DV_SEQ; // 부릉 배달 시퀀스(PK)
	private String STORE_NM; // 상점명
	private String FRANCHISEE_NM; // 프렌차이즈명
	private String FRANCHISEE_TYPE; // 프렌차이즈유형
	private String VROONG_NO; // 부릉오더넘버
	private String RE_VROONG_NO; // 재배송부릉오더넘버
	private String PRIME_DELIVERY_NO; // 프라임딜리버리넘버
	private String SELF_DELIVERY_NO; // 자체배송번호
	private String STATE; // 상태
	private String START_ADDRESS; // 출발지주소
	private String FINISH_ADDRESS; // 도착지주소
	private String PAY_TYPE; // 결제수단
	private Integer ITEM_AMT; // 상품가액
	private Integer CANCEL_CMS; // 취소수수료
	private Integer SETTLE_ITEM_AMT; // 정산상품가액(송급포함)
	private Integer BASIC_DELIVER_FEE; // 기본배송료
	private Integer ADD_DISTANCE_AMT; // 거리추가
	private Integer WEATHER_PREMIUM_AMT; // 기상할증
	private Integer AREA_PREMIUM_AMT; // 지역할증
	private Integer PICKUP_PREMIUM_AMT; // 픽업할증
	private Integer HOLIDAY_PREMIUM_AMT; // 명절할증
	private Integer OVERLOAD_PREMIUM_AMT; // 과적할증
	private Integer RE_MOVE_DELIVER_FEE; // 재이동배송비
	private Integer RETURN_DELIVER_FEE; // 반납배송비
	private Integer SETTLE_CANCEL_CMS; // 정산취소수수료
	private Integer CARD_CMS; // 카드수수료
	private Integer STORE_DEPOSIT_AMT; // 상점입금액
	private Integer DELIVERY_AGENCY_CMS; // 배달대행수수료
	private String CARD_CMS_RATE; // 카드수수료율(%)
	private String BIGO; // 비고
	private Integer DISTANCE; // 거리
	private String RECEIPT_TIME; // 접수시각
	private String ASSIGNED_TIME; // 배차시각
	private String PICKUP_TIME; // 픽업시각
	private String COMPLET_TIME; // 완료시각
	private String CANCEL_TIME; // 취소시각
	private String FINAL_PAY_TYPE; // 최종결제수단
	private Integer COMPLET_COUNT; // 완료건수
	private Integer CANCEL_COUNT; // 취소건수
	private Integer PREMIUM_AMT; // 할증금액
	private String PREMIUM_MEMO; // 할증내용
	private Integer USER_CD; // 사용자코드
	private Timestamp REG_DATE; // 등록일시

	// 추가 컬럼
	private Integer RN; // 순번
	private String USER_NM; // 등록자명

}
