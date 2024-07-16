package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_SETTLE_KISAPP_ORD_OMS {
	private Integer RN; // 순번
	private String ORD_DATE;// 주문일자(PK)
	private String DTORD_NO;// 상세주문번호(PK)
	private String CH_ORD_NO;// 주문번호(PK)
	private String STORE_ID;// 점포코드(PK)
	private Integer SET_KISAPP_SEQ;// 키스 앱 시퀀스
	private String GUBUN;// 구분
	private String CH_NM;// 채널사명
	private String ORD_TIME;// 주문일시
	private String PAY_EXPECTED_DATE;// 지급예정일
	private String BIZ_NO;// 사업자번호
	private String STORE_NM;// 점포명
	private Integer MENU_TOT_AMT;// 상품가합계
	private Integer DELIVER_FEE;// 배달료
	private Integer ACT_AMT;// 결제금액
	private Integer CMS_AMT;// 수수료
	private Integer VAT_AMT;// VAT
	private Integer PAY_AMT;// 지급액
	private String BIGO;// 비고
	private Integer USER_CD;// 사용자코드
	private Timestamp REG_DATE;// 등록일시

	// 추가 컬럼
	private String USER_NM; // 등록자명

	
}
