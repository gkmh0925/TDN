package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_SETTLE_BAEMIN_DV_ORD_OMS {
	private String ORD_DATE; 					//주문일자(PK)
	private String CH_ORD_NO;					//배민주문번호(PK)
	private String CH_CD;						//채널사코드(PK)	
	private Integer SET_BAEMIN_DV_SEQ; 			//배민배달시퀀스번호(PK)
	private String BRANCH_NO; 					//지점번호
	private String BRANCH_NM; 					//상호명	
	private String EXP_PAY_DATE;			    //지급예정일자
	private String PAY_DATE;	   				//결제일자
	private String PAY_TIME;					//결제시각
	private String SETTLE_STD_DATE;				//정산기준일자
	private String BAEMIN_SETTLE_TYPE;			//정산유형
	private String BAEMIN_SETTLE_CYCLE;			//정산주기
	private String SALE_SCALE;					//매출규모
	private String ORD_ID;						//주문서번호
	private String ORD_STATE;					//주문상태
	private String ITEM_NM;						//상품명
	private String ITEM_OPTION_NM;				//옵션명
	private String ITEM_ORD_NO;					//상품주문번호
	private String DELIVERY_TYPE;				//배송방식
	private String DELIVERY_SUBJECT;			//배달주체
	private Integer SALE_ITEM_AMT;				//매출상품금액
	private Integer SALE_DV_AMT;				//매출배달팁
	private Integer SALE_ENVELOPE_AMT;			//매출봉투값
	private Integer SALE_PARTIAL_REFUND;		//매출부분환불금액
	private Integer SALE_SELLER_CPN_AMT;		//매출셀러부담쿠폰금액
	private Integer SALE_SUM;					//매출합계
	private Integer SETTLE_PAY_CHARGE_TARGET_AMT;//결제정산수수료 부과 대상금액	
	private Integer RELAY_FEE_CHARGE_TARGET_AMT;//중개이용료 부과 대상금액
	private Integer COMM_SD_SUPPLY_AMT;			//결제정산기준수수료
	private Integer COMM_SD_PREFERENTIAL_AMT;	//결제정산우대수수료
	private Integer COMM_PAY_SETTLE_VAT;		//결제정산수수료부가세
	private Integer COMM_RELAY_FEE_SUPPLY_AMT;	//중개이용료공급가
	private Integer COMM_RELAY_FEE_VAT;			//중개이용료부가세
	private Integer COMM_BM_RD_FEE_SUPPLY_AMT;	//배민라이더스 이용료
	private Integer COMM_BM_RD_FEE_VAT;			//배민라이더스 이용료 부가세
	private Integer COMM_VAT_INCLUDED;			//수수료(VAT포함)
	private Integer SETTLE_AMT;					//정산금액
	private Integer USER_CD;					//사용자코드
	private Timestamp REG_DATE;					//등록일시
	
	
	// 추가 컬럼
	private Integer RN; 						// 순번
	private String USER_NM; 					// 등록자명
	private String SETTLE_MONTH; 				// 월정산

	
	
	
	
}
