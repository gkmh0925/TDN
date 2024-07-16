package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_USER_ACCOUNT_OMS {
	private Integer USER_CD; // *사용자코드*(PK)
	private String CPN_CD; // *업체코드*
	private String USER_NM; // *사용자명*
	private String ACCOUNT; // *계정*
	private String PASSWORD; // *비밀번호*
	private String USER_ADDR; // *주소*
	private String USER_ADDR_DETAIL; // *상세주소*
	private String USER_EMAIL; // *이메일*
	private String PHONE; // *핸드폰*
	private String USER_LV; // *유저권한*(FK)
	private String AUTH_NO; // *인증번호*
	private Integer REG_USER_CD; // *등록사용자*
	private Timestamp REG_DATE; // *등록일시*
	private Integer MDF_USER_CD; // *수정사용자*
	private Timestamp MDF_DATE; // *수정일시*
	private String OTP_LINK; // *구글 OTP 링크*

	private String LEVEL_EXP;
	private String REG_USER_NM;
	private String MDF_USER_NM;

	private String IP_ADDRESS;

	private Integer RN; // 행 넘버

	
}