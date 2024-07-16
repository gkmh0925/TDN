package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;


@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_RELAY_TOTAL_OMS {
	private String SETTLE_MONTH;
	private Integer SET_RELAY_SEQ;// 요기요 배달 시퀀스(PK)
	private String CPN_CD  ; //업체코드
	private String CPN_NM  ; //업체명
	private String ORD_ID  ; //주문아이디
	private String ORD_DATE  ;//주문일자
	private String DELIVERY_DATE; //배송일자
	private Integer KIS_FEE  ; //KIS 수수료
	private String APPLY_GUBUN;
	private Integer USER_CD; // 사용자코드
	private Timestamp REG_DATE; // 등록일시

	// 추가 컬럼
	private Integer RN; // 순번
	private String USER_NM; // 등록자명
}
