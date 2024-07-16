package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

//!@@0701 yh

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class TB_DELIVERY_STATE {
	
	private Integer RN;
	
	// 상품
	private String CH_CD;
	private Timestamp MDF_DATE;
	private Integer MDF_USER_CD;
	private String PRD_BASE_IMG_URL;
	private String PRD_CD;
	private String PRD_CTGR_CD;
	private String PRD_DT_IMG_URL;
	private String PRD_GUIDE;
	private String PRD_NM;
	private String PRD_STATE;
	private Timestamp PRD_STATE_MDF_DT;
	private String PRD_TYPE;
	private Timestamp REG_DATE;
	private Integer REG_USER_CD;
	private Integer SALE_AMT;
	private String SALE_END_DT;
	private String SALE_START_DT;
	private Integer SEQ;
	private Integer STD_AMT;
	private String STO_CTGR_CD;
	private String TAX_TYPE;
	private String USE_SALES_ADULT;
	private String WORK_TYPE;
	private String REG_USER_NM;
	private String MDF_USER_NM;
	
	// 채널사
	private String CPN_NM;
	private String CPN_TYPE;

	
		
	
	//카테고리
	private String CTGR_TYPE;
	private String CTGR_CD;
	private String CTGR_NM;
	private Integer CTGR_LEVEL;
	private String FIRST_CTGR;
	private String SECOND_CTGR;
	private String THIRD_CTGR;
	private String LINK_CD;
	private String CTGR_INFO;
}