package oms.kisvan.emart.model.dto;

import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_CHANNEL_STORE_OPERATION_OMS {
	private String STO_CD; // *매장코드*(PK)
	private String CH_STATE; // *채널사매장상태*
	private String CH_SEND_FLAG; // *채널사전송플래그*
	private Integer REG_USER_CD; // *등록사용자*
	private Timestamp REG_DATE; // *등록일시*
	private Integer MDF_USER_CD; // *수정사용자*
	private Date MDF_DATE; // *수정일시*
	private String CH_CD; // *채널사코드*(PK)(FK)

	// JOIN 추가 컬럼
	private String CH_STATE_NM; // 채널사 매장 상태명
	private String CH_SEND_FLAG_NM; // 채널사 전송 플래그명

	private String REG_USER_NM; // 등록 사용자명
	private String MDF_USER_NM; // 수정 사용자명
	private String CH_NM; // 채널사명
}