package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_SETTLE_POS_ORD_OMS {
	private String ORD_DT; // 주문일자(PK)
	private String CHNEL_CD; // 제휴사구분코드(PK)
	private String STR_CD; // 점포코드(PK)
	private String CH_ORD_NO; // 채널사주문번호(PK)
	private String SALE_STORE; // 매출발생점포코드(PK)
	private String DTORD_NO; // 상세주문번호(PK)
	private String EM_ORD_NO; // 이마트주문번호(PK)
	private String TRANL_CD; // 배송업체
	private Integer ORD_AMT; // 주문금액(배달비제외)
	private String ORD_STAT; // 주문상태
	private String NPAY_ORD_NO; // 네이버 NPAY번호
	private String PICKUP_CD; // 픽업코드
	private String MFC_TP_CD; // MFC구분
	private Integer USER_CD; // 사용자코드
	private Timestamp REG_DATE; // 등록일시

	// 추가 컬럼
	private Integer RN; // 순번
	private String USER_NM; // 사용자명
	private String CHNEL_NM; // 채널명
	private String STR_NM; // 매장명
	private String SALE_STORE_NM; // 판매매장명
	private String STATE_RESULT; // 정산상태구분
	
}
