package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_SETTLE_CHAINLOGIS_DV_OMS {
	private String ORD_DATE; //     주문일자(PK)
	private String ORD_ID; //   주문번호(PK)
	private String DELIVER_DATE; //     배달일자(PK)
	private Integer SET_CHAINLOGIS_DV_SEQ; //   체인로지스 배달 시퀀스(PK)
	private String RESERVATION_NO; //   예약번호
	private String STORE_NM; //     점포명
	private Integer DELIVERY_TOT_AMT; //    배달총금액(부가세포함)
	private Integer USER_CD; //     사용자코드
	private Timestamp REG_DATE; //  등록일시

	// 추가 컬럼
	private Integer RN; // 순번
	private String USER_NM; // 등록자명

}
