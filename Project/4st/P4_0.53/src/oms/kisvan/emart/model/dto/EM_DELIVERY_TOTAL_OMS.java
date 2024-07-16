package oms.kisvan.emart.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_DELIVERY_TOTAL_OMS {
	private String CPN_NM; // 구분
	private Integer PLATFORM_AMT; // 플랫폼 매출액
	private Integer ADD_DEP_AMT; // 추가입금액
	private Integer PLATFORM_FEE; // 플랫폼수수료1
	private Integer PLATFORM_FEE2; // 플랫폼수수료2
	private Integer CUSTORMER_CHARGE;// 고객부담금
	private Integer DELIVER_EVENT_FUND;// 배달료 행사지원금
	private Integer ADDITIONAL_CHARGE; // 추가부담금
	private Integer DELIVERY_AGENCY_FEE;// 배달대행료
	private Integer DELIVER_SUPPORT_FUND;// 배달지원금
	private Integer KIS_FEE;// KIS중계수수료

}