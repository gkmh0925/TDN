package oms.kisvan.emart.model.dto;

import java.text.DecimalFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreMonthDetailsInfoDTO {
	private int RN;
	private String STR_CD; //매장코드
	private String STR_NM; //매장명
	private String STO_TYPE_NM;
	private String ORDER_ORD_AMT;
	private String ADD_DEPOSIT;
	private String DELIVER_FEE;
	private String DELIVER_EVENT_FUND;
	private String LEVY;
	private String DELIVERY_AMT;
	private String DELIVERY_SUBSIDY;
	private String PLATFORM_FEE;
	private String PLATFORM_CNT;
	private String DELIVERY_CNT;
	private String NON_MATCH_ORD_DV;
	private String POS_CNT;
	private String NON_MATCH_ORD_DV_POS;
	private String KIS_FEE;
	private String IS_MODIFY;
	private String IS_COMPLET;
	private String IS_SETTLE;
	
    public void setDELIVERY_AMT(int DELIVERY_AMT) {
        this.DELIVERY_AMT = formatAmount(DELIVERY_AMT);
    }
	
    public void setORDER_ORD_AMT(int ORDER_ORD_AMT) {
        this.ORDER_ORD_AMT = formatAmount(ORDER_ORD_AMT);
    }
	
    public void setDELIVER_FEE(int DELIVER_FEE) {
        this.DELIVER_FEE = formatAmount(DELIVER_FEE);
    }
    
    public void setDELIVER_EVENT_FUND(int DELIVER_EVENT_FUND) {
        this.DELIVER_EVENT_FUND = formatAmount(DELIVER_EVENT_FUND);
    }
	
    public void setPLATFORM_FEE(int PLATFORM_FEE) {
        this.PLATFORM_FEE = formatAmount(PLATFORM_FEE);
    }
	
    public void setKIS_FEE(int KIS_FEE) {
        this.KIS_FEE = formatAmount(KIS_FEE);
    }
	
    // 숫자를 천 단위 콤마로 포맷팅하는 메서드
    private String formatAmount(int amount) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(amount);
    }
	
}
