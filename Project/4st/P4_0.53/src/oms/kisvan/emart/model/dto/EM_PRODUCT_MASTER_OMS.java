package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_PRODUCT_MASTER_OMS {
	private String CH_CD; 
	private String PRODUCT_CD; 
	private String PRODUCT_NM; 
	private Integer SEQ; 
	private Integer NORMAL_AMOUNT; 
	private Integer DISCOUNT_AMOUNT; 
	private Integer ORD_MAX_COUNT; 
	private Integer CONDITION_NO; 
	private Integer TARGET_VALUE; 
	private Integer BENEFIT_VALUE; 
	private Integer RANKING; 
	private String PRODUCT_STATE; 
	private String EVENT_TYPE; 
	private String PRODUCT_GUIDE; 
	private Integer REG_USER_CD; 
	private Timestamp REG_DATE; // *등록일시*
	private Integer MDF_USER_CD; 
	private Timestamp MDF_DATE; 
	
	
	
	
	
	


}