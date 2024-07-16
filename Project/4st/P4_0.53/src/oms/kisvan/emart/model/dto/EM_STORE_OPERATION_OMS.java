package oms.kisvan.emart.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_STORE_OPERATION_OMS {
	private String STO_CD;// *매장코드*(PK)(FK)
	private String LOC_LAT;// *위도*
	private String LOC_LONG;// *경도*
	private String DAY_START_TIME;// *주중 영업 시작*
	private String DAY_END_TIME;// *주중 영업 끝*
	private String WEEKEND_START_TIME;// *주말 영업 시작*
	private String WEEKEND_END_TIME;// *주말 영업 끝*
	private String HOLIDAY_START_TIME;// *공휴일 영업 시작*
	private String HOLIDAY_END_TIME;// *공휴일 영업 끝*
	private Integer MINIMUM_ORDER_PRICE;// *최소 주문 가능 금액(원)*
	private Integer MAXIMUM_ORDER_PRICE;// *최대 주문 가능 금액(원)*
	private Integer DELIVERY_PRICE;// *배달비(원)*
	private Integer ESTIMATED_DELIVERY_TIME;// *배달예상시간*
	private String DELIVERY_CH_CD;// *배달대행사*
	private String ACC_HOLDER;// *예금주*
	private String BANK_NM;// *은행명*
	private String ACC_NUM;// *계좌번호*
	private String STOP_START_DAY;// *운영휴일시작일*
	private String STOP_END_DAY;// *운영휴일종료일*
	private String BREAK_TIME;// *쉬는 시간*

	// JOIN 추가 컬럼
	private String DELIVERY_CH_NM; // 배달대행사명
}
