package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
//채널사별 수수료설정 정보
public class EM_CMS_OPERATION_OMS {
	private String CPN_TYPE; // *업체타입*
	private String CPN_CD; // *업체코드*
	private String CMS_APPLY_TYPE; // *수수료타입*
	private String CMS_APPLY_TYPE_NM; // *수수료타입명*
	private String CMS_CALC_TYPE; // *수수료금액타입*
	private String CMS_CALC_TYPE_NM; // *수수료금액타입명*
	private Integer REG_USER_CD; // 등록 사용자명
	private Timestamp REG_DATE; // *등록일시*
	private Integer MDF_USER_CD; // *수정사용자*
	private Timestamp MDF_DATE; // *수정일시*
	
	
 // 추가 컬럼
	private String REG_USER_NM; // 등록 사용자명
	private String MDF_USER_NM; // 수정 사용자명
	//join
	private String DEFINITION_CD; // 정의코드
	private String DEFINITION_NM; // 정의명
	
}