package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_BAS_CODEMAP {
	private String CODE_TITLE; //
	private String CHANL; //
	private String STOREID; //
	private String CLIENT_ID; //
	private String CHANL_ID; //
	private String STATE; //
	private Timestamp REGDATE; //
	private String REGUSER; //
	private String CODENAME; //
	private String BIZITEMID; //
	private String CODEDESC; //
	private String PRICE; //
	private String CODE_ID; //
}
