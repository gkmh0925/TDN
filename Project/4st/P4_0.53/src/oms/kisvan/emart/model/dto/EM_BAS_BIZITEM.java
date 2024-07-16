package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_BAS_BIZITEM {
	private String BIZITEMID; //
	private String BIZITEMTYPE; //
	private String ISIMP; //
	private String REGDATE; //
	private Timestamp STOCD; //

}
