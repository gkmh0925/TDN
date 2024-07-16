package oms.kisvan.emart.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_ORDER_MENU_OMS {
	private String ORD_ID; // *주문ID*(PK)
	private String STORE_ID; // *가맹점 상점ID*(PK)
	private String SALE_STORE; // *매출발생점포코드*(PK)
	private String DTORD_NO; // *상세주문번호*(PK)
	private String MENU_CD; // *메뉴코드*(PK)
	private Integer MENU_CD_SEQ; // *메뉴코드순번*(PK)
	private String MENU_NM; // *메뉴명*
	private Integer QTY; // *수량*
	private Integer PRICE; // *단가*
	private Integer AMOUNT; // *금액*
}
