package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_ORDER_HISTORY_OMS {
	private Integer HISTORY_ID; // *이력 아이디*(PK)
	private String ORD_ID; // *주문ID*
	private String STORE_ID; // *가맹점 상점ID*
	private String SALE_STORE; // *매출발생점포코드*
	private String DTORD_NO; // *상세주문번호*
	private String HISTORY_TYPE; // *주문이력타입*
	private String ORDER_STATE; // *주문상태*
	private String DELIVERY_STATE; // *배달 대행 상태*
	private String STATUS_CD; // *결과코드*
	private String STATUS_MSG; // *결과메세지*
	private Integer REG_USER_CD; // *등록사용자코드*
	private Timestamp REG_DATE; // *등록일시*
}
