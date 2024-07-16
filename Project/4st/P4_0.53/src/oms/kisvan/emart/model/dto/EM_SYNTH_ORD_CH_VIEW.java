package oms.kisvan.emart.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_SYNTH_ORD_CH_VIEW {
	private String CHNEL_CD; // 업체코드
	private String ORD_ID; // 주문번호
	private String ORD_DATE; // 주문일자
	private Integer ORD_AMT; // 상품가합계
	private Integer DELIVER_FEE; // 배달료
	private Integer DELIVER_EVENT_FUND; // 배달료행사지원금
	private Integer PLATFORM_CNT; // 플랫폼건수
	private Integer LEVY; // 추가부담금
	private Integer PLATFORM_FEE; // 플랫폼수수료1
	private Integer PLATFORM_FEE2; // 플랫폼수수료2
	private String SETTLE_MONTH; // 정산연월

}
