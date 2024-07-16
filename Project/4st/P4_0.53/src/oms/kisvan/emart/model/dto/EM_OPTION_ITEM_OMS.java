package oms.kisvan.emart.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class EM_OPTION_ITEM_OMS {
	private String OPTION_ITEM_CD;// *옵션아이템코드*
	private String OPTION_CD;// *옵션코드*
	private String MENU_CD;// *메뉴코드*
	private String ITEM_CD;// *아이템코드*
	private String ITEM_NM;// *아이템명*
	private Integer PRICE;// *단가*
	private Integer SEQUENCE;// *정렬순서*
}