package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_DELIVERY_FEE_MASTER_OMS {
	
	private int RN; // 행 넘버
    private int DV_SEQ_CD; // *배달료 마스터 코드*
    private String START_MONTH; // *적용시작일*
    private String END_MONTH; // *적용종료일*
    private String MASTER_NM; // *마스터명*
    private String MASTER_TYPE; // *마스터타입*
    private int AMT; // *금액*
    private Integer REG_USER_CD; // *등록사용자*
    private Timestamp REG_DATE; // *등록일시*
    private Integer MDF_USER_CD; // *수정사용자*
    private Timestamp MDF_DATE; // *수정일시*

}