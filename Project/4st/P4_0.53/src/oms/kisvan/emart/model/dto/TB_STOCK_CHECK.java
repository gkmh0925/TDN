package oms.kisvan.emart.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class TB_STOCK_CHECK {
	private String STO_ID; // *매장ID*
	private String PRD_CD; // *상품코드*
	private Integer PRD_CNT; // *상품수량*
	private Timestamp REGDT; // *등록일시*
	private Integer POL_CNT; // POL_CNT

}
