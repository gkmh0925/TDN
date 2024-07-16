package oms.kisvan.emart.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class COMPARE_CAL_RELAY {
	
	private String ORD_TIME;
	private String ORD_ID;
	private String CPN_CD ;
	private String 	CPN_NM;
	
	private String ORD_STATE;
	private String FRANCHISE_CODE;
	
	private String FRANCHISE_NAME;
	private String STATE_RESULT;
	
	private String ORD_TIME_R;
	private String ORD_ID_R;
	private String CPN_CD_R;
	private String CPN_NM_R;
	
	private String SETTLE_MONTH;
	private Integer SET_RELAY_SEQ;
	private String 	DELIVERY_DATE;
	private Integer KIS_FEE;
	
}
