package oms.kisvan.emart.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_SYNTH_DV_CH_VIEW {
	private String CHNEL_CD; // 업체코드
	private String ORD_DATE; // 배달일자
	private String RECEIPT_TIME; // 배달시간
	private String ORD_ID; // 주문번호
	private Integer DELIVER_FEE; // 배달대행료
	private Integer ADD_DELIVER_FEE; // 추가대행료
	private Integer DELIVERY_CNT; // 배달건수
	private String SETTLE_MENTH; // 정산연월

}
