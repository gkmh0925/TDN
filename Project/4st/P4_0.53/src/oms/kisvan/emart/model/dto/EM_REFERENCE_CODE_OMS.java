package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_REFERENCE_CODE_OMS {
	private String DEFINITION_CD; // *정의코드*(PK)
	private String DEFINITION_NM; // *정의명칭*
	private String MEMO; // *메모*
	private Integer REG_USER_CD; // *등록사용자*
	private Timestamp REG_DATE; // *등록일시*
	private Integer MDF_USER_CD; // *수정사용자*
	private Timestamp MDF_DATE; // *수정일시*
	private String GROUP_CD; // *그룹코드*(PK)
}
