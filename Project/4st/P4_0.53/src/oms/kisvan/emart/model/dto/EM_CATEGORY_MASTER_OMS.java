package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_CATEGORY_MASTER_OMS {
	private String RN;	//순번
	private String CTGR_TYPE;// *카테고리타입*
	private String CH_CD;// *채널코드*
	private Integer CTGR_CD;// *카테고리코드*(PK)
	private String CTGR_NM;// *카테고리명*
	private Integer CTGR_LEVEL;// *카테고리차수*
	private Integer SEQ;// *정렬순서*
	private Integer FIRST_CTGR;// *첫번째카테고리*
	private Integer SECOND_CTGR;// *두번째카테고리*
	private Integer THIRD_CTGR;// *세번째카테고리*
	private Integer REG_USER_CD;// *등록사용자*
	private Timestamp REG_DATE;// *등록일시*
	private Integer MDF_USER_CD;// *수정사용자*
	private Timestamp MDF_DATE;// *수정일시*
	private String LINK_CD; // *배민 링크CD*
	
	private String CTGR_INFO; //카테고리 설명
	
	private String CTGR_TYPE_NM; 	//카테고리타입명
	private String CPN_NM;// *COMPANY MASTER OMS 호출용*
}