package oms.kisvan.emart.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class COMPARE_RELAY {
	private String ORD_DT_P; // POS데이터 주문일자
	private String CHNEL_CD_P; // POS데이터 채널사코드
	private String CHNEL_NM_P; // POS데이터 채널사명
	private String EM_ORD_NO_P; // POS데이터 이마트24주문번호
	private String DTORD_NO_P; // POS데이터 상세주문번호
	private String STR_CD_P; // POS데이터 매장코드
	private String SALE_STORE_P; // POS데이터 판매매장코드
	private Integer ORD_AMT_P; // POS데이터 상품가액
	private String ORD_STAT_P; // POS데이터 주문상태값
	private String ORD_STAT_NM_P; // POS데이터 주문상태명
	private String STATE_RESULT_P; // POS데이터 주문상태값구분
	private String MFC_TP_CD_P; // POS데이터 매장구분
	private String SETTLE_MONTH_P; // POS데이터 월정산

	private String ORD_TIME_R; // 중계데이터 주문일자
	private String FRANCHISE_CODE_R; // 중계데이터 채널사코드
	private String FRANCHISE_NAME_R; // 중계데이터 채널사명
	private String ORD_ID_R; // 중계데이터 이마트24주문번호
	private String DTORD_NO_R; // 중계데이터 상세주문번호
	private String STORE_ID_R; // 중계데이터 매장코드
	private String SALE_STORE_R; // 중계데이터 판매매장코드
	private Integer ACT_AMT_R; // 중계데이터 상품가액
	private Integer TOT_AMT_R; // 중계데이터 총금액
	private String ORD_STATE_R; // 중계데이터 주문상태값
	private String ORD_STATE_NM_R; // 중계데이터 주문상태명
	private String STATE_RESULT_R; // 중계데이터 주문상태값구분
	private String MFC_TP_CD_R; // 중계데이터 매장구분
	private String SETTLE_MONTH_R; // 중계데이터 월정산
	
	private String ORD_DT_C; // 채널사통합 주문일자
	private String CHNEL_CD_C; // 채널사통합 채널사코드
	private String EM_ORD_NO_C; // 채널사통합 이마트24주문번호
	private String DTORD_NO_C; // 채널사통합 상세주문번호
	private String STR_CD_C; // 채널사통합 매장코드
	private String SALE_STORE_C; // 채널사통합 판매매장코드
	private Integer ORD_AMT_C; // 채널사통합 상품가액
	private String ORD_STATE_C; // 채널사통합 주문상태값
	private String ORD_STATE_NM_C; // 채널사통합 주문상태명
	private String STATE_RESULT_C; // 채널사통합 주문상태값구분
	private String MFC_TP_CD_C; // 채널사 매장구분
	private String SETTLE_MONTH_C; // 채널사데이터 월정산

	private String ORD_DT_D; // 배달사통합 주문일자
	private String CHNEL_CD_D; // 배달사통합 배달사코드
	private String CHNEL_NM_D; // 배달사통합 배달사명
	private String EM_ORD_NO_D; // 배달사통합 이마트24주문번호
	private Integer ACT_AMT_D; // 배달사통합 기본배송료
	private Integer TOT_AMT_D; // 배달사통합 전체배송료 (배송료 + 할증)
	private String STATE_RESULT_D; // 배달사통합 주문상태값구분
	private String MFC_TP_CD_D; // 배달사 매장구분
	private String SETTLE_MONTH_D; // 배달사데이터 월정산
}
